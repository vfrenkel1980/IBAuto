package webInfra.ibWeb.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import webInfra.ibWeb.pages.LandingPage;

public class LandingPageObject {

    /*-------------------MAPPING-------------------*/
    public static final By DOWNLOAD_BTN = By.xpath("//*[@id=\"download_button\"]");
    public static final By FIRST_NAME_TB = By.xpath("//*[@id=\"name\"]");
    public static final By LAST_NAME_TB = By.xpath("//*[@id=\"lname\"]");
    public static final By EMAIL_TB = By.xpath("//*[@id=\"email\"]");
    public static final By COUNTRY_SELECTION_DDL = By.xpath("//*[@id=\"countriesSelection\"]");
    public static final By COMPANY_TB = By.xpath("//*[@id=\"company\"]");
    public static final By NUMBER_OF_DEVS_DDL = By.xpath("//*[@id=\"devCountSelection\"]");
    public static final By INDUSTRY_DDL = By.xpath("//*[@id=\"industry\"]");
    public static final By PHONE_TB = By.xpath("//*[@id=\"phone\"]");
    public static final By PASSWORD_TB = By.xpath("//*[@id=\"password\"]");
    public static final By PASSWORD_CONFIRMATION_TB = By.xpath("//*[@id=\"password_confirmation\"]");
    public static final By TERMS_CB = By.xpath("//*[@id=\"terms\"]");
    public static final By PRIVACY_CB = By.xpath("//*[@id=\"privacy\"]");
    public static final By MAILING_LIST_CB = By.xpath("//*[@id=\"mailing_list\"]");
    public static final By SUBMIT_BTN = By.xpath("//button[@type='button' and contains(.,'BOOST MY BUILD')]");
    public static final By AWESOME_LBL = By.xpath("//*[contains(text(),'Awesome')]");


    private EventFiringWebDriver eventWebDriver;

    public LandingPageObject(EventFiringWebDriver driver){
        this.eventWebDriver = driver;
    }

    public void register(LandingPage lp){
        eventWebDriver.findElement(DOWNLOAD_BTN).click();
        WebDriverWait wait = new WebDriverWait(eventWebDriver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(FIRST_NAME_TB)).sendKeys(lp.getName());
        eventWebDriver.findElement(LAST_NAME_TB).sendKeys(lp.getLname());
        eventWebDriver.findElement(EMAIL_TB).sendKeys(lp.getEmail());
        eventWebDriver.findElement(COUNTRY_SELECTION_DDL).sendKeys(lp.getCountry());
        eventWebDriver.findElement(COMPANY_TB).sendKeys(lp.getCompany());
        eventWebDriver.findElement(NUMBER_OF_DEVS_DDL).sendKeys(lp.getDevCount());
        eventWebDriver.findElement(INDUSTRY_DDL).sendKeys(lp.getIndustry());
        eventWebDriver.findElement(PHONE_TB).sendKeys(lp.getPhone());
        eventWebDriver.findElement(PASSWORD_TB).clear();
        eventWebDriver.findElement(PASSWORD_TB).sendKeys(lp.getPass());
        eventWebDriver.findElement(PASSWORD_CONFIRMATION_TB).clear();
        eventWebDriver.findElement(PASSWORD_CONFIRMATION_TB).clear();
        eventWebDriver.findElement(PASSWORD_CONFIRMATION_TB).sendKeys(lp.getPass());
        if (lp.isMailing())
            eventWebDriver.findElement(MAILING_LIST_CB).click();
        eventWebDriver.findElement(TERMS_CB).click();
        eventWebDriver.findElement(PRIVACY_CB).click();
        eventWebDriver.findElement(SUBMIT_BTN).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(AWESOME_LBL));
    }
}
