package Native.UnitTests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import frameworkInfra.testbases.BatmanBCTestBase;
import frameworkInfra.testbases.TestBase;
import frameworkInfra.utils.*;

import ibInfra.ibService.IbService;

import ibInfra.windowscl.WindowsService;

import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.sun.jna.platform.win32.WinReg.*;
import static frameworkInfra.Listeners.SuiteListener.extent;
import static frameworkInfra.Listeners.SuiteListener.htmlReporter;
import static frameworkInfra.Listeners.SuiteListener.test;


public class UnitTests extends TestBase{
    WindowsService winService = new WindowsService();
    IbService ibService = new IbService();
    static {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/TestOutput" + formatter.format(calendar.getTime()) + "- UnitTest.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @BeforeMethod
    public void beforeMethod(Method method, ITestContext context){
        test = extent.createTest(method.getName());
        test.log(Status.INFO, method.getName() + " test started");
        test.assignCategory(context.getName());
        log.info(method.getName() + " test started");
    }

    @Test(testName = "test1")
    public void testAttachment() {
        RegistryService.deleteRegKey(HKEY_CLASSES_ROOT,"WOW6432Node\\Interface","{8CA4C95D-CBE4-474A-AB9E-3F8C9313D740}");
    }
}


