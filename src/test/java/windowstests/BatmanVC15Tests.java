package windowstests;

import frameworkInfra.testbases.BatmanBCTestBase;
import frameworkInfra.testbases.UnitTestBase;
import frameworkInfra.utils.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

import static frameworkInfra.utils.StaticDataProvider.*;

public class BatmanVC15Tests extends BatmanBCTestBase {


    @Test(testName = "Audacity 2017 - Debug - build" , groups = { "Build" })
    public void audacityDebugBuild(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.AUDACITY_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "ACE 2017 - Debug - build" , groups = { "Build" })
    public void ace2017DebugBuild(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.ACE_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Big Project 2017 - Debug - build" , groups = { "Build" })
    public void bigProjectDebugBuild(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.BIGPROJECT_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Big Project 2017 - Release - build" , groups = { "Build" })
    public void bigProjectReleaseBuild(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.BIGPROJECT_X32_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Chrome release - build" , groups = { "Build" })
    public void chromeReleaseBuild(){
        winService.runCommandWaitForFinish(ProjectsCommands.CHROME_RELEASE_CLEAN);
        int returnCode = ibService.cleanAndBuild(ProjectsCommands.CHROME_RELEASE_BUILD);
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Check that \"Predicted\" execution is enable: VS2017 Release")
    public void checkForPredictedExecutionWithoutMSBuild() {
        ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.AUDACITY_X32_DEBUG, "%s"));
        Assert.assertFalse(Parser.doesFileContainString(StaticDataProvider.Locations.OUTPUT_LOG_FILE, StaticDataProvider.WarningMessages.PREDICTED_DISABLED));
    }

    @Test(testName = "Check if IBMSBHLP.log created")
    public void checkIBMSBHLPlogCreation(){
        ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.AUDACITY_X32_DEBUG, "%s"));
        File ibmsbhlpLog = new File(StaticDataProvider.Locations.LOGS_ROOT + "\\IBMSBHLP.log");
        Assert.assertFalse(ibmsbhlpLog.exists(), "IBMSBHLP.log file was created during the \"Predicted\" execution");
    }

}
