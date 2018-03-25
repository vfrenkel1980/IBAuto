package ibclienttests;

import frameworkInfra.testbases.AgentSettingsTestBase;
import frameworkInfra.utils.Parser;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;

public class AgentSettingsTests extends AgentSettingsTestBase {

    @Test(testName = "Avoid local Execution turned ON, Standalone OFF")
    public void avoidLocalExecutionTurnedOnStandaloneOff() {
        setRegistry("1","Builder", RegistryKeys.AVOID_LOCAL);
        setRegistry("0","Builder", RegistryKeys.STANDALONE_MODE);
        ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_SUCCESS, "%s"));
        Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.LOCAL));
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.AGENT));
    }

    @Test(testName = "Avoid local Execution turned OFF, Standalone ON")
    public void avoidLocalExecutionTurnedOffStandaloneOn() {
        setRegistry("0","Builder", RegistryKeys.AVOID_LOCAL);
        setRegistry("1","Builder", RegistryKeys.STANDALONE_MODE);
        ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_SUCCESS, "%s"));
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.LOCAL));
        Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.AGENT));
    }

    @Test(testName = "Avoid local Execution turned OFF, Standalone OFF")
    public void avoidLocalExecutionTurnedOffStandaloneOff() {
        setRegistry("0", "Builder", RegistryKeys.AVOID_LOCAL);
        setRegistry("0", "Builder", RegistryKeys.STANDALONE_MODE);
        ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_SUCCESS, "%s"));
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.LOCAL));
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.AGENT));
    }

    @Test(testName = "Verify Extended logging level")
    public void verifyExtendedLoggingLevel() {
        String result;
        setRegistry("4", "Log" ,RegistryKeys.LOGGING_LEVEL);
        ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_SUCCESS, "%s"));
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
        ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_SUCCESS, "%s"));
        try {
            result = ibService.findValueInPacketLog("LoggingLevel");
            Assert.assertEquals(result, "0");
        } catch (IOException e) {
            e.getMessage();
        }
    }

    @Test(testName = "Verify MultiBuild Success")
    public void verifyMultiBuild() {
        int instanceCount;
        setRegistry("2", "BuildService" ,RegistryKeys.MIN_LOCAL_CORES);
        ibService.cleanAndBuildDontWaitTermination(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.AUDACITY_X32_DEBUG, "%s"));
        ibService.cleanAndBuildDontWaitTermination(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC14_BATMAN.BLENDER_X64_RELEASE, "%s"));
        instanceCount = winService.getNumberOfProcessInstances(Processes.BUILDSYSTEM);
        Assert.assertEquals(instanceCount, 2, "Number of running instances does not match");
        winService.waitForProcessToFinish(Processes.BUILDSYSTEM);
    }







        /*------------------------------METHODS------------------------------*/

    private void setRegistry(String required, String folder, String keyName){
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\" + folder, keyName, required);
    }
}
