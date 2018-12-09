package Native.windowstests.phoenix;

import frameworkInfra.testbases.SingleUseVMTestBase;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import frameworkInfra.utils.parsers.Parser;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PhoenixSingleUseVMTests extends SingleUseVMTestBase {
//Win10
    @Test(testName = "SU VM Build Test")
    public void sUVMBuildTest() {
        ibService.agentServiceStart();
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_PHOENIX.AUDACITY_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '"), "No agents were assigned to the build");
    }

    @Test(testName = "SU VM Stop Service Positive Test")
    public void sUVMStopServicePositiveTest() {
        //check coord reg key for single use vm kill -
        ibService.agentServiceStart();
        SystemActions.sleep(10);
        ibService.agentServiceStop();
        SystemActions.sleep(25);
        ibService.agentServiceStart();
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_PHOENIX.ACE_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '"), "No agents were assigned to the build");
    }
}
