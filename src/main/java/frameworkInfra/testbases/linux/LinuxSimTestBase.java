package frameworkInfra.testbases.linux;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import frameworkInfra.Listeners.SuiteListener;
import frameworkInfra.testbases.TestBase;
import frameworkInfra.utils.CustomJsonParser;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.XmlParser;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.*;

import java.lang.reflect.Method;

import static frameworkInfra.Listeners.SuiteListener.*;

@Listeners(SuiteListener.class)
public class LinuxSimTestBase extends LinuxTestBase {

    @BeforeSuite
    public void envSetUp(ITestContext testContext) {
        TestBase.log.info("starting before suite");
        rawIpList = XmlParser.getIpList("Machines/Simulation IP list.xml");
        testNum = TestNum.Sim;

        ipList = XmlParser.breakDownIPList(rawIpList);

        ibVersion = getIBVersion();
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/TestOutput" + formatter.format(calendar.getTime()) + " - " + ibVersion + ".html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        linuxService.killib_db_check(ipList.get(1));
        TestBase.log.info("finished before suite");
    }

    @BeforeClass
    public void initializeEnv(ITestContext testContext) {
        TestBase.log.info("starting before class");
        test = extent.createTest("Before Class");
        test.assignCategory("BEFORE CLASS");
        test.log(Status.INFO, "BEFORE CLASS started");

        if (!linuxService.isIBServiceUp("ib_server",ipList.get(0))) {
            test.log(Status.ERROR, "IB service in coordinator is down... FAILING ALL TESTS!");
            extent.flush();
            System.exit(0);
        }

        String className = this.getClass().getName();
        className = className.substring(className.lastIndexOf('.') + 1);
        String classip = CustomJsonParser.getValueFromKey(StaticDataProvider.LinuxSimulation.LINUX_SIM_NAME_IP_LIST,className);


        for (int i=1; i<3; ++i) {

            String currip = ipList.get(i);

            if(ipList.get(i).equals(classip)) {
                if (linuxService.startIBService(ipList.get(i))) {
                    String err = "startIBService failed" + ipList.get(i) + "... FAILING ALL TESTS!";
                    test.log(Status.ERROR, err);
                    extent.flush();
                    System.exit(0);
                }
            }
            else if(linuxService.stopIBService(ipList.get(i))) {
                String err = "stopIBService failed " + ipList.get(i) + "... FAILING ALL TESTS!";
                test.log(Status.ERROR, err);
                extent.flush();
                throw new SkipException("EXITING");
            }

        }

        if(className == "LinuxSimulationTests")
             linuxService.deleteLogsFolder(ipList);

         TestBase.log.info("finished before class");
    }

    @BeforeMethod
    @Parameters({"cycle"})
    public void beforeMethod(Method method, String cycle) {
        testName = getTestName(method);
        test = extent.createTest(testName);
        test.assignCategory("Linux Simulation - Cycle " + cycle);
        test.log(Status.INFO, method.getName() + " test started");
    }

    @AfterSuite
    public void afterSuite() {
        //TODO : run script
    }
}
