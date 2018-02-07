package uitests;

import ibInfra.vsui.VSUIService;
import ibInfra.windowscl.WindowsCLService;
import io.appium.java_client.windows.WindowsDriver;
import jdk.nashorn.internal.ir.annotations.Ignore;
import frameworkInfra.sikuli.sikulimapping.ibmonitor.IBMonitor;
import frameworkInfra.testbases.VSTestBase;
import frameworkInfra.utils.Parser;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class VSTests extends VSTestBase {


    @Test
    public void test() {
        WindowsCLService run = new WindowsCLService();
        WindowsDriver driver = null;
        VSUIService runVs = new VSUIService();
        run.installIB();

        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("app", "C:\\Program Files (x86)\\Microsoft Visual Studio\\Preview\\Professional\\Common7\\IDE\\devenv.exe");
            driver = new WindowsDriver(new URL("http://127.0.0.1:4723"), capabilities);
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            runVs.openProject("C:\\QA\\Simulation\\Projects\\ConsoleApplication1\\ConsoleApplication1.sln");
            runVs.executeBuild("Rebuild Solution");
        }catch (Exception e){
            e.getMessage();
        }
        finally {
            driver.close();
        }
    }

}
