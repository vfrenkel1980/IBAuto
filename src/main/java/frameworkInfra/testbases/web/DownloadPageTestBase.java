package frameworkInfra.testbases.web;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.BeforeClass;
import webInfra.ibWeb.pageObjects.DownloadPageObject;

import static frameworkInfra.Listeners.SuiteListener.extent;
import static frameworkInfra.Listeners.SuiteListener.test;

public class DownloadPageTestBase extends IbWebTestBase {

    protected DownloadPageObject downloadPageObject;

    @BeforeClass
    public void setUpEnv() {
        test = extent.createTest("Before Class");
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/main/resources/WebDrivers/chromedriver.exe");
        webDriver = new ChromeDriver();
        eventWebDriver = new EventFiringWebDriver(webDriver);
        eventWebDriver.register(handler);
        eventWebDriver.get("https://test-store.incredibuild.com/");
        eventWebDriver.manage().window().maximize();
        downloadPageObject = new DownloadPageObject(eventWebDriver);
    }
}
