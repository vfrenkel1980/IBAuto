package frameworkInfra.testbases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.SystemActions;
import ibInfra.windowscl.IWindowsCL;
import ibInfra.windowscl.WindowsCLService;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;

public class WindowsTestBase extends TestBase {

    protected WindowsCLService runWin = new WindowsCLService();
    private static int ibVersion = 0;

    static {
        ibVersion = IWindowsCL.getIbVersion();
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

        String currentMsBuildReg = RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\builder", StaticDataProvider.RegistryKeys.MSBUILD);
        String currentPredictedReg = RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\builder", StaticDataProvider.RegistryKeys.PREDICTED);
        String currentLocalLogging = RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\builder", StaticDataProvider.RegistryKeys.LOCAL_LOGGING);
        String currentStandalone = RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\builder", StaticDataProvider.RegistryKeys.STANDALONE_MODE);

        //set default registry values for run
        verifyRegistry(msBuild, currentMsBuildReg, StaticDataProvider.RegistryKeys.MSBUILD);
        verifyRegistry(predicted, currentPredictedReg, StaticDataProvider.RegistryKeys.PREDICTED);
        verifyRegistry("0", currentLocalLogging, StaticDataProvider.RegistryKeys.LOCAL_LOGGING);
        verifyRegistry("0", currentStandalone, StaticDataProvider.RegistryKeys.STANDALONE_MODE);
        //copy latest extent report to backup folder
        SystemActions.copyFilesByExtension(StaticDataProvider.Locations.WORKSPACE_REPORTS, StaticDataProvider.Locations.QA_ROOT + "\\Logs\\Automation HTML Reports", ".html", false);
        //delete HTML report from workspace folder
        SystemActions.deleteFilesByPrefix(StaticDataProvider.Locations.WORKSPACE_REPORTS, "Test");
        //stop agent service
        runWin.runCommandWaitForFinish("net stop \"IncrediBuild Agent\" ");
        //kill tray icon
        SystemActions.killProcess(StaticDataProvider.Processes.TRAY_ICON);
        //delete logs folder
        SystemActions.deleteFilesByPrefix(StaticDataProvider.Locations.IB_ROOT + "\\logs", "*");
        //delete build logs folder
        SystemActions.deleteFilesByPrefix(StaticDataProvider.Locations.QA_ROOT + "\\BuildLogs\\", "*");
        //start agent service
        runWin.runCommandWaitForFinish("net start \"IncrediBuild Agent\" ");

    }

    @BeforeClass
    @Parameters ({ "logLevel"})
    public static void init(String logLevel){
        String log4jConfPath = "log4j.properties";
        PropertyConfigurator.configure(log4jConfPath);
        test = extent.createTest("Before Class - Change Logging Level to " + logLevel);
        test.assignCategory("BEFORE CLASS");
        test.log(Status.INFO, "BEFORE CLASS started");
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\Log", StaticDataProvider.RegistryKeys.LOGGING_LEVEL, logLevel );
    }

    @AfterSuite
    public void postSimulation(){
        //stop agent service
        runWin.runCommandWaitForFinish("net stop \"IncrediBuild Agent\" ");
        //copy logs to backup folder
        SystemActions.copyFilesByExtension(StaticDataProvider.Locations.IB_ROOT + "\\logs",
                StaticDataProvider.Locations.QA_ROOT + "\\logs\\Post Simulation Client Logs\\Post_simulation__log_backup_", ".log", true);
        //TODO: parse .log for assertion error
        //TODO: parse .txt for access violations


        //start agent service
        runWin.runCommandWaitForFinish("net start \"IncrediBuild Agent\" ");
        SystemActions.startProcess(StaticDataProvider.Locations.IB_ROOT + "\\" + StaticDataProvider.Processes.TRAY_ICON);
    }

    private void verifyRegistry(String required, String current, String keyName){
        if (!required.equals(current)){
            RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\builder", keyName, required);
        }
    }


}
