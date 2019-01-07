package frameworkInfra.testbases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import frameworkInfra.Listeners.SuiteListener;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.SystemActions;
import frameworkInfra.utils.parsers.Parser;
import ibInfra.ibService.IIBService;
import ibInfra.ibService.IbService;
import ibInfra.windowscl.WindowsService;
import org.apache.log4j.PropertyConfigurator;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static frameworkInfra.Listeners.SuiteListener.*;
import static frameworkInfra.utils.StaticDataProvider.*;

@Listeners(SuiteListener.class)
public class WindowsTestBase extends TestBase {

    private static int ibVersion = 0;
    public WindowsService winService = new WindowsService();
    public IbService ibService = new IbService();
    public String testName = "";
    public String suiteName = "";

    static {
        ibVersion = IIBService.getIbVersion();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/TestOutput" + formatter.format(calendar.getTime()) + " - " + ibVersion + ".html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    /**
     * In the before suite we perform the following actions:
     * 1 - set the default registry values.
     * 2 - stop services.
     * 3 - delete logs.
     * 4 - start services
     *
     * @param predicted defines whether predicted will be 0/2
     * @param msBuild   defines whether msbuild will be 0/1.
     *                  <p>
     *                  Both params are grabbed hard coded from the batman/vmsim .xml
     */

    @BeforeSuite
    @Parameters({"predicted", "msBuild"})
    public void setUpEnv(String predicted, String msBuild, ITestContext context) {
        test = extent.createTest("Before Suite");
        test.assignCategory("BEFORE SUITE");
        test.log(Status.INFO, "BEFORE SUITE started");
        log.info("BEFORE SUITE started");

        if (!RegistryService.doesValueExist(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\Builder", RegistryKeys.SAVE_BUILD_PACKET)) {
            RegistryService.createRegValue(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\Builder", RegistryKeys.SAVE_BUILD_PACKET, "1");
        }
        String currentMsBuildReg = RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\builder", RegistryKeys.MSBUILD);
        String currentPredictedReg = RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\builder", RegistryKeys.PREDICTED);
        String currentLocalLogging = RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\builder", RegistryKeys.LOCAL_LOGGING);
        String currentStandalone = RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\builder", RegistryKeys.STANDALONE_MODE);
        String currentCustomStepReg = RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\Builder", RegistryKeys.CUSTOM_STEP_VS10_SUPPORT);

        //set default registry values for run
        verifyRegistry(msBuild, currentMsBuildReg, RegistryKeys.MSBUILD);
        verifyRegistry(predicted, currentPredictedReg, RegistryKeys.PREDICTED);
        verifyRegistry("0", currentLocalLogging, RegistryKeys.LOCAL_LOGGING);
        verifyRegistry("0", currentStandalone, RegistryKeys.STANDALONE_MODE);
        verifyRegistry("1", currentCustomStepReg, RegistryKeys.CUSTOM_STEP_VS10_SUPPORT);
        //stop agent service
        winService.runCommandWaitForFinish("net stop \"IncrediBuild Agent\" ");
        winService.runCommandWaitForFinish("net stop \"IncrediBuild Coordinator\" ");
        //kill tray icon
        SystemActions.killProcess(Processes.TRAY_ICON);
        suiteName = getSuiteName(context);
        if (!suiteName.equals("Babylon")) {
            //delete logs folder
            SystemActions.deleteFilesByPrefix(IbLocations.IB_ROOT + "\\logs\\Helper", "*");
            SystemActions.deleteFilesByPrefix(IbLocations.IB_ROOT + "\\logs", "*");
            //delete build logs folder
            SystemActions.deleteFilesByPrefix(Locations.QA_ROOT + "\\BuildLogs\\", "*");
        }
        //start agent service
        winService.runCommandWaitForFinish("net start \"IncrediBuild Agent\" ");
        winService.runCommandWaitForFinish("net start \"IncrediBuild Coordinator\" ");
    }

    @BeforeClass
    @Parameters({"logLevel"})
    public void init(String logLevel) {
        String log4jConfPath = "log4j.properties";
        PropertyConfigurator.configure(log4jConfPath);
        test = extent.createTest("Before Class - Change Logging Level to " + logLevel);
        test.assignCategory("BEFORE CLASS");
        test.log(Status.INFO, "BEFORE CLASS started");
        log.info("BEFORE CLASS started - Change Logging Level to" + logLevel);
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\Log", RegistryKeys.LOGGING_LEVEL, logLevel);
    }

    /**
     * After each test we validate that an message stating corrupt pdb's is not found in the log.
     *
     * @param result testng  test result
     * @throws IOException exceptions thrown for not being able to read log
     */

    @AfterMethod
    public void afterMethod(ITestResult result) throws IOException {
        if (new File(Locations.QA_ROOT, "buildlog.txt").exists()) {
            if (Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.PDB_ERROR))
                LogOutput.PDB_ERROR_TESTS = LogOutput.PDB_ERROR_TESTS + testName + "\n";
            SystemActions.deleteFile(Locations.OUTPUT_LOG_FILE);
        }
        extent.flush();
    }

    /**
     * We use the after suite in order to backup the client logs
     */

    @AfterSuite
    public void postSimulation() {
        //stop agent service
        winService.runCommandWaitForFinish("net stop \"IncrediBuild Agent\" ");
        //copy logs to backup folder
        SystemActions.copyFilesByExtension(IbLocations.IB_ROOT + "\\logs",
                Locations.QA_ROOT + "\\logs\\Post Simulation Client Logs\\Post_simulation__log_backup_", ".log", true);
        if (suiteName.equals("Babylon")) {
            SystemActions.deleteFilesByPrefix(IbLocations.IB_ROOT + "\\logs\\Helper", "*");
            SystemActions.deleteFilesByPrefix(IbLocations.IB_ROOT + "\\logs", "*");
        }
        //start agent service
        winService.runCommandWaitForFinish("net start \"IncrediBuild Agent\" ");
        SystemActions.startProcess(IbLocations.IB_ROOT + "\\" + Processes.TRAY_ICON);
        log.info("Suite finished");
    }

    private void verifyRegistry(String required, String current, String keyName) {
        if (!required.equals(current)) {
            RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\builder", keyName, required);
        }
    }

    public static String getSuiteName(ITestContext context) {
        return context.getCurrentXmlTest().getSuite().getName();
    }

}
