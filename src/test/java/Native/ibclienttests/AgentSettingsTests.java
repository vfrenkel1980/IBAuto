package Native.ibclienttests;

import com.aventstack.extentreports.Status;
import frameworkInfra.sikuli.sikulimapping.CoordMonitor.CoordMonitor;
import frameworkInfra.sikuli.sikulimapping.IBSettings.IBSettings;
import frameworkInfra.testbases.AgentSettingsTestBase;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.parsers.Parser;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import ibInfra.windowscl.WindowsService;
import org.apache.velocity.runtime.directive.Parse;
import org.sikuli.script.FindFailed;
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
import static frameworkInfra.utils.StaticDataProvider.ProjectsCommands.COORD_SETTINGS.COORD_SETTINGS_LOCATION;
import static frameworkInfra.utils.StaticDataProvider.WindowsMachines.AGENT_SETTINGS_HLPR_NAME;

/**
 * @brief<b> <a href="https://incredibuild.atlassian.net/wiki/spaces/ITK/pages/427327495/Agent+settings+-+Automation+coverage"><b>Agent settings Automation coverage</b></a> tests</b>
 * @details Run on AgentSettings (AgentSettings host)
 */
public class AgentSettingsTests extends AgentSettingsTestBase {
    /**
     * @test Avoid local execution turned ON, Standalone OFF
     * @pre{
     * - Set registry key Avoid Local ON
     * - Set registry key Standalone Mode OFF
     * }
     * @steps{
     * - Clean and build IB
     * - Set registry key Avoid Local OFF
     * - Verify result in output log file
     * }
     * @result{ - true/false
     * -true(File contain string Agent)/false(Failed to find Agent string)
     * }
     */
    @Test(testName = "Avoid local Execution turned ON, Standalone OFF")
    public void avoidLocalExecutionTurnedOnStandaloneOff() {
        setRegistry("1", "Builder", RegistryKeys.AVOID_LOCAL);
        setRegistry("0", "Builder", RegistryKeys.STANDALONE_MODE);
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.AGENT_SETTINGS.AUDACITY_X32_DEBUG, "%s"));
        setRegistry("0", "Builder", RegistryKeys.AVOID_LOCAL);
        Assert.assertTrue(ibService.verifyAvoidLocal(Locations.OUTPUT_LOG_FILE), "failed to verify avoid local in output log");
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.AGENT), "Failed to find Agent in output log");
    }

    /**
     * @test Avoid local execution turned OFF, Standalone ON
     * @pre{
     * - Set registry key Avoid Local OFF
     * - Set registry key Standalone Mode ON}
     * @steps{
     *  - Clean and build IB
     *  - Verify result in output log file}
     * @result{ - true/false
     * - true(Failed contain string Agent word)/false(Failed to find Agent string)
     * }
     */
    @Test(testName = "Avoid local Execution turned OFF, Standalone ON")
    public void avoidLocalExecutionTurnedOffStandaloneOn() {
        setRegistry("0", "Builder", RegistryKeys.AVOID_LOCAL);
        setRegistry("1", "Builder", RegistryKeys.STANDALONE_MODE);
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.AGENT_SETTINGS.AUDACITY_X32_DEBUG, "%s"));
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.LOCAL), "Failed to find Local in output log");
        Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.AGENT), "Fount Agent in output log, should'nt be!");
    }
    /**
     * @test Avoid local execution turned OFF, Standalone OFF
     * @pre{
     *  - Set registry key Avoid Local OFF
     *  - Set registry key Standalone Mode ON
     *  }
     * @steps{
     *  - Clean and build IB
     *  -Verify result in output log file}
     * @result{ true/false
     * - true(Failed contain string Agent word)/false(Failed to find Agent string)
     * }
     */
    @Test(testName = "Avoid local Execution turned OFF, Standalone OFF")
    public void avoidLocalExecutionTurnedOffStandaloneOff() {
        setRegistry("0", "Builder", RegistryKeys.AVOID_LOCAL);
        setRegistry("0", "Builder", RegistryKeys.STANDALONE_MODE);
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.AGENT_SETTINGS.AUDACITY_X32_DEBUG, "%s"));
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.LOCAL), "Failed to find Local in output log");
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.AGENT), "Failed to find Agent in output log");
    }

    /**
     * @test Verify extended logging level
     * @pre{
     * - Set registry key logging Level
     * }
     * @steps{
     * - Run RUNME file
     * - Find value of required key in packet log}
     * @result{ - Expected result 4}
     */
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

    /**
     * @test Verify Minimal logging level
     * @pre{
     * - Set registry key Logging Level
     * }
     * @steps{
     * - Run RUNME file
     * - Find value of required key in packet log}
     * @result{ - Expected result 0}
     */
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

    /**
     * @test Verify Task Termination On High CPU Consumption
     * @pre{ }
     * @steps{ }
     * @result{ }
     */
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
        int lastAgent = Parser.getLastLineIndexForString(Locations.OUTPUT_LOG_FILE, "Agent '" + AGENT_SETTINGS_HLPR_NAME);
        int firstLocal = Parser.getFirstLineForString(Locations.OUTPUT_LOG_FILE, "Local");
        for (int i = 0; i < 2; i++) {
            winService.runCommandDontWaitForTermination(Processes.PSEXEC + " -d -i 1 -u admin -p 4illumination \\\\" + WindowsMachines.AGENT_SETTINGS_HLPR_IP +
                    " cmd.exe /c \"TASKKILL /F /IM nothing.exe\"");
        }
        Assert.assertTrue(firstLocal > lastAgent, lastAgent + " appears after Local in output.log" + "local: " + firstLocal + " Agent: " + lastAgent);
    }

    /**
     * @test Pro License - Verify MultiBuild Does Work When Adding Registry
     * @pre{ }
     * @steps{
     *  - Load license
     *  - Set registry key
     *  - Run command from win service Rebuild}
     * @result{ - Maximum number of concurrent builds reached}
     */
    @Test(testName = "Pro License - Verify MultiBuild Does Not Work When Adding Registry")
    public void proLicenseVerifyMultiBuildDoesNotWorkWhenAddingRegistry() {
        ibService.loadIbLicense(IbLicenses.NO_ENT_LIC);
        setRegistry("2", "BuildService", RegistryKeys.MAX_CONCURRENT_BUILDS);
        winService.runCommandDontWaitForTermination(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.AGENT_SETTINGS.AUDACITY_X32_DEBUG, ProjectsCommands.REBUILD));
        SystemActions.sleep(10);
        winService.runCommandWaitForFinish(IbLocations.BUILD_CONSOLE + String.format(TestProjects.TEST_PROJ, ProjectsCommands.REBUILD) + " /out=" + Locations.OUTPUT_LOG_FILE);
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.MAX_ALLOWED_BUILDS), "Max allowed builds did not appear in the log");
    }

    /**
     * @test Pro License - Verify MultiBuild Missing from UI
     * @pre{ }
     * @steps{
     * - Load IB license(without Enterprise)
     * - Run buildsettings executable file
     * - Verify if Multiple Build Tab exist
     * - Kill buildsettings process
     * - Load Agent Settings IB license}
     * @result{ - MultiBuild tab should not be displayed with PRO license}
     */
    @Test(testName = "Pro License - Verify MultiBuild Missing from UI")
    public void proLicenseVerifyMultiBuildMissingFromUI() {
        ibService.loadIbLicense(IbLicenses.NO_ENT_LIC);
        winService.runCommandDontWaitForTermination(IbLocations.BUILDSETTINGS);
        boolean isPresent = client.verifyMultipleBuildsTab();
        SystemActions.killProcess(Processes.BUILDSETTINGS);
        ibService.loadIbLicense(IbLicenses.AGENT_SETTINGS_LIC);
        Assert.assertFalse(isPresent, "MultiBuild tab should not be displayed with PRO license");
    }

    /**
     * @test Ent License - Verify MultiBuild Exists In UI
     * @pre{ }
     * @steps{
     * - Load Agent Settings IB License
     * - Run buildsettings executable file
     * - Run buildsettings executable file
     * - Verify if Multiple Build Tab exist
     * - Kill buildsettings process}
     * @result{ - MultiBuild tab should be displayed with PRO license}
     */
    @Test(testName = "Ent License - Verify MultiBuild Exists In UI")
    public void entLicenseVerifyMultiBuildExistsInUI() {
        ibService.loadIbLicense(IbLicenses.AGENT_SETTINGS_LIC);
        winService.runCommandDontWaitForTermination(IbLocations.BUILDSETTINGS);
        winService.runCommandDontWaitForTermination(StaticDataProvider.IbLocations.BUILDSETTINGS);
        boolean isPresent = client.verifyMultipleBuildsTab();
        SystemActions.killProcess(Processes.BUILDSETTINGS);
        Assert.assertTrue(isPresent, "MultiBuild tab should be displayed with PRO license");
    }

    /**
     * @test Verify Build History by date
     * @pre{
     * - Get local date
     * - Set local date}
     * @steps{
     * - Run buildsettings executable file
     * - Restart IncrediBuild_Agent service
     * - Open history file
     * }
     * @result{ - Numbers of History files}
     */
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

    /**
     * @test Verify Build History by click
     * @steps{
     * - Run RUNME file
     * - Run buildsettings executable file
     * - Click on Clear History button
     * - Open history file}
     * @result{ - Numbers of history files}
     */
    @Test(testName = "Verify Build History By Click")
    public void verifyBuildHistoryByClick() {
        winService.runCommandWaitForFinish(String.format(ProjectsCommands.MISC_PROJECTS.TEST_SAMPLE, 8, "60000"));
        winService.runCommandDontWaitForTermination(Processes.AGENTSETTINGS);
        client.clickClearHistory();
        int numOfHistoryFiles = SystemActions.countAllFilesInDirectory(StaticDataProvider.IbLocations.IB_ROOT + "\\history");
        Assert.assertEquals(2, numOfHistoryFiles, "Files in history folder are not deleted");
    }

    /**
     * @test Verify CPU Utilization
     * @pre{ }
     * @steps{
     * - Run RUNME file
     * - Change in CPU Utilization tab number of cores to 1
     * - Run buildsettings executable file
     * - Set registry key force CPU count when Initiator with value 0
     * - Stop Agent Service
     * - Start Agent Service}
     * @result{ }
     */
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

    /**
     * @test Change default Start Page
     * @pre{ }
     * @steps{
     * - Run RUNME file
     * - Run BuildSettings.exe file
     * - Change Startup page to projects
     * - Run BuildMonitor.exe file
     *  - Verify projects page is open}
     * @result{ }
     */
    @Test(testName = "Change Default Start Page")
    public void changeDefaultStartPage() {
        winService.runCommandWaitForFinish(String.format(ProjectsCommands.MISC_PROJECTS.TEST_SAMPLE, 8, "60000"));
        winService.runCommandDontWaitForTermination(Processes.AGENTSETTINGS);
        client.changeStartupPageToProjects();
        winService.runCommandDontWaitForTermination(Processes.BUILDMONITOR);
        client.verifyProjectsPageIsOpen();
    }

    /**
     * @test Verify Output options
     * @pre{ }
     * @steps{
     * - Run file BuildSettings.exe
     * - Enable output options}
     * @result{ }
     */
    @Test(testName = "Verify Output Options")
    public void verifyOutputOptions() {
        winService.runCommandDontWaitForTermination(Processes.AGENTSETTINGS);
        client.enableOutputOptions();
        String output = winService.runCommandGetOutput(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_SUCCESS, "rebuild"));
        Assert.assertTrue(output.contains("Agent '"), "Could not find Agents in build output");
        //TODO: when showcmd bug is fixed, add the assertion (9897)
        //Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, ""));
    }

    /**
     * @test Stop Agent Service
     * @pre{ }
     * @steps{
     * - Run file BuildSettings.exe
     * - Stop Agent service}
     * @result{ - Agent service should be stopped}
     */
    @Test(testName = "Stop Agent Service", dependsOnMethods = {"verifyTaskTerminationOnHighCPUConsumption"})
    public void stopAgentService() {
        winService.runCommandDontWaitForTermination(Processes.AGENTSETTINGS);
        client.stopAgentService();
        Assert.assertFalse(winService.isServiceRunning(WindowsServices.AGENT_SERVICE), "Agent service is running, should be stopped.");
    }

    /**
     * @test Start Agent Service
     * @pre{ }
     * @steps{
     * - Run file BuildSettings.exe
     * - Start Agent service}
     * @result{ - Agent service should be running}
     */
    @Test(testName = "Start Agent Service", dependsOnMethods = {"stopAgentService"})
    public void startAgentService() {
        winService.runCommandDontWaitForTermination(Processes.AGENTSETTINGS);
        client.startAgentService();
        Assert.assertTrue(winService.isServiceRunning(WindowsServices.AGENT_SERVICE), "Agent service is not running, should be running.");
    }

    /**
     * @test Restart Agent Service
     * @pre{ }
     * @steps{
     * - Run file BuildSettings.exe
     * - Restart Agent service }
     * @result{ - Agent service should be running}
     */
    @Test(testName = "Restart Agent Service", dependsOnMethods = {"startAgentService"})
    public void restartAgentService() {
        winService.runCommandDontWaitForTermination(Processes.AGENTSETTINGS);
        client.restartAgentService();
        Assert.assertTrue(winService.isServiceRunning(WindowsServices.AGENT_SERVICE), "Agent service is not running, should be running.");
    }

    /**
     * @test Verify Core limit per build limitation
     * @pre{ }
     * @steps{
     * - Run file BuildSettings.exe
     * - Limiting the number of cores per build
     * - Perform IB clean and build
     *  - Run file BuildSettings.exe
     *  - Disabling number of cores per build limit}
     * @result{ }
     */

    @Test(testName = "Verify Core Limit Per Build Limitation")
    public void verifyCoreLimitPerBuildLimitation() {
        winService.runCommandDontWaitForTermination(Processes.AGENTSETTINGS);
        client.limitNumberOfCoresPerBuild();
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.AGENT_SETTINGS.AUDACITY_X32_DEBUG, "%s"));
        winService.runCommandDontWaitForTermination(Processes.AGENTSETTINGS);
        client.disableLimitOfCoresPerBuild();
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent 'Vm-agntset-hlp (Core #1)"), "Didn't find core 1 for helper");
        Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent 'Vm-agntset-hlp (Core #2)"), "Found core 2 for helper");
    }

    /**
     * @test Verify only fail locally OFF
     * @pre{
     * - Set registry key Avoid Local ON
     * - Set registry key Standalone Mode OFF
     * }
     * @steps{
     * - Run file BuildSettings.exe
     * - Disable FailOnlyLocally option
     * - Perform IB clean and build
     * - Set registry key Avoid Local OFF}
     * @result{ }
     */
    @Test(testName = "Verify OnlyFailLocally Off")
    public void verifyOnlyFailLocallyOff() {
        setRegistry("1", "Builder", RegistryKeys.AVOID_LOCAL);
        setRegistry("0", "Builder", RegistryKeys.STANDALONE_MODE);
        winService.runCommandDontWaitForTermination(Processes.AGENTSETTINGS);
        client.disableFailOnlyLocally();
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_FAIL, "%s"));
        setRegistry("0", "Builder", RegistryKeys.AVOID_LOCAL);
        Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Local"), "Build was failed on local but should't");
    }

    /**
     * @test Verify only fail locally ON
     * @pre{
     * - Set registry key Avoid Local ON
     * }
     * @steps{
     * - Run file BuildSettings.exe
     * - Enable FailOnlyLocally option
     * - Perform IB clean and build
     * - Set registry key Avoid Local OFF}
     * @result{ }
     */
    @Test(testName = "Verify OnlyFailLocally On", dependsOnMethods = {"verifyOnlyFailLocallyOff"})
    public void verifyOnlyFailLocallyOn() {
        setRegistry("1", "Builder", RegistryKeys.AVOID_LOCAL);
        winService.runCommandDontWaitForTermination(Processes.AGENTSETTINGS);
        client.enableFailOnlyLocally();
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_FAIL, "%s"));
        setRegistry("0", "Builder", RegistryKeys.AVOID_LOCAL);
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Local"), "Build was failed on remote but should't");
    }

    /**
     * @test Verify build with errors
     * @pre{
     *  - Set registry All
     * }
     * @steps{ - Perform IB clean and build}
     * @result{ - Build succeeded, expected 8}
     */
    @Test(testName = "Verify Build With Errors")
    public void verifyBuildWithErrors() {
        setRegistry("All", "Builder", "Flags");
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_FAIL, "%s"));
        Assert.assertEquals(Parser.countOccurencesInFile(Locations.OUTPUT_LOG_FILE, LogOutput.BUILD_SUCCEEDED), 8);
    }

    /**
     * @test Enable Scheduling and verify Tray icon
     * @pre{ }
     * @steps{
     * - Run file BuildSettings.exe
     * - Enable Scheduling and verify icon
     * - Run file BuildSettings.exe
     * - Disable Scheduling and verify icon
     * - Opening Coordinator Monitor from tray
     * - Click on Allow Enable Disable As Helper option}
     * @result{ }
     */
    @Test(testName = "Enable Scheduling And Verify Tray Icon")
    public void enableSchedulingAndVerifyTrayIconWithPermission() {
    //    client.openCoordMonitorFromTray();
       // coordinator.clickAllowEnableDisableAsHelper();
       // SystemActions.sleep(30);
       // SystemActions.killProcess(Processes.COORDMONITOR);
        winService.runCommandDontWaitForTermination(Processes.AGENTSETTINGS);
        client.enableSchedulingAndVerifyIcon();
        winService.runCommandDontWaitForTermination(Processes.AGENTSETTINGS);
        client.disableSchedulingAndVerifyIcon();
        SystemActions.sleep(30);
        client.openCoordMonitorFromTray();
        coordinator.clickAllowEnableDisableAsHelper();
        SystemActions.sleep(30);

    }

    /**
     * @test Enable Scheduling and verify Tray icon without permission
     * @pre{ }
     * @steps{
     * - Run file BuildSettings.exe
     * - Is Not Active Scheduling option
     * - Run file BuildSettings.exe
     * - Open Coordinator Monitor from Tray icon
     * - Click on Allow Enable Disable As Helper option
     * - Killing process CoordMonitor.exe}
     * @result{ }
     */
    @Test(testName = "Enable Scheduling And Verify Tray Icon Without Permission")
    public void enableSchedulingAndVerifyTrayIconWithoutPermission() {
        winService.runCommandDontWaitForTermination(Processes.AGENTSETTINGS);
       // client.verifyAllowEnableDisableAsHelperDisabledFromTray();
        SystemActions.sleep(30);
        client.isNotActiveScheduling();
        winService.runCommandDontWaitForTermination(Processes.AGENTSETTINGS);
        client.openCoordMonitorFromTray();
        coordinator.clickAllowEnableDisableAsHelper();
        SystemActions.sleep(15);
        SystemActions.killProcess(Processes.COORDMONITOR);
    }

    /**
     * @test Verify PDB file limit 1
     * @pre{
     * - Set registry key Standalone OFF
     * - Set registry key Max Concurrent PDBs ON
     * }
     * @steps{
     * - Perform IB clean and build
     * - Get helper cores
     * - Set registry key Max Concurrent PDBs 12}
     * @result{ }
     */
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

    /**
     * @test Verify PDB File Limit Unchecked
     * @pre{
     * - Set registry key Standalone OFF
     * - Set registry key Max Concurrent PDBs OFF
     * }
     * @steps{
     * - Perform IB clean and build
     * - Get helper cores
     * - Set registry key Max Concurrent PDBs 12}
     * @result{ }
     */
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

    /**
     * @test Verify CPU Utilization As Initiator and PDB limit
     * @pre{
     * - Set registry key Standalone OFF
     * - Set registry key Max Concurrent PDBs ON
     * - Set registry key force CPU count when Initiator ON
     * }
     * @steps{
     * - Perform IB clean and build
     * - Get helper cores
     * - Set registry key force CPU count when Initiator OFF}
     * @result{ }
     */
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

    /**
     * @test Verify when CPU Utilization As Helper checked
     * @pre{
     * - Set registry key Standalone OFF
     * - Set registry key force CPU count when Helper ON
     * }
     * @steps{
     * - Perform IB clean and build
     * - Get helper cores
     * - Set registry key force CPU count when Helper OFF}
     * @result{ }
     */
    @Test(testName = "Verify When CPU Utilization As Helper checked")
    public void verifyWhenCPUUtilizationAsHelperChecked() {
        setRegistry("0", "Builder", RegistryKeys.STANDALONE_MODE);
        setRegKeyWithServiceRestart("1",RegistryKeys.FORCE_CPU_HELPER);
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.AGENT_SETTINGS.LITTLE_PROJECT_X86_DEBUG, "%s"));
        int helperNumber = Parser.getHelperCores(Locations.OUTPUT_LOG_FILE).size();
        setRegKeyWithServiceRestart("0",RegistryKeys.FORCE_CPU_HELPER);
        Assert.assertTrue(helperNumber > 1, "CPU utilization should be > 1, but found " + helperNumber);
    }
