package webInfra.ibWeb.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import webInfra.ibWeb.pages.SignupPage;

public class SignupPageObject {

    public static final By FIRST_NAME_TB = By.xpath("//*[@id=\"name\"]");
    public static final By LAST_NAME_TB = By.xpath("//*[@id=\"lname\"]");
    public static final By EMAIL_TB = By.xpath("//*[@id=\"email\"]");
    public static final By PASSWORD_TB = By.xpath("//*[@id=\"password\"]");
    public static final By PASSWORD_CONFIRMATION_TB = By.xpath("//*[@id=\"password_confirmation\"]");
    public static final By PHONE_TB = By.xpath("//*[@id=\"phone\"]");
    public static final By TERMS_CB = By.xpath("//*[@id=\"terms\"]");
    public static final By SUBMIT_FIRST_FORM_BTN = By.xpath("//*[@id=\"first-step-submit\"]");


    //Second stage
    public static final By COUNTRY_SELECTION_DDL = By.xpath("//*[@id=\"countriesSelection\"]");
    public static final By COMPANY_TB = By.xpath("//*[@id=\"company\"]");
    public static final By CITY_TB = By.xpath("//*[@id=\"city\"]");
    public static final By LICENSEE_MAIL_TB = By.xpath("//*[@id=\"licensee_mail\"]");
    public static final By LICENSEE_NAME_TB = By.xpath("//*[@id=\"licensee_name\"]");
    public static final By PRIVACY_AGREEMENT_CB = By.xpath("//*[@id=\"privacy\"]");
    public static final By FREE_DEV_SUBMIT_BTN = By.xpath("//*[@id=\"free-dev-submit\"]");
    private static final By ENTER_STORE = By.xpath("//a[contains(text(),'BUY NOW')]");

    //Login page
    public static final By LOGIN_BTN = By.xpath("//*[@value=\"LOG IN\"]");


    private EventFiringWebDriver eventWebDriver;

    public SignupPageObject(EventFiringWebDriver eventWebDriver) {
        this.eventWebDriver = eventWebDriver;
    }

    public void signUpNewUser(SignupPage sp){
        WebDriverWait wait = new WebDriverWait(eventWebDriver, 60);
        wait.until(ExpectedConditions.visibilityOfElementLocated(FIRST_NAME_TB)).sendKeys(sp.getName());
        eventWebDriver.findElement(FIRST_NAME_TB).sendKeys(sp.getLname());
        eventWebDriver.findElement(LAST_NAME_TB).sendKeys(sp.getLname());
        eventWebDriver.findElement(EMAIL_TB).sendKeys(sp.getEmail());
        eventWebDriver.findElement(PASSWORD_TB).sendKeys(sp.getPass());
        eventWebDriver.findElement(PASSWORD_CONFIRMATION_TB).sendKeys(sp.getPass());
        eventWebDriver.findElement(PHONE_TB).sendKeys(sp.getPhone());
        eventWebDriver.findElement(TERMS_CB).click();
        eventWebDriver.findElement(SUBMIT_FIRST_FORM_BTN).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(COUNTRY_SELECTION_DDL)).sendKeys(sp.getCountry());
        eventWebDriver.findElement(COMPANY_TB).sendKeys(sp.getCompany());
        eventWebDriver.findElement(CITY_TB).sendKeys(sp.getCity());
        eventWebDriver.findElement(LICENSEE_MAIL_TB).sendKeys(sp.getLicenseeMail());
        eventWebDriver.findElement(LICENSEE_NAME_TB).sendKeys(sp.getLicenseeName());
        eventWebDriver.findElement(PRIVACY_AGREEMENT_CB).click();
        eventWebDriver.findElement(FREE_DEV_SUBMIT_BTN).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(ENTER_STORE));
    }

    public void logIn(String userName, String pass){
        WebDriverWait wait = new WebDriverWait(eventWebDriver, 60);
        eventWebDriver.navigate().to("https://test.incredibuild.com/ibonlinestore/login");
        eventWebDriver.findElement(EMAIL_TB).sendKeys(userName);
        eventWebDriver.findElement(PASSWORD_TB).sendKeys(pass);
        eventWebDriver.findElement(LOGIN_BTN).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(ENTER_STORE));
    }
}
