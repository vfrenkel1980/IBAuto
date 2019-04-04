package Cloud.cloudSetup;

import cloudInfra.CloudHelper.CloudTestBase;
import org.testng.annotations.Test;

public class CloudEnvDestruction extends CloudTestBase {

    @Test(testName = "Destroy cloud Environment")
    public void destroyEnvironment() {
        cloudService.getCloudIdsFromJSON();
        cloudService.deleteVm();
    }

    @Test(testName = "Turn Off Initiator", dependsOnMethods = { "destroyEnvironment"})
    public void turnOffInitiator() {
        cloudService.stopVm(INITIATOR);
    }
}
