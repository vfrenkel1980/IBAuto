package ibInfra.vs;

import com.aventstack.extentreports.Status;
import frameworkInfra.utils.AppiumActions;
import frameworkInfra.utils.Parser;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import ibInfra.ibService.IbService;
import ibInfra.windowscl.WindowsService;
import io.appium.java_client.windows.WindowsDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.sun.jna.platform.win32.WinReg.HKEY_CURRENT_USER;
import static frameworkInfra.Listeners.SuiteListener.test;

public class VSUIService implements IVSUIService {

    private WindowsService winService = new WindowsService();
    private WindowsDriver driver;

    public VSUIService(WindowsDriver driver){
        this.driver = driver;
    }
    public VSUIService(){
    }

    public void vsFirstActivation(){
        driver.findElementByName("Not now, maybe later.").click();
        driver.findElementByName("Start Visual Studio").click();
    }

    @Override
    public String getInstalledMSBuildVersion() {
        Map<String, String> lookFor = new HashMap<String, String>();
        String installedBuild = "";
        lookFor.put("version", "version");
        WindowsService windowsService = new WindowsService();
        String out = windowsService.runCommandGetOutput(InitMSBuild.MSBUILD);
        try {
            FileUtils.writeStringToFile(new File(Locations.QA_ROOT + "\\out.txt"), out, "UTF-8");
            installedBuild = Parser.retrieveDataFromFile(Locations.QA_ROOT + "\\out.txt", lookFor);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            SystemActions.deleteFile(Locations.QA_ROOT + "\\out.txt");
        }
        installedBuild = installedBuild.substring(0, installedBuild.indexOf(" "));
        return installedBuild;
    }

    @Override
    public void killDriver() {
        if (driver != null) {
            driver.quit();
        }
        SystemActions.killProcess("devenv.exe");
        driver = null;
    }

    @Override
    public void openProject(String projectPath) {
        driver.findElementByName("File").click();
        driver.findElementByName("Open").click();
        driver.findElementByName("Project/Solution...").click();
        SystemActions.sleep(2);
        driver.findElementByClassName("Edit").sendKeys(projectPath);
        driver.findElementByName("Open").click();
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//*[contains(@Name, \"Solution ')]"))));
        test.log(Status.INFO, projectPath + " project opened");
    }

    @Override
    public void createNewProject(String projectName) {
        List<WebElement> nameTB;
        List<WebElement> locationTB;
        driver.findElementByName("File").click();
        driver.findElementByName("New").click();
        driver.findElementByName("Project...").click();
        SystemActions.sleep(3);
        driver.findElementByName("Windows Console Application").click();
        nameTB =driver.findElementsByName("Name:");
        nameTB.get(1).sendKeys(projectName);
        nameTB =driver.findElementsByName("Location:");
        nameTB.get(1).sendKeys(Locations.QA_ROOT + "\\projects");
        driver.findElementByName("OK").click();
        SystemActions.sleep(5);
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
        SystemActions.sleep(3);
        winService.waitForProcessToFinish("buildsystem.exe");
    }

    @Override
    public void performIbActionFromMenuDontWaitForFinish(String action) {
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
        SystemActions.sleep(3);
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
        AppiumActions.rightClick(newel, driver);
        SystemActions.sleep(2);
        AppiumActions.contextMenuIncrediBuildClick(newel, driver);
        driver.findElementByName(action).click();
        test.log(Status.INFO, "Successfully clicked on " + newel.getText() + action);
        SystemActions.sleep(3);
        winService.waitForProcessToFinish("buildsystem.exe");
    }

    @Override
    public void openVSInstance(String version, boolean isFirstActivation) {
        String pathToDevenv = "";
        switch (version) {
            case "preview":
                pathToDevenv = "C:\\Program Files (x86)\\Microsoft Visual Studio\\Preview\\Professional\\Common7\\IDE\\devenv.exe";
                break;
            case "8":
                pathToDevenv = "C:\\Program Files (x86)\\Microsoft Visual Studio 8\\Common7\\IDE\\devenv.exe";
                break;
            case "9":
                pathToDevenv = "C:\\Program Files (x86)\\Microsoft Visual Studio 9.0\\Common7\\IDE\\devenv.exe";
                break;
            case "10":
                pathToDevenv = "C:\\Program Files (x86)\\Microsoft Visual Studio 10.0\\Common7\\IDE\\devenv.exe";
                break;
            case "11":
                pathToDevenv = "C:\\Program Files (x86)\\Microsoft Visual Studio 11.0\\Common7\\IDE\\devenv.exe";
                break;
            case "12":
                pathToDevenv = "C:\\Program Files (x86)\\Microsoft Visual Studio 12.0\\Common7\\IDE\\devenv.exe";
                break;
            case "14":
                pathToDevenv = "C:\\Program Files (x86)\\Microsoft Visual Studio 14.0\\Common7\\IDE\\devenv.exe";
                break;
            case "15":
                pathToDevenv = "C:\\Program Files (x86)\\Microsoft Visual Studio\\2017\\Professional\\Common7\\IDE\\devenv.exe";
                break;
        }
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            test.log(Status.INFO, "Opening VS" + version);
            capabilities.setCapability("app", pathToDevenv);
            driver = new WindowsDriver(new URL("http://127.0.0.1:4723"), capabilities);
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            test.log(Status.INFO, "Visual Studio opened successfully");
            if(isFirstActivation) {
                try {
                    SystemActions.sleep(10);
                    vsFirstActivation();
                } catch (Exception e) {
                    e.getMessage();
                }
                finally {
                    driver.quit();
                }
            }
        } catch (MalformedURLException e) {
            test.log(Status.ERROR, "Failed to open VS with following error: ------>" + e.getMessage());
        }

    }
}
