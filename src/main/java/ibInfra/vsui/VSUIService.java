package ibInfra.vsui;

import frameworkInfra.testbases.TestBase;
import ibInfra.windowscl.WindowsCLService;

import static frameworkInfra.testbases.VSTestBase.driver;

public class VSUIService extends TestBase implements IVSUIService {
    WindowsCLService runWin = new WindowsCLService();

    public void vsFirstActivation(){
        driver.findElementByName("Not now, maybe later.").click();
        driver.findElementByName("Start Visual Studio").click();
    }

    @Override
    public void installVSWithIB() {

    }

    @Override
    public void installVSWithoutIB() {

    }

    @Override
    public void upgradeVSWithIB() {

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
    public void executeBuild(String action) {
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

}
