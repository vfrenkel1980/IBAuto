package Native.windowstests.phoenix;


import frameworkInfra.testbases.SingleUseVMTestBase;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import frameworkInfra.utils.parsers.Parser;
import ibInfra.ibExecs.IIBCoordMonitor;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;


/**
 * @brief<b> <a href="https://incredibuild.atlassian.net/wiki/spaces/IN/pages/65538/Single-use+Virtual+Machine+Image"><b>Single-use VM Enterprise feature</b></a> tests</b>
 * @details Requires Enterprise license <br>
 * vm: Phoenix on srv-3<br>
 * etc...
 */
public class PhoenixSingleUseVMTests extends SingleUseVMTestBase {
    IIBCoordMonitor coordMonitor = new IIBCoordMonitor();
    /**
     * @test SingleUse VM sanity e2e workflow test.<br>
     * @pre{ }
     * @steps{
     * - Start agent service and subscribe agent;
     * - Clean&build ConsApp.}
     * @result{
     * - Agent service is down after install (before 1st step);
     * - Build is succeeded;
     * - Build is distributed.}
     */
    @Test(testName = "SingleUse VM e2e flow Test")
    public void singleUseVME2EflowTest() {
        Assert.assertFalse(winService.isServiceRunning(WindowsServices.AGENT_SERVICE), "Agent service is running, should not be running.");
        setUp();
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_PHOENIX.CONSAPP_X64_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '"), "No agents were assigned to the build");
    }

