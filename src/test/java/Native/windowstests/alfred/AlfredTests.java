package Native.windowstests.alfred;

import frameworkInfra.testbases.AlfredTestBase;
import frameworkInfra.utils.StaticDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AlfredTests extends AlfredTestBase {

    @Test(testName = "Audacity 2017 - Debug - build" , groups = { "Build" })
    public void audacityDebugBuild(){
        int returnCode = ibService.cleanAndBuild(StaticDataProvider.IbLocations.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC15_ALFRED.AUDACITY_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }
}
