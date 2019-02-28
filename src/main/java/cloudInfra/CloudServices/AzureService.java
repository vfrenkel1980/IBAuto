package cloudInfra.CloudServices;

import com.aventstack.extentreports.Status;
import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.compute.*;
import com.microsoft.azure.management.network.*;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.CreatedResources;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.azure.management.storage.StorageAccount;
import com.microsoft.rest.LogLevel;
import frameworkInfra.utils.StaticDataProvider.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static frameworkInfra.Listeners.SuiteListener.test;

public class AzureService extends CloudService{

    private File credFile;
    private Azure azure;
    private VirtualMachine vm;
    private Network network;
    private NetworkSecurityGroup backEndNSG;
    private CreatedResources<VirtualMachine> virtualMachines;
    private CreatedResources<NetworkInterface> networkInterfaces;
    private CreatedResources<Disk> disks;
    private List disksKeys;
    private List networkInterfacesKeys;
    private List virtualMachinesKeys;
    private Region region = Region.EUROPE_WEST;
    private String resGroup = "qa-performance";
    private VirtualMachineSizeTypes type;
    public int vmCount;
    private String initiator;
    protected List<String> diskIds = new ArrayList<>();
    protected List<String> nicIds = new ArrayList<>();
    protected List<String> vmIds = new ArrayList<>();


    public AzureService(String cpu, String memory, String vmCount, String initiator) {
        if (cpu.equals("2") && memory.equals("4"))
            this.type = VirtualMachineSizeTypes.STANDARD_A2_V2;
        else if (cpu.equals("4") && memory.equals("8"))
            this.type = VirtualMachineSizeTypes.STANDARD_A4_V2;
        else if (cpu.equals("8") && memory.equals("16"))
            this.type = VirtualMachineSizeTypes.STANDARD_A8_V2;

        this.vmCount = Integer.parseInt(vmCount);
        this.initiator = initiator;
        registerCloud();
        createNetwork();
    }

    private void registerCloud() {
        try {
            test.log(Status.INFO, "Registering to Azure cloud service...");
            credFile = new File(System.getenv("AZURE_AUTH_LOCATION"));
            azure = Azure.configure()
                    .withLogLevel(LogLevel.BASIC)
                    .authenticate(credFile)
                    .withDefaultSubscription();
            test.log(Status.INFO, "Registration complete");
        } catch (Exception e) {
            test.log(Status.ERROR, "Registration failed");
        }
    }

    private void createNetwork() {
        test.log(Status.INFO, "Creating Network and Security group...");
        network = azure.networks()
                .define(resGroup + "-vnet")
                .withRegion(region)
                .withExistingResourceGroup(resGroup)
                .withAddressSpace("10.0.3.0/24")
                .withSubnet("default","10.0.3.0/24")
                .create();

        backEndNSG = azure.networkSecurityGroups().define("secGroup")
                .withRegion(region)
                .withExistingResourceGroup(resGroup)
                .defineRule("ALLOW-RDP")
                .allowInbound()
                .fromAnyAddress()
                .fromAnyPort()
                .toAnyAddress()
                .toPort(3389)
                .withProtocol(SecurityRuleProtocol.TCP)
                .withDescription("Allow RDP")
                .withPriority(300)
                .attach()
                .create();
        test.log(Status.INFO, "Network and Security Group created.");

    }

    @Override
    public void startVm() {
        test.log(Status.INFO, "Starting VM " + vm + "...");
        vm = azure.virtualMachines().getByResourceGroup(resGroup, initiator);
        vm.start();
        test.log(Status.INFO, vm + "Started");

    }



    public void stopVm(VirtualMachine vm) {
        test.log(Status.INFO, "Stopping VM " + vm + "...");
        vm = azure.virtualMachines().getByResourceGroup(resGroup, vm.name());
        vm.powerOff();
        test.log(Status.INFO, vm + "Stopped");
    }

    @Override
    public void deleteVm() {
        test.log(Status.INFO, "Deleting Vm's...");

        for (String id:vmIds) {
            azure.virtualMachines().deleteById(id);
        }

        for (int i = 0; i < vmCount; i++) {
            azure.disks().deleteById(diskIds.get(i));
            azure.networkInterfaces().deleteById(nicIds.get(i));
        }
        test.log(Status.INFO, "VM's deleted");
    }

