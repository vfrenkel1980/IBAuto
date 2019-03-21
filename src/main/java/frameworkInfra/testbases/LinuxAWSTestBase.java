package frameworkInfra.testbases;

import cloudInfra.CloudServices.AwsService;
import com.aventstack.extentreports.Status;
import frameworkInfra.utils.StaticDataProvider;
import ibInfra.linuxcl.LinuxDBService;
import ibInfra.linuxcl.LinuxService;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import static frameworkInfra.Listeners.SuiteListener.extent;
import static frameworkInfra.Listeners.SuiteListener.test;

public class LinuxAWSTestBase extends TestBase{

    public LinuxService linuxService = new LinuxService();
    LinuxDBService linuxDBService = new LinuxDBService();
//    static AwsService awsService = new AwsService();
//    public Calendar calendar = Calendar.getInstance();
    protected SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
    protected static Vector< String> initIPVec = new Vector<>();
    protected  Vector< String> active_initiators_ip_vec = new Vector<>();
    static List<String> connectedMachinesToGrid;
    protected static String ibVersion = "";
    protected static String VERSION = System.getProperty("version");
    protected static String INITIATORS = System.getProperty("initiators");

    static {
        initIPVec.add(0, StaticDataProvider.LinuxAWS.COORD_IP); //coord inst id
        initIPVec.add(1, StaticDataProvider.LinuxAWS.INIT_PERF_INST_IP);
        initIPVec.add(2, StaticDataProvider.LinuxAWS.INIT_PERF_INST_IP);
        initIPVec.add(3, StaticDataProvider.LinuxAWS.INIT_PERF_INST_IP);
        initIPVec.add(4, StaticDataProvider.LinuxAWS.AND5_IP);
        initIPVec.add(5, StaticDataProvider.LinuxAWS.AND6_IP);
        initIPVec.add(6, StaticDataProvider.LinuxAWS.AND7_IP);
        initIPVec.add(7, StaticDataProvider.LinuxAWS.AND8_IP);
        initIPVec.add(8, StaticDataProvider.LinuxAWS.AND9_IP);
    }

    @BeforeSuite
    public void linuxSetup(ITestContext testContext) {
        OS = "linux";

        String[] init_arr = INITIATORS.split("[\\s\\,]+");

        for(String initiator: init_arr)
            active_initiators_ip_vec.add(initIPVec.get(Integer.parseInt(initiator)));

//        if (linuxService.startIBService(initIPVec.get(0),StaticDataProvider.LinuxAWS.WIN_KEY_FILE_PATH)) {
//            test.log(Status.ERROR, "IB service in coordinator "  + " is down... FAILING ALL TESTS!");
//            extent.flush();
//            System.exit(0);
//        }
//
//        connectedMachinesToGrid = linuxDBService.selectAll(StaticDataProvider.LinuxDB.DB_COORD_REPORT, StaticDataProvider.LinuxDB.COLUMN_MACHINE, StaticDataProvider.LinuxDB.TABLE_HELPER_MACHINES, initIPVec.get(0), StaticDataProvider.LinuxAWS.LINUX_KEY_FILE_PATH);
//        for (int i=0; i < connectedMachinesToGrid.size(); ++i) {
//            if (connectedMachinesToGrid.get(i).contains("."))
//                connectedMachinesToGrid.set(i, connectedMachinesToGrid.get(i).substring(0, connectedMachinesToGrid.get(i).indexOf(".")));
//        }
//
////        linuxService.deleteLogsFolder(connectedMachinesToGrid);
//
//        if (!VERSION.equals("current")) {
//            for (String currIP:active_initiators_ip_vec) {
//                if (linuxService.startIBService(currIP, StaticDataProvider.LinuxAWS.WIN_KEY_FILE_PATH) ){
//                    test.log(Status.ERROR, "IB service in initiator " + currIP + " is down... FAILING ALL TESTS!");
//                    extent.flush();
//                    System.exit(0);
//                }
//            }
//            linuxService.updateIB(initIPVec.get(0), VERSION, connectedMachinesToGrid, StaticDataProvider.LinuxAWS.LINUX_KEY_FILE_PATH);
//        }
//        ibVersion = linuxService.getIBVersion(initIPVec.get(0),StaticDataProvider.LinuxAWS.LINUX_KEY_FILE_PATH);
    }

    @BeforeClass
    public void initializeEnv(ITestContext testContext) {
        log.info("starting before class");
//        test = extent.createTest("Before Class");
//        test.assignCategory("BEFORE CLASS");
//        test.log(Status.INFO, "BEFORE CLASS started");
    }

}
