package frameworkInfra.testbases.incrediCloud;

import com.aventstack.extentreports.Status;
import frameworkInfra.Listeners.SuiteListener;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import java.lang.reflect.Method;

import static frameworkInfra.Listeners.SuiteListener.extent;
import static frameworkInfra.Listeners.SuiteListener.test;

@Listeners(SuiteListener.class)
public class OnboardingTestBase extends ICEngineTestBase {


    @BeforeMethod
    public void beforeMethod(Method method) {
        testName = getTestName(method);
        test = extent.createTest(testName);
        test.assignCategory("Field Validation test");
        test.log(Status.INFO, method.getName() + " test started");
    }

    @AfterMethod
    public void afterMethod(){
        extent.flush();
    }
}
