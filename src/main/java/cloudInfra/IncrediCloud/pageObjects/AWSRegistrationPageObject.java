package cloudInfra.IncrediCloud.pageObjects;

import cloudInfra.IncrediCloud.metadata.Enums.OnboardingType;
import org.openqa.selenium.By;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AWSRegistrationPageObject extends RegistrationPageObject {

    private static final By USER_NAME_TB = By.xpath("//*[@placeholder='Access Key']");
    private static final By PASSWORD_TB = By.xpath("//*[@placeholder='Secret']");
    private static final By LOGIN_BTN = By.xpath("//*[contains(text(),'LOGIN')]");
    private static final By LOW_BOARDING_ROLE_ARN_INPUT = By.xpath("//input[@placeholder='Role ARN']");
    private static final By LOW_BOARDING_EXTERNAL_ID_INPUT = By.xpath("//input[@placeholder='External ID']");
    private static final By CONTINUE_BUTTON = By.xpath("//*[contains(text(),'Continue')]");

    private static final String ACCESS_KEY = "AKIA4BHIO2JBKQX4ZCT5";
    private static final String SECRET_KEY = "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";
    private static final String LOW_BOARDING_ROLE_ARN = "arn:aws:iam::827268715074:role/IB_POLICY_ROLE_Low";
    private static final String LOW_BOARDING_EXTERNAL_ID = "0506ea90-6f69-11ea-b788-dd73d2e8a2d8";

    private static EventFiringWebDriver eventWebDriver;
    private WebDriverWait wait;

    public AWSRegistrationPageObject(EventFiringWebDriver driver) {
        //super(cloudConfigurationData );
        eventWebDriver = driver;
        wait = new WebDriverWait(eventWebDriver, 10);
    }

    public void selectUser(String user, OnboardingType onboardingType) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(USER_NAME_TB));
        eventWebDriver.findElement(USER_NAME_TB).sendKeys(ACCESS_KEY);
        eventWebDriver.findElement(PASSWORD_TB).sendKeys(SECRET_KEY);
        eventWebDriver.findElement(LOGIN_BTN).click();
        if (onboardingType.equals(OnboardingType.LOW_ONBOARDING)) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(LOW_BOARDING_ROLE_ARN_INPUT));
            eventWebDriver.findElement(LOW_BOARDING_ROLE_ARN_INPUT)
                    .sendKeys(LOW_BOARDING_ROLE_ARN);
            eventWebDriver.findElement(LOW_BOARDING_EXTERNAL_ID_INPUT)
                    .sendKeys(LOW_BOARDING_EXTERNAL_ID);
            wait.until(ExpectedConditions.elementToBeClickable(CONTINUE_BUTTON)).click();
        }
    }


}
