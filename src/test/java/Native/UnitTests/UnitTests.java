package Native.UnitTests;

import com.jcraft.jsch.JSchException;
import frameworkInfra.utils.*;
import frameworkInfra.utils.databases.PostgresJDBC;
import frameworkInfra.utils.parsers.Parser;
import ibInfra.dataObjects.postgres.CoordBuild;
import ibInfra.windowscl.WindowsService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class UnitTests {

    @Test
    public void test() {
        WindowsService winService = new WindowsService();
        winService.runCommandDontWaitForTermination(StaticDataProvider.IbLocations.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC15_BATMAN.AUDACITY_X32_DEBUG, StaticDataProvider.ProjectsCommands.REBUILD));
        winService.runCommandDontWaitForTermination(StaticDataProvider.Processes.PSEXEC + " \\\\" + StaticDataProvider.WindowsMachines.SECOND_INITIATOR + " -u Administrator -p 4illumination -i 1 " +
                "\"C:\\Program Files (x86)\\IncrediBuild\\buildconsole\" C:\\QA\\Simulation\\VC11\\ACE_VC11\\ACE_vc2012.sln /rebuild /cfg=\"debug|win32\" /title=\"ACE 2012 - Debug\" " +
                "/out=\"C:\\QA\\simulation\\buildlog.txt\" /showagent /showcmd /showtime");

        winService.waitForProcessToFinishOnRemoteMachine(StaticDataProvider.WindowsMachines.SECOND_INITIATOR, "Administrator" , "4illumination", "buildsystem");
        winService.runCommandWaitForFinish("xcopy \"r:\\QA\\Simulation\\buildLog.txt\" " + StaticDataProvider.Locations.SECOND_INITIATOR_LOG_PATH );
        Assert.assertTrue(SystemActions.doesFileExist(StaticDataProvider.Locations.SECOND_INITIATOR_LOG_PATH + "buildLog.txt"));

        boolean isPresent = Parser.doesFileContainString(StaticDataProvider.Locations.SECOND_INITIATOR_LOG_PATH + "buildlog.txt", StaticDataProvider.LogOutput.AGENT);
        if (isPresent){
            SystemActions.copyFile(StaticDataProvider.Locations.SECOND_INITIATOR_LOG_PATH + "buildlog.txt", StaticDataProvider.Locations.QA_ROOT + "\\logs\\for_investigation");
        }
        SystemActions.deleteFile(StaticDataProvider.Locations.SECOND_INITIATOR_LOG_PATH + "buildlog.txt");
        winService.waitForProcessToFinish(StaticDataProvider.Processes.BUILDSYSTEM);
        Assert.assertTrue(isPresent, "No agent assigned to build");
    }

    @Test(testName = "test2")
    public void test2() throws JSchException {
        List<CoordBuild> coords= new ArrayList<CoordBuild>();
        PostgresJDBC postgresJDBC = new PostgresJDBC();
        for (int i= 0; i < 1; i++)
            coords.add(new CoordBuild());
        for (CoordBuild coord:coords) {
            postgresJDBC.runFunctionOnCoordBuildTable("localhost", "ib", "ib", "coordinatordb", "sp_insert_coord_build", coord);
        }
        System.out.println("");

    }


    @Test(testName = "test3")
    public void test3 () {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/main/resources/WebDrivers/chromedriver.exe");
        WebDriver webDriver = new ChromeDriver();
        webDriver.get("http://localhost:8000/#/material/overview");
        webDriver.manage().window().maximize();
        webDriver.findElement(By.xpath("//a[@href=\"#/material/builds\"]")).click();
        WebDriverWait wait = new WebDriverWait(webDriver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'All')]"))).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='md-spinner md-theme-default md-indeterminate']")));

        String string = webDriver.findElement(By.xpath("//*[@id=\"page-content-container\"]/div[2]/div/div[2]/div[2]/div/div[2]/div/div[1]/div[1]/div/label")).getText();
        System.out.println(string);
        webDriver.close();
    }

}