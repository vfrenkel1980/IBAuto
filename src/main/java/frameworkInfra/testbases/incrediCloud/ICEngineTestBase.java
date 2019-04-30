package frameworkInfra.testbases.incrediCloud;

import cloudInfra.IncrediCloud.incrediCloudService.IncrediCloudService;
import cloudInfra.IncrediCloud.pageObjects.AzureRegistrationPageObject;
import cloudInfra.IncrediCloud.pageObjects.OnboardingPageObject;
import cloudInfra.IncrediCloud.webServer.WebServer;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import frameworkInfra.Listeners.SuiteListener;
import frameworkInfra.testbases.TestBase;
import ibInfra.ibService.IbService;
import ibInfra.ibUIService.IBUIService;
import ibInfra.windowscl.WindowsService;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import java.io.File;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import static frameworkInfra.Listeners.SuiteListener.extent;
import static frameworkInfra.Listeners.SuiteListener.htmlReporter;
import static frameworkInfra.Listeners.SuiteListener.test;

@Listeners(SuiteListener.class)
public class ICEngineTestBase extends TestBase {

    final public String COORDID = "Automation";
    final protected int POOL_SIZE = 4;
    //TODO: change the number of cores (2) to an var once cores selection is implemented
    final public int GRID_CORES_WO_CLOUD = 8;
    final public int GRID_CORES = POOL_SIZE * 2 + GRID_CORES_WO_CLOUD;
    final public int POOL_CORES = POOL_SIZE * 2;
    final public int PORT = 12345;
    final protected int TIMEOUT = 480;
    final protected int CORES_LIMIT = 20;
    final protected int COORD_PORT = 31105;
    final protected int VM_PORT = 31106;
    protected WindowsService winService = new WindowsService();
    protected IbService ibService = new IbService();
    protected AzureRegistrationPageObject azurePageObject;
    protected OnboardingPageObject onboardingPageObject;
    protected WebServer webServer = new WebServer(PORT);
    protected Thread serverThread = new Thread(webServer);
    protected IncrediCloudService icService = new IncrediCloudService(COORDID);
    private IBUIService ibuiService = new IBUIService();
    protected IBUIService.Coordinator coordinator = ibuiService.new Coordinator();

    static {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/TestOutput" + formatter.format(calendar.getTime()) + "- ICEngine.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @BeforeClass
    public void beforeClass(){
        test = extent.createTest("Before Class");
        ibService.updateIB("Latest");
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/main/resources/WebDrivers/chromedriver.exe");
        webDriver = new ChromeDriver();
        eventWebDriver = new EventFiringWebDriver(webDriver);
        eventWebDriver.register(handler);
        eventWebDriver.get("https://incredicloud.azurewebsites.net/?coord_id=" + COORDID + "&redirect_uri=http://127.0.0.1:" + PORT + "/cloudauthentication");
        eventWebDriver.manage().window().maximize();
        eventWebDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        azurePageObject = new AzureRegistrationPageObject(eventWebDriver);
        onboardingPageObject = new OnboardingPageObject(eventWebDriver);
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        testName = getTestName(method);
        test = extent.createTest(testName);
        test.assignCategory("ICEngine test");
        test.log(Status.INFO, method.getName() + " test started");
    }

    @AfterMethod
    public void afterMethod(){
        if (webDriver != null) {
            webDriver.quit();
            eventWebDriver.quit();
            eventWebDriver.unregister(handler);
            webDriver = null;
        }
        extent.flush();
    }


    protected void startWebServerThread(){
        serverThread.start();
    }

    protected void waitForWebServerResponse(){
        try {
            serverThread.join();
        } catch (InterruptedException e) {
            test.log(Status.ERROR, "Failed to retrieve response from server with error: " + e.getMessage());
        }
    }

}
