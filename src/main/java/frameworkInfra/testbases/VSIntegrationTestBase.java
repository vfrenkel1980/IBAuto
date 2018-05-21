package frameworkInfra.testbases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import frameworkInfra.Listeners.SuiteListener;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import ibInfra.ibService.IIBService;
import ibInfra.ibService.IbService;
import ibInfra.vs.VSUIService;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static frameworkInfra.Listeners.SuiteListener.*;

@Listeners(SuiteListener.class)
public class VSIntegrationTestBase extends TestBase {

    protected static int ibVersion = 0;
    public IbService ibService = new IbService();
    public VSUIService vsuiService = new VSUIService();
    protected String projectPath = "";
    protected String projectName = "";

    static {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/TestOutput" + formatter.format(calendar.getTime()) + " - VSIntegration.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @BeforeSuite
    public void beforeSuite(){
        ibService.disableVsMonitor();
        ibService.installIB("Latest", IbLicenses.UI_LIC);
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT +"\\Builder", RegistryKeys.SAVE_BUILD_PACKET, "1");
    }

    @BeforeClass
    @Parameters({"VCVersion"})
    public void setUpEnv(String VCVersion) {
        test = extent.createTest("Before Class");
        test.assignCategory("VC" + VCVersion);
        try {
            vsuiService.openVSInstance(VCVersion, false);
            switch (VCVersion) {
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
            vsuiService.openProject(projectPath);
        }catch (Exception e){
            e.getMessage();
        }
        finally {
            extent.flush();
        }
    }

    @BeforeMethod
    @Parameters({"VCVersion"})
    public void beforeMethod(Method method, ITestContext context, String VCVersion){
        test = extent.createTest(method.getName());
        test.log(Status.INFO, method.getName() + " test started");
        test.assignCategory("VC" + VCVersion);
        log.info(method.getName() + " test started");
    }

    @AfterClass
    public void afterClass(){
        vsuiService.killDriver();
        extent.flush();
    }
}
