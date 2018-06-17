package Native.windowstests;

import com.aventstack.extentreports.Status;
import frameworkInfra.testbases.BatmanBCTestBase;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static frameworkInfra.Listeners.SuiteListener.test;

public class BatmanExitCodeTests extends BatmanBCTestBase {

    @Test(testName = "/exitcodebase (SUCCESS = 0) Test")
    public void exitCodeBaseTest0() {
        int returnCode = winService.runCommandWaitForFinish(String.format(ProjectsCommands.MISC_PROJECTS.XG_CONSOLE_SAMPLE + " /exitcodebase=10"));
        Assert.assertTrue(returnCode == 0, "exitCodeBaseTest0 failed with return code " + returnCode);
    }

    @Test(testName = "/exitcodebase (BUILD_ERROR = 1) Test")
    public void exitCodeBaseTest1() {
        int returnCode = winService.runCommandWaitForFinish(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.MISC_PROJECTS.FAILEDPROJECT_X64_DEBUG + " /rebuild /exitcodebase"));
        Assert.assertTrue(returnCode == 1, "exitCodeBaseTest1 failed with return code " + returnCode);
    }

    @Test(testName = "/exitcodebase (USER_CANCELED = 2) Test")
    public void exitCodeBaseTest2() {
        String result = "";
        winService.runCommandDontWaitForTermination(IbLocations.BUILD_CONSOLE + String.format(TestProjects.VC15PROJECT + " /rebuild /cfg=\"release|x64\" /exitcodebase"));
        SystemActions.sleep(2);
        try {
            SystemActions.killProcess(Processes.BUILD_CONSOLE);
            result = ibService.findValueInPacketLog("ExitCode ");
        } catch (IOException e) {
            test.log(Status.WARNING, e.getMessage());
        }
        Assert.assertTrue(result.equals("10002"), "exitCodeBaseTest2 failed with return code " + result);
    }

    @Test(testName = "/exitcodebase (SYSTEM_ERROR = 3) Test")
    public void exitCodeBaseTest3() {
        int returnCode = winService.runCommandWaitForFinish(String.format(ProjectsCommands.MISC_PROJECTS.IB_CONSOLE_FAILEDBUILD + " /exitcodebase=100"));
        Assert.assertTrue(returnCode == 103, "exitCodeBaseTest3 failed with return code " + returnCode);
    }

    @Test(testName = "/exitcodebase (MAX_BUILDS_REACHED = 4) Test")
    public void exitCodeBaseTest4() {
        setBuildServiceRegistry(RegistryKeys.MAX_CONCURRENT_BUILDS, "1");
        winService.runCommandDontWaitForTermination(IbLocations.BUILD_CONSOLE + String.format(TestProjects.VC10PROJECT + " /rebuild /cfg=\"debug|win32\" "));
        int returnCode = winService.runCommandWaitForFinish(String.format(IbLocations.BUILD_CONSOLE + String.format(TestProjects.VC8PROJECT + " /rebuild /cfg=\"debug|win32\" /exitcodebase=-8  /nowait")));
        Assert.assertTrue(returnCode == -4, "exitCodeBaseTest4 failed with return code " + returnCode);
    }

    @Test(testName = "/exitcodebase (NO_CORES_AVAILABLE = 5) Test")
    public void exitCodeBaseTest5() {
        setBuildServiceRegistry(RegistryKeys.MIN_LOCAL_CORES, "8");
        setBuildServiceRegistry(RegistryKeys.MAX_CONCURRENT_BUILDS, "2");
        winService.runCommandDontWaitForTermination(IbLocations.BUILD_CONSOLE + String.format(TestProjects.VC14PROJECT + " /rebuild /cfg=\"release|x64\" "));
        int returnCode = winService.runCommandWaitForFinish(String.format(IbLocations.BUILD_CONSOLE + String.format(TestProjects.VC15PROJECT + " /rebuild /cfg=\"debug|x64\" /exitcodebase=1000000000000  /nowait")));
        Assert.assertTrue(returnCode == 10005, "exitCodeBaseTest5 failed with return code " + returnCode);
    }

    /*------------------------------METHODS------------------------------*/

    private void setBuildServiceRegistry(String keyName, String required) {
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\BuildService", keyName, required);
    }
}
