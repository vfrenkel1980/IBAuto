package ibclienttests;

import frameworkInfra.testbases.AgentSettingsTestBase;
import frameworkInfra.utils.Parser;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;

public class AgentSettingsTests extends AgentSettingsTestBase {

    @Test(testName = "Avoid local Execution turned ON, Standalone OFF")
    public void avoidLocalExecutionTurnedOnStandaloneOff() {
        setRegistry("1", StaticDataProvider.RegistryKeys.AVOID_LOCAL);
        setRegistry("0", StaticDataProvider.RegistryKeys.STANDALONE_MODE);
        ibService.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.ConsoleAppProj.CONSOLE_APP_SUCCESS, "%s"));
        Assert.assertFalse(Parser.doesFileContainString(StaticDataProvider.Locations.OUTPUT_LOG_FILE, StaticDataProvider.LogOutput.LOCAL));
        Assert.assertTrue(Parser.doesFileContainString(StaticDataProvider.Locations.OUTPUT_LOG_FILE, StaticDataProvider.LogOutput.AGENT));
    }

    @Test(testName = "Avoid local Execution turned OFF, Standalone ON")
    public void avoidLocalExecutionTurnedOffStandaloneOn() {
        setRegistry("0", StaticDataProvider.RegistryKeys.AVOID_LOCAL);
        setRegistry("1", StaticDataProvider.RegistryKeys.STANDALONE_MODE);
        ibService.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.ConsoleAppProj.CONSOLE_APP_SUCCESS, "%s"));
        Assert.assertTrue(Parser.doesFileContainString(StaticDataProvider.Locations.OUTPUT_LOG_FILE, StaticDataProvider.LogOutput.LOCAL));
        Assert.assertFalse(Parser.doesFileContainString(StaticDataProvider.Locations.OUTPUT_LOG_FILE, StaticDataProvider.LogOutput.AGENT));
    }

    @Test(testName = "Avoid local Execution turned OFF, Standalone OFF")
    public void avoidLocalExecutionTurnedOffStandaloneOff() {
        setRegistry("0", StaticDataProvider.RegistryKeys.AVOID_LOCAL);
        setRegistry("0", StaticDataProvider.RegistryKeys.STANDALONE_MODE);
        ibService.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.ConsoleAppProj.CONSOLE_APP_SUCCESS, "%s"));
        Assert.assertTrue(Parser.doesFileContainString(StaticDataProvider.Locations.OUTPUT_LOG_FILE, StaticDataProvider.LogOutput.LOCAL));
        Assert.assertTrue(Parser.doesFileContainString(StaticDataProvider.Locations.OUTPUT_LOG_FILE, StaticDataProvider.LogOutput.AGENT));
    }







        /*------------------------------METHODS------------------------------*/

    private void setRegistry(String required, String keyName){
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\builder", keyName, required);
    }
}
