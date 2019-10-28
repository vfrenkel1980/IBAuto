package frameworkInfra.utils;

import com.aventstack.extentreports.Status;
import frameworkInfra.testbases.TestBase;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import static frameworkInfra.Listeners.SuiteListener.test;

/**
 * Static class that is used in order to perform custom and advanced Appium actions
 */

public class AppiumActions {

    public static void rightClick(WebElement element, WindowsDriver driver) {
        try {
            Actions action = new Actions(driver).contextClick(element);
            action.build().perform();
            test.log(Status.INFO, "Successfully Right clicked on " + element.getText());

        } catch (StaleElementReferenceException e) {
            System.out.println("Element is not attached to the page document "
                    + e.getMessage());
        } catch (NoSuchElementException e) {
            System.out.println("Element " + element + " was not found in DOM "
                    + e.getMessage());
        } catch (Exception e) {
            System.out.println("Element " + element + " was not clickable "
                    + e.getMessage());
        }
    }

    public static void contextMenuIncrediBuildClick(WebElement project, WindowsDriver driver){
        List<WebElement> ibElements = null;
        WebElement goTo = null;
        ibElements = driver.findElementsByName("IncrediBuild");
        ibElements.get(1).click();
        try {
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            goTo = driver.findElementByName("Go To");
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }
        catch (Exception e){
            e.getMessage();
        }
        if (goTo != null){
            AppiumActions.rightClick(project, driver);
            ibElements.get(0).click();
            test.log(Status.INFO, "Successfully clicked on " + ibElements.get(0).getText() );
        }
    }
}
