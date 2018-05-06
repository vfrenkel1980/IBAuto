package Native.uitests;

import frameworkInfra.testbases.SetupTestBase;
import org.sikuli.script.FindFailed;
import org.testng.annotations.Test;

public class SetupTests extends SetupTestBase {

    @Test(testName = "Test")
    public void test(){
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
            installer.changeInstallationPath("c:\\incredibuild");
            installer.clickNext();
            installer.clickNext();
            installer.browseLicense();
            installer.browseLicenseNavigateToDesktop();
            installer.selectLicense();
            installer.clickNext();
            installer.cancelReleaseNotes();
            installer.clickFinish();
        } catch (FindFailed e) {
            e.getMessage();
        }
        ibService.verifyIbServicesRunning();
    }
}
