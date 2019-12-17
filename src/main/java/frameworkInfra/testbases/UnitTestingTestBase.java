package frameworkInfra.testbases;

import com.aventstack.extentreports.Status;
import frameworkInfra.Listeners.SuiteListener;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider.*;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.io.File;
import java.lang.reflect.Method;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static frameworkInfra.Listeners.SuiteListener.extent;
import static frameworkInfra.Listeners.SuiteListener.test;

@Listeners(SuiteListener.class)
public class UnitTestingTestBase extends WindowsTestBase{

    @BeforeClass
    public void avoidLocal() {
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\Builder", RegistryKeys.AVOID_LOCAL, "1");
    }

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

    @AfterClass
    public void restartLocal() {
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\Builder", RegistryKeys.AVOID_LOCAL, "0");
    }
    @AfterMethod
    public void deleteTestResults(){
        new File(System.getProperty("user.dir")+"\\TestResult.xml").delete();
    }

}
