package frameworkInfra.testbases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.jcraft.jsch.JSchException;
import frameworkInfra.Listeners.SuiteListener;
import frameworkInfra.utils.StaticDataProvider.LinuxCommands;
import frameworkInfra.utils.StaticDataProvider.LinuxDB;
import frameworkInfra.utils.StaticDataProvider.LinuxSimulation;
import frameworkInfra.utils.StaticDataProvider.Locations;
import frameworkInfra.utils.SystemActions;
import frameworkInfra.utils.parsers.Parser;
import frameworkInfra.utils.parsers.XmlParser;
import ibInfra.windowscl.WindowsService;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static frameworkInfra.Listeners.SuiteListener.*;
import frameworkInfra.utils.StaticDataProvider;

@Listeners(SuiteListener.class)
public class LinuxSanityTestBase extends LinuxTestBase {

    private String className = this.getClass().getName();
    static List<String>  HostNameList = new ArrayList<String>();
    protected static String SanityHostName;
    protected static String CoorHostName;
    protected static String SanityHelpName;
//    private static String firstBuild = new String();
//    private static String lastBuild = new String();

    @BeforeSuite
    public void envSetUp(ITestContext testContext) {
        log.info("starting before suite");
        switch (ENV){
            case "linuxsim1a":
                SanityHostName = "l1a-u16-STests";
                CoorHostName = "l1a-u14-coor";
                SanityHelpName = "l1a-u14-snih";
                break;
            case "linuxsim1b":
                SanityHostName = "l1b-u16-STests";
                CoorHostName = "l1b-u14-coor";
                SanityHelpName = "l1b-u14-snih";
                break;
        }

        log.info("RUNNING VERSION: " + IB_VERSION);
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/TestOutput" + formatter.format(calendar.getTime()) + " - Sanity.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        log.info("finished  before suite");
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        testName = getTestName(method);
        test = extent.createTest(testName);
        test.assignCategory("Linux Sanity BeforeMethod");
        test.log(Status.INFO, method.getName() + " test started");
    }

    @AfterSuite
    public void afterSuite() throws JSchException {
        test = extent.createTest("AFTER SUITE");
        test.assignCategory("AFTER SUITE");
        test.log(Status.INFO, "AFTER SUITE" + " test started");

        WindowsService windowsService = new WindowsService();
        windowsService.runCommandWaitForFinish( StaticDataProvider.VMrunCommands.VMRUN + " stop " +  StaticDataProvider.LinuxSimulation.SANITY_VM_PATH);
        windowsService.runCommandWaitForFinish( StaticDataProvider.VMrunCommands.VMRUN + " stop " +  StaticDataProvider.LinuxSimulation.SANITY_HELPER_VM_PATH);
    }

}
