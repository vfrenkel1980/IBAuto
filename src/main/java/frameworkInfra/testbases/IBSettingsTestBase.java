package frameworkInfra.testbases;

import com.aventstack.extentreports.Status;
import frameworkInfra.Listeners.SuiteListener;
import io.appium.java_client.windows.WindowsDriver;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@Listeners(SuiteListener.class)
public class IBSettingsTestBase extends TestBase {

    public static WindowsDriver session = null;

    @BeforeClass
    public static void init(){
        String log4jConfPath = "log4j.properties";
        PropertyConfigurator.configure(log4jConfPath);

    }

    @BeforeMethod
    public void beforeMethod(Method method){
        test = extent.createTest(method.getName());
        test.assignCategory("UI");
        test.log(Status.INFO, method.getName() + " test started");

        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            test.log(Status.INFO, "Opening IBSettings");
            capabilities.setCapability("app", "C:\\Program Files (x86)\\Xoreax\\IncrediBuild\\BuildSettings.exe");
            session = new WindowsDriver(new URL("http://127.0.0.1:4723"), capabilities);
            session.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            test.log(Status.INFO, "IBSettings opened successfully");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    @AfterMethod
    public void afterMethod(ITestResult result) throws IOException {
        if (session != null) {
            session.quit();
        }
        session = null;
    }
}
