package frameworkInfra.testbases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import ibInfra.ibService.IIBService;
import ibInfra.ibService.IbService;
import ibInfra.vsui.VSUIService;
import io.appium.java_client.windows.WindowsDriver;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class VSIntegrationTestBase extends TestBase {

    public static WindowsDriver driver = null;
    private static int ibVersion = 0;
    public String devenvPath = "";
    public IbService ibService = new IbService();
    public VSUIService vsService = new VSUIService();

    static {
        ibVersion = IIBService.getIbVersion();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/TestOutput" + formatter.format(calendar.getTime()) + ibVersion + ".html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
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
}
