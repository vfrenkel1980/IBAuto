package frameworkInfra.testbases.web.dashboard;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import frameworkInfra.testbases.TestBase;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.databases.PostgresJDBC;
import frameworkInfra.utils.databases.SQLiteJDBC;
import ibInfra.ibService.IbService;
import ibInfra.ibUIService.IBUIService;
import ibInfra.windowscl.WindowsService;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static frameworkInfra.Listeners.SuiteListener.extent;
import static frameworkInfra.Listeners.SuiteListener.htmlReporter;
import static frameworkInfra.Listeners.SuiteListener.test;

public class DBSchemasTestBase extends TestBase {

    protected PostgresJDBC postgresJDBC = new PostgresJDBC();
    protected SQLiteJDBC sqLiteJDBC = new SQLiteJDBC();
    public WindowsService winService = new WindowsService();
    protected IbService ibService = new IbService();
    protected IBUIService ibuiService = new IBUIService();
    protected IBUIService.Installer installer = ibuiService.new Installer();


    static {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/TestOutput" + formatter.format(calendar.getTime()) + "- DBSCHEME.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        testName = getTestName(method);
        test = extent.createTest(testName);
        test.assignCategory("DBSchemas test");
        test.log(Status.INFO, method.getName() + " test started");
    }

    @AfterSuite
    public void afterSuite(){
        ibService.uninstallIB(IB_VERSION);
    }



}
