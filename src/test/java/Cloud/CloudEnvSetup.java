package Cloud;

import cloudInfra.CloudHelper.CloudTestBase;
import org.testng.annotations.Test;

public class CloudEnvSetup extends CloudTestBase {

    @Test(testName = "Setup Environment")
    public void environmentSetup() {
        cloudService.create();

    }

}
