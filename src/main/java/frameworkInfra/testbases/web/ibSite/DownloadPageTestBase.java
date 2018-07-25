package frameworkInfra.testbases.web.ibSite;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import webInfra.ibWeb.pageObjects.DownloadPageObject;

import static frameworkInfra.Listeners.SuiteListener.extent;
import static frameworkInfra.Listeners.SuiteListener.test;

public class DownloadPageTestBase extends IbWebTestBase {

    protected DownloadPageObject downloadPageObject;

    @BeforeClass
    @Parameters({"lang"})
    public void setUpEnv(String lang) {
        test = extent.createTest("Before Class");
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/main/resources/WebDrivers/chromedriver.exe");
        webDriver = new ChromeDriver();
        eventWebDriver = new EventFiringWebDriver(webDriver);
        eventWebDriver.register(handler);
        switch (lang){
            case "en":
                eventWebDriver.get("https://test-store.incredibuild.com/");
                break;
            case "jp":
                eventWebDriver.get("https://qa-jp-store.incredibuild.com/register#/regWindows/");
                break;
        }
        eventWebDriver.manage().window().maximize();
        downloadPageObject = new DownloadPageObject(eventWebDriver);
    }
}
