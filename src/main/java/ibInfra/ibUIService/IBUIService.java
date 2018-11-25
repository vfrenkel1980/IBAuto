package ibInfra.ibUIService;

import com.aventstack.extentreports.Status;
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
import java.util.concurrent.ExecutionException;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static frameworkInfra.Listeners.SuiteListener.test;

public class IBUIService implements IIBUIService {

    private WindowsService winService = new WindowsService();
    private Screen screen = new Screen();

    /**
     * start the IB ui installer
     * @param version version to install
     */
    @Override
    public void startIBUIInstaller(String version) {
        String installationFile = getIbSetupInstallation(version);
        winService.runCommandDontWaitForTermination(installationFile);
    }

    /**
     * get the path to the installer
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
        public void clickNext() throws FindFailed {
            test.log(Status.INFO, "Clicking Next");
            screen.wait(IBInstaller.NextBTN.similar((float) 0.8),240).click();
        }

        @Override
        public void acceptTerms() throws FindFailed {
            test.log(Status.INFO, "Clicking Accept terms");
            screen.wait(IBInstaller.TermsUncheckCB.similar((float) 0.5),5).click();
        }

        @Override
        public void clickFinish() throws FindFailed {
            test.log(Status.INFO, "Clicking Finish");
            screen.wait(IBInstaller.FinishBTN.similar((float) 0.8),360).click();
        }

        @Override
        public void uncheckReleaseNotes() throws FindFailed {
            test.log(Status.INFO, "Removing Release notes CB");
            screen.wait(IBInstaller.ReleaseNotesUncheckCB.similar((float) 0.8),120).click();
        }

        @Override
        public void uncheckRemoteUpdate() throws FindFailed {
            test.log(Status.INFO, "Removing Remote update CB");
            screen.wait(IBInstaller.UpdateOtherComputersCB.similar((float) 0.8),5).click();
        }

        @Override
        public void installNewCoordinator() throws FindFailed {
            test.log(Status.INFO, "Selecting \"install new coordinator\"");
            screen.wait(IBInstaller.NewCoordinatorRB.similar((float) 0.5),5).click();
        }

        @Override
        public void changeInstallationPath(String path) throws FindFailed {
            test.log(Status.INFO, "Changing installation path to: " + path);
            screen.wait(IBInstaller.InstallationPathTB.similar((float) 0.5),5).click();
            screen.type(path);
        }

        @Override
        public void uncheckEnvVar() throws FindFailed {
            test.log(Status.INFO, "Removing Env Vars CB");
            screen.wait(IBInstaller.EnvVarCB.similar((float) 0.5),5).click();
        }

        @Override
        public void browseLicense() throws FindFailed {
            test.log(Status.INFO, "Clicking on browse license");
            screen.wait(IBInstaller.BrowseLicenseBTN.similar((float) 0.5),600).click();
        }

        @Override
        public void browseLicenseNavigateToDesktop() throws FindFailed {
            test.log(Status.INFO, "Clicking on Desktop tab");
            screen.wait(IBInstaller.DesktopTabBTN.similar((float) 0.8),5).click();
        }

        @Override
        public void selectLicense() throws FindFailed {
            test.log(Status.INFO, "Selecting license");
            screen.wait(IBInstaller.LicenseFile.similar((float) 0.7),5).doubleClick();
            screen.wait(IBInstaller.LicenseLoadedOKBTN.similar((float) 0.5),10).click();
            test.log(Status.INFO, "License selected");
        }

        @Override
        public void selectCoordinator(String coordName) throws FindFailed {
            test.log(Status.INFO, "Selecting existing coordinator");
            screen.wait(IBInstaller.CoordinatorNameTB.similar((float) 0.5),5).click();
            screen.type(coordName);
            SystemActions.sleep(2);
        }

        @Override
        public void selectUninstall() throws FindFailed {
            test.log(Status.INFO, "Selecting uninstall");
            screen.wait(IBInstaller.UninstallRB.similar((float) 0.7),5).click();
        }

        @Override
        public void selectManualHelperPorts() throws FindFailed {
            test.log(Status.INFO, "Selecting manual ports");
            screen.wait(IBInstaller.ManualPortSelectionRB.similar((float) 0.7),5).click();
            screen.wait(IBInstaller.AgentServicePortTB.similar((float) 0.7),5).click();
            screen.wait(IBInstaller.AgentServicePortTB.similar((float) 0.7),5).type(InstallationPorts.AGENT_PORT);
            screen.wait(IBInstaller.HelpersPortTB.similar((float) 0.7),5).click();
            screen.wait(IBInstaller.HelpersPortTB.similar((float) 0.4),5).type(InstallationPorts.HELPER_PORT);
        }

        @Override
        public void selectManualCoordPort() throws FindFailed {
            test.log(Status.INFO, "Selecting manual coordinator ports");
            screen.wait(IBInstaller.CoordinatorPortTB.similar((float) 0.9),5).click();
            screen.wait(IBInstaller.CoordinatorPortTB.similar((float) 0.4),5).type(InstallationPorts.COORDINATOR_PORT);
        }

        @Override
        public void uncheckLaunchDashboard() throws FindFailed {
            test.log(Status.INFO, "Removing \"Launch Dashboard\" CB");
            screen.wait(IBInstaller.LaunchDashboardCB.similar((float) 0.7),600).click();
        }

        @Override
        public void uncheckCreateEntShortcut() throws FindFailed {
            test.log(Status.INFO, "Removing \"Create Ent. shortcut\" CB");
            screen.wait(IBInstaller.EnterpriseShortcutCB.similar((float) 0.7),5).click();
        }

        @Override
        public void selectDowngrade() throws FindFailed {
            test.log(Status.INFO, "Selecting downgrade option");
            screen.wait(IBInstaller.DowngradeToProRB.similar((float) 0.7),5).click();
        }

        @Override
        public void changeDashboardPort() throws FindFailed {
            test.log(Status.INFO, "Selecting manual dashboard ports");
            screen.wait(IBInstaller.DashboardPortTB.similar((float) 0.9),5).click();
            screen.wait(IBInstaller.DashboardPortTB.similar((float) 0.4),5).type(InstallationPorts.DASHBOARD_PORT);
        }

        @Override
        public void changeEntInstallationLocation(String path) throws FindFailed {
            test.log(Status.INFO, "Changing Enterprise installation path to: " + path);
            screen.wait(IBInstaller.EntInstallationLocationTB.similar((float) 0.5),5).click();
            screen.type(path);
        }

        @Override
        public void verifyInvalidLicenseMessage() throws FindFailed {
            test.log(Status.INFO, "Validating Invalid License Message");
            screen.wait(IBInstaller.InvalidLicenseMessage.similar((float) 0.5),600);
        }

        @Override
        public void clickExit() throws FindFailed {
            test.log(Status.INFO, "Clicking Exit");
            screen.wait(IBInstaller.ExitBtn.similar((float) 0.7),5).click();
        }

        @Override
        public void clickCustom() throws FindFailed {
            test.log(Status.INFO, "Clicking Custom");
            screen.wait(IBInstaller.CustomTab.similar((float) 0.7),5).click();
        }

        @Override
        public void selectSingleUseVM() throws FindFailed {
            test.log(Status.INFO, "Selecting Single Use VM");
            screen.wait(IBInstaller.SingleUseVMCB.similar((float) 0.7),5).click();
        }
    }

    /**
     * sub class for performing UI actions on the IB client
     */
    public class Client implements IClient{

