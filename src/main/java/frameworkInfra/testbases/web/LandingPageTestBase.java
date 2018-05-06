package frameworkInfra.testbases.web;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.BeforeClass;
import webInfra.ibWeb.pageObjects.DownloadPageObject;
import webInfra.ibWeb.pageObjects.LandingPageObject;

import static frameworkInfra.Listeners.SuiteListener.extent;
import static frameworkInfra.Listeners.SuiteListener.test;

public class LandingPageTestBase extends IbWebTestBase {

    protected LandingPageObject landingPageObject;

    @BeforeClass
    public void setUpEnv() {
        test = extent.createTest("Before Class");
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/main/resources/WebDrivers/chromedriver.exe");
        webDriver = new ChromeDriver();
        eventWebDriver = new EventFiringWebDriver(webDriver);
        eventWebDriver.register(handler);
        eventWebDriver.get("https://en-store.incredibuild.com/incredibuild-codeproject#/");
        eventWebDriver.manage().window().maximize();
        landingPageObject = new LandingPageObject(eventWebDriver);
    }
}
