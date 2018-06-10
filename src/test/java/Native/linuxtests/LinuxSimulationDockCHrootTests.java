package Native.linuxtests;

//import frameworkInfra.testbases.linux.LinuxSimTestBase;

import frameworkInfra.testbases.LinuxSimTestBase;
import frameworkInfra.utils.StaticDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LinuxSimulationDockCHrootTests extends LinuxSimTestBase {

    @Test(testName = "Sim docker kenrel4 run")
    public void SimdDockerKenrel4Run() {

        linuxService.linuxRunSSHCommand("docker rm IB-Test01", ipList.get(2));

        linuxService.linuxRunSSHCommand("/opt/incredibuild/bin/ib_docker run -w /disk2/projects/linux-4.3.3 -v /disk2/projects:/disk2/projects --name IB-Test01 5624cdd7a83e ib_console make clean", ipList.get(2));

        linuxService.linuxRunSSHCommand("docker rm IB-Test01", ipList.get(2));

        int exitCode = linuxService.linuxRunSSHCommand("/opt/incredibuild/bin/ib_docker run -w /disk2/projects/linux-4.3.3 " +
                "-v /disk2/projects:/disk2/projects --name IB-Test01 5624cdd7a83e ib_console --ib-crash -d1 -c Kernel make -j8", ipList.get(2));

        Assert.assertEquals(exitCode, 0, "Test " + testName + " failed with Exit code " + exitCode);

        linuxService.linuxRunSSHCommand("docker rm IB-Test01", ipList.get(2));

        linuxService.linuxRunSSHCommand("/opt/incredibuild/bin/ib_docker run -w /disk2/projects/linux-4.3.3 " +
                "-v /disk2/projects:/disk2/projects --name IB-Test01 5624cdd7a83e ib_console make clean", ipList.get(2));

        linuxService.linuxRunSSHCommand("docker rm IB-Test01", ipList.get(2));
    }

    @Test(testName = "Sim docker kenrel4 exec", dependsOnMethods = "SimdDockerKenrel4Run")
    public void SimdDckerKenrel4Exec() {

        int exitCode = linuxService.linuxRunSSHCommand("/opt/incredibuild/bin/ib_docker run -td -w /disk2/projects/linux-4.3.3" +
                " -v /disk2/projects:/disk2/projects --name IB-Test02 5624cdd7a83", ipList.get(2));

        if (exitCode != 0) {
            linuxService.linuxRunSSHCommand("docker stop IB-Test02", ipList.get(2));
            linuxService.linuxRunSSHCommand("rm IB-Test02", ipList.get(2));
            Assert.assertEquals(exitCode, 0, "Test " + testName + " failed with Exit code " + exitCode);
        }

        linuxService.linuxRunSSHCommand("docker exec IB-Test02 ib_console make clean", ipList.get(2));
        exitCode = linuxService.linuxRunSSHCommand("docker exec IB-Test02 ib_console --ib-crash -d1 -c Kernel make -j8", ipList.get(2));
        linuxService.linuxRunSSHCommand("docker exec IB-Test02 ib_console make clean", ipList.get(2));
        linuxService.linuxRunSSHCommand("docker stop IB-Test02", ipList.get(2));
        linuxService.linuxRunSSHCommand("docker rm IB-Test02", ipList.get(2));
        Assert.assertEquals(exitCode, 0, "Test " + testName + " failed with Exit code " + exitCode);
    }


    @Test(testName = "Sim CHroot")
    public void SimdCHroot() {

        int exitCode = linuxService.linuxRunSSHCommand(" mount|grep chroot", ipList.get(2));

        if (exitCode != 0)
            linuxService.linuxRunSSHCommand("sudo mount  --bind /disk2 /chroot/xenial_u16/disk2 +(password); sudo -S true; sudo mount --bind /usr /chroot/xenial_u16/usr; sudo -S true", ipList.get(2));


       exitCode = linuxService.linuxRunSSHCommand("sudo chroot /chroot/xenial_u16 /bin/bash -c \" " + StaticDataProvider.LinuxSimulation.CD_KERNEL4_DIR + ";"
               + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";" + String.format(StaticDataProvider.LinuxSimulation.MAKE_BUILD,"--ib-crash -d1 --f","chroot_Kernel4", "", "32") + "\"", ipList.get(2));


        Assert.assertEquals(exitCode, 0, "Test " + testName + "failed with Exit code " + exitCode);

        linuxService.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_KERNEL4_DIR + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";", ipList.get(2));

    }
}
