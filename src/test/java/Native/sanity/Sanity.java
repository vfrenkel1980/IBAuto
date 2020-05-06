package Native.sanity;

import frameworkInfra.testbases.SanityTestBase;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.SystemActions;
import org.testng.Assert;
import org.testng.annotations.Test;
import frameworkInfra.utils.StaticDataProvider.*;

import static com.sun.jna.platform.win32.WinReg.HKEY_CURRENT_USER;
import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
/**
 * @brief<b> <a href=><b>Batman Automation coverage</b></a> tests</b>
 * @details Run on Batman (Batman host)
 */
public class Sanity extends SanityTestBase {

    @Test(testName = "Audacity 2017 - Debug - build" , groups = { "Build" })
    public void audacityDebugBuild(){
        int returnCode = ibService.cleanAndBuild(StaticDataProvider.IbLocations.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC15_BATMAN.AUDACITY_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }
    /**
     * @test Verify Pro Uninstall Leftovers
     * @pre{ }
     * @steps{
     * - Install incredibuild
     * - Run clean and build
     * - Uninstall IB using command line
     * - Check if a HKEY key exist
     * - Check if a file exist
     * - Check if a file exist
     * }
     * @result{ - Return file }
     */
    @Test(testName = "Verify Pro Uninstall Leftovers")
    public void verifyProUninstallLeftovers() {
        ibService.installIB(IB_VERSION, IbLicenses.UI_LIC);
        runBuildAndAssert();
        ibService.uninstallIB(IB_VERSION);
        Assert.assertFalse(RegistryService.doesKeyExist(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT), "HKLM wasn't removed in verifyProUninstallLeftovers test");
        Assert.assertFalse(RegistryService.doesKeyExist(HKEY_CURRENT_USER, Locations.IB_HKCU_REG_ROOT), "HKCU wasn't removed in verifyProUninstallLeftovers test");
        Assert.assertFalse(SystemActions.doesFileExist(IbLocations.IB_ROOT), "IB root folder wasn't removed in verifyProUninstallLeftovers test");
        Assert.assertFalse(SystemActions.doesFileExist(IbLocations.IB_SHORTCUTS), "Start menu shortcuts weren't removed in verifyProUninstallLeftovers test");
    }

    private void runBuildAndAssert() {
        int returnCode = ibService.cleanAndBuild("\"" + IbLocations.IB_ROOT + "\\" + Processes.BUILD_CONSOLE + "\" " + String.format(TestProjects.TEST_PROJ, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }
}
