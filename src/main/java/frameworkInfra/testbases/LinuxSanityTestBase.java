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
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static frameworkInfra.Listeners.SuiteListener.*;


@Listeners(SuiteListener.class)
public class LinuxSanityTestBase extends LinuxTestBase {

    private String className = this.getClass().getName();
    static List<String>  HostNameList;
    protected static String SanityHostName = "l1a-u16-STests";
    private static String CoorHostName = "l1a-u14-coor";
    private static String firstBuild = new String();
    private static String lastBuild = new String();

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
//        testType = TestType.Sanity;
//        ipList = XmlParser.breakDownIPList(rawIpList);
        log.info("RUNNING VERSION: " + VERSION);
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/TestOutput" + formatter.format(calendar.getTime()) + " - " + ibVersion + ".html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        try {
           log.info( "Reverting machine ");
            Runtime.getRuntime().exec("cmd vmrun stop  \"F:\\VMs\\l2b-u16-S_Tests\\l2b-u16-S_Tests.vmx\"");
            Runtime.getRuntime().exec("cmd vmrun revertToSnapshot  \"F:\\VMs\\l2b-u16-S_Tests\\l2b-u16-S_Tests.vmx\"");
            Runtime.getRuntime().exec("cmd vmrun start  \"F:\\VMs\\l2b-u16-S_Tests\\l2b-u16-S_Tests.vmx\"");
        } catch (IOException e) {
            e.getMessage();
        }

//clean - no IB

//        linuxService.killibDbCheck(SanityHostName);

//        connectedMachinesToGrid = linuxDBService.selectAll(LinuxDB.DB_COORD_REPORT, LinuxDB.COLUMN_MACHINE, LinuxDB.TABLE_HELPER_MACHINES, ipList.get(0));
//        for (int i=0; i < connectedMachinesToGrid.size(); ++i) {
//            if (connectedMachinesToGrid.get(i).contains("."))
//                connectedMachinesToGrid.set(i, connectedMachinesToGrid.get(i).substring(0, connectedMachinesToGrid.get(i).indexOf(".")));
//        }

        HostNameList.add(SanityHostName);
        linuxService.deleteLogsFolder(HostNameList);

        firstBuild = getFirstBuild(SanityHostName);

        if (!VERSION.equals("current"))
            linuxService.updateIB(ipList.get(0), VERSION, connectedMachinesToGrid);
        ibVersion = linuxService.getIBVersion(ipList.get(0));

        log.info("finished before suite");
    }


    @BeforeClass
    public void initializeEnv(ITestContext testContext) {
        log.info("starting before class");
        test = extent.createTest("Before Class");
        test.assignCategory("BEFORE CLASS");
        test.log(Status.INFO, "BEFORE CLASS started");


//        if (!linuxService.isIBServiceUp( ipList.get(0))) {
//            test.log(Status.ERROR, "IB service in coordinator is down... FAILING ALL TESTS!");
//            extent.flush();
//            System.exit(0);
//        }
//
//        for (int i=1; i <= NumInitators; ++i) {
//            if ((i == simClassType.ordinal()) && (linuxService.startIBService(ipList.get(i)))) {
//                test.log(Status.ERROR, "IB service in initiator "+ i + " is down... FAILING ALL TESTS!");
//                extent.flush();
//                System.exit(0);
//            }
//            else if((i != simClassType.ordinal()) && (linuxService.stopIBService(ipList.get(i)))) {
//                String err = "startIBService failed " + ipList.get(i) + "... FAILING ALL TESTS!";
//                test.log(Status.ERROR, err);
//            }
//        }

        log.info("finished before class");
    }

    @BeforeMethod
//    @Parameters({"cycle"})
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
        boolean isFailed;
        int exitStatus = 0;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy");
        String output = "res_" + dateFormat.format(calendar.getTime());

//        for (int i=1; i <= NumInitators; ++i) {
//            String scriptFileName = output + "_" + String.valueOf(i);
//
//            linuxService.startIBService(ipList.get(i));
//            lastBuilds.add(linuxService.runQueryLastBuild(LinuxCommands.BUILD_ID, LinuxCommands.BUILD_HISTORY, ipList.get(i).replaceAll("\n", "")).replaceAll("\n", ""));
//
//            if(Integer.parseInt(lastBuilds.get(i-1)) >= Integer.parseInt(firstBuilds.get(i-1)))
//              linuxService.linuxRunSSHCommand("./ib_db_check.py -d sim2_ib_db_check_data.py -r " + firstBuilds.get(i-1) + "," + lastBuilds.get(i-1) + " > " + scriptFileName + "; exit 0", ipList.get(i));
//
//            linuxService.getFile(ipList.get(i), LinuxCommands.HOME_DIR + scriptFileName, Locations.LINUX_SCRIPT_OUTPUT + "\\" + scriptFileName);
//        }

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
    }

}
