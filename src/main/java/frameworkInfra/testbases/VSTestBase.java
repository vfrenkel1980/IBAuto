package frameworkInfra.testbases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.Status;
import frameworkInfra.Listeners.SuiteListener;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import frameworkInfra.utils.databases.PostgresJDBC;
import ibInfra.ibService.IbService;
import ibInfra.vs.VSCommands;
import ibInfra.vs.VSUIService;
import ibInfra.windowscl.WindowsService;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.*;
import org.testng.internal.Systematiser;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static frameworkInfra.Listeners.SuiteListener.*;

@Listeners(SuiteListener.class)
public class VSTestBase extends TestBase {

    protected static int ibVersion = 0;
    protected static String SCENARIO = System.getProperty("scenario");
    public String VSINSTALLATION = System.getProperty("vsinstallation");
    public String devenvPath = "";
    public IbService ibService = new IbService();
    public VSUIService vsuiService = new VSUIService();
    public VSCommands vsCommands = new VSCommands();
    public WindowsService winService = new WindowsService();
    public PostgresJDBC postgresJDBC = new PostgresJDBC();

    static {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/TestOutput" + formatter.format(calendar.getTime()) + "_SCENARIO_" + SCENARIO + ".html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    /**
     * This class is used in order to test different scenarios for IB and VS installations.
     * We use the parameter SCENARIO in order to distinguish between installation scenarios:
     * 1 - vs installed, install IB from installer
     * 2 - upgrade vs and install IB from vs installer
     * 3 - install old IB, install vs and upgrade IB from VS installer
     * 4 - install vs without IB
     *
     * @exception SkipException is thrown in scenario 4 in order to avoid performing
     * test class as IB is not installed in this scenario.
     *
     */

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

            case "1":
                test.log(Status.INFO, "Before class started\n SCENARIO 1: vs installed, install IB from installer");
                if (VSINSTALLATION.equals("15"))
                    vsCommands.upgradeVS();
                else
                    vsCommands.upgradeVSPreview();
                ibService.installIB("Latest", IbLicenses.VSTESTS_LIC);
                ibService.verifyIbServicesRunning(true, true);
                break;

            case "2":
                test.log(Status.INFO, "Before class started\n SCENARIO 2: upgrade vs and install IB from vs installer");
                if (VSINSTALLATION.equals("15"))
                    vsCommands.upgradeVSWithIB();
                else
                    vsCommands.upgradeVSPreviewWithIB();
                ibService.verifyIbServicesRunning(true, true);
                break;

            case "3":
                test.log(Status.INFO, "Before class started\n SCENARIO 3: install old IB, install vs and upgrade IB from VS installer");
                ibService.installIB("2147", IbLicenses.VSTESTS_LIC);
                if (VSINSTALLATION.equals("15"))
                    vsCommands.installVSWithIB();
                else
                    vsCommands.installVSPreviewWithIB();
                ibService.verifyIbServicesRunning(true, true);
                vsuiService.openVSInstance(VSINSTALLATION, true, SCENARIO);
                SystemActions.killProcess("devenv.exe");
                break;

            case "4":
                test.log(Status.INFO, "Before class started\n SCENARIO 4: install vs without IB");
                if (VSINSTALLATION.equals("15"))
                    vsCommands.installVSWithoutIB();
                else
                    vsCommands.installVSPreviewWithoutIB();
                test.log(Status.PASS, "");
                extent.flush();
                throw new SkipException("EXITING");

            default:
                break;
        }
        extent.flush();
    }

    @BeforeMethod
    public void beforeMethod(Method method, ITestContext context){
        test = extent.createTest(method.getName());
        test.log(Status.INFO, method.getName() + " test started");
        test.assignCategory("VC: " + VSINSTALLATION + " Scenario: " + SCENARIO);
        log.info(method.getName() + " test started");
    }

    @AfterMethod
    public void afterMethod(Method method,ITestResult result) throws IOException {
        SystemActions.copyFile(Locations.OUTPUT_LOG_FILE, "z:\\buildLog_" + method.getName() + "_" + "SCENARIO_" + SCENARIO + ".txt");
        SystemActions.deleteFile(Locations.OUTPUT_LOG_FILE);
        vsuiService.killDriver();
        extent.flush();
    }

    @AfterClass
    public void afterClass(){
        test = extent.createTest("AFTER CLASS");
        test.log(Status.INFO, "AFTER CLASS started");
        test.assignCategory("VC: " + VSINSTALLATION + " Scenario: " + SCENARIO);
        log.info("AFTER CLASS started");
        ibService.unloadIbLicense();
        SystemActions.sleep(5);
        ibService.isLicenseLoaded();
        extent.flush();
    }

}
