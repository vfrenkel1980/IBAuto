package cloudInfra.IncrediCloud.pageObjects;

import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AWSRegistrationPageObject extends RegistrationPageObject {





    private static EventFiringWebDriver eventWebDriver;
    private WebDriverWait wait;
    public AWSRegistrationPageObject(EventFiringWebDriver driver) {
        eventWebDriver = driver;
        wait = new WebDriverWait(eventWebDriver, 10);
    }

    public void selectUser(String user){

    }
}
