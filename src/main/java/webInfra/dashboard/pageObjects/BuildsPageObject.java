package webInfra.dashboard.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BuildsPageObject {

    /*-------------------MAPPING-------------------*/
    public static final By OVERVIEW_TAB = By.xpath("//a[@href=\"#/material/overview\"]");
    public static final By AGENTS_TAB = By.xpath("//a[@href=\"#/material/agents\"]");
    public static final By BUILDS_TAB = By.xpath("//a[@href=\"#/material/builds\"]");
    public static final By CUSTOM_TAB = By.xpath("//button[contains(text(),'Custom')]");
    public static final By H12_TAB = By.xpath("//button[contains(text(),'12H')]");
    public static final By H24_TAB = By.xpath("//button[contains(text(),'24H')]");
    public static final By ALL_TAB = By.xpath("//button[contains(text(),'All')]");
    public static final By OVERLAY = By.xpath("//*[@id='overlay']");

    /*----BUILDS----*/


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

    public int convertStringTimeToEpoch(String time){
        int epoch = 0;

        if (time.contains("h")){
            String hours = time.substring(0,time.indexOf("h"));
            String minutes = time.substring(time.indexOf(" "), time.indexOf("m")).replaceAll(" ","");
            epoch = Integer.parseInt(hours) * 3600 + Integer.parseInt(minutes)*60;
        }
        else{
            String minutes = time.substring(0, time.indexOf("m"));
            String seconds = time.substring(time.indexOf(" "), time.indexOf("s")).replaceAll(" ","");
            epoch = Integer.parseInt(minutes) * 60 + Integer.parseInt(seconds);
        }
        return epoch;
    }

    public void waitForLoadingScreen(){
        wait.until(ExpectedConditions.invisibilityOfElementLocated(OVERLAY));
    }


}
