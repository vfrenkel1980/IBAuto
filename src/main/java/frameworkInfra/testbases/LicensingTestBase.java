package frameworkInfra.testbases;

import com.aventstack.extentreports.Status;
import com.sun.jna.platform.win32.WinReg;
import frameworkInfra.Listeners.SuiteListener;
import frameworkInfra.utils.parsers.Parser;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import ibInfra.ibExecs.IIBCoordMonitor;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.lang.reflect.Method;

import static frameworkInfra.Listeners.SuiteListener.*;

@Listeners(SuiteListener.class)
public class LicensingTestBase extends ReleaseTestBase {

    protected String scenario = "";
    private String scenarioDescription;
    String currentDate = "";
    Boolean startTests = false;
    IIBCoordMonitor coordMonitor = new IIBCoordMonitor();

    @BeforeSuite
    public void beforeSuite() {
        test = extent.createTest("Before Suite");
        test.assignCategory("BEFORE SUITE");
        test.log(Status.INFO, "BEFORE SUITE started");
        log.info("BEFORE SUITE started");

        SystemActions.deleteFilesByPrefix(Locations.WORKSPACE_REPORTS, "Test");
        currentDate = SystemActions.getLocalDateAsString();

        ibService.installIB(IB_VERSION);
        RegistryService.setRegistryKey(WinReg.HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\Coordinator", RegistryKeys.AUTOMATIC_UPDATE_SUBSCRIBED_AGENTS, "1");
        RegistryService.setRegistryKey(WinReg.HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\Builder", RegistryKeys.STANDALONE_MODE, "0");
        RegistryService.setRegistryKey(WinReg.HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\Builder", RegistryKeys.AVOID_LOCAL, "1");
        ibService.customPackAllocationOn();
    }

    @BeforeClass
    public void beforeClass(ITestContext testContext) {
        test = extent.createTest("Before Class");
        test.assignCategory("BEFORE CLASS");
        test.log(Status.INFO, "BEFORE CLASS started");
        log.info("BEFORE CLASS started");


        scenario = testContext.getName();
        switch (scenario) {
            case ("1"): //Clean installation - No IB license loaded
                scenarioDescription = "Clean install - No license loaded";
                test.log(Status.INFO, "1");
                break;
            case ("2"): //Unloaded license
                scenarioDescription = "Unloaded license";
                test.log(Status.INFO, "2");
                ibService.loadIbLicense(IbLicenses.VALID_LIC);
                SystemActions.sleep(10);
                winService.runCommandWaitForFinish(IbLocations.XGCOORDCONSOLE + "/AllocateAll");
                SystemActions.sleep(10);
                try {
                    coordMonitor.waitForAgentIsUpdated(WindowsMachines.LICENSE_HLPR_NAME);
                } catch (RuntimeException |SAXException |IOException |ParserConfigurationException e) {
                    test.log(Status.ERROR, "Helper is not updated. Error: "+e);
                }
                ibService.unloadIbLicense();
                SystemActions.sleep(5);
                break;
            case ("3"): //No packages aside from agent package
                scenarioDescription = "No packages aside from agent package";
                ibService.loadIbLicense(IbLicenses.VALID_LIC);
                SystemActions.sleep(10);
                winService.runCommandWaitForFinish(IbLocations.XGCOORDCONSOLE + "/AllocateAll");
                SystemActions.sleep(10);
                winService.runCommandWaitForFinish(IbLocations.XGCOORDCONSOLE + "/DeallocateAll");
                SystemActions.sleep(10);
                break;
            case ("4"): //License Loaded and Agent Unsubscribed
                scenarioDescription = "License Loaded and Agent Unsubscribed";
                ibService.loadIbLicense(IbLicenses.VALID_LIC);
                SystemActions.sleep(10);
                winService.runCommandWaitForFinish(IbLocations.XGCOORDCONSOLE + "/AllocateAll");
                SystemActions.sleep(10);
                winService.runCommandWaitForFinish(IbLocations.XGCOORDCONSOLE + "/Unsubscribe");
                SystemActions.sleep(10);
                break;
            case ("5"): //Temp License is Expired
                scenarioDescription = "Temp License is Expired";
                ibService.loadIbLicense(IbLicenses.VALID_LIC);
                SystemActions.sleep(10);
                winService.runCommandWaitForFinish(IbLocations.XGCOORDCONSOLE + "/AllocateAll");
                SystemActions.sleep(10);
                SystemActions.addPeriodToSystemTime(0, 0, 5);
                SystemActions.sleep(20);
                System.out.println("Start build");
                break;
            case ("6"): //All Allocated Packages are temporary and expired
                scenarioDescription = "All Allocated Packages are temporary and expired";
                ibService.loadIbLicense(IbLicenses.EXPIRED_SOLUTIONS_LIC);
                SystemActions.sleep(10);
                winService.runCommandWaitForFinish(IbLocations.XGCOORDCONSOLE + "/AllocateAll");
                SystemActions.sleep(10);
                break;
            case ("7"): //Expired license loading
                scenarioDescription = "Expired license loading";
                SystemActions.deleteFile(IbLocations.IB_ROOT + "\\Logs\\XlicProc.log");
                ibService.loadIbLicense(IbLicenses.EXPIRED_LIC);
                SystemActions.sleep(10);
                Assert.assertTrue(Parser.doesFileContainString(IbLocations.IB_ROOT + "\\Logs\\XlicProc.log", "License has expired and can not be loaded. Please contact sales@incredibuild.com to receive a new license."));
                break;
            case ("8"): //Trialnse is Expired
                scenarioDescription = "Trial License is Expired";
                ibService.loadIbLicense(trialLicenseFile);
                SystemActions.sleep(10);
                winService.runCommandWaitForFinish(IbLocations.XGCOORDCONSOLE + "/AllocateAll");
                SystemActions.sleep(10);
                SystemActions.addPeriodToSystemTime(0, 5, 0);
                SystemActions.sleep(20);
                System.out.println("Start build");
                break;
        }
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        testName = getTestName(method);
        test = extent.createTest(testName + ": " + scenarioDescription);
        test.assignCategory(scenarioDescription);
        test.log(Status.INFO, "BEFORE METHOD started: " + method + " scenario: " + scenarioDescription);

    }

    @AfterClass
    public void afterClass(ITestContext testContext) {
        test = extent.createTest("After Class");
        test.assignCategory("AFTER CLASS");
        test.log(Status.INFO, "AFTER CLASS started");
        log.info("AFTER CLASS started");

        ibService.unloadIbLicense();
        scenario = testContext.getName();
        switch (scenario) {
            case ("5"):
                SystemActions.subtractPeriodFromSystemTime(0, 0, 5);
                SystemActions.sleep(20);
                break;
            case ("8"):
                SystemActions.subtractPeriodFromSystemTime(0, 5, 0);
                SystemActions.sleep(20);
                break;
        }
    }

    @AfterMethod
    public void afterMethod2() {
        SystemActions.deleteFile(Locations.OUTPUT_LOG_FILE);
    }


    @AfterSuite
    public void afterSuite() {
        test = extent.createTest("After Suite");
        test.assignCategory("AFTER SUITE");
        test.log(Status.INFO, "AFTER SUITE started");
        log.info("AFTER SUITE started");
        SystemActions.setLocalDateFromString(currentDate);
        ibService.uninstallIB(IB_VERSION);
    }

}
