package Native.releasetests;


import frameworkInfra.testbases.EnterpriseNegativeTestBase;
import frameworkInfra.testbases.EnterpriseTestBase;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.parsers.Parser;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * @brief<b> <b>Enterprise features negative tests</b>
 * @details Requires Pro license <br>
 * vm: Dashbord on Host-6<br>
 * etc...
 */
public class EnterpriseNegativeTests extends EnterpriseNegativeTestBase {
    /**
     * @test <a href="https://incredibuild.atlassian.net/wiki/spaces/IN/pages/131730/Unsubscribing+Agents+via+the+Command+LineVerify">Xgcoordconsole /Unsubscribe Feature</a>
     * @pre{ }
     * @steps{ - Run the command: xgcoordconsole /unsubscribe=agent_machine_name
     * }
     * @result{ - The agent is subscribed and the subscribed status is true in the coordmonitor
     * }
     */
    @Test(testName = "Verify Xgcoordconsole Unsubscribe Feature")
    public void verifyXgcoordconsoleUnsubscribeFeature() {
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
        Assert.assertTrue(subscribeAgentStatus, "The agent is not subscribed, Unsubscribe Negative test failed");
    }
}

