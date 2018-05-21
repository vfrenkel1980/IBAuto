package Native.windowstests;

import frameworkInfra.testbases.BatmanBCTestBase;
import frameworkInfra.utils.Parser;
import frameworkInfra.utils.StaticDataProvider.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

public class BatmanVC15PreviewTests extends BatmanBCTestBase {


    @Test(testName = "Audacity 2017 Preview - Debug - build" , groups = { "Build" })
    public void audacityDebugBuild(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC15Preview_BATMAN.AUDACITY_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "ACE 2017 Preview - Debug - build" , groups = { "Build" })
    public void ace2017DebugBuild(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC15Preview_BATMAN.ACE_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Big Project 2017 Preview - Debug - build" , groups = { "Build" })
    public void bigProjectDebugBuild(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC15Preview_BATMAN.BIGPROJECT_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Big Project 2017 Preview - Release - build" , groups = { "Build" })
    public void bigProjectReleaseBuild(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC15Preview_BATMAN.BIGPROJECT_X32_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Check that \"Predicted\" execution is enable: VS2017 Preview")
    public void checkForPredictedExecutionWithoutMSBuild() {
        ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC15Preview_BATMAN.AUDACITY_X32_DEBUG, "%s"));
        Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.PREDICTED_DISABLED));
    }

    @Test(testName = "Check if IBMSBHLP.log created")
    public void checkIBMSBHLPlogCreation(){
        ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC15Preview_BATMAN.AUDACITY_X32_DEBUG, "%s"));
        File ibmsbhlpLog = new File(IbLocations.LOGS_ROOT + "\\IBMSBHLP.log");
        Assert.assertFalse(ibmsbhlpLog.exists(), "IBMSBHLP.log file was created during the \"Predicted\" execution");
    }

}
