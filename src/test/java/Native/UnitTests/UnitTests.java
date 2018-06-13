package Native.UnitTests;

import frameworkInfra.sikuli.sikulimapping.IBSettings.IBSettings;
import frameworkInfra.utils.*;
import ibInfra.ibService.IbService;
import ibInfra.vs.VSUIService;
import ibInfra.windowscl.WindowsService;
import org.apache.velocity.runtime.directive.Parse;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;

public class UnitTests {

    @Test
    public void test() {
        VSUIService vsService = new VSUIService();
        IbService ibService = new IbService();
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT +"\\Builder", StaticDataProvider.RegistryKeys.SAVE_BUILD_PACKET, "1");
        vsService.openVSInstance("15", false);
        vsService.createNewProject("custom");
        vsService.performIbActionFromPrjExplorer(StaticDataProvider.VsActions.REBUILD_SOLUTION, "solution", "custom");
        String result;
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"));
        } catch (IOException e) {
            e.getMessage();
        }
    }

    @Test
    public void test2(){
        WindowsService winService = new WindowsService();
        Screen screen = new Screen();
        boolean objectMissing = false;
        winService.runCommandDontWaitForTermination(StaticDataProvider.IbLocations.BUILDSETTINGS);
        if (screen.exists(IBSettings.MultiBuildTab, 5) == null)
            objectMissing = true;
        Assert.assertTrue(objectMissing, "MultiBuild tab should not be displayed with PRO license");
    }

    @Test(testName = "Ruby2.4 SyncPrivateAssemblies")
    public void ruby24SyncPrivateAssemblies() {
        String result = "";
        int agentCount = 0;
        IbService ibService = new IbService();
        WindowsService winService = new WindowsService();
        List<String> batmanMachineList;
        List rawBatmanList;
        rawBatmanList = XmlParser.getIpList("Machines/BatmanGrid.xml");
        batmanMachineList = XmlParser.breakDownIPList(rawBatmanList);
        winService.runCommandWaitForFinish(StaticDataProvider.ProjectsCommands.MISC_PROJECTS.RUBY_SYNC_PRIVATE_ASSEMBLIES);
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"), "Build failed");
        } catch (IOException e) {
            e.getMessage();
        }
        for (String machine : batmanMachineList) {
            if (Parser.doesFileContainString(StaticDataProvider.Locations.OUTPUT_LOG_FILE, "Agent '" + machine)) {
                agentCount++;
            }
        }
        Assert.assertTrue(agentCount > 0, "No agents were assigned to the build");
    }
}
