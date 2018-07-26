package frameworkInfra.testbases;

import com.aventstack.extentreports.Status;
import com.sun.jna.platform.win32.WinReg;
import frameworkInfra.Listeners.SuiteListener;
import frameworkInfra.utils.Parser;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.SystemActions;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.lang.reflect.Method;

import static frameworkInfra.Listeners.SuiteListener.extent;
import static frameworkInfra.Listeners.SuiteListener.test;

@Listeners(SuiteListener.class)
public class LicensingPositiveTestBase extends ReleaseTestBase{

    protected String scenario = "";
    private String scenarioDescription;
    String currentDate = "";
    Boolean startTests = false;

    @BeforeSuite
    public void beforeSuite(){
        test = extent.createTest("Before Suite");
        test.assignCategory("BEFORE SUITE");
        test.log(Status.INFO, "BEFORE SUITE started");
        log.info("BEFORE SUITE started");

        SystemActions.deleteFilesByPrefix(StaticDataProvider.Locations.WORKSPACE_REPORTS, "Test");
        currentDate = SystemActions.getLocalDateAsString();

        ibService.installIB("Latest");
        RegistryService.setRegistryKey(WinReg.HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\Coordinator", StaticDataProvider.RegistryKeys.AUTOMATIC_UPDATE_SUBSCRIBED_AGENTS, "1");
        RegistryService.setRegistryKey(WinReg.HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\Builder", StaticDataProvider.RegistryKeys.STANDALONE_MODE, "0");
        RegistryService.setRegistryKey(WinReg.HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\Builder", StaticDataProvider.RegistryKeys.AVOID_LOCAL, "1");
        ibService.customPackAllocationOn();
    }

    @BeforeClass
    public void beforeClass(ITestContext testContext){
        test = extent.createTest("Before Class");
        test.assignCategory("BEFORE CLASS");
        test.log(Status.INFO, "BEFORE CLASS started");
        log.info("BEFORE CLASS started");



        scenario = testContext.getName();
        switch (scenario){
            case ("1"): //Valid license with all packages
                scenarioDescription = "Valid license with all packages";
                ibService.loadIbLicense("IncrediBuild - Vlad - License Testing Environment April 2018.IB_lic");
                winService.runCommandWaitForFinish(StaticDataProvider.IbLocations.XGCOORDCONSOLE + "/AllocateAll");
                SystemActions.sleep(60);
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
