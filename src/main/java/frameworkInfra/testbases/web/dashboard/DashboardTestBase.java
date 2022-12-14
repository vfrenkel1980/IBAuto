package frameworkInfra.testbases.web.dashboard;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import frameworkInfra.Listeners.SuiteListener;
import frameworkInfra.testbases.TestBase;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.databases.PostgresJDBC;
import frameworkInfra.utils.databases.SQLiteJDBC;
import ibInfra.ibService.IbService;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.*;
import webInfra.dashboard.helpers.DashboardHelper;
import webInfra.dashboard.pageObjects.BuildsPageObject;
import webInfra.dashboard.pageObjects.OverviewPageObject;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static frameworkInfra.Listeners.SuiteListener.extent;
import static frameworkInfra.Listeners.SuiteListener.htmlReporter;
import static frameworkInfra.Listeners.SuiteListener.test;

@Listeners(SuiteListener.class)
public class DashboardTestBase extends TestBase {

    protected BuildsPageObject buildPageObject;
    protected OverviewPageObject overviewPageObject;
    protected DashboardHelper dashboardHelper = new DashboardHelper();
    protected PostgresJDBC postgresJDBC = new PostgresJDBC();
    protected SQLiteJDBC sqLiteJDBC = new SQLiteJDBC();
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
        ibService.installIB(IB_VERSION, IbLicenses.DASHBOARD_LIC);
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_SUCCESS, "%s"));
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_FAIL, "%s"));
    }

    @BeforeClass
    public void beforeClass() {

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

    }

    @AfterSuite
    public void afterSuite(){
        ibService.uninstallIB(IB_VERSION);
    }

}
