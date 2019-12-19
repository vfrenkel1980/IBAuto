package ibInfra.ibUIService;

import com.amazonaws.services.s3.model.InstructionFileId;
import com.aventstack.extentreports.Status;
import frameworkInfra.sikuli.sikulimapping.CoordMonitor.CoordMonitor;
import frameworkInfra.sikuli.sikulimapping.IBInstaller.IBInstaller;
import frameworkInfra.sikuli.sikulimapping.IBMonitor.Monitor;
import frameworkInfra.sikuli.sikulimapping.IBSettings.IBSettings;
import frameworkInfra.sikuli.sikulimapping.IBStatusBars.IBStatusBars;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import ibInfra.windowscl.WindowsService;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.Assert;

import java.io.File;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static frameworkInfra.Listeners.SuiteListener.test;

public class IBUIService implements IIBUIService {

    private WindowsService winService = new WindowsService();
    private Screen screen = new Screen();

    /**
     * start the IB ui installer
     *
     * @param version version to install
     */
    @Override
    public void startIBUIInstaller(String version) {
        String installationFile = getIbSetupInstallation(version);
        winService.runCommandDontWaitForTermination(installationFile);
    }

    /**
     * get the path to the installer
     *
     * @param version version to get
     * @return full path to the installer
     */
    @Override
    public String getIbSetupInstallation(String version) {
        File folder = new File(Locations.NETWORK_IB_INSTALLATIONS + version);
        File[] listOfFiles = folder.listFiles();
        String fileName = "";

        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile() && listOfFile.getName().contains("ibsetup") && !listOfFile.getName().contains("console")) {
                fileName = listOfFile.getAbsolutePath();
            }
        }
        return fileName;
    }

    /**
     * get the path to the installer
     *
     * @param version version to get
     * @return full path to the installer
     */
    @Override
    public String getEntInstallation(String version) {
        File folder = new File(Locations.NETWORK_IB_INSTALLATIONS + version);
        File[] listOfFiles = folder.listFiles();
        String fileName = "";

        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().contains("ibenterprise")) {
                fileName = file.getAbsolutePath();
            }
        }
        if (fileName.equals(""))
            test.log(Status.WARNING, "Ent installation file is missing from folder");
        return fileName;
    }

    /**
     * start the IB Ent installer
     *
     * @param version version to install
     */
    @Override
    public void startEntInstaller(String version) {
        String installationFile = getEntInstallation(version);
        winService.runCommandDontWaitForTermination(installationFile);
    }

    /**
     * sub class for performing UI actions on the installer
     */
    public class Installer implements IInstaller {

        @Override
        public void clickNext()  {
            test.log(Status.INFO, "Clicking Next");
            try {
                screen.wait(IBInstaller.NextBTN.similar((float) 0.8), 50).click();
            }
            catch (FindFailed findFailed){
                test.log(Status.WARNING, "Failed to click to Next button : " + findFailed.getMessage());
                Assert.fail();
            }
        }

        @Override
        public void acceptTerms() throws FindFailed {
            test.log(Status.INFO, "Clicking Accept terms");
            screen.wait(IBInstaller.TermsUncheckCB.similar((float) 0.5), 5).click();
        }

        @Override
        public void clickFinish() throws FindFailed {
            try {
                test.log(Status.INFO, "Clicking Finish");
                screen.wait(IBInstaller.FinishBTN.similar((float) 0.8), 40).click();
            }
            catch (FindFailed findFailed){
                test.log(Status.WARNING, "Failed to click to Finish button : " + findFailed.getMessage());
                Assert.fail();
            }
        }

        @Override
        public void uncheckReleaseNotes() throws FindFailed {
            test.log(Status.INFO, "Removing Release notes CB");
            screen.wait(IBInstaller.ReleaseNotesUncheckCB.similar((float) 0.8), 10000).click();
        }

        @Override
        public void uncheckRemoteUpdate() throws FindFailed {
            test.log(Status.INFO, "Removing Remote update CB");
            screen.wait(IBInstaller.UpdateOtherComputersCB.similar((float) 0.8), 5).click();
        }

        @Override
        public void installNewCoordinator() throws FindFailed {
            test.log(Status.INFO, "Selecting \"install new coordinator\"");
            screen.wait(IBInstaller.NewCoordinatorRB.similar((float) 0.5), 5).click();
        }

        @Override
        public void changeInstallationPath(String path) throws FindFailed {
            test.log(Status.INFO, "Changing installation path to: " + path);
            screen.wait(IBInstaller.InstallationPathTB.similar((float) 0.5), 5).click();
            screen.type(path);
        }

        @Override
        public void uncheckEnvVar() throws FindFailed {
            test.log(Status.INFO, "Removing Env Vars CB");
            screen.wait(IBInstaller.EnvVarCB.similar((float) 0.5), 5).click();
        }

        @Override
        public void browseLicense() throws FindFailed {
            test.log(Status.INFO, "Clicking on browse license");
            screen.wait(IBInstaller.BrowseLicenseBTN.similar((float) 0.5), 10000).click();
        }

        @Override
        public void browseLicenseNavigateToDesktop() throws FindFailed {
            test.log(Status.INFO, "Clicking on Desktop tab");
            screen.wait(IBInstaller.DesktopTabBTN.similar((float) 0.8), 60).click();
        }

        @Override
        public void selectLicense() throws FindFailed {
            test.log(Status.INFO, "Selecting license");
            screen.wait(IBInstaller.LicenseFile.similar((float) 0.7), 5).doubleClick();
            screen.wait(IBInstaller.LicenseLoadedOKBTN.similar((float) 0.5), 10).click();
            test.log(Status.INFO, "License selected");
        }

        @Override
        public void selectCoordinator(String coordName) throws FindFailed {
            test.log(Status.INFO, "Selecting existing coordinator");
            screen.wait(IBInstaller.CoordinatorNameTB.similar((float) 0.5), 5).click();
            screen.type(coordName);
            SystemActions.sleep(2);
        }

        @Override
        public void selectUninstall()  {
            test.log(Status.INFO, "Selecting uninstall");
            try {
                screen.wait(IBInstaller.UninstallRB.similar((float) 0.7), 300).click();
            }
            catch (FindFailed findFailed){
                test.log(Status.WARNING, "Uninstall fail: " + findFailed.getMessage());
                Assert.fail();
                }
        }

        @Override
        public void selectManualHelperPorts() throws FindFailed {
            test.log(Status.INFO, "Selecting manual ports");
            screen.wait(IBInstaller.ManualPortSelectionRB.similar((float) 0.7), 5).click();
            screen.wait(IBInstaller.AgentServicePortTB.similar((float) 0.7), 5).click();
            screen.wait(IBInstaller.AgentServicePortTB.similar((float) 0.7), 5).type(InstallationPorts.AGENT_PORT);
            screen.wait(IBInstaller.HelpersPortTB.similar((float) 0.7), 5).click();
            screen.wait(IBInstaller.HelpersPortTB.similar((float) 0.4), 5).type(InstallationPorts.HELPER_PORT);
        }

        @Override
        public void selectManualCoordPort() throws FindFailed {
            test.log(Status.INFO, "Selecting manual coordinator ports");
            screen.wait(IBInstaller.CoordinatorPortTB.similar((float) 0.9), 5).click();
            screen.wait(IBInstaller.CoordinatorPortTB.similar((float) 0.4), 5).type(InstallationPorts.COORDINATOR_PORT);
        }

        @Override
        public void uncheckLaunchDashboard() throws FindFailed {
            test.log(Status.INFO, "Removing \"Launch Dashboard\" CB");
            screen.wait(IBInstaller.LaunchDashboardCB.similar((float) 0.7), 6000).click();
        }

        @Override
        public void uncheckCreateEntShortcut() throws FindFailed {
            test.log(Status.INFO, "Removing \"Create Ent. shortcut\" CB");
            screen.wait(IBInstaller.EnterpriseShortcutCB.similar((float) 0.7), 5).click();
        }

        @Override
        public void selectDowngrade() throws FindFailed {
            test.log(Status.INFO, "Selecting downgrade option");
            screen.wait(IBInstaller.DowngradeToProRB.similar((float) 0.7), 5).click();
        }

        @Override
        public void changeDashboardPort() throws FindFailed {
            test.log(Status.INFO, "Selecting manual dashboard ports");
            screen.wait(IBInstaller.DashboardPortTB.similar((float) 0.9), 5).click();
            screen.wait(IBInstaller.DashboardPortTB.similar((float) 0.4), 5).type(InstallationPorts.DASHBOARD_PORT);
        }

        @Override
        public void changeEntInstallationLocation(String path) throws FindFailed {
            test.log(Status.INFO, "Changing Enterprise installation path to: " + path);
            screen.wait(IBInstaller.EntInstallationLocationTB.similar((float) 0.5), 5).click();
            screen.type(path);
        }

        @Override
        public void verifyInvalidLicenseMessage() throws FindFailed {
            test.log(Status.INFO, "Validating Invalid License Message");
            screen.wait(IBInstaller.InvalidLicenseMessage.similar((float) 0.5), 600);
        }

        @Override
        public void clickExit() throws FindFailed {
            test.log(Status.INFO, "Clicking Exit");
            screen.wait(IBInstaller.ExitBtn.similar((float) 0.7), 5).click();
        }

        @Override
        public void clickCustom() throws FindFailed {
            test.log(Status.INFO, "Clicking Custom");
            screen.wait(IBInstaller.CustomTab.similar((float) 0.7), 5).click();
        }

        @Override
        public void selectSingleUseVM() throws FindFailed {
            test.log(Status.INFO, "Selecting Single Use VM");
            screen.wait(IBInstaller.SingleUseVMCB.similar((float) 0.7), 5).click();
        }
    }

    /**
     * sub class for performing UI actions on the IB client
     */
    public class Client implements IClient {


        @Override
        public void verifyVSBarPattern(Pattern pat) {
            test.log(Status.INFO, "Looking for VS Bar");
            try {
                screen.wait(pat.similar((float) 0.8), 5);
            } catch (FindFailed findFailed) {
                test.log(Status.WARNING, "Failed to find VS Bar with error: " + findFailed.getMessage());
                Assert.fail();
            }
        }

        @Override
        public void verifyTrayIconPattern(Pattern pat) {
            test.log(Status.INFO, "Looking for Tray Icon");
            try {
                screen.wait(pat.similar((float) 0.8), 5);
            } catch (FindFailed findFailed) {
                test.log(Status.WARNING, "Failed to find Tray Icon with error: " + findFailed.getMessage());
                Assert.fail();
            }
        }

        @Override
        public void verifyMonitorBarPattern(Pattern pat) {
            test.log(Status.INFO, "Looking for Monitor Bar");
            try {
                screen.wait(pat.similar((float) 0.9), 5);
            } catch (FindFailed findFailed) {
                test.log(Status.WARNING, "Failed to find Monitor Bar with error: " + findFailed.getMessage());
                Assert.fail();
            }
        }

        @Override
        public void verifyHistoryColoringPattern(Pattern pat) {
            test.log(Status.INFO, "Looking for History Coloring");
            try {
                screen.wait(pat.similar((float) 0.6), 5);
            } catch (FindFailed findFailed) {
                test.log(Status.WARNING, "Failed to find History coloring with error: " + findFailed.getMessage());
                Assert.fail();
            }
        }

        @Override
        public void clickProjectsTab() {
            SystemActions.sleep(3);
            test.log(Status.INFO, "Looking for Projects Tab");
            try {
                screen.wait(Monitor.Tabs.Projects.similar((float) 0.8), 5).click();
            } catch (FindFailed findFailed) {
                test.log(Status.WARNING, "Failed to find Projects Tab with error: " + findFailed.getMessage());
                Assert.fail();
            }
        }

        @Override
        public void verifyProjectsTabColoring(Pattern pat) {
            test.log(Status.INFO, "Looking for Project Color Bar");
            try {
                screen.wait(pat.similar((float) 0.9), 5);
            } catch (FindFailed findFailed) {
                test.log(Status.WARNING, "Failed to find Project Color Bar with error: " + findFailed.getMessage());
                Assert.fail();
            }
        }

        @Override
        public void openMonitorFromTray() {
            test.log(Status.INFO, "Opening Monitor from tray");
            try {
                screen.wait(IBSettings.TrayIcon.Green.similar((float) 0.9), 5).hover();
                screen.wait(IBSettings.TrayIcon.Green.similar((float) 0.9), 5).rightClick();
                screen.wait(IBSettings.TrayIcon.monitorTray.similar((float) 0.9), 5).click();
            } catch (FindFailed findFailed) {
                test.log(Status.WARNING, "Failed to open monitor with error: " + findFailed.getMessage());
                Assert.fail();
            }
        }

        @Override
        public void openHistoryFromTray() {
            test.log(Status.INFO, "Opening History from tray");
            try {
                screen.wait(IBSettings.TrayIcon.Green.similar((float) 0.9), 5).hover();
                screen.wait(IBSettings.TrayIcon.Green.similar((float) 0.9), 5).rightClick();
                screen.wait(IBSettings.TrayIcon.historyTray.similar((float) 0.9), 5).click();
            } catch (FindFailed findFailed) {
                test.log(Status.WARNING, "Failed to open history with error: " + findFailed.getMessage());
                Assert.fail();
            }
        }

        @Override
        public void openCoordMonitorFromTray() {
            test.log(Status.INFO, "Opening Coordinator Monitor from tray");
            try {
                //screen.wait(IBSettings.TrayIcon.Green.similar((float) 0.9), 5).hover();
                screen.wait(IBSettings.TrayIcon.Green.similar((float) 0.9), 10).rightClick();
                screen.wait(IBSettings.TrayIcon.coordMonitorTray.similar((float) 0.9), 30).click();
            } catch (FindFailed findFailed) {
                test.log(Status.WARNING, "Failed to open coordinator monitor with error: " + findFailed.getMessage());
                Assert.fail();
            }
        }

        @Override
        public void openAgentSettingsFromTray() {
            test.log(Status.INFO, "Opening Agent Settings from tray");
            try {
                screen.wait(IBSettings.TrayIcon.Green.similar((float) 0.9), 5).hover();
                screen.wait(IBSettings.TrayIcon.Green.similar((float) 0.9), 5).rightClick();
                screen.wait(IBSettings.TrayIcon.agentSettingsTray.similar((float) 0.9), 5).click();
            } catch (FindFailed findFailed) {
                test.log(Status.WARNING, "Failed to open agent settings with error: " + findFailed.getMessage());
                Assert.fail();
            }
        }

        @Override
        public void verifyAllowEnableDisableAsHelperDisabledFromTray() {
            test.log(Status.INFO, "Verify allow enable/disable as helper disabled");
            openTray();
            try {
                screen.wait(IBSettings.TrayIcon.enableDisableAsHelperDeniedTray.similar((float) 1), 2);
            } catch (FindFailed findFailed) {
                test.log(Status.WARNING, "Failed to enable/disable as  helper is not denied from tray, failed with error: " + findFailed.getMessage());
                Assert.fail();
            }
        }

        @Override
        public void verifyAgentEnabledAsHelperFromTray() {
            test.log(Status.INFO, "Verify Agent is enabled as helper from tray");
            openTray();
            try {
                screen.wait(IBSettings.TrayIcon.enabledAsHelperTray.similar((float) 1), 2);
            } catch (FindFailed findFailed) {
                test.log(Status.WARNING, "Failed to find enabled as helper from tray, failed with error: " + findFailed.getMessage());
                Assert.fail();
            }
        }

        public void openTray() {
            try {
                screen.wait(IBSettings.TrayIcon.Green.similar((float) 0.9), 5).hover();
                screen.wait(IBSettings.TrayIcon.Green.similar((float) 0.9), 5).rightClick();
            } catch (FindFailed findFailed) {
                test.log(Status.WARNING, "Failed to open tray-icon menu, failed with error: " + findFailed.getMessage());
                Assert.fail();
            }
        }

        @Override
        public void verifyBuildMonitorOpened() {
            boolean objectExists = false;
            if (screen.exists(IBStatusBars.buildMonitor, 15) != null)
                objectExists = true;
            Assert.assertTrue(objectExists, "Could not find Build Monitor windows");
        }

        @Override
        public void verifyBuildHistoryOpened() {
            boolean objectExists = false;
            if (screen.exists(IBStatusBars.buildHistory, 15) != null)
                objectExists = true;
            Assert.assertTrue(objectExists, "Could not find Build History Window");
        }

        @Override
        public void verifyCoordinatorMonitorOpened() {
            boolean objectExists = false;
            if (screen.exists(IBStatusBars.coordinatorMonitor, 15) != null)
                objectExists = true;
            Assert.assertTrue(objectExists, "Could not find Coordinator monitor Window");
        }

        @Override
        public void verifyAgentSettingsOpened() {
            boolean objectExists = false;
            if (screen.exists(IBStatusBars.agentSettings, 15) != null)
                objectExists = true;
            Assert.assertTrue(objectExists, "Could not find Agent Settings Window");
        }

        @Override
        public void clickClearHistory() {
            test.log(Status.INFO, "Clicking on Clear History");
            try {
                screen.wait(IBSettings.agent.similar((float) 0.9), 5).click();
                screen.wait(IBSettings.GeneralTab.similar((float) 0.9), 5).click();
                screen.wait(IBSettings.ClearHistoryBtn.similar((float) 0.9), 5).click();
                screen.wait(IBSettings.ConfirmationBtn.similar((float) 0.5), 10).click();
                SystemActions.sleep(10);
            } catch (FindFailed findFailed) {
                test.log(Status.WARNING, "Failed to open agent settings with error: " + findFailed.getMessage());
                Assert.fail();
            }
        }

        @Override
        public void changeCpuUtilCores() {
            try {
                screen.wait(IBSettings.agent.similar((float) 0.9), 5).click();
                screen.wait(IBSettings.CpuUtilTab.similar((float) 0.9), 5).click();
                screen.wait(IBSettings.CpuUtilConfDdl.similar((float) 0.9), 5).click();
                screen.wait(IBSettings.CpuUtilUserDefined.similar((float) 0.9), 5).click();
                screen.wait(IBSettings.CpuUtilNumOfCoresTB.similar((float) 0.9), 5).click();
                screen.type("1");
                screen.wait(IBSettings.OKButton.similar((float) 0.9), 5).click();
                screen.wait(IBSettings.ConfirmationBtn2.similar((float) 0.9), 5).click();
                screen.wait(IBSettings.OKMessageBoxButton.similar((float) 0.9), 5).click();

            } catch (FindFailed findFailed) {
                test.log(Status.WARNING, "Failed to open agent settings with error: " + findFailed.getMessage());
                Assert.fail();
            }
        }

        @Override
        public void changeStartupPageToProjects() {
            try {
                screen.wait(IBSettings.BuildMonitorTab.similar((float) 0.9), 5).click();
                screen.wait(IBSettings.StartingPageProgressDdl.similar((float) 0.9), 5).click();
                screen.wait(IBSettings.StartingPageProjectsDdl.similar((float) 0.9), 5).click();
                screen.wait(IBSettings.OKButton.similar((float) 0.9), 5).click();
            } catch (FindFailed findFailed) {
                test.log(Status.WARNING, "Failed to open agent settings with error: " + findFailed.getMessage());
                Assert.fail();
            }
        }

        @Override
        public void verifyProjectsPageIsOpen() {
            try {
                boolean objectExists = false;
                if (screen.exists(IBSettings.SelectedProjectsTab, 15) == null)
                    objectExists = true;
                Assert.assertFalse(objectExists, "Monitor did not open in Projects page");
            } catch (Exception e) {
                e.getMessage();
            } finally {
                RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\monitor", "StartPage", "Progress");
                SystemActions.killProcess(Processes.BUILDMONITOR);
            }
        }

        @Override
        public void enableOutputOptions() {
            try {
                screen.wait(IBSettings.BuildMonitorTab.similar((float) 0.9), 5).click();
                screen.wait(IBSettings.OutputTab.similar((float) 0.9), 5).click();
                screen.wait(IBSettings.ShowAgentNameCB.similar((float) 0.9), 5).click();
                screen.wait(IBSettings.ShowCommandLineCB.similar((float) 0.9), 5).click();
                screen.wait(IBSettings.OKButton.similar((float) 0.9), 5).click();
            } catch (FindFailed findFailed) {
                test.log(Status.WARNING, "Failed to enable output options with error: " + findFailed.getMessage());
                Assert.fail();
            }
        }

        @Override
        public boolean verifyMultipleBuildsTab() {
            test.log(Status.INFO, "Navigating to \"Initiator\" tab");
            try {
                screen.wait(IBSettings.InitiatorTab.similar((float) 0.9), 5).click();
            } catch (FindFailed findFailed) {
                test.log(Status.WARNING, "Failed to navigate to Initiator tab with error: " + findFailed.getMessage());
                Assert.fail();
            }
            if (screen.exists(IBSettings.MultiBuildTab, 15) != null)
                return true;
            else
                return false;
        }

        @Override
        public void enableFailOnlyLocally() {
            try {
                screen.wait(IBSettings.InitiatorTab.similar((float) 0.9), 5).click();
                screen.wait(IBSettings.AdvancedTab.similar((float) 0.9), 5).click();
                screen.wait(IBSettings.EnableFailOnlyLocally.similar((float) 0.9), 5).click();
                screen.wait(IBSettings.OKButton.similar((float) 0.9), 5).click();
            } catch (FindFailed findFailed) {
                test.log(Status.WARNING, "Failed to enable FailOnlyLocally option with error: " + findFailed.getMessage());
                Assert.fail();
            }
        }

        @Override
        public void disableFailOnlyLocally() {
            try {
                screen.wait(IBSettings.InitiatorTab.similar((float) 0.9), 5).click();
                screen.wait(IBSettings.AdvancedTab.similar((float) 0.9), 5).click();
                screen.wait(IBSettings.DisableFailOnlyLocally.similar((float) 0.9), 5).click();
                screen.wait(IBSettings.OKButton.similar((float) 0.9), 5).click();
            } catch (FindFailed findFailed) {
                test.log(Status.WARNING, "Failed to disable FailOnlyLocally option with error: " + findFailed.getMessage());
                Assert.fail();
            }
        }

        @Override
        public void stopAgentService() {
            try {
                screen.wait(IBSettings.agent.similar((float) 0.9), 5).click();
                screen.wait(IBSettings.StopServiceBtn.similar((float) 0.9), 5).click();
                screen.wait(IBSettings.OKMessageBoxButton.similar((float) 0.9), 5).click();
            } catch (FindFailed findFailed) {
                test.log(Status.WARNING, "Failed to enable output options with error: " + findFailed.getMessage());
                Assert.fail();
            }
        }

        @Override
        public void startAgentService() {
            try {
                screen.wait(IBSettings.StartServiceBtn.similar((float) 0.9), 5).click();
                screen.wait(IBSettings.OKMessageBoxButton.similar((float) 0.9), 5).click();
            } catch (FindFailed findFailed) {
                test.log(Status.WARNING, "Failed to enable output options with error: " + findFailed.getMessage());
                Assert.fail();
            }
        }

        @Override
        public void restartAgentService() {
            try {
                screen.wait(IBSettings.RestartServiceBtn.similar((float) 0.9), 5).click();
                screen.wait(IBSettings.OKMessageBoxButton.similar((float) 0.9), 5).click();
            } catch (FindFailed findFailed) {
                test.log(Status.WARNING, "Failed to enable output options with error: " + findFailed.getMessage());
                Assert.fail();
            }
        }

        @Override
        public void limitNumberOfCoresPerBuild() {
            test.log(Status.INFO, "Limiting the number of cores per build");
            try {
                screen.wait(IBSettings.InitiatorTab.similar((float) 0.9), 5).click();
                screen.wait(IBSettings.EnableLimitNumOFCoresPerBuildCB.similar((float) 0.9), 5).click();
                screen.wait(IBSettings.NumOfCoresPerBuild.similar((float) 0.9), 5).click();
                screen.type("5");
                screen.wait(IBSettings.OKButton.similar((float) 0.9), 5).click();

            } catch (FindFailed findFailed) {
                test.log(Status.WARNING, "Failed to enable core limit per build with error: " + findFailed.getMessage());
                Assert.fail();
            }
        }

        @Override
        public void disableLimitOfCoresPerBuild() {
            test.log(Status.INFO, "Disabling number of cores per build limit");
            try {
                screen.wait(IBSettings.InitiatorTab.similar((float) 0.9), 5).click();
                screen.wait(IBSettings.DisableLimitNumOFCoresPerBuildCB.similar((float) 0.9), 5).click();
                screen.wait(IBSettings.OKButton.similar((float) 0.9), 5).click();
            } catch (FindFailed findFailed) {
                test.log(Status.WARNING, "Failed to disable core limit with error: " + findFailed.getMessage());



            }
        }

        @Override
        public void enableSchedulingAndVerifyIcon() {
            try {
                screen.wait(IBSettings.agent.similar((float) 0.9), 5).click();
                screen.wait(IBSettings.PreferenceTab.similar((float) 0.9), 5).click();
                screen.wait(IBSettings.EnableSchedulingCB.similar((float) 0.9), 5).click();
                screen.wait(IBSettings.OKButton.similar((float) 0.9), 5).click();
                boolean objectExists = false;
                if (screen.exists(IBSettings.DisabledTrayIcon, 15) != null)
                    objectExists = true;
                Assert.assertTrue(objectExists, "Icon did not change to disabled");
            } catch (FindFailed findFailed) {
                test.log(Status.WARNING, "Failed to enable scheduling with error: " + findFailed.getMessage());
                Assert.fail();
            }
        }

        @Override
        public void disableSchedulingAndVerifyIcon() {
            try {
                screen.wait(IBSettings.DisableSchedulingCB.similar((float) 0.9), 5).click();
                screen.wait(IBSettings.OKButton.similar((float) 0.9), 5).click();
                boolean objectExists = false;
                if (screen.exists(IBSettings.TrayIcon.Green, 15) != null)
                    objectExists = true;
                Assert.assertTrue(objectExists, "Could not find Build History Window");
            } catch (FindFailed findFailed) {
                test.log(Status.WARNING, "Failed to enable scheduling with error: " + findFailed.getMessage());
                Assert.fail();
            }
        }

        @Override
        public void isNotActiveScheduling() {
            try {
                screen.wait(IBSettings.agent.similar((float) 0.9), 5).click();
                screen.wait(IBSettings.PreferenceTab.similar((float) 0.9), 5).click();
                screen.wait(IBSettings.isNotActiveScheduling.similar((float) 0.9), 5).click();
                boolean objectExists = false;
                if (screen.exists(IBSettings.isNotActiveScheduling, 15) != null)
                    objectExists = true;
                Assert.assertTrue(objectExists, "Could not find SchedulingCB");
                screen.wait(IBSettings.OKButton.similar((float) 0.9), 5).click();
            } catch (FindFailed findFailed) {
                test.log(Status.WARNING, "Failed to enable scheduling with error:" + findFailed.getMessage());
                Assert.fail();
            }
        }
        }

        public class Coordinator implements ICoordinator {

            @Override
            public void disableAsHelper() {
                test.log(Status.INFO, "Disabling agent as helper");
                try {
                    screen.wait(CoordMonitor.HelperFromList.similar((float) 0.8), 15).rightClick();
                    screen.wait(CoordMonitor.DisableAsHelperMenu.similar((float) 0.8), 15).click();
                } catch (FindFailed findFailed) {
                    test.log(Status.WARNING, "Failed to disable as helper, failed with error: " + findFailed.getMessage());
                    Assert.fail();
                }
            }

            @Override
            public void verifyHelperIsDisabled() {
                boolean objectExists = false;
                if (screen.exists(CoordMonitor.DisabledAgentValidation, 15) != null)
                    objectExists = true;
                Assert.assertTrue(objectExists, "Could not find agent disabled verification");
            }

            @Override
            public void enableAsHelper() {
                test.log(Status.INFO, "Enabling agent as helper");
                try {
                    screen.wait(CoordMonitor.HelperFromList.similar((float) 0.8), 15).rightClick();
                    screen.wait(CoordMonitor.EnableAsHelperMenu.similar((float) 0.8), 15).click();
                } catch (FindFailed findFailed) {
                    test.log(Status.WARNING, "Failed to enable as helper, failed with error: " + findFailed.getMessage());
                    Assert.fail();
                }
            }

            @Override
            public void verifyHelperIsEnabled() {
                boolean objectExists = false;
                if (screen.exists(CoordMonitor.EnabledAgentValidation, 15) != null)
                    objectExists = true;
                Assert.assertTrue(objectExists, "Could not find agent enabled verification");
            }

            @Override
            public void unsubscribeAgent() {
                test.log(Status.INFO, "Unsubscribing agent as helper");
                try {
                    SystemActions.sleep(5);
                    screen.wait(CoordMonitor.HelperFromList.similar((float) 0.8), 15).rightClick();
                    screen.wait(CoordMonitor.UnsubscribeAgentMenu.similar((float) 0.8), 15).click();
                } catch (FindFailed findFailed) {
                    test.log(Status.WARNING, "Failed to unsubscribe agent, failed with error: " + findFailed.getMessage());
                    Assert.fail();
                }
            }

            @Override
            public void verifyAgentIsUnsubscribed() {
                boolean objectExists = false;
                if (screen.exists(CoordMonitor.UnsubscribedAgentValidation, 15) != null)
                    objectExists = true;
                Assert.assertTrue(objectExists, "Could not find agent unsubscribed verification");
            }

            @Override
            public void subscribeAgent() {
                test.log(Status.INFO, "Subscribing agent as helper");
                try {
                    SystemActions.sleep(5);
                    screen.wait(CoordMonitor.HelperFromList.similar((float) 0.8), 15).rightClick();
                    screen.wait(CoordMonitor.SubscribeAgentMenu.similar((float) 0.8), 15).click();
                } catch (FindFailed findFailed) {
                    test.log(Status.WARNING, "Failed to subscribe agent, failed with error: " + findFailed.getMessage());
                    Assert.fail();
                }
            }

            @Override
            public void verifyAgentIsSubscribed() {
                boolean objectExists = false;
                if (screen.exists(CoordMonitor.SubscribedAgentValidation, 15) != null)
                    objectExists = true;
                Assert.assertTrue(objectExists, "Could not find agent subscribed verification");
            }

            @Override
            public void clickAllowRemoteAdministration() {
                test.log(Status.INFO, "Clicking allow remote administration");
                try {
                    screen.wait(CoordMonitor.HelperFromList.similar((float) 0.8), 15).rightClick();
                    screen.wait(CoordMonitor.AllowRemoteAdministrationMenu.similar((float) 0.8), 15).click();
                } catch (FindFailed findFailed) {
                    test.log(Status.WARNING, "Failed to click allow remote administration, failed with error: " + findFailed.getMessage());
                    Assert.fail();
                }
            }

            @Override
            public void clickAllowEnableDisableAsHelper() {
                test.log(Status.INFO, "Clicking allow Enable Disable as helper");
                try {
                    screen.wait(CoordMonitor.InitiatorFromList.similar((float) 0.97), 15).rightClick();
                    screen.wait(CoordMonitor.AllowEnableDisableAsHelperMenu.similar((float) 0.95), 15).click();
                    screen.wait(CoordMonitor.FileMenu.similar((float) 0.95), 15).click();
                    screen.wait(CoordMonitor.ExitButton.similar((float) 0.95),15).click();

                } catch (FindFailed findFailed) {
                    test.log(Status.WARNING, "Failed to click allow Enable Disable as helper, failed with error: " + findFailed.getMessage());
                    Assert.fail();
                }
            }

            @Override
            public void stopCoordService() {
                test.log(Status.INFO, "Stopping coordinator service");
                try {
                    screen.wait(CoordMonitor.ToolsMenu.similar((float) 0.95), 15).click();
                    screen.wait(CoordMonitor.StopServiceMenu.similar((float) 0.95), 15).click();
                } catch (FindFailed findFailed) {
                    test.log(Status.WARNING, "Failed to stop coordinator service, failed with error: " + findFailed.getMessage());
                    Assert.fail();
                }
            }

            @Override
            public void startCoordService() {
                test.log(Status.INFO, "Starting coordinator service");
                try {
                    screen.wait(CoordMonitor.ToolsMenu.similar((float) 0.95), 15).click();
                    screen.wait(CoordMonitor.StartServiceMenu.similar((float) 0.95), 15).click();
                } catch (FindFailed findFailed) {
                    test.log(Status.WARNING, "Failed to start coordinator service, failed with error: " + findFailed.getMessage());
                    Assert.fail();
                }
            }

            @Override
            public void pauseCloud() {
                test.log(Status.INFO, "Pausing cloud");
                try {
                    screen.wait(CoordMonitor.CloudEnabledButton.similar((float) 0.95), 15).click();
                    screen.wait(CoordMonitor.ToolsMenu.similar((float) 0.95), 15).hover();
                    screen.wait(CoordMonitor.PauseCloudButton.similar((float) 0.95), 15).click();
                    screen.wait(CoordMonitor.PauseCloudOnly.similar((float) 0.95), 15).click();
                } catch (FindFailed findFailed) {
                    test.log(Status.WARNING, "Failed to Pause cloud, failed with error: " + findFailed.getMessage());
                    Assert.fail();
                }
            }

            @Override
            public void pauseCloudAndDeletePool() {
                test.log(Status.INFO, "Pausing cloud and deleting pool");
                try {
                    screen.wait(CoordMonitor.CloudEnabledButton.similar((float) 0.95), 15).click();
                    screen.wait(CoordMonitor.ToolsMenu.similar((float) 0.95), 15).hover();
                    screen.wait(CoordMonitor.PauseCloudButton.similar((float) 0.95), 15).click();
                    screen.wait(CoordMonitor.PauseCloudAndDeletePool.similar((float) 0.95), 15).click();
                } catch (FindFailed findFailed) {
                    test.log(Status.WARNING, "Failed to Pause cloud, failed with error: " + findFailed.getMessage());
                    Assert.fail();
                }
            }

            @Override
            public void enableCloud(boolean isDeleted) {
                test.log(Status.INFO, "Enabling cloud");
                try {
                    screen.wait(CoordMonitor.CloudPausedButton.similar((float) 0.95), 15).click();
                    screen.wait(CoordMonitor.ToolsMenu.similar((float) 0.95), 15).hover();
                    screen.wait(CoordMonitor.ResumeCloudButton.similar((float) 0.95), 15).click();
                    if (isDeleted)
                        screen.wait(CoordMonitor.ResumeCloudPopUpButton.similar((float) 0.80), 15).click();
                } catch (FindFailed findFailed) {
                    test.log(Status.WARNING, "Failed to enable cloud, failed with error: " + findFailed.getMessage());
                    Assert.fail();
                }
            }

            @Override
            public void deactivateCloud() {
                test.log(Status.INFO, "Deactivating cloud");
                try {
                    screen.wait(CoordMonitor.CloudEnabledButton.similar((float) 0.95), 15).click();
                    screen.wait(CoordMonitor.DeactivateCloud.similar((float) 0.95), 15).click();
                    screen.wait(CoordMonitor.DeactivateButton.similar((float) 0.95), 15).click();
                } catch (FindFailed findFailed) {
                    test.log(Status.WARNING, "Failed to Deactivate cloud, failed with error: " + findFailed.getMessage());
                    Assert.fail();
                }
            }

            @Override
            public void verifyCloudDeactivated() {
                test.log(Status.INFO, "Verifying cloud is deactivated");
                try {
                    screen.wait(CoordMonitor.DeactivatingButton.similar((float) 0.95), 60);
                } catch (FindFailed findFailed) {
                    test.log(Status.WARNING, "Failed to verify cloud deactivated, failed with error: " + findFailed.getMessage());
                    Assert.fail();
                }
            }
        }

    }

