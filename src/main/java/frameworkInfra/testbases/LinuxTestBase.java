package frameworkInfra.testbases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.SystemActions;
import frameworkInfra.utils.XmlParser;
import ibInfra.linuxcl.LinuxService;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class LinuxTestBase extends TestBase{

    public enum TestNum{
        MultiBuild, MultiIn, Sim
    }

    protected static List rawIpList;
    protected static List rawIpList2;
    protected static List rawIpList3;

   protected static LinuxSimTestBase.TestNum testNum;

    public static List<String> ipList;
    public static List<String> multiGridIPList;
    String buildID;
    protected static String ibVersion = "";
    public LinuxService linuxService = new LinuxService();

    protected Calendar calendar = Calendar.getInstance();
    protected SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
    //@BeforeSuite
    //public void envSetUp(ITestContext testContext) {
    //}

    @BeforeSuite
    public void linuxSetup(ITestContext testContext) {
        log.info("starting LinuxTestBase before suite");
        //copy latest extent report to backup folder
        SystemActions.copyFilesByExtension(StaticDataProvider.Locations.WORKSPACE_REPORTS, StaticDataProvider.Locations.QA_ROOT + "\\Logs\\Automation HTML Reports", ".html", false);
        //delete HTML report from workspace folder
        SystemActions.deleteFilesByPrefix(StaticDataProvider.Locations.WORKSPACE_REPORTS, "Test");

        log.info("finished LinuxTestBase before suite");

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
        buildID = linuxService.runQueryLastBuild(StaticDataProvider.LinuxCommands.BUILD_ID, StaticDataProvider.LinuxCommands.BUILD_HISTORY, ipList.get(1));
        getResult(result);
        extent.flush();
    }

    protected static String getIBVersion() {
        LinuxService runCommand = new LinuxService();
        ibVersion = runCommand.linuxRunSSHCommandOutputString("ib_console --version", ipList.get(0));
        return ibVersion.substring(ibVersion.indexOf("[") + 1, ibVersion.indexOf("]"));
    }

}

