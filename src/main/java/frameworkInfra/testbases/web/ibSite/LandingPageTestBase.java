package frameworkInfra.testbases.web.ibSite;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import webInfra.ibWeb.pageObjects.LandingPageObject;

import static frameworkInfra.Listeners.SuiteListener.extent;
import static frameworkInfra.Listeners.SuiteListener.test;

public class LandingPageTestBase extends IbWebTestBase {

    protected LandingPageObject landingPageObject;

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
                eventWebDriver.get("https://test-store.incredibuild.com/incredibuild-codeproject#/");
                break;
            case "jp":
                eventWebDriver.get("https://qa-jp-store.incredibuild.com/incredibuild-codeproject#/");
                break;
        }
        eventWebDriver.manage().window().maximize();
        landingPageObject = new LandingPageObject(eventWebDriver);
    }
}
