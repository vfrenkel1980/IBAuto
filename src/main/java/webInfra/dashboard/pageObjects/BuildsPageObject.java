package webInfra.dashboard.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BuildsPageObject {

    /*-------------------MAPPING-------------------*/
    private static final By OVERVIEW_TAB = By.xpath("//button[contains(@class, 'overview_tab')]");
    private static final By AGENTS_TAB = By.xpath("//button[contains(@class, 'agents_tab')]");
    private static final By BUILDS_TAB = By.xpath("//button[contains(@class, 'builds_tab')]");
    private static final By CUSTOM_TAB = By.xpath("//button[contains(Custom,'custom_tab')]");
    private static final By H12_TAB = By.xpath("//button[contains(@class,'12h_tab')]");
    private static final By H24_TAB = By.xpath("//button[contains(@class,'24h_tab')]");
    private static final By TODAY_TAB = By.xpath("//button[contains(@class,'today_tab')]");
    private static final By ALL_TAB = By.xpath("//button[contains(@class,'all_tab')]");
    private static final By OVERLAY = By.xpath("//*[@id='overlay']");
    private static final By KPI_SUCCEES_BUILDS = By.xpath("//*[@class='successful_builds_lbl']");
    private static final By KPI_FAILED_BUILDS = By.xpath("//*[@class='failed_builds_lbl']");
    private static final By KPI_TOTAL_BUILDS = By.xpath("//*[@class='total_builds']");
    private static final By KPI_AVG_DURATION = By.xpath("//*[@class='avg_build_duration_lbl']");
    private static final By KPI_DISTRIBUTED_TIME = By.xpath("//*[@class='distributed_lbl']");


    private static EventFiringWebDriver eventWebDriver;
    private WebDriverWait wait;
    public BuildsPageObject(EventFiringWebDriver driver) {
        eventWebDriver = driver;
        wait = new WebDriverWait(eventWebDriver, 10);
    }


    public String getSuccessfulBuildsUI() {
        return eventWebDriver.findElement(KPI_SUCCEES_BUILDS).getText();
    }

    public String getFailedBuildsUI() {
        return eventWebDriver.findElement(KPI_FAILED_BUILDS).getText();
    }

    public String getTotalBuildsUI(){
        return eventWebDriver.findElement(KPI_TOTAL_BUILDS).getText();
    }

    public String getAvgBuildDurationUI() {
        return eventWebDriver.findElement(KPI_AVG_DURATION).getText();
    }

    public String getDistributedTimeUI() {
        return eventWebDriver.findElement(KPI_DISTRIBUTED_TIME).getText();
    }

    public String getTopBuildTimeInitiator(int num) {
        return eventWebDriver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div[2]/div/div[4]/div/div[2]/div/div[1]/div/div/*[local-name() = 'svg']/*[local-name() = 'g'][9]/text["+num+"]/tspan")).getText();
    }

    public String getTopBuildTimeDuration(int num) {
        return eventWebDriver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div[2]/div/div[4]/div/div[2]/div/div[1]/div/div/*[local-name() = 'svg']/*[local-name() = 'g'][6]//*[local-name() = 'g']["+num+"]/text/tspan")).getText();
    }

    public void goToBuildsPage() {
        eventWebDriver.findElement(BUILDS_TAB).click();
    }

    public void goToTab(String t) {
        waitForLoadingScreen();
            By tab = null;
            switch (t) {
                case "All":
                    tab = ALL_TAB;
                    break;
                case "Today":
                    tab = TODAY_TAB;
                    break;
                case "H12":
                    tab = H12_TAB;
                    break;
                case "H24":
                    tab = H24_TAB;
                    break;
                default:
                    return;
            }
        wait.until(ExpectedConditions.elementToBeClickable(tab)).click();
        waitForLoadingScreen();
    }

    public void waitForLoadingScreen(){
        wait.until(ExpectedConditions.invisibilityOfElementLocated(OVERLAY));
    }

}
