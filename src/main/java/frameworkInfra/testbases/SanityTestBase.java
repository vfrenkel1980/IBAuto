package frameworkInfra.testbases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import frameworkInfra.utils.Parser;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.SystemActions;
import ibInfra.ibService.IIBService;
import ibInfra.ibService.IbService;
import ibInfra.windowscl.WindowsService;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static frameworkInfra.Listeners.SuiteListener.extent;
import static frameworkInfra.Listeners.SuiteListener.htmlReporter;
import static frameworkInfra.Listeners.SuiteListener.test;

public class SanityTestBase extends TestBase {

    private static int ibVersion = 0;
    public WindowsService winService = new WindowsService();
    public IbService ibService = new IbService();
    public String testName = "";

    static {
        ibVersion = IIBService.getIbVersion();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/TestOutput" + formatter.format(calendar.getTime()) + " - " + ibVersion + ".html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @BeforeMethod
    public void beforeMethod(Method method,ITestContext testContext){
        testName = getTestName(method);
        test = extent.createTest(testName);
        test.assignCategory("SANITY");
        test.log(Status.INFO, method.getName() + " test started");
        testName = testContext.getName();
    }

    @AfterMethod
    public void afterMethod(ITestResult result) throws IOException {
        if (new File(StaticDataProvider.Locations.QA_ROOT, "buildlog.txt").exists()) {
            if (Parser.doesFileContainString(StaticDataProvider.Locations.OUTPUT_LOG_FILE, StaticDataProvider.LogOutput.PDB_ERROR))
                StaticDataProvider.LogOutput.PDB_ERROR_TESTS = StaticDataProvider.LogOutput.PDB_ERROR_TESTS + testName + "\n";
            SystemActions.deleteFile(StaticDataProvider.Locations.OUTPUT_LOG_FILE);
        }
        extent.flush();
    }
}
