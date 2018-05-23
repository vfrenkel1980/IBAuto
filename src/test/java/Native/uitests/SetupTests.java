package Native.uitests;

import com.aventstack.extentreports.Status;
import frameworkInfra.testbases.SetupTestBase;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import org.sikuli.script.FindFailed;
import org.testng.Assert;
import org.testng.annotations.Test;


import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static frameworkInfra.Listeners.SuiteListener.test;

public class SetupTests extends SetupTestBase {

    @Test(testName = "Install In A Different Directory")
    public void installInADifferentDirectory(){
        ibuiService.startIBUIInstaller("Latest");
        try {
            installer.clickNext();
            installer.clickNext();
            installer.acceptTerms();
            installer.clickNext();
            installer.clickNext();
            installer.installNewCoordinator();
            installer.clickNext();
            installer.clickNext();
            installer.clickNext();
            installer.clickNext();
            installer.changeInstallationPath(Locations.DIFFERENT_INSTALLATION_DIRECTORY);
            installer.clickNext();
            installer.clickNext();
            installer.browseLicense();
            installer.browseLicenseNavigateToDesktop();
            installer.selectLicense();
            installer.clickNext();
            installer.clickNext();
            installer.cancelReleaseNotes();
            installer.clickFinish();
        } catch (FindFailed e) {
            test.log(Status.ERROR, "Test failed with the following error: " + e.getMessage());
            Assert.fail();
        }
        IbLocations.IB_ROOT = RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\builder", "Folder");
        Assert.assertTrue(ibService.verifyIbServicesRunning(true, true), "Services are not running!!!!");
        Assert.assertEquals(IbLocations.IB_ROOT, Locations.DIFFERENT_INSTALLATION_DIRECTORY, "Installed location " + IbLocations.IB_ROOT + " does not match expected location: "
                + Locations.DIFFERENT_INSTALLATION_DIRECTORY);
        int returnCode = ibService.cleanAndBuild("\"" + IbLocations.IB_ROOT + "\\" + Processes.BUILD_CONSOLE + "\" " + String.format(TestProjects.TEST_PROJ, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Install On An Existing Coordinator")
    public void installOnAnExistingCoordinator(){
        ibuiService.startIBUIInstaller("Latest");
        try {
            installer.clickNext();
            installer.clickNext();
            installer.acceptTerms();
            installer.clickNext();
            installer.clickNext();
            installer.selectCoordinator(WindowsMachines.BABYLON);
            installer.clickNext();
            installer.clickNext();
            installer.clickNext();
            installer.clickNext();
            installer.clickNext();
            installer.cancelReleaseNotes();
            installer.clickFinish();
        } catch (FindFailed e) {
            test.log(Status.ERROR, "Test failed with the following error: " + e.getMessage());
            Assert.fail();
        }
        IbLocations.IB_ROOT = RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\builder", "Folder");
        Assert.assertTrue(ibService.verifyIbServicesRunning(true, false), "Services are not running!!!!");
        Assert.assertEquals(ibService.getCoordinator(), WindowsMachines.BABYLON, "Current coordinator" + ibService.getCoordinator() + " does not match expected coordinator: "
                + WindowsMachines.BABYLON);
        int returnCode = ibService.cleanAndBuild("\"" + IbLocations.IB_ROOT + "\\" + Processes.BUILD_CONSOLE + "\" " + String.format(TestProjects.TEST_PROJ, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "UninstallRB IB")
    public void uninstallIb(){
        ibService.installIB("Latest", IbLicenses.UI_LIC);
        ibuiService.startIBUIInstaller("Latest");
        try {
            installer.clickNext();
            installer.selectUninstall();
            installer.clickNext();
            installer.clickNext();
            installer.clickFinish();
        } catch (FindFailed e) {
            test.log(Status.ERROR, "Test failed with the following error: " + e.getMessage());
            Assert.fail();
        }
        Assert.assertFalse(ibService.verifyIbServicesRunning(true, true), "Services are running!!!! Should be Uninstalled");
    }

    @Test(testName = "Repair IB")
    public void repairIb(){
        ibService.installIB("Latest", IbLicenses.UI_LIC);
        winService.runCommandWaitForFinish("net stop \"" + WindowsServices.AGENT_SERVICE + "\"");
        SystemActions.deleteFilesByPrefix(IbLocations.IB_ROOT, "*.exe");
        ibuiService.startIBUIInstaller("Latest");
        try {
            installer.clickNext();
            installer.clickNext();
            installer.clickNext();
            installer.cancelReleaseNotes();
            installer.clickFinish();
        } catch (FindFailed e) {
            test.log(Status.ERROR, "Test failed with the following error: " + e.getMessage());
            Assert.fail();
        }
        IbLocations.IB_ROOT = RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\builder", "Folder");
        Assert.assertTrue(ibService.verifyIbServicesRunning(true, true), "Services are not running!!!!");
        int returnCode = ibService.cleanAndBuild("\"" + IbLocations.IB_ROOT + "\\" + Processes.BUILD_CONSOLE + "\" " + String.format(TestProjects.TEST_PROJ, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Upgrade IB")
    public void upgradeIb(){
        ibService.installIB("2190", IbLicenses.UI_LIC);
        ibuiService.startIBUIInstaller("Latest");
        try {
            installer.clickNext();
            installer.clickNext();
            installer.clickNext();
            installer.cancelReleaseNotes();
            installer.cancelRemoteUpdate();
            installer.clickFinish();
        } catch (FindFailed e) {
            test.log(Status.ERROR, "Test failed with the following error: " + e.getMessage());
            Assert.fail();
        }
        IbLocations.IB_ROOT = RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\builder", "Folder");
        Assert.assertTrue(ibService.verifyIbServicesRunning(true, true), "Services are not running!!!!");
        int returnCode = ibService.cleanAndBuild("\"" + IbLocations.IB_ROOT + "\\" + Processes.BUILD_CONSOLE + "\" " + String.format(TestProjects.TEST_PROJ, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Upgrade IB")
    public void downgradeIb(){
        ibService.installIB("Latest", IbLicenses.UI_LIC);
        ibuiService.startIBUIInstaller("2190");
        try {
            installer.clickNext();
            installer.clickNext();
            installer.clickNext();
            installer.cancelReleaseNotes();
            installer.cancelRemoteUpdate();
            installer.clickFinish();
        } catch (FindFailed e) {
            test.log(Status.ERROR, "Test failed with the following error: " + e.getMessage());
            Assert.fail();
        }
        IbLocations.IB_ROOT = RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\builder", "Folder");
        Assert.assertTrue(ibService.verifyIbServicesRunning(true, true), "Services are not running!!!!");
        int returnCode = ibService.cleanAndBuild("\"" + IbLocations.IB_ROOT + "\\" + Processes.BUILD_CONSOLE + "\" " + String.format(TestProjects.TEST_PROJ, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Verify Port Changes")
    public void verifyPortChanges(){
        ibuiService.startIBUIInstaller("Latest");
        try {
            installer.clickNext();
            installer.clickNext();
            installer.acceptTerms();
            installer.clickNext();
            installer.clickNext();
            installer.installNewCoordinator();
            installer.clickNext();
            installer.clickNext();
            installer.selectManualHelperPorts();
            installer.clickNext();
            installer.selectManualCoordPort();
            installer.clickNext();
            installer.clickNext();
            installer.clickNext();
            installer.browseLicense();
            installer.browseLicenseNavigateToDesktop();
            installer.selectLicense();
            installer.clickNext();
            installer.clickNext();
            installer.cancelReleaseNotes();
            installer.clickFinish();
        } catch (FindFailed e) {
            test.log(Status.ERROR, "Test failed with the following error: " + e.getMessage());
            Assert.fail();
        }
        IbLocations.IB_ROOT = RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\builder", "Folder");
        Assert.assertTrue(ibService.verifyIbServicesRunning(true, true), "Services are not running!!!!");
        String coordPort = RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\Coordinator", "Port");
        String agentPort = RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\BuildService", "ForcePortNum");
        String helpersPort = RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\Worker", "ForcePortNum");

        Assert.assertEquals(coordPort, InstallationPorts.COORDINATOR_PORT, "Coordinator port found in registry does not match entered port!");
        Assert.assertEquals(agentPort, InstallationPorts.AGENT_PORT, "Agent port found in registry does not match entered port!");
        Assert.assertEquals(helpersPort, InstallationPorts.HELPER_PORT, "Helpers port found in registry does not match entered port!");

        int returnCode = ibService.cleanAndBuild("\"" + IbLocations.IB_ROOT + "\\" + Processes.BUILD_CONSOLE + "\" " + String.format(TestProjects.TEST_PROJ, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }
}
