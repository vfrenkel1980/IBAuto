package cloudInfra.CloudHelper;

import cloudInfra.CloudServices.AwsService;
import cloudInfra.CloudServices.AzureService;
import cloudInfra.CloudServices.CloudService;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import frameworkInfra.Listeners.SuiteListener;
import frameworkInfra.testbases.TestBase;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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

    static {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
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
    }



}
