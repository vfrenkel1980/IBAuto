package frameworkInfra.testbases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import frameworkInfra.utils.SystemActions;
import frameworkInfra.utils.XmlParser;
import ibInfra.linuxcl.LinuxService;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import static frameworkInfra.utils.StaticDataProvider.*;

public class LinuxSimTestBase extends LinuxTestBase {

    @BeforeSuite
    public void envSetUp(ITestContext testContext) {
        log.info("starting before suite");
        rawIpList = XmlParser.getIpList("Simulation IP list.xml");
        testNum = TestNum.Sim;

        ipList = XmlParser.breakDownIPList(rawIpList);

        ibVersion = getIBVersion();
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/TestOutput" + formatter.format(calendar.getTime()) + " - " + ibVersion + ".html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        linuxService.killib_db_check(ipList.get(1));
        log.info("finished before suite");
    }

    @BeforeClass
    public void initializeEnv(ITestContext testContext) {
        log.info("starting before class");
        test = extent.createTest("Before Class");
        test.assignCategory("BEFORE CLASS");
        test.log(Status.INFO, "BEFORE CLASS started");


        if (!linuxService.isIBServiceUp("ib_server", ipList.get(0))) {
            test.log(Status.ERROR, "IB service in coordinator is down... FAILING ALL TESTS!");
            extent.flush();
            System.exit(0);
        }

        if (!linuxService.isIBServiceUp("ib_server", ipList.get(1))) {
            test.log(Status.ERROR, "IB service in initiator is down... FAILING ALL TESTS!");
            extent.flush();
            System.exit(0);
        }
        // linuxService.deleteLogsFolder(ipList);
         log.info("finished before class");
    }

    @BeforeMethod
    @Parameters({"cycle"})
    public void beforeMethod(Method method, String cycle) {
        testName = getTestName(method);
        test = extent.createTest(testName);
        test.assignCategory("Linux Simulation - Cycle " + cycle);
        test.log(Status.INFO, method.getName() + " test started");
    }

    public void getResult(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, result.getName() + " test passed");
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.log(Status.SKIP, result.getName() + " test skipped - Build ID = " + buildID);
        } else if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.ERROR, result.getName() + " test has failed - Build ID = " + buildID + "------->" + result.getThrowable());
            String path = captureScreenshot(result.getName());
            test.fail("Screenshot " + test.addScreenCaptureFromPath(path, "Screenshot"));
        }
    }

    @AfterSuite
    public void afterSuite() {
        //runscript
    }
}
