package frameworkInfra.testbases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import frameworkInfra.Listeners.SuiteListener;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import ibInfra.ibService.IIBService;
import ibInfra.ibService.IbService;
import ibInfra.windowscl.WindowsService;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static frameworkInfra.Listeners.SuiteListener.*;

@Listeners(SuiteListener.class)
public class ReleaseTestBase extends TestBase {

    public WindowsService winService = new WindowsService();
    public IbService ibService = new IbService();
    protected static String trialLicenseFile = "";

    static {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/TestOutput" + formatter.format(calendar.getTime()) + " - License Tests" + ".html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @BeforeSuite
    public void beforeSuite() {
        SystemActions.deleteFilesByPrefix(Locations.QA_ROOT + "\\License\\", "*IncrediBuild FreeDev license");
        List<String> licFileList = SystemActions.getAllFilesInDirectory(Locations.TRIAL_LICENSE_PATH);
        trialLicenseFile = licFileList.get(0);
        SystemActions.copyFile(Locations.TRIAL_LICENSE_PATH + trialLicenseFile, Locations.QA_ROOT + "\\License\\"+ trialLicenseFile);
    }

}
