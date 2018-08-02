package Native.ibclienttests;

import frameworkInfra.sikuli.sikulimapping.IBSettings.IBSettings;
import frameworkInfra.testbases.AgentSettingsTestBase;
import frameworkInfra.utils.parsers.Parser;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;

public class AgentSettingsTests extends AgentSettingsTestBase {

    @Test(testName = "Avoid local Execution turned ON, Standalone OFF")
    public void avoidLocalExecutionTurnedOnStandaloneOff() {
        setRegistry("1","Builder", RegistryKeys.AVOID_LOCAL);
        setRegistry("0","Builder", RegistryKeys.STANDALONE_MODE);
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.AGENT_SETTINGS.AUDACITY_X32_DEBUG, "%s"));
        Assert.assertFalse(ibService.verifyAvoidLocal(Locations.OUTPUT_LOG_FILE));
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.AGENT));
    }

    @Test(testName = "Avoid local Execution turned OFF, Standalone ON")
    public void avoidLocalExecutionTurnedOffStandaloneOn() {
        setRegistry("0","Builder", RegistryKeys.AVOID_LOCAL);
        setRegistry("1","Builder", RegistryKeys.STANDALONE_MODE);
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.AGENT_SETTINGS.AUDACITY_X32_DEBUG, "%s"));
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.LOCAL));
        Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.AGENT));
    }

    @Test(testName = "Avoid local Execution turned OFF, Standalone OFF")
    public void avoidLocalExecutionTurnedOffStandaloneOff() {
        setRegistry("0", "Builder", RegistryKeys.AVOID_LOCAL);
        setRegistry("0", "Builder", RegistryKeys.STANDALONE_MODE);
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.AGENT_SETTINGS.AUDACITY_X32_DEBUG, "%s"));
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.LOCAL));
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.AGENT));
    }

    @Test(testName = "Verify Extended logging level")
    public void verifyExtendedLoggingLevel() {
        String result;
        setRegistry("4", "Log" ,RegistryKeys.LOGGING_LEVEL);
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.AGENT_SETTINGS.AUDACITY_X32_DEBUG, "%s"));
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
        setRegistry("0", "Log" ,RegistryKeys.LOGGING_LEVEL);
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.AGENT_SETTINGS.AUDACITY_X32_DEBUG, "%s"));
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
        winService.runCommandDontWaitForTermination(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.AGENT_SETTINGS.AUDACITY_X32_DEBUG + " /out=" + Locations.OUTPUT_LOG_FILE + " /showagent /showcmd /showtime", ProjectsCommands.BUILD) );
        SystemActions.sleep(7);
        for (int i = 0 ; i < 2 ; i++) {
            winService.runCommandDontWaitForTermination(Processes.PSEXEC + " -d -i 1 -u admin -p 4illumination \\\\" + WindowsMachines.AGENT_SETTINGS_HLPR_IP +
                    " cmd.exe /c " + Processes.NOTHING);
        }
        winService.waitForProcessToFinish(Processes.BUILDSYSTEM);
        int lastAgent = Parser.getLastLineForString(Locations.OUTPUT_LOG_FILE, "Agent '" + WindowsMachines.AGENT_SETTINGS_HLPR_NAME);
        int firstLocal = Parser.getFirstLineForString(Locations.OUTPUT_LOG_FILE, "Local");
        for (int i = 0 ; i < 2 ; i++) {
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
        boolean objectMissing = false;
        ibService.loadIbLicense(IbLicenses.NO_ENT_LIC);
        winService.runCommandDontWaitForTermination(IbLocations.BUILDSETTINGS);
        if (screen.exists(IBSettings.MultiBuildTab, 15) == null)
            objectMissing = true;
        SystemActions.killProcess(Processes.BUILDSETTINGS);
        ibService.loadIbLicense(IbLicenses.AGENT_SETTINGS_LIC);
        Assert.assertTrue(objectMissing, "MultiBuild tab should not be displayed with PRO license");
    }

    @Test(testName = "Ent License - Verify MultiBuild Exists In UI")
    public void entLicenseVerifyMultiBuildExistsInUI() {
        boolean objectExists = false;
        ibService.loadIbLicense(IbLicenses.AGENT_SETTINGS_LIC);
        winService.runCommandDontWaitForTermination(IbLocations.BUILDSETTINGS);
        if (screen.exists(IBSettings.MultiBuildTab, 15) != null)
            objectExists = true;
        SystemActions.killProcess(Processes.BUILDSETTINGS);
        Assert.assertTrue(objectExists, "MultiBuild tab should not be displayed with PRO license");
    }




        /*------------------------------METHODS------------------------------*/

    private void setRegistry(String required, String folder, String keyName){
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\" + folder, keyName, required);
    }
}
