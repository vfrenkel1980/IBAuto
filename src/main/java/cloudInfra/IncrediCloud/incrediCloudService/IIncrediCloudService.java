package cloudInfra.IncrediCloud.incrediCloudService;

import cloudInfra.IncrediCloud.metadata.VirtualMachineInfo;

import java.util.ArrayList;

public interface IIncrediCloudService {

    void initRest();

    void loginToCloud();

    void refreshToken();

    int getStatusQueue(boolean getAllMachines);

    boolean waitForDeliveredMachines(int numOfMachines);

    void setSecretInRegistry();

    void deactivateCloud();

    String getCloudStatus();

    void waitForCloudStatus(String status);

    ArrayList<String> getVirtualMachinesNames();

    VirtualMachineInfo getVirtualMachineInformation(String machineName);


}