        @Override
        public void verifyVSBarPattern(Pattern pat) {
            test.log(Status.INFO, "Looking for VS Bar");
            try {
                screen.wait(pat.similar((float) 0.9),5);
            } catch (FindFailed findFailed) {
                test.log(Status.WARNING, "Failed to find VS Bar with error: " + findFailed.getMessage());
                Assert.fail();
            }
        }

        @Override
        public void verifyTrayIconPattern(Pattern pat) {
            test.log(Status.INFO, "Looking for Tray Icon");
            try {
                screen.wait(pat.similar((float) 0.8),5);
            } catch (FindFailed findFailed) {
                test.log(Status.WARNING, "Failed to find Tray Icon with error: " + findFailed.getMessage());
                Assert.fail();
            }
        }

        @Override
        public void verifyMonitorBarPattern(Pattern pat) {
            test.log(Status.INFO, "Looking for Monitor Bar");
            try {
                screen.wait(pat.similar((float) 0.9),5);
            } catch (FindFailed findFailed) {
                test.log(Status.WARNING, "Failed to find Monitor Bar with error: " + findFailed.getMessage());
                Assert.fail();
            }
        }

        @Override
        public void verifyHistoryColoringPattern(Pattern pat) {
            test.log(Status.INFO, "Looking for History Coloring");
            try {
                screen.wait(pat.similar((float) 0.6),5);
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
                screen.wait(Monitor.Tabs.Projects.similar((float) 0.8),5).click();
            } catch (FindFailed findFailed) {
                test.log(Status.WARNING, "Failed to find Projects Tab with error: " + findFailed.getMessage());
                Assert.fail();
            }
        }

