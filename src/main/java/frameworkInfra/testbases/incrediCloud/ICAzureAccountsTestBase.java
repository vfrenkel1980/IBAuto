package frameworkInfra.testbases.incrediCloud;

import cloudInfra.IncrediCloud.Pages.OnboardingPage;
import cloudInfra.IncrediCloud.pageObjects.AzureRegistrationPageObject;
import cloudInfra.IncrediCloud.pageObjects.OnboardingPageObject;
import com.aventstack.extentreports.Status;
import frameworkInfra.Listeners.SuiteListener;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.*;

import java.lang.reflect.Method;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static frameworkInfra.Listeners.SuiteListener.extent;
import static frameworkInfra.Listeners.SuiteListener.test;

@Listeners(SuiteListener.class)
public class ICAzureAccountsTestBase extends ICEngineTestBase {

    protected OnboardingPage failedOnboardingPage;
    protected OnboardingPage preUpdateOnboardingPage;
    protected OnboardingPage postUpdateOnboardingPage;

    @BeforeClass
    public void beforeClass(){
        test = extent.createTest("Before Class");
        failedOnboardingPage = new OnboardingPage("East Asia", "Test", "User", "Test@user.com", "Com", TYPE, TIMEOUT, CORES_LIMIT, 10,
                COORD_PORT, VM_PORT);
        preUpdateOnboardingPage = new OnboardingPage("East Asia", "Test", "User", "Test@user.com", "Com", TYPE, TIMEOUT, CORES_LIMIT, 2,
                COORD_PORT, VM_PORT);
        postUpdateOnboardingPage = new OnboardingPage("East Asia", "Test", "User", "Test@user.com", "Com", TYPE, TIMEOUT, CORES_LIMIT, 4,
                COORD_PORT, VM_PORT);
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        testName = getTestName(method);
        test = extent.createTest(testName);
        test.assignCategory("IC Azure Accounts test");
        test.log(Status.INFO, method.getName() + " test started");
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
        }
        eventWebDriver.manage().window().maximize();
        onboardingPageObject = new OnboardingPageObject(eventWebDriver);
        cloudRegistrationPageObject = new AzureRegistrationPageObject(eventWebDriver);
    }

    @AfterMethod
    public void afterMethod(Method method){
        killDriver();
        extent.flush();
    }

    @AfterClass
    public void afterClass(){
        test = extent.createTest("AFTER CLASS");
        webServer.closeWebServer();
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\Coordinator", StaticDataProvider.RegistryKeys.INCREDICLOUDSECRET, "");
        winService.restartService(StaticDataProvider.WindowsServices.COORD_SERVICE);
        icService.deactivateCloud();
    }
}
