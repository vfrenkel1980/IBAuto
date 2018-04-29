package webInfra.ibWeb.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DownloadPage{

    /*-------------------MAPPING-------------------*/
    //First stage

    public static final By DOWNLOAD_BTN = By.xpath("//*[@id=\"myDIV\"]/ul/li[7]/a");
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
    public static final By STATE_DDL = By.xpath("//*[@id=\"statesSelection\"]");
    public static final By CITY_TB = By.xpath("//*[@id=\"city\"]");
    public static final By HOW_DID_YOU_HEAR_DDL = By.xpath("//*[@id=\"howDidYouSelection\"]");
    public static final By JOB_TITLE_TB = By.xpath("//*[@id=\"jobtitle\"]");

    public static final By CPP_CB = By.xpath("//*[@id=\"C++\"]");
    public static final By CSSHORT_CB = By.xpath("//*[@id=\"C#short\"]");
    public static final By CSLONG_CB = By.xpath("//*[@id=\"C#long compilation time\"]");
    public static final By JAVA_CB = By.xpath("//*[@id=\"Java Build\"]");
    public static final By MAILING_LIST_CB = By.xpath("//*[@id=\"mailinglist\"]");
    public static final By FREE_DEV_SUBMIT_BTN = By.xpath("//*[@id=\"free-dev-submit\"]");
    public static final By PREVIOUS_BTN = By.xpath("//*[@value=\"prev\"]");

    private EventFiringWebDriver eventWebDriver;

    public DownloadPage(EventFiringWebDriver driver){
        this.eventWebDriver = driver;
    }

    public void createNewFreeDevWinAccount(String name, String lname, String email, String pass, String phone, String country, String company, String state, String city, String how, String job,
                                           boolean cpp, boolean csshort, boolean cslong, boolean java, boolean mailing){
        eventWebDriver.findElement(DOWNLOAD_BTN).click();
        WebDriverWait wait = new WebDriverWait(eventWebDriver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(FIRST_NAME_TB)).sendKeys(name);
        eventWebDriver.findElement(LAST_NAME_TB).sendKeys(lname);
        eventWebDriver.findElement(EMAIL_TB).sendKeys(email);
        eventWebDriver.findElement(PASSWORD_TB).sendKeys(pass);
        eventWebDriver.findElement(PASSWORD_CONFIRMATION_TB).sendKeys(pass);
        eventWebDriver.findElement(PHONE_TB).sendKeys(phone);
        eventWebDriver.findElement(TERMS_CB).click();
        eventWebDriver.findElement(SUBMIT_FIRST_FORM_BTN).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(COUNTRY_SELECTION_DDL)).sendKeys(country);
        eventWebDriver.findElement(COMPANY_TB).sendKeys(company);
        if (country.equals("united states"))
            eventWebDriver.findElement(STATE_DDL).sendKeys(state);
        eventWebDriver.findElement(CITY_TB).sendKeys(city);
        eventWebDriver.findElement(HOW_DID_YOU_HEAR_DDL).sendKeys(how);
        eventWebDriver.findElement(JOB_TITLE_TB).sendKeys(job);
        if (cpp)
            eventWebDriver.findElement(CPP_CB).click();
        if (csshort)
            eventWebDriver.findElement(CSSHORT_CB).click();
        if (cslong)
            eventWebDriver.findElement(CSLONG_CB).click();
        if (java)
            eventWebDriver.findElement(JAVA_CB).click();
        if (mailing)
            eventWebDriver.findElement(MAILING_LIST_CB).click();
        eventWebDriver.findElement(FREE_DEV_SUBMIT_BTN).click();
    }


}
