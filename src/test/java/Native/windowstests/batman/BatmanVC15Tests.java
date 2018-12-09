package Native.windowstests.batman;

import com.aventstack.extentreports.Status;
import frameworkInfra.testbases.BatmanBCTestBase;
import frameworkInfra.utils.*;
import frameworkInfra.utils.parsers.Parser;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

import static frameworkInfra.Listeners.SuiteListener.test;
import static frameworkInfra.utils.StaticDataProvider.*;

public class BatmanVC15Tests extends BatmanBCTestBase {


    @Test(testName = "Audacity 2017 - Debug - build" , groups = { "Build" })
    public void audacityDebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.AUDACITY_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "ACE 2017 - Debug - build" , groups = { "Build" })
    public void ace2017DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.ACE_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Big Project 2017 - Debug - build" , groups = { "Build" })
    public void bigProjectDebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.BIGPROJECT_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Big Project 2017 - Release - build" , groups = { "Build" })
    public void bigProjectReleaseBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.BIGPROJECT_X32_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Chrome release - build" , groups = { "Build" })
    public void chromeReleaseBuild(){
        if (testName.equals("Minimal")){
            test.log(Status.SKIP, "Skipping Chrome test on Minimal logging");
            throw new SkipException("Skipped test");
        }
        winService.runCommandWaitForFinish(ProjectsCommands.CHROME_BATMAN.CHROME_RELEASE_CLEAN);
        int returnCode = winService.runCommandWaitForFinish(ProjectsCommands.CHROME_BATMAN.CHROME_RELEASE_BUILD);
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Qt release - build" , groups = { "Build" })
    public void qtReleaseBuild() {
        if (testName.equals("Minimal")){
            test.log(Status.SKIP, "Skipping QT test on Minimal logging");
            throw new SkipException("Skipped test");
        }
        winService.runCommandWaitForFinish(ProjectsCommands.QT_BATMAN.QT_CLEAN);
        winService.runCommandWaitForFinish(ProjectsCommands.QT_BATMAN.QT_BUILD);
        String result = "";
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"), "Build failed");
        } catch (IOException e) {
            test.log(Status.WARNING, e.getMessage());
        }
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '"), "No agents were assigned to the build");
    }

    @Test(enabled = false, testName = "Android CPP - Debug - build" , groups = { "Build" })
    public void androidCPPDebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.ANDROIDCPP_ARM64_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(enabled = false, testName = "Android CS - Debug - build" , groups = { "Build" })
    public void androidCSDebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.ANDROIDCS_ANYCPU_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(enabled = false, testName = "Core CLR - Debug - build" , groups = { "Build" }) //bug #10018
    public void coreCLRDebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.CORECLR_X64_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Check that \"Predicted\" execution is enable: VS2017 Release")
    public void checkForPredictedExecutionWithoutMSBuild() {
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.AUDACITY_X32_DEBUG, "%s"));
        Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.PREDICTED_DISABLED));
    }

    @Test(testName = "Check if IBMSBHLP.log created")
    public void checkIBMSBHLPlogCreation(){
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.AUDACITY_X32_DEBUG, "%s"));
        File ibmsbhlpLog = new File(IbLocations.LOGS_ROOT + "\\IBMSBHLP.log");
        Assert.assertFalse(ibmsbhlpLog.exists(), "IBMSBHLP.log file was created during the \"Predicted\" execution");
    }

    @Test(testName = "Check Basic Agent Assignment")
    public void checkBasicAgentAssignment(){
        boolean pass = true;
        winService.runCommandWaitForFinish(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.AUDACITY_X32_DEBUG
                + " /out=" + StaticDataProvider.Locations.OUTPUT_LOG_FILE + " /showagent /showcmd /showtime", ProjectsCommands.REBUILD));
        for (String machine : batmanMachineList) {
            if (!Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '" + machine)){
                test.log(Status.INFO, machine + " is not assigned to build");
                pass = false;
            }
        }
        Assert.assertTrue(pass, "Some machines from the Grid were not assigned to the build process");
    }

    @Test(testName = "Verify Build Groups")
    public void verifyBuildGroups(){
        boolean pass = true;
        winService.runCommandWaitForFinish(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.AUDACITY_X32_DEBUG
                + " /out=" + StaticDataProvider.Locations.OUTPUT_LOG_FILE + " /showagent /showcmd /showtime", ProjectsCommands.REBUILD));
        for (String machine : vmSimMachineList) {
            if (Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '" + machine)){
                test.log(Status.INFO, machine + " is assigned to build");
                pass = false;
            }
        }
        Assert.assertTrue(pass, "Some machines from VMSIM Grid were assigned to the Batman build process");
    }

}
