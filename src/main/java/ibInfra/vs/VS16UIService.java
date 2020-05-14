package ibInfra.vs;

import com.aventstack.extentreports.Status;
import frameworkInfra.utils.AppiumActions;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.StaticDataProvider.InitMSBuild;
import frameworkInfra.utils.StaticDataProvider.Locations;
import frameworkInfra.utils.StaticDataProvider.VsDevenvInstallPath;
import frameworkInfra.utils.SystemActions;
import ibInfra.windowscl.WindowsService;
import io.appium.java_client.windows.WindowsDriver;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static frameworkInfra.Listeners.SuiteListener.test;

/**
 * Class to perform actions in VS
 */
public class VS16UIService implements IVSUIService {

    private WindowsService winService = new WindowsService();
    private WindowsDriver driver;

    public VS16UIService(WindowsDriver driver) {
        this.driver = driver;
    }

    public VS16UIService() {
    }

    public void vsFirstActivation() {
        driver.findElementByName("Not now, maybe later.").click();
        driver.findElementByName("Start Visual Studio").click();
    }

    @Override
    public String getInstalledMSBuildVersion() {
        String installedBuild = "";
        String out = "";
        WindowsService windowsService = new WindowsService();
        out = windowsService.runCommandGetOutput(InitMSBuild.MSBUILD + " /version");
        try {
            FileUtils.writeStringToFile(new File(Locations.QA_ROOT + "\\out.txt"), out, "UTF-8");
            BufferedReader input = new BufferedReader(new FileReader(Locations.QA_ROOT + "\\out.txt"));
            String line;

            while ((line = input.readLine()) != null) {
                installedBuild = line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
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

    @Override
    public void openProject(String projectPath) {

        driver.findElementByName("Open a local Visual Studio project or .sln file").click();
        SystemActions.sleep(2);
        driver.findElementByClassName("Edit").sendKeys(projectPath);
        driver.findElementByName("Open").click();
        WebDriverWait wait = new WebDriverWait(driver, 90);
        try {
            driver.switchTo().window(driver.getWindowHandle());
        } catch (Exception e) {
            driver.switchTo().window(driver.getWindowHandle());
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//*[contains(@Name, \"Solution '\")]"))));
        test.log(Status.INFO, projectPath + " project opened");
    }

    @Override
    public void createNewProject(String projectName, String version) {
        driver.findElementByName("Create a _new project").click();
        driver.findElementByName("Console App").click();
        driver.findElementByName("Next").click();
        driver.findElement(By.xpath("//*[@AutomationId=\"projectNameText\"]")).clear();
        driver.findElement(By.xpath("//*[@AutomationId=\"projectNameText\"]")).sendKeys(projectName);
        driver.findElement(By.xpath("//*[@AutomationId=\"PART_EditableTextBox\"]")).clear();
        driver.findElement(By.xpath("//*[@AutomationId=\"PART_EditableTextBox\"]")).sendKeys(Locations.QA_ROOT + "\\projects");
        driver.findElement(By.xpath("//*[@AutomationId=\"button_Next\"]")).click();
        WebDriverWait wait = new WebDriverWait(driver, 90);
        //in vs2019 the following command will switch to the new opened windows (a new session)
        try {
            driver.switchTo().window(driver.getWindowHandle());
        } catch (Exception e) {
            driver.switchTo().window(driver.getWindowHandle());
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//*[@Name=\"Build\"]"))));

    }

    @Override
    public void performIbActionFromMenu(String action) {
        driver.findElementByName("Build");
        driver.findElementByName("Extensions").click();
        driver.findElementByName("IncrediBuild").click();
        driver.findElementByName(action).click();
        test.log(Status.INFO, "Successfully clicked on " + action);
        SystemActions.sleep(3);
        winService.waitForProcessToFinish("buildsystem.exe");
    }

    @Override
    public void performIbActionFromMenuDontWaitForFinish(String action) {
        driver.findElementByName("Build");
        driver.findElementByName("Extensions").click();
        driver.findElementByName("IncrediBuild").click();
        driver.findElementByName(action).click();
        test.log(Status.INFO, "Successfully clicked on " + action);
    }

    @Override
    public void performIbActionFromPrjExplorer(String action, String type, String solutionName) throws Exception {
        driver.findElementByName("Build");
        WebElement newel;
        if (type.equals("solution")) {
            newel = driver.findElement(By.xpath("//*[contains(@Name, \"Solution '" + solutionName + "'\")]"));
        } else {
            newel = driver.findElementByName(solutionName);
        }
        AppiumActions.rightClick(newel, driver);
        SystemActions.sleep(2);
        if (type.equals(StaticDataProvider.VsTreeType.SOLUTION)) {
            AppiumActions.solutionContextMenuIncrediBuildClickForVCVersion16(driver);
            AppiumActions.solutionActionClickForVCVersion16(action, driver);
        } else if (type.equals(StaticDataProvider.VsTreeType.PROJECT)) {
            AppiumActions.projectContextMenuIncrediBuildClickForVCVersion16(driver);
            AppiumActions.projectActionClickForVCVersion16(action, driver);
        }
        test.log(Status.INFO, "Successfully clicked on " + newel.getText() + action);
        SystemActions.sleep(3);
        winService.waitForProcessToFinish("buildsystem.exe");
    }

    /**
     * Open VS instance
     *
     * @param version           version to open 116 is PREVIEW
     * @param isFirstActivation boolean value to apply first activation menus if needed
     * @param scenario          if scenario=3 - install on a different drive
     */
    @Override
    public void openVSInstance(String version, boolean isFirstActivation, String scenario) {
        String pathToDevenv = "";
        switch (version) {
            case "116":
                if (scenario.equals("3"))
                    pathToDevenv = "E:\\Microsoft Visual Studio\\Preview\\Professional\\Common7\\IDE\\devenv.exe";
                else
                    pathToDevenv = VsDevenvInstallPath.VS2019_PREVIEW + "\\devenv.exe";
                break;
            case "16":
                if (scenario.equals("3"))
                    pathToDevenv = "E:\\Microsoft Visual Studio\\2019\\Professional\\Common7\\IDE\\devenv.exe";
                else
                    pathToDevenv = "C:\\Program Files (x86)\\Microsoft Visual Studio\\2019\\Professional\\Common7\\IDE\\devenv.exe";
                break;
        }
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            if (test != null) {
                test.log(Status.INFO, "Opening VS" + version);
            }
            capabilities.setCapability("app", pathToDevenv);
            driver = new WindowsDriver(new URL("http://127.0.0.1:4723"), capabilities);
            Assert.assertNotNull(driver);
            driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
            if (test != null) {
                test.log(Status.INFO, "Visual Studio opened successfully");
            }
            if (isFirstActivation) {
                try {
                    SystemActions.sleep(10);
                    vsFirstActivation();
                } catch (Exception e) {
                    test.log(Status.ERROR, "Failed set VS first activation: ------>" + e.getMessage());
                } finally {
                    driver.quit();
                }
            }
        } catch (Exception e) {
            test.log(Status.ERROR, "Failed to open VS with following error: ------>" + e.getMessage());
        }

    }
}
