package cloudInfra.IncrediCloud.pageObjects;

import cloudInfra.IncrediCloud.metadata.Enums.OnboardingType;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AzureRegistrationPageObject extends RegistrationPageObject {
    //MAPPING
    private static final By AZURE_EMAIL_TB = By.xpath("//*[@type='email']");
    private static final By PASSWORD_TB = By.xpath("//*[@type='password']");
    private static final By NEXT_BUTTON = By.xpath("//*[@value='Next']");
    private static final By SIGNIN_BUTTON = By.xpath("//*[@value='Sign in']");
    private static final By ACCEPT_BUTTON = By.xpath("//*[@value='Accept']");
    private static final By LOW_BOARDING_TENANT_ID_INPUT = By.xpath("//input[@placeholder='Tenant ID']");
    private static final By LOW_BOARDING_APPLICATION_ID_INPUT = By.xpath("//input[@placeholder='New Azure AD Application ID']");
    private static final By LOW_BOARDING_CLIENT_SECRET_INPUT = By.xpath("//input[@placeholder='Client Secret']");
    private static final By CONTINUE_BUTTON = By.xpath("//*[contains(text(),'Continue')]");

    private static final String PROD_USER = "automation@incredicloudcs.onmicrosoft.com";
    private static final String PASSWORD = "Raku6621";
    private static final String LOW_BOARDING_TENANT_ID = "bde8b775-ae5e-4043-bd01-ab0b17249045";
    private static final String LOW_BOARDING_APPLICATION_ID = "29b00fa9-9d80-4dec-aa91-a583fcae4c9c";
    private static final String LOW_BOARDING_CLIENT_SECRET = "ZjhiNmZlZDktZmFjYi00MDcxLTk5YTQtMWFmYzcyMzRhZGFm";

    private static EventFiringWebDriver eventWebDriver;
    private WebDriverWait wait;

    public AzureRegistrationPageObject(EventFiringWebDriver driver) {
        eventWebDriver = driver;
        wait = new WebDriverWait(eventWebDriver, 10);
    }

    public void selectUser(String user, OnboardingType onboardingType) {
        wait.until(ExpectedConditions.visibilityOf(eventWebDriver.findElement(AZURE_EMAIL_TB)));
        eventWebDriver.findElement(AZURE_EMAIL_TB).sendKeys(user);
        wait.until(ExpectedConditions.elementToBeClickable(NEXT_BUTTON)).click();
        eventWebDriver.findElement(PASSWORD_TB).sendKeys(PASSWORD);
        wait.until(ExpectedConditions.elementToBeClickable(SIGNIN_BUTTON)).click();
        ((JavascriptExecutor) eventWebDriver).executeScript(
                "arguments[0].scrollIntoView();", eventWebDriver.findElement(ACCEPT_BUTTON));
        wait.until(ExpectedConditions.elementToBeClickable(ACCEPT_BUTTON)).click();

        if (onboardingType.equals(OnboardingType.LOW_ONBOARDING)) {
            wait.until(ExpectedConditions.presenceOfElementLocated(LOW_BOARDING_TENANT_ID_INPUT));
            eventWebDriver.findElement(LOW_BOARDING_TENANT_ID_INPUT)
                    .sendKeys(LOW_BOARDING_TENANT_ID);
            eventWebDriver.findElement(LOW_BOARDING_APPLICATION_ID_INPUT)
                    .sendKeys(LOW_BOARDING_APPLICATION_ID);
            eventWebDriver.findElement(LOW_BOARDING_CLIENT_SECRET_INPUT)
                    .sendKeys(LOW_BOARDING_CLIENT_SECRET);
            wait.until(ExpectedConditions.elementToBeClickable(CONTINUE_BUTTON)).click();
        }
    }
}
