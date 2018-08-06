package Native.windowstests.batman;

import com.aventstack.extentreports.Status;
import frameworkInfra.testbases.BatmanBCTestBase;
import frameworkInfra.utils.parsers.Parser;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.StaticDataProvider.Locations;
import frameworkInfra.utils.StaticDataProvider.ProjectsCommands;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static frameworkInfra.Listeners.SuiteListener.test;
import static frameworkInfra.utils.RegistryService.addRegKeyValue;
import static frameworkInfra.utils.RegistryService.removeRegKeyValue;
import static frameworkInfra.utils.RegistryService.setRegistryKey;

public class BatmanMiscProjTests extends BatmanBCTestBase {

    @Test(testName = "Ruby2 4 SyncPrivateAssemblies")
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

    //Custom Step Support tests
    @Test(testName = "IBCustomStep ON Success Test")
    public void ibcustomStepOnSuccessTest() {
        setCustomStepSupportRegistry("1");
        int returnCode = winService.runCommandWaitForFinish(ProjectsCommands.MISC_PROJECTS.PROJECTVC10_CUSTOMSTEP_SUCCESS);
        Assert.assertTrue(returnCode == 0, "customStepOnSuccessTest failed with return code " + returnCode);

    }

    @Test(testName = "IBCustomStep ON Failed Test")
    public void ibcustomStepOnFailTest() {
        setCustomStepSupportRegistry("1");
        int returnCode = winService.runCommandWaitForFinish(ProjectsCommands.MISC_PROJECTS.PROJECTVC15_CUSTOMSTEP_FAIL);
        Assert.assertTrue(returnCode == 1, "customStepOnFailTest failed with return code " + returnCode);
    }

    @Test(testName = "IBCustomStep OFF Success Test")
    public void ibcustomStepOffSuccessTest() {
        setCustomStepSupportRegistry("0");
        addFlagsRegKeyValue("All");
        int returnCode = winService.runCommandWaitForFinish(ProjectsCommands.MISC_PROJECTS.PROJECTVC10_CUSTOMSTEP_SUCCESS);
        removeFlagsRegKeyValue("All");
        setCustomStepSupportRegistry("1");
        Assert.assertTrue(returnCode == 0, "customStepOffSuccessTest failed with return code " + returnCode);
    }

    @Test(testName = "IBCustomStep OFF Failed Test")
    public void ibcustomStepOffFailTest() {
        setCustomStepSupportRegistry("0");
        addFlagsRegKeyValue("All");
        int returnCode = winService.runCommandWaitForFinish(ProjectsCommands.MISC_PROJECTS.PROJECTVC15_CUSTOMSTEP_FAIL);
        removeFlagsRegKeyValue("All");
        setCustomStepSupportRegistry("1");
        Assert.assertTrue(returnCode == 1, "customStepOffFailTest failed with return code " + returnCode);
    }


    /*------------------------------METHODS------------------------------*/

    private void setCustomStepSupportRegistry(String required) {
        setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\Builder", RegistryKeys.CUSTOM_STEP_VS10_SUPPORT, required);
    }

    private void addFlagsRegKeyValue(String required) {
        addRegKeyValue(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\Builder", RegistryKeys.FLAGS, required);
    }

    private void removeFlagsRegKeyValue(String required) {
        removeRegKeyValue(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\Builder", RegistryKeys.FLAGS, required);
    }
}


