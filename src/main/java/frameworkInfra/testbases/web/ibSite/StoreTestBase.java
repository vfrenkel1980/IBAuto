package frameworkInfra.testbases.web.ibSite;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import webInfra.ibWeb.pageObjects.*;
import webInfra.ibWeb.pages.SignupPage;
import webInfra.ibWeb.pages.WindowsRegistrationForm;

import java.util.concurrent.TimeUnit;

import static frameworkInfra.Listeners.SuiteListener.extent;
import static frameworkInfra.Listeners.SuiteListener.test;

public class StoreTestBase extends IbWebTestBase{

    protected SignupPageObject signupPageObject;
    protected StorePageObject storePageObject;
    protected CartPageObject cartPageObject;
    protected TranzilaPageObject tranzilaPageObject;
    protected DownloadPageObject downloadPageObject;
    protected SignupPage sp;

    @BeforeClass
    public void setUpEnv() {
        test = extent.createTest("Before Class");
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/main/resources/WebDrivers/chromedriver.exe");
        webDriver = new ChromeDriver();
        eventWebDriver = new EventFiringWebDriver(webDriver);
        eventWebDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        eventWebDriver.register(handler);
        eventWebDriver.get("https://en-site.incredibuild.com/ibonlinestore/signup#/signUp");
        eventWebDriver.manage().window().maximize();
        signupPageObject = new SignupPageObject(eventWebDriver);
        sp = new SignupPage("New", " User", mailAddress3, "111111", password, "France", "", "Com", "Paris",
                mailAddress3, "Tester");
        storePageObject = new StorePageObject(eventWebDriver);
        cartPageObject = new CartPageObject(eventWebDriver);
        tranzilaPageObject = new TranzilaPageObject(eventWebDriver);
        downloadPageObject = new DownloadPageObject(eventWebDriver);
    }
}
