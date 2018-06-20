package Native.windowstests;

import com.aventstack.extentreports.Status;
import frameworkInfra.testbases.BatmanBCTestBase;
import frameworkInfra.utils.Parser;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.StaticDataProvider.Locations;
import frameworkInfra.utils.StaticDataProvider.ProjectsCommands;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

import static frameworkInfra.Listeners.SuiteListener.test;

public class BatmanMiscProjTests extends BatmanBCTestBase {

    @Test(testName = "Ruby2.4 SyncPrivateAssemblies")
    public void ruby24SyncPrivateAssemblies() {
        String result = "";
        winService.runCommandWaitForFinish(ProjectsCommands.MISC_PROJECTS.RUBY_SYNC_PRIVATE_ASSEMBLIES);
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"), "Build failed");
        } catch (IOException e) {
            test.log(Status.WARNING, e.getMessage());
        }
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '"), "No agents were assigned to the build");
    }

    @Test(testName = "IBCustomStep ON Success Test")
    public void customStepOnSuccessTest() {
        int returnCode = winService.runCommandWaitForFinish(ProjectsCommands.MISC_PROJECTS.PROJECTVC10_CUSTOMSTEP_SUCCESS);
        Assert.assertTrue(returnCode == 0, "customStepOnSuccessTest failed with return code " + returnCode);

    }

    @Test(testName = "IBCustomStep ON Failed Test")
    public void customStepOnFailTest() {
        int returnCode = winService.runCommandWaitForFinish(ProjectsCommands.MISC_PROJECTS.PROJECTVC15_CUSTOMSTEP_FAIL);
        Assert.assertTrue(returnCode == 1, "customStepOnFailTest failed with return code " + returnCode);
}


    //IBCustomStep OFF Success Test
    //IBCustomStep OFF Fail Test
}



