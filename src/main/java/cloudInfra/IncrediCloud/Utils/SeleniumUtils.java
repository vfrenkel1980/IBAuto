package cloudInfra.IncrediCloud.Utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SeleniumUtils {

    /***
     *
     * @param webDriver
     * @param webElement
     */
    public static void scrollToWebElement(WebDriver webDriver, WebElement webElement) {
        ((JavascriptExecutor) webDriver).executeScript(
                "arguments[0].scrollIntoView();", webElement);
    }

    /***
     *
     * @param webDriver
     * @param horizontal default value is 0
     * @param vertical default value is 0
     */
    public static void scrollByPixel(WebDriver webDriver, int horizontal, int vertical) {
        // "window.scrollBy(1500,1000)"
        final String SCRIPT_FORMAT = "window.scrollBy(%d,%d)";
        String script = String.format(SCRIPT_FORMAT, horizontal, vertical);

        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript(script);
    }

    /***
     * BETTER TO AVOID USING IT
     * @param webDriver
     * @param zoomRangeInPercentage
     */
    public static void zoomInOut(WebDriver webDriver, int zoomRangeInPercentage) {
        //"document.body.style.zoom='95%'"
        final String SCRIPT_FORMAT = "document.body.style.zoom='%d%'";
        String script = String.format(SCRIPT_FORMAT, zoomRangeInPercentage);

        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript(script);
    }
}
