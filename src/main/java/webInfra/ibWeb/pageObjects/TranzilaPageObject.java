package webInfra.ibWeb.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Month;

public class TranzilaPageObject {

    private static final By COMPANY_TB = By.xpath("//*[@id=\"company\"]");
    private static final By NAME_TB = By.xpath("//*[@id=\"contact\"]");
    private static final By CARD_NUMBER_TB = By.xpath("//*[@id=\"ccno\"]");
    private static final By CARD_MONTH_TB = By.xpath("//select[@id=\"expmonth\"]");
    private static final By CARD_YEAR_DDL = By.xpath("//select[@id=\"expyear\"]");
    private static final By CARD_CVV_DDL = By.xpath("//*[@id=\"mycvv\"]");
    private static final By PAY_TRANZILA_BTN = By.xpath("//*[@id=\"send\"]");
    private static final By THANK_YOU_MESSAGE = By.xpath("//*[contains(text(),'Thank You for Your Order!')]");


    private EventFiringWebDriver eventWebDriver;
    private WebDriverWait wait;

    public TranzilaPageObject(EventFiringWebDriver eventWebDriver) {
        this.eventWebDriver = eventWebDriver;
        wait = new WebDriverWait(eventWebDriver, 60);
    }

    public void enterName(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(NAME_TB)).sendKeys("Moran Cohen");
    }

    public void enterCompany(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(COMPANY_TB)).sendKeys("xoreax");
    }

    public void enterCardNumber(){
        eventWebDriver.findElement(CARD_NUMBER_TB).sendKeys("5326100353857075");
    }

    public void selectCardExpDate(){
        Select month = new Select(eventWebDriver.findElement(CARD_MONTH_TB));
        month.selectByVisibleText("05");
        Select year = new Select(eventWebDriver.findElement(CARD_YEAR_DDL));
        year.selectByVisibleText("2019");
    }

    public void enterSecurityCode(){
        eventWebDriver.findElement(CARD_CVV_DDL).sendKeys("645");
    }

    public boolean clickPayButtonAndVerifyOrderComplete(){
        eventWebDriver.findElement(PAY_TRANZILA_BTN).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(THANK_YOU_MESSAGE));
        return eventWebDriver.findElement(THANK_YOU_MESSAGE).isDisplayed();
    }
}
