package frameworkInfra.testbases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import frameworkInfra.Listeners.SuiteListener;
import frameworkInfra.utils.SystemActions;
import ibInfra.ibService.IbService;
import ibInfra.windowscl.WindowsService;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static frameworkInfra.Listeners.SuiteListener.*;

@Listeners(SuiteListener.class)
public class InterfacesTestBase extends AlfredTestBase{

    protected IbService ibService = new IbService();
    protected WindowsService winService = new WindowsService();


    @BeforeMethod
    @Parameters({ "logLevel"})
    public void beforeMethod(Method method, String logLevel){
        testName = getTestName(method);
        test = extent.createTest(testName);
        test.assignCategory("Interfaces");
        test.log(Status.INFO, method.getName() + " test started");
        log.info(method.getName() + " test started");
    }

    @AfterClass
    public void interfacesAfterClass(){
        SystemActions.deleteFile("c:\\qa\\simulation\\coordexport.xml");
    }
}
