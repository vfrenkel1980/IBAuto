package frameworkInfra.Listeners;

import com.aventstack.extentreports.Status;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static frameworkInfra.Listeners.SuiteListener.extent;
import static frameworkInfra.Listeners.SuiteListener.test;
import static frameworkInfra.testbases.LinuxTestBase.buildID;
import static frameworkInfra.testbases.TestBase.OS;

public class TestListener implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {

    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.log(Status.PASS, result.getName() + " test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        if (OS.contains("win")) {
            test.log(Status.ERROR, result.getName() + " test has failed " + result.getThrowable());
        } else if (OS.contains("linux")){
            test.log(Status.ERROR, result.getName() + " test has failed - Build ID = " + buildID + "------->" + result.getThrowable());
        }
        String path = captureScreenshot(result.getName());
        try {
            test.fail("Screenshot " + test.addScreenCaptureFromPath(path, "Screenshot"));
        } catch (IOException e) {
            e.getMessage();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.log(Status.SKIP, result.getName() + " test skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }

    private String captureScreenshot(String fileName){
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
}
