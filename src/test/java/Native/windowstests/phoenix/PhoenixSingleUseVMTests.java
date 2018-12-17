package Native.windowstests.phoenix;

import frameworkInfra.testbases.SingleUseVMTestBase;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import frameworkInfra.utils.parsers.Parser;
import org.testng.Assert;
import org.testng.annotations.Test;
import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;

public class PhoenixSingleUseVMTests extends SingleUseVMTestBase {

    @Test(testName = "SU VM First use Service Down Test")
    public void SUVMFirstuseServiceDownTest() {
        Assert.assertFalse(winService.isServiceRunning(WindowsServices.AGENT_SERVICE), "Agent service is running, should not be running.");
    }

    @Test(testName = "SU VM First use Unsubscribed Test")
    public void SUVMFirstUseUnsubscribedTest() {
        ibService.agentServiceStart();
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_PHOENIX.CONSAPP_X64_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
        Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '"), "Agents were assigned to the build but shouldn't");
    }

    @Test(testName = "SU VM Build Test")
    public void sUVMBuildTest() {
        setUp();
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_PHOENIX.CONSAPP_X64_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '"), "No agents were assigned to the build");
    }

    @Test(testName = "SU VM Stop Service Positive Test")
    public void sUVMStopServicePositiveTest() {
        setUp();
        setUnsubscribeTimeOnCoord(30);
        ibService.agentServiceStop();
        SystemActions.sleep(25);
        ibService.agentServiceStart();
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_PHOENIX.CONSAPP_X64_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '"), "No agents were assigned to the build");
    }

    @Test(testName = "SU VM Stop Service Custom Time Positive Test")
    public void sUVMStopServiceCustomTimePositiveTest() {
        setUp();
        setUnsubscribeTimeOnCoord(300);
        winService.runCommandWaitForFinish("net stop \"" + WindowsServices.AGENT_SERVICE + "\"");
        SystemActions.sleep(295);
        winService.runCommandWaitForFinish("net start \"" + WindowsServices.AGENT_SERVICE + "\"");
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_PHOENIX.CONSAPP_X64_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '"), "No agents were assigned to the build");
    }

    @Test(enabled = false,testName = "SU VM Reset Test")
    public void sUVMResetTest() {
        setUp();
        setUnsubscribeTimeOnCoord(30);
        //reset single use vm Feature request  10128
        String ibat = RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\BuildService", RegistryKeys.RESET_SINGLE_USE);
        Assert.assertTrue(ibat.equals("1"));
        Assert.assertFalse(winService.isServiceRunning(WindowsServices.AGENT_SERVICE), "Agent service is running, should not be running.");
        SystemActions.sleep(45);
        setUp();
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_PHOENIX.CONSAPP_X64_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '"), "No agents were assigned to the build");
        SystemActions.sleep(180);
        String ibat1 = RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\BuildService", RegistryKeys.RESET_SINGLE_USE);
        Assert.assertTrue(ibat1.equals("2"));
    }

    @Test(testName = "SU VM Auto Assign Packages Disabled Test")
    public void sUVMAutoAssignPackagesDisabledTest() {
        setUnsubscribeTimeOnCoord(30);
        autoSubscribeSUVM("0");
        setUp();
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_PHOENIX.CONSAPP_X64_RELEASE, "%s"));
        autoSubscribeSUVM("1");
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
        Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '"), "Packages were allocated to the Agent");
    }

    @Test(testName = "SU VM Stop Service Negative Test")
    public void sUVMStopServiceNegativeTest() {
        setUp();
        setUnsubscribeTimeOnCoord(30);
        winService.runCommandWaitForFinish("net stop \"" + WindowsServices.AGENT_SERVICE + "\"");
        SystemActions.sleep(31);
        winService.runCommandWaitForFinish("net start \"" + WindowsServices.AGENT_SERVICE + "\"");
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_PHOENIX.CONSAPP_X64_RELEASE, "%s"));
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
        SystemActions.sleep(1);
        winService.runCommandDontWaitForTermination(Processes.PSEXEC + " \\\\" + WindowsMachines.BABYLON + " -u Administrator -p 4illumination -i 0 " + "xgCoordConsole /Subscribeall");
        //add to build group

    }

    public void setUnsubscribeTimeOnCoord(int time){
        winService.runCommandWaitForFinish("REG ADD \\\\BABYLON\\HKLM\\SOFTWARE\\Wow6432Node\\Xoreax\\IncrediBuild\\Coordinator /v OfflinePeriodCloudNode /t REG_SZ /d " + time + " /f");
        SystemActions.sleep(2);
    }

    public void autoSubscribeSUVM(String value){
        winService.runCommandWaitForFinish("REG ADD \\\\BABYLON\\HKLM\\SOFTWARE\\Wow6432Node\\Xoreax\\IncrediBuild\\Coordinator /v AutoSubscribeCloudNode /t REG_SZ /d " + value + " /f");
        SystemActions.sleep(1);
    }
}
