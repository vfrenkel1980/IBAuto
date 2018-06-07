package Native.windowstests;

import com.aventstack.extentreports.Status;
import frameworkInfra.testbases.BatmanBCTestBase;
import frameworkInfra.utils.Parser;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import org.apache.commons.lang3.StringUtils;
import org.apache.tools.ant.ProjectComponent;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static frameworkInfra.Listeners.SuiteListener.test;

public class GeneralWinTests extends BatmanBCTestBase{


    @Test(testName = "Verify MultiBuild Success")
    public void verifyMultiBuildSuccess() {
        int instanceCount;
        setRegistry("4", "BuildService", RegistryKeys.MIN_LOCAL_CORES);
        ibService.cleanAndBuildDontWaitTermination(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.AUDACITY_X32_DEBUG, "%s"));
        ibService.cleanAndBuildDontWaitTermination(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC14_BATMAN.BLENDER_X64_RELEASE, "%s"));
        SystemActions.sleep(5);
        instanceCount = winService.getNumberOfProcessInstances(Processes.BUILDSYSTEM);
        Assert.assertEquals(instanceCount, 2, "Number of running instances does not match");
        winService.waitForProcessToFinish(Processes.BUILDSYSTEM);
    }

    @Test(testName = "Verify MultiBuild Failure")
    public void verifyMultiBuildFailure() {
        int instanceCount;
        setRegistry("8", "BuildService", RegistryKeys.MIN_LOCAL_CORES);
        ibService.cleanAndBuildDontWaitTermination(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.AUDACITY_X32_DEBUG, "%s"));
        ibService.cleanAndBuildDontWaitTermination(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC14_BATMAN.BLENDER_X64_RELEASE, "%s"));
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
            winService.runCommandWaitForFinish(Processes.PSEXEC + " -d -i 1 -u administrator -p 4illumination \\\\"
                    + WindowsMachines.BABYLON + " cmd.exe /c \"net stop " + WindowsServices.COORD_SERVICE + "\"");
            winService.waitForProcessToFinish(Processes.BUILDSYSTEM);
            try {
                String result = ibService.findValueInPacketLog("ExitCode ");
                Assert.assertTrue(result.equals("0") || result.equals("2"), "Build failed with exit code " + result);
            } catch (IOException e) {
                e.getMessage();
            }
            int lastAgent = Parser.getLastLineForString(Locations.OUTPUT_LOG_FILE, "Agent '");
            int lastLocal = Parser.getLastLineForString(Locations.OUTPUT_LOG_FILE, "Local");

            Assert.assertTrue(lastAgent > lastLocal, "Last Local task was build after remote tasks. Last Agent: " + lastAgent + " Last Local: " + lastLocal);
        }catch (Exception e){
            e.getMessage();
        }
        finally {
            winService.runCommandWaitForFinish(Processes.PSEXEC + " -d -i 1 -u administrator -p 4illumination \\\\"
                    + StaticDataProvider.WindowsMachines.BABYLON + " cmd.exe /c \"net start " + WindowsServices.COORD_SERVICE + "\"");
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
        winService.runCommandDontWaitForTermination(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.AUDACITY_X32_DEBUG, ProjectsCommands.REBUILD));
        winService.runCommandDontWaitForTermination(Processes.PSEXEC + " \\\\" + WindowsMachines.SECOND_INITIATOR + " -u Administrator -p 4illumination -i 1 " +
                "\"C:\\Program Files (x86)\\IncrediBuild\\buildconsole\" C:\\QA\\Simulation\\VC11\\ACE_VC11\\ACE_vc2012.sln /rebuild /cfg=\"debug|win32\" /title=\"ACE 2012 - Debug\" " +
                "/out=\"C:\\QA\\simulation\\buildlog.txt\" /showagent /showcmd /showtime");

        winService.waitForProcessToFinishOnRemoteMachine(WindowsMachines.SECOND_INITIATOR, "Administrator" , "4illumination", "buildsystem");
        winService.runCommandWaitForFinish("xcopy \"r:\\QA\\Simulation\\buildLog.txt\" \"c:\\qa\\simulation\\second_initiator_output\"");
        boolean isPresent = Parser.doesFileContainString(StaticDataProvider.Locations.QA_ROOT + "\\second_initiator_output\\buildlog.txt", "Agent '");
        SystemActions.deleteFile(Locations.QA_ROOT + "\\second_initiator_output\\buildlog.txt");
        winService.waitForProcessToFinish(Processes.BUILDSYSTEM);
        Assert.assertTrue(isPresent, "No agent assigned to build");
    }



        /*------------------------------METHODS------------------------------*/

    private void setRegistry(String required, String folder, String keyName) {
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\" + folder, keyName, required);
    }
}
