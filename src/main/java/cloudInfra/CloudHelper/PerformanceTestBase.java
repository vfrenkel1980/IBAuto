package cloudInfra.CloudHelper;

import cloudInfra.CloudServices.AwsService;
import cloudInfra.CloudServices.AzureService;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import frameworkInfra.testbases.TestBase;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import frameworkInfra.utils.databases.PostgresJDBC;
import ibInfra.ibService.IbService;
import ibInfra.windowscl.WindowsService;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
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

    protected WindowsService winService = new WindowsService();
    protected IbService ibService = new IbService();

    @BeforeSuite
    public void init(){
        SystemActions.deleteFilesByPrefix(Locations.WORKSPACE_REPORTS, "Test");
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/TestPerformance" + formatter.format(calendar.getTime()) + ".html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        int cores = Integer.parseInt(NUMOFMACHINES) * Integer.parseInt(CPU);
        requestedCores = Integer.toString(cores);
    }

    @AfterMethod
    public void afterMethod(){
        endTime = new Date(System.currentTimeMillis());
        long duration = endTime.getTime() - startTime.getTime();
        duration/=1000;
        String buildDuration = Long.toString(duration);
        postgresJDBC.insertDataToTable("192.168.10.73", "postgres", "postgres123", "release_manager", "Azure_Performance",
                "date, project_name, initiator, duration, helper_type, total_requested_cores",
                "\'" + formatter.format(calendar.getTime()) + "\', \'" + projectName + "\', \'" + INITIATOR + "\', \'" + buildDuration + "\', \'" + CLOUD + "\', \'" + requestedCores + "\'");
    }

    @AfterClass
    public void afterClass(){
        //TODO: copy .dat file to machine and create a method to list machines in the file
        ibService.customPackAllocationOn();
        ibService.unsubscribeAllMachines("coordinator-1");
    }
}
