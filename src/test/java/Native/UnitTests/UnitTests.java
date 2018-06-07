package Native.UnitTests;

import frameworkInfra.utils.*;
import ibInfra.ibService.IbService;
import ibInfra.vs.VSUIService;
import ibInfra.windowscl.WindowsService;
import org.sikuli.script.FindFailed;
import org.testng.Assert;
import org.testng.annotations.Test;

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
    public void test2() throws FindFailed, IOException {
        IbService ibService = new IbService();
        ibService.cleanAndBuildDontWaitTermination(StaticDataProvider.IbLocations.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC15_BATMAN.AUDACITY_X32_DEBUG, "%s"));
        WindowsService windowsService = new WindowsService();
        windowsService.runCommandDontWaitForTermination(StaticDataProvider.Processes.PSEXEC + " \\\\" + StaticDataProvider.WindowsMachines.SECOND_INITIATOR + " -u Administrator -p 4illumination -i 1 " +
                "\"C:\\Program Files (x86)\\IncrediBuild\\buildconsole\" C:\\QA\\Simulation\\VC11\\ACE_VC11\\ACE_vc2012.sln /rebuild /cfg=\"debug|win32\" /title=\"ACE 2012 - Debug\" " +
                "/out=\"C:\\QA\\simulation\\buildlog.txt\" /showagent /showcmd /showtime");

        windowsService.waitForProcessToFinishOnRemoteMachine(StaticDataProvider.WindowsMachines.SECOND_INITIATOR, "administrator" , "4illumination", "buildsystem");
        windowsService.runCommandWaitForFinish("xcopy \"r:\\QA\\Simulation\\buildLog.txt\" \"c:\\qa\\simulation\\second_initiator_output\"");
        boolean isPresent = Parser.doesFileContainString(StaticDataProvider.Locations.QA_ROOT + "\\vmsimoutput\\buildlog.txt", "Agent '");
        SystemActions.deleteFile(StaticDataProvider.Locations.QA_ROOT + "\\second_initiator_output\\buildlog.txt");
        windowsService.waitForProcessToFinish(StaticDataProvider.Processes.BUILDSYSTEM);
        Assert.assertTrue(isPresent);
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
