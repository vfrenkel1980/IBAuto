package frameworkInfra.testbases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import frameworkInfra.utils.SystemActions;
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


public class TestBase {

    public static final Logger log = Logger.getLogger(TestBase.class.getName());

    public static ExtentReports extent;
    public static ExtentTest test;
    public static ExtentHtmlReporter htmlReporter;
    public String testName = "";

    @BeforeSuite
    public static void cleanup(){
        SystemActions.deleteFilesByPrefix(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reportscreenshots/", "*.png");
        SystemActions.deleteFilesByPrefix(System.getProperty("user.dir") + "", "*log.out");
    }

    @BeforeClass
    public static void init(){
        String log4jConfPath = "log4j.properties";
        PropertyConfigurator.configure(log4jConfPath);
    }

    public void log(String data){
        log.info(data);
        Reporter.log(data);
        test.log(Status.INFO, data);
    }

    public void getResult(ITestResult result) throws IOException {
        if (result.getStatus()==ITestResult.SUCCESS){
            test.log(Status.PASS, result.getName() + " test passed");
        }else if (result.getStatus()==ITestResult.SKIP){
            test.log(Status.SKIP, result.getName() + " test skipped");
        }else if (result.getStatus()==ITestResult.FAILURE){
            test.log(Status.ERROR, result.getName() + " test has failed " + result.getThrowable());
            String path = captureScreenshot(result.getName());
            test.fail("Screenshot " + test.addScreenCaptureFromPath(path, "Screenshot"));
        }
        log.info(result.getName() + "test finished");
    }

    public String captureScreenshot(String fileName){
        File destFile = null;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");

        try {
            Robot robot = new Robot();
            String format = ".png";
            fileName = fileName + "_Screenshot";

            String screenShotDirectory = new File(System.getProperty("user.dir")).getAbsolutePath() + "/src/main/java/frameworkInfra/reportscreenshots/";
            destFile = new File(screenShotDirectory + fileName + "_" + formatter.format(calendar.getTime()) + format);
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage image = robot.createScreenCapture(screenRect);
            ImageIO.write(image, "png", destFile);

        } catch (AWTException | IOException ex) {
            System.err.println(ex);
        }
        return destFile.getPath();
    }

    @AfterMethod
    public void afterMethod(ITestResult result) throws IOException, InterruptedException {
        getResult(result);
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
