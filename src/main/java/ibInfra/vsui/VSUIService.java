package ibInfra.vsui;

import com.aventstack.extentreports.Status;
import frameworkInfra.testbases.TestBase;
import frameworkInfra.utils.AppiumActions;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
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

    private WindowsService winService = new WindowsService();
    private IbService ibService = new IbService();

    public void vsFirstActivation(){
        driver.findElementByName("Not now, maybe later.").click();
        driver.findElementByName("Start Visual Studio").click();
        SystemActions.sleep(15);
    }

    @Override
    public void installVSWithIB() {
        winService.runCommandWaitForFinish(WindowsCommands.INSTALL_VS_WITH_IB);
        winService.waitForProcessToStart("vs_installer.exe");
        winService.waitForProcessToFinish("vs_installer.exe");
        RegistryService.setRegistryKey(HKEY_CURRENT_USER, "Software\\Xoreax\\IncrediBuild\\Builder", RegistryKeys.VS_FIRST_ACTIVATION, "0");
    }

    @Override
    public void installVSWithoutIB() {
        winService.runCommandWaitForFinish(WindowsCommands.INSTALL_VS_WO_IB);
        winService.waitForProcessToStart("vs_installer.exe");
        winService.waitForProcessToFinish("vs_installer.exe");
    }

    @Override
    public void upgradeVSWithIB() {
        upgradeVS();
        winService.runCommandWaitForFinish(WindowsCommands.MODIFY_ADD_INCREDIBUILD);
        winService.waitForProcessToFinish("vs_professional.exe");
        winService.waitForProcessToStart("vs_installer.exe");
        winService.waitForProcessToFinish("vs_installer.exe");
        ibService.loadIbLicense(IbLicenses.VSTESTS_LIC);
        RegistryService.setRegistryKey(HKEY_CURRENT_USER, "Software\\Xoreax\\IncrediBuild\\Builder", RegistryKeys.VS_FIRST_ACTIVATION, "0");
    }

    @Override
    public void upgradeVS() {
        winService.runCommandWaitForFinish(WindowsCommands.UPDATE_VS_WITH_IB);
        winService.waitForProcessToStart("vs_bootstrapper.exe");
        winService.waitForProcessToFinish("vs_bootstrapper.exe");
        winService.waitForProcessToStart("vs_installer.exe");
        winService.waitForProcessToFinish("vs_installer.exe");
    }

    @Override
    public void installVSPreviewWithIB() {
        winService.runCommandWaitForFinish(WindowsCommands.INSTALL_VSPREVIEW_WITH_IB);
        winService.waitForProcessToStart("vs_installer.exe");
        winService.waitForProcessToFinish("vs_installer.exe");
        RegistryService.setRegistryKey(HKEY_CURRENT_USER, "Software\\Xoreax\\IncrediBuild\\Builder", RegistryKeys.VS_FIRST_ACTIVATION, "0");
    }

    @Override
    public void installVSPreviewWithoutIB() {
        winService.runCommandWaitForFinish(WindowsCommands.INSTALL_VSPREVIEW_WO_IB);
        winService.waitForProcessToStart("vs_installer.exe");
        winService.waitForProcessToFinish("vs_installer.exe");
    }

    @Override
    public void upgradeVSPreviewWithIB() {
        upgradeVSPreview();
        winService.runCommandWaitForFinish(WindowsCommands.MODIFY_PREVIEW_ADD_INCREDIBUILD);
        winService.waitForProcessToFinish("vs_professional_preview.exe");
        winService.waitForProcessToStart("vs_installer.exe");
        winService.waitForProcessToFinish("vs_installer.exe");
        ibService.loadIbLicense(IbLicenses.VSTESTS_LIC);
        RegistryService.setRegistryKey(HKEY_CURRENT_USER, "Software\\Xoreax\\IncrediBuild\\Builder", RegistryKeys.VS_FIRST_ACTIVATION, "0");
    }

    @Override
    public void upgradeVSPreview() {
        winService.runCommandWaitForFinish(WindowsCommands.UPDATE_VSPREVIEW);
        winService.waitForProcessToStart("vs_bootstrapper.exe");
        winService.waitForProcessToFinish("vs_bootstrapper.exe");
        winService.waitForProcessToStart("vs_installer.exe");
        winService.waitForProcessToFinish("vs_installer.exe");
    }

    @Override
    public void uninstallIbExtension() {

    }

    @Override
    public void openProject(String projectPath) {
        driver.findElementByName("File").click();
        driver.findElementByName("Open").click();
        driver.findElementByName("Project/Solution...").click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.getMessage();
        }
        driver.findElementByClassName("Edit").sendKeys(projectPath);
        driver.findElementByName("Open").click();
        test.log(Status.INFO, projectPath + " project opened");
    }

    @Override
    public void performIbActionFromMenu(String action) {
        driver.findElementByName("Build");
        try {
            driver.findElementByName("Incredibuild").click();
        }
        catch (Exception e){
            try {
                driver.findElementByName("IncrediBuild").click();
            }
            catch (Exception e1){
                e.getMessage();
            }
        }
        driver.findElementByName(action).click();
        test.log(Status.INFO, "Successfully clicked on " + action);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        winService.waitForProcessToFinish("buildsystem.exe");
    }

    @Override
    public void performIbActionFromPrjExplorer(String action,String type, String solutionName){
        driver.findElementByName("Build");
        WebElement newel;
        if (type.equals("solution")) {
            newel = driver.findElement(By.xpath("//*[contains(@Name, \"Solution '" + solutionName + "'\")]"));
        }
        else{
            newel = driver.findElementByName(solutionName);
        }
        AppiumActions.rightClick(newel);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        AppiumActions.contextMenuIncrediBuildClick(newel);
        driver.findElementByName(action).click();
        test.log(Status.INFO, "Successfully clicked on " + newel.getText() + action);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        winService.waitForProcessToFinish("buildsystem.exe");
    }

    @Override
    public void openVSInstance(String version) {
        String pathToDevenv = "";
        switch (version) {
            case "preview":
                pathToDevenv = "C:\\Program Files (x86)\\Microsoft Visual Studio\\Preview\\Professional\\Common7\\IDE\\devenv.exe";
                break;
            case "8":
                pathToDevenv = "\"C:\\Program Files (x86)\\Microsoft Visual Studio 8\\Common7\\IDE\\devenv.exe\"";
                break;
            case "9":
                pathToDevenv = "\"C:\\Program Files (x86)\\Microsoft Visual Studio 9.0\\Common7\\IDE\\devenv.exe\"";
                break;
            case "10":
                pathToDevenv = "\"C:\\Program Files (x86)\\Microsoft Visual Studio 10.0\\Common7\\IDE\\devenv.exe\"";
                break;
            case "11":
                pathToDevenv = "\"C:\\Program Files (x86)\\Microsoft Visual Studio 11.0\\Common7\\IDE\\devenv.exe\"";
                break;
            case "12":
                pathToDevenv = "\"C:\\Program Files (x86)\\Microsoft Visual Studio 12.0\\Common7\\IDE\\devenv.exe\"";
                break;
            case "14":
                pathToDevenv = "\"C:\\Program Files (x86)\\Microsoft Visual Studio 14.0\\Common7\\IDE\\devenv.exe\"";
                break;
            case "15":
                pathToDevenv = "\"C:\\Program Files (x86)\\Microsoft Visual Studio\\2017\\Professional\\Common7\\IDE\\devenv.exe\"";
                break;
        }
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            test.log(Status.INFO, "Opening VS" + version);
            capabilities.setCapability("app", pathToDevenv);
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
