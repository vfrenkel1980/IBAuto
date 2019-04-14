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

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.remote.http.W3CHttpCommandCodec;
import org.openqa.selenium.remote.http.W3CHttpResponseCodec;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import static com.sun.jna.platform.win32.WinReg.*;
import static frameworkInfra.Listeners.SuiteListener.extent;
import static frameworkInfra.Listeners.SuiteListener.htmlReporter;
import static frameworkInfra.Listeners.SuiteListener.test;
import static io.restassured.RestAssured.when;


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
        winService.runCommandDontWaitForTermination("powershell.exe -noexit \"& '" + System.getProperty("user.dir") + "/src/main/resources/Scripts/startIbService.ps1'\"");
    }
}


