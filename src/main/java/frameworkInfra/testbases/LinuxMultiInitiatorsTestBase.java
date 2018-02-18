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

    public static LinuxService runLinux = new LinuxService();
    protected static List rawIpList;
    public static List<String> ipList;
    public static List<String> otherGridIPList;
    String buildID;
    private static String ibVersion = "";


    @BeforeClass
    public void initializeEnv(){
        rawIpList = XmlParser.getIpList("MultiBuild IP list.xml");
        ipList = runLinux.breakDownIPList(rawIpList);

        rawIpList = XmlParser.getIpList("MultiInitiators IP list.xml");
        otherGridIPList = runLinux.breakDownIPList(rawIpList);

        test = extent.createTest("Before Suite");
        test.assignCategory("BEFORE SUITE");
        test.log(Status.INFO, "BEFORE SUITE started");

        runLinux.deleteLogsFolder(ipList);

        if(!runLinux.isIBServiceUp("ib_server", ipList.get(0))) {
            test.log(Status.ERROR, "IB service is down... FAILING ALL TESTS!");
            extent.flush();
            System.exit(0);
        }

        if(runLinux.StartIBService("ib_server", ipList.get(1))) {
            String err = "StartIBService failed $s... FAILING ALL TESTS!"+ipList.get(1);
            test.log(Status.ERROR, err);
            extent.flush();
            System.exit(0);
        }

        if(runLinux.StartIBService("ib_server", ipList.get(2))) {
            String err = "StartIBService failed $s... FAILING ALL TESTS!"+ipList.get(1);
            test.log(Status.ERROR, err);
            extent.flush();
            System.exit(0);
        }

        if(runLinux.StartIBService("ib_server", ipList.get(3))) {
            String err = "StartIBService failed $s... FAILING ALL TESTS!"+ipList.get(1);
            test.log(Status.ERROR, err);
            extent.flush();
            System.exit(0);
        }

        if(runLinux.StartIBService("ib_server", ipList.get(4))) {
            String err = "StartIBService failed $s... FAILING ALL TESTS!"+ipList.get(1);
            test.log(Status.ERROR, err);
            extent.flush();
            System.exit(0);
        }

        if(runLinux.StartIBService("ib_server", otherGridIPList.get(1))) {
            String err = "StartIBService failed $s... FAILING ALL TESTS!"+otherGridIPList.get(1);
            test.log(Status.ERROR, err);
            extent.flush();
            System.exit(0);
        }
    }

    @Override
    @AfterClass  //???input? exeptions?
    public void afterClass() {

        if(runLinux.StartIBService("ib_server", otherGridIPList.get(1))) {
            String err = "StartIBService failed $s... FAILING ALL TESTS!"+otherGridIPList.get(1);
            test.log(Status.ERROR, err);
        }

        extent.flush();
    }

}
