package frameworkInfra.testbases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.jcraft.jsch.JSchException;
import frameworkInfra.Listeners.SuiteListener;
import frameworkInfra.utils.Parser;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import frameworkInfra.utils.XmlParser;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.List;

import static frameworkInfra.Listeners.SuiteListener.*;

@Listeners(SuiteListener.class)
public class LinuxSimTestBase extends LinuxTestBase {

    private String firstBuild = "";

    @BeforeSuite
    public void envSetUp(ITestContext testContext) {
        log.info("starting before suite");
        switch (ENV){
            case "linuxsim1a":
                rawIpList = XmlParser.getIpList("Simulation IP list.xml");
                break;
            case "linuxsim1b":
                rawIpList = XmlParser.getIpList("Secondary Simulation IP list.xml");
                break;
        }
        testNum = TestNum.Sim;
        ipList = XmlParser.breakDownIPList(rawIpList);

        ibVersion = getIBVersion();
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/TestOutput" + formatter.format(calendar.getTime()) + " - " + ibVersion + ".html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        linuxService.killibDbCheck(ipList.get(1));
        connectedMachinesToGrid = linuxDBService.selectAll(LinuxDB.DB_COORD_REPORT, LinuxDB.COLUMN_MACHINE, LinuxDB.TABLE_HELPER_MACHINES, ipList.get(0));
        linuxService.deleteLogsFolder(connectedMachinesToGrid);
        firstBuild = getFirstBuild(ipList.get(1));

        log.info("finished before suite");
    }

    @BeforeClass
    public void initializeEnv(ITestContext testContext) {
        log.info("starting before class");
        test = extent.createTest("Before Class");
        test.assignCategory("BEFORE CLASS");
        test.log(Status.INFO, "BEFORE CLASS started");


        if (!linuxService.isIBServiceUp( ipList.get(0))) {
            test.log(Status.ERROR, "IB service in coordinator is down... FAILING ALL TESTS!");
            extent.flush();
            System.exit(0);
        }

        if (!linuxService.isIBServiceUp( ipList.get(1))) {
            test.log(Status.ERROR, "IB service in initiator 1 is down... FAILING ALL TESTS!");
            extent.flush();
            System.exit(0);
        }

        if (!linuxService.isIBServiceUp( ipList.get(2))) {
            test.log(Status.ERROR, "IB service in initiator 2 is down... FAILING ALL TESTS!");
            extent.flush();
            System.exit(0);
        }

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

    @AfterSuite
    public void afterSuite() throws JSchException {
        test = extent.createTest("AFTER SUITE");
        test.assignCategory("AFTER SUITE");
        test.log(Status.INFO, "AFTER SUITE" + " test started");
        boolean isFailed;

        String suiteLastBuild = linuxService.runQueryLastBuild(LinuxCommands.BUILD_ID, LinuxCommands.BUILD_HISTORY, ipList.get(1)).replaceAll("\n","");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy");
        String output = "res" + dateFormat.format(calendar.getTime());
        linuxService.linuxRunSSHCommand("./ib_db_check.py -d sim2_ib_db_check_data.py -r " + firstBuild + "," + suiteLastBuild + " > " + output + "; exit 0" , ipList.get(1));
        linuxService.getFile(ipList.get(1), "/home/xoraex/" + output, Locations.LINUX_SCRIPT_OUTPUT + "MultiBuild\\" + output);

        List<String> files = SystemActions.getAllFilesInDirectory(Locations.LINUX_SCRIPT_OUTPUT + "\\");
        for (String file: files ) {
            isFailed = Parser.doesFileContainString(file, "ErrorMessages:");
            if (isFailed)
                test.log(Status.WARNING, "Errors found in " + file);
        }
    }

}
