package frameworkInfra.testbases;

import com.aventstack.extentreports.Status;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.XmlParser;
import ibInfra.linuxcl.LinuxService;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.util.List;

public class LinuxMultiInitiatorsTestBase extends LinuxSimTestBase{

    public static List<String> otherGridIPList;

    @Override
    @BeforeClass
    public void initializeEnv(){
        test = extent.createTest("Before Class");
        test.assignCategory("BEFORE CLASS");
        test.log(Status.INFO, "BEFORE CLASS started");

        rawIpList2 = XmlParser.getIpList("MultiBuild IP list.xml");
        otherGridIPList = runLinux.breakDownIPList(rawIpList2);

        runLinux.deleteLogsFolder(ipList);

        if(!runLinux.isIBServiceUp("ib_server", ipList.get(0))) {
            test.log(Status.ERROR, "IB service is down... FAILING ALL TESTS!");
            extent.flush();
            System.exit(0);
        }

        for (int i=1; i<5; ++i) {

            if(runLinux.StartIBService("ib_server", ipList.get(i))) {
                String err = "StartIBService failed " +ipList.get(i) + "... FAILING ALL TESTS!";
                test.log(Status.ERROR, err);
                extent.flush();
                System.exit(0);
            }
        }

        if(runLinux.StopIBService("ib_server", otherGridIPList.get(1))) {
            String err = "StartIBService failed $s... FAILING ALL TESTS!"+otherGridIPList.get(1);
            test.log(Status.ERROR, err);
            extent.flush();
            System.exit(0);
        }
    }

    @Override
    @AfterClass
    public void afterClass() {

        if(runLinux.StartIBService("ib_server", otherGridIPList.get(1))) {
            String err = "StartIBService failed $s... FAILING ALL TESTS!"+otherGridIPList.get(1);
            test.log(Status.ERROR, err);
        }

        extent.flush();
    }

}
