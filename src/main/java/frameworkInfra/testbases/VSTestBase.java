package frameworkInfra.testbases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.Status;
import frameworkInfra.Listeners.SuiteListener;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import ibInfra.ibService.IbService;
import ibInfra.vs.VSCommands;
import ibInfra.vs.VSUIService;
import ibInfra.windowscl.WindowsService;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static frameworkInfra.Listeners.SuiteListener.*;

@Listeners(SuiteListener.class)
public class VSTestBase extends TestBase {

    protected static int ibVersion = 0;
    private String SCENARIO = System.getProperty("scenario");
    public String VSINSTALLATION = System.getProperty("vsinstallation");
    public String devenvPath = "";
    public IbService ibService = new IbService();
    public VSUIService vsuiService;
    public VSCommands vsCommands = new VSCommands();
    public WindowsService winService = new WindowsService();

    static {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/TestOutput" + formatter.format(calendar.getTime()) + "-VSExtension.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @BeforeClass
    public void setUpEnv() {
        test = extent.createTest("Before Class");

        if (VSINSTALLATION.toLowerCase().equals("preview")){
            devenvPath = VsDevenvInstallPath.VS2017_PREVIEW;
        } else {
            devenvPath = VsDevenvInstallPath.VS2017_RELEASE;
            try {
                winService.downloadFile(URL.VS_RELEASE_URL, Locations.VS_INSTALL_DIR + "\\vs_professional.exe");
            } catch (IOException e) {
                e.getMessage();
            }
        }

        switch (SCENARIO) {
            //vs installed, install IB from installer
            case "1":
                test.log(Status.INFO, "Before class started\n SCENARIO 1: vs installed, install IB from installer");
                if (VSINSTALLATION.equals("15"))
                    vsCommands.upgradeVS();
                else
                    vsCommands.upgradeVSPreview();
                ibService.installIB("Latest");
                ibService.verifyIbServicesRunning();
                break;

            //upgrade vs and install IB from vs installer
            case "2":
                test.log(Status.INFO, "Before class started\n SCENARIO 2: upgrade vs and install IB from vs installer");
                if (VSINSTALLATION.equals("15"))
                    vsCommands.upgradeVSWithIB();
                else
                    vsCommands.upgradeVSPreviewWithIB();
                ibService.verifyIbServicesRunning();
                break;

            //install old IB, install vs and upgrade IB from VS installer
            case "3":
                test.log(Status.INFO, "Before class started\n SCENARIO 3: install old IB, install vs and upgrade IB from VS installer");
                ibService.installIB("2147");
                if (VSINSTALLATION.equals("15"))
                    vsCommands.installVSWithIB();
                else
                    vsCommands.installVSPreviewWithIB();
                ibService.verifyIbServicesRunning();

                SystemActions.killProcess("devenv.exe");
                break;

            //install vs without IB
            case "4":
                test.log(Status.INFO, "Before class started\n SCENARIO 4: install vs without IB");
                if (VSINSTALLATION.equals("15"))
                    vsCommands.installVSWithoutIB();
                else
                    vsCommands.installVSPreviewWithoutIB();
                test.log(Status.PASS, "");
                extent.flush();
                throw new SkipException("EXITING");

            //default for testing purpose
            default:
                break;
        }
        vsuiService = new VSUIService(driver);
        vsuiService.openVSInstance(VSINSTALLATION, true);
        driver.quit();
        driver = null;
        extent.flush();
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
        SystemActions.deleteFile(Locations.OUTPUT_LOG_FILE);
        if (driver != null) {
            SystemActions.killProcess("devenv.exe");
            driver.quit();
        }
        driver = null;
        extent.flush();
    }

    @AfterClass
    public void afterClass(){
        test = extent.createTest("AFTER CLASS");
        test.log(Status.INFO, "AFTER CLASS started");
        test.assignCategory(VSINSTALLATION + " " + SCENARIO);
        log.info("AFTER CLASS started");
        ibService.unloadIbLicense();
        SystemActions.sleep(5);
        ibService.isLicenseLoaded();
        extent.flush();
    }

}
