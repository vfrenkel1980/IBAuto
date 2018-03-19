package frameworkInfra.utils;

import frameworkInfra.testbases.VSTestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.NoSuchElementException;

public class AppiumActions extends VSTestBase{

    public static void rightClick(WebElement element) {
        try {
            Actions action = new Actions(driver).contextClick(element);
            action.build().perform();

            System.out.println("Successfully Right clicked on the element");
        } catch (StaleElementReferenceException e) {
            System.out.println("Element is not attached to the page document "
                    + e.getStackTrace());
        } catch (NoSuchElementException e) {
            System.out.println("Element " + element + " was not found in DOM "
                    + e.getStackTrace());
        } catch (Exception e) {
            System.out.println("Element " + element + " was not clickable "
                    + e.getStackTrace());
        }
    }

    public static void contextMenuIncrediBuildClick(WebElement project){
        List<WebElement> ibElements = null;
        WebElement goTo = null;
        ibElements =driver.findElementsByName("Incredibuild");
        if (ibElements.size() == 0) {
            ibElements = driver.findElementsByName("IncrediBuild");
        }
        ibElements.get(1).click();

        try {
            goTo = driver.findElementByName("Go To");
        }
        catch (Exception e){
            e.getMessage();
        }
        if (goTo != null){
            AppiumActions.rightClick(project);
            ibElements.get(0).click();
        }
    }
}
