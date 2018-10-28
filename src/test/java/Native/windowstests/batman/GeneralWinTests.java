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

import java.io.IOException;
import java.util.List;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static frameworkInfra.Listeners.SuiteListener.test;

public class GeneralWinTests extends BatmanBCTestBase{


    @Test(testName = "Verify MultiBuild Success")
    public void verifyMultiBuildSuccess() {
        int instanceCount;
        setRegistry("4", "BuildService", RegistryKeys.MIN_LOCAL_CORES);
        setRegistry("2", "BuildService", StaticDataProvider.RegistryKeys.MAX_CONCURRENT_BUILDS);
        winService.runCommandDontWaitForTermination(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.AUDACITY_X32_DEBUG, ProjectsCommands.REBUILD));
        SystemActions.sleep(1);
        winService.runCommandDontWaitForTermination(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC14_BATMAN.BLENDER_X64_RELEASE, ProjectsCommands.REBUILD));
        SystemActions.sleep(5);
        instanceCount = winService.getNumberOfProcessInstances(Processes.BUILDSYSTEM);
        Assert.assertEquals(instanceCount, 2, "Number of running instances does not match");
        winService.waitForProcessToFinish(Processes.BUILDSYSTEM);
    }

    @Test(testName = "Verify MultiBuild Failure")
    public void verifyMultiBuildFailure() {
        int instanceCount;
        setRegistry("8", "BuildService", RegistryKeys.MIN_LOCAL_CORES);
        winService.runCommandDontWaitForTermination(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.AUDACITY_X32_DEBUG, ProjectsCommands.REBUILD));
        SystemActions.sleep(1);
        winService.runCommandDontWaitForTermination(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC14_BATMAN.BLENDER_X64_RELEASE, ProjectsCommands.REBUILD));
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
        winService.runCommandDontWaitForTermination(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.AUDACITY_X32_DEBUG, ProjectsCommands.REBUILD));
        SystemActions.sleep(10);
        try {
            winService.runCommandWaitForFinish(Processes.PSEXEC + " -d -i 0 -u administrator -p 4illumination \\\\"
                    + WindowsMachines.BABYLON + " cmd.exe /c \"net stop " + WindowsServices.COORD_SERVICE + "\"");
            winService.waitForProcessToFinish(Processes.BUILDSYSTEM);
            int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.AUDACITY_X32_DEBUG, "%s"));
            Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
            Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '"), "No agents were assigned to the build");
        }catch (Exception e){
            e.getMessage();
        }
        finally {
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

    @Test(testName = "Verify Errors In Logs")
    public void verifyErrorsInLogs() {
        int errorCount = 0;
        List<String> files = SystemActions.getAllFilesInDirectory(IbLocations.IB_ROOT + "\\logs");
        for (String file : files) {
            for (String aERROR_LIST : LogOutput.ERROR_LIST) {
                if(Parser.doesFileContainString(IbLocations.IB_ROOT + "\\logs\\" + file, aERROR_LIST)) {
                    errorCount++;
                    test.log(Status.WARNING, aERROR_LIST + " Appears in " + file);
                }
            }
        }
        Assert.assertFalse(errorCount > 0);
    }

    @Test(testName = "Verify Multi Initiator Assignment")
    public void verifyMultiInitiatorAssignment() {
        try {
            winService.runCommandDontWaitForTermination(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.AUDACITY_X32_DEBUG, ProjectsCommands.REBUILD));
            winService.runCommandDontWaitForTermination(Processes.PSEXEC + " \\\\" + WindowsMachines.SECOND_INITIATOR + " -u Administrator -p 4illumination -i 1 " +
                    "\"C:\\Program Files (x86)\\IncrediBuild\\buildconsole\" C:\\QA\\Simulation\\VC11\\ACE_VC11\\ACE_vc2012.sln /rebuild /cfg=\"release|win32\" /title=\"ACE 2012 - Debug\" " +
                    "/out=\"C:\\QA\\simulation\\buildlog.txt\" /showagent /showcmd /showtime");
            SystemActions.sleep(1);
            winService.waitForProcessToFinishOnRemoteMachine(WindowsMachines.SECOND_INITIATOR, "Administrator", "4illumination", "buildconsole");
            winService.runCommandWaitForFinish("xcopy \"r:\\QA\\Simulation\\buildLog.txt\" " + Locations.SECOND_INITIATOR_LOG_PATH);
            Assert.assertTrue(SystemActions.doesFileExist(Locations.SECOND_INITIATOR_LOG_PATH + "buildLog.txt"));

            boolean isPresent = Parser.doesFileContainString(Locations.SECOND_INITIATOR_LOG_PATH + "buildlog.txt", LogOutput.AGENT);
            if (isPresent) {
                SystemActions.copyFile(Locations.SECOND_INITIATOR_LOG_PATH + "buildlog.txt", Locations.QA_ROOT + "\\logs\\for_investigation\\buildlog.txt");
            }
            SystemActions.deleteFile(Locations.SECOND_INITIATOR_LOG_PATH + "buildlog.txt");
            Assert.assertTrue(isPresent, "No agent assigned to build");
        }
        catch (Exception e){
            e.getMessage();
        }
        finally {
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

    @Test(testName = "Verify PDB Error In Log")
    public void verifyPDBErrorInLog() {
        Assert.assertEquals(LogOutput.PDB_ERROR_TESTS, "", "see list of builds that failed with PDB error: " + LogOutput.PDB_ERROR_TESTS);
    }

        /*------------------------------METHODS------------------------------*/

    private void setRegistry(String required, String folder, String keyName) {
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\" + folder, keyName, required);
    }
}
