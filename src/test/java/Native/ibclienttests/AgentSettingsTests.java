package Native.ibclienttests;

import com.aventstack.extentreports.Status;
import frameworkInfra.sikuli.sikulimapping.IBSettings.IBSettings;
import frameworkInfra.testbases.AgentSettingsTestBase;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.parsers.Parser;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import ibInfra.windowscl.WindowsService;
import org.apache.velocity.runtime.directive.Parse;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static frameworkInfra.Listeners.SuiteListener.test;

public class AgentSettingsTests extends AgentSettingsTestBase {

    @Test(testName = "Avoid local Execution turned ON, Standalone OFF")
    public void avoidLocalExecutionTurnedOnStandaloneOff() {
        setRegistry("1", "Builder", RegistryKeys.AVOID_LOCAL);
        setRegistry("0", "Builder", RegistryKeys.STANDALONE_MODE);
        winService.runCommandWaitForFinish(String.format(ProjectsCommands.MISC_PROJECTS.TEST_SAMPLE, 8, "60000"));
        setRegistry("0", "Builder", RegistryKeys.AVOID_LOCAL);
        Assert.assertTrue(ibService.verifyAvoidLocal(Locations.OUTPUT_LOG_FILE), "failed to verify avoid local in output log");
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.AGENT), "Failed to find Agent in output log");
    }

    @Test(testName = "Avoid local Execution turned OFF, Standalone ON")
    public void avoidLocalExecutionTurnedOffStandaloneOn() {
        setRegistry("0", "Builder", RegistryKeys.AVOID_LOCAL);
        setRegistry("1", "Builder", RegistryKeys.STANDALONE_MODE);
        winService.runCommandWaitForFinish(String.format(ProjectsCommands.MISC_PROJECTS.TEST_SAMPLE, 8, "60000"));
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.LOCAL), "Failed to find Local in output log");
        Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.AGENT), "Fount Agent in output log, should'nt be!");
    }

    @Test(testName = "Avoid local Execution turned OFF, Standalone OFF")
    public void avoidLocalExecutionTurnedOffStandaloneOff() {
        setRegistry("0", "Builder", RegistryKeys.AVOID_LOCAL);
        setRegistry("0", "Builder", RegistryKeys.STANDALONE_MODE);
        winService.runCommandWaitForFinish(String.format(ProjectsCommands.MISC_PROJECTS.TEST_SAMPLE, 8, "60000"));
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.LOCAL), "Failed to find Local in output log");
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.AGENT), "Failed to find Agent in output log");
    }

    @Test(testName = "Verify Extended logging level")
    public void verifyExtendedLoggingLevel() {
        String result;
        setRegistry("4", "Log", RegistryKeys.LOGGING_LEVEL);
        winService.runCommandWaitForFinish(String.format(ProjectsCommands.MISC_PROJECTS.TEST_SAMPLE, 8, "60000"));
        try {
            result = ibService.findValueInPacketLog("LoggingLevel");
            Assert.assertEquals(result, "4");
        } catch (IOException e) {
            e.getMessage();
        }
    }

    @Test(testName = "Verify Minimal logging level")
    public void verifyMinimalLoggingLevel() {
        String result;
        setRegistry("0", "Log", RegistryKeys.LOGGING_LEVEL);
        winService.runCommandWaitForFinish(String.format(ProjectsCommands.MISC_PROJECTS.TEST_SAMPLE, 8, "60000"));
        try {
            result = ibService.findValueInPacketLog("LoggingLevel");
            Assert.assertEquals(result, "0");
        } catch (IOException e) {
            e.getMessage();
        }
    }

    @Test(testName = "Verify Task Termination On High CPU Consumption")
    public void verifyTaskTerminationOnHighCPUConsumption() {
        winService.runCommandWaitForFinish(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.AGENT_SETTINGS.AUDACITY_X32_DEBUG, ProjectsCommands.CLEAN));
        winService.runCommandDontWaitForTermination(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.AGENT_SETTINGS.AUDACITY_X32_DEBUG + " /out=" + Locations.OUTPUT_LOG_FILE + " /showagent /showcmd /showtime", ProjectsCommands.BUILD));
        SystemActions.sleep(7);
        for (int i = 0; i < 2; i++) {
            winService.runCommandDontWaitForTermination(Processes.PSEXEC + " -d -i 1 -u admin -p 4illumination \\\\" + WindowsMachines.AGENT_SETTINGS_HLPR_IP +
                    " cmd.exe /c " + Processes.NOTHING);
        }
        winService.waitForProcessToFinish(Processes.BUILDSYSTEM);
        int lastAgent = Parser.getLastLineIndexForString(Locations.OUTPUT_LOG_FILE, "Agent '" + WindowsMachines.AGENT_SETTINGS_HLPR_NAME);
        int firstLocal = Parser.getFirstLineForString(Locations.OUTPUT_LOG_FILE, "Local");
        for (int i = 0; i < 2; i++) {
            winService.runCommandDontWaitForTermination(Processes.PSEXEC + " -d -i 1 -u admin -p 4illumination \\\\" + WindowsMachines.AGENT_SETTINGS_HLPR_IP +
                    " cmd.exe /c \"TASKKILL /F /IM nothing.exe\"");
        }
        Assert.assertTrue(firstLocal > lastAgent, lastAgent + " appears after Local in output.log" + "local: " + firstLocal + " Agent: " + lastAgent);
    }

    @Test(testName = "Pro License - Verify MultiBuild Does Not Work When Adding Registry")
    public void proLicenseVerifyMultiBuildDoesNotWorkWhenAddingRegistry() {
        ibService.loadIbLicense(IbLicenses.NO_ENT_LIC);
        setRegistry("2", "BuildService", RegistryKeys.MAX_CONCURRENT_BUILDS);
        winService.runCommandDontWaitForTermination(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.AGENT_SETTINGS.AUDACITY_X32_DEBUG, ProjectsCommands.REBUILD));
        SystemActions.sleep(10);
        winService.runCommandWaitForFinish(IbLocations.BUILD_CONSOLE + String.format(TestProjects.TEST_PROJ, ProjectsCommands.REBUILD) + " /out=" + Locations.OUTPUT_LOG_FILE);
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.MAX_ALLOWED_BUILDS), "Max allowed builds did not appear in the log");
    }

    @Test(testName = "Pro License - Verify MultiBuild Missing from UI")
    public void proLicenseVerifyMultiBuildMissingFromUI() {
        ibService.loadIbLicense(IbLicenses.NO_ENT_LIC);
        winService.runCommandDontWaitForTermination(IbLocations.BUILDSETTINGS);
        boolean isPresent = client.verifyMultipleBuildsTab();
        SystemActions.killProcess(Processes.BUILDSETTINGS);
        ibService.loadIbLicense(IbLicenses.AGENT_SETTINGS_LIC);
        Assert.assertFalse(isPresent, "MultiBuild tab should not be displayed with PRO license");
    }

    @Test(testName = "Ent License - Verify MultiBuild Exists In UI")
    public void entLicenseVerifyMultiBuildExistsInUI() {
        ibService.loadIbLicense(IbLicenses.AGENT_SETTINGS_LIC);
        winService.runCommandDontWaitForTermination(IbLocations.BUILDSETTINGS);
        winService.runCommandDontWaitForTermination(StaticDataProvider.IbLocations.BUILDSETTINGS);
        boolean isPresent = client.verifyMultipleBuildsTab();
        SystemActions.killProcess(Processes.BUILDSETTINGS);
        Assert.assertTrue(isPresent, "MultiBuild tab should be displayed with PRO license");
    }

    @Test(testName = "Verify Build History By Date")
    public void verifyBuildHistoryByDate() {
        String currentDate = SystemActions.getLocalDateAsString();
        SystemActions.setLocalDateFromString("11-11-21");
        winService.runCommandDontWaitForTermination(Processes.AGENTSETTINGS);
        winService.restartService(WindowsServices.AGENT_SERVICE);
        int numOfHistoryFiles = SystemActions.countAllFilesInDirectory(StaticDataProvider.IbLocations.IB_ROOT + "\\history");
        SystemActions.setLocalDateFromString(currentDate);
        Assert.assertEquals(2, numOfHistoryFiles, "Files in history folder are not deleted");
    }

    @Test(testName = "Verify Build History By Click")
    public void verifyBuildHistoryByClick() {
        winService.runCommandWaitForFinish(String.format(ProjectsCommands.MISC_PROJECTS.TEST_SAMPLE, 8, "60000"));
        winService.runCommandDontWaitForTermination(Processes.AGENTSETTINGS);
        client.clickClearHistory();
        int numOfHistoryFiles = SystemActions.countAllFilesInDirectory(StaticDataProvider.IbLocations.IB_ROOT + "\\history");
        Assert.assertEquals(2, numOfHistoryFiles, "Files in history folder are not deleted");
    }

    @Test(testName = "Verify CPU Utilization")
    public void verifyCPUUtilization() {
        winService.runCommandDontWaitForTermination(StaticDataProvider.Processes.AGENTSETTINGS);
        client.changeCpuUtilCores();
        winService.runCommandWaitForFinish(String.format(ProjectsCommands.MISC_PROJECTS.TEST_SAMPLE, 8, "60000"));
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\Builder", RegistryKeys.FORCE_CPU_INITIATOR, "0");
        ibService.agentServiceStop();
        ibService.agentServiceStart();
        Assert.assertFalse(Parser.doesFileContainString(StaticDataProvider.Locations.OUTPUT_LOG_FILE, "CPU 2"));
    }

    @Test(testName = "Change Default Start Page")
    public void changeDefaultStartPage() {
        winService.runCommandWaitForFinish(String.format(ProjectsCommands.MISC_PROJECTS.TEST_SAMPLE, 8, "60000"));
        winService.runCommandDontWaitForTermination(Processes.AGENTSETTINGS);
        client.changeStartupPageToProjects();
        winService.runCommandDontWaitForTermination(Processes.BUILDMONITOR);
        client.verifyProjectsPageIsOpen();
    }

    @Test(testName = "Verify Output Options")
    public void verifyOutputOptions() {
        winService.runCommandDontWaitForTermination(Processes.AGENTSETTINGS);
        client.enableOutputOptions();
        String output = winService.runCommandGetOutput(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_SUCCESS, "rebuild"));
        Assert.assertTrue(output.contains("Agent '"), "Could not find Agents in build output");
        //TODO: when showcmd bug is fixed, add the assertion (9897)
        //Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, ""));
    }

    @Test(testName = "Stop Agent Service", dependsOnMethods = {"verifyTaskTerminationOnHighCPUConsumption"})
    public void stopAgentService() {
        winService.runCommandDontWaitForTermination(Processes.AGENTSETTINGS);
        client.stopAgentService();
        Assert.assertFalse(winService.isServiceRunning(WindowsServices.AGENT_SERVICE), "Agent service is running, should be stopped.");
    }

    @Test(testName = "Start Agent Service", dependsOnMethods = {"stopAgentService"})
    public void startAgentService() {
        winService.runCommandDontWaitForTermination(Processes.AGENTSETTINGS);
        client.startAgentService();
        Assert.assertTrue(winService.isServiceRunning(WindowsServices.AGENT_SERVICE), "Agent service is not running, should be running.");
    }

    @Test(testName = "Restart Agent Service", dependsOnMethods = {"startAgentService"})
    public void restartAgentService() {
        winService.runCommandDontWaitForTermination(Processes.AGENTSETTINGS);
        client.restartAgentService();
        Assert.assertTrue(winService.isServiceRunning(WindowsServices.AGENT_SERVICE), "Agent service is not running, should be running.");
    }

    @Test(testName = "Verify Core Limit Per Build Limitation")
    public void verifyCoreLimitPerBuildLimitation() {
        winService.runCommandDontWaitForTermination(Processes.AGENTSETTINGS);
        client.limitNumberOfCoresPerBuild();
        winService.runCommandWaitForFinish(String.format(ProjectsCommands.MISC_PROJECTS.TEST_SAMPLE, 8, "60000"));
        winService.runCommandDontWaitForTermination(Processes.AGENTSETTINGS);
        client.disableLimitOfCoresPerBuild();
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent 'Vm-agntset-hlp (Core #1)"), "Didn't find core 1 for helper");
        Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent 'Vm-agntset-hlp (Core #2)"), "Found core 2 for helper");
    }

    @Test(testName = "Verify OnlyFailLocally Off")
    public void verifyOnlyFailLocallyOff() {
        setRegistry("1", "Builder", RegistryKeys.AVOID_LOCAL);
        setRegistry("0", "Builder", RegistryKeys.STANDALONE_MODE);
        winService.runCommandDontWaitForTermination(Processes.AGENTSETTINGS);
        client.disableFailOnlyLocally();
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_FAIL, "%s"));
        Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Local"), "Build was failed on local but should't");
    }

    @Test(testName = "Verify OnlyFailLocally On", dependsOnMethods = {"verifyOnlyFailLocallyOff"})
    public void verifyOnlyFailLocallyOn() {
        winService.runCommandDontWaitForTermination(Processes.AGENTSETTINGS);
        client.enableFailOnlyLocally();
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_FAIL, "%s"));
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Local"), "Build was failed on remote but should't");
    }

    @Test(testName = "Verify Build With Errors")
    public void verifyBuildWithErrors() {
        setRegistry("All", "Builder", "Flags");
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_FAIL, "%s"));
        Assert.assertEquals(Parser.countOccurencesInFile(Locations.OUTPUT_LOG_FILE, LogOutput.BUILD_SUCCEEDED), 8);
    }

    @Test(testName = "Enable Scheduling And Verify Tray Icon")
    public void enableSchedulingAndVerifyTrayIcon() {
        winService.runCommandDontWaitForTermination(Processes.AGENTSETTINGS);
        client.enableSchedulingAndVerifyIcon();
        winService.runCommandDontWaitForTermination(Processes.AGENTSETTINGS);
        client.disableSchedulingAndVerifyIcon();
    }

    @Test(testName = "Verify PDB File Limit 1")
    public void verifyPDBFileLimit1() {
        setRegistry("0", "Builder", RegistryKeys.STANDALONE_MODE);
        setRegistry("1", "Builder", RegistryKeys.MAX_CONCURRENT_PDBS);
        SystemActions.sleep(5);
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.AGENT_SETTINGS.LITTLE_PROJECT_X86_DEBUG, "%s"));
        int helperNumber = Parser.getHelperCores(Locations.OUTPUT_LOG_FILE).size();
        setRegistry("12", "Builder", RegistryKeys.MAX_CONCURRENT_PDBS);
        Assert.assertTrue(helperNumber == 1, "PDB File Limit should be 1, but found " + helperNumber);
    }

    @Test(testName = "Verify PDB File Limit Unchecked")
    public void verifyPDBFileLimitUnchecked() {
        setRegistry("0", "Builder", RegistryKeys.STANDALONE_MODE);
        setRegistry("0", "Builder", RegistryKeys.MAX_CONCURRENT_PDBS);
        SystemActions.sleep(5);
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.AGENT_SETTINGS.LITTLE_PROJECT_X86_DEBUG, "%s"));
        int helperNumber = Parser.getHelperCores(Locations.OUTPUT_LOG_FILE).size();
        setRegistry("12", "Builder", RegistryKeys.MAX_CONCURRENT_PDBS);
        Assert.assertTrue(helperNumber > 1, "PDB File Limit should be >=2, but found " + helperNumber);
    }

    @Test(testName = "Verify CPU Utilization As Initiator and PDB limit")
    public void verifyCPUUtilizationAsInitiatorAndPDBLimit() {
        setRegistry("0", "Builder", RegistryKeys.STANDALONE_MODE);
        setRegistry("1", "Builder", RegistryKeys.MAX_CONCURRENT_PDBS);
        setRegKeyWithServiceRestart("1",RegistryKeys.FORCE_CPU_INITIATOR);
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.AGENT_SETTINGS.LITTLE_PROJECT_X86_DEBUG, "%s"));
        int helperNumber = Parser.getHelperCores(Locations.OUTPUT_LOG_FILE).size();
        setRegistry("12", "Builder", RegistryKeys.MAX_CONCURRENT_PDBS);
        setRegKeyWithServiceRestart("0",RegistryKeys.FORCE_CPU_INITIATOR);
        Assert.assertTrue(helperNumber == 1, "CPU utilization should be 1, but found " + helperNumber);
    }

    @Test(testName = "Verify When CPU Utilization As Helper checked")
    public void verifyWhenCPUUtilizationAsHelperChecked() {
        setRegistry("0", "Builder", RegistryKeys.STANDALONE_MODE);
        setRegKeyWithServiceRestart("1",RegistryKeys.FORCE_CPU_HELPER);
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.AGENT_SETTINGS.LITTLE_PROJECT_X86_DEBUG, "%s"));
        int helperNumber = Parser.getHelperCores(Locations.OUTPUT_LOG_FILE).size();
        setRegKeyWithServiceRestart("0",RegistryKeys.FORCE_CPU_HELPER);
        Assert.assertTrue(helperNumber > 1, "CPU utilization should be > 1, but found " + helperNumber);
    }



    /*------------------------------METHODS------------------------------*/

    private void setRegistry(String required, String folder, String keyName) {
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\" + folder, keyName, required);
    }

    private void setRegKeyWithServiceRestart(String required,String keyName) {
        setRegistry(required, "Builder", keyName);
        ibService.agentServiceStop();
        ibService.agentServiceStart();
        SystemActions.sleep(5);
}
}
