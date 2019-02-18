package frameworkInfra.testbases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import frameworkInfra.Listeners.SuiteListener;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import ibInfra.ibService.IIBService;
import ibInfra.ibService.IbService;
import ibInfra.windowscl.WindowsService;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import static com.sun.jna.platform.win32.WinReg.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static frameworkInfra.Listeners.SuiteListener.*;

@Listeners(SuiteListener.class)
public class ReleaseTestBase extends TestBase {

    public WindowsService winService = new WindowsService();
    public IbService ibService = new IbService();
    protected String trialLicenseFile;

    static {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/TestOutput" + formatter.format(calendar.getTime()) + " - License Tests" + ".html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @BeforeSuite
    public void updateTrialLicense() {
        try {
            SystemActions.deleteFilesByPrefix(Locations.QA_ROOT + "\\License\\", "IncrediBuild FreeDev license");
            List<String> licFileList = SystemActions.getAllFilesInDirectory(Locations.TRIAL_LICENSE_PATH);
            trialLicenseFile = licFileList.get(0);
            SystemActions.copyFile(Locations.TRIAL_LICENSE_PATH + trialLicenseFile, Locations.QA_ROOT + "\\License\\" + trialLicenseFile);
            test.log(Status.INFO, "Trial license is updated for suite");
        }catch(Exception e){
            test.log(Status.ERROR, "Failed to update trial licence with ERROR"+e.getMessage());
        }
    }


    @AfterSuite
    public void deleteTrialLicenseRegKey() {
        RegistryService.deleteRegKey(HKEY_CLASSES_ROOT,"WOW6432Node\\Interface",RegistryKeys.GUID);
        test.log(Status.INFO, "Trial license regKey is deleted");
    }

}
