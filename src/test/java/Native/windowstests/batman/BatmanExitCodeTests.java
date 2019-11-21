package Native.windowstests.batman;

import com.aventstack.extentreports.Status;
import frameworkInfra.testbases.BatmanBCTestBase;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static frameworkInfra.Listeners.SuiteListener.test;
import static frameworkInfra.utils.RegistryService.setRegistryKey;

public class BatmanExitCodeTests extends BatmanBCTestBase {

    @Test(testName = "Exit Code Base Test 0")
    public void exitCodeBaseTest0() {
        int returnCode = winService.runCommandWaitForFinish(ProjectsCommands.EXITCODEBASE.XG_CONSOLE_SAMPLE);
        Assert.assertTrue(returnCode == 0, "exitCodeBaseTest0 failed with return code " + returnCode);
    }

    @Test(testName = "Exit Code Base Test 1")
    public void exitCodeBaseTest1() {
        int returnCode = winService.runCommandWaitForFinish(ProjectsCommands.EXITCODEBASE.FAILEDPROJECT_X64_DEBUG);
        Assert.assertTrue(returnCode == 10001, "exitCodeBaseTest1 failed with return code " + returnCode);
    }

    @Test(testName = "Exit Code Base Test 2")
    public void exitCodeBaseTest2() {
        String result = "";
        winService.runCommandDontWaitForTermination(ProjectsCommands.EXITCODEBASE.PROJECTVC15_DEBUG_X64);
        SystemActions.sleep(5);
        try {
            SystemActions.killProcess(Processes.BUILD_CONSOLE);
            SystemActions.sleep(10);
            result = ibService.findValueInPacketLog("ExitCode ");
        } catch (IOException e) {
            test.log(Status.WARNING, e.getMessage());
        }
        Assert.assertTrue(result.equals("10002"), "exitCodeBaseTest2 failed with return code " + result);
    }

    @Test(testName = "Exit Code Base Test 3")
    public void exitCodeBaseTest3() {
        int returnCode = winService.runCommandWaitForFinish(ProjectsCommands.EXITCODEBASE.IB_CONSOLE_FAILEDBUILD);
        Assert.assertTrue(returnCode == 103, "exitCodeBaseTest3 failed with return code " + returnCode);
    }

    @Test(testName = "Exit Code Base Test 4")
    public void exitCodeBaseTest4() {
        setBuildServiceRegistry(RegistryKeys.MAX_CONCURRENT_BUILDS, "1");
        winService.runCommandDontWaitForTermination(ProjectsCommands.EXITCODEBASE.PROJECTVC15_RELEASE_X64);
        SystemActions.sleep(2);
        int returnCode = winService.runCommandWaitForFinish(ProjectsCommands.EXITCODEBASE.PROJECTVC10_DEBUG_WIN32);
        Assert.assertTrue(returnCode == -4, "exitCodeBaseTest4 failed with return code " + returnCode);
        winService.waitForProcessToFinish(Processes.BUILD_CONSOLE);
    }

    @Test(testName = "Exit Code Base Test 5")
    public void exitCodeBaseTest5() {
        setBuildServiceRegistry(RegistryKeys.MIN_LOCAL_CORES, "8");
        setBuildServiceRegistry(RegistryKeys.MAX_CONCURRENT_BUILDS, "2");
        winService.runCommandDontWaitForTermination(ProjectsCommands.EXITCODEBASE.PROJECTVC10_DEBUG_WIN32);
        SystemActions.sleep(2);
        int returnCode = winService.runCommandWaitForFinish(ProjectsCommands.EXITCODEBASE.PROJECTVC15_RELEASE_X64);
        Assert.assertTrue(returnCode == 10005, "exitCodeBaseTest5 failed with return code " + returnCode);
        winService.waitForProcessToFinish(Processes.BUILD_CONSOLE);
    }

    /*------------------------------METHODS------------------------------*/

    private void setBuildServiceRegistry(String keyName, String required) {
        setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\BuildService", keyName, required);
    }
}
