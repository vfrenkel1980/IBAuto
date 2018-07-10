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
import org.testng.SkipException;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.List;

import static frameworkInfra.Listeners.SuiteListener.*;

@Listeners(SuiteListener.class)
public class LinuxMultiBuildTestBase extends LinuxTestBase{

    private static List<String> otherGridIPList;
    private String firstBuild = "";

    @BeforeSuite
    public void envSetUp(ITestContext testContext) {
        switch (ENV){
            case "linuxsim2a":
                rawIpList = XmlParser.getIpList("MultiBuild IP list.xml");
                rawIpList2 = XmlParser.getIpList("MultiInitiators IP list.xml");
                break;
            case "linuxsim2b":
                rawIpList = XmlParser.getIpList("Secondary MultiBuild IP list.xml");
                rawIpList2 = XmlParser.getIpList("Secondary MultiInitiators IP list.xml");
                break;
        }
        ipList = XmlParser.breakDownIPList(rawIpList);
        testNum = TestNum.MultiBuild;

        otherGridIPList = XmlParser.breakDownIPList(rawIpList2);
        linuxService.killibDbCheck(ipList.get(1));

        ibVersion = getIBVersion();
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/TestOutput" + formatter.format(calendar.getTime()) + " - " + ibVersion + ".html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        SystemActions.deleteFilesByPrefix(Locations.LINUX_SCRIPT_OUTPUT + "MultiBuild\\", "res");

        firstBuild = getFirstBuild(ipList.get(1));
    }

    @BeforeClass
    public void initializeEnv(ITestContext testContext){
        log.info("starting before class");
        test = extent.createTest("Before Class");
        test.assignCategory("BEFORE CLASS");
        test.log(Status.INFO, "BEFORE CLASS started");

        /*log.info("starting delete logs folder");
        linuxService.deleteLogsFolder(connectedMachinesToGrid);
        log.info("finished delete logs folder");*/

        if(!linuxService.isIBServiceUp( ipList.get(0))) {
            test.log(Status.ERROR, "IB service is down... FAILING ALL TESTS!");
            extent.flush();
            throw new SkipException("EXITING");
        }

        if(linuxService.startIBService( ipList.get(1))) {
            String err = "startIBService failed " +ipList.get(1) + "... FAILING ALL TESTS!";
            test.log(Status.ERROR, err);
            extent.flush();
            throw new SkipException("EXITING");
        }

        for (int i=1; i<5; ++i) {

            if(linuxService.stopIBService( otherGridIPList.get(i))) {
                String err = "stopIBService failed " +otherGridIPList.get(i) + "... FAILING ALL TESTS!";
                test.log(Status.ERROR, err);
                extent.flush();
                throw new SkipException("EXITING");
            }
        }
        log.info("finished before class");
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        testName = getTestName(method);
        test = extent.createTest(testName);
        test.assignCategory("Linux Multi Build");
        test.log(Status.INFO, method.getName() + " test started");
    }

    @Override
    @AfterClass
    public void afterClass() {
        log.info("starting after class");
        test = extent.createTest("After Class");
        test.assignCategory("AFTER CLASS");
        test.log(Status.INFO, "AFTER CLASS started");
        for (int i = 1; i < 5; ++i) {

            if (linuxService.startIBService(otherGridIPList.get(i))) {
                String err = "startIBService failed " + otherGridIPList.get(i) + "... FAILING ALL TESTS!";
                test.log(Status.ERROR, err);
                extent.flush();
                throw new SkipException("EXITING");
            }
        }

        linuxService.linuxRunSSHCommand(LinuxSimulation.CD_KERNEL_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";", ipList.get(1));
        linuxService.linuxRunSSHCommand(LinuxSimulation.CD_APACHE_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";", ipList.get(1));
        linuxService.linuxRunSSHCommand(LinuxSimulation.CD_CMAKE_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";", ipList.get(1));
        linuxService.linuxRunSSHCommand(LinuxSimulation.CD_GIT_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";", ipList.get(1));

        extent.flush();
        log.info("finished after class");
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
        linuxService.linuxRunSSHCommand("./ib_db_check.py -d mb_ib_db_check_data.py -r " + firstBuild + "," + suiteLastBuild + " > " + output + "; exit 0" , ipList.get(1));
        linuxService.getFile(ipList.get(1), "/home/xoraex/" + output, Locations.LINUX_SCRIPT_OUTPUT + "MultiBuild\\" + output);

        List<String> files = SystemActions.getAllFilesInDirectory(Locations.LINUX_SCRIPT_OUTPUT + "MultiBuild\\");
        for (String file: files ) {
            isFailed = Parser.doesFileContainString(file, "ErrorMessages:");
            if (isFailed)
                test.log(Status.WARNING, "Errors found in " + file);
        }
    }
}
