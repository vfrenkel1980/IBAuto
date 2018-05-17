package ibInfra.ibUIService;

import org.sikuli.script.FindFailed;

public interface IIBUIService {

    void startIBUIInstaller(String version);

    String getIbSetupInstallation(String version);

    public interface IInstaller{

        void clickNext() throws FindFailed;

        void acceptTerms() throws FindFailed;

        void clickFinish() throws FindFailed;

        void cancelReleaseNotes() throws FindFailed;

        void cancelRemoteUpdate() throws FindFailed;

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

    }
}
