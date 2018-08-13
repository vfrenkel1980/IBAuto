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


    private static EventFiringWebDriver eventWebDriver;
    private WebDriverWait wait;
    public BuildsPageObject(EventFiringWebDriver driver) {
        eventWebDriver = driver;
        wait = new WebDriverWait(eventWebDriver, 10);
    }


    public String getSuccessfulBuildsUI() {
        return eventWebDriver.findElement(By.xpath("//*[@id=\"page-content-container\"]/div[2]/div/div[2]/div[2]/div/div[2]/div/div[1]/div[1]/div/label")).getText();
    }

    public String getFailedBuildsUI() {
        return eventWebDriver.findElement(By.xpath("//div[@id='page-content-container']//div[@class='grid']//div[2]//div[1]//div[1]//label[1]")).getText();
    }
    public String getTotalBuildsUI(){
        return eventWebDriver.findElement(By.xpath("//div[@class='md-layout md-gutter-24']//div[1]//div[1]//div[2]//div[1]//div[1]//div[1]//div[1]")).getText();
    }
    public String getAvgBuildDurationUI() {
        return eventWebDriver.findElement(By.xpath("//div[@class='md-layout md-gutter-24']//div[3]//div[1]//div[2]//div[1]//div[1]//div[1]//div[1]")).getText();
    }

    public String getDistributedTimeUI() {
        return eventWebDriver.findElement(By.xpath("//label[contains(text(),'%')]")).getText().replace("%","");
    }
    public String getTopBuildTimeInitiator(int num) {
        return eventWebDriver.findElement(By.xpath(".//*[@id='highcharts-z4vqdez-6']/svg/g[9]/text["+num+"]/tspan")).getText();
    }
    public String getTopBuildTimeDuration(int num) {
        return eventWebDriver.findElement(By.xpath(".//*[@id='highcharts-z4vqdez-6']/svg/g[6]/g["+num+"]/text/tspan")).getText();
    }

    public void goToBuildsPage() {
        eventWebDriver.findElement(BUILDS_TAB).click();
    }

    public void goToAllTab() {
        waitForLoadingScreen();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'All')]"))).click();
        waitForLoadingScreen();
    }

    public void waitForLoadingScreen(){
        wait.until(ExpectedConditions.invisibilityOfElementLocated(OVERLAY));
    }


}
