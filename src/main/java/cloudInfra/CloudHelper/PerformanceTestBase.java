package cloudInfra.CloudHelper;

import cloudInfra.CloudServices.AwsService;
import cloudInfra.CloudServices.AzureService;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import frameworkInfra.testbases.TestBase;
import frameworkInfra.utils.databases.PostgresJDBC;
import ibInfra.windowscl.WindowsService;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static frameworkInfra.Listeners.SuiteListener.extent;
import static frameworkInfra.Listeners.SuiteListener.htmlReporter;
import static frameworkInfra.Listeners.SuiteListener.test;

public class PerformanceTestBase extends CloudTestBase{


    static {
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/Performance" + formatter.format(calendar.getTime()) + ".html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    protected WindowsService winService = new WindowsService();

    @BeforeSuite
    public void init(){
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/Performance" + formatter.format(calendar.getTime()) + ".html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
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
