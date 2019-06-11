package cloudInfra.IncrediCloud.incrediCloudService;

import java.util.List;

public interface IIncrediCloudService {

    void initRest();

    void loginToCloud();

    void refreshToken();

    int getStatusQueue(boolean getAllMachines);

    boolean waitForDeliveredMachines(int numOfMachines);

    void setSecretInRegistry();

    void deactivateCloud();

    List getCouldIps();


}
