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

public class VmBCTestBase extends WindowsTestBase {

    @BeforeMethod
    @Parameters ({ "logLevel"})
    public void beforeMethod(Method method, String logLevel){
        testName = getTestName(method);
        test = extent.createTest(testName);
        if (logLevel.equals("4"))
            test.assignCategory("VmSim Cl - Detailed logging");
        if (logLevel.equals("0"))
            test.assignCategory("VmSim CL - Minimal logging");
        test.log(Status.INFO, method.getName() + " test started");
    }
}
