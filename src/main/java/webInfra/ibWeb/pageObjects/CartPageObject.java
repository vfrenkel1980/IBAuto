package webInfra.ibWeb.pageObjects;

import frameworkInfra.utils.SystemActions;
import org.openqa.selenium.By;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPageObject {

    private static final String CART_ADD_SOLUTIONS = "//*[contains(text(), \"%s\")]/../../../descendant::*[@src=\"/ibonlinestore/images/solutionPlus.png\"]";
    private static final String CART_REDUCE_SOLUTIONS = "//*[contains(text(), \"%s\")]/../../../descendant::*[@src=\"/ibonlinestore/images/solutionMinus.png\"]";

    private static final String CART_DELETE_SOLUTION = "//*[@class='cartLineItemProductName' and contains(text(), '%s')]/ancestor::*[@class='cartLineItemLine']/parent::*/descendant::*[@class='removeLineItemX']";

    private static final By EULA_CB = By.xpath("//*[@id=\"confirmTerms\"]/following-sibling::*[@class=\"confirmCheckmark\"]");
    private static final By PRIVACY_CB = By.xpath("//*[@id=\"confirmPrivacy\"]/following-sibling::*[@class=\"confirmCheckmark\"]");
    private static final By CART_TOTAL_PRICE = By.xpath("//*[@class=\"cartTotalPrice\"]");
    private static final By CONTINUE_TO_PAYMENT_BTN = By.xpath("//button[contains(text(),'continue to secure payment')]");
    private static final By LOADING_SPINNER = By.xpath("//*[@class=\"white-overlay\"]");

    //*[contains(text(), %s)]/parent::*/descendant::/*[@id=\"confirmTerms\"]

    private EventFiringWebDriver eventWebDriver;
    private WebDriverWait wait;

    public CartPageObject(EventFiringWebDriver eventWebDriver) {
        this.eventWebDriver = eventWebDriver;
        wait = new WebDriverWait(eventWebDriver, 60);
    }


    public boolean verifyCartPageLoaded(){
        SystemActions.sleep(10);
        return eventWebDriver.findElement(CONTINUE_TO_PAYMENT_BTN).isDisplayed();
    }

    public void addSolutionByNameCartPage(String solName){
        eventWebDriver.findElement(By.xpath(String.format(CART_ADD_SOLUTIONS, solName))).click();
        waitForLoadingScreenCartPage();
    }

    public void reduceSolutionByNameCartPage(String solName){
        eventWebDriver.findElement(By.xpath(String.format(CART_REDUCE_SOLUTIONS, solName))).click();
        waitForLoadingScreenCartPage();
    }

    private void waitForLoadingScreenCartPage(){
        SystemActions.sleep(1);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(LOADING_SPINNER));
    }

    public void removeItemFromCart(String solName){
        eventWebDriver.findElement(By.xpath(String.format(CART_DELETE_SOLUTION, solName))).click();
        waitForLoadingScreenCartPage();
    }

    public String getTotalSum(){
        waitForLoadingScreenCartPage();
        return eventWebDriver.findElement(CART_TOTAL_PRICE).getText();
    }

    public void clickEulaCb(){
        eventWebDriver.findElement(EULA_CB).click();
    }

    public void clickPrivacyCb(){
        eventWebDriver.findElement(PRIVACY_CB).click();
    }

    public void clickContinueToPayment(){
        eventWebDriver.findElement(CONTINUE_TO_PAYMENT_BTN).click();
    }

    public void navigateBack(){
        eventWebDriver.navigate().back();
    }
}
