package cloudInfra.CloudHelper;

import cloudInfra.CloudServices.AwsService;
import cloudInfra.CloudServices.AzureService;
import cloudInfra.CloudServices.CloudService;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import frameworkInfra.Listeners.SuiteListener;
import frameworkInfra.testbases.TestBase;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import frameworkInfra.utils.databases.PostgresJDBC;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static frameworkInfra.Listeners.SuiteListener.extent;
import static frameworkInfra.Listeners.SuiteListener.htmlReporter;
import static frameworkInfra.Listeners.SuiteListener.test;

@Listeners(SuiteListener.class)
public class CloudTestBase extends TestBase {

    protected static String NUMOFMACHINES = System.getProperty("numofmachines");
    protected static String CPU = System.getProperty("cpu");
    protected static String MEMORY = System.getProperty("memory");
    protected static String CLOUD = System.getProperty("cloudtype");
    protected static String INITIATOR = System.getProperty("initiators");
    protected CloudService cloudService;
    public PostgresJDBC postgresJDBC = new PostgresJDBC();
    private static Calendar calendar = Calendar.getInstance();
    private static SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
    protected String projectName;
    private String requestedCores;
    private Date startTime;
    private Date endTime;

    static {

        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/CloudSetup" + formatter.format(calendar.getTime()) + ".html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @BeforeSuite
    public void init(){
        test = extent.createTest("Before Suite");
        SystemActions.deleteFile(Locations.CLOUD_IDS_JSON);
        switch (CLOUD) {
            case "azure":
                cloudService = new AzureService(CPU, MEMORY, NUMOFMACHINES, INITIATOR);
                break;
            case "aws":
                cloudService = new AwsService(CPU, MEMORY, NUMOFMACHINES, INITIATOR);
                break;
        }

        int cores = Integer.parseInt(NUMOFMACHINES) * Integer.parseInt(CPU);
        requestedCores = Integer.toString(cores);
    }

    @BeforeMethod
    public void beforeMethod(Method method, ITestContext context){
        test = extent.createTest(method.getName());
        test.log(Status.INFO, method.getName() + " test started");
        startTime = new Date(System.currentTimeMillis());
    }

    @AfterMethod
    public void afterMethod(){
        endTime = new Date(System.currentTimeMillis());
        long duration = startTime.getTime() - endTime.getTime();
        duration/=1000;
        String buildDuration = Long.toString(duration);
        postgresJDBC.insertDataToTable("192.168.10.73", "postgres", "postgres123", "release_manager", "Azure_Performance",
                "date, project_name, initiator, duration, helper_type, total_requested_cores",
                "\'" + formatter.format(calendar.getTime()) + "\', \'" + projectName + "\', \'" + INITIATOR + "\', \'" + buildDuration + "\', \'" + cloudService.getType() + "\', \'" + requestedCores+ "\'");
    }



}
