package Native.linuxtests;

//import frameworkInfra.testbases.linux.LinuxSimTestBase;

import frameworkInfra.testbases.LinuxSimTestBase;
import frameworkInfra.utils.StaticDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LinuxSimulationDockCHrootTests extends LinuxSimTestBase {

    @Test(testName = "Sim docker kenrel4 run")
    public void SimdDockerKenrel4Run() {

        linuxService.linuxRunSSHCommand("docker rm IB-Test01", ipList.get(simClassType.ordinal()));
        linuxService.linuxRunSSHCommand("/opt/incredibuild/bin/ib_docker run -w /disk2/projects/linux-4.3.3 -v /disk2/projects:/disk2/projects --name IB-Test01 5624cdd7a83e  make clean", ipList.get(simClassType.ordinal()));
        linuxService.linuxRunSSHCommand("docker rm IB-Test01", ipList.get(simClassType.ordinal()));

        int exitCode = linuxService.linuxRunSSHCommand("/opt/incredibuild/bin/ib_docker run -w /disk2/projects/linux-4.3.3 " +
                "-v /disk2/projects:/disk2/projects --name IB-Test01 5624cdd7a83e ib_console --ib-crash -d1 -c dockKernel make -j32", ipList.get(simClassType.ordinal()));

        if (exitCode != 0) {
            linuxService.linuxRunSSHCommand("docker stop IB-Test01", ipList.get(simClassType.ordinal()));
            linuxService.linuxRunSSHCommand("docker rm IB-Test01", ipList.get(simClassType.ordinal()));
            Assert.assertTrue(exitCode <= 0, "Test " + testName + " failed with Exit code " + exitCode);
        }

        linuxService.linuxRunSSHCommand("docker rm IB-Test01", ipList.get(simClassType.ordinal()));

        linuxService.linuxRunSSHCommand("/opt/incredibuild/bin/ib_docker run -w /disk2/projects/linux-4.3.3 " +
                "-v /disk2/projects:/disk2/projects --name IB-Test01 5624cdd7a83e make clean", ipList.get(simClassType.ordinal()));

        linuxService.linuxRunSSHCommand("docker rm IB-Test01", ipList.get(simClassType.ordinal()));
    }

    @Test(testName = "Sim docker kenrel4 exec", dependsOnMethods = "SimdDockerKenrel4Run")
    public void SimdDckerKenrel4Exec() {

        int exitCode = linuxService.linuxRunSSHCommand("/opt/incredibuild/bin/ib_docker run -td -w /disk2/projects/linux-4.3.3 -v /disk2/projects:/disk2/projects --name IB-Test02 5624cdd7a83e", ipList.get(simClassType.ordinal()));
        linuxService.linuxRunSSHCommand("docker exec IB-Test02 make clean", ipList.get(simClassType.ordinal()));
        linuxService.linuxRunSSHCommand("docker stop IB-Test02", ipList.get(simClassType.ordinal()));
        linuxService.linuxRunSSHCommand("docker rm IB-Test02", ipList.get(simClassType.ordinal()));
        Assert.assertTrue(exitCode <= 0, "Test " + testName + "a failed with Exit code " + exitCode);

        exitCode = linuxService.linuxRunSSHCommand("/opt/incredibuild/bin/ib_docker run -td -w /disk2/projects/linux-4.3.3 -v /disk2/projects:/disk2/projects --name IB-Test02 5624cdd7a83e", ipList.get(simClassType.ordinal()));

        Assert.assertTrue(exitCode <= 0, "Test " + testName + "a failed with Exit code " + exitCode);
        exitCode = linuxService.linuxRunSSHCommand("docker exec IB-Test02 ib_console --ib-crash -d1 -c exec_dockKernel make -j32", ipList.get(simClassType.ordinal()));

        linuxService.linuxRunSSHCommand("docker exec IB-Test02 make clean", ipList.get(simClassType.ordinal()));
        linuxService.linuxRunSSHCommand("docker stop IB-Test02", ipList.get(simClassType.ordinal()));
        linuxService.linuxRunSSHCommand("docker rm IB-Test02", ipList.get(simClassType.ordinal()));
        Assert.assertTrue(exitCode <= 0, "Test " + testName + " failed with Exit code " + exitCode);
    }

    @Test(testName = "Sim docker kenrel4 run privileged", dependsOnMethods = "SimdDckerKenrel4Exec")
    public void SimdDockerKenrel4RunPrivileged() {

        linuxService.linuxRunSSHCommand("docker rm IB-Test03", ipList.get(simClassType.ordinal()));
        linuxService.linuxRunSSHCommand("/opt/incredibuild/bin/ib_docker run -w /disk2/projects/linux-4.3.3 -v /disk2/projects:/disk2/projects --name IB-Test03 5624cdd7a83e make clean", ipList.get(simClassType.ordinal()));
        linuxService.linuxRunSSHCommand("docker rm IB-Test03", ipList.get(simClassType.ordinal()));

        int exitCode = linuxService.linuxRunSSHCommand("/opt/incredibuild/bin/ib_docker run -w /disk2/projects/linux-4.3.3 " +
                "-v /disk2/projects:/disk2/projects --privileged --name IB-Test03 5624cdd7a83e ib_console --ib-crash -d1 -c dockKernelPrivileged make -j32", ipList.get(simClassType.ordinal()));

        if (exitCode != 0) {
            linuxService.linuxRunSSHCommand("docker stop IB-Test03", ipList.get(simClassType.ordinal()));
            linuxService.linuxRunSSHCommand("docker rm IB-Test03", ipList.get(simClassType.ordinal()));
            Assert.assertTrue(exitCode <= 0, "Test " + testName + " failed with Exit code " + exitCode);
        }

        linuxService.linuxRunSSHCommand("docker rm IB-Test03", ipList.get(simClassType.ordinal()));

        linuxService.linuxRunSSHCommand("/opt/incredibuild/bin/ib_docker run -w /disk2/projects/linux-4.3.3 " +
                "-v /disk2/projects:/disk2/projects --name IB-Test03 5624cdd7a83e make clean", ipList.get(simClassType.ordinal()));

        linuxService.linuxRunSSHCommand("docker rm IB-Test03", ipList.get(simClassType.ordinal()));
    }

    @Test(testName = "Sim docker kenrel4 exec privileged", dependsOnMethods = "SimdDockerKenrel4RunPrivileged")
    public void SimdDckerKenrel4ExecPrivileged() {

        int exitCode = linuxService.linuxRunSSHCommand("/opt/incredibuild/bin/ib_docker run -td -w /disk2/projects/linux-4.3.3 -v /disk2/projects:/disk2/projects --privileged --name IB-Test04 5624cdd7a83", ipList.get(simClassType.ordinal()));
        linuxService.linuxRunSSHCommand("docker exec IB-Test04 make clean", ipList.get(simClassType.ordinal()));
        linuxService.linuxRunSSHCommand("docker stop IB-Test04", ipList.get(simClassType.ordinal()));
        linuxService.linuxRunSSHCommand("docker rm IB-Test04", ipList.get(simClassType.ordinal()));
        Assert.assertTrue(exitCode <= 0, "Test " + testName + "a failed with Exit code " + exitCode);

        exitCode = linuxService.linuxRunSSHCommand("/opt/incredibuild/bin/ib_docker run -td -w /disk2/projects/linux-4.3.3 -v /disk2/projects:/disk2/projects --privileged --name IB-Test04 5624cdd7a83", ipList.get(simClassType.ordinal()));

        exitCode = linuxService.linuxRunSSHCommand("docker exec IB-Test04 ib_console --ib-crash -d1 -c exec_dockKernelPrivileged make -j32", ipList.get(simClassType.ordinal()));

        linuxService.linuxRunSSHCommand("docker exec IB-Test04 make clean", ipList.get(simClassType.ordinal()));
        linuxService.linuxRunSSHCommand("docker stop IB-Test04", ipList.get(simClassType.ordinal()));
        linuxService.linuxRunSSHCommand("docker rm IB-Test04", ipList.get(simClassType.ordinal()));
        Assert.assertTrue(exitCode <= 0, "Test " + testName + " failed with Exit code " + exitCode);
    }

    @Test(testName = "Sim Chroot", dependsOnMethods = "SimdDckerKenrel4ExecPrivileged")
    public void SimdCHroot() {

        linuxService.linuxRunSSHCommand("sudo mount  --bind /disk2 /chroot/xenial_u16/disk2; sudo -S true; sudo mount --bind /usr /chroot/xenial_u16/usr; sudo -S true", ipList.get(simClassType.ordinal()));

       int exitCode = linuxService.linuxRunSSHCommand("sudo chroot /chroot/xenial_u16 /bin/bash -c \" cd "+ StaticDataProvider.LinuxSimulation.CHROOT_KERNEL4_DIR + "; "
               + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + "; " + String.format(StaticDataProvider.LinuxSimulation.MAKE_BUILD,"--ib-crash -d1 --f","chroot_Kernel4", "", "32") + " ;echo $?\"", ipList.get(simClassType.ordinal()));

       Assert.assertTrue(exitCode <= 0, "Test " + testName + "failed with Exit code " + exitCode);

       linuxService.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_KERNEL4_DIR + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";", ipList.get(simClassType.ordinal()));
    }
}
