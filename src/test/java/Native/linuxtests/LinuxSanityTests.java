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


        WindowsService windowsService = new WindowsService();
        log.info("starting vmrun revertToSnapshot");
        int exitCode =  windowsService.runCommandWaitForFinish(StaticDataProvider.VMrunCommands.VMRUN + " revertToSnapshot " +  StaticDataProvider.LinuxSimulation.SANITY_VM_PATH +  " \"clean - no IB\"");
        Assert.assertTrue((exitCode == 0), "Sim LinuxSanityCleanTests - revertToSnapshot failed with Exit code " + exitCode);
        SystemActions.sleep(30);
        exitCode = windowsService.runCommandWaitForFinish(StaticDataProvider.VMrunCommands.VMRUN + " start " +  StaticDataProvider.LinuxSimulation.SANITY_VM_PATH );
        Assert.assertTrue((exitCode == 0), "Sim LinuxSanityCleanTests - start failed with Exit code " + exitCode);
        SystemActions.sleep(30);

        exitCode = linuxService.installIB(SanityHostName, IB_VERSION, " -i -S -O ", CoorHostName, CoorHostName, "/etc/incredibuild/", false);
        Assert.assertTrue((exitCode <= 0), "Sim LinuxSanityCleanTests - install1 failed with Exit code " + exitCode);

        exitCode = linuxService.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_KERNEL4_SANITY_DIR + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";" +
                String.format(StaticDataProvider.LinuxSimulation.MAKE_BUILD, "", "Sanity_Kernel", "", "32"), SanityHostName);
        Assert.assertTrue((exitCode <= 0), "Sim LinuxSanityCleanTests - kernel failed with Exit code " + exitCode);
    }

    @Test(testName = "Sim Sanity clean kernel plus")
    public void LinuxSanityCleanTestsPlus() {

        WindowsService windowsService = new WindowsService();
        int exitCode = windowsService.runCommandWaitForFinish(StaticDataProvider.VMrunCommands.VMRUN + " revertToSnapshot " +  StaticDataProvider.LinuxSimulation.SANITY_VM_PATH +  " \"clean - no IB\"");
        Assert.assertTrue((exitCode == 0), "Sim LinuxSanityCleanTestsPlus - revertToSnapshot failed with Exit code " + exitCode);
        SystemActions.sleep(30);
        exitCode = windowsService.runCommandWaitForFinish(StaticDataProvider.VMrunCommands.VMRUN + " start " +  StaticDataProvider.LinuxSimulation.SANITY_VM_PATH );
        Assert.assertTrue((exitCode == 0), "Sim LinuxSanityCleanTestsPlus - start failed with Exit code " + exitCode);
        SystemActions.sleep(30);

        exitCode = linuxService.installIB(SanityHostName, IB_VERSION, " -i -S -Z 20 -G 8888 -D -O ", CoorHostName, CoorHostName, "/etc/incredibuild/", false);
        Assert.assertTrue((exitCode <= 0), "Sim LinuxSanityCleanTestsPlus - install failed with Exit code " + exitCode);

        exitCode =  linuxService.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_KERNEL4_SANITY_DIR + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";" +
                String.format(StaticDataProvider.LinuxSimulation.MAKE_BUILD, "", "Sanity_Kernel", "", "32"), SanityHostName);
        Assert.assertTrue((exitCode <= 0), "Sim LinuxSanityCleanTestsPlus - kernel failed with Exit code " + exitCode);
    }

    @Test(testName = "Sim Sanity coord kernel")
    public void LinuxSanityCoordTests() {

        WindowsService windowsService = new WindowsService();
        int exitCode = windowsService.runCommandWaitForFinish( StaticDataProvider.VMrunCommands.VMRUN + " revertToSnapshot " +  StaticDataProvider.LinuxSimulation.SANITY_VM_PATH + " \"Coordinator installed\"");
        Assert.assertTrue((exitCode == 0), "Sim LinuxSanityCoordTests - revertToSnapshot coord failed with Exit code " + exitCode);
        SystemActions.sleep(30);
        exitCode =  windowsService.runCommandWaitForFinish(StaticDataProvider.VMrunCommands.VMRUN + " revertToSnapshot " +  StaticDataProvider.LinuxSimulation.SANITY_HELPER_VM_PATH + " \"clean - no IB\"");
        Assert.assertTrue((exitCode == 0), "Sim LinuxSanityCoordTests - revertToSnapshot helper failed with Exit code " + exitCode);
        SystemActions.sleep(30);


        exitCode =  windowsService.runCommandWaitForFinish(StaticDataProvider.VMrunCommands.VMRUN + " start " +  StaticDataProvider.LinuxSimulation.SANITY_VM_PATH );
        Assert.assertTrue((exitCode == 0), "Sim LinuxSanityCoordTests - start coord failed with Exit code " + exitCode);
        SystemActions.sleep(30);
        exitCode = windowsService.runCommandWaitForFinish(StaticDataProvider.VMrunCommands.VMRUN + " start " + StaticDataProvider.LinuxSimulation.SANITY_HELPER_VM_PATH);
        Assert.assertTrue((exitCode == 0), "Sim LinuxSanityCoordTests - start helper failed with Exit code " + exitCode);
        SystemActions.sleep(30);

        exitCode = linuxService.linuxRunSSHCommand(StaticDataProvider.LinuxCommands.UNINSTALL_IB, SanityHostName);
        Assert.assertTrue((exitCode <= 0), "Sim LinuxSanityCoordTests - uninstall failed with Exit code " + exitCode);

        exitCode = linuxService.installIB(SanityHostName, IB_VERSION, " -i -S -C", CoorHostName, CoorHostName, "/etc/incredibuild/", true);
        Assert.assertTrue((exitCode <= 0), "Sim LinuxSanityCoordTests - install failed with Exit code " + exitCode);

        exitCode = linuxService.installIB(SanityHelpName, IB_VERSION, " -i -S -O ", SanityHostName, CoorHostName, "/etc/incredibuild/", false);
        Assert.assertTrue((exitCode <= 0), "Sim LinuxSanityCoordTests - install2 failed with Exit code " + exitCode);

        exitCode = linuxService.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_KERNEL4_SANITY_DIR + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";" +
                String.format(StaticDataProvider.LinuxSimulation.MAKE_BUILD, "", "Sanity_Kernel", "", "16"), SanityHostName);
        Assert.assertTrue((exitCode <= 0), "Sim LinuxSanityCoordTests - kerne failed with Exit code " + exitCode);
    }
}

