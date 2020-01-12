package frameworkInfra.utils;

import com.aventstack.extentreports.Status;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.Keys;
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
    public static void click(WebElement element, WindowsDriver driver) {
        try {
            Actions action = new Actions(driver).click(element);
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

    public static void contextMenuIncrediBuildClick(WebElement project, WindowsDriver driver) {
        List<WebElement> ibElements;
        WebElement goTo = null;
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

//        ibElements = driver.findElementsByXPath("//*[contains(@Name,\"IncrediBuild\")]");
        ibElements = driver.findElementsByName("IncrediBuild");
        if (ibElements.size() == 0) {
            ibElements = driver.findElementsByName("Incredibuild");
        }
        ibElements.get(1).click();

        try {
            goTo = driver.findElementByName("Go To");
        } catch (Exception e) {
            e.getMessage();
        }
        if (goTo != null) {
            AppiumActions.rightClick(project, driver);
            ibElements.get(0).click();
            test.log(Status.INFO, "Successfully clicked on " + ibElements.get(0).getText());
        }
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public static void solutionContextMenuIncrediBuildClickForVCVersion16(WindowsDriver driver) {
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        Actions action = new Actions(driver);
        action.sendKeys(Keys.ARROW_DOWN);
        action.sendKeys(Keys.ARROW_DOWN);
        action.sendKeys(Keys.ARROW_DOWN);
        action.sendKeys(Keys.ARROW_DOWN);
        action.sendKeys(Keys.ARROW_DOWN);
        action.sendKeys(Keys.ARROW_DOWN);
        action.sendKeys(Keys.ARROW_DOWN);
        action.sendKeys(Keys.ARROW_DOWN);
        action.sendKeys(Keys.ARROW_DOWN);
        action.sendKeys(Keys.ARROW_RIGHT);
        action.build().perform();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public static void projectContextMenuIncrediBuildClickForVCVersion16(WindowsDriver driver) {
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        Actions action = new Actions(driver);
        action.sendKeys(Keys.ARROW_DOWN);
        action.sendKeys(Keys.ARROW_DOWN);
        action.sendKeys(Keys.ARROW_DOWN);
        action.sendKeys(Keys.ARROW_DOWN);
        action.sendKeys(Keys.ARROW_DOWN);
        action.sendKeys(Keys.ARROW_DOWN);
        action.sendKeys(Keys.ARROW_DOWN);
        action.sendKeys(Keys.ARROW_DOWN);
        action.sendKeys(Keys.ARROW_RIGHT);
        action.build().perform();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public static void solutionActionClickForVCVersion16(String actionType,WindowsDriver driver) throws Exception {
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        Actions action = new Actions(driver);
        if (actionType.equals(StaticDataProvider.VsActions.BUILD_SOLUTION)) {

        }  else if (actionType.equals(StaticDataProvider.VsActions.REBUILD_SOLUTION)) {
            action.sendKeys(Keys.ARROW_DOWN);
        }
        else if (actionType.equals(StaticDataProvider.VsActions.CLEAN_SOLUTION)) {
            action.sendKeys(Keys.ARROW_DOWN);
            action.sendKeys(Keys.ARROW_DOWN);
        } else
            throw new Exception(String.format("Action '%s'is not supported yet in method 'projectActionClickForVCVersion16!'", action));
        action.sendKeys(Keys.ENTER);
        action.build().perform();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public static void projectActionClickForVCVersion16(String actionType, WindowsDriver driver) throws Exception {
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        Actions action = new Actions(driver);
        if (actionType.equals(StaticDataProvider.VsActions.BUILD_PROJECT)) {

        }  else if (actionType.equals(StaticDataProvider.VsActions.REBUILD_PROJECT)) {
            action.sendKeys(Keys.ARROW_DOWN);
        }
        else if (actionType.equals(StaticDataProvider.VsActions.CLEAN_PROJECT)) {
            action.sendKeys(Keys.ARROW_DOWN);
            action.sendKeys(Keys.ARROW_DOWN);
        } else
            throw new Exception(String.format("Action '%s'is not supported yet in method 'projectActionClickForVCVersion16!'", action));
        action.sendKeys(Keys.ENTER);
        action.build().perform();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

}
