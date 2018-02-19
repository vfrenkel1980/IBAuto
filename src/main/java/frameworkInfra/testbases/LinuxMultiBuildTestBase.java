package frameworkInfra.testbases;

import com.aventstack.extentreports.Status;
import frameworkInfra.utils.XmlParser;
import org.testng.annotations.*;

import java.util.List;

public class LinuxMultiBuildTestBase extends LinuxSimTestBase{

    public static List<String> otherGridIPList;

    @Override
    @BeforeClass
    public void initializeEnv(){
        log.info("starting before class");
        test = extent.createTest("Before Class");
        test.assignCategory("BEFORE CLASS");
        test.log(Status.INFO, "BEFORE CLASS started");

        rawIpList2 = XmlParser.getIpList("MultiInitiators IP list.xml");
        otherGridIPList = runLinux.breakDownIPList(rawIpList2);

        log.info("starting delete logs folder");
        runLinux.deleteLogsFolder(ipList);
        log.info("finished delete logs folder");

        if(!runLinux.isIBServiceUp("ib_server", ipList.get(0))) {
            test.log(Status.ERROR, "IB service is down... FAILING ALL TESTS!");
            extent.flush();
            System.exit(0);
        }

        if(runLinux.startIBService("ib_server", ipList.get(1))) {
            String err = "startIBService failed " +ipList.get(1) + "... FAILING ALL TESTS!";
            test.log(Status.ERROR, err);
            extent.flush();
            System.exit(0);
        }

        for (int i=1; i<5; ++i) {

            if(runLinux.stopIBService("ib_server", otherGridIPList.get(i))) {
                String err = "stopIBService failed " +otherGridIPList.get(i) + "... FAILING ALL TESTS!";
                test.log(Status.ERROR, err);
                extent.flush();
                System.exit(0);
            }
        }
        log.info("finished before class");
    }

    @Override
    @AfterClass  //???input? exeptions?
    public void afterClass() {
        log.info("starting after class");
        for (int i = 1; i < 5; ++i) {

            if (runLinux.startIBService("ib_server", otherGridIPList.get(i))) {
                String err = "startIBService failed " + otherGridIPList.get(i) + "... FAILING ALL TESTS!";
                test.log(Status.ERROR, err);
                extent.flush();
                System.exit(0);
            }
        }
        extent.flush();
        log.info("finished after class");
    }

}
