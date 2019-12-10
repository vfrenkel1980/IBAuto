package Native.Enterprise;

import frameworkInfra.testbases.EnterpriseNegativeTestBase;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import frameworkInfra.utils.parsers.Parser;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;

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
        winService.runCommandWaitForFinish(IbLocations.XGCOORDCONSOLE + "/DeallocateAll /Agents=" + WindowsMachines.DASHBORD_HELPER);
        winService.runCommandWaitForFinish(IbLocations.XGCOORDCONSOLE + "/allocatePackages=\"4Cores\" /Agents=" + WindowsMachines.DASHBORD_HELPER);
        String output = winService.runCommandGetOutput(IbLocations.XGCOORDCONSOLE + " /unsubscribe=" + WindowsMachines.DASHBORD_HELPER);
        boolean subscribeAgentStatus = true;
        try {
            subscribeAgentStatus = coordMonitor.getAgentSubscribeStatus(WindowsMachines.DASHBORD_HELPER);
            winService.runCommandWaitForFinish(IbLocations.XGCOORDCONSOLE + "/Subscribe /Agents=" + WindowsMachines.DASHBORD_HELPER);
            winService.runCommandWaitForFinish(IbLocations.XGCOORDCONSOLE + "/AllocateAll /Agents=" + WindowsMachines.DASHBORD_HELPER);
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

    //multibuild

    @Ignore
    @Test(testName = "Verify MultiBuild Success")
    public void verifyMultiBuildSuccess() {
        int instanceCount;
        setRegistry("2", "BuildService", RegistryKeys.MAX_CONCURRENT_BUILDS);
        winService.runCommandDontWaitForTermination(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.Dashboard.AUDACITY_X32_DEBUG, ProjectsCommands.REBUILD));
        SystemActions.sleep(1);
        winService.runCommandDontWaitForTermination(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC14_BATMAN.BLENDER_X64_RELEASE, ProjectsCommands.REBUILD));
        SystemActions.sleep(5);
        instanceCount = winService.getNumberOfProcessInstances(Processes.BUILDSYSTEM);
        Assert.assertEquals(instanceCount, 1, "Number of running instances does not match");
        winService.waitForProcessToFinish(Processes.BUILDSYSTEM);
    }

    //multihelper
    @Ignore
    @Test(testName = "Verify Multi Helper Assignment")
    public void verifyMultiHelperAssignment() {
        try {
            winService.runCommandDontWaitForTermination(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.AUDACITY_X32_DEBUG, ProjectsCommands.REBUILD));
            winService.runCommandDontWaitForTermination(Processes.PSEXEC + " \\\\" + WindowsMachines.SECOND_INITIATOR + " -u Administrator -p 4illumination -i 0 " +
                    String.format("\"C:\\Program Files (x86)\\IncrediBuild\\buildconsole.exe\" " + ProjectsCommands.VC15_BATMAN.AUDACITY_SECOND_INITIATOR, ProjectsCommands.REBUILD));
            SystemActions.sleep(30);
            winService.waitForProcessToFinishOnRemoteMachine(WindowsMachines.SECOND_INITIATOR, "Administrator", "4illumination", "buildconsole");
            boolean isPresent = Parser.doesFileContainString(Locations.SECOND_INITIATOR_LOG_PATH + "buildlog.txt", LogOutput.AGENT);
            if (isPresent) {
                SystemActions.copyFile(Locations.SECOND_INITIATOR_LOG_PATH + "buildlog.txt", Locations.QA_ROOT + "\\logs\\for_investigation\\buildlog.txt");
            }
            SystemActions.deleteFile(Locations.SECOND_INITIATOR_LOG_PATH + "buildlog.txt");
            Assert.assertTrue(isPresent, "No agent assigned to build");
        } catch (Exception e) {
            e.getMessage();
        } finally {
            winService.waitForProcessToFinish(Processes.BUILD_CONSOLE);
        }
    }

    /*------------------------------METHODS------------------------------*/

    private void setRegistry(String required, String folder, String keyName) {
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\" + folder, keyName, required);
    }

}