    /**
     * @test Verify that the packages are not allocated automatically to single use agent when AutoSubscribe is turned off<br>
     * @pre{
     * - Agent service is started;
     * - Agent is subscribed;
     * - Auto subscribe setting (AutoSubscribeCloudNode regKey)is off}
     * @steps{
     * - Clean&build ConsApp.}
     * @result{
     * - The build is distributed.}
     */
    @Test(testName = "SingleUse VM Auto Assign Disabled Test")
    public void singleUseVMAutoAssignDisabledTest() {
        try {
            autoSubscribeSUVM("0");
            SystemActions.sleep(10);
            setUp();
            ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_PHOENIX.CONSAPP_X64_RELEASE, "%s"));
            Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '"), "Packages were allocated to the Agent");
        }catch (Exception e){
            e.getMessage();
        }finally {
            autoSubscribeSUVM("1");
        }
    }

    /**
     * @test Verify that the single use agent is displayed on the coordinator monitor after the start of agent service.
     * @pre{
     * - Agent service is started;
     * - Agent is subscribed;}
     * @steps{
     * - Export coordinator monitor}
     * @result{
     * - Single use agent is displayed on the coordinator monitor}
     */
    @Test(testName = "SingleUse VM Coord Mon Test")
    public void singleUseVMFCoordMonTest() {
        ibService.agentServiceStart();
        exportCoordMon();
        Assert.assertTrue(Parser.doesFileContainString(Locations.EXPORT_COORDMON_FILE, "phoenix"), "Single Use vm is not displayed on coordinator monitor");
    }

    /**
     * @test Verify that the single use agent is not destroyed when it goes offline for less than OfflinePeriodCloudNode time<br>
     * @pre{
     * - Agent service is started;
     * - Agent is subscribed;
     * - OfflinePeriodCloudNode time == 30(default);}
     * @steps{
     * - Stop agent service;
     * - Wait for 23 seconds;
     * - Start agent service;
     * - Clean&build Audacity.}
     * @result{
     * - Build is distributed.}
     */
    @Test(testName = "SingleUse VM Stop Service Positive Test")
    public void singleUseVMStopServicePositiveTest() {
        setUp();
        ibService.agentServiceStop();
        SystemActions.sleep(20);
        ibService.agentServiceStart();
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_PHOENIX.AUDACITY_X32_DEBUG, "%s"));
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '"), "No agents were assigned to the build");
    }

    /**
     * @test Verify that the single use agent is not destroyed when it goes offline less than OfflinePeriodCloudNode time (max time)<br>
     * @pre{
     * - Agent service is started;
     * - Agent is subscribed;
     * - OfflinePeriodCloudNode time == 300(max);}
     * @steps{
     * - Stop agent service;
     * - Wait for 285 seconds;
     * - Start agent service;
     * - Clean&build Audacity.}
     * @result{
     * - Build is distributed.}
     */
    @Test(testName = "SingleUse VM Stop Service Max Time Positive Test")
    public void singleUseVMStopServiceMaxTimePositiveTest() {
        setUp();
        setUnsubscribeTimeOnCoord(300);
        ibService.agentServiceStop();
        SystemActions.sleep(285);
        ibService.agentServiceStart();
        SystemActions.sleep(5);
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_PHOENIX.AUDACITY_X32_DEBUG, "%s"));
        setUnsubscribeTimeOnCoord(30);
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '"), "No agents were assigned to the build");
    }

    /**
     * @test Verify that the single use agent is destroyed when it goes offline for more than OfflinePeriodCloudNode time<br>
     * @pre{
     * - Agent service is started;
     * - Agent is subscribed;
     * - OfflinePeriodCloudNode time == 30(default);}
     * @steps{
     * - Stop agent service;
     * - Wait for 40 seconds;
     * - Start agent service;
     * - Clean&build ConsApp;
     * - Export coordinator monitor}
     * @result{
     * - Build is not distributed;
     * - Agent is not displayed on the Exported coordinator monitor}
     */
    @Test(testName = "SingleUse VM Stop Service Negative Test")
    public void singleUseVMStopServiceNegativeTest() {
        setUp();
        ibService.agentServiceStop();
        SystemActions.sleep(40);
        setUp();
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_PHOENIX.CONSAPP_X64_RELEASE, "%s"));
        Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '"), "Packages were allocated to the Agent");
        exportCoordMon();
        Assert.assertFalse(Parser.doesFileContainString(Locations.EXPORT_COORDMON_FILE, "phoenix"), "Single Use vm is displayed on coordinator monitor");
    }

    /**
     * @test Verify the single use <a href="https://incredibuild.atlassian.net/wiki/spaces/IN/pages/65538/Single-use+Virtual+Machine+Image#Single-useVirtualMachineImage-ResetSingle-UseVM">RESET feature</a>.<br>
     * @pre{
     * - Agent service is started;
     * - Agent is subscribed;
     * }
     * @steps{
     * - Reset SingleUse VM on coordinator;
     * - Wait for 99 seconds;
     * - Agent service is started;
     * - Agent is subscribed;
     * - Clean&build ConsApp.}
     * @result{
     * - Agent service is stopped after single use vm reset;
     * - ibat regKey is changed from 2 to 1 after single use vm reset;
     * - Build is distributed after agent service is started (agent subscribed and packages allocated).}
     * @todo implement reset single use vm once when Feature request  10128 is implemented
     */
    @Test(testName = "SingleUse VM Reset Test")
    public void singleUseVMResetTest() {
        setUp();
        //reset single use vm Feature request  10128
        //workaround to previous line
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\BuildService", RegistryKeys.RESET_SINGLE_USE, "1");
        ibService.agentServiceStop();
        //
        Assert.assertTrue(getIbatRegKey().equals("1"));
        Assert.assertFalse(winService.isServiceRunning(WindowsServices.AGENT_SERVICE), "Agent service is running, should not be running.");
        SystemActions.sleep(99);
        setUp();
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_PHOENIX.CONSAPP_X64_RELEASE, "%s"));
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '"), "No agents were assigned to the build");
    }


    /*------------------------------METHODS------------------------------*/
    public void setUp() {
        ibService.agentServiceStart();
        winService.runCommandWaitForFinish(Processes.PSEXEC + " \\\\" + WindowsMachines.BABYLON + " -u Administrator -p 4illumination -i 0 " + IbLocations.XGCOORDCONSOLE_USEFILE +" /Subscribe");
        String ibat = getIbatRegKey();
        int time = 0;
        while (!ibat.equals("2") & time <= 180) {
            ibat = getIbatRegKey();
            int timeout = 10;
            SystemActions.sleep(timeout);
            time += timeout;
        }
        Assert.assertTrue(ibat.equals("2"), "ibat reg key is not changed to 2");
        //add to build group
    }

    public void exportCoordMon() {
        winService.runCommandWaitForFinish(Processes.PSEXEC + " \\\\" + WindowsMachines.BABYLON + " -u Administrator -p 4illumination -i 0 " + Processes.XGCOORDCONSOLE+" /exportstatus=\"\\\\PHOENIX\\c$\\QA\\Simulation\\coordmon.xml\"");
    }

    public String getIbatRegKey() {
        return RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\BuildService", RegistryKeys.RESET_SINGLE_USE);
    }

}
