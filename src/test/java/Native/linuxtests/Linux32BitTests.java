package Native.linuxtests;

import frameworkInfra.testbases.LinuxSimTestBase;
import frameworkInfra.utils.StaticDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;



public class Linux32BitTests extends LinuxSimTestBase {

    @Test(testName = "Sim 32Bit kernel")
    public void SimTest32BitKernel() {
        int exitCode = linuxService.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_32BIT_KERNEL_DIR + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";" +
                String.format(StaticDataProvider.LinuxSimulation.MAKE_BUILD, "--ib-crash -d1 --f", "32_bit_Kernel", "", "32"), ipList.get(simClassType.ordinal()));
        linuxService.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_32BIT_KERNEL_DIR + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";", ipList.get(simClassType.ordinal()));
        Assert.assertTrue((exitCode <= 0), "Sim 32Bit kernel failed with Exit code " + exitCode);
    }
}

