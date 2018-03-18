package frameworkInfra.testbases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider.*;
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

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;

public class VSIntegrationTestBase extends TestBase {

    public static WindowsDriver driver = null;
    protected static int ibVersion = 0;
    public IbService ibService = new IbService();
    public VSUIService vsService = new VSUIService();
    protected String projectPath = "";
    protected String projectName = "";

    static {
        ibVersion = IIBService.getIbVersion();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/TestOutput" + formatter.format(calendar.getTime()) + ibVersion + ".html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @BeforeSuite
    public void beforeSuite(){
        ibService.disableVsMonitor();
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT +"\\Builder", RegistryKeys.SAVE_BUILD_PACKET, "1");
    }

    @BeforeClass
    @Parameters({"VCVersion"})
    public void setUpEnv(String VCVersion) {
        test = extent.createTest("Before Class");
        ibService.updateIB("Latest");
        vsService.openVS2017instance(VCVersion);
        switch (VCVersion){
            case "8":
                projectPath = TestProjects.VC8PROJECT;
                projectName = "vc8project";
                break;
            case "9":
                projectPath = TestProjects.VC9PROJECT;
                projectName = "vc9project";
                break;
            case "10":
                projectPath = TestProjects.VC10PROJECT;
                projectName = "vc10project";
                break;
            case "11":
                projectPath = TestProjects.VC11PROJECT;
                projectName = "vc11project";
                break;
            case "12":
                projectPath = TestProjects.VC12PROJECT;
                projectName = "vc12project";
                break;
            case "14":
                projectPath = TestProjects.VC14PROJECT;
                projectName = "vc14project";
                break;
            case "15":
                projectPath = TestProjects.VC15PROJECT;
                projectName = "vc15project";
                break;
        }
        vsService.openProject(projectPath);
    }

    @BeforeMethod
    @Parameters({"VCVersion"})
    public void beforeMethod(Method method, ITestContext context, String VCVersion){
        test = extent.createTest(method.getName());
        test.log(Status.INFO, method.getName() + " test started");
        test.assignCategory("VC" + VCVersion);
        log.info(method.getName() + " test started");
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
