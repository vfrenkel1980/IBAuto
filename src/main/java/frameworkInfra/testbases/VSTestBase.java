package frameworkInfra.testbases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.SystemActions;
import com.aventstack.extentreports.Status;
import ibInfra.vsui.VSUIService;
import ibInfra.windowscl.IWindowsCL;
import ibInfra.windowscl.WindowsCLService;
import io.appium.java_client.windows.WindowsDriver;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;

public class VSTestBase extends TestBase {
    public static WindowsDriver driver = null;
    private static int ibVersion = 0;
    public VSUIService vsServie = new VSUIService();


    static {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/TestOutput" + formatter.format(calendar.getTime()) + ".html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @BeforeClass
    //@Parameters({"scenario"})
    public static void setUpEnv(/*String scenario*/) {
        test = extent.createTest("Before Class");
        test.log(Status.INFO, "Before class started");
/*        if (scenario.equals("1")) {

        }

        if (scenario.equals("2")) {
            test.log(Status.INFO,"2");
        }
        if (scenario.equals("3"))
            System.out.println("scenario 3");
        if (scenario.equals("4"))
            System.out.println("scenario 4");*/
    }

    @BeforeMethod
    public void beforeMethod(Method method, ITestContext context){
        test = extent.createTest(method.getName());
        test.log(Status.INFO, method.getName() + " test started");
        test.assignCategory(context.getName());

        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            test.log(Status.INFO, "Opening VS2017");
            capabilities.setCapability("app", "C:\\Program Files (x86)\\Microsoft Visual Studio\\Preview\\Professional\\Common7\\IDE\\devenv.exe");
            driver = new WindowsDriver(new URL("http://127.0.0.1:4723"), capabilities);
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            test.log(Status.INFO, "Visual Studio opened successfully");
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

    private static int getIBVersion(){
        String regVersion = RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\builder", StaticDataProvider.RegistryKeys.VERSION);
        int version = Integer.parseInt(regVersion);
        version -= 1001000;
        return version;
    }
}
