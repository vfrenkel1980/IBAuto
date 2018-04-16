package Native.UnitTests;

import ibInfra.ibService.IbService;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class UnitTests {

    @Test(testName = "Check Basic Agent Assignment")
    public void checkBasicAgentAssignment() throws IOException {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\src\\main\\resources\\WebDrivers\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.google.com");


    }



    @Test(testName = "Check version of installed IB extension")
    public void checkInstalledExtension(){
        IbService ibService = new IbService();
        String extensionVersion = ibService.getIbVsExtensionVersion("C:\\Program Files (x86)\\Microsoft Visual Studio\\2017\\Professional\\Common7\\IDE");
        String expectedExtensionVersion = ibService.getExpectedIbVsExtensionVersion();
        Assert.assertTrue(extensionVersion.equals(expectedExtensionVersion), "IncrediBuild Extension Version: " + expectedExtensionVersion + "\n" + "Installed Extension Version: " + extensionVersion);
    }
}
