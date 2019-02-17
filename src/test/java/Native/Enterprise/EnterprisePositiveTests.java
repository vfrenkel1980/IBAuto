package Native.Enterprise;

import frameworkInfra.testbases.EnterprisePositiveTestBase;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * @brief<b> <b>Enterprise features tests with Enterprise license loaded (IB Enterprise installed)</b>
 * @details vm: Dashbord on Host-6
 */
public class EnterprisePositiveTests extends EnterprisePositiveTestBase {
    /**
     * @test Verify <a href="https://incredibuild.atlassian.net/wiki/spaces/IN/pages/131730/Unsubscribing+Agents+via+the+Command+LineVerify">Xgcoordconsole /Unsubscribe Feature</a> when only core package is allocated
     * @pre{
     * - Only core package is allocated to the Agent}
     * @steps{
     * - Run the command: xgcoordconsole /unsubscribe=agent_machine_name
     * }
     * @result{
     * - The agent is unsubscribed: the subscribed status is false on the coordmonitor
     * }
     */
    @Test(testName = "Verify Xgcoordconsole Unsubscribe Feature")
    public void verifyXgcoordconsoleUnsubscribeFeature() {
        winService.runCommandWaitForFinish(IbLocations.XGCOORDCONSOLE + "/DeallocateAll");
        winService.runCommandWaitForFinish(IbLocations.XGCOORDCONSOLE + "/allocatePackages=\"4Cores\"");
        winService.runCommandWaitForFinish(IbLocations.XGCOORDCONSOLE + " /unsubscribe=" + WindowsMachines.DASHBORD_HELPER);
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
        Assert.assertFalse(subscribeAgentStatus, "The agent is subscribed, Unsubscribe test failed");

    }

    /**
     * @test Verify <a href="https://incredibuild.atlassian.net/wiki/spaces/IN/pages/131730/Unsubscribing+Agents+via+the+Command+LineVerify">Xgcoordconsole /Unsubscribe Feature</a> when Solution packages are allocated
     * @pre{
     * - Solution and  core packages are allocated to the Agent}
     * @steps{
     * - Run the command: xgcoordconsole /unsubscribe=agent_machine_name
     * }
     * @result{
     * - The agent isn't unsubscribed: the subscribed status is true on the coordmonitor;
     * - The error message is displayed in the command line output.
     * }
     */
    @Test(testName = "Verify Xgcoordconsole Unsubscribe Feature With Solution Package")
    public void verifyXgcoordconsoleUnsubscribeWithSolutionPackage() {
        String output = winService.runCommandGetOutput(IbLocations.XGCOORDCONSOLE + " /unsubscribe=" + WindowsMachines.DASHBORD_HELPER);
        boolean subscribeAgentStatus = true;
        try {
            subscribeAgentStatus = coordMonitor.getAgentSubscribeStatus(WindowsMachines.DASHBORD_HELPER);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(subscribeAgentStatus, "The agent is not subscribed, Unsubscribe test failed");
        Assert.assertTrue(output.contains(LogOutput.INITIATOR_ERROR__UNSUBSCRIBE_AGENT), "The "+LogOutput.ENT_LIC_REQUIRED_UNSUBSCRIBE_AGENT+" message is not displayed in the cmd output");
    }
}
