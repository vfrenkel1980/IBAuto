package frameworkInfra.testbases.incrediCloud;

import cloudInfra.IncrediCloud.Pages.OnboardingPage;
import cloudInfra.IncrediCloud.Utils.CloudUtils;
import cloudInfra.IncrediCloud.incrediCloudService.IncrediCloudService;
import cloudInfra.IncrediCloud.metadata.Configuration.CloudConfigurationData;
import cloudInfra.IncrediCloud.metadata.Enums.CloudStatusType;
import cloudInfra.IncrediCloud.metadata.Enums.CloudType;
import cloudInfra.IncrediCloud.metadata.Enums.OnboardingType;
import cloudInfra.IncrediCloud.metadata.VirtualMachineInfo;
import cloudInfra.IncrediCloud.pageObjects.AWSRegistrationPageObject;
import cloudInfra.IncrediCloud.pageObjects.AzureRegistrationPageObject;
import cloudInfra.IncrediCloud.pageObjects.OnboardingPageObject;
import cloudInfra.IncrediCloud.pageObjects.RegistrationPageObject;
import cloudInfra.IncrediCloud.webServer.WebServer;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import frameworkInfra.Listeners.SuiteListener;
import frameworkInfra.testbases.TestBase;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.StaticDataProvider.Locations;
import frameworkInfra.utils.StaticDataProvider.Processes;
import frameworkInfra.utils.StaticDataProvider.RegistryKeys;
import frameworkInfra.utils.SystemActions;
import ibInfra.ibService.IbService;
import ibInfra.ibUIService.IBUIService;
import ibInfra.windowscl.WindowsService;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static frameworkInfra.Listeners.SuiteListener.*;

@Listeners(SuiteListener.class)
public class ICEngineTestBase extends TestBase {

    public static String TYPE;
    public static CloudType CLOUD;
    public static OnboardingType ONBOARDING_TYPE;

    public static String ENV = System.getProperty("incredicloudEnv");
    //    public static String TYPE = System.getProperty("machineType");
//    public static CloudType CLOUD = CloudType.valueOf(System.getProperty("cloudtype"));
//    public static OnboardingType ONBOARDING_TYPE = (System.getProperty("onboardingType") == null ? OnboardingType.BASIC_ONBOARDING : OnboardingType.valueOf(System.getProperty("onboardingType")));
    public static boolean IS_PRIVATE_NETWORK = (System.getProperty("isPVOn") == null ? false : Boolean.getBoolean(System.getProperty("isPVOn")));
    final protected String PROD_USER = "automation@incredicloudcs.onmicrosoft.com";
    final protected String LIMITED_USER = "automation@incredicloudcs.onmicrosoft.com";
    final public String COORDID = "Automation";
    final protected int POOL_SIZE = 4;
    //    public int MACHINE_CORES = getMachineCores(TYPE);
//    final public int ON_PREM_CORES = 16;
//    final public int GRID_CORES = POOL_SIZE * MACHINE_CORES + ON_PREM_CORES;
//    final public int POOL_CORES = POOL_SIZE * MACHINE_CORES;
    public int MACHINE_CORES;
    final public int ON_PREM_CORES = 16;
    public int GRID_CORES;
    public int POOL_CORES;

    final public int PORT = 12345;
    final protected int TIMEOUT = 480;
    final protected int CORES_LIMIT = 60;
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
    protected boolean isOnBoarding = false;
    protected CloudConfigurationData configurationData;

