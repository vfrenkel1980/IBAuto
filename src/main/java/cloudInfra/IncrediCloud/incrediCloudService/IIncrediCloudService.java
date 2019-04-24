package cloudInfra.IncrediCloud.incrediCloudService;

public interface IIncrediCloudService {

    void initRest();

    void loginToCloud();

    void refreshToken();

    int getStatusQueue(boolean getAllMachines);

    boolean waitForDeliveredMachines(int numOfMachines);

    void setSecretInRegistry();
}
