package ibInfra.vs;

import com.aventstack.extentreports.Status;
import frameworkInfra.utils.AppiumActions;
import frameworkInfra.utils.parsers.Parser;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import ibInfra.ibService.IIBService;
import ibInfra.windowscl.WindowsService;
import io.appium.java_client.windows.WindowsDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static frameworkInfra.Listeners.SuiteListener.test;

/**
 * Class to perform actions in VS
 */
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
        String installedBuild = "";
        String out = "";
        WindowsService windowsService = new WindowsService();
        //TODO: remove this section when latest version hist VS
        int version = IIBService.getIbVersion();
        if (version > 2457)
            out = windowsService.runCommandGetOutput(InitMSBuild.MSBUILD + " /version");
        else
            out = windowsService.runCommandGetOutput(InitOLDMSBuild.OLD_MSBUILD + " /version");
        try {
            FileUtils.writeStringToFile(new File(Locations.QA_ROOT + "\\out.txt"), out, "UTF-8");
            BufferedReader input = new BufferedReader(new FileReader(Locations.QA_ROOT + "\\out.txt"));
            String line;

            while ((line = input.readLine()) != null) {
                installedBuild = line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            SystemActions.deleteFile(Locations.QA_ROOT + "\\out.txt");
        }
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

    //TODO: once 2019 is released change all values of 15 from simulations to 16
    //e.g. VSIntegrationTestBase
    @Override
    public void openProject(String projectPath, String version) {

        if (version.equals("15")){
            driver.findElementByName("File").click();
            driver.findElementByName("Open").click();
            driver.findElementByName("Project/Solution...").click();
        }
        else {
            driver.findElementByName("Open a project or solution").click();
        }
        SystemActions.sleep(2);
        driver.findElementByClassName("Edit").sendKeys(projectPath);
        driver.findElementByName("Open").click();
        WebDriverWait wait = new WebDriverWait(driver,90);
        try {
            driver.switchTo().window(driver.getWindowHandle());
        } catch (Exception e){
            driver.switchTo().window(driver.getWindowHandle());
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//*[contains(@Name, \"Solution '\")]"))));
        test.log(Status.INFO, projectPath + " project opened");
    }

    @Override
    public void createNewProject(String projectName, String version) {
        List<WebElement> nameTB;
        if (version.equals("15")){
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
        }
        else{
            driver.findElementByName("Create a new project").click();
            driver.findElementByName("Console App").click();
            driver.findElementByName("Next").click();
            driver.findElement(By.xpath("//*[@AutomationId=\"projectNameText\"]")).clear();
            driver.findElement(By.xpath("//*[@AutomationId=\"projectNameText\"]")).sendKeys(projectName);
            driver.findElement(By.xpath("//*[@AutomationId=\"PART_EditableTextBox\"]")).clear();
            driver.findElement(By.xpath("//*[@AutomationId=\"PART_EditableTextBox\"]")).sendKeys(Locations.QA_ROOT + "\\projects");
            driver.findElement(By.xpath("//*[@AutomationId=\"button_Next\"]")).click();
        }
        WebDriverWait wait = new WebDriverWait(driver,90);
        //in vs2019 the following command will switch to the new opened windows (a new session)
        try {
            driver.switchTo().window(driver.getWindowHandle());
        } catch (Exception e){
            driver.switchTo().window(driver.getWindowHandle());
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//*[@Name=\"Build\"]"))));

    }

    @Override
    public void performIbActionFromMenu(String action) {
        driver.findElementByName("Build");
        List<WebElement> ibElements = null;
        ibElements =driver.findElementsByName("Incredibuild");
        if (ibElements.size() == 0) {
            ibElements = driver.findElementsByName("IncrediBuild");
        }
        ibElements.get(0).click();
        driver.findElementByName(action).click();
        test.log(Status.INFO, "Successfully clicked on " + action);
        SystemActions.sleep(3);
        winService.waitForProcessToFinish("buildsystem.exe");
    }

    @Override
    public void performIbActionFromMenuDontWaitForFinish(String action) {
        driver.findElementByName("Build");
        List<WebElement> ibElements = null;
        ibElements =driver.findElementsByName("Incredibuild");
        if (ibElements.size() == 0) {
            ibElements = driver.findElementsByName("IncrediBuild");
        }
        ibElements.get(0).click();
        driver.findElementByName(action).click();
        test.log(Status.INFO, "Successfully clicked on " + action);
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

    /**
     * Open VS instance
     * @param version version to open
     * @param isFirstActivation boolean value to apply first activation menus if needed
     * @param scenario if scenario=3 - install on a different drive
     */
    @Override
    public void openVSInstance(String version, boolean isFirstActivation, String scenario) {
        String pathToDevenv = "";
        switch (version) {
            case "preview":
                if (scenario.equals("3"))
                    pathToDevenv = "E:\\Microsoft Visual Studio\\Preview\\Professional\\Common7\\IDE\\devenv.exe";
                else
                    pathToDevenv = VsDevenvInstallPath.VS2019_PREVIEW + "\\devenv.exe";
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
                if (scenario.equals("3"))
                    pathToDevenv = "E:\\Microsoft Visual Studio\\2017\\Professional\\Common7\\IDE\\devenv.exe";
                else
                    pathToDevenv = "C:\\Program Files (x86)\\Microsoft Visual Studio\\2017\\Professional\\Common7\\IDE\\devenv.exe";
                break;
        }
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            if (test != null) {
                test.log(Status.INFO, "Opening VS" + version);
            }
            capabilities.setCapability("app", pathToDevenv);
            driver = new WindowsDriver(new URL("http://127.0.0.1:4723"), capabilities);
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            if (test != null) {
                test.log(Status.INFO, "Visual Studio opened successfully");
            }
            SystemActions.sleep(30);
            if(isFirstActivation) {
                try {
                    SystemActions.sleep(10);
                    vsFirstActivation();
                } catch (Exception e) {
                    test.log(Status.ERROR, "Failed set VS first activation: ------>" + e.getMessage());
                }
                finally {
                    driver.quit();
                }
            }
        } catch (Exception e) {
            test.log(Status.ERROR, "Failed to open VS with following error: ------>" + e.getMessage());
        }

    }
}
