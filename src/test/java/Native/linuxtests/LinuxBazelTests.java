package Native.linuxtests;

import frameworkInfra.testbases.LinuxSimTestBase;
import frameworkInfra.utils.StaticDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;


public class LinuxBazelTests extends LinuxSimTestBase {

    @Test(testName = "Sim Bazel kernel")
    public void SimTest32BitKernel() {

        int exitCode = linuxService.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_TENSOR_DIR + ";" + StaticDataProvider.LinuxSimulation.BAZEL_CLEAN + ";" +
                String.format(StaticDataProvider.LinuxSimulation.BAZEL_BUILD, LINUXCLFLAGS, "tensor", "", "24"), ipList.get(simClassType.ordinal()));
        linuxService.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_TENSOR_DIR + ";" + StaticDataProvider.LinuxSimulation.BAZEL_CLEAN + ";", ipList.get(simClassType.ordinal()));
        Assert.assertTrue((exitCode <= 0), "Sim tensorflow failed with Exit code " + exitCode);
    }

    @Test(testName = "Sim Bazel Sandbox kernel")
    public void SimTestSandbox() {

        int exitCode = linuxService.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_TENSOR_DIR + "&&" + StaticDataProvider.LinuxSimulation.BAZEL_CLEAN + "&&" +
                String.format(StaticDataProvider.LinuxSimulation.BAZEL_BUILD2, "--ib-crash -d1", "tensor_sandboxed", "", "24"), ipList.get(simClassType.ordinal()));
        linuxService.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_TENSOR_DIR + "&&" + StaticDataProvider.LinuxSimulation.BAZEL_CLEAN + ";", ipList.get(simClassType.ordinal()));
        Assert.assertTrue((exitCode <= 0), "Sim tensorflow with sandboxed build failed with Exit code " + exitCode);
    }
}