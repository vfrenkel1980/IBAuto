package frameworkInfra.testbases;

import com.aventstack.extentreports.Status;
import frameworkInfra.utils.ConfigurationReader;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.SystemActions;
import ibInfra.windowscl.WindowsCLService;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.*;

import java.lang.reflect.Method;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;

public class VmBCTestBase extends TestBase {

    protected WindowsCLService runCommand = new WindowsCLService();
    private ConfigurationReader cfg = new ConfigurationReader();


    @BeforeSuite
    @Parameters({"predicted", "msBuild"})
    public void setUpEnv(String predicted, String msBuild){
        test = extent.createTest("Before Suite");
        test.assignCategory("BEFORE SUITE");
        test.log(Status.INFO, "BEFORE SUITE started");

        //String requiredMsBuildReg = cfg.getProperty("MSBuildReg");
        //String requiredPredictedReg = cfg.getProperty(("PredictedReg"));
        String currentMsBuildReg = RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\builder", StaticDataProvider.RegistryKeys.MSBUILD);
        String currentPredictedReg = RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\builder", StaticDataProvider.RegistryKeys.PREDICTED);
        String currentLocalLogging = RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\builder", StaticDataProvider.RegistryKeys.LOCAL_LOGGING);
        String currentStandalone = RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\builder", StaticDataProvider.RegistryKeys.STANDALONE_MODE);

        //set default registry values for run
        verifyRegistry(msBuild, currentMsBuildReg, StaticDataProvider.RegistryKeys.MSBUILD);
        verifyRegistry(predicted, currentPredictedReg, StaticDataProvider.RegistryKeys.PREDICTED);
        verifyRegistry("0", currentLocalLogging, StaticDataProvider.RegistryKeys.LOCAL_LOGGING);
        verifyRegistry("0", currentStandalone, StaticDataProvider.RegistryKeys.STANDALONE_MODE);
        //get IB version
        ibVersion = getIBVersion();
        //copy latest extent report to backup folder
        SystemActions.copyFilesByExtension(StaticDataProvider.Locations.WORKSPACE_REPORTS, StaticDataProvider.Locations.QA_ROOT + "\\Logs\\Automation HTML Reports", ".html", false);
        //delete HTML report from workspace folder
        SystemActions.deleteFilesByPrefix(StaticDataProvider.Locations.WORKSPACE_REPORTS, "Test");
        //stop agent service
        runCommand.runCommandWaitForFinish("net stop \"IncrediBuild Agent\" ");
        //kill tray icon
        SystemActions.killProcess(StaticDataProvider.Processes.TRAY_ICON);
        //delete logs folder
        SystemActions.deleteFilesByPrefix(StaticDataProvider.Locations.IB_ROOT + "\\logs", "*");
        //delete build logs folder
        SystemActions.deleteFilesByPrefix(StaticDataProvider.Locations.QA_ROOT + "\\BuildLogs\\", "*");
        //start agent service
        runCommand.runCommandWaitForFinish("net start \"IncrediBuild Agent\" ");

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


    @BeforeMethod
    @Parameters ({ "logLevel"})
    public void beforeMethod(Method method, String logLevel){
        testName = getTestName(method);
        test = extent.createTest(testName);
        test.assignCategory("VM Sim CL - Log Level: " + logLevel);
        test.log(Status.INFO, method.getName() + " test started");
    }

    @AfterSuite
    public void postSimulation(){
        //stop agent service
        runCommand.runCommandWaitForFinish("net stop \"IncrediBuild Agent\" ");
        //copy logs to backup folder
        SystemActions.copyFilesByExtension(StaticDataProvider.Locations.IB_ROOT + "\\logs",
                StaticDataProvider.Locations.QA_ROOT + "\\logs\\Post Simulation Client Logs\\Post_simulation__log_backup_", ".log", true);
        //TODO: parse .log for assertion error
        //TODO: parse .txt for access violations


        //start agent service
        runCommand.runCommandWaitForFinish("net start \"IncrediBuild Agent\" ");
        SystemActions.startProcess(StaticDataProvider.Locations.IB_ROOT + "\\" + StaticDataProvider.Processes.TRAY_ICON);
    }

    private void verifyRegistry(String required, String current, String keyName){
        if (!required.equals(current)){
            RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\builder", keyName, required);
        }
    }

    private int getIBVersion(){
        String regVersion = RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\builder", StaticDataProvider.RegistryKeys.VERSION);
        int version = Integer.parseInt(regVersion);
        version -= 1001000;
        return version;
    }

}
