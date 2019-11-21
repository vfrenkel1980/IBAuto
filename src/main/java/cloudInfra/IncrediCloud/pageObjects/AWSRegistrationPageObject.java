package cloudInfra.IncrediCloud.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AWSRegistrationPageObject extends RegistrationPageObject {


    private static final By USER_NAME_TB = By.xpath("//*[@placeholder='Access Key']");
    private static final By PASSWORD_TB = By.xpath("//*[@placeholder='Secret Key']");
    private static final By LOGIN_BTN = By.xpath("//*[contains(text(),'LOGIN')]");

    private static EventFiringWebDriver eventWebDriver;
    private WebDriverWait wait;

    public AWSRegistrationPageObject(EventFiringWebDriver driver) {
        eventWebDriver = driver;
        wait = new WebDriverWait(eventWebDriver, 10);
    }

    public void selectUser(String user){
        eventWebDriver.findElement(USER_NAME_TB).sendKeys("AKIA4BHIO2JBDEF24LE6");
        eventWebDriver.findElement(PASSWORD_TB).sendKeys("GNLO7lNZ2Q5ADgHXZscHEAflslyIjRAK4IYEuBMG");
        eventWebDriver.findElement(LOGIN_BTN).click();
    }


}
