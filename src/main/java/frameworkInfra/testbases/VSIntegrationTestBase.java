package frameworkInfra.testbases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.SystemActions;
import ibInfra.ibService.IIBService;
import ibInfra.ibService.IbService;
import ibInfra.vsui.VSUIService;
import io.appium.java_client.windows.WindowsDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class VSIntegrationTestBase extends VSTestBase {

    static {
        ibVersion = IIBService.getIbVersion();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/TestOutput" + formatter.format(calendar.getTime()) + ibVersion + ".html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @BeforeClass
    public void setUpEnv() {
        test = extent.createTest("Before Class");
    }

    @BeforeMethod
    @Parameters({"VCVersion"})
    public void beforeMethod(Method method, ITestContext context, String VCVersion){
        test = extent.createTest(method.getName());
        test.log(Status.INFO, method.getName() + " test started");
        test.assignCategory("VC" + VCVersion);
        log.info(method.getName() + " test started");

        vsService.openVS2017instance(VCVersion);
    }

    @AfterMethod
    public void afterMethod(ITestResult result) throws IOException {
        if (driver != null) {
            driver.quit();
        }
        driver = null;
        getResult(result);
    }

    @AfterClass
    public void afterClass(){
        extent.flush();
    }
}
