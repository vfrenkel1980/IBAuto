package Native.uitests;

import com.aventstack.extentreports.Status;
import frameworkInfra.testbases.SetupTestBase;
import frameworkInfra.utils.StaticDataProvider.*;
import org.sikuli.script.FindFailed;
import org.testng.Assert;
import org.testng.annotations.Test;

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
        Assert.assertTrue(ibService.verifyIbServicesRunning(true, true), "Services are not running!!!!");
        Assert.assertEquals(InitIBRoot.IB_ROOT, Locations.DIFFERENT_INSTALLATION_DIRECTORY, "Installed location " + InitIBRoot.IB_ROOT + " does not match expected location: "
                + Locations.DIFFERENT_INSTALLATION_DIRECTORY);
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
        Assert.assertTrue(ibService.verifyIbServicesRunning(true, false), "Services are not running!!!!");
        Assert.assertEquals(ibService.getCoordinator(), WindowsMachines.BABYLON, "Current coordinator" + ibService.getCoordinator() + " does not match expected coordinator: "
                + WindowsMachines.BABYLON);
    }

    @Test(testName = "Uninstall IB")
    public void uninstallIb(){
        ibService.installIB("2284", IbLicenses.UI_LIC);
/*        ibuiService.startIBUIInstaller("Latest");
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
        Assert.assertFalse(ibService.verifyIbServicesRunning(true, true), "Services are running!!!! Should be Uninstalled");*/
    }

    @Test(testName = "Repair IB")
    public void repairIb(){
        ibService.installIB("Latest", IbLicenses.UI_LIC);
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
        Assert.assertTrue(ibService.verifyIbServicesRunning(true, true), "Services are not running!!!!");
    }
}
