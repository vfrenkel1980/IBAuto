package frameworkInfra.testbases.incrediCloud;

import cloudInfra.IncrediCloud.Pages.OnboardingPage;
import cloudInfra.IncrediCloud.pageObjects.AWSRegistrationPageObject;
import cloudInfra.IncrediCloud.pageObjects.AzureRegistrationPageObject;
import cloudInfra.IncrediCloud.pageObjects.OnboardingPageObject;
import com.aventstack.extentreports.Status;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static frameworkInfra.Listeners.SuiteListener.extent;
import static frameworkInfra.Listeners.SuiteListener.test;

public class CloudUpdateTestBase extends ICEngineTestBase {

    protected OnboardingPage updateIncreasePoolSize;
    protected OnboardingPage updateDecreasePoolSize;
    protected OnboardingPage updatePorts;
    final public int GRID_CORES_AFTER_INCREASE = GRID_CORES + 4;
    final public int GRID_CORES_AFTER_DECREASE = GRID_CORES - 4;

    @BeforeClass
    public void beforeClass() {
        switch (CLOUD) {
            case AZURE:
                onboardingPage = new OnboardingPage("North Europe", "Test", "User", "Test@user.com", "Com", TYPE, TIMEOUT, CORES_LIMIT, POOL_SIZE,
                        COORD_PORT, VM_PORT);
                updateIncreasePoolSize = new OnboardingPage("North Europe", "Test", "User", "Test@user.com", "Com", TYPE, TIMEOUT, CORES_LIMIT, POOL_SIZE + 2,
                        COORD_PORT, VM_PORT);
                updateDecreasePoolSize = new OnboardingPage("North Europe", "Test", "User", "Test@user.com", "Com", TYPE, TIMEOUT, CORES_LIMIT, POOL_SIZE - 2,
                        COORD_PORT, VM_PORT);
                updatePorts = new OnboardingPage("North Europe", "Test", "User", "Test@user.com", "Com", TYPE, TIMEOUT, CORES_LIMIT, POOL_SIZE - 2,
                        31100, 31103);
                break;
            case AWS:
                onboardingPage = new OnboardingPage("EU (Ireland)", "Test", "User", "Test@user.com", "Com", TYPE, TIMEOUT, CORES_LIMIT, POOL_SIZE,
                        COORD_PORT, VM_PORT);
                updateIncreasePoolSize = new OnboardingPage("EU (Ireland)", "Test", "User", "Test@user.com", "Com", TYPE, TIMEOUT, CORES_LIMIT, POOL_SIZE + 2,
                        COORD_PORT, VM_PORT);
                updateDecreasePoolSize = new OnboardingPage("EU (Ireland)", "Test", "User", "Test@user.com", "Com", TYPE, TIMEOUT, CORES_LIMIT, POOL_SIZE - 2,
                        COORD_PORT, VM_PORT);
                updatePorts = new OnboardingPage("EU (Ireland)", "Test", "User", "Test@user.com", "Com", TYPE, TIMEOUT, CORES_LIMIT, POOL_SIZE - 2,
                        31100, 31103);
                break;
        }
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        testName = getTestName(method);
        test = extent.createTest(testName);
        test.assignCategory("IC update cloud policy");
        test.log(Status.INFO, method.getName() + " test started");
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
            case "dev":
                eventWebDriver.get("https://incredicloud-onboarding.azurewebsites.net/?coord_id=" + COORDID + "&redirect_uri=http://127.0.0.1:" + PORT + "/cloudauthentication");
                break;
        }
        eventWebDriver.manage().window().maximize();
        onboardingPageObject = new OnboardingPageObject(eventWebDriver);

        switch (CLOUD) {
            case AZURE:
                cloudRegistrationPageObject = new AzureRegistrationPageObject(eventWebDriver);
                break;
            case AWS:
                cloudRegistrationPageObject = new AWSRegistrationPageObject(eventWebDriver);
                break;
        }

    }

    @AfterMethod
    public void afterMethod(Method method) {
        killDriver();
        extent.flush();
    }

    @AfterClass
    public void afterClass() {
        test = extent.createTest("AFTER CLASS");
        webServer.closeWebServer();
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\Coordinator", StaticDataProvider.RegistryKeys.INCREDICLOUDSECRET, "");
        winService.restartService(StaticDataProvider.WindowsServices.COORD_SERVICE);
        icService.deactivateCloud();
    }
}
