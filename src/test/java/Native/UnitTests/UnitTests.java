package Native.UnitTests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import frameworkInfra.testbases.BatmanBCTestBase;
import frameworkInfra.testbases.TestBase;
import frameworkInfra.utils.*;

import frameworkInfra.utils.databases.PostgresJDBC;
import frameworkInfra.utils.parsers.Parser;
import ibInfra.ibService.IbService;

import ibInfra.windowscl.WindowsService;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.remote.http.W3CHttpCommandCodec;
import org.openqa.selenium.remote.http.W3CHttpResponseCodec;
import org.testng.Assert;
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


    @Test(testName = "IBTC Help Test")
    public void iBTCHelpTest() {
        String result = winService.runCommandGetOutput(StaticDataProvider.IbLocations.IBCONSOLE+ " /command=\"" + StaticDataProvider.ProjectsCommands.TESTING_ROBIN.NUNIT3_1DLL_TEST+"\" /test=gtest" );
        int exitCode = winService.runCommandWaitForFinish(StaticDataProvider.IbLocations.IBCONSOLE+ " /command=\"" + StaticDataProvider.ProjectsCommands.TESTING_ROBIN.NUNIT3_1DLL_TEST+"\" /test=gtest");
        System.out.println(result);
        Assert.assertTrue(exitCode == 3, "The test execution errorlevel is not match to 3. Errorlevel = " + exitCode);
     //   Assert.assertTrue(Parser.doesFileContainString(StaticDataProvider.Locations.OUTPUT_LOG_FILE, "Agent '"), "No agents were assigned to the build");
        Assert.assertTrue(result.contains("In order to accelerate NUnit tests, please use IBTestConsole."));
    }
}



