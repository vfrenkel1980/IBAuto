package Native.UnitTests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import frameworkInfra.testbases.BatmanBCTestBase;
import frameworkInfra.testbases.TestBase;
import frameworkInfra.utils.*;

import frameworkInfra.utils.databases.PostgresJDBC;
import ibInfra.ibService.IbService;

import ibInfra.windowscl.WindowsService;

import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.sun.jna.platform.win32.WinReg.*;
import static frameworkInfra.Listeners.SuiteListener.extent;
import static frameworkInfra.Listeners.SuiteListener.htmlReporter;
import static frameworkInfra.Listeners.SuiteListener.test;


public class UnitTests {
    WindowsService winService = new WindowsService();
    IbService ibService = new IbService();
    PostgresJDBC postgresJDBC = new PostgresJDBC();
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
    String projectName = "test";
    String INITIATOR = "testinit";
    private Date startTime;
    private Date endTime;
    String requestedCores = "15";


    @Test(testName = "test1")
    public void test() {
        startTime = new Date(System.currentTimeMillis());
        endTime = new Date(System.currentTimeMillis());
        long duration = startTime.getTime() - endTime.getTime();
        duration/=1000;
        String buildDuration = Long.toString(duration);
        postgresJDBC.insertDataToTable("192.168.10.73", "postgres", "postgres123", "release_manager", "Azure_Performance",
                "date, project_name, initiator, duration, total_requested_cores",
                "\'" + formatter.format(calendar.getTime()) + "\', \'" + projectName + "\', \'" + INITIATOR + "\', \'" + buildDuration + "\', \'" + requestedCores+ "\'");
/*        postgresJDBC.insertDataToTable("192.168.10.73", "postgres", "postgres123", "release_manager", "vs_release_versioning",
                "vs_version, ib_version, msbuild_version, ib_installer_name",
                "1,1,1,1");*/
    }
}


