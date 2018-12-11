package Native.windowstests.phoenix;

import frameworkInfra.testbases.SingleUseVMTestBase;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import frameworkInfra.utils.parsers.Parser;
import org.testng.Assert;
import org.testng.annotations.Test;


public class PhoenixSingleUseVMTests extends SingleUseVMTestBase {
//Win10

    @Test(testName = "SU VM First use Service Down Test")
    public void SUVMFirstuseServiceDownTest() {
        Assert.assertFalse(winService.isServiceRunning(WindowsServices.AGENT_SERVICE), "Agent service is running, should not be running.");
    }

    @Test(testName = "SU VM First use Unsubscribed Test")
    public void SUVMFirstUseUnsubscribedTest() {
        //assert check agent unsubscribed
    }

    @Test(testName = "SU VM Build Test")
    public void sUVMBuildTest() {
        setUp();
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_PHOENIX.AUDACITY_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '"), "No agents were assigned to the build");
    }

    @Test(testName = "SU VM Stop Service Positive Test")
    public void sUVMStopServicePositiveTest() {
        setUp();
        setUnsubscribeTimeOnCoord(30);
        ibService.agentServiceStart();
        SystemActions.sleep(10);
        ibService.agentServiceStop();
        SystemActions.sleep(25);
        ibService.agentServiceStart();
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_PHOENIX.ACE_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '"), "No agents were assigned to the build");
    }

    @Test(testName = "SU VM Stop Service Custom Time Positive Test")
    public void sUVMStopServiceCustomTimePositiveTest() {
//        //change time to reconnect (coord reg key for single use vm kill)to 300
//        //stop agent service for 295 sec +
//        //start service+
//        //build+
//        //exitcode+
        setUp();
        setUnsubscribeTimeOnCoord(300);
        winService.runCommandWaitForFinish("net stop \"" + WindowsServices.AGENT_SERVICE + "\"");
        SystemActions.sleep(295);
        winService.runCommandWaitForFinish("net start \"" + WindowsServices.AGENT_SERVICE + "\"");
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_PHOENIX.ACE_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '"), "No agents were assigned to the build");
    }
    //
    @Test(testName = "SU VM Reset Test")
    public void sUVMResetTest() {
        ////check coord reg key for single use vm kill-
//        //reset SUVM on coordinator-
//        ////reg key ibat on agent-
//        //stop agent service for 60 sec+
//        //start service+
//        //build+
//        //exitcode+
//        //check remote+
//        //// >2 min after service start - reg key ibat on agent+
        setUp();
        winService.runCommandWaitForFinish("net stop \"" + WindowsServices.AGENT_SERVICE + "\"");
        SystemActions.sleep(35);
        winService.runCommandWaitForFinish("net start \"" + WindowsServices.AGENT_SERVICE + "\"");
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_PHOENIX.AUDACITY_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '"), "No agents were assigned to the build");
        SystemActions.sleep(180);
    }

    @Test(testName = "SU VM Auto Assign Packages Disabled Test")
    public void sUVMAutoAssignPackagesDisabledTest() {
        ////check coord reg key for single use vm kill+
        //uncheck autoassign-
        //stop agent service for 31 sec+
        //start service+
        //build+
        //exitcode+
        //standalone+
        //no on coord monitor-
        setUp();
        setUnsubscribeTimeOnCoord(30);
        winService.runCommandWaitForFinish("net stop \"" + WindowsServices.AGENT_SERVICE + "\"");
        SystemActions.sleep(31);
        winService.runCommandWaitForFinish("net start \"" + WindowsServices.AGENT_SERVICE + "\"");
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_PHOENIX.AUDACITY_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
        Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '"), "Packages were allocated to the Agent");
    }
    @Test(testName = "SU VM Stop Service Negative Test")
    public void sUVMStopServiceNegativeTest() {
        ////check coord reg key for single use vm kill-
        //stop agent service for 31 sec+
        //start service+
        //build+
        //exitcode+
        //standalone+
        //no on coord monitor-
        setUp();
        setUnsubscribeTimeOnCoord(30);
        winService.runCommandWaitForFinish("net stop \"" + WindowsServices.AGENT_SERVICE + "\"");
        SystemActions.sleep(31);
        winService.runCommandWaitForFinish("net start \"" + WindowsServices.AGENT_SERVICE + "\"");
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_PHOENIX.AUDACITY_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
        Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '"), "Packages were allocated to the Agent");
    }

//    public void readRemoteReg(){
//        WinReg.HKEYByReference result = new WinReg.HKEYByReference();
//        Advapi32.RegConnectRegistry("\\\\babylon", WinReg.HKEY_LOCAL_MACHINE, result);
//
//    }

    public void setUp(){
        ibService.agentServiceStart();
        //subscribe agent+
        winService.runCommandDontWaitForTermination(Processes.PSEXEC + " \\\\" + WindowsMachines.BABYLON + " -u Administrator -p 4illumination -i 1 " + "xgCoordConsole /Subscribeall");
        //add to build group

    }

    public void setUnsubscribeTimeOnCoord(int time){
        winService.runCommandWaitForFinish("cmd /D REG ADD \\\\babylon\\HKLM\\SOFTWARE\\Wow6432Node\\Xoreax\\IncrediBuild\\Coordinator /v OfflinePeriodCloudNode /t REG_SZ /d " + time + " /f");
    }
}
