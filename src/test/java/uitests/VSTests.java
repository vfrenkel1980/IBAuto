package uitests;

import frameworkInfra.testbases.TestBase;
import frameworkInfra.testbases.WindowsTestBase;
import frameworkInfra.utils.AppiumActions;
import frameworkInfra.utils.Parser;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider;
import ibInfra.ibService.IbService;
import ibInfra.vsui.VSUIService;
import ibInfra.windowscl.WindowsService;
import io.appium.java_client.windows.WindowsDriver;
import frameworkInfra.testbases.VSTestBase;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static frameworkInfra.testbases.VSTestBase.driver;
import static frameworkInfra.utils.StaticDataProvider.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class VSTests /*extends TestBase*/ {

    IbService ibService = new IbService();
    @Test
    public void test() {
        IbService run = new IbService();
        WindowsDriver driver = null;
        VSUIService runVs = new VSUIService();
        //run.installIB("Latest");
        String projectName = "ConsoleApplication1";
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("app", "C:\\Program Files (x86)\\Microsoft Visual Studio\\2017\\Professional\\Common7\\IDE\\devenv.exe");
            driver = new WindowsDriver(new URL("http://127.0.0.1:4723"), capabilities);
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            //runVs.openProject("C:\\QA\\Simulation\\Projects\\ConsoleApplication1\\ConsoleApplication1.sln");
            //runVs.performIbActionFromMenu("Rebuild Solution");
            driver.findElementByName("File").click();
            driver.findElementByName("Open").click();
            driver.findElementByName("Project/Solution...").click();
            driver.findElementByClassName("Edit").sendKeys("C:\\QA\\Simulation\\Projects\\ConsoleApplication1\\ConsoleApplication1.sln");
            driver.findElementByName("Open").click();


            WebElement newel = driver.findElement(By.xpath("//*[contains(@Name, \"Solution '" + projectName + "'\")]"));
            Actions action = new Actions(driver).contextClick(newel);
            action.build().perform();
            Thread.sleep(2000);
            List<WebElement> newel2 = driver.findElementsByName("Incredibuild");
            newel2.get(0).click();
            System.out.println("");
         }catch (Exception e){
            e.getMessage();
        }
        finally {
            driver.close();
        }
    }


}
