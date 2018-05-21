package frameworkInfra.testbases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import frameworkInfra.Listeners.SuiteListener;
import ibInfra.ibService.IIBService;
import ibInfra.ibService.IbService;
import ibInfra.ibUIService.IBUIService;
import ibInfra.windowscl.WindowsService;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.aventstack.extentreports.Status.INFO;
import static frameworkInfra.Listeners.SuiteListener.*;

@Listeners(SuiteListener.class)
public class SetupTestBase extends TestBase {

    public WindowsService winService = new WindowsService();
    protected IBUIService ibuiService = new IBUIService();
    public IbService ibService = new IbService();
    protected IBUIService.Installer installer = ibuiService.new Installer();


    static {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/TestOutput" + formatter.format(calendar.getTime()) + " - Setup.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @BeforeSuite
    public void beforeSuite() {
        try {
            int version = IIBService.getIbVersion();
            ibService.uninstallIB(String.valueOf(version));
        }
        catch (Exception e)
        {
            e.getMessage();
            test.log(INFO, "Nothing to remove, IB is not installed");
        }
    }

    @BeforeMethod
    public void beforeMethod(Method method, ITestContext context) {
        test = extent.createTest(method.getName());
        test.log(INFO, method.getName() + " test started");
        test.assignCategory(context.getName());
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        ibService.uninstallIB("Latest");
        extent.flush();
    }
}
