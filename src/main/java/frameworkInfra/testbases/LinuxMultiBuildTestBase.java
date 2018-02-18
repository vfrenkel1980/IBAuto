package frameworkInfra.testbases;

import com.aventstack.extentreports.Status;
import frameworkInfra.utils.XmlParser;
import org.testng.annotations.*;

import java.util.List;

public class LinuxMultiBuildTestBase extends LinuxSimTestBase{

    public static List<String> otherGridIPList;


    @BeforeClass
    public void initializeEnv(){
        test = extent.createTest("Before Class");
        test.assignCategory("BEFORE CLASS");
        test.log(Status.INFO, "BEFORE CLASS started");

        rawIpList2 = XmlParser.getIpList("MultiInitiators IP list.xml");
        otherGridIPList = runLinux.breakDownIPList(rawIpList2);

        runLinux.deleteLogsFolder(ipList);

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
    }

    @Override
    @AfterClass  //???input? exeptions?
    public void afterClass() {

        for (int i = 1; i < 5; ++i) {

            if (runLinux.startIBService("ib_server", otherGridIPList.get(i))) {
                String err = "startIBService failed " + otherGridIPList.get(i) + "... FAILING ALL TESTS!";
                test.log(Status.ERROR, err);
                extent.flush();
                System.exit(0);
            }
            extent.flush();
        }
    }

}
