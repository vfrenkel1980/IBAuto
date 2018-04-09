package frameworkInfra.testbases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.sun.jndi.toolkit.url.Uri;
import frameworkInfra.utils.SystemActions;
import ibInfra.windowscl.WindowsService;
import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class SetupTestBase extends TestBase {

    public WindowsService winService = new WindowsService();


    static {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/TestOutput" + formatter.format(calendar.getTime()) + ".html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @BeforeMethod
    public void beforeMethod(Method method, ITestContext context) {
        test = extent.createTest(method.getName());
        test.log(Status.INFO, method.getName() + " test started");
        test.assignCategory(context.getName());
/*        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();

            capabilities.setCapability("app", "Root");
            driver = new WindowsDriver(new URL("http://127.0.0.1:4723"), capabilities);
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

            WebElement installerWindow = driver.findElementByName("IncrediBuild Setup - Welcome");
            String installerTopLevelWindowHandle = installerWindow.getAttribute("NativeWindowHandle");
            installerTopLevelWindowHandle = String.format("%040x", new BigInteger(1, installerTopLevelWindowHandle.getBytes("UTF-8")));

            DesiredCapabilities appCapabilities = new DesiredCapabilities();
            appCapabilities.setCapability("appTopLevelWindow", installerTopLevelWindowHandle);
            driver = new WindowsDriver(new URL("http://127.0.0.1:4723"), appCapabilities);

        } catch (MalformedURLException | UnsupportedEncodingException e) {
            e.getMessage();
        }*/
        try{
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("app", "\"C:\\Users\\Mark\\Desktop\\Xoreax_Setup_14620\\Setup.exe\"");
            driver = new WindowsDriver(new URL("http://127.0.0.1:4723"), capabilities);
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


    }

    @AfterMethod
    public void afterMethod(ITestResult result)throws IOException {
        if (driver != null) {
            driver.quit();
        }
        driver = null;
        getResult(result);
        extent.flush();
    }
}
