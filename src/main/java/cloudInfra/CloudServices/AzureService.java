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
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static frameworkInfra.Listeners.SuiteListener.test;

public class AzureService implements ICloudService {

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
    private String resGroup = "TestRes2";

    @Override
    public void registerCloud() {
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

    @Override
    public void createNetwork() {
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
    public void createVm(int vmCount) {
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
                    .withSize(VirtualMachineSizeTypes.STANDARD_B2S)
                    .withNewStorageAccount(storageAccountCreatable);
            creatableVirtualMachines.add(virtualMachineCreatable);
        }
        test.log(Status.INFO, "Creating VM's...");
        virtualMachines = azure.virtualMachines().create(creatableVirtualMachines);
        virtualMachinesKeys = new ArrayList(virtualMachines.keySet());
        test.log(Status.INFO, "VM's created");
    }

    @Override
    public void startVm(VirtualMachine vm) {
        test.log(Status.INFO, "Starting VM " + vm + "...");
        vm = azure.virtualMachines().getByResourceGroup(resGroup, vm.name());
        vm.start();
        test.log(Status.INFO, vm + "Started");

    }

    @Override
    public void stopVm(VirtualMachine vm) {
        test.log(Status.INFO, "Stopping VM " + vm + "...");
        vm = azure.virtualMachines().getByResourceGroup(resGroup, vm.name());
        vm.powerOff();
        test.log(Status.INFO, vm + "Stopped");
    }

    @Override
    public void deleteVm(int vmCount) {
        test.log(Status.INFO, "Deleting Vm's...");

        for (int i = 0; i < vmCount; i++) {
            azure.virtualMachines().deleteById(virtualMachines.get(virtualMachinesKeys.get(i)).id());
        }

        for (int i = 0; i < vmCount; i++) {
            azure.disks().deleteById(disks.get(disksKeys.get(0)).id());
            azure.networkInterfaces().deleteById(networkInterfaces.get(networkInterfacesKeys.get(i)).id());
        }
        test.log(Status.INFO, "VM's deleted");
    }
}
