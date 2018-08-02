package frameworkInfra.testbases.web.dashboard;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import frameworkInfra.Listeners.SuiteListener;
import frameworkInfra.testbases.TestBase;
import frameworkInfra.utils.databases.PostgresJDBC;
import ibInfra.ibService.IbService;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.*;
import webInfra.dashboard.pageObjects.BuildsPageObject;
import webInfra.ibWeb.pageObjects.DownloadPageObject;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import static frameworkInfra.Listeners.SuiteListener.extent;
import static frameworkInfra.Listeners.SuiteListener.htmlReporter;
import static frameworkInfra.Listeners.SuiteListener.test;

@Listeners(SuiteListener.class)
public class DashboardTestBase extends TestBase {

    protected BuildsPageObject buildPageObject;
    protected PostgresJDBC postgresJDBC = new PostgresJDBC();
    protected IbService ibService = new IbService();

    static {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/TestOutput" + formatter.format(calendar.getTime()) + "- DASHBOARD.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @BeforeSuite
    public void beforeSuite(){
        test = extent.createTest("Before Suite");
        ibService.updateIBEnt();
    }

    @BeforeClass
    public void beforeClass() {
        test = extent.createTest("Before Class");
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/main/resources/WebDrivers/chromedriver.exe");
        webDriver = new ChromeDriver();
        eventWebDriver = new EventFiringWebDriver(webDriver);
        eventWebDriver.register(handler);
        eventWebDriver.get("http://localhost:8000/#/");
        eventWebDriver.manage().window().maximize();
        eventWebDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        buildPageObject = new BuildsPageObject(eventWebDriver);
    }

    @BeforeMethod
    public void beforeMethod(Method method, ITestContext context){
        test = extent.createTest(method.getName());
        test.log(Status.INFO, method.getName() + " test started");
        test.assignCategory(context.getName());
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