        @Override
        public void verifyProjectsTabColoring(Pattern pat) {
            test.log(Status.INFO, "Looking for Project Color Bar");
            try {
                screen.wait(pat.similar((float) 0.9),5);
            } catch (FindFailed findFailed) {
                test.log(Status.WARNING, "Failed to find Project Color Bar with error: " + findFailed.getMessage());
                Assert.fail();
            }
        }

        @Override
        public void openMonitorFromTray() {
            test.log(Status.INFO, "Opening Monitor from tray");
            try {
                screen.wait(IBSettings.TrayIcon.Green.similar((float) 0.9),5).rightClick();
                screen.wait(IBSettings.TrayIcon.monitorTray.similar((float) 0.9),5).click();
            } catch (FindFailed findFailed) {
                test.log(Status.WARNING, "Failed to open monitor with error: " + findFailed.getMessage());
                Assert.fail();
            }
        }

        @Override
        public void openHistoryFromTray() {
            test.log(Status.INFO, "Opening History from tray");
            try {
                screen.wait(IBSettings.TrayIcon.Green.similar((float) 0.9),5).rightClick();
                screen.wait(IBSettings.TrayIcon.historyTray.similar((float) 0.9),5).click();
            } catch (FindFailed findFailed) {
                test.log(Status.WARNING, "Failed to open history with error: " + findFailed.getMessage());
                Assert.fail();
            }
        }

        @Override
        public void openCoordMonitorFromTray() {
            test.log(Status.INFO, "Opening Coordinator Monitor from tray");
            try {
                screen.wait(IBSettings.TrayIcon.Green.similar((float) 0.9),5).rightClick();
                screen.wait(IBSettings.TrayIcon.coordMonitorTray.similar((float) 0.9),5).click();
            } catch (FindFailed findFailed) {
                test.log(Status.WARNING, "Failed to open coordinator monitor with error: " + findFailed.getMessage());
                Assert.fail();
            }
        }

        @Override
        public void openAgentSettingsFromTray() {
            test.log(Status.INFO, "Opening Agent Settings from tray");
            try {
                screen.wait(IBSettings.TrayIcon.Green.similar((float) 0.9),5).rightClick();
                screen.wait(IBSettings.TrayIcon.agentSettingsTray.similar((float) 0.9),5).click();
            } catch (FindFailed findFailed) {
                test.log(Status.WARNING, "Failed to open agent settings with error: " + findFailed.getMessage());
                Assert.fail();
            }
        }

        @Override
        public void verifyBuildMonitorOpened() {
            boolean objectExists = false;
            if (screen.exists(IBStatusBars.buildMonitor, 15) == null)
                objectExists = true;
            Assert.assertTrue(objectExists, "Could not find Build Monitor windows");
        }

        @Override
        public void verifyBuildHistoryOpened() {
            boolean objectExists = false;
            if (screen.exists(IBStatusBars.buildHistory, 15) == null)
                objectExists = true;
            Assert.assertTrue(objectExists, "Could not find Build History Window");
        }

        @Override
        public void verifyCoordinatorMonitorOpened() {
            boolean objectExists = false;
            if (screen.exists(IBStatusBars.coordinatorMonitor, 15) == null)
                objectExists = true;
            Assert.assertTrue(objectExists, "Could not find Coordinator monitor Window");
        }

