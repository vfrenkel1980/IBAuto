package Native.coordMonitor;

import frameworkInfra.testbases.CoordMonitorTestBase;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import frameworkInfra.utils.parsers.Parser;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CoordMonitorTests extends CoordMonitorTestBase {

    @Test(testName = "Disable Agent As Helper")
    public void disableAgentAsHelper() {
        coordinator.disableAsHelper();
        coordinator.verifyHelperIsDisabled();
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.AGENT_SETTINGS.AUDACITY_X32_DEBUG, "%s"));
        Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.AGENT), "Agent is listed in build log, should be disabled");
    }

    @Test(testName = "Enable Agent As Helper", dependsOnMethods = {"disableAgentAsHelper"})
    public void enableAgentAsHelper() {
        coordinator.enableAsHelper();
        coordinator.verifyHelperIsEnabled();
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.AGENT_SETTINGS.AUDACITY_X32_DEBUG, "%s"));
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.AGENT), "Agent is not listed in build log, should be enabled");
    }

    @Test(testName = "Unsubscribe Agent", dependsOnMethods = {"enableAgentAsHelper"})
    public void unsubscribeAgent() {
        coordinator.unsubscribeAgent();
        coordinator.verifyAgentIsUnsubscribed();
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.AGENT_SETTINGS.AUDACITY_X32_DEBUG, "%s"));
        Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.AGENT), "Agent is listed in build log, should be unsubscribed");
    }
    

    @Test(testName = "Subscribe Agent", dependsOnMethods = {"unsubscribeAgent"})
    public void subscribeAgent() {
        coordinator.subscribeAgent();
        coordinator.verifyAgentIsSubscribed();
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.AGENT_SETTINGS.AUDACITY_X32_DEBUG, "%s"));
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.AGENT), "Agent is not listed in build log, should be subscribed");
    }

    @Test(testName = "Stop Coordinator Service", dependsOnMethods = {"subscribeAgent"})
    public void stopCoordinatorService() {
        coordinator.stopCoordService();
        SystemActions.sleep(3);
        Assert.assertFalse(winService.isServiceRunning(WindowsServices.COORD_SERVICE), "Coordinator service is did not stop");
    }

    @Test(testName = "Start Coordinator Service", dependsOnMethods = {"stopCoordinatorService"})
    public void startCoordinatorService() {
        coordinator.startCoordService();
        SystemActions.sleep(3);
        Assert.assertTrue(winService.isServiceRunning(WindowsServices.COORD_SERVICE), "Coordinator service is did not start");
    }

    @Test(testName = "Deny Enable/Disable As Helper", dependsOnMethods = {"startCoordinatorService"})
    public void denyEnableDisableAsHelper() {
        coordinator.clickAllowEnableDisableAsHelper();
        restartTrayIcon();
        client.verifyAllowEnableDisableAsHelperDisabledFromTray();
    }

    @Test(testName = "Allow Enable/Disable As Helper", dependsOnMethods = {"denyEnableDisableAsHelper"})
    public void allowEnableDisableAsHelper() {
        coordinator.clickAllowEnableDisableAsHelper();
        restartTrayIcon();
        client.verifyAgentEnabledAsHelperFromTray();
    }

    @Test(testName = "Disable Remote Administration", dependsOnMethods = {"allowEnableDisableAsHelper"})
    public void disableRemoteAdministration() {
        coordinator.clickAllowRemoteAdministration();
        String out = winService.runCommandGetOutput(Processes.PSEXEC + " \\\\" + WindowsMachines.AGENT_SETTINGS_HLPR_NAME + " -u Admin -p 4illumination -i 0 xgCoordConsole /RESETALLFILECACHES");
        Assert.assertTrue(out.contains("error code 4"), "successfully ran /RESETALLFILECACHES - should FAIL");
    }

    @Test(testName = "Allow Remote Administration", dependsOnMethods = {"disableRemoteAdministration"})
    public void allowRemoteAdministration() {
        coordinator.clickAllowRemoteAdministration();
        String out = winService.runCommandGetOutput(Processes.PSEXEC + " \\\\" + WindowsMachines.AGENT_SETTINGS_HLPR_NAME + " -u Admin -p 4illumination -i 0 xgCoordConsole /RESETALLFILECACHES");
        Assert.assertTrue(out.contains("error code 0"), "failed to run /RESETALLFILECACHES - administrative rights granted?");
    }


/*---------------------------------METHODS----------------------------------------------*/
    public void restartTrayIcon(){
        SystemActions.killProcess(Processes.TRAY_ICON);
        SystemActions.sleep(8);
        SystemActions.startProcess(Processes.TRAY_ICON);
    }

}
