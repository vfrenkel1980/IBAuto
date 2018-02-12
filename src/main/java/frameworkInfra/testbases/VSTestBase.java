package frameworkInfra.testbases;

import frameworkInfra.utils.SystemActions;
import com.aventstack.extentreports.Status;
import io.appium.java_client.windows.WindowsDriver;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class VSTestBase extends TestBase {
    public static WindowsDriver driver = null;

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
            test.log(Status.INFO, "Opening VS2017");
            capabilities.setCapability("app", "C:\\Program Files (x86)\\Microsoft Visual Studio\\2017\\Professional\\Common7\\IDE\\devenv.exe");
            driver = new WindowsDriver(new URL("http://127.0.0.1:4723"), capabilities);
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            test.log(Status.INFO, "Visual Studio opened successfully");
            SystemActions.deleteFilesByPrefix(System.getProperty("java.io.tmpdir"), "pkt");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    @AfterMethod
    public void afterMethod(ITestResult result) throws IOException {
        if (driver != null) {
            driver.quit();
        }
        driver = null;
        getResult(result);
    }
}
