package Native.releasetests;

import frameworkInfra.testbases.EnterprisePositiveTestBase;
import frameworkInfra.utils.StaticDataProvider.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * @brief<b> <b>Enterprise features tests</b>
 * @details Requires Enterprise license <br>
 * vm: Dashbord on Host-6<br>
 * etc...
 */
public class EnterprisePositiveTests extends EnterprisePositiveTestBase {
    /**
     * @test Verify <a href="https://incredibuild.atlassian.net/wiki/spaces/IN/pages/131730/Unsubscribing+Agents+via+the+Command+LineVerify">Xgcoordconsole /Unsubscribe Feature</a>
     * when only core package is allocated
     * @pre{ - Only core package is allocated}
     * @steps{ - Run the command: xgcoordconsole /unsubscribe=agent_machine_name
     * }
     * @result{ - The agent is unsubscribed and the subscribed status is false in the coordmonitor
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
     * @test Verify <a href="https://incredibuild.atlassian.net/wiki/spaces/IN/pages/131730/Unsubscribing+Agents+via+the+Command+LineVerify">Xgcoordconsole /Unsubscribe Feature</a>
     * when Solution packages are allocated
     * @pre{ - Solution and  core packages are allocated}
     * @steps{ - Run the command: xgcoordconsole /unsubscribe=agent_machine_name
     * }
     * @result{ - The agent isn't unsubscribed and the subscribed status is true in the coordmonitor
     * }
     */
    @Test(testName = "Verify Xgcoordconsole Unsubscribe Feature With Solution Package")
    public void verifyXgcoordconsoleUnsubscribeWithSolutionPackage() {
        winService.runCommandWaitForFinish(IbLocations.XGCOORDCONSOLE + " /unsubscribe=" + WindowsMachines.DASHBORD_HELPER);
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
    }
}
