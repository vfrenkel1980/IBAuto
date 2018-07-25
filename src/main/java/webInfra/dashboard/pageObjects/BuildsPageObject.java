package webInfra.dashboard.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public class BuildsPageObject {

    /*-------------------MAPPING-------------------*/
    public static final By BUILDS_TAB = By.xpath("//a[@href=\"#/material/builds\"]");
    public static final By ALL_TAB = By.xpath("//button[contains(text(),'All')]");



    private EventFiringWebDriver eventWebDriver;
    public BuildsPageObject(EventFiringWebDriver driver){
        this.eventWebDriver = driver;
    }

}
