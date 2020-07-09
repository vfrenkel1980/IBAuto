package frameworkInfra.testbases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.google.inject.Module;
import com.jcraft.jsch.JSchException;
import frameworkInfra.Listeners.SuiteListener;
import frameworkInfra.utils.parsers.Parser;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import frameworkInfra.utils.parsers.XmlParser;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;

import static frameworkInfra.Listeners.SuiteListener.*;

@Listeners(SuiteListener.class)
public class LinuxSimTestBase extends LinuxTestBase {

    public enum SimClassType {
        None, GenSim, Dock, Ccache, Thirty2Bit, Bazel
    }

    private static int NumInitators = 5;

    private String className = this.getClass().getName();
    private static List<String> firstBuilds = new ArrayList<String>();
    private static List<String> lastBuilds = new ArrayList<String>();
    private static List<String> has_crashes = new ArrayList<String>();
    private static List<String> log_crashes = new ArrayList<String>();
    protected SimClassType simClassType;
    private Calendar calender = Calendar.getInstance();

    @BeforeSuite
    public void envSetUp(ITestContext testContext) {
        log.info(calender.getTime());
        log.info("starting before suite");
        switch (ENV) {
            case "linuxsim1a":
                rawIpList = XmlParser.getIpList("Simulation IP list.xml");
                break;
            case "linuxsim1b":
                rawIpList = XmlParser.getIpList("Secondary Simulation IP list.xml");
                break;
        }
        testType = TestType.Sim;
        ipList = XmlParser.breakDownIPList(rawIpList);
        log.info("RUNNING VERSION: " + IB_VERSION);
        reportFilePath = System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/TestOutput" + formatter.format(calendar.getTime()) + " - " + ibVersion + ".html";
        htmlReporter = new ExtentHtmlReporter(reportFilePath);
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        linuxService.killibDbCheck(ipList.get(1));
        connectedMachinesToGrid = linuxDBService.selectAll(LinuxDB.DB_COORD_REPORT, LinuxDB.COLUMN_MACHINE, LinuxDB.TABLE_HELPER_MACHINES, ipList.get(0));
        for (int i = 0; i < connectedMachinesToGrid.size(); ++i) {
            if (connectedMachinesToGrid.get(i).contains("."))
                connectedMachinesToGrid.set(i, connectedMachinesToGrid.get(i).substring(0, connectedMachinesToGrid.get(i).indexOf(".")));
        }
        test = extent.createTest("Before Suite");
        linuxService.deleteLogsFolder(connectedMachinesToGrid);


        if (!linuxService.isIBCoordinatorServiceUp(ipList.get(0))) {
            test.log(Status.ERROR, "IB service in coordinator is down... FAILING ALL TESTS!");
            extent.flush();
            System.exit(0);
        }


        if (!IB_VERSION.equals("current")) {

            for (int i = 1; i <= NumInitators; ++i) {
                if (linuxService.startIBService(ipList.get(i))) {
                    test.log(Status.ERROR, "IB service in initiator " + i + " is down... FAILING ALL TESTS!");
                    extent.flush();
                    System.exit(0);
                }
            }
            linuxService.updateIB(ipList.get(0), IB_VERSION, connectedMachinesToGrid);
        }
        ibVersion = linuxService.getIBVersion(ipList.get(0));

        log.info("Finished before suite for version: " + ibVersion);
        test.log(Status.INFO,"Before Suite ended for version: " + ibVersion);
    }


    @BeforeClass
    public void initializeEnv(ITestContext testContext) {
        log.info("starting before class");
        test = extent.createTest("Before Class");
        test.assignCategory("BEFORE CLASS");
        test.log(Status.INFO, "BEFORE CLASS started");

        if (className.contains("CcacheTests"))
            simClassType = SimClassType.Ccache;
        else if (className.contains("DockCHrootTests"))
            simClassType = SimClassType.Dock;
        else if (className.contains("Linux32BitTests"))
            simClassType = SimClassType.Thirty2Bit;
        else if (className.contains("Bazel"))
            simClassType = SimClassType.Bazel;
        else
            simClassType = SimClassType.GenSim;


        for (int i = 1; i <= NumInitators; ++i) {
            if ((i == simClassType.ordinal()) && (linuxService.startIBService(ipList.get(i)))) {
                test.log(Status.ERROR, "IB service in initiator " + i + " is down... FAILING ALL TESTS!");
                extent.flush();
                System.exit(0);
            } else if ((i != simClassType.ordinal()) && (linuxService.stopIBService(ipList.get(i)))) {
                String err = "startIBService failed " + ipList.get(i) + "... FAILING ALL TESTS!";
                test.log(Status.ERROR, err);
            }
        }

        for (int i = 1; i <= NumInitators; ++i)
            firstBuilds.add(getFirstBuild(ipList.get(i)));

        log.info("finished before class");
    }

