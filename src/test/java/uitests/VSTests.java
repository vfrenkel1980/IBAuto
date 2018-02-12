package uitests;

import jdk.nashorn.internal.ir.annotations.Ignore;
import frameworkInfra.sikuli.sikulimapping.ibmonitor.IBMonitor;
import frameworkInfra.testbases.VSTestBase;
import frameworkInfra.utils.Parser;
import com.aventstack.extentreports.Status;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class VSTests extends VSTestBase {
    @Ignore
    @Test
    public void runVSBuildAndGetResultUsingLog() throws InterruptedException, FindFailed, IOException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("ExitCode", "ExitCode ");
        Screen screen = new Screen();
        driver.findElementByName("File").click();
        driver.findElementByName("Open").click();
        driver.findElementByName("Project/Solution...").click();
        driver.findElementByClassName("Edit").sendKeys("\\\\192.168.10.15\\Share\\Mark\\ConsoleApplication3\\ConsoleApplication3.sln");
        driver.findElementByName("Open").click();
        driver.findElementByName("Build");
        driver.findElementByName("Incredibuild").click();
        driver.findElementByName("Build Solution").click();
        screen.wait(IBMonitor.warningBar.similar((float) 0.8),30).click(IBMonitor.warningBar);
        driver.close();
        test.log(Status.INFO, "Build finished");
        String fileName = Parser.getFileToParse(System.getProperty("java.io.tmpdir"), "pkt*");
        String exitCode = Parser.retrieveDataFromFile(System.getProperty("java.io.tmpdir") + fileName, map);
        Assert.assertEquals(exitCode, "0", "Exit code does not match, build failed");
    }
}
