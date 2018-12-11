package frameworkInfra.testbases.web.ibSite;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import webInfra.ibWeb.pageObjects.DownloadPageObject;

import java.util.concurrent.TimeUnit;

import static frameworkInfra.Listeners.SuiteListener.extent;
import static frameworkInfra.Listeners.SuiteListener.test;

public class DownloadPageTestBase extends IbWebTestBase {

    protected DownloadPageObject downloadPageObject;

    @BeforeClass
    public void setUpEnv(String lang) {
        test = extent.createTest("Before Class");
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/main/resources/WebDrivers/chromedriver.exe");
        webDriver = new ChromeDriver();
        eventWebDriver = new EventFiringWebDriver(webDriver);
        eventWebDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        eventWebDriver.register(handler);
        eventWebDriver.get("https://test.incredibuild.com/");
        eventWebDriver.manage().window().maximize();
        downloadPageObject = new DownloadPageObject(eventWebDriver);
    }
}
