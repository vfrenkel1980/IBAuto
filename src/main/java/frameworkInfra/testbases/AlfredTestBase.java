package frameworkInfra.testbases;

import com.aventstack.extentreports.Status;
import frameworkInfra.Listeners.SuiteListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;

import java.lang.reflect.Method;

import static frameworkInfra.Listeners.SuiteListener.extent;
import static frameworkInfra.Listeners.SuiteListener.test;

@Listeners(SuiteListener.class)
public class AlfredTestBase extends WindowsTestBase{

    @BeforeMethod
    @Parameters({ "logLevel"})
    public void beforeMethod(Method method, String logLevel){
        testName = getTestName(method);
        test = extent.createTest(testName);
        if (logLevel.equals("4"))
            test.assignCategory("Alfred CL - Detailed logging");
        if (logLevel.equals("0"))
            test.assignCategory("Alfred CL - Minimal logging");
        test.log(Status.INFO, method.getName() + " test started");
        log.info(method.getName() + " test started");
    }
}
