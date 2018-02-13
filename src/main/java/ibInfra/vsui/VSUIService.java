package ibInfra.vsui;

import frameworkInfra.testbases.WindowsTestBase;
import frameworkInfra.utils.StaticDataProvider;


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
