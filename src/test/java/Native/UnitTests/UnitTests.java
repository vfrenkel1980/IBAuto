package Native.UnitTests;

import frameworkInfra.utils.MailService;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.SystemActions;
import ibInfra.ibService.IbService;
import ibInfra.ibUIService.IBUIService;
import ibInfra.vs.VSUIService;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import webInfra.ibWeb.downloadPage.RegistrationForm;
import webInfra.ibWeb.pageObjects.DownloadPageObject;

import java.io.IOException;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static frameworkInfra.Listeners.SuiteListener.extent;
import static frameworkInfra.Listeners.SuiteListener.test;

public class UnitTests {

    @Test
    public void test() {
        VSUIService vsService = new VSUIService();
        IbService ibService = new IbService();
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT +"\\Builder", StaticDataProvider.RegistryKeys.SAVE_BUILD_PACKET, "1");
        vsService.openVSInstance("15", false);
        vsService.createNewProject("custom");
        vsService.performIbActionFromPrjExplorer(StaticDataProvider.VsActions.REBUILD_SOLUTION, "solution", "custom");
        String result;
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"));
        } catch (IOException e) {
            e.getMessage();
        }
    }

    @Test
    public void test2() {

    }
}
