package frameworkInfra.testbases.web;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import frameworkInfra.testbases.TestBase;
import frameworkInfra.utils.SystemActions;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import webInfra.ibWeb.pageObjects.DownloadPageObject;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static frameworkInfra.Listeners.SuiteListener.extent;
import static frameworkInfra.Listeners.SuiteListener.htmlReporter;
import static frameworkInfra.Listeners.SuiteListener.test;

public class IbWebTestBase extends TestBase {

    protected String host = "imap.gmail.com";
    protected String mailAddress = "incrediautomation@gmail.com";
    protected String mailAddress2 = "incrediautomation2@gmail.com";
    protected String mailAddress3 = "incrediautomation3@gmail.com";
    protected String password = "4illumination";

    static {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/TestOutput" + formatter.format(calendar.getTime()) + "- IBWEB.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @BeforeMethod
    @Parameters({"lang"})
    public void beforeMethod(Method method, ITestContext context, String lang){
        test = extent.createTest(method.getName());
        test.log(Status.INFO, method.getName() + " test started");
        test.assignCategory(context.getName() + " - " + lang);
        log.info(method.getName() + " test started");
    }

    @AfterClass
    public void afterClass(){
        if (webDriver != null) {
            webDriver.quit();
            eventWebDriver.quit();
            eventWebDriver.unregister(handler);
        }
        webDriver = null;
        extent.flush();
    }


}
