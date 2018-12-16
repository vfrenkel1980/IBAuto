package frameworkInfra.testbases;

import com.aventstack.extentreports.Status;
import frameworkInfra.Listeners.SuiteListener;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import frameworkInfra.utils.databases.PostgresJDBC;
import frameworkInfra.utils.parsers.XmlParser;
import org.testng.ITestContext;
import org.testng.annotations.*;

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
    public PostgresJDBC postgresJDBC = new PostgresJDBC();
    /**
     * In the before suite we break down the ip list for each of the
     * simulations env so we can than verify the assignment of the agent to the build
     * in tests checkBasicAgentAssignment and verifyBuildGroups
     */

    @BeforeSuite
    public void beforeSuite(){
        rawBatmanList = XmlParser.getIpList("BatmanGrid.xml");
        batmanMachineList = XmlParser.breakDownIPList(rawBatmanList);
        rawVmSimList = XmlParser.getIpList("VmSimGrid.xml");
        vmSimMachineList = XmlParser.breakDownIPList(rawVmSimList);
        SystemActions.deleteFilesByPrefix(Locations.QA_ROOT + "\\logs\\for_investigation\\", "*.txt");
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

}