//TODO
    /**
     * @test Verify IB installation folder not have a coordintorsettings
     * @pre{ }
     * @steps{
     * - Open Coordinator Setting as Administrator
     * - Verify Coordinator Monitor Opened}
     * @result{ - Coordinator Settings option is disabled}
     */
    @Test(testName= "Verify IB installation folder not have a coordintorsettings")
    public void installationFolderNotHaveCoordSettingsFile(){
        winService.runCommandDontWaitForTermination(Processes.PSEXEC + " -d -i 1 -u admin -p 4illumination \\\\" + WindowsMachines.AGENT_SETTINGS_HLPR_IP + SystemActions.doesFileExist(COORD_SETTINGS_LOCATION));

//        boolean isExist=SystemActions.doesFileExist(COORD_SETTINGS_LOCATION);
//        Assert.assertFalse(isExist, "IB installation folder have a coordintorsettings");
//        Assert.assertFalse(winService.isServiceRunning(Processes.COORDSETTINGS), "CoordSettings running, should not be running");
//        winService.waitForProcessToStart(Processes.COORDMONITOR);
//        SystemActions.sleep(30);
//        client.verifyCoordinatorMonitorOpened();
//        try {
//            screen.wait(CoordMonitor.ToolsMenu.similar((float)0.9), 15).click();
//            screen.wait(CoordMonitor.StopServiceMenu.similar((float) 0.95), 15);
//            boolean objectExists = false;
//            if (screen.exists(CoordMonitor.StopServiceMenu.similar((float) 0.95), 15) != null)
//                objectExists = true;
//            Assert.assertTrue(objectExists, "Could not find Stop Service");
//        }catch(FindFailed findFailed) {
//            test.log(Status.WARNING, "Stop coordinator service is not enabled, failed with error: " + findFailed.getMessage());
//            Assert.fail();
//        }
//        SystemActions.killProcess(Processes.COORDMONITOR);
    }

    /*------------------------------METHODS------------------------------*/

    /**
     *
     * @param required
     * @param folder
     * @param keyName
     */
    private void setRegistry(String required, String folder, String keyName) {
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\" + folder, keyName, required);
    }

    /**
     *
     * @param required
     * @param keyName
     */
    private void setRegKeyWithServiceRestart(String required,String keyName) {
        setRegistry(required, "Builder", keyName);
        ibService.agentServiceStop();
        ibService.agentServiceStart();
        SystemActions.sleep(5);
}
}// end of AgentSettingsTest class
