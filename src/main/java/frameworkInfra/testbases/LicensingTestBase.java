package frameworkInfra.testbases;

import com.aventstack.extentreports.Status;
import com.sun.jna.platform.win32.WinReg;
import frameworkInfra.Listeners.SuiteListener;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.SystemActions;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.lang.reflect.Method;

import static frameworkInfra.Listeners.SuiteListener.*;

@Listeners(SuiteListener.class)
public class LicensingTestBase extends ReleaseTestBase{

    protected String scenario = "";
    private String scenarioDescription;
    protected String currentDate = SystemActions.getLocalDateAsString();

    @BeforeSuite
    public void beforeSuite(){
        test = extent.createTest("Before Suite");
        test.assignCategory("BEFORE SUITE");
        test.log(Status.INFO, "BEFORE SUITE started");
        log.info("BEFORE SUITE started");

        SystemActions.deleteFilesByPrefix(StaticDataProvider.Locations.WORKSPACE_REPORTS, "Test");

        ibService.installIB("Latest");
        RegistryService.setRegistryKey(WinReg.HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\Coordinator", StaticDataProvider.RegistryKeys.AUTOMATIC_UPDATE_SUBSCRIBED_AGENTS, "1");
        ibService.customPackAllocationOn();
    }

    @BeforeClass
    public void beforeClass(ITestContext testContext){
        test = extent.createTest("Before Class");
        test.assignCategory("BEFORE CLASS");
        test.log(Status.INFO, "BEFORE CLASS started");
        log.info("BEFORE CLASS started");

        RegistryService.setRegistryKey(WinReg.HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\Builder", StaticDataProvider.RegistryKeys.STANDALONE_MODE, "0");
        RegistryService.setRegistryKey(WinReg.HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\Builder", StaticDataProvider.RegistryKeys.AVOID_LOCAL, "1");

        scenario = testContext.getName();
        switch (scenario){
            case ("1"): //Clean installation - No IB license loaded
                scenarioDescription = "Clean install - No license loaded";
                test.log(Status.INFO,"1");
                break;
            case ("2"): //Unloaded license
                scenarioDescription = "Unloaded license";
                test.log(Status.INFO,"2");
                ibService.loadIbLicense("IncrediBuild - Vlad - License Testing Environment April 2018.IB_lic");
                SystemActions.sleep(60);
                ibService.unloadIbLicense();
                break;
            case ("3"): //No packages aside from agent package
                scenarioDescription = "No packages aside from agent package";
                ibService.loadIbLicense("IncrediBuild - Vlad - License Testing Environment April 2018.IB_lic");
                winService.runCommandWaitForFinish(StaticDataProvider.IbLocations.XGCOORDCONSOLE + "/AllocateAll");
                SystemActions.sleep(5);
                winService.runCommandWaitForFinish(StaticDataProvider.IbLocations.XGCOORDCONSOLE + "/DeallocateAll");
                SystemActions.sleep(5);
                break;
            case ("4"): //License Loaded and Agent Unsubscribed
                scenarioDescription = "License Loaded and Agent Unsubscribed";
                ibService.loadIbLicense("IncrediBuild - Vlad - License Testing Environment April 2018.IB_lic");
                winService.runCommandWaitForFinish(StaticDataProvider.IbLocations.XGCOORDCONSOLE + "/AllocateAll");
                SystemActions.sleep(5);
                winService.runCommandWaitForFinish(StaticDataProvider.IbLocations.XGCOORDCONSOLE + "/UnsubscribeAll");
                SystemActions.sleep(5);
                break;
            case ("5"): //Temp License is Expired
                scenarioDescription = "Temp License is Expired";
                ibService.loadIbLicense("IncrediBuild - Vlad - License Testing Environment April 2018.IB_lic");
                winService.runCommandWaitForFinish(StaticDataProvider.IbLocations.XGCOORDCONSOLE + "/AllocateAll");
                SystemActions.sleep(5);
                SystemActions.addPeriodToSystemTime(0, 0, 5);
                break;
        }
    }

    @BeforeMethod
    public void beforeMethod(Method method){
        testName = getTestName(method);
        test = extent.createTest(testName + ": " + scenarioDescription);
        test.assignCategory(scenarioDescription);
        test.log(Status.INFO, "BEFORE METHOD started: " + method + " scenario: " + scenarioDescription);

    }

    @AfterClass
    public void afterClass(){
        test = extent.createTest("After Class");
        test.assignCategory("AFTER CLASS");
        test.log(Status.INFO, "AFTER CLASS started");
        log.info("AFTER CLASS started");

        ibService.unloadIbLicense();
        SystemActions.setLocalDateFromString(currentDate);
    }

    @AfterMethod
    public void afterMethod2(){
        SystemActions.deleteFile(StaticDataProvider.Locations.OUTPUT_LOG_FILE);
    }


    @AfterSuite
    public void afterSuite(){
        ibService.uninstallIB("Latest");
    }

}
