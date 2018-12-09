package Native.linuxtests;

import frameworkInfra.testbases.LinuxSanityTestBase;
import frameworkInfra.testbases.LinuxTestBase;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.SystemActions;
import ibInfra.windowscl.WindowsService;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;


public class LinuxSanityTests extends LinuxSanityTestBase {

    @Test(testName = "Sim Sanity clean kernel")
    public void LinuxSanityCleanTests() {

        log.info("-------------------- starting LinuxSanityCleanTests");
        WindowsService windowsService = new WindowsService();
        log.info("starting vmrun revertToSnapshot");
        int exitCode =  windowsService.runCommandWaitForFinish("\"C:\\Program Files (x86)\\VMware\\VMware VIX\\vmrun.exe\" revertToSnapshot  \"F:\\VMs\\l2b-u16-S_Tests\\l2b-u16-S_Tests.vmx\" \"clean - no IB\"");
        SystemActions.sleep(30);
        log.info("revertToSnapshot exited with " + exitCode);
        log.info("starting vmrun start");
        exitCode = windowsService.runCommandWaitForFinish(" \"C:\\Program Files (x86)\\VMware\\VMware VIX\\vmrun.exe\" start  \"F:\\VMs\\l2b-u16-S_Tests\\l2b-u16-S_Tests.vmx\"");
        SystemActions.sleep(30);
        log.info("vmrun start  exited with " + exitCode);

        exitCode = linuxService.installIB(SanityHostName, VERSION, " -i -S -O ", CoorHostName, CoorHostName, "/etc/incredibuild/", false);
        log.info("installIB exited with " + exitCode);
        Assert.assertTrue((exitCode <= 0), "Sim LinuxSanityCleanTests - install1 failed with Exit code " + exitCode);

        exitCode = linuxService.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_KERNEL4_SANITY_DIR + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";" +
                String.format(StaticDataProvider.LinuxSimulation.MAKE_BUILD, "", "Sanity_Kernel", "", "32"), SanityHostName);
        log.info("build exited with " + exitCode);
        Assert.assertTrue((exitCode <= 0), "Sim LinuxSanityCleanTests - kernel failed with Exit code " + exitCode);
    }

    @Test(testName = "Sim Sanity clean kernel plus")
    public void LinuxSanityCleanTestsPlus() {

        log.info("-------------------- starting LinuxSanityCleanTestsPlus");
        WindowsService windowsService = new WindowsService();
        log.info("starting vmrun revertToSnapshot");
        int exitCode = windowsService.runCommandWaitForFinish(" \"C:\\Program Files (x86)\\VMware\\VMware VIX\\vmrun.exe\" revertToSnapshot  \"F:\\VMs\\l2b-u16-S_Tests\\l2b-u16-S_Tests.vmx\" \"clean - no IB\"");
        SystemActions.sleep(30);
        log.info("revertToSnapshot exited with " + exitCode);
        log.info("starting vmrun start");
        exitCode = windowsService.runCommandWaitForFinish(" \"C:\\Program Files (x86)\\VMware\\VMware VIX\\vmrun.exe\" start  \"F:\\VMs\\l2b-u16-S_Tests\\l2b-u16-S_Tests.vmx\"");
        SystemActions.sleep(30);
        log.info("vmrun start  exited with " + exitCode);

        exitCode = linuxService.installIB(SanityHostName, VERSION, " -i -S -Z 20 -G 8888 -D -O ", CoorHostName, CoorHostName, "/etc/incredibuild/", false);
        log.info("installIB exited with " + exitCode);
        Assert.assertTrue((exitCode <= 0), "Sim LinuxSanityCleanTestsPlus - install failed with Exit code " + exitCode);

        exitCode =  linuxService.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_KERNEL4_SANITY_DIR + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";" +
                String.format(StaticDataProvider.LinuxSimulation.MAKE_BUILD, "", "Sanity_Kernel", "", "32"), SanityHostName);
        log.info("build exited with " + exitCode);
        Assert.assertTrue((exitCode <= 0), "Sim LinuxSanityCleanTestsPlus - kernel failed with Exit code " + exitCode);
    }

    @Test(testName = "Sim Sanity coord kernel")
    public void LinuxSanityCoordTests() {

        log.info("-------------------- starting LinuxSanityCoordTests");
        WindowsService windowsService = new WindowsService();
        log.info("starting vmrun revertToSnapshot");
        int exitCode = windowsService.runCommandWaitForFinish(" \"C:\\Program Files (x86)\\VMware\\VMware VIX\\vmrun.exe\" revertToSnapshot  \"F:\\VMs\\l2b-u16-S_Tests\\l2b-u16-S_Tests.vmx\" \"Coordinator istalled\"");
        SystemActions.sleep(30);
        log.info("revertToSnapshot exited with " + exitCode);
        exitCode =  windowsService.runCommandWaitForFinish(" \"C:\\Program Files (x86)\\VMware\\VMware VIX\\vmrun.exe\" revertToSnapshot  \"E:\\NewSim VM's\\l1a-u14-snih\\l1a-u14-snih.vmx\" \"clean - no IB\"");
        SystemActions.sleep(30);
        log.info("revertToSnapshot exited with " + exitCode);

        log.info("starting vmrun start");
        exitCode =  windowsService.runCommandWaitForFinish(" \"C:\\Program Files (x86)\\VMware\\VMware VIX\\vmrun.exe\" start  \"F:\\VMs\\l2b-u16-S_Tests\\l2b-u16-S_Tests.vmx\"");
        SystemActions.sleep(30);
        log.info("vmrun start exited with " + exitCode);
        exitCode = windowsService.runCommandWaitForFinish(" \"C:\\Program Files (x86)\\VMware\\VMware VIX\\vmrun.exe\" start  \"E:\\NewSim VM's\\l1a-u14-snih\\l1a-u14-snih.vmx\"");
        SystemActions.sleep(30);
        log.info("vmrun start helper exited with " + exitCode);

        exitCode = linuxService.linuxRunSSHCommand(StaticDataProvider.LinuxCommands.UNINSTALL_IB, SanityHostName);
        Assert.assertTrue((exitCode <= 0), "Sim LinuxSanityCoordTests - uninstall failed with Exit code " + exitCode);

        exitCode = linuxService.installIB(SanityHostName, VERSION, " -i -S -C", CoorHostName, CoorHostName, "/etc/incredibuild/", true);
        log.info("installIB coord exited with " + exitCode);
        Assert.assertTrue((exitCode <= 0), "Sim LinuxSanityCoordTests - install failed with Exit code " + exitCode);

        exitCode = linuxService.installIB(SanityHelpName, VERSION, " -i -S -O ", SanityHostName, CoorHostName, "/etc/incredibuild/", false);
        log.info("installIB agent with " + exitCode);
        Assert.assertTrue((exitCode <= 0), "Sim LinuxSanityCoordTests - install2 failed with Exit code " + exitCode);

        exitCode = linuxService.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_KERNEL4_SANITY_DIR + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";" +
                String.format(StaticDataProvider.LinuxSimulation.MAKE_BUILD, "", "Sanity_Kernel", "", "16"), SanityHostName);
        log.info("build exited with " + exitCode);
        Assert.assertTrue((exitCode <= 0), "Sim LinuxSanityCoordTests - kerne failed with Exit code " + exitCode);
    }
}

