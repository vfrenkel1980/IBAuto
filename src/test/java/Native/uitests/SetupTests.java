package Native.uitests;

import com.aventstack.extentreports.Status;
import frameworkInfra.testbases.SetupTestBase;
import frameworkInfra.utils.*;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.databases.PostgresJDBC;
import frameworkInfra.utils.parsers.Parser;
import org.sikuli.script.FindFailed;
import org.testng.Assert;
import org.testng.annotations.Test;


import java.io.File;
import java.util.List;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static com.sun.jna.platform.win32.WinReg.HKEY_CURRENT_USER;
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
            installer.uncheckReleaseNotes();
            installer.clickFinish();
        } catch (FindFailed e) {
            test.log(Status.ERROR, "Test failed with the following error: " + e.getMessage());
            Assert.fail();
        }
        IbLocations.IB_ROOT = RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\builder", "Folder");
        Assert.assertTrue(ibService.verifyIbServicesRunning(true, true), "Services are not running!!!!");
        Assert.assertEquals(IbLocations.IB_ROOT, Locations.DIFFERENT_INSTALLATION_DIRECTORY, "Installed location " + IbLocations.IB_ROOT + " does not match expected location: "
                + Locations.DIFFERENT_INSTALLATION_DIRECTORY);
        runBuildAndAssert();
    }

    @Test(enabled = false, testName = "Install On An Existing Coordinator")
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
            installer.uncheckReleaseNotes();
            installer.clickFinish();
        } catch (FindFailed e) {
            test.log(Status.ERROR, "Test failed with the following error: " + e.getMessage());
            Assert.fail();
        }
        IbLocations.IB_ROOT = RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\builder", "Folder");
        Assert.assertTrue(ibService.verifyIbServicesRunning(true, false), "Services are not running!!!!");
        Assert.assertEquals(ibService.getCoordinator(), WindowsMachines.BABYLON, "Current coordinator" + ibService.getCoordinator() + " does not match expected coordinator: "
                + WindowsMachines.BABYLON);
        runBuildAndAssert();
    }

    @Test(enabled = false, testName = "Install Single Use VM")
    public void installSingleUseVM(){
        ibuiService.startIBUIInstaller("Latest");
        try {
            installer.clickNext();
            installer.clickNext();
            installer.acceptTerms();
            installer.clickNext();
            installer.clickCustom();
            installer.selectSingleUseVM();
            installer.clickNext();
            installer.selectCoordinator(WindowsMachines.BABYLON);
            installer.clickNext();
            installer.clickNext();
            installer.clickNext();
            installer.clickNext();
            installer.clickNext();
            installer.uncheckReleaseNotes();
            installer.clickFinish();
        } catch (FindFailed e) {
            test.log(Status.ERROR, "Test failed with the following error: " + e.getMessage());
            Assert.fail();
        }
        Assert.assertFalse(ibService.verifyIbServicesRunning(true, false), "Services are running after SingleUseVM install!!!!");
        winService.runCommandWaitForFinish("net start \"" + WindowsServices.AGENT_SERVICE + "\"");
        Assert.assertTrue(ibService.verifyIbServicesRunning(true, false), "Services are not running!!!!");
        runBuildAndAssert();
    }

    @Test(testName = "Uninstall IB")
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
            installer.uncheckReleaseNotes();
            installer.clickFinish();
        } catch (FindFailed e) {
            test.log(Status.ERROR, "Test failed with the following error: " + e.getMessage());
            Assert.fail();
        }
        IbLocations.IB_ROOT = RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\builder", "Folder");
        Assert.assertTrue(ibService.verifyIbServicesRunning(true, true), "Services are not running!!!!");
        runBuildAndAssert();
    }

    @Test(testName = "Upgrade IB")
    public void upgradeIb(){
        ibService.installIB("2190", IbLicenses.UI_LIC);
        ibuiService.startIBUIInstaller("Latest");
        try {
            installer.clickNext();
            installer.clickNext();
            installer.clickNext();
            installer.uncheckReleaseNotes();
            installer.uncheckRemoteUpdate();
            installer.clickFinish();
        } catch (FindFailed e) {
            test.log(Status.ERROR, "Test failed with the following error: " + e.getMessage());
            Assert.fail();
        }
        IbLocations.IB_ROOT = RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\builder", "Folder");
        Assert.assertTrue(ibService.verifyIbServicesRunning(true, true), "Services are not running!!!!");
        runBuildAndAssert();
    }

    @Test(testName = "Downgrade IB")
    public void downgradeIb(){
        ibService.installIB("Latest", IbLicenses.UI_LIC);
        ibuiService.startIBUIInstaller("2190");
        try {
            installer.clickNext();
            installer.clickNext();
            installer.clickNext();
            installer.uncheckReleaseNotes();
            installer.uncheckRemoteUpdate();
            installer.clickFinish();
        } catch (FindFailed e) {
            test.log(Status.ERROR, "Test failed with the following error: " + e.getMessage());
            Assert.fail();
        }
        IbLocations.IB_ROOT = RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\builder", "Folder");
        Assert.assertTrue(ibService.verifyIbServicesRunning(true, true), "Services are not running!!!!");
        runBuildAndAssert();
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
            installer.uncheckReleaseNotes();
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

        runBuildAndAssert();
    }

    @Test(testName = "Upgrade Pro To Enterprise")
    public void upgradeProToEnterprise(){
        ibService.installIB("Latest", IbLicenses.UI_LIC);
        ibuiService.startEntInstaller("Latest");
        try {
            installer.clickNext();
            installer.clickNext();
            installer.clickNext();
            installer.uncheckLaunchDashboard();
            installer.uncheckCreateEntShortcut();
            installer.uncheckReleaseNotes();
            installer.uncheckRemoteUpdate();
            installer.clickFinish();
        } catch (FindFailed e) {
            test.log(Status.ERROR, "Test failed with the following error: " + e.getMessage());
            Assert.fail();
        }
        Assert.assertTrue(winService.isServiceRunning(WindowsServices.ENTERPRISE_SERVICE), WindowsServices.ENTERPRISE_SERVICE + " is not running");
        Assert.assertTrue(ibService.verifyIbServicesRunning(true, true), "Services are not running!!!!");
        runBuildAndAssert();
        String exitCode = postgresJDBC.getLastValueFromTable("localhost", "ib", "ib", "coordinatordb", "*", "public.coord_build", "status", "id");
        Assert.assertTrue(exitCode.equals("0") , "DB exit code is: " + exitCode);

    }

    @Test(testName = "Downgrade Enterprise To Pro")
    public void downgradeEnterpriseToPro(){
        installEnterprise();
        ibuiService.startEntInstaller("Latest");
        try {
            installer.clickNext();
            installer.selectDowngrade();
            installer.clickNext();
            installer.clickNext();
            installer.clickFinish();
        } catch (FindFailed e) {
            test.log(Status.ERROR, "Test failed with the following error: " + e.getMessage());
            Assert.fail();
        }
        Assert.assertFalse(winService.isServiceRunning(WindowsServices.ENTERPRISE_SERVICE), WindowsServices.ENTERPRISE_SERVICE + " is running, should be stopped");
        Assert.assertTrue(ibService.verifyIbServicesRunning(true, true), "Services are not running!!!!");
        runBuildAndAssert();
    }

    @Test(testName = "Enterprise Clean Installation")
    public void enterpriseCleanInstallation(){
        installEnterprise();
        Assert.assertTrue(winService.isServiceRunning(WindowsServices.ENTERPRISE_SERVICE), WindowsServices.ENTERPRISE_SERVICE + " is running, should be stopped");
        Assert.assertTrue(ibService.verifyIbServicesRunning(true, true), "Services are not running!!!!");
        runBuildAndAssert();
        String exitCode = postgresJDBC.getLastValueFromTable("localhost", "ib", "ib", "coordinatordb", "*", "public.coord_build", "status", "id");
        Assert.assertTrue(exitCode.equals("0") , "DB exit code is: " + exitCode);
    }

    @Test(testName = "Change Enterprise Installation Location And Port")
    public void changeEnterpriseInstallationLocationAndPort(){
        ibuiService.startEntInstaller("Latest");
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
            installer.clickNext();
            installer.changeEntInstallationLocation(Locations.DIFFERENT_ENT_INSTALLATION_DIRECTORY);
            installer.changeDashboardPort();
            installer.clickNext();
            installer.clickNext();
            installer.browseLicense();
            installer.browseLicenseNavigateToDesktop();
            installer.selectLicense();
            installer.clickNext();
            installer.clickNext();
            installer.uncheckReleaseNotes();
            installer.uncheckLaunchDashboard();
            installer.uncheckCreateEntShortcut();
            installer.clickFinish();
        } catch (FindFailed e) {
            test.log(Status.ERROR, "Test failed with the following error: " + e.getMessage());
            Assert.fail();
        }
        Assert.assertTrue(winService.isServiceRunning(WindowsServices.ENTERPRISE_SERVICE), WindowsServices.ENTERPRISE_SERVICE + " is running, should be stopped");
        Assert.assertTrue(ibService.verifyIbServicesRunning(true, true), "Services are not running!!!!");
        Assert.assertEquals(RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\coordinator", RegistryKeys.ENT_INSTALLATION_REG),
                Locations.DIFFERENT_ENT_INSTALLATION_DIRECTORY);
        Parser.doesFileContainString(IbLocations.ENTERPRISE_DIRECTORY + "\\Dashboard\\Apache24\\conf\\httpd.conf", "Listen " + InstallationPorts.DASHBOARD_PORT);
        runBuildAndAssert();
        String exitCode = postgresJDBC.getLastValueFromTable("localhost", "ib", "ib", "coordinatordb", "*", "public.coord_build", "status", "id");
        Assert.assertTrue(exitCode.equals("0") , "DB exit code is: " + exitCode);
    }

    @Test(testName = "Negative Upgrade Pro To Ent With No Ent License")
    public void negativeUpgradeProToEntWithNoEntLicense(){
        ibService.installIB("Latest", IbLicenses.NO_ENT_LIC);
        ibuiService.startEntInstaller("Latest");
        try {
            installer.verifyInvalidLicenseMessage();
            installer.clickExit();
        } catch (FindFailed e) {
            test.log(Status.ERROR, "Test failed with the following error: " + e.getMessage());
            Assert.fail();
        }
    }

    @Test(testName = "Verify Installer Exit Code While Devenv Is Running")
    public void verifyInstallerExitCodeWhileDevenvIsRunning(){
        vsuiService.openVSInstance("15", false, "");
        int exitCode = ibService.installIB("Latest");
        vsuiService.killDriver();
        Assert.assertEquals(exitCode, 2, "Installation finished with exit code different than 2!");
    }

    @Test(testName = "Verify Successful Installer Exit Code")
    public void verifySuccessfulInstallerExitCode(){
        Assert.assertEquals(ibService.installIB("Latest"), 0, "Installation finished with exit code different than 0!");
    }

    @Test(testName = "Verify Wrong Parameter Installer Exit Code")
    public void verifyWrongParameterInstallerExitCode(){
        String installationFile = ibService.getIbConsoleInstallation("Latest");
        int exitCode = winService.runCommandWaitForFinish(installationFile + " /someparam");
        Assert.assertEquals(exitCode, 4, "Installation finished with exit code different than 4!");
    }

    @Test(testName = "Verify Installation Logs Created")
    public void verifyInstallationLogsCreated(){
        String filename = "";
        String installLogsFolder = System.getProperty("java.io.tmpdir") + "IB_Setup_Logs";
        SystemActions.deleteFilesByPrefix(installLogsFolder, "IncrediBuild_Setup");
        ibService.installIB("Latest", IbLicenses.UI_LIC);
        List<String> files = SystemActions.getAllFilesInDirectory(installLogsFolder);
        for (String file : files) {
            if (file.contains("IncrediBuild_Setup"))
                filename = file;
        }
        Assert.assertTrue(new File(installLogsFolder, filename).exists(), "Something went wrong and installation log s not created");
    }

    @Test(testName = "Verify Pro Uninstall Leftovers")
    public void verifyProUninstallLeftovers() {
        ibService.installIB("Latest", IbLicenses.UI_LIC);
        runBuildAndAssert();
        ibService.uninstallIB("Latest");
        Assert.assertFalse(RegistryService.doesKeyExist(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT),"HKLM wasn't removed in verifyProUninstallLeftovers test");
        Assert.assertFalse(RegistryService.doesKeyExist(HKEY_CURRENT_USER, Locations.IB_HKCU_REG_ROOT), "HKCU wasn't removed in verifyProUninstallLeftovers test");
        Assert.assertFalse(SystemActions.doesFileExist(IbLocations.IB_ROOT), "IB root folder wasn't removed in verifyProUninstallLeftovers test");
        Assert.assertFalse(SystemActions.doesFileExist(IbLocations.IB_SHORTCUTS), "Start menu shortcuts weren't removed in verifyProUninstallLeftovers test");
    }

    @Test(testName = "Verify Enterprise Uninstall Leftovers ")
    public void verifyEnterpriseUninstallLeftovers() {
        installEnterprise();
        runBuildAndAssert();
        ibService.uninstallIB("Latest");
        Assert.assertFalse(RegistryService.doesKeyExist(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT),"HKLM wasn't removed in verifyEnterpriseUninstallLeftovers test");
        Assert.assertFalse(RegistryService.doesKeyExist(HKEY_CURRENT_USER, Locations.IB_HKCU_REG_ROOT), "HKCU wasn't removed in verifyEnterpriseUninstallLeftovers test");
        Assert.assertFalse(SystemActions.doesFileExist(IbLocations.IB_ROOT), "IB root folder wasn't removed in verifyEnterpriseUninstallLeftovers test");
        Assert.assertFalse(SystemActions.doesFileExist(IbLocations.ENTERPRISE_DIRECTORY), "IB Statistic folder wasn't removed from " + IbLocations.ENTERPRISE_DIRECTORY);
        Assert.assertFalse(SystemActions.doesFileExist(IbLocations.IB_SHORTCUTS), "Start menu shortcuts weren't removed in verifyEnterpriseUninstallLeftovers test");
    }

    @Test(testName = "Verify VS_Integrated Parameter")
    public void verifyVSIntegratedParameter() {
        boolean isRunning = false;
        String process = ibService.getIbConsoleInstallation("Latest");
        winService.runCommandDontWaitForTermination(String.format(StaticDataProvider.WindowsCommands.IB_INSTALL_COMMAND + " /vs_integrated", process));
        while (winService.isProcessRunning(process.substring(process.lastIndexOf("\\") + 1)))
            if (winService.isProcessRunning("vsixinstaller.exe"))
                isRunning = true;

        Assert.assertFalse(isRunning, "VSIXIstaller running, shouldn't be");
    }

    /*-------------------------------METHODS-------------------------------*/

    private void runBuildAndAssert(){
        int returnCode = ibService.cleanAndBuild("\"" + IbLocations.IB_ROOT + "\\" + Processes.BUILD_CONSOLE + "\" " + String.format(TestProjects.TEST_PROJ, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }
    private void installEnterprise() {
        ibuiService.startEntInstaller("Latest");
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
            installer.clickNext();
            installer.clickNext();
            installer.clickNext();
            installer.browseLicense();
            installer.browseLicenseNavigateToDesktop();
            installer.selectLicense();
            installer.clickNext();
            installer.clickNext();
            installer.uncheckLaunchDashboard();
            installer.uncheckCreateEntShortcut();
            installer.uncheckReleaseNotes();
            installer.clickFinish();
        } catch (FindFailed e) {
            test.log(Status.ERROR, "Test failed with the following error: " + e.getMessage());
            Assert.fail();
        }
    }
}
