package frameworkInfra.testbases;

import com.aventstack.extentreports.Status;
import frameworkInfra.Listeners.EventHandler;
import frameworkInfra.Listeners.SuiteListener;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.SystemActions;
import io.appium.java_client.windows.WindowsDriver;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static frameworkInfra.Listeners.SuiteListener.extent;
import static frameworkInfra.Listeners.SuiteListener.test;

@Listeners(SuiteListener.class)
public class TestBase {

    public static final Logger log = Logger.getLogger(TestBase.class.getName());
    public WindowsDriver driver = null;
    public static WebDriver webDriver = null;
    public EventFiringWebDriver eventWebDriver;
    protected EventHandler handler = new EventHandler();
    public String testName = "";
    public static String OS = System.getProperty("os.name").toLowerCase();


    @BeforeSuite
    public static void cleanup(){
        SystemActions.deleteFilesByPrefix(System.getProperty("user.dir") + "", "*log.out");
        String log4jConfPath = "log4j.properties";
        PropertyConfigurator.configure(log4jConfPath);
        SystemActions.deleteFilesByPrefix(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reportscreenshots/", "*.png");

        //copy latest extent report to backup folder
        SystemActions.copyFilesByExtension(StaticDataProvider.Locations.WORKSPACE_REPORTS, StaticDataProvider.Locations.QA_ROOT + "\\Logs\\Automation HTML Reports", ".html", false);
        //delete HTML report from workspace folder
        SystemActions.deleteFilesByPrefix(StaticDataProvider.Locations.WORKSPACE_REPORTS, "Test");
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
