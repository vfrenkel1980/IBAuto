package frameworkInfra.testbases.web.dashboard;

import frameworkInfra.Listeners.SuiteListener;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import webInfra.dashboard.pageObjects.BuildsPageObject;
import webInfra.dashboard.pageObjects.OverviewPageObject;

import java.util.concurrent.TimeUnit;

import static frameworkInfra.Listeners.SuiteListener.extent;
import static frameworkInfra.Listeners.SuiteListener.test;

@Listeners(SuiteListener.class)
public class OverviewTabTestBase extends DashboardTestBase {

    @BeforeClass
    public void beforeClassBuilds(){
        test = extent.createTest("Before Class");
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/main/resources/WebDrivers/chromedriver.exe");
        webDriver = new ChromeDriver();
        eventWebDriver = new EventFiringWebDriver(webDriver);
        eventWebDriver.register(handler);
        eventWebDriver.get("http://localhost:8000/#/");
        eventWebDriver.manage().window().maximize();
        eventWebDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        overviewPageObject = new OverviewPageObject(eventWebDriver);
    }

    @AfterClass
    public void afterClass(){
        if (webDriver != null) {
            webDriver.quit();
            eventWebDriver.quit();
            eventWebDriver.unregister(handler);
        }
        webDriver = null;
        extent.flush();
    }
}
