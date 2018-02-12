package frameworkInfra.testbases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.Status;
import frameworkInfra.utils.StaticDataProvider;
import ibInfra.ibService.IIBService;
import ibInfra.ibService.IbService;
import ibInfra.vsui.VSUIService;
import ibInfra.windowscl.WindowsService;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class VSTestBase extends TestBase {

    public static WindowsDriver driver = null;
    private static int ibVersion = 0;
    public VSUIService vsService = new VSUIService();
    public WindowsService runWin = new WindowsService();
    public IbService runIb = new IbService();
    private String SCENARIO = System.getProperty("scenario");

    static {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/TestOutput" + formatter.format(calendar.getTime()) + ".html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @BeforeClass
    public void setUpEnv() {
        test = extent.createTest("Before Class");
        test.log(Status.INFO, "Before class started");
        //vs installed, install IB from installer
        if (SCENARIO.equals("1")) {
            runIb.installIB();
        }

        //upgrade vs and install IB from vs installer
        if (SCENARIO.equals("2")) {
            String oldExtensionVersion = runIb.getIbVsExtensionVersion();
            vsService.upgradeVSWithIB();
            String extensionVersion = runIb.getIbVsExtensionVersion();
            ibVersion = IIBService.getIbVersion();
            if (runIb.verifyExtensionUpgrade(oldExtensionVersion, extensionVersion))
                test.log(Status.INFO, "VS extension upgrade from " + oldExtensionVersion + " to " + extensionVersion);
            else
                test.log(Status.ERROR, "VS extension did not upgrade and remained " + oldExtensionVersion);
            if (runIb.verifyIbInstallation(ibVersion))
                test.log(Status.INFO, "IB " + ibVersion + " installed successfully from VS installer");
            else
                test.log(Status.ERROR, "IB failed to install from VS Installer");

        }

        //install old IB, install vs and upgrade IB from VS installer
        if (SCENARIO.equals("3")){
            runIb.installIB();
            int oldIbVersion = IIBService.getIbVersion();
            vsService.installVSWithIB();
            String extensionVersion = "";
            ibVersion = IIBService.getIbVersion();

            if (runIb.verifyIbUpgrade(oldIbVersion, ibVersion))
                test.log(Status.INFO, "IB upgraded successfully from " + oldIbVersion + " to " + ibVersion);
            else
                test.log(Status.ERROR, "IB failed to upgrade from " + oldIbVersion + " to " + " using VS Installer");
        }

        //install vs without IB
        if (SCENARIO.equals("4")){
            String extensionVersion = "";
            vsService.installVSWithoutIB();
            if (runIb.verifyExtensionInstalled(extensionVersion))
                test.log(Status.INFO, "Extension installed successfully with version " + extensionVersion);
            else
                test.log(Status.ERROR, "Extension installation failed. Version is " + extensionVersion);
        }
    }

    @BeforeMethod
    public void beforeMethod(Method method, ITestContext context){
        test = extent.createTest(method.getName());
        test.log(Status.INFO, method.getName() + " test started");
        test.assignCategory(context.getName());
        log.info(method.getName() + " test started");

        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            test.log(Status.INFO, "Opening VS2017");
            capabilities.setCapability("app", "C:\\Program Files (x86)\\Microsoft Visual Studio\\Preview\\Professional\\Common7\\IDE\\devenv.exe");
            driver = new WindowsDriver(new URL("http://127.0.0.1:4723"), capabilities);
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            test.log(Status.INFO, "Visual Studio opened successfully");
            try {
                driver.findElementByName("Not now, maybe later.").click();
                vsService.vsFirstActivation();
            } catch (Exception e){
                e.getMessage();
            }
        } catch (MalformedURLException e) {
            e.getMessage();
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
