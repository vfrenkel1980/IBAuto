package windowstests;

import frameworkInfra.testbases.WindowsTestBase;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.SystemActions;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;

public class GeneralWinTests extends WindowsTestBase {


    @Test(testName = "Verify MultiBuild Success")
    public void verifyMultiBuildSuccess() {
        int instanceCount;
        setRegistry("4", "BuildService", StaticDataProvider.RegistryKeys.MIN_LOCAL_CORES);
        ibService.cleanAndBuildDontWaitTermination(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC15_BATMAN.AUDACITY_X32_DEBUG, "%s"));
        ibService.cleanAndBuildDontWaitTermination(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC14_BATMAN.BLENDER_X64_RELEASE, "%s"));
        SystemActions.sleep(5);
        instanceCount = winService.getNumberOfProcessInstances(StaticDataProvider.Processes.BUILDSYSTEM);
        Assert.assertEquals(instanceCount, 2, "Number of running instances does not match");
        winService.waitForProcessToFinish(StaticDataProvider.Processes.BUILDSYSTEM);
    }

    @Test(testName = "Verify MultiBuild Failure")
    public void verifyMultiBuildFailure() {
        int instanceCount;
        setRegistry("8", "BuildService", StaticDataProvider.RegistryKeys.MIN_LOCAL_CORES);
        ibService.cleanAndBuildDontWaitTermination(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC15_BATMAN.AUDACITY_X32_DEBUG, "%s"));
        ibService.cleanAndBuildDontWaitTermination(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC14_BATMAN.BLENDER_X64_RELEASE, "%s"));
        SystemActions.sleep(5);
        instanceCount = winService.getNumberOfProcessInstances(StaticDataProvider.Processes.BUILDSYSTEM);
        Assert.assertEquals(instanceCount, 1, "Number of running instances does not match");
        winService.waitForProcessToFinish(StaticDataProvider.Processes.BUILDSYSTEM);
        winService.waitForProcessToStart(StaticDataProvider.Processes.BUILDSYSTEM);
        SystemActions.killProcess(StaticDataProvider.Processes.BUILDSYSTEM);
        setRegistry("4", "BuildService", StaticDataProvider.RegistryKeys.MIN_LOCAL_CORES);
    }





        /*------------------------------METHODS------------------------------*/

    private void setRegistry(String required, String folder, String keyName) {
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\" + folder, keyName, required);
    }
}
