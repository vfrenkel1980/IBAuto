package ibInfra.vsui;

import com.aventstack.extentreports.Status;
import frameworkInfra.testbases.TestBase;
import frameworkInfra.utils.AppiumActions;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider.*;
import ibInfra.ibService.IbService;
import ibInfra.windowscl.WindowsService;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static com.sun.jna.platform.win32.WinReg.HKEY_CURRENT_USER;
import static frameworkInfra.testbases.VSTestBase.driver;

public class VSUIService extends TestBase implements IVSUIService {

    WindowsService runWin = new WindowsService();
    IbService ibService = new IbService();

    public void vsFirstActivation(){
        driver.findElementByName("Not now, maybe later.").click();
        driver.findElementByName("Start Visual Studio").click();
    }

    @Override
    public void installVSWithIB() {
        runWin.runCommandWaitForFinish(WindowsCommands.INSTALL_VS_WITH_IB);
        runWin.waitForProcessToStart("vs_installer.exe");
        runWin.waitForProcessToFinish("vs_installer.exe");
        RegistryService.setRegistryKey(HKEY_CURRENT_USER, "Software\\Xoreax\\IncrediBuild\\Builder", RegistryKeys.VS_FIRST_ACTIVATION, "0");
    }

    @Override
    public void installVSWithoutIB() {
        runWin.runCommandWaitForFinish(WindowsCommands.INSTALL_VS_WO_IB);
        runWin.waitForProcessToStart("vs_installer.exe");
        runWin.waitForProcessToFinish("vs_installer.exe");
    }

    @Override
    public void upgradeVSWithIB() {
        upgradeVS();
        runWin.runCommandWaitForFinish(WindowsCommands.MODIFY_ADD_INCREDIBUILD);
        runWin.waitForProcessToFinish("vs_professional.exe");
        runWin.waitForProcessToStart("vs_installer.exe");
        runWin.waitForProcessToStart("vs_installer.exe");
        ibService.loadIbLicense(IbLicenses.VSTESTS_LIC);
    }

    @Override
    public void upgradeVS() {
        runWin.runCommandWaitForFinish(WindowsCommands.UPDATE_VS_WITH_IB);
        runWin.waitForProcessToStart("vs_bootstrapper.exe");
        runWin.waitForProcessToFinish("vs_bootstrapper.exe");
        runWin.waitForProcessToStart("vs_installer.exe");
        runWin.waitForProcessToFinish("vs_installer.exe");
    }

    @Override
    public void installVSPreviewWithIB() {
        runWin.runCommandWaitForFinish(WindowsCommands.INSTALL_VSPREVIEW_WITH_IB);
        runWin.waitForProcessToStart("vs_installer.exe");
        runWin.waitForProcessToFinish("vs_installer.exe");
        RegistryService.setRegistryKey(HKEY_CURRENT_USER, "Software\\Xoreax\\IncrediBuild\\Builder", RegistryKeys.VS_FIRST_ACTIVATION, "0");
    }

    @Override
    public void installVSPreviewWithoutIB() {
        runWin.runCommandWaitForFinish(WindowsCommands.INSTALL_VSPREVIEW_WO_IB);
        runWin.waitForProcessToStart("vs_installer.exe");
        runWin.waitForProcessToFinish("vs_installer.exe");
    }

    @Override
    public void upgradeVSPreviewWithIB() {
        upgradeVSPreview();
        runWin.runCommandWaitForFinish(WindowsCommands.MODIFY_PREVIEW_ADD_INCREDIBUILD);
        runWin.waitForProcessToFinish("vs_professional_preview.exe");
        runWin.waitForProcessToStart("vs_installer.exe");
        runWin.waitForProcessToStart("vs_installer.exe");
        ibService.loadIbLicense(IbLicenses.VSTESTS_LIC);
    }

    @Override
    public void upgradeVSPreview() {
        runWin.runCommandWaitForFinish(WindowsCommands.UPDATE_VSPREVIEW);
        runWin.waitForProcessToStart("vs_bootstrapper.exe");
        runWin.waitForProcessToFinish("vs_bootstrapper.exe");
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
    public void executeBuildFromPrjExplorer(String action, String solutionName){
        driver.findElementByName("Build");
        WebElement newel = driver.findElement(By.xpath("//*[contains(@Name, \"Solution '" + solutionName + "'\")]"));
        AppiumActions.rightClick(newel);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        AppiumActions.contextMenuIncrediBuildClick();
        driver.findElementByName(action).click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        runWin.waitForProcessToFinish("buildsystem.exe");
    }

    @Override
    public void openVS2017instance(String vsinstallation) {
        String PathToDevenv = "";
        switch (vsinstallation) {
            case "release":
                PathToDevenv = "C:\\Program Files (x86)\\Microsoft Visual Studio\\2017\\Professional\\Common7\\IDE\\devenv.exe";
                break;
            case "preview":
                PathToDevenv = "C:\\Program Files (x86)\\Microsoft Visual Studio\\Preview\\Professional\\Common7\\IDE\\devenv.exe";
                break;
        }
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            test.log(Status.INFO, "Opening VS2017");
            capabilities.setCapability("app", PathToDevenv);
            driver = new WindowsDriver(new URL("http://127.0.0.1:4723"), capabilities);
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            test.log(Status.INFO, "Visual Studio opened successfully");
            try {
                vsFirstActivation();
            } catch (Exception e) {
                e.getMessage();
            }
        } catch (MalformedURLException e) {
            e.getMessage();
        }

    }
}
