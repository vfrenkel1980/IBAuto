package frameworkInfra.testbases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.Status;
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

public class VSTestBase extends TestBase {

    public static WindowsDriver driver = null;
    private static int ibVersion = 0;
    public VSUIService vsService = new VSUIService();
    public IbService runIb = new IbService();
    private String SCENARIO = System.getProperty("scenario");
    public String VSINSTALLATION = System.getProperty("vsinstallation");
    public String DevenvPath = "";

    static {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/TestOutput" + formatter.format(calendar.getTime()) + ".html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @BeforeClass
    public void setUpEnv() {
        test = extent.createTest("Before Class");

        if (VSINSTALLATION.toLowerCase().equals("preview")){
            DevenvPath = StaticDataProvider.VsDevenvInstallPath.VS2017_PREVIEW;
        } else {
            DevenvPath = StaticDataProvider.VsDevenvInstallPath.VS2017_RELEASE;
        }

        switch (SCENARIO) {
            //vs installed, install IB from installer
            case "1":
                test.log(Status.INFO, "Before class started\n SCENARIO 1: vs installed, install IB from installer");
                runIb.installIB("Latest");
                runIb.verifyIbServicesRunning();
                break;

            //upgrade vs and install IB from vs installer
            case "2":
                test.log(Status.INFO, "Before class started\n SCENARIO 2: upgrade vs and install IB from vs installer");
                if (VSINSTALLATION.equals("release"))
                    vsService.upgradeVSWithIB();
                else
                    vsService.upgradeVSPreviewWithIB();
                runIb.verifyIbServicesRunning();
                break;

            //install old IB, install vs and upgrade IB from VS installer
            case "3":
                test.log(Status.INFO, "Before class started\n SCENARIO 3: install old IB, install vs and upgrade IB from VS installer");
                runIb.installIB("2147");
                if (VSINSTALLATION.equals("release"))
                    vsService.installVSWithIB();
                else
                    vsService.installVSPreviewWithIB();
                runIb.verifyIbServicesRunning();
                break;

            //install vs without IB
            case "4":
                test.log(Status.INFO, "Before class started\n SCENARIO 4: install vs without IB");
                if (VSINSTALLATION.equals("release"))
                    vsService.installVSWithoutIB();
                else
                    vsService.installVSPreviewWithoutIB();
                extent.flush();
                System.exit(0);
                break;

            //default for testing purpose
            default:
                break;
        }
    }

    @BeforeMethod
    public void beforeMethod(Method method, ITestContext context){
        test = extent.createTest(method.getName());
        test.log(Status.INFO, method.getName() + " test started");
        test.assignCategory(VSINSTALLATION + " " + SCENARIO);
        log.info(method.getName() + " test started");
    }

    @AfterMethod
    public void afterMethod(ITestResult result) throws IOException {
        SystemActions.deleteFilesByPrefix(StaticDataProvider.Locations.QA_ROOT, "BuildLog");
        if (driver != null) {
            driver.quit();
        }
        driver = null;
        getResult(result);
    }

    @AfterClass
    public void afterClass(){
        runIb.uninstallIB(String.valueOf(IIBService.getIbVersion()));
        extent.flush();
    }

}
