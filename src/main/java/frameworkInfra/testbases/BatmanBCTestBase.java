package frameworkInfra.testbases;

import com.aventstack.extentreports.Status;
import org.testng.annotations.*;
import java.lang.reflect.Method;


public class BatmanBCTestBase extends WindowsTestBase {

    @BeforeMethod
    @Parameters ({ "logLevel"})
    public void beforeMethod(Method method, String logLevel){
        testName = getTestName(method);
        test = extent.createTest(testName);
        if (logLevel.equals("4"))
            test.assignCategory("Batman CL - Detailed logging");
        if (logLevel.equals("0"))
            test.assignCategory("Batman CL - Minimal logging");
        test.log(Status.INFO, method.getName() + " test started");
        log.info(method.getName() + " test started");
    }

}
