package webInfra.dashboard.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OverviewPageObject {

    private static final By TIME_SAVED = By.xpath("//*[@id = \"time_saved_lbl\"]");
    private static final By COST_SAVED = By.xpath("//*[@id = \"cost_saved_lbl\"]");
    private static final By LOCAL_PROCESSING_TIME = By.xpath("//*[@id = \"local_proccessing_time_lbl\"]");
    private static final By REMOTE_PROCESSING_TIME = By.xpath("//*[@id = \"dist_proccessing_time_lbl\"]");


    private static EventFiringWebDriver eventWebDriver;
    private WebDriverWait wait;
    public OverviewPageObject(EventFiringWebDriver driver) {
        eventWebDriver = driver;
        wait = new WebDriverWait(eventWebDriver, 10);
    }

    public String getTimeSaved(){
        return eventWebDriver.findElement(TIME_SAVED).getText();
    }

    public String getCostSaved(){
        String savedCost = eventWebDriver.findElement(COST_SAVED).getText();
        return savedCost.substring(savedCost.indexOf("$") + 1 );
    }

    public String getLocalProcessingTime(){
        return eventWebDriver.findElement(LOCAL_PROCESSING_TIME).getText();
    }

    public String getRemoteProcessingTime(){
        return eventWebDriver.findElement(REMOTE_PROCESSING_TIME).getText();
    }


}