    @BeforeMethod
    @Parameters({"cycle"})
    public void beforeMethod(Method method, String cycle) {
        testName = getTestName(method);
        test = extent.createTest(testName);
        test.assignCategory("Linux Simulation - Cycle " + cycle);
        test.log(Status.INFO, method.getName() + " test started");
        log.info("Linux Simulation - Cycle " + cycle + "; Test name: " + testName);
        if (cycle.equals("21") || cycle.equals("22")){
            LinuxTestBase.LINUXCLFLAGS = ("-f");
            log.info("For performance, Cycle" + cycle + " is without -d, only flags are: " + LINUXCLFLAGS);
        }
    }

    @AfterSuite
    public void afterSuite() throws JSchException {
        test = extent.createTest("AFTER SUITE");
        test.assignCategory("AFTER SUITE");
        test.log(Status.INFO, "AFTER SUITE" + " test started");
        boolean isFailed;
        int exitStatus = 0;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy");
        String output = "res_" + dateFormat.format(calendar.getTime());
        String performance = "performance_" + dateFormat.format(calendar.getTime());

        for (int i = 1; i <= NumInitators; ++i) {
            String scriptFileName = output + "_" + String.valueOf(i);

            linuxService.startIBService(ipList.get(i));
            lastBuilds.add(linuxService.runQueryLastBuild(LinuxCommands.BUILD_ID, LinuxCommands.BUILD_HISTORY, ipList.get(i).replaceAll("\n", "")).replaceAll("\n", ""));

            if (Integer.parseInt(lastBuilds.get(i - 1)) >= Integer.parseInt(firstBuilds.get(i - 1))) {
                linuxService.linuxRunSSHCommand("./ib_db_check.py -d sim2_ib_db_check_data.py -r " + firstBuilds.get(i - 1) + "," + lastBuilds.get(i - 1) + " > " + scriptFileName + "; exit 0", ipList.get(i));
                linuxService.getFile(ipList.get(i), LinuxCommands.HOME_DIR + scriptFileName, Locations.LINUX_SCRIPT_OUTPUT + "\\" + scriptFileName);
            }
        }

        String testsFileName = "ib_tests_res_" + dateFormat.format(calendar.getTime());
        exitStatus = linuxService.linuxRunSSHCommand(" cd " + LinuxSimulation.IB_TESTS_DIR + " && " + LinuxSimulation.RUN_IB_TESTS + " > " + testsFileName + "; exit 0", ipList.get(1));
        linuxService.getFile(ipList.get(1), LinuxSimulation.IB_TESTS_DIR + testsFileName, Locations.LINUX_SCRIPT_OUTPUT + "\\" + testsFileName);

        List<String> files = SystemActions.getAllFilesInDirectory(Locations.LINUX_SCRIPT_OUTPUT + "\\");
        for (String file : files) {
            isFailed = Parser.doesFileContainString(Locations.LINUX_SCRIPT_OUTPUT + "\\" + file, "ErrorMessages:");
            if (isFailed)
                test.log(Status.WARNING, "Errors found in " + file);

            if ((exitStatus != 0) && (file.contains("ib_tests_res")))
                test.log(Status.WARNING, "Errors found in " + file);
        }

        for (String machine : ipList) {
           has_crashes = linuxService.findFile(machine, "/etc/incredibuild/log/", "*.has_crash");
            if (has_crashes.size() >0 && !has_crashes.get(0).equals("")) {
                test.log(Status.WARNING, "Found has_crash files in Machine: " + machine + " Path: /etc/incredibuild/log/");
                log.info("In machine: " + machine + "; has_crashes value is: " + has_crashes);
            } else {
                test.log(Status.INFO, "No has_crashes files found in Machine: " + machine);
            }

            log_crashes = linuxService.findFile(machine, "/etc/incredibuild/log/", "*.crash.log");
            if (log_crashes.size()>0 && !log_crashes.get(0).equals("")) {
                test.log(Status.WARNING, "Found log_crashes files in Machine: " + machine + " Path: /etc/incredibuild/log/");
                log.info("In machine: " + machine + "; log_crashes value is: " + log_crashes);
                }
            else {
                test.log(Status.INFO, "No log_crashes files found in Machine: " + machine);
            }
        }
        //performance reports
        test = extent.createTest("Performance Report");
        test.assignCategory("Performance-Report");
        test.log(Status.INFO, "Performance Report - check started");

        for (int i = 1; i <= NumInitators; ++i) {
            String performanceFileName = performance + "_" + String.valueOf(i) + "_" + ibVersion;
            //log.info("for: " + ipList.get(i) + " firstBuilds is:  " + firstBuilds.get(i));
            linuxService.linuxRunSSHCommand(String.format(LinuxCommands.RUN_SQLITE_AVERAGE, firstBuilds.get(i-1)) + " >> " + performanceFileName + "; exit 0", ipList.get(i));
            linuxService.getFile(ipList.get(i), LinuxCommands.HOME_DIR + performanceFileName, Locations.LINUX_SCRIPT_OUTPUT + "\\" + performanceFileName);
        }

    }

}
