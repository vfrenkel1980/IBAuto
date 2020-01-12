package frameworkInfra.testbases;

import com.aventstack.extentreports.Status;
import frameworkInfra.Listeners.SuiteListener;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider.Locations;
import frameworkInfra.utils.StaticDataProvider.RegistryKeys;
import frameworkInfra.utils.StaticDataProvider.WindowsMachines;
import ibInfra.ibExecs.IIBCoordMonitor;
import org.testng.ITestContext;
import org.testng.annotations.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static frameworkInfra.Listeners.SuiteListener.extent;
import static frameworkInfra.Listeners.SuiteListener.test;

@Listeners(SuiteListener.class)
public class UnitTestingTestBase extends WindowsTestBase{
    IIBCoordMonitor coordMonitor = new IIBCoordMonitor();

    @BeforeMethod
    @Parameters({ "logLevel"})
    public void beforeMethod(Method method, String logLevel, ITestContext testContext){
        testName = getTestName(method);
        test = extent.createTest(testName);
        if (logLevel.equals("4"))
            test.assignCategory("Unit Testing Cl - Detailed logging");
        if (logLevel.equals("0"))
            test.assignCategory("Unit Testing  CL - Minimal logging");
        test.log(Status.INFO, method.getName() + " test started");
        log.info(method.getName() + " test started");
        testName = testContext.getName();
    }

    @BeforeSuite
    public void beforeSuite(){
        test = extent.createTest("Before Suite");
        test.assignCategory("BEFORE SUITE");
        test.log(Status.INFO, "BEFORE SUITE started");
        log.info("BEFORE SUITE started");

        ibService.updateIB(IB_VERSION);
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\builder", RegistryKeys.STANDALONE_MODE, "0");
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\builder", RegistryKeys.AVOID_LOCAL, "0");
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\builder", RegistryKeys.RESTART_ON_LOCAL, "0");
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT +"\\Builder", RegistryKeys.SAVE_BUILD_PACKET, "1");
        try {
            coordMonitor.waitForAgentIsUpdated(WindowsMachines.AGENT_SETTINGS_HLPR_NAME);
        } catch (RuntimeException | SAXException | IOException | ParserConfigurationException e) {
            test.log(Status.ERROR, "Helper is not updated. Error: "+e);
        }
        test.log(Status.INFO, "BEFORE SUITE finished");
        log.info("BEFORE SUITE finished");

    }

    @AfterMethod
    public void deleteTestResults(){
        new File(System.getProperty("user.dir")+"\\TestResult.xml").delete();
    }

}
