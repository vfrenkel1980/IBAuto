package frameworkInfra.testbases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import frameworkInfra.Listeners.SuiteListener;
import frameworkInfra.utils.SystemActions;
import ibInfra.ibService.IbService;
import ibInfra.linuxcl.LinuxService;
import ibInfra.vsui.VSUIService;
import ibInfra.windowscl.WindowsService;
import io.appium.java_client.windows.WindowsDriver;
import org.apache.log4j.PropertyConfigurator;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.*;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static frameworkInfra.Listeners.SuiteListener.extent;
import static frameworkInfra.Listeners.SuiteListener.test;

@Listeners(SuiteListener.class)
public class TestBase {

    public static final Logger log = Logger.getLogger(TestBase.class.getName());
    public static WindowsDriver driver = null;
    public String testName = "";
    public static String OS = System.getProperty("os.name").toLowerCase();


    @BeforeSuite
    public static void cleanup(){
        SystemActions.deleteFilesByPrefix(System.getProperty("user.dir") + "", "*log.out");
        String log4jConfPath = "log4j.properties";
        PropertyConfigurator.configure(log4jConfPath);
        SystemActions.deleteFilesByPrefix(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reportscreenshots/", "*.png");
    }

    public void log(String data){
        log.info(data);
        Reporter.log(data);
        test.log(Status.INFO, data);
    }

    @AfterClass
    public void afterClass(){
        extent.flush();
    }

    public String getTestName(Method method){
        Test testAnnotation = (Test) method.getAnnotation(Test.class);
        return testAnnotation.testName();
    }

}
