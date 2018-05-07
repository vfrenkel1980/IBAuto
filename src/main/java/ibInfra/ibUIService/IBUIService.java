package ibInfra.ibUIService;

import com.aventstack.extentreports.Status;
import frameworkInfra.sikuli.sikulimapping.IBInstaller.IBInstaller;
import frameworkInfra.utils.StaticDataProvider.*;
import ibInfra.windowscl.WindowsService;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;

import java.io.File;

import static frameworkInfra.Listeners.SuiteListener.test;

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
            test.log(Status.INFO, "Clicking Next");
            screen.wait(IBInstaller.NextBTN.similar((float) 0.8),40).click();
        }

        @Override
        public void acceptTerms() throws FindFailed {
            test.log(Status.INFO, "Clicking Accept terms");
            screen.wait(IBInstaller.TermsUncheckCB.similar((float) 0.5),2).click();
        }

        @Override
        public void clickFinish() throws FindFailed {
            test.log(Status.INFO, "Clicking Finish");
            screen.wait(IBInstaller.FinishBTN.similar((float) 0.8),2).click();
        }

        @Override
        public void cancelReleaseNotes() throws FindFailed {
            test.log(Status.INFO, "Removing Release notes CB");
            screen.wait(IBInstaller.ReleaseNotesUncheckCB.similar((float) 0.8),120).click();
        }

        @Override
        public void installNewCoordinator() throws FindFailed {
            test.log(Status.INFO, "Selecting \"install new coordinator\"");
            screen.wait(IBInstaller.NewCoordinatorRB.similar((float) 0.5),2).click();
        }

        @Override
        public void changeInstallationPath(String path) throws FindFailed {
            test.log(Status.INFO, "Changing installation path to: " + path);
            screen.wait(IBInstaller.InstallationPathTB.similar((float) 0.5),2).click();
            screen.type(path);
        }

        @Override
        public void uncheckEnvVar() throws FindFailed {
            test.log(Status.INFO, "Removing Env Vars CB");
            screen.wait(IBInstaller.EnvVarCB.similar((float) 0.5),2).click();
        }

        @Override
        public void browseLicense() throws FindFailed {
            test.log(Status.INFO, "Clicking on browse license");
            screen.wait(IBInstaller.BrowseLicenseBTN.similar((float) 0.5),120).click();
        }

        @Override
        public void browseLicenseNavigateToDesktop() throws FindFailed {
            screen.wait(IBInstaller.DesktopTabBTN.similar((float) 0.8),2).click();
        }

        @Override
        public void selectLicense() throws FindFailed {
            test.log(Status.INFO, "Selecting license");
            screen.wait(IBInstaller.LicenseFile.similar((float) 0.7),2).doubleClick();
            screen.wait(IBInstaller.LicenseLoadedOKBTN.similar((float) 0.5),10).click();
            test.log(Status.INFO, "License selected");
        }

        @Override
        public void selectCoordinator(String coordName) throws FindFailed {
            test.log(Status.INFO, "Selecting existing coordinator");
            screen.wait(IBInstaller.CoordinatorNameTB.similar((float) 0.5),2).click();
            screen.type(coordName);
        }
    }

}
