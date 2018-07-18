package Native.UnitTests;

import com.jcraft.jsch.JSchException;
import frameworkInfra.utils.*;
import ibInfra.ibService.IbService;
import ibInfra.linuxcl.LinuxDBService;
import ibInfra.linuxcl.LinuxService;
import ibInfra.vs.VSUIService;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;

public class UnitTests {

    @Test
    public void test() {
        VSUIService vsService = new VSUIService();
        IbService ibService = new IbService();
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\Builder", StaticDataProvider.RegistryKeys.SAVE_BUILD_PACKET, "1");
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
    public void test2() {
        LinuxService linuxService = new LinuxService();
        System.out.println(linuxService.linuxRunSSHCommandOutputString("ls -la", "h-27-cen7-test"));
    }


    @Test(testName = "test3")
    public void test3 () {
        LinuxService linuxService = new LinuxService();
        String installationFilePath = linuxService.getInstallerName(StaticDataProvider.LinuxMachines.LINUX_BUILDER, "0.94.81");
        String installationFileName = installationFilePath.substring(installationFilePath.lastIndexOf("/") + 1);
        System.out.println(installationFileName);
    }

}