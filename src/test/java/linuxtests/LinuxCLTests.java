package linuxtests;

import frameworkInfra.testbases.LinuxSimTestBase;
import org.testng.annotations.Test;


import java.util.List;

public class LinuxCLTests extends LinuxSimTestBase {

    @Test(testName = "Linux Test" )
    public void runIbConsoleAndVerifyResult() throws InterruptedException {
        System.out.println(ipList.get(0));



/*        LinuxCL runCommand = new LinuxCL();

        String success = "//*[@id=\"build_history_contentholder_table\"]/tbody/tr[1]/td[2]/div/div[2]";
        String navigateCommand = "cd /disk2/projects/httpd-2.4.18";
        String executeCommand = "ib_console make -j32";
        String executeClean = "make clean";
        runCommand.linuxRunSSHCommand(navigateCommand + "; " + executeClean + "& "+ executeCommand + "& "+ executeClean + "& "+ executeCommand, "192.168.11.82");


        System.setProperty("webdriver.chrome.driver","C:\\Users\\Mark\\Downloads\\AutomationPOCtools\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        String baseUrl = "http://192.168.11.82:8080/incredibuild/";

        driver.get(baseUrl);
        Thread.sleep(10000);
        String result = driver.findElement(By.xpath(success)).getText();
        Assert.assertEquals(result, "Completed", "Result did not match expected");
        driver.close();*/
    }
}