        @Override
        public void verifyAgentSettingsOpened() {
            boolean objectExists = false;
            if (screen.exists(IBStatusBars.agentSettings, 15) == null)
                objectExists = true;
            Assert.assertTrue(objectExists, "Could not find Agent Settings Window");
        }

        @Override
        public void clickClearHistory() {
            test.log(Status.INFO, "Clicking on Clear History");
            try {
                screen.wait(IBSettings.agent.similar((float) 0.9),5).click();
                screen.wait(IBSettings.ClearHistoryBtn.similar((float) 0.9),5).click();
                screen.wait(IBSettings.ConfirmationBtn.similar((float) 0.5),10).click();
                SystemActions.sleep(10);
            } catch (FindFailed findFailed) {
                test.log(Status.WARNING, "Failed to open agent settings with error: " + findFailed.getMessage());
                Assert.fail();
            }
        }

        @Override
        public void changeCpuUtilCores() {
            try {
                //screen.wait(IBSettings.agent.similar((float) 0.9),5).click();
                screen.wait(IBSettings.CpuUtilTab.similar((float) 0.9),5).click();
                screen.wait(IBSettings.CpuUtilConfDdl.similar((float) 0.9),5).click();
                screen.wait(IBSettings.CpuUtilUserDefined.similar((float) 0.9),5).click();
                screen.wait(IBSettings.CpuUtilNumOfCoresTB.similar((float) 0.9),5).click();
                screen.type("1");
                screen.wait(IBSettings.OKButton.similar((float) 0.9),5).click();
                screen.wait(IBSettings.ConfirmationBtn2.similar((float) 0.9),5).click();
                screen.wait(IBSettings.OKMessageBoxButton.similar((float) 0.9),5).click();

            } catch (FindFailed findFailed) {
                test.log(Status.WARNING, "Failed to open agent settings with error: " + findFailed.getMessage());
                Assert.fail();
            }
        }

        @Override
        public void changeStartupPageToProjects() {
            try {
                screen.wait(IBSettings.BuildMonitorTab.similar((float) 0.9),5).click();
                screen.wait(IBSettings.StartingPageProgressDdl.similar((float) 0.9),5).click();
                screen.wait(IBSettings.StartingPageProjectsDdl.similar((float) 0.9),5).click();
                screen.wait(IBSettings.OKButton.similar((float) 0.9),5).click();
            } catch (FindFailed findFailed) {
                test.log(Status.WARNING, "Failed to open agent settings with error: " + findFailed.getMessage());
                Assert.fail();
            }
        }

        @Override
        public void verifyProjectsPageIsOpen() {
            try{
            boolean objectExists = false;
            if (screen.exists(IBSettings.SelectedProjectsTab, 15) == null)
                objectExists = true;
            Assert.assertFalse(objectExists, "Monitor did not open in Projects page");
            } catch (Exception e){
                e.getMessage();
            }
            finally {
                RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\monitor", "StartPage", "Progress");
                SystemActions.killProcess(Processes.BUILDMONITOR);
            }
        }

        @Override
        public void enableOutputOptions() {
            try {
                screen.wait(IBSettings.BuildMonitorTab.similar((float) 0.9),5).click();
                screen.wait(IBSettings.OutputTab.similar((float) 0.9),5).click();
                screen.wait(IBSettings.ShowAgentNameCB.similar((float) 0.9),5).click();
                screen.wait(IBSettings.ShowCommandLineCB.similar((float) 0.9),5).click();
                screen.wait(IBSettings.OKButton.similar((float) 0.9),5).click();
            } catch (FindFailed findFailed) {
                test.log(Status.WARNING, "Failed to enable output options with error: " + findFailed.getMessage());
                Assert.fail();
            }
        }

        @Override
        public boolean verifyMultipleBuildsTab() {
            try {
                screen.wait(IBSettings.InitiatorTab.similar((float) 0.9),5).click();
            } catch (FindFailed findFailed) {
                test.log(Status.WARNING, "Failed to enable output options with error: " + findFailed.getMessage());
                Assert.fail();
            }
            if (screen.exists(IBSettings.MultiBuildTab, 15) != null)
                return true;
            else
                return false;
        }


    }

}
