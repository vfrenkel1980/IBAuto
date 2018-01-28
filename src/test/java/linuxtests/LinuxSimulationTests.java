package linuxtests;

import frameworkInfra.testbases.LinuxSimTestBase;
import frameworkInfra.utils.StaticDataProvider;
import ibInfra.linuxcl.LinuxCL;
import org.testng.Assert;
import org.testng.annotations.Test;


import java.util.List;


public class LinuxSimulationTests extends LinuxSimTestBase {

    @Test(testName = "Linux threads" )
    public void runIbConsoleAndVerifyResult() throws InterruptedException {
//        threads thread1 = new threads();
//        thread1.start();
//        threads thread2 = new threads();
//        thread2.start();




        int exitCode = runCommand.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_APACHE_DIR + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";" + StaticDataProvider.LinuxSimulation.BUILD,
                StaticDataProvider.LinuxMachines.TEST_MACHINE);
        Assert.assertTrue(exitCode  == 0 || exitCode == 2, "build failed");


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

    @Test(testName = "MyName", invocationCount = 3)
    public void myFirstTest(){
        Assert.assertEquals(1, 1, ":(");
    }

}
