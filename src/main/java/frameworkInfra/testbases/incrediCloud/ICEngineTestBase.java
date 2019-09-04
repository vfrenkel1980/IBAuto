package frameworkInfra.testbases.incrediCloud;

import cloudInfra.IncrediCloud.Pages.OnboardingPage;
import cloudInfra.IncrediCloud.incrediCloudService.IncrediCloudService;
import cloudInfra.IncrediCloud.pageObjects.*;
import cloudInfra.IncrediCloud.webServer.WebServer;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import frameworkInfra.Listeners.SuiteListener;
import frameworkInfra.testbases.TestBase;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import ibInfra.ibService.IbService;
import ibInfra.ibUIService.IBUIService;
import ibInfra.windowscl.WindowsService;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static frameworkInfra.Listeners.SuiteListener.extent;
import static frameworkInfra.Listeners.SuiteListener.htmlReporter;
import static frameworkInfra.Listeners.SuiteListener.test;

@Listeners(SuiteListener.class)
public class ICEngineTestBase extends TestBase {

    public static String ENV = System.getProperty("incredicloudEnv");
    public static String TYPE = System.getProperty("machineType");
    public static String CLOUD = System.getProperty("cloudtype");
    final protected String PROD_USER = "mark@doriextermanxoreax.onmicrosoft.com";
    final protected String LIMITED_USER = "mark2@doriextermanxoreax.onmicrosoft.com";
    final public String COORDID = "Automation";
    final protected int POOL_SIZE = 4;
    public int MACHINE_CORES = getMachineCores(TYPE);
    final public int ON_PREM_CORES = 16;
    final public int GRID_CORES = POOL_SIZE * MACHINE_CORES + ON_PREM_CORES;
    final public int POOL_CORES = POOL_SIZE * MACHINE_CORES;
    final public int PORT = 12345;
    final protected int TIMEOUT = 480;
    final protected int CORES_LIMIT = 100;
    final protected int COORD_PORT = 31105;
    final protected int VM_PORT = 31106;
    protected WindowsService winService = new WindowsService();
    protected IbService ibService = new IbService();
    protected RegistrationPageObject cloudRegistrationPageObject;
    protected OnboardingPageObject onboardingPageObject;
    protected WebServer webServer = new WebServer(PORT);
    protected Thread serverThread = new Thread(webServer);
    protected IncrediCloudService icService = new IncrediCloudService(COORDID);
    private IBUIService ibuiService = new IBUIService();
    protected IBUIService.Coordinator coordinator = ibuiService.new Coordinator();
    protected OnboardingPage onboardingPage;

    static {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/TestOutput" + formatter.format(calendar.getTime()) + "- IncrediCloud.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @BeforeSuite
    public void beforeSuite(){
        test = extent.createTest("Before Suite");
        ibService.updateIB(IB_VERSION);
        switch (ENV){
            case "prod":
                RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\Coordinator", RegistryKeys.INCREDICLOUDSITEURL, "https://incredicloud.azurewebsites.net");
                RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\Coordinator", RegistryKeys.INCREDICLOUDAPIURL, "https://incredicloudapim-prod.azure-api.net");
                break;
            case "uat":
                RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\Coordinator", RegistryKeys.INCREDICLOUDSITEURL, "https://incredicloud-onboarding-uat.azurewebsites.net");
                RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\Coordinator", RegistryKeys.INCREDICLOUDAPIURL, "https://incredicloudapigwtest.azure-api.net");
                break;
            case "aws":
                RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\Coordinator", RegistryKeys.INCREDICLOUDSITEURL, "https://incredicloud-onboarding-aws.azurewebsites.net");
                RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\Coordinator", RegistryKeys.INCREDICLOUDAPIURL, "https://incredicloudapim-aws.azure-api.net");
                break;
        }
    }

    @BeforeClass
    public void beforeClass(){
        SystemActions.sleep(600); //wait for the previous resource to be deleted
        test = extent.createTest("Before Class");
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/main/resources/WebDrivers/chromedriver.exe");
        webDriver = new ChromeDriver();
        eventWebDriver = new EventFiringWebDriver(webDriver);
        eventWebDriver.register(handler);
        switch (ENV){
            case "prod":
                eventWebDriver.get("https://incredicloud.azurewebsites.net/?coord_id=" + COORDID + "&redirect_uri=http://127.0.0.1:" + PORT + "/cloudauthentication");
                break;
            case "uat":
                eventWebDriver.get("https://incredicloud-onboarding-uat.azurewebsites.net/?coord_id=" + COORDID + "&redirect_uri=http://127.0.0.1:" + PORT + "/cloudauthentication");
                break;
            case "aws":
                eventWebDriver.get("https://incredicloud-onboarding-aws.azurewebsites.net/?coord_id=" + COORDID + "&redirect_uri=http://127.0.0.1:" + PORT + "/cloudauthentication");
                break;
        }
        eventWebDriver.manage().window().maximize();
        eventWebDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        switch (CLOUD){
            case "azure":
                cloudRegistrationPageObject = new AzureRegistrationPageObject(eventWebDriver);
                onboardingPage = new OnboardingPage("UK West", "Test", "User", "Test@user.com", "Com", TYPE, TIMEOUT, CORES_LIMIT, POOL_SIZE,
                        COORD_PORT, VM_PORT);
                break;
            case "aws":
                cloudRegistrationPageObject = new AWSRegistrationPageObject(eventWebDriver);
                //TODO: change to AWS values
                onboardingPage = new OnboardingPage("EU (Ireland)", "Test", "User", "Test@user.com", "Com", TYPE, TIMEOUT, CORES_LIMIT, POOL_SIZE,
                        COORD_PORT, VM_PORT);
        }
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
    public void afterMethod(Method method){
        if (method.getName().equals("performOnboarding") || method.getName().equals("updateCloudSettings"))
            webDriver.close();
        extent.flush();
    }

    @AfterSuite
    public void afterSuite(){
        SystemActions.killProcess(Processes.COORDMONITOR);
        killDriver();
    }


    //METHODS
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

    protected void killDriver(){
        if (webDriver != null) {
            webDriver.quit();
            eventWebDriver.quit();
            eventWebDriver.unregister(handler);
            webDriver = null;
        }
    }

    private int getMachineCores(String machineType){
        int cores= 0;
        switch (machineType){
            case "D2":
                cores = 2;
                break;
            case "D4s_v3":
                cores = 4;
        }
        return cores;
    }

}
