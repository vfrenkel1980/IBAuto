package ibInfra.ibUIService;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;

public interface IIBUIService {

    void startIBUIInstaller(String version);

    String getEntInstallation(String version);

    void startEntInstaller(String version);

    String getIbSetupInstallation(String version);

    interface IInstaller{

        void clickNext() throws FindFailed;

        void acceptTerms() throws FindFailed;

        void clickFinish() throws FindFailed;

        void clickAdvanced() throws FindFailed;

        void clickAssignBuildGroup() throws FindFailed;

        void uncheckReleaseNotes() throws FindFailed;

        void uncheckRemoteUpdate() throws FindFailed;

        void installNewCoordinator() throws FindFailed;

        void changeInstallationPath(String path) throws FindFailed;

        void uncheckEnvVar() throws FindFailed;

        void browseLicense() throws FindFailed;

        void browseLicenseNavigateToDesktop() throws FindFailed;

        void selectLicense() throws FindFailed;

        void selectCoordinator(String coordName) throws FindFailed;

        void selectUninstall() throws FindFailed;


        void selectManualHelperPorts() throws FindFailed;

        void selectManualCoordPort() throws FindFailed;

        void uncheckLaunchDashboard() throws FindFailed;

        void uncheckCreateEntShortcut() throws FindFailed;

        void selectDowngrade() throws FindFailed;

        void changeDashboardPort() throws FindFailed;

        void changeEntInstallationLocation(String path) throws FindFailed;

        void verifyInvalidLicenseMessage() throws FindFailed;

        void clickExit() throws FindFailed;

        void clickCustom() throws FindFailed;

        void selectSingleUseVM() throws FindFailed;

    }

    interface IClient{

        void verifyVSBarPattern(Pattern pat);

        void verifyTrayIconPattern(Pattern pat);

        void verifyMonitorBarPattern(Pattern pat);

        void verifyHistoryColoringPattern(Pattern pat);

        void verifyProjectsTabColoring(Pattern pat);

        void clickProjectsTab();

        void openMonitorFromTray();

        void openHistoryFromTray();

        void openCoordMonitorFromTray();

        void openCoordSettingsFromTray();

        void openAgentSettingsFromTray();

        void clickAdvancedOptionOfCoordSettings();

        void verifyAllowEnableDisableAsHelperDisabledFromTray();

        void verifyAgentEnabledAsHelperFromTray();

        void verifyBuildMonitorOpened();

        void verifyBuildHistoryOpened();

        void verifyCoordinatorMonitorOpened();

        void verifyAgentSettingsOpened();

        void clickClearHistory();

        void changeCpuUtilCores();

        void changeStartupPageToProjects();

        void verifyProjectsPageIsOpen();

        void enableOutputOptions();

        boolean verifyMultipleBuildsTab();

        void stopAgentService();

        void startAgentService();

        void restartAgentService();

        void limitNumberOfCoresPerBuild();

        void disableLimitOfCoresPerBuild();

        void enableSchedulingAndVerifyIcon();

        void disableSchedulingAndVerifyIcon();

        void disableFailOnlyLocally();

        void enableFailOnlyLocally();

        void isNotActiveScheduling();
    }

    interface ICoordinator{

        void disableAsHelper();

        void verifyHelperIsDisabled();

        void enableAsHelper();

        void verifyHelperIsEnabled();

        void unsubscribeAgent();

        void verifyAgentIsUnsubscribed();

        void subscribeAgent();

        void verifyAgentIsSubscribed();

        void clickAllowRemoteAdministration();

        void clickAllowEnableDisableAsHelper();

        void stopCoordService();

        void startCoordService();

        void pauseCloud();

        void pauseCloudAndDeletePool();

        void enableCloud(boolean isDeleted);

        void deactivateCloud();

        void verifyCloudDeactivated();



    }
}