    static {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/TestOutput" + formatter.format(calendar.getTime()) + "- IncrediCloud.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @BeforeSuite
    public void beforeSuite() throws Exception {
        test = extent.createTest("Before Suite");
        // ibService.updateIB(IB_VERSION);

        webServer = new WebServer(PORT);
        serverThread = new Thread(webServer);
        icService = new IncrediCloudService(COORDID);

        // retrieve Cloud configuration data
        configurationData = CloudUtils.getCloudConfigurationData();

        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\Coordinator", RegistryKeys.MINIDLELEVEL, "0.02");
        switch (ENV) {
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
            case "dev":
                RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\Coordinator", RegistryKeys.INCREDICLOUDSITEURL, "https://incredicloud-onboarding.azurewebsites.net");
                RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\Coordinator", RegistryKeys.INCREDICLOUDAPIURL, "https://incredicloudapigwdev.azure-api.net");
                break;
        }
    }

    @BeforeClass
    @Parameters({"machineType", "cloudtype", "onboardingType"})
    public void beforeClass(String machineType, String cloudtype, String onboardingType) {
        TYPE = machineType;
        CLOUD = CloudType.valueOf(cloudtype);
        ONBOARDING_TYPE = (onboardingType == null ? OnboardingType.BASIC_ONBOARDING : OnboardingType.valueOf(onboardingType));
        calculateCoresGrid();
        //SystemActions.sleep(600); //wait for the previous resource to be deleted
        test = extent.createTest("Before Class");
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/main/resources/WebDrivers/chromedriver.exe");
        webDriver = new ChromeDriver();
        eventWebDriver = new EventFiringWebDriver(webDriver);
        eventWebDriver.register(handler);
        switch (ENV) {
            case "prod":
                eventWebDriver.get("https://incredicloud.azurewebsites.net/?coord_id=" + COORDID + "&redirect_uri=http://127.0.0.1:" + PORT + "/cloudauthentication");
                break;
            case "uat":
                eventWebDriver.get("https://incredicloud-onboarding-uat.azurewebsites.net/?coord_id=" + COORDID + "&redirect_uri=http://127.0.0.1:" + PORT + "/cloudauthentication");
                break;
            case "aws":
                eventWebDriver.get("https://incredicloud-onboarding-aws.azurewebsites.net/?coord_id=" + COORDID + "&redirect_uri=http://127.0.0.1:" + PORT + "/cloudauthentication");
                break;
            case "dev":
                eventWebDriver.get("https://incredicloud-onboarding.azurewebsites.net/?coord_id=" + COORDID + "&redirect_uri=http://127.0.0.1:" + PORT + "/cloudauthentication");
                break;
        }
        eventWebDriver.manage().window().maximize();
        eventWebDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        switch (CLOUD) {
            case AZURE:
                cloudRegistrationPageObject = new AzureRegistrationPageObject(eventWebDriver);
                onboardingPage = new OnboardingPage("North Europe", "Test", "User", "Test@user.com", "Com", TYPE, TIMEOUT, CORES_LIMIT, POOL_SIZE,
                        COORD_PORT, VM_PORT);

                // Set private network
                if (IS_PRIVATE_NETWORK) {
                    onboardingPage.setIsPrivateNetwork(true);
                    onboardingPage.setResourceGroupName(configurationData.getAzureConfigurationData().getResourceGroupName());
                    onboardingPage.setVirtualNetwork(configurationData.getAzureConfigurationData().getVirtualNetwork());
                    onboardingPage.setPrivateNetworkSubnet(configurationData.getAzureConfigurationData().getSubnet());
                }
                break;
            case AWS:
                cloudRegistrationPageObject = new AWSRegistrationPageObject(eventWebDriver);
                onboardingPage = new OnboardingPage("EU (Ireland)", "Test", "User", "Test@user.com", "Com", TYPE, TIMEOUT, CORES_LIMIT, POOL_SIZE,
                        COORD_PORT, VM_PORT);

                if (IS_PRIVATE_NETWORK) {
                    onboardingPage.setIsPrivateNetwork(true);
                    onboardingPage.setPrivateNetworkVpc(configurationData.getAwsConfigurationData().getVpc());
                    onboardingPage.setPrivateNetworkSubnet(configurationData.getAwsConfigurationData().getSubnet()); // default
                    //onboardingPage.setPrivateNetworkSubnet(configurationData.getAwsConfigurationData().getAdditionalSubnet());
                }
                break;

        }

        // Set Custom tags
        HashMap<String, String> customTags = new HashMap();
        customTags.put("group", "RnD");
        customTags.put("team", "QA");
        onboardingPage.setCustomTags(customTags);
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
    public void afterMethod(Method method) {
        // if (method.getName().equals("performOnboarding") || method.getName().equals("updateCloudSettings"))

    }

    @AfterClass
    public void afterClass() {
        try {
            webServer.closeWebServer();
            RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\Coordinator", StaticDataProvider.RegistryKeys.INCREDICLOUDSECRET, "");
            winService.restartService(StaticDataProvider.WindowsServices.COORD_SERVICE);
            if (webDriver != null) {
                webDriver.close();
            }

            if (extent != null) {
                extent.flush();
            }
        } catch (Exception e) {
            test.log(Status.INFO, "Afterclass");
        }

        if (isOnBoarding) {
            deactivateCloud();
        }
        SystemActions.killProcess(Processes.COORDMONITOR);
        killDriver();
    }


    //METHODS
    protected void calculateCoresGrid() {
        MACHINE_CORES = getMachineCores(TYPE);
        GRID_CORES = POOL_SIZE * MACHINE_CORES + ON_PREM_CORES;
        POOL_CORES = POOL_SIZE * MACHINE_CORES;
    }

    protected void startWebServerThread() {
        serverThread.start();
    }

    protected void waitForWebServerResponse() {
        try {
            serverThread.join();
        } catch (InterruptedException e) {
            test.log(Status.ERROR, "Failed to retrieve response from server with error: " + e.getMessage());
        }
    }

    protected void killDriver() {
        if (webDriver != null) {
            webDriver.quit();
            eventWebDriver.quit();
            eventWebDriver.unregister(handler);
            webDriver = null;
        }
    }

    private int getMachineCores(String machineType) {
        int cores = 2;
        switch (machineType) {
            case "D2":
            case "t2.large":
            case "c4.large":
                cores = 2;
                break;
            case "D4s_v3":
                cores = 4;
                break;
        }
        return cores;
    }

    protected void verifyVirtualMachinesInfo() {
        ArrayList<String> vmNames = icService.getVirtualMachinesNames();
        //ArrayList<String> vmNames = new ArrayList<>();
        //vmNames.add("incredibuild_Cl-xRzvC_0");
        //onboardingPage.setMachineTypeInUI("Standard_D2_v3 - 2 Cores, 8GB RAM, $0.188/hour");
        for (String vmName : vmNames) {
            VirtualMachineInfo vmi = icService.getVirtualMachineInformation(vmName);
            Assert.assertTrue(onboardingPage.getMachineTypeInUI().startsWith(vmi.getVmSize()));

            Iterator iterator = onboardingPage.getCustomTags().entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry mapEntry = (Map.Entry) iterator.next();
                String key = (String) mapEntry.getKey();
                String val = (String) mapEntry.getValue();
                Assert.assertTrue(vmi.getTags().containsKey(key), "");
                Assert.assertEquals(vmi.getTags().get(key), val, "");
            }

        }
    }

    private void deactivateCloud() {
        icService.deactivateCloud();
        isOnBoarding = false;
        if (CLOUD.equals(CloudType.AZURE)) {
            SystemActions.sleep(200);
        } else if (CLOUD.equals(CloudType.AWS)) {
            SystemActions.sleep(10);
        }
    }

}
