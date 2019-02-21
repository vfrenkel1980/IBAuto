package frameworkInfra.testbases;

import cloudInfra.CloudServices.AwsService;
import com.aventstack.extentreports.Status;
import ibInfra.linuxcl.LinuxDBService;
import ibInfra.linuxcl.LinuxService;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Vector;

import static frameworkInfra.Listeners.SuiteListener.extent;
import static frameworkInfra.Listeners.SuiteListener.test;

public class LinuxAWSTestBase extends TestBase{

    public LinuxService linuxService = new LinuxService();
    LinuxDBService linuxDBService = new LinuxDBService();
//    static AwsService awsService = new AwsService();
//    public Calendar calendar = Calendar.getInstance();
    protected SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
    protected Vector<String> init_inst_id_vec = new Vector<String>();


    @BeforeSuite
    public void linuxSetup(ITestContext testContext) {
        OS = "linux";

        //update IB version  - coord
        //get initiators?? group??




    }


    @BeforeClass
    public void initializeEnv(ITestContext testContext) {
        log.info("starting before class");
        test = extent.createTest("Before Class");
        test.assignCategory("BEFORE CLASS");
        test.log(Status.INFO, "BEFORE CLASS started");


    }


}
