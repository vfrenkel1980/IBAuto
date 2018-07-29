package Native.UnitTests;

import com.jcraft.jsch.JSchException;
import frameworkInfra.utils.*;
import ibInfra.dataObjects.postgres.CoordBuild;
import ibInfra.ibService.IbService;
import ibInfra.linuxcl.LinuxDBService;
import ibInfra.linuxcl.LinuxService;
import ibInfra.vs.VSUIService;
import ibInfra.windowscl.WindowsService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;

public class UnitTests {

    @Test
    public void test() {
        String time = "19h 30m";
        String time2 = "30m 21s";
        String hours = "";
        String minutes = "";
        String seconds = "";
        int epoch = 0;

        if (time2.contains("h")){
            hours = time.substring(0,time.indexOf("h"));
            minutes = time.substring(time.indexOf(" "), time.indexOf("m")).replaceAll(" ","");
            epoch = Integer.parseInt(hours) *3600 + Integer.parseInt(minutes)*60;
        }
        else{
            minutes = time2.substring(0, time2.indexOf("m"));
            seconds = time2.substring(time2.indexOf(" "), time2.indexOf("s")).replaceAll(" ","");
            epoch = Integer.parseInt(minutes) *60 + Integer.parseInt(seconds);
        }
        System.out.println(epoch);
    }

    @Test(testName = "test2")
    public void test2() throws JSchException {
        List<CoordBuild> cars= new ArrayList<CoordBuild>();
        for (int i= 0; i < 5; i++)
            cars.add(new CoordBuild());
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

        webDriver.findElement(By.xpath("//*[@id=\"page-content-container\"]/div[2]/div/div[2]/div[2]/div/div[2]/div/div[1]/div[1]/div/label")).getText();
        System.out.println("");
    }

}