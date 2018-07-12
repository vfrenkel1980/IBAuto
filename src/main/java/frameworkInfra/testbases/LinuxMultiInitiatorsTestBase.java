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
import ibInfra.linuxcl.LinuxMultiThreaded;
import ibInfra.linuxcl.LinuxRunScriptThreads;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static frameworkInfra.Listeners.SuiteListener.*;

@Listeners(SuiteListener.class)
public class LinuxMultiInitiatorsTestBase extends LinuxTestBase{

    private static List<String> otherGridIPList;
    private List<String> firstBuilds = new ArrayList<String>();

    @BeforeSuite
    public void envSetUp(ITestContext testContext) {
        switch (ENV){
            case "linuxsim2a":
                rawIpList = XmlParser.getIpList("MultiInitiators IP list.xml");
                rawIpList2 = XmlParser.getIpList("MultiBuild IP list.xml");
                break;
            case "linuxsim2b":
                rawIpList = XmlParser.getIpList("Secondary MultiInitiators IP list.xml");
                rawIpList2 = XmlParser.getIpList("Secondary MultiBuild IP list.xml");
                break;
        }
        ipList = XmlParser.breakDownIPList(rawIpList);
        testNum = TestNum.MultiIn;

        otherGridIPList = XmlParser.breakDownIPList(rawIpList2);
        linuxService.killibDbCheck(ipList.get(1));

        connectedMachinesToGrid = linuxDBService.selectAll(LinuxDB.DB_COORD_REPORT, LinuxDB.COLUMN_MACHINE, LinuxDB.TABLE_HELPER_MACHINES, ipList.get(0));
        for (int i=0; i < connectedMachinesToGrid.size(); ++i) {
            if (connectedMachinesToGrid.get(i).contains("."))
                connectedMachinesToGrid.set(i, connectedMachinesToGrid.get(i).substring(0,connectedMachinesToGrid.get(i).indexOf(".")));
        }

        ibVersion = getIBVersion();
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/TestOutput" + formatter.format(calendar.getTime()) + " - " + ibVersion + ".html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        SystemActions.deleteFilesByPrefix(Locations.LINUX_SCRIPT_OUTPUT + "MultiInitiator\\", "res");

        for (int i = 1 ; i < 5 ; i++){
            firstBuilds.add(getFirstBuild(ipList.get(i)));
        }
    }

    @BeforeClass
    public void initializeEnv(ITestContext testContext){
        log.info("starting before class");
        test = extent.createTest("Before Class");
        test.assignCategory("BEFORE CLASS");
        test.log(Status.INFO, "BEFORE CLASS started");
        linuxService.deleteLogsFolder(connectedMachinesToGrid);

        if(!linuxService.isIBServiceUp( ipList.get(0))) {
            test.log(Status.ERROR, "IB service is down... FAILING ALL TESTS!");
            extent.flush();
            throw new SkipException("EXITING");
        }

        for (int i = 1; i < 5; ++i) {

            if(linuxService.startIBService( ipList.get(i))) {
                String err = "startIBService failed " +ipList.get(i) + "... FAILING ALL TESTS!";
                test.log(Status.ERROR, err);
                extent.flush();
                throw new SkipException("EXITING");
            }
        }

        if(linuxService.stopIBService(otherGridIPList.get(1))) {
            String err = "stopIBService failed " +ipList.get(1) + "... FAILING ALL TESTS!";
            test.log(Status.ERROR, err);
            extent.flush();
            throw new SkipException("EXITING");
        }
        log.info("finished before class");
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        testName = getTestName(method);
        test = extent.createTest(testName);
        test.assignCategory("Linux Multi Initiators");
        test.log(Status.INFO, method.getName() + " test started");
    }

    @Override
    @AfterClass
    public void afterClass() {
        log.info("starting after class");
        if(linuxService.startIBService(otherGridIPList.get(1))) {
            String err = "startIBService failed " +ipList.get(1) + "... FAILING ALL TESTS!";
            test.log(Status.ERROR, err);
        }

        for (int i=1; i<5; ++i){
            if(linuxService.isLinuxOSUbuntu(ipList.get(i))) {

                linuxService.linuxRunSSHCommand(LinuxSimulation.CD_KERNEL_DIR + ";" + LinuxSimulation.MAKE_CLEAN, ipList.get(i));
                linuxService.linuxRunSSHCommand(LinuxSimulation.CD_APACHE_DIR + ";" + LinuxSimulation.MAKE_CLEAN , ipList.get(i));
                linuxService.linuxRunSSHCommand(LinuxSimulation.CD_CMAKE_DIR + ";" + LinuxSimulation.MAKE_CLEAN, ipList.get(i));
                linuxService.linuxRunSSHCommand(LinuxSimulation.CD_SAMBA_DIR + ";" + LinuxSimulation.MAKE_CLEAN, ipList.get(i));
                linuxService.linuxRunSSHCommand(LinuxSimulation.CD_CPP_DIR + ";" + LinuxSimulation.MAKE_CLEAN, ipList.get(i));
                linuxService.linuxRunSSHCommand(LinuxSimulation.CD_MYSQL_DIR + ";" + LinuxSimulation.MAKE_CLEAN, ipList.get(i));
                linuxService.linuxRunSSHCommand(LinuxSimulation.CD_GIT_DIR + ";" + LinuxSimulation.MAKE_CLEAN, ipList.get(i));
                linuxService.linuxRunSSHCommand(LinuxSimulation.CD_GDB_DIR + ";" + LinuxSimulation.MAKE_CLEAN, ipList.get(i));
                linuxService.linuxRunSSHCommand(LinuxSimulation.CD_BOOST_DIR + ";" + LinuxSimulation.B2_CLEAN, ipList.get(i));
            }
            else{
                linuxService.linuxRunSSHCommand(LinuxSimulation.CD_KERNEL_DIR + ";" + LinuxSimulation.MAKE_CLEAN, ipList.get(i));
                linuxService.linuxRunSSHCommand(LinuxSimulation.CD_GPSD_DIR + ";" + LinuxSimulation.SCONS_CLEAN, ipList.get(i));
                linuxService.linuxRunSSHCommand(LinuxSimulation.CD_SAMBA2_DIR + ";" + LinuxSimulation.MAKE_CLEAN, ipList.get(i));
                linuxService.linuxRunSSHCommand(LinuxSimulation.CD_MYSQL2_DIR + ";" + LinuxSimulation.MAKE_CLEAN, ipList.get(i));
            }
        }



        extent.flush();
        log.info("finished after class");
    }

    @AfterSuite
    public void afterSuite() throws InterruptedException {
        test = extent.createTest("AFTER SUITE");
        test.assignCategory("AFTER SUITE");
        test.log(Status.INFO, "AFTER SUITE" + " test started");
        boolean isFailed;

        ExecutorService execService = Executors.newFixedThreadPool(4);
        execService.execute(new LinuxRunScriptThreads(firstBuilds.get(0), ipList.get(1)));
        execService.execute(new LinuxRunScriptThreads(firstBuilds.get(1), ipList.get(2)));
        execService.execute(new LinuxRunScriptThreads(firstBuilds.get(2), ipList.get(3)));
        execService.execute(new LinuxRunScriptThreads(firstBuilds.get(3), ipList.get(4)));

        execService.shutdown();
        execService.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);

        List<String> files = SystemActions.getAllFilesInDirectory(Locations.LINUX_SCRIPT_OUTPUT + "MultiInitiator\\");
        for (String file: files ) {
            isFailed = Parser.doesFileContainString(file, "ErrorMessages:");
            if (isFailed)
                test.log(Status.WARNING, "Errors found in " + file);
        }

    }
}
