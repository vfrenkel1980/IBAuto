package frameworkInfra.testbases;

import com.aventstack.extentreports.Status;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.XmlParser;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.util.List;

public class LinuxMultiInitiatorsTestBase extends LinuxSimTestBase{

    public static List<String> otherGridIPList;

    @Override
    @BeforeClass
    public void initializeEnv(ITestContext testContext){
        log.info("starting before class");
        test = extent.createTest("Before Class");
        test.assignCategory("BEFORE CLASS");
        test.log(Status.INFO, "BEFORE CLASS started");

        rawIpList2 = XmlParser.getIpList("MultiBuild IP list.xml");
        otherGridIPList = runLinux.breakDownIPList(rawIpList2);

        //runLinux.deleteLogsFolder(ipList);

        if(!runLinux.isIBServiceUp("ib_server", ipList.get(0))) {
            test.log(Status.ERROR, "IB service is down... FAILING ALL TESTS!");
            extent.flush();
            System.exit(0);
        }

        for (int i=1; i<5; ++i) {

            if(runLinux.startIBService("ib_server", ipList.get(i))) {
                String err = "startIBService failed " +ipList.get(i) + "... FAILING ALL TESTS!";
                test.log(Status.ERROR, err);
                extent.flush();
                System.exit(0);
            }
        }

        if(runLinux.stopIBService("ib_server", otherGridIPList.get(1))) {
            String err = "stopIBService failed " +ipList.get(1) + "... FAILING ALL TESTS!";
            test.log(Status.ERROR, err);
            extent.flush();
            System.exit(0);
        }
        log.info("finished before class");
    }

    @Override
    @AfterClass
    public void afterClass() {
        log.info("starting after class");
        if(runLinux.startIBService("ib_server", otherGridIPList.get(1))) {
            String err = "startIBService failed " +ipList.get(1) + "... FAILING ALL TESTS!";
            test.log(Status.ERROR, err);
        }

        runLinux.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_KERNEL_DIR + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";", ipList.get(1));
        runLinux.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_GPSD_DIR + ";" + StaticDataProvider.LinuxSimulation.SCONS_CLEAN + ";", ipList.get(2));
        runLinux.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_CMAKE_DIR + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";", ipList.get(3));
        runLinux.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_APACHE_DIR + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";", ipList.get(4));

        extent.flush();
        log.info("finished after class");
    }

}
