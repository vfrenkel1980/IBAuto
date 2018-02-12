package uitests;

import frameworkInfra.testbases.TestBase;
import frameworkInfra.testbases.WindowsTestBase;
import frameworkInfra.utils.StaticDataProvider;
import ibInfra.ibService.IbService;
import ibInfra.vsui.VSUIService;
import ibInfra.windowscl.WindowsService;
import io.appium.java_client.windows.WindowsDriver;
import frameworkInfra.testbases.VSTestBase;
import org.apache.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class VSTests /*extends TestBase*/ {


/*    @Test
    public void test() {
        IbService run = new IbService();
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
    }*/

    @Test
    public void test2(){
        String SCENARIO = System.getProperty("scenario");
        Logger log = Logger.getLogger(TestBase.class.getName());

        if (SCENARIO.equals("1"))
            log.info("1");
        if (SCENARIO.equals("2"))
            log.info("2");
    }

}
