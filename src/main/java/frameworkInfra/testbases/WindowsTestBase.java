package frameworkInfra.testbases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import frameworkInfra.Listeners.SuiteListener;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.SystemActions;
import ibInfra.ibService.IIBService;
import ibInfra.ibService.IbService;
import ibInfra.windowscl.WindowsService;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.*;

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

    static {
        ibVersion = IIBService.getIbVersion();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/TestOutput" + formatter.format(calendar.getTime()) + " - " + ibVersion + ".html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @BeforeSuite
    @Parameters({"predicted", "msBuild"})
    public void setUpEnv(String predicted, String msBuild){
        test = extent.createTest("Before Suite");
        test.assignCategory("BEFORE SUITE");
        test.log(Status.INFO, "BEFORE SUITE started");
        log.info("BEFORE SUITE started");

        String currentMsBuildReg = RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\builder", RegistryKeys.MSBUILD);
        String currentPredictedReg = RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\builder", RegistryKeys.PREDICTED);
        String currentLocalLogging = RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\builder", RegistryKeys.LOCAL_LOGGING);
        String currentStandalone = RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\builder", RegistryKeys.STANDALONE_MODE);

        //set default registry values for run
        verifyRegistry(msBuild, currentMsBuildReg, RegistryKeys.MSBUILD);
        verifyRegistry(predicted, currentPredictedReg, RegistryKeys.PREDICTED);
        verifyRegistry("0", currentLocalLogging, RegistryKeys.LOCAL_LOGGING);
        verifyRegistry("0", currentStandalone, RegistryKeys.STANDALONE_MODE);
        //copy latest extent report to backup folder
        SystemActions.copyFilesByExtension(Locations.WORKSPACE_REPORTS, Locations.QA_ROOT + "\\Logs\\Automation HTML Reports", ".html", false);
        //delete HTML report from workspace folder
        SystemActions.deleteFilesByPrefix(Locations.WORKSPACE_REPORTS, "Test");
        //stop agent service
        winService.runCommandWaitForFinish("net stop \"IncrediBuild Agent\" ");
        //kill tray icon
        SystemActions.killProcess(Processes.TRAY_ICON);
        //delete logs folder
        SystemActions.deleteFilesByPrefix(InitIBRoot.IB_ROOT + "\\logs", "*");
        SystemActions.deleteFilesByPrefix(InitIBRoot.IB_ROOT + "\\logs\\Helper", "*");
        //delete build logs folder
        SystemActions.deleteFilesByPrefix(Locations.QA_ROOT + "\\BuildLogs\\", "*");
        //start agent service
        winService.runCommandWaitForFinish("net start \"IncrediBuild Agent\" ");
    }

    @BeforeClass
    @Parameters ({ "logLevel"})
    public static void init(String logLevel){
        String log4jConfPath = "log4j.properties";
        PropertyConfigurator.configure(log4jConfPath);
        test = extent.createTest("Before Class - Change Logging Level to " + logLevel);
        test.assignCategory("BEFORE CLASS");
        test.log(Status.INFO, "BEFORE CLASS started");
        log.info("BEFORE CLASS started - Change Logging Level to" + logLevel);

        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\Log", RegistryKeys.LOGGING_LEVEL, logLevel );
    }

    @AfterSuite
    public void postSimulation(){
        //stop agent service
        winService.runCommandWaitForFinish("net stop \"IncrediBuild Agent\" ");
        //copy logs to backup folder
        SystemActions.copyFilesByExtension(InitIBRoot.IB_ROOT + "\\logs",
                Locations.QA_ROOT + "\\logs\\Post Simulation Client Logs\\Post_simulation__log_backup_", ".log", true);
        //TODO: parse .log for assertion error
        //TODO: parse .txt for access violations


        //start agent service
        winService.runCommandWaitForFinish("net start \"IncrediBuild Agent\" ");
        SystemActions.startProcess(InitIBRoot.IB_ROOT + "\\" + Processes.TRAY_ICON);
        log.info("Suite finished");
    }

    private void verifyRegistry(String required, String current, String keyName){
        if (!required.equals(current)){
            RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\builder", keyName, required);
        }
    }


}
