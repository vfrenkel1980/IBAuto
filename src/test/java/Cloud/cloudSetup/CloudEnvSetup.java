package Cloud.cloudSetup;

import cloudInfra.CloudHelper.CloudTestBase;
import org.testng.annotations.Test;

public class CloudEnvSetup extends CloudTestBase {

    @Test(testName = "Start Initiator")
    public void startInitiator() {
        cloudService.startVm();
    }

    @Test(testName = "Setup Environment", dependsOnMethods = { "startInitiator"})
    public void environmentSetup() {
        cloudService.create();
    }

    @Test(testName = "Create JSON", dependsOnMethods = { "environmentSetup"})
    public void createJSON() {
        cloudService.saveCloudIdsToJSON();
    }



}
