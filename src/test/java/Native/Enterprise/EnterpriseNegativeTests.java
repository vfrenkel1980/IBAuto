package Native.Enterprise;

import frameworkInfra.testbases.EnterpriseNegativeTestBase;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * @brief<b> <b>Enterprise features negative tests with Pro license loaded</b>
 * @details vm: Dashbord on Host-6
 */
public class EnterpriseNegativeTests extends EnterpriseNegativeTestBase {
    /**
     * @test Verify <a href="https://incredibuild.atlassian.net/wiki/spaces/IN/pages/131730/Unsubscribing+Agents+via+the+Command+LineVerify">Xgcoordconsole /Unsubscribe Feature</a>
     * @pre{
     * - Only core package is allocated to the Agent
     * }
     * @steps{
     * - Run the command: xgcoordconsole /unsubscribe=agent_machine_name
     * }
     * @result{
     * - The agent is not unsubscribed and the subscribed status is true in the coordmonitor;
     * - The error message is displayed in the command line output.
     * }
     */
    @Test(testName = "Verify Xgcoordconsole Unsubscribe Feature")
    public void verifyXgcoordconsoleUnsubscribeFeature() {
        winService.runCommandWaitForFinish(IbLocations.XGCOORDCONSOLE + "/DeallocateAll");
        winService.runCommandWaitForFinish(IbLocations.XGCOORDCONSOLE + "/allocatePackages=\"4Cores\"");
        String output = winService.runCommandGetOutput(IbLocations.XGCOORDCONSOLE + " /unsubscribe=" + WindowsMachines.DASHBORD_HELPER);
        boolean subscribeAgentStatus = true;
        try {
            subscribeAgentStatus = coordMonitor.getAgentSubscribeStatus(WindowsMachines.DASHBORD_HELPER);
            winService.runCommandWaitForFinish(IbLocations.XGCOORDCONSOLE + "/SubscribeAll");
            winService.runCommandWaitForFinish(IbLocations.XGCOORDCONSOLE + "/AllocateAll");
            SystemActions.sleep(10);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(subscribeAgentStatus, "The agent is not subscribed, Unsubscribe Negative test failed");
        Assert.assertTrue(output.contains(LogOutput.ENT_LIC_REQUIRED_UNSUBSCRIBE_AGENT), "The " + LogOutput.ENT_LIC_REQUIRED_UNSUBSCRIBE_AGENT + " message is not displayed in the cmd output");
    }

    /**
     * @test Verify /quickvalidate flag
     * @pre{
     * }
     * @steps{
     * - Run the build with the /quickvalidate flag
     * }
     * @result{
     * - Build failed;
     * - The error is displayed: Enterprise license file is required
     * }
     */

    @Test(testName = "Verify Quickvalidate Flag")
    public void verifyQuickvalidateFlag() {
        int exitcode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.Dashboard.AUDACITY_X32_DEBUG, "%s") + " /quickvalidate");
        String output = winService.runCommandGetOutput(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.Dashboard.AUDACITY_X32_DEBUG, "%s") + " /quickvalidate");
        Assert.assertTrue(exitcode != 0, "The build exited with returncode " + exitcode);
        Assert.assertTrue(output.contains(LogOutput.INITIATOR_ERROR_QUICKVALIDATE), "The " + LogOutput.INITIATOR_ERROR_QUICKVALIDATE + " message is not displayed in the cmd output");
    }

}

