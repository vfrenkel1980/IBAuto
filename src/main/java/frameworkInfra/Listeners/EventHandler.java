package frameworkInfra.Listeners;

import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

import static frameworkInfra.Listeners.SuiteListener.test;

public class EventHandler implements WebDriverEventListener {
    @Override
    public void beforeAlertAccept(WebDriver driver) {
        test.log(Status.INFO, "About to accept alert");
    }

    @Override
    public void afterAlertAccept(WebDriver driver) {
        test.log(Status.INFO, "Alert accepted");
    }

    @Override
    public void afterAlertDismiss(WebDriver driver) {
        test.log(Status.INFO, "Alert dismissed");
    }

    @Override
    public void beforeAlertDismiss(WebDriver driver) {
        test.log(Status.INFO, "About to dismiss alert");
    }

    @Override
    public void beforeNavigateTo(String url, WebDriver driver) {
        test.log(Status.INFO, "Navigating to " + url);
    }

    @Override
    public void afterNavigateTo(String url, WebDriver driver) {
        test.log(Status.INFO, url + " opened successfully");
    }

    @Override
    public void beforeNavigateBack(WebDriver driver) {

    }

    @Override
    public void afterNavigateBack(WebDriver driver) {

    }

    @Override
    public void beforeNavigateForward(WebDriver driver) {

    }

    @Override
    public void afterNavigateForward(WebDriver driver) {

    }

    @Override
    public void beforeNavigateRefresh(WebDriver driver) {

    }

    @Override
    public void afterNavigateRefresh(WebDriver driver) {

    }

    @Override
    public void beforeFindBy(By by, WebElement element, WebDriver driver) {
        test.log(Status.INFO, "Searching for element " + element + " using " + by);
    }

    @Override
    public void afterFindBy(By by, WebElement element, WebDriver driver) {
        test.log(Status.INFO, "Found element " + element + " using " + by);
    }

    @Override
    public void beforeClickOn(WebElement element, WebDriver driver) {
        test.log(Status.INFO, "Clicking on " + element);
    }

    @Override
    public void afterClickOn(WebElement element, WebDriver driver) {
        test.log(Status.INFO, "Successfully clicked on " + element);
    }

    @Override
    public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
        if (keysToSend !=null)
            test.log(Status.INFO, "Changing value of " + element + " to " + keysToSend[0].toString());
        else
            test.log(Status.INFO, "Clearing text for " + element);
    }

    @Override
    public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
        if (keysToSend !=null)
            test.log(Status.INFO, "Successfully changed value of " + element + " to " + keysToSend[0].toString());
        else
            test.log(Status.INFO, "Cleared text for " + element);
    }

    @Override
    public void beforeScript(String script, WebDriver driver) {

    }

    @Override
    public void afterScript(String script, WebDriver driver) {

    }

    @Override
    public void onException(Throwable throwable, WebDriver driver) {
        test.log(Status.INFO, "Failed with error ---> " + throwable);
    }
}