    @Override
    public void create() {
        List<Creatable<VirtualMachine>> creatableVirtualMachines = new ArrayList<>();
        List<Creatable<NetworkInterface>> creatableNetworkInterfaces = new ArrayList<>();
        List<Creatable<Disk>> creatableDisk = new ArrayList<>();

        for (int i = 0; i < vmCount; i++) {

            Creatable<NetworkInterface> networkInterfaceCreatable = azure.networkInterfaces()
                    .define("testNIC-" + i)
                    .withRegion(region)
                    .withExistingResourceGroup(resGroup)
                    .withExistingPrimaryNetwork(network)
                    .withSubnet("default")
                    .withPrimaryPrivateIPAddressDynamic()
                    .withExistingNetworkSecurityGroup(backEndNSG);
            creatableNetworkInterfaces.add(networkInterfaceCreatable);

            Creatable<Disk> disksCreatable= azure.disks()
                    .define(SdkContext.randomResourceName("test_dsk", 30))
                    .withRegion(region)
                    .withNewResourceGroup(resGroup)
                    .withWindowsFromVhd("https://jsudh.blob.core.windows.net/vhd/hlp-6-2-19.vhd")
                    .withSizeInGB(127)
                    .withSku(DiskSkuTypes.STANDARD_LRS);
            creatableDisk.add(disksCreatable);
        }

        test.log(Status.INFO, "Creating NIC's and Disks....");

        networkInterfaces = azure.networkInterfaces().create(creatableNetworkInterfaces);
        networkInterfacesKeys = new ArrayList(networkInterfaces.keySet());
        disks = azure.disks().create(creatableDisk);
        disksKeys = new ArrayList(disks.keySet());

        test.log(Status.INFO, "NIC's and Disks created");
        test.log(Status.INFO, "Creating Storage account...");

        Creatable<StorageAccount> storageAccountCreatable = azure.storageAccounts().define("jsudh")
                .withRegion(region)
                .withExistingResourceGroup(resGroup);

        test.log(Status.INFO, "Storage account created");

        for (int i = 0; i < vmCount; i++) {
            Creatable<VirtualMachine> virtualMachineCreatable = azure.virtualMachines()
                    .define("TESTVM-" + i)
                    .withRegion(region)
                    .withExistingResourceGroup(resGroup)
                    .withExistingPrimaryNetworkInterface(networkInterfaces.get(networkInterfacesKeys.get(i)))
                    .withSpecializedOSDisk(disks.get(disksKeys.get(i)), OperatingSystemTypes.WINDOWS)
                    .withSize(type)
                    .withNewStorageAccount(storageAccountCreatable);
            creatableVirtualMachines.add(virtualMachineCreatable);
        }
        test.log(Status.INFO, "Creating VM's...");
        virtualMachines = azure.virtualMachines().create(creatableVirtualMachines);
        virtualMachinesKeys = new ArrayList(virtualMachines.keySet());
        test.log(Status.INFO, "VM's created");
    }

    @Override
    public void saveCloudIdsToJSON(){
        JSONObject obj = new JSONObject();
        JSONArray diskIds = new JSONArray();

        for (int i = 0 ; i < vmCount; i++){
            diskIds.add(disks.get(disksKeys.get(i)).id());
        }
        obj.put("Disk ID's", diskIds);

        JSONArray nicIds = new JSONArray();
        for (int i = 0 ; i < vmCount; i++){
            nicIds.add(networkInterfaces.get(networkInterfacesKeys.get(i)).id());
        }
        obj.put("NIC ID's", nicIds);


        JSONArray machineIds = new JSONArray();
        for (int i = 0 ; i < vmCount; i++){
            machineIds.add(virtualMachines.get(virtualMachinesKeys.get(i)).id());
        }
        obj.put("VM ID's", machineIds);

        try (FileWriter file = new FileWriter(Locations.CLOUD_IDS_JSON)) {
            file.write(obj.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.getMessage();
        }
    }

    @Override
    public void getCloudIdsFromJSON(){
        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(new FileReader("c:\\test.json"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray msg = (JSONArray) jsonObject.get("Disk ID's");
            diskIds.addAll(msg);
            JSONArray msg2 = (JSONArray) jsonObject.get("NIC ID's");
            nicIds.addAll(msg2);
            JSONArray msg3 = (JSONArray) jsonObject.get("VM ID's");
            vmIds.addAll(msg3);
        } catch (ParseException | IOException e) {
            e.getMessage();
        }
    }

    @Override
    public String getType(){
        return this.type.toString();
    }
}
