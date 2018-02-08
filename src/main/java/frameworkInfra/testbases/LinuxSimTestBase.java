package frameworkInfra.testbases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.XmlParser;
import ibInfra.linuxcl.LinuxService;
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
    private static List rawIpList;
    public static List<String> ipList;
    String buildID;
    private static String ibVersion = "";

    static {
        rawIpList = XmlParser.getIpList();
        ipList = runLinux.breakDownIPList(rawIpList);
        ibVersion = getIBVersion();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/TestOutput" + formatter.format(calendar.getTime()) + " - " + ibVersion + ".html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @BeforeSuite
    public void envSetUp(){
        test = extent.createTest("Before Suite");
        test.assignCategory("BEFORE SUITE");
        test.log(Status.INFO, "BEFORE SUITE started");

        //runLinux.deleteLogsFolder(ipList);

        if(!runLinux.isIBServiceUp("ib_server", LinuxMachines.VM_SIM_1A)) {
           test.log(Status.ERROR, "IB service is down... FAILING ALL TESTS!");
        }
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
        buildID = runLinux.runQueryLastBuild(LinuxCommands.BUILD_ID, LinuxCommands.BUILD_HISTORY, LinuxMachines.VM_SIM_1A);
        getResult(result);
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
