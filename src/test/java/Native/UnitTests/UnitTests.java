package Native.UnitTests;

import com.jcraft.jsch.JSchException;
import frameworkInfra.utils.*;
import ibInfra.ibService.IbService;
import ibInfra.linuxcl.LinuxService;
import ibInfra.vs.VSUIService;
import ibInfra.windowscl.WindowsService;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;

public class UnitTests{

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

    @Test(testName = "test2")
    public void test2() throws JSchException {
        LinuxService linuxService = new LinuxService();
        linuxService.getFile("192.168.10.123", "/home/xoreax/res29_06_2018_192.168.10.123", "x:\\");
    }

    @Test(testName = "test3")
    public void test3() {
        LinuxService linuxService = new LinuxService();
        String lastBuild = linuxService.runQueryLastBuild(StaticDataProvider.LinuxCommands.BUILD_ID, StaticDataProvider.LinuxCommands.BUILD_HISTORY,"192.168.11.118");
        lastBuild = lastBuild.replace("\n","");
        int firstBuild = Integer.parseInt(lastBuild) + 1;
        lastBuild = String.valueOf(firstBuild);
        System.out.println(lastBuild);

    }
}
