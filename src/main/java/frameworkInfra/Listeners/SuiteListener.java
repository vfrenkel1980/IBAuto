package frameworkInfra.Listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.testng.ISuite;
import org.testng.ISuiteListener;

/**
 * SuiteListener is used to perform desired actions on
 * before and after suite is run.
 * We also use this listener in order to initialize the
 * extent report objects so they can be widely used.
 */


public class SuiteListener implements ISuiteListener {

    public static ExtentReports extent;
    public static ExtentTest test;
    public static ExtentHtmlReporter htmlReporter;

    @Override
    public void onStart(ISuite suite) {
    }

    @Override
    public void onFinish(ISuite suite) {
        extent.flush();
    }
}
