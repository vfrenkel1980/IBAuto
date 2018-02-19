package frameworkInfra.testbases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.XmlParser;
import ibInfra.linuxcl.LinuxService;
import org.aspectj.lang.annotation.Before;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import static frameworkInfra.utils.StaticDataProvider.*;

public class LinuxSimTestBase extends TestBase {

    public static LinuxService runLinux = new LinuxService();
    protected static List rawIpList;
    protected static List rawIpList2;
    public static List<String> ipList;
    String buildID;
    private static String ibVersion = "";

/*    static {


    }*/

    @BeforeSuite
    public void envSetUp(ITestContext testContext){
        log.info("starting before suite");
        if (testContext.getName().equals("LinuxMultiBuild"))
            rawIpList = XmlParser.getIpList("MultiBuild IP list.xml");
        if (testContext.getName().equals("LinuxMultiInitiator"))
            rawIpList = XmlParser.getIpList("MultiInitiators IP list.xml");
        if (testContext.getName().contains("Cycle"))
            rawIpList = XmlParser.getIpList("Simulation IP list.xml");

        ipList = runLinux.breakDownIPList(rawIpList);
        ibVersion = getIBVersion();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/TestOutput" + formatter.format(calendar.getTime()) + " - " + ibVersion + ".html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        runLinux.deleteLogsFolder(ipList);
        log.info("finished before suite");
    }

    @BeforeClass
    public void initializeEnv(ITestContext testContext){
        log.info("starting before class");

        test = extent.createTest("Before Class");
        test.assignCategory("BEFORE CLASS");
        test.log(Status.INFO, "BEFORE CLASS started");
        if (testContext.getName().equals("Cycle 1")) {

            if (!runLinux.isIBServiceUp("ib_server", ipList.get(0))) {
                test.log(Status.ERROR, "IB service is down... FAILING ALL TESTS!");
                extent.flush();
                System.exit(0);
            }

            if (!runLinux.isIBServiceUp("ib_server", ipList.get(1))) {
                test.log(Status.ERROR, "IB service is down... FAILING ALL TESTS!");
                extent.flush();
                System.exit(0);
            }
        }
        log.info("finished before class");
    }

    @BeforeMethod
    @Parameters({"cycle"})
    public void beforeMethod(Method method, String cycle){
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

    @AfterMethod
    public void afterMethod(ITestResult result) throws InterruptedException, IOException {
        buildID = runLinux.runQueryLastBuild(LinuxCommands.BUILD_ID, LinuxCommands.BUILD_HISTORY, ipList.get(1));
        getResult(result);
        extent.flush();
    }

    private static String getIBVersion() {
        LinuxService runCommand = new LinuxService();
        try {
            ibVersion = runCommand.linuxRunSSHCommandOutputString("ib_console --version", ipList.get(0));
        } catch (InterruptedException e) {
            e.getMessage();
        }
        return ibVersion.substring(ibVersion.indexOf("[") + 1, ibVersion.indexOf("]"));
    }


}
