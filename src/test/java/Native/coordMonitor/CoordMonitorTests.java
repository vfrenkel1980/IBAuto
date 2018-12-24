package Native.coordMonitor;

import frameworkInfra.testbases.CoordMonitorTestBase;
import frameworkInfra.utils.StaticDataProvider.*;
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
}
