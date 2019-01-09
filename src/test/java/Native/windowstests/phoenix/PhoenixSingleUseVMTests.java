package Native.windowstests.phoenix;

import frameworkInfra.testbases.SingleUseVMTestBase;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import frameworkInfra.utils.parsers.Parser;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;

/**
 * Single-use Virtual Machine Image functional tests<br>
 * Requires Enterprise license<br>
 * vm: Phoenix on srv-3
 */

public class PhoenixSingleUseVMTests extends SingleUseVMTestBase {
    /**
     * Sanity e2e workflow test.<br>
     * Checks:<br>
     *  <ul>
     *      <li>the agent service is down on first use;</li>
     *      <li> the build is executed successfully;</li>
     *      <li> the build was distributed.</li>
     *  </ul>
     */
    @Test(testName = "SU VM First Use Build Test")
    public void suVMFirstUseBuildTest() {
        Assert.assertFalse(winService.isServiceRunning(WindowsServices.AGENT_SERVICE), "Agent service is running, should not be running.");
        setUp();
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_PHOENIX.CONSAPP_X64_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '"), "No agents were assigned to the build");
    }

    /**
     * Verify that the packages are not allocated automatically when AutoSubscribeCloudNode is off<br>
     * Checks:<br>
     * <ul>
     *     <li>the build is not distributed</li>
     * </ul>
     */
    @Test(testName = "SU VM Auto Assign Packages Disabled Test")
    public void suVMAutoAssignPackagesDisabledTest() {
        try {
            autoSubscribeSUVM("0");
            setUp();
            ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_PHOENIX.CONSAPP_X64_RELEASE, "%s"));
            Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '"), "Packages were allocated to the Agent");
        } catch (Exception e) {
            e.getMessage();
        } finally {
            autoSubscribeSUVM("1");
        }
    }

    /**
     * Verify that the single use agent is displayed on the coordinator monitor after the agent service is started.<br>
     * Checks:<br>
     * <ul>
     *     <li>the exported coordinator monitor file contains the agent name</li>
     * </ul>
     */
    @Test(testName = "SU VM Coord Mon Test")
    public void suVMFCoordMonTest() {
        ibService.agentServiceStart();
        exportCoordMon();
        Assert.assertTrue(Parser.doesFileContainString(Locations.EXPORT_COORDMON_FILE, "phoenix"), "Single Use vm is not displayed on coordinator monitor");
    }

    /**
     *Verify that the single use agent is not destroyed when it goes offline for less than OfflinePeriodCloudNode time<br>
     * Checks:<br>
     * <ul>
     *     <li>the build is distributed after goes offline for 23 sec (OfflinePeriodCloudNode time == 30(default)</li>
     * </ul>
     */
    @Test(testName = "SU VM Stop Service Positive Test")
    public void suVMStopServicePositiveTest() {
        setUp();
        ibService.agentServiceStop();
        SystemActions.sleep(23);
        ibService.agentServiceStart();
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_PHOENIX.AUDACITY_X32_DEBUG, "%s"));
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '"), "No agents were assigned to the build");
    }

    /**
     *Verify that the single use agent is not destroyed when it goes offline less than OfflinePeriodCloudNode time (max time)<br>
     * Checks:<br>
     * <ul>
     *     <li>the build is distributed after goes offline for 285 sec (OfflinePeriodCloudNode time == 300(max)</li>
     * </ul>
     */
    @Test(testName = "SU VM Stop Service Max Time Positive Test")
    public void suVMStopServiceMaxTimePositiveTest() {
        setUp();
        setUnsubscribeTimeOnCoord(300);
        ibService.agentServiceStop();
        SystemActions.sleep(285);
        ibService.agentServiceStart();
        SystemActions.sleep(5);
        try {
            ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_PHOENIX.AUDACITY_X32_DEBUG, "%s"));
            Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '"), "No agents were assigned to the build");
        } catch (Exception e) {
            e.getMessage();
        } finally {
            setUnsubscribeTimeOnCoord(30);
        }
    }

    /**
     *Verify that the single use agent is destroyed when it goes offline for more than OfflinePeriodCloudNode time<br>
     * Checks:<br>
     * <ul>
     *     <li>the build is not distributed after goes offline for 40 sec (OfflinePeriodCloudNode time == 30(default)</li>
     *     <li> the exported coordinator monitor file doesn't contain the agent name</li>
     * </ul>
     */
    @Test(testName = "SU VM Stop Service Negative Test")
    public void sUVMStopServiceNegativeTest() {
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
     *Verify the single use RESET feature.<br>
     * Checks:<br>
     * <ul>
     *     <li>the agent service is stoped after single use vm reset</li>
     *     <li> ibat regKey is changed from 2 to 1 after single use vm reset</li>
     *     <li> the build is distributed after agent service is started, agent subscribed and packages allocated</li>
     * </ul>
     */
    @Test(testName = "SU VM Reset Test")
    public void suVMResetTest() {
        setUp();
        //TODO: reset single use vm once when Feature request  10128 is implemented
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
        winService.runCommandWaitForFinish(Processes.PSEXEC + " \\\\" + WindowsMachines.BABYLON + " -u Administrator -p 4illumination -i 0 " + "xgCoordConsole /Subscribeall");
        String ibat = getIbatRegKey();
        int time = 0;
        while (!ibat.equals("2") || time <= 180) {
            ibat = getIbatRegKey();
            int timeout = 10;
            SystemActions.sleep(timeout);
            time += timeout;
        }
        Assert.assertTrue(ibat.equals("2"), "ibat reg key is not changed to 2");
        //add to build group
    }

    public void exportCoordMon() {
        winService.runCommandWaitForFinish(Processes.PSEXEC + " \\\\" + WindowsMachines.BABYLON + " -u Administrator -p 4illumination -i 0 " + "xgcoordconsole /exportstatus=\"\\\\PHOENIX\\c$\\QA\\Simulation\\coordmon.xml\"");
    }

    public String getIbatRegKey() {
        return RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\BuildService", RegistryKeys.RESET_SINGLE_USE);
    }
}
