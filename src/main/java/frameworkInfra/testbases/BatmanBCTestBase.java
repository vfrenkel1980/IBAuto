package frameworkInfra.testbases;

import com.aventstack.extentreports.Status;
import frameworkInfra.Listeners.SuiteListener;
import frameworkInfra.utils.Parser;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import frameworkInfra.utils.XmlParser;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import static frameworkInfra.Listeners.SuiteListener.extent;
import static frameworkInfra.Listeners.SuiteListener.test;

@Listeners(SuiteListener.class)
public class BatmanBCTestBase extends WindowsTestBase {

    protected static List rawBatmanList;
    public static List<String> batmanMachineList;
    protected static List rawVmSimList;
    public static List<String> vmSimMachineList;

    @BeforeSuite
    public void beforeSuite(){
        rawBatmanList = XmlParser.getIpList("Machines/BatmanGrid.xml");
        batmanMachineList = XmlParser.breakDownIPList(rawBatmanList);
        rawVmSimList = XmlParser.getIpList("Machines/VmSimGrid.xml");
        vmSimMachineList = XmlParser.breakDownIPList(rawVmSimList);
    }

    @BeforeMethod
    @Parameters ({ "logLevel"})
    public void beforeMethod(Method method, String logLevel, ITestContext testContext){
        testName = getTestName(method);
        test = extent.createTest(testName);
        if (logLevel.equals("4"))
            test.assignCategory("Batman CL - Detailed logging");
        if (logLevel.equals("0"))
            test.assignCategory("Batman CL - Minimal logging");
        test.log(Status.INFO, method.getName() + " test started");
        log.info(method.getName() + " test started");
        testName = testContext.getName();
    }

    @AfterMethod
    public void afterMethod(ITestResult result) throws IOException {
        if (new File(Locations.QA_ROOT, "buildlog.txt").exists()) {
            if (Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.PDB_ERROR))
                LogOutput.PDB_ERROR_TESTS = LogOutput.PDB_ERROR_TESTS + testName + "\n";
            SystemActions.deleteFile(Locations.OUTPUT_LOG_FILE);
        }
    }

}
