package frameworkInfra.testbases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.XmlParser;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.util.List;

public class LinuxMultiInitiatorsTestBase extends LinuxTestBase{

    protected static List<String> otherGridIPList;
    //protected static LinuxSimTestBase.TestNum testNum = TestNum.MultiIn;

    @BeforeSuite
    public void envSetUp(ITestContext testContext) {

        rawIpList = XmlParser.getIpList("MultiInitiators IP list.xml");
        ipList = XmlParser.breakDownIPList(rawIpList);
        testNum = TestNum.MultiIn;
        rawIpList3 = XmlParser.getIpList("MultiGridIPs.xml");
        multiGridIPList = XmlParser.breakDownIPList(rawIpList3);

        rawIpList2 = XmlParser.getIpList("MultiBuild IP list.xml");
        otherGridIPList = XmlParser.breakDownIPList(rawIpList2);
        linuxService.killib_db_check(ipList.get(1));

        ibVersion = getIBVersion();
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/TestOutput" + formatter.format(calendar.getTime()) + " - " + ibVersion + ".html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    //@Override
    @BeforeClass
    public void initializeEnv(ITestContext testContext){
        log.info("starting before class");
        test = extent.createTest("Before Class");
        test.assignCategory("BEFORE CLASS");
        test.log(Status.INFO, "BEFORE CLASS started");


        linuxService.deleteLogsFolder(multiGridIPList);

        if(!linuxService.isIBServiceUp("ib_server", ipList.get(0))) {
            test.log(Status.ERROR, "IB service is down... FAILING ALL TESTS!");
            extent.flush();
            throw new SkipException("EXITING");
        }

        for (int i=1; i<5; ++i) {

            if(linuxService.startIBService("ib_server", ipList.get(i))) {
                String err = "startIBService failed " +ipList.get(i) + "... FAILING ALL TESTS!";
                test.log(Status.ERROR, err);
                extent.flush();
                throw new SkipException("EXITING");
            }
        }

        if(linuxService.stopIBService("ib_server", otherGridIPList.get(1))) {
            String err = "stopIBService failed " +ipList.get(1) + "... FAILING ALL TESTS!";
            test.log(Status.ERROR, err);
            extent.flush();
            throw new SkipException("EXITING");
        }
        log.info("finished before class");
    }

    @BeforeMethod
    // @Parameters({"cycle"})
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
        if(linuxService.startIBService("ib_server", otherGridIPList.get(1))) {
            String err = "startIBService failed " +ipList.get(1) + "... FAILING ALL TESTS!";
            test.log(Status.ERROR, err);
        }

        linuxService.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_KERNEL_DIR + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";", ipList.get(1));
        linuxService.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_GPSD_DIR + ";" + StaticDataProvider.LinuxSimulation.SCONS_CLEAN + ";", ipList.get(2));
        linuxService.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_CMAKE_DIR + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";", ipList.get(3));
        linuxService.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_APACHE_DIR + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";", ipList.get(4));

        extent.flush();
        log.info("finished after class");
    }

    @AfterSuite
    public void afterSuite() {
        //runscript
    }
}
