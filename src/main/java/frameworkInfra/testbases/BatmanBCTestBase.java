package frameworkInfra.testbases;

import com.aventstack.extentreports.Status;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.SystemActions;
import frameworkInfra.utils.XmlParser;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;


public class BatmanBCTestBase extends WindowsTestBase {

    protected static List rawList;
    public static List<String> gridMachineList;

    @BeforeSuite
    public void beforeSuite(){
        rawList = XmlParser.getIpList("BatmanGrid.xml");
        gridMachineList = XmlParser.breakDownIPList(rawList);
    }

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

    @AfterMethod
    public void afterMethod(ITestResult result) throws IOException {
        SystemActions.deleteFile(StaticDataProvider.Locations.OUTPUT_LOG_FILE);
        getResult(result);
    }

}
