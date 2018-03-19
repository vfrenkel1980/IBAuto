package frameworkInfra.testbases;

import com.aventstack.extentreports.Status;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.XmlParser;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.*;

import java.util.List;

public class LinuxMultiBuildTestBase extends LinuxSimTestBase{

    public static List<String> otherGridIPList;

    @Override
    @BeforeClass
    public void initializeEnv(ITestContext testContext){
        log.info("starting before class");
        test = extent.createTest("Before Class");
        test.assignCategory("BEFORE CLASS");
        test.log(Status.INFO, "BEFORE CLASS started");

        rawIpList2 = XmlParser.getIpList("MultiInitiators IP list.xml");
        otherGridIPList = linuxService.breakDownIPList(rawIpList2);

        log.info("starting delete logs folder");
        linuxService.deleteLogsFolder(multiGridIPList);
        log.info("finished delete logs folder");

        if(!linuxService.isIBServiceUp("ib_server", ipList.get(0))) {
            test.log(Status.ERROR, "IB service is down... FAILING ALL TESTS!");
            extent.flush();
            throw new SkipException("EXITING");
        }

        if(linuxService.startIBService("ib_server", ipList.get(1))) {
            String err = "startIBService failed " +ipList.get(1) + "... FAILING ALL TESTS!";
            test.log(Status.ERROR, err);
            extent.flush();
            throw new SkipException("EXITING");
        }

        for (int i=1; i<5; ++i) {

            if(linuxService.stopIBService("ib_server", otherGridIPList.get(i))) {
                String err = "stopIBService failed " +otherGridIPList.get(i) + "... FAILING ALL TESTS!";
                test.log(Status.ERROR, err);
                extent.flush();
                throw new SkipException("EXITING");
            }
        }
        log.info("finished before class");
    }

    @Override
    @AfterClass
    public void afterClass() {
        log.info("starting after class");
        test = extent.createTest("After Class");
        test.assignCategory("AFTER CLASS");
        test.log(Status.INFO, "AFTER CLASS started");
        for (int i = 1; i < 5; ++i) {

            if (linuxService.startIBService("ib_server", otherGridIPList.get(i))) {
                String err = "startIBService failed " + otherGridIPList.get(i) + "... FAILING ALL TESTS!";
                test.log(Status.ERROR, err);
                extent.flush();
                throw new SkipException("EXITING");
            }
        }

        linuxService.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_KERNEL_DIR + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";", ipList.get(1));
        linuxService.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_APACHE_DIR + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";", ipList.get(1));
        linuxService.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_CMAKE_DIR + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";", ipList.get(1));
        linuxService.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_GIT_DIR + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";", ipList.get(1));

        extent.flush();
        log.info("finished after class");
    }

}
