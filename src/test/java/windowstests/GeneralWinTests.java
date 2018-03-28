package windowstests;

import frameworkInfra.testbases.WindowsTestBase;
import frameworkInfra.utils.Parser;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;

public class GeneralWinTests extends WindowsTestBase {


    @Test(testName = "Verify MultiBuild Success")
    public void verifyMultiBuildSuccess() {
        int instanceCount;
        setRegistry("4", "BuildService", RegistryKeys.MIN_LOCAL_CORES);
        ibService.cleanAndBuildDontWaitTermination(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.AUDACITY_X32_DEBUG, "%s"));
        ibService.cleanAndBuildDontWaitTermination(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC14_BATMAN.BLENDER_X64_RELEASE, "%s"));
        SystemActions.sleep(5);
        instanceCount = winService.getNumberOfProcessInstances(Processes.BUILDSYSTEM);
        Assert.assertEquals(instanceCount, 2, "Number of running instances does not match");
        winService.waitForProcessToFinish(Processes.BUILDSYSTEM);
    }

    @Test(testName = "Verify MultiBuild Failure")
    public void verifyMultiBuildFailure() {
        int instanceCount;
        setRegistry("8", "BuildService", RegistryKeys.MIN_LOCAL_CORES);
        ibService.cleanAndBuildDontWaitTermination(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.AUDACITY_X32_DEBUG, "%s"));
        ibService.cleanAndBuildDontWaitTermination(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC14_BATMAN.BLENDER_X64_RELEASE, "%s"));
        SystemActions.sleep(5);
        instanceCount = winService.getNumberOfProcessInstances(Processes.BUILDSYSTEM);
        Assert.assertEquals(instanceCount, 1, "Number of running instances does not match");
        winService.waitForProcessToFinish(Processes.BUILDSYSTEM);
        winService.waitForProcessToStart(Processes.BUILDSYSTEM);
        SystemActions.killProcess(Processes.BUILDSYSTEM);
        setRegistry("4", "BuildService", RegistryKeys.MIN_LOCAL_CORES);
    }

    @Test(testName = "Verify Backup Coordinator Functionality")
    public void verifyBackupCoordinatorFunctionality() {
        winService.runCommandDontWaitForTermination(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.AUDACITY_X32_DEBUG, ProjectsCommands.REBUILD));
        SystemActions.sleep(10);
        winService.runCommandWaitForFinish(Processes.PSEXEC + " -d -i 1 -u admin -p 4illumination \\\\"
                + WindowsMachines.BABYLON + " cmd.exe /c \"net stop " + WindowsServices.COORD_SERVICE + "\"");
        winService.waitForProcessToFinish(Processes.BUILDSYSTEM);
        try {
            String result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"));
        } catch (IOException e) {
            e.getMessage();
        }
        int lastAgent = Parser.getLastLineForString(Locations.OUTPUT_LOG_FILE, "Agent '");
        int lastLocal = Parser.getLastLineForString(Locations.OUTPUT_LOG_FILE, "Local");

        Assert.assertTrue(lastAgent > lastLocal, "Last Local task was build after remote tasks. Last Agent: " + lastAgent + " Last Local: " + lastLocal);
    }





        /*------------------------------METHODS------------------------------*/

    private void setRegistry(String required, String folder, String keyName) {
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\" + folder, keyName, required);
    }
}
