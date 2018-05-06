package ibInfra.ibUIService;

import frameworkInfra.sikuli.sikulimapping.IBInstaller.IBInstaller;
import frameworkInfra.utils.StaticDataProvider.*;
import ibInfra.windowscl.WindowsService;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;

import java.io.File;

public class IBUIService implements IIBUIService {

    private WindowsService winService = new WindowsService();
    private Screen screen = new Screen();

    @Override
    public void startIBUIInstaller(String version) {
        String installationFile = getIbSetupInstallation(version);
        winService.runCommandDontWaitForTermination(installationFile);
    }

    @Override
    public String getIbSetupInstallation(String version) {
        File folder = new File(Locations.NETWORK_IB_INSTALLATIONS + version);
        File[] listOfFiles = folder.listFiles();
        String fileName = "";

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile() && listOfFiles[i].getName().contains("ibsetup")&& !listOfFiles[i].getName().contains("console")) {
                fileName = listOfFiles[i].getAbsolutePath();
            }
        }
        return fileName;
    }

    public class Installer implements IInstaller {

        @Override
        public void clickNext() throws FindFailed {
            screen.wait(IBInstaller.NextBTN.similar((float) 0.5),20).click();
        }

        @Override
        public void acceptTerms() throws FindFailed {
            screen.wait(IBInstaller.TermsUncheckCB.similar((float) 0.5),2).click();
        }

        @Override
        public void clickFinish() throws FindFailed {
            screen.wait(IBInstaller.FinishBTN.similar((float) 0.5),2).click();
        }

        @Override
        public void cancelReleaseNotes() throws FindFailed {
            screen.wait(IBInstaller.ReleaseNotesUncheckCB.similar((float) 0.5),2).click();
        }

        @Override
        public void installNewCoordinator() throws FindFailed {
            screen.wait(IBInstaller.NewCoordinatorRB.similar((float) 0.5),2).click();
        }

        @Override
        public void changeInstallationPath(String path) throws FindFailed {
            screen.wait(IBInstaller.InstallationPathTB.similar((float) 0.5),2).type(path);
        }

        @Override
        public void uncheckEnvVar() throws FindFailed {
            screen.wait(IBInstaller.EnvVarCB.similar((float) 0.5),2).click();
        }

        @Override
        public void browseLicense() throws FindFailed {
            screen.wait(IBInstaller.BrowseLicenseBTN.similar((float) 0.5),2).click();
        }

        @Override
        public void browseLicenseNavigateToDesktop() throws FindFailed {
            screen.wait(IBInstaller.DesktopTabBTN.similar((float) 0.5),2).click();
        }

        @Override
        public void selectLicense() throws FindFailed {
            screen.wait(IBInstaller.LicenseFile.similar((float) 0.5),2).doubleClick();
            screen.wait(IBInstaller.LicenseLoadedOKBTN.similar((float) 0.5),10).click();
        }

        @Override
        public void selectCoordinator(String coordName) throws FindFailed {
            screen.wait(IBInstaller.CoordinatorNameTB.similar((float) 0.5),2).type(coordName);
        }
    }

}
