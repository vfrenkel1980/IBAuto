package cloudInfra.IncrediCloud.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static frameworkInfra.testbases.incrediCloud.ICEngineTestBase.ENV;

public class AzureRegistrationPageObject extends RegistrationPageObject{

    //MAPPING
    private static final By AZURE_EMAIL_TB = By.xpath("//*[@type='email']");
    private static final By PASSWORD_TB = By.xpath("//*[@type='password']");
    private static final By NEXT_BUTTON = By.xpath("//*[@value='Next']");
    private static final By SIGNIN_BUTTON = By.xpath("//*[@value='Sign in']");
    private static final By ACCEPT_BUTTON = By.xpath("//*[@value='Accept']");

    private static EventFiringWebDriver eventWebDriver;
    private WebDriverWait wait;
    public AzureRegistrationPageObject(EventFiringWebDriver driver) {
        eventWebDriver = driver;
        wait = new WebDriverWait(eventWebDriver, 10);
    }

    public void selectUser(String user){
        eventWebDriver.findElement(AZURE_EMAIL_TB).sendKeys(user);
        wait.until(ExpectedConditions.elementToBeClickable(NEXT_BUTTON)).click();
        switch (ENV){
            case "prod":
                eventWebDriver.findElement(PASSWORD_TB).sendKeys("4illumination!");
                break;
            case "uat":
                eventWebDriver.findElement(PASSWORD_TB).sendKeys("4illumination!");
                break;
        }
        wait.until(ExpectedConditions.elementToBeClickable(SIGNIN_BUTTON)).click();
        wait.until(ExpectedConditions.elementToBeClickable(ACCEPT_BUTTON)).click();
    }
}
