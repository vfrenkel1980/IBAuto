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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static com.sun.jna.platform.win32.WinReg.HKEY_CURRENT_USER;
import static frameworkInfra.Listeners.SuiteListener.test;

/**
 * @brief  <a href="https://incredibuild.atlassian.net/wiki/spaces/ITK/pages/784269325/Setup+Installation"></b>Setup & Installation</b></a> tests</b>
 * @details Run on UI Automation (HOST-4)
 */
public class SetupTests extends SetupTestBase {

    /**
     * @test Install in a different directory
     * @pre{ Uninstall previously installed IB versions }
     * @steps{
     * - Start the IB ui installer
     * - Get registry key
     * - Verify if incredibuild services are running
     * - Verify if installed location match expected location
     * - Perform IB clean and build
     * }
     * @result{ - Incredibuild should be installed.
     * - Service should be running. }
     */
    @Test(testName = "Install In A Different Directory")
    public void installInADifferentDirectory() {
        ibuiService.startIBUIInstaller(IB_VERSION);
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

    /**
     * @test Install on an Existing Coordinator
     * @pre{ }
     * @steps{
     * - Start the IB ui installer
     * - Get registry key
     * - Run clean and build
     * - Verify if incredibuild services are running
     * - Get the coordinator of the current ib agent
     * - Perform IB clean and build
     * }
     * @result{ - Incredibuild should be installed.
     * - Service should be running.}
     */
    @Test(enabled = false, testName = "Install On An Existing Coordinator")
    public void installOnAnExistingCoordinator() {
        ibuiService.startIBUIInstaller(IB_VERSION);
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

    /**
     * @test Install Single Use VM
     * @steps{
     * - Start the IB ui installer
     * - Get registry key
     * - Verify if incredibuild services are running
     * - Run IB Agent service
     * - Verify if incredibuild services are running
     * - Perform IB clean and build
     * }
     * @result{ - Return code}
     */
    @Test(enabled = false, testName = "Install Single Use VM")
    public void installSingleUseVM() {
        ibuiService.startIBUIInstaller(IB_VERSION);
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

    /**
     * @test Uninstall IB
     * @pre{ }
     * @steps{
     * - Install incredibuild
     * - Start the IB ui installer
     * - Select Uninstall option
     * - Verify if incredibuild services are running
     * }
     * @result{ - All Folders/RegValues/Files are corresponds to IncrediBuild installation should be deleted.
     * - IB add-in for VS should be removed from VS IDE.}
     */
    @Test(testName = "Uninstall IB")
    public void uninstallIb() {
        ibService.installIB(IB_VERSION, IbLicenses.UI_LIC);
        ibuiService.startIBUIInstaller(IB_VERSION);
        try {
            installer.clickNext();
            installer.selectUninstall();
            installer.clickNext();
            installer.clickNext();
            SystemActions.sleep(300);
            installer.clickFinish();
        } catch (FindFailed e) {
            test.log(Status.ERROR, "Test failed with the following error: " + e.getMessage());
            Assert.fail();
        }
        Assert.assertFalse(ibService.verifyIbServicesRunning(true, true), "Services are running!!!! Should be Uninstalled");
    }

    /**
     * @test Repair IB
     * @pre{ Install last IB version on your testing machine }
     * @steps{
     * - Install incredibuild
     * - Run IncrediBuild_Agent service
     * - Delete all files in folder with a predefined prefix
     * - Start the IB ui installer
     * - Removing Release notes CB
     * - Get registry key
     * - Verify if incredibuild services are running
     * - Perform IB clean and build
     * }
     * @result{ - Incredibuild should be installed.
     * - Service should be running.
     * - Tryicon should appear.
     * - Incredibuild add-in for VS should be installed.
     * }
     */
    @Test(testName = "Repair IB")
    public void repairIb() {
        ibService.installIB(IB_VERSION, IbLicenses.UI_LIC);
        winService.runCommandWaitForFinish("net stop \"" + WindowsServices.AGENT_SERVICE + "\"");
        SystemActions.deleteFilesByPrefix(IbLocations.IB_ROOT, "*.exe");
        ibuiService.startIBUIInstaller(IB_VERSION);
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

    /**
     * @test Upgrade IB
     * @pre{ Run Setup.exe and choose to save IBSetupConsole.exe file for next silent installation from command prompt... }
     * @steps{
     * - Install incredibuild version 2190
     * - Start the IB ui installer
     * - Removing Release notes CB
     * - Removing Remote update CB
     * - Get registry key
     * - Verify if incredibuild services are running
     * - Perform IB clean and build
     * }
     * @result{ - Incredibuild version should be updated.
     * - Service should be running.
     * - Tryicon should appear.
     * - Incredibuild add-in for VS should be installed.
     * }
     */
    @Test(testName = "Upgrade IB")
    public void upgradeIb() {
        ibService.installIB("2190", IbLicenses.UI_LIC);
        ibuiService.startIBUIInstaller(IB_VERSION);
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

    /**
     * @test Downgrade IB
     * @pre{ }
     * @steps{
     * - Install incredibuild
     * - Start the IB ui installer 2190
     * - Removing Release notes CB
     * - Removing Remote update CB
     * - Get Registry Key
     * - Verify if incredibuild services are running
     * - Run clean and build
     * }
     * @result{ - Return code}
     */
    @Test(testName = "Downgrade IB")
    public void downgradeIb() {
        ibService.installIB(IB_VERSION, IbLicenses.UI_LIC);
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

    /**
     * @test Verify Port Changes
     * @pre{ }
     * @steps{
     * - Start the IB ui installer
     * - Selecting "install new coordinator"
     * - Selecting manual ports
     * - Selecting manual coordinator ports
     * - Browse License BTN
     * - Removing Release notes CB
     * - Get registry key
     * - Verify if incredibuild services are running
     * - Get registry key Coordinator port
     * - Get registry key Agent port
     * - Get registry key Helper port
     * - Run clean and build
     * }
     * @result{ - Return code}
     */
    @Test(testName = "Verify Port Changes")
    public void verifyPortChanges() {
        ibuiService.startIBUIInstaller(IB_VERSION);
        try {
            installer.clickNext();
            installer.clickNext();
            installer.acceptTerms();
            installer.clickNext();
            installer.clickNext();
            installer.installNewCoordinator();
            installer.clickNext();
            installer.clickNext();
            installer.selectManualAgentPorts(InstallationPorts.AGENT_PORT);
            installer.selectManualHelperPorts(InstallationPorts.HELPER_PORT);
            installer.clickNext();
            installer.selectManualCoordPort(InstallationPorts.COORDINATOR_PORT);
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

    /**
     * @test Upgrade Pro to Enterprise
     * @pre{ }
     * @steps{
     * - Install incredibuild
     * - Start the IB ui installer
     * - Removing "Launch Dashboard" CB
     * - Removing "Create Ent. shortcut" CB
     * - Removing Remote update CB
     * - Run clean and build
     * - Get last value from postgresJDBC table
     * }
     * @result{ - Return code}
     */
    @Test(testName = "Upgrade Pro To Enterprise")
    public void upgradeProToEnterprise() {
        ibService.installIB(IB_VERSION, IbLicenses.UI_LIC);
        ibuiService.startEntInstaller(IB_VERSION);
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
        Assert.assertTrue(exitCode.equals("0"), "DB exit code is: " + exitCode);

    }

    /**
     * @test Downgrade Enterprise to Pro
     * @pre{ }
     * @steps{
     * - Install Enterprise IB
     * - Start the IB ui installer
     * - Selecting Downgrade to Incredibuild Pro Edition option
     * - Verify if Incredibuild Dashboard Server is running
     * - Verify if incredibuild services are running
     * - Run clean and build
     * }
     * @result{ - Return code}
     */
    @Test(testName = "Downgrade Enterprise To Pro")
    public void downgradeEnterpriseToPro() {
        installEnterprise();
        ibuiService.startEntInstaller(IB_VERSION);
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

    /**
     * @test Enterprise Clean Installation
     * @pre{ }
     * @steps{
     * - Install Enterprise IB
     * - Verify if Incredibuild dashboard server is running
     * - Verify if incredibuild services are running
     * - Run clean and build
     * - Get last value from postgresJDBC table
     * }
     * @result{ }
     */
    @Test(testName = "Enterprise Clean Installation")
    public void enterpriseCleanInstallation() {
        installEnterprise();
        Assert.assertTrue(winService.isServiceRunning(WindowsServices.ENTERPRISE_SERVICE), WindowsServices.ENTERPRISE_SERVICE + " is running, should be stopped");
        Assert.assertTrue(ibService.verifyIbServicesRunning(true, true), "Services are not running!!!!");
        runBuildAndAssert();
        String exitCode = postgresJDBC.getLastValueFromTable("localhost", "ib", "ib", "coordinatordb", "*", "public.coord_build", "status", "id");
        Assert.assertTrue(exitCode.equals("0"), "DB exit code is: " + exitCode);
    }

    /**
     * @test Change Enterprise installation location and Port
     * @pre{ }
     * @steps{
     * - Start the IB ui installer
     * - Selecting "install new coordinator"
     * - Changing Enterprise installation path to different Enterprise installation directory
     * - Selecting manual dashboard ports
     * - Browse license
     * - Removing Release notes CB
     * - Removing "Launch Dashboard" CB
     * - Removing "Create Ent. shortcut" CB
     * - Verify if Incredibuild dashboard server is running
     * - Verify if incredibuild services are running
     * - Get registry key
     * - Run clean and build
     * - Get last value from postgresJDBC table
     * }
     * @result{ }
     */
    @Test(testName = "Change Enterprise Installation Location And Port")
    public void changeEnterpriseInstallationLocationAndPort() {
        ibuiService.startEntInstaller(IB_VERSION);
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
           // installer.changeEntInstallationLocation(Locations.DIFFERENT_ENT_INSTALLATION_DIRECTORY);
           // installer.changeDashboardPort();
            installer.clickNext();
            installer.clickNext();
            SystemActions.sleep(1200);
            installer.browseLicense();
            installer.browseLicenseNavigateToDesktop();
            installer.selectLicense();
            installer.clickNext();
            installer.clickNext();
            installer.uncheckReleaseNotes();
            SystemActions.sleep(5);
            installer.uncheckLaunchDashboard();
            SystemActions.sleep(5);
            installer.uncheckCreateEntShortcut();
            installer.clickFinish();
        } catch (FindFailed e) {
            test.log(Status.ERROR, "Test failed with the following error: " + e.getMessage());
            Assert.fail();
        }
        Assert.assertTrue(winService.isServiceRunning(WindowsServices.ENTERPRISE_SERVICE), WindowsServices.ENTERPRISE_SERVICE + " is running, should be stopped");
        Assert.assertTrue(ibService.verifyIbServicesRunning(true, true), "Services are not running!!!!");
       // Assert.assertEquals(RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\coordinator", RegistryKeys.ENT_INSTALLATION_REG),
        //        Locations.DIFFERENT_ENT_INSTALLATION_DIRECTORY);
        //Parser.doesFileContainString(IbLocations.ENTERPRISE_DIRECTORY + "\\Dashboard\\Apache24\\conf\\httpd.conf", "Listen " + InstallationPorts.DASHBOARD_PORT);
        runBuildAndAssert();
        String exitCode = postgresJDBC.getLastValueFromTable("localhost", "ib", "ib", "coordinatordb", "*", "public.coord_build", "status", "id");
        Assert.assertTrue(exitCode.equals("0"), "DB exit code is: " + exitCode);
    }

    /**
     * @test Negative upgrade Pro to Ent with No Ent license
     * @pre{ }
     * @steps{
     *  - Install incredibuild
     *  - Start the IB ui installer
     *  - Verify invalid license message
     * }
     * @result{ }
     */
    @Test(testName = "Negative Upgrade Pro To Ent With No Ent License")
    public void negativeUpgradeProToEntWithNoEntLicense() {
        ibService.installIB(IB_VERSION, IbLicenses.NO_ENT_LIC);
        ibuiService.startEntInstaller(IB_VERSION);
        try {
            installer.verifyInvalidLicenseMessage();
            installer.clickExit();
        } catch (FindFailed e) {
            test.log(Status.ERROR, "Test failed with the following error: " + e.getMessage());
            Assert.fail();
        }
    }

    /**
     * @test Verify Installer Exit Code while Devenv is Running
     * @pre{ }
     * @steps{
     * - Open VS instance
     * - Install incredibuild
     * - Killing process
     * }
     * @result{ - Return code }
     */
    @Test(testName = "Verify Installer Exit Code While Devenv Is Running")
    public void verifyInstallerExitCodeWhileDevenvIsRunning() {
        vsuiService.openVSInstance("15", false, "");
        SystemActions.sleep(10);
        int exitCode = ibService.installIB(IB_VERSION);
        vsuiService.killDriver();
        Assert.assertEquals(exitCode, 2, "Installation finished with exit code different than 2!");
    }
    /**
     * @test Verify Successful Installer Exit Code
     * @pre{ }
     * @steps{
     * - Install incredibuild
     * }
     * @result{ - Return code }
     */
    @Test(testName = "Verify Successful Installer Exit Code")
    public void verifySuccessfulInstallerExitCode() {
        Assert.assertEquals(ibService.installIB(IB_VERSION), 0, "Installation finished with exit code different than 0!");
    }
    /**
     * @test Verify Wrong Parameter Installer Exit Code
     * @pre{ }
     * @steps{
     * - Get the console installation exe from directory
     * - Run installation file
     * }
     * @result{ - Return code }
     */
    @Test(testName = "Verify Wrong Parameter Installer Exit Code")
    public void verifyWrongParameterInstallerExitCode() {
        String installationFile = ibService.getIbConsoleInstallation(IB_VERSION);
        int exitCode = winService.runCommandWaitForFinish(installationFile + " /someparam");
        Assert.assertEquals(exitCode, 4, "Installation finished with exit code different than 4!");
    }
    /**
     * @test Verify Installation Logs Created
     * @pre{ }
     * @steps{
     * - Gets the system property indicated by the specified key.
     * - Delete all files in folder with a predefined prefix
     * - Install incredibuild
     * - Get list all file in the required directory
     * }
     * @result{ - Return file }
     */
    @Test(testName = "Verify Installation Logs Created")
    public void verifyInstallationLogsCreated() {
        String filename = "";
        String installLogsFolder = System.getProperty("java.io.tmpdir") + "IB_Setup_Logs";
        SystemActions.deleteFilesByPrefix(installLogsFolder, "IncrediBuild_Setup");
        ibService.installIB(IB_VERSION, IbLicenses.UI_LIC);
        List<String> files = SystemActions.getAllFilesInDirectory(installLogsFolder);
        for (String file : files) {
            if (file.contains("IncrediBuild_Setup"))
                filename = file;
        }
        Assert.assertTrue(new File(installLogsFolder, filename).exists(), "Something went wrong and installation log s not created");
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
    /**
     * @test Verify Enterprise Uninstall Leftovers
     * @pre{ }
     * @steps{
     * - Install incredibuild
     * - Run clean and build
     * - Uninstall IB using command line
     * - Check if a HKEY key exist
     * - Check if a HKEY key exist
     * - Check if a file exist
     * - Check if a file exist
     * - Check if a file exist
     * }
     * @result{ - Return file }
     */
    @Test(testName = "Verify Enterprise Uninstall Leftovers ")
    public void verifyEnterpriseUninstallLeftovers() {
        installEnterprise();
        runBuildAndAssert();
        ibService.uninstallIB(IB_VERSION);
        Assert.assertFalse(RegistryService.doesKeyExist(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT), "HKLM wasn't removed in verifyEnterpriseUninstallLeftovers test");
        Assert.assertFalse(RegistryService.doesKeyExist(HKEY_CURRENT_USER, Locations.IB_HKCU_REG_ROOT), "HKCU wasn't removed in verifyEnterpriseUninstallLeftovers test");
        Assert.assertFalse(SystemActions.doesFileExist(IbLocations.IB_ROOT), "IB root folder wasn't removed in verifyEnterpriseUninstallLeftovers test");
        Assert.assertFalse(SystemActions.doesFileExist(IbLocations.ENTERPRISE_DIRECTORY), "IB Statistic folder wasn't removed from " + IbLocations.ENTERPRISE_DIRECTORY);
        Assert.assertFalse(SystemActions.doesFileExist(IbLocations.IB_SHORTCUTS), "Start menu shortcuts weren't removed in verifyEnterpriseUninstallLeftovers test");
    }
    /**
     * @test Verify VS_Integrated Parameter
     * @pre{ }
     * @steps{
     * - Get the console installation exe from directory
     * - Run windows IB_INSTALL_COMMAND command
     * - Run vsixinstaller process
     * }
     * @result{ }
     */
    @Test(testName = "Verify VS_Integrated Parameter")
    public void verifyVSIntegratedParameter() {
        boolean isRunning = false;
        String process = ibService.getIbConsoleInstallation(IB_VERSION);
        winService.runCommandDontWaitForTermination(String.format(StaticDataProvider.WindowsCommands.IB_INSTALL_COMMAND + " /vs_integrated", process));
        while (winService.isProcessRunning(process.substring(process.lastIndexOf("\\") + 1)))
            if (winService.isProcessRunning("vsixinstaller.exe"))
                isRunning = true;

        Assert.assertFalse(isRunning, "VSIXIstaller running, shouldn't be");
    }
    /**
     * @test Verify Console Installation Port Flags
     * @pre{ }
     * @steps{
     * - Get the console installation exe from directory
     * - Run windows IB_INSTALL_COMMAND command
     * - Get registry key of Port
     * - Get registry key of ForcePortNum
     * - Get registry key of ForcePortNum
     * }
     * @result{  }
     */
    @Test(testName = "Verify Console Installation Port Flags")
    public void verifyConsoleInstallationPortFlags() {
        String process = ibService.getIbConsoleInstallation(IB_VERSION);
        winService.runCommandWaitForFinish(String.format(StaticDataProvider.WindowsCommands.IB_INSTALL_COMMAND + " /AGENT:SERVICEPORT=25000" +
                " /AGENT:HELPERPORT=25001 /COORD:SERVICEPORT=25002", process));
        Assert.assertEquals(RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\Coordinator", RegistryKeys.COORD_PORT), "25002");
        Assert.assertEquals(RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\BuildService", RegistryKeys.SERVICE_PORT), "25000");
        Assert.assertEquals(RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\Worker", RegistryKeys.SERVICE_PORT), "25001");
    }
    /**
     * @test Verify Console Installation Cache Flags
     * @pre{ }
     * @steps{
     * - Get the console installation exe from directory
     * - Run windows IB_INSTALL_COMMAND command
     * - Get registry key of Size
     * }
     * @result{  }
     */
    @Test(testName = "Verify Console Installation Cache Flag")
    public void verifyConsoleInstallationCacheFlag() {
        String process = ibService.getIbConsoleInstallation(IB_VERSION);
        winService.runCommandWaitForFinish(String.format(StaticDataProvider.WindowsCommands.IB_INSTALL_COMMAND + " /AGENT:FILECACHE=1024", process));
        Assert.assertEquals(RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\Shadow", RegistryKeys.CACHE_SIZE), "1024");
    }
    /**
     * @test Install Agent Only
     * @pre{ }
     * @steps{
     * - Get the console installation exe from directory
     * - Run windows IB_INSTALL_NO_PARAMS command
     * - Verify if IncrediBuild_Agent service running
     * - Verify if IncrediBuild_Coordinator service running
     * }
     * @result{  }
     */
    @Test(testName = "Install Agent Only")
    public void InstallAgentOnly() {
        String process = ibService.getIbConsoleInstallation(IB_VERSION);
        winService.runCommandWaitForFinish(String.format(WindowsCommands.IB_INSTALL_NO_PARAMS + " /Components=agent", process));
        Assert.assertTrue(winService.isServiceRunning(WindowsServices.AGENT_SERVICE), "Agent service is not running, agent installed?");
        Assert.assertFalse(winService.isServiceRunning(WindowsServices.COORD_SERVICE), "Coord service is running, is coordinator installed?");
    }
    /**
     * @test Install Coordinator Only
     * @pre{ }
     * @steps{
     * - Get the console installation exe from directory
     * - Run windows IB_INSTALL_NO_PARAMS command
     * - Verify if IncrediBuild_Coordinator service running
     * - Verify if IncrediBuild_Agent service running
     * }
     * @result{  }
     */
    @Test(testName = "Install Coordinator Only")
    public void InstallCoordinatorOnly() {
        String process = ibService.getIbConsoleInstallation(IB_VERSION);
        winService.runCommandWaitForFinish(String.format(WindowsCommands.IB_INSTALL_NO_PARAMS + " /Components=Coordinator", process));
        Assert.assertTrue(winService.isServiceRunning(WindowsServices.COORD_SERVICE), "Coord service is not running, Coord installed?");
        Assert.assertFalse(winService.isServiceRunning(WindowsServices.AGENT_SERVICE), "Agent is running, is agent installed?");
    }

    /**
     * @test Verify all IB binaries (.exe .dll) with sha1 sha256 signatures in installation folder recursivelly
     * @pre{ }
     * @steps{
     * - Install IB;
     * - Get list of all files in installation folder;
     * }
     * @result{
     * - All dll and exe IB files are signed with both sha1 and sha256 signatures except Ignore IB binaries list.
     * }
     */
    @Test(testName = "Verify Binaries Signatures")
    public void verifyBinariesSignatures() {
        ibService.installIB(IB_VERSION);
        File filePath = new File(StaticDataProvider.IbLocations.IB_ROOT);
        Collection<File> allFiles = Parser.findFiles(filePath, ".exe");
        allFiles.addAll(Parser.findFiles(filePath, ".dll"));
        ArrayList<String> ignoreList = winService.textFileToList(Locations.IGNORE_IB_BINARIES_LIST);
        int exit =-1;
        for (File file : allFiles) {
            int curExit = -1;
            if (!ignoreList.contains(file.getName())) {
                curExit = winService.runCommandWaitForFinish(Locations.SIGNTOOL + " verify /pa /ds 0 \"" + file.getAbsolutePath() + "\"");
                if(curExit!=0){
                    test.log(Status.ERROR,  file.getName() + " is not signed with sha256 signature");
                    exit=curExit;
                }
            }
        }
        Assert.assertTrue(exit==-1,"Not all IB binaries are signed");
    }
    /*-------------------------------METHODS-------------------------------*/

    private void runBuildAndAssert() {
        int returnCode = ibService.cleanAndBuild("\"" + IbLocations.IB_ROOT + "\\" + Processes.BUILD_CONSOLE + "\" " + String.format(TestProjects.TEST_PROJ, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    private void installEnterprise() {
        ibuiService.startEntInstaller(IB_VERSION);
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
}//end of SetupTest class
