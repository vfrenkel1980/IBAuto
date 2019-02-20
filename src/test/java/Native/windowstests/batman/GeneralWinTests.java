package Native.windowstests.batman;

import com.aventstack.extentreports.Status;
import frameworkInfra.testbases.BatmanBCTestBase;
import frameworkInfra.utils.parsers.Parser;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static frameworkInfra.Listeners.SuiteListener.test;

public class GeneralWinTests extends BatmanBCTestBase {

    @Test(testName = "Verify Backup Coordinator Functionality")
    public void verifyBackupCoordinatorFunctionality() {
        winService.runCommandDontWaitForTermination(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.AUDACITY_X32_DEBUG, ProjectsCommands.REBUILD));
        SystemActions.sleep(10);
        try {
            winService.runCommandWaitForFinish(Processes.PSEXEC + " -d -i 0 -u administrator -p 4illumination \\\\"
                    + WindowsMachines.BABYLON + " cmd.exe /c \"net stop " + WindowsServices.COORD_SERVICE + "\"");
            winService.waitForProcessToFinish(Processes.BUILDSYSTEM);
            int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.AUDACITY_X32_DEBUG, "%s"));
            Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
            Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '"), "No agents were assigned to the build");
        } catch (Exception e) {
            e.getMessage();
        } finally {
            winService.runCommandWaitForFinish(Processes.PSEXEC + " -d -i 0 -u administrator -p 4illumination \\\\"
                    + WindowsMachines.BABYLON + " cmd.exe /c \"net start " + WindowsServices.COORD_SERVICE + "\"");
        }
    }

    @Test(testName = "Verify Backup Coord Service Memory Usage")
    public void verifyBackupCoordServiceMemoryUsage() {
        String output = winService.runCommandGetOutput(String.format(WindowsCommands.GET_MEMORY_USAGE, MemoryThresholds._20K));
        Assert.assertFalse(StringUtils.containsIgnoreCase(output, "CoordService.exe"), "CoordService has exceeded the memory threshold");
    }

    @Test(testName = "Verify Agent Service Memory Usage")
    public void verifyAgentCoordServiceMemoryUsage() {
        String output = winService.runCommandGetOutput(String.format(WindowsCommands.GET_MEMORY_USAGE, MemoryThresholds._20K));
        Assert.assertFalse(StringUtils.containsIgnoreCase(output, "BuildService.exe"), "BuildService has exceeded the memory threshold");
    }

    @Test(testName = "Verify BuildSystem Memory Usage During Build")
    public void verifyBuildSystemMemoryUsageDuringBuild() {
        winService.runCommandDontWaitForTermination(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.AUDACITY_X32_DEBUG, ProjectsCommands.REBUILD));
        SystemActions.sleep(20);
        String output = winService.runCommandGetOutput(String.format(WindowsCommands.GET_MEMORY_USAGE, MemoryThresholds._200K));
        winService.waitForProcessToFinish(Processes.BUILD_CONSOLE);
        Assert.assertFalse(StringUtils.containsIgnoreCase(output, "BuildSystem.exe"), "BuildSystem  has exceeded the memory threshold using");
    }

    @Test(testName = "Verify Multi Initiator Assignment")
    public void verifyMultiInitiatorAssignment() {
        try {
            winService.runCommandDontWaitForTermination(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.AUDACITY_X32_DEBUG, ProjectsCommands.REBUILD));
            winService.runCommandDontWaitForTermination(Processes.PSEXEC + " \\\\" + WindowsMachines.SECOND_INITIATOR + " -u Administrator -p 4illumination -i 0 " +
                    String.format("\"C:\\Program Files\\Xoreax\\IncrediBuild\\buildconsole.exe\" " + ProjectsCommands.VC15_BATMAN.AUDACITY_SECOND_INITIATOR, ProjectsCommands.REBUILD));
            SystemActions.sleep(30);
            winService.waitForProcessToFinishOnRemoteMachine(WindowsMachines.SECOND_INITIATOR, "Administrator", "4illumination", "buildconsole");
            boolean isPresent = Parser.doesFileContainString(Locations.SECOND_INITIATOR_LOG_PATH + "buildlog.txt", LogOutput.AGENT);
            if (isPresent) {
                SystemActions.copyFile(Locations.SECOND_INITIATOR_LOG_PATH + "buildlog.txt", Locations.QA_ROOT + "\\logs\\for_investigation\\buildlog.txt");
            }
            SystemActions.deleteFile(Locations.SECOND_INITIATOR_LOG_PATH + "buildlog.txt");
            Assert.assertTrue(isPresent, "No agent assigned to build");
        } catch (Exception e) {
            e.getMessage();
        } finally {
            winService.waitForProcessToFinish(Processes.BUILD_CONSOLE);
        }
    }

    @Test(testName = "Verify Predicted Disabled Build")
    public void verifyPredictedDisabledBuild() {
        setRegistry("0", "Builder", RegistryKeys.PREDICTED);
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.AUDACITY_X32_DEBUG, "%s"));
        setRegistry("2", "Builder", RegistryKeys.PREDICTED);
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(enabled = false, testName = "Verify BuildMon - Agent Service stopped During Build")
    public void verifyBuildMonAgentServiceStoppedDuringBuild() {
        winService.runCommandDontWaitForTermination(ProjectsCommands.MISC_PROJECTS.XG_CONSOLE_SAMPLE_LONG + " /openmonitor");
        SystemActions.sleep(1);
        try {
            winService.runCommandWaitForFinish("net stop \"" + WindowsServices.AGENT_SERVICE + "\"");
            SystemActions.sleep(5);
            Assert.assertFalse(Parser.doesFileContainString(IbLocations.LOGS_ROOT + "\\BuildMonitor.log", LogOutput.BUILDSERVICE_STOPPED_FAIL));
            Assert.assertTrue(Parser.doesFileContainString(IbLocations.LOGS_ROOT + "\\BuildMonitor.log", LogOutput.BUILDSERVICE_STOPPED));
        } catch (Exception e) {
            test.log(Status.ERROR, "Test failed with the following error: " + e.getMessage());
        } finally {
            winService.runCommandWaitForFinish("net start \"" + WindowsServices.AGENT_SERVICE + "\"");
            SystemActions.killProcess(Processes.BUILDMONITOR);
        }
    }

    @Test(enabled = false, testName = "Verify BuildMon  - Agent Service stopped")
    public void verifyBuildMonAgentServiceStopped() {
        winService.runCommandWaitForFinish(ProjectsCommands.MISC_PROJECTS.XG_CONSOLE_SAMPLE_LONG + " /openmonitor");
        try {
            winService.runCommandWaitForFinish("net stop \"" + WindowsServices.AGENT_SERVICE + "\"");
            ibService.openBuildMonitor();
            Assert.assertFalse(Parser.doesFileContainString(IbLocations.LOGS_ROOT + "\\BuildMonitor.log", LogOutput.BUILDMONITOR_ACCESS_VIOLATION));
        } catch (Exception e) {
            test.log(Status.ERROR, "Test failed with the following error: " + e.getMessage());
        } finally {
            winService.runCommandWaitForFinish("net start \"" + WindowsServices.AGENT_SERVICE + "\"");
            SystemActions.killProcess(Processes.BUILDMONITOR);
        }
    }

    @Test(testName = "Verify MsBuild version is updated for VS install")
    public void verifyMsBuildversionIsUpdatedForVSInstall() {
        setRegistry("1", "Builder", RegistryKeys.AUTO_PREDICTED_UPDATE);
        String msBuildSupportedVersion = postgresJDBC.getLastValueFromTable("192.168.10.73", "postgres", "postgres123", "release_manager", " ms_build_support_version ", "windows_builds_ib_info ", "ms_build_support_version", "build_number");
        setRegistry("15.4.8.50001", "Builder", RegistryKeys.MSBUILD_SUPPORTED_VERSION_15);
        SystemActions.killProcess(StaticDataProvider.Processes.TRAY_ICON);
        SystemActions.sleep(2);
        SystemActions.startProcess(StaticDataProvider.Processes.TRAY_ICON);
        int timer = 0;
        String result = "";
        while (!msBuildSupportedVersion.equals(result) && timer <= 200) {
            result = getRegistry("Builder", RegistryKeys.MSBUILD_SUPPORTED_VERSION_15);
            int timeout = 10;
            SystemActions.sleep(timeout);
            timer += timeout;
        }
        Assert.assertTrue(msBuildSupportedVersion.equals(result), "The MSBuild version is not updated. Found value " + result);
    }

    @Test(testName = "Verify PDB Error In Log")
    public void verifyPDBErrorInLog() {
        Assert.assertEquals(LogOutput.PDB_ERROR_TESTS, "", "see list of builds that failed with PDB error: " + LogOutput.PDB_ERROR_TESTS);
    }

    @Test(testName = "Verify .proj File Support")
    public void verifyProjFileSupport(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.PROJ_WIN32_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '"), "No agents were assigned to the build");

    }

    @Test(testName = "Verify OnlyFailLocally Flag")
    public void verifyOnlyFailLocallyFlag(){
        winService.runCommandWaitForFinish(String.format(ProjectsCommands.EXITCODEBASE.FAILEDPROJECT_X64_DEBUG, ProjectsCommands.REBUILD) + " /OnlyFailLocally=on /showagent");
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Local"), "The build was executed on remote, should fail locally");
    }

    /*------------------------------METHODS------------------------------*/

    private void setRegistry(String required, String folder, String keyName) {
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\" + folder, keyName, required);
    }

    private String getRegistry(String folder, String keyName) {
        return RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\" + folder, keyName);
    }
}
