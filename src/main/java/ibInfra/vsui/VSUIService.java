package ibInfra.vsui;

import com.aventstack.extentreports.Status;
import frameworkInfra.testbases.WindowsTestBase;
import frameworkInfra.utils.StaticDataProvider;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.remote.DesiredCapabilities;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static frameworkInfra.testbases.VSTestBase.driver;

public class VSUIService extends WindowsTestBase implements IVSUIService {

    public void vsFirstActivation(){
        driver.findElementByName("Not now, maybe later.").click();
        driver.findElementByName("Start Visual Studio").click();
    }

    @Override
    public void installVSWithIB() {
        runWin.runCommandWaitForFinish(StaticDataProvider.WindowsCommands.INSTALL_VS_WITH_IB);
        runWin.waitForProcessToStart("vs_installer.exe");
        runWin.waitForProcessToFinish("vs_installer.exe");
    }

    @Override
    public void installVSWithoutIB() {
        runWin.runCommandWaitForFinish(StaticDataProvider.WindowsCommands.INSTALL_VS_WO_IB);
        runWin.waitForProcessToStart("vs_installer.exe");
        runWin.waitForProcessToFinish("vs_installer.exe");
    }

    @Override
    public void upgradeVSWithIB() {
        runWin.runCommandWaitForFinish(StaticDataProvider.WindowsCommands.UPDATE_VS_WITH_IB);
        runWin.waitForProcessToStart("vs_installer.exe");
        runWin.waitForProcessToFinish("vs_installer.exe");
    }

    @Override
    public void installVSPreviewWithIB() {
        runWin.runCommandWaitForFinish(StaticDataProvider.WindowsCommands.INSTALL_VSPREVIEW_WITH_IB);
        runWin.waitForProcessToStart("vs_installer.exe");
        runWin.waitForProcessToFinish("vs_installer.exe");
    }

    @Override
    public void installVSPreviewWithoutIB() {
        runWin.runCommandWaitForFinish(StaticDataProvider.WindowsCommands.INSTALL_VSPREVIEW_WO_IB);
        runWin.waitForProcessToStart("vs_installer.exe");
        runWin.waitForProcessToFinish("vs_installer.exe");
    }

    @Override
    public void upgradeVSPreviewWithIB() {
        runWin.runCommandWaitForFinish(StaticDataProvider.WindowsCommands.UPDATE_VSPREVIEW_WITH_IB);
        runWin.waitForProcessToStart("vs_installer.exe");
        runWin.waitForProcessToFinish("vs_installer.exe");
    }

    @Override
    public void uninstallIbExtension() {

    }

    @Override
    public void openProject(String projectPath) {
        driver.findElementByName("File").click();
        driver.findElementByName("Open").click();
        driver.findElementByName("Project/Solution...").click();
        driver.findElementByClassName("Edit").sendKeys(projectPath);
        driver.findElementByName("Open").click();
    }

    @Override
    public void executeBuildFromMenu(String action) {
        driver.findElementByName("Build");
        driver.findElementByName("Incredibuild").click();
        driver.findElementByName(action).click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        runWin.waitForProcessToFinish("buildsystem.exe");
    }

    @Override
    public void openVS2017instance() {
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            test.log(Status.INFO, "Opening VS2017 Preview");
            capabilities.setCapability("app", "C:\\Program Files (x86)\\Microsoft Visual Studio\\Preview\\Professional\\Common7\\IDE\\devenv.exe");
            driver = new WindowsDriver(new URL("http://127.0.0.1:4723"), capabilities);
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            test.log(Status.INFO, "Visual Studio opened successfully");
            try {
                driver.findElementByName("Not now, maybe later.").click();
                vsFirstActivation();
            } catch (Exception e){
                e.getMessage();
            }
        } catch (MalformedURLException e) {
            e.getMessage();
        }
    }


}
