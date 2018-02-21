package uitests;

import frameworkInfra.testbases.TestBase;
import frameworkInfra.testbases.WindowsTestBase;
import frameworkInfra.utils.Parser;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider;
import ibInfra.ibService.IbService;
import ibInfra.vsui.VSUIService;
import ibInfra.windowscl.WindowsService;
import io.appium.java_client.windows.WindowsDriver;
import frameworkInfra.testbases.VSTestBase;
import org.apache.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static frameworkInfra.utils.StaticDataProvider.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class VSTests /*extends TestBase*/ {

    IbService ibService = new IbService();
/*    @Test
    public void test() {
        IbService run = new IbService();
        WindowsDriver driver = null;
        VSUIService runVs = new VSUIService();
        run.installIB("Latest");

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
            runVs.executeBuildFromMenu("Rebuild Solution");
        }catch (Exception e){
            e.getMessage();
        }
        finally {
            driver.close();
        }
    }*/

    @Test(testName = "Successful build - check for success. Predicted 0, MSBuild 0")
    public void successCheckForSuccessfulBuildNoPredictedNoMSBuild(){
        setRegistry("0", RegistryKeys.PREDICTED);
        setRegistry("0", RegistryKeys.MSBUILD);
        ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_SUCCESS, "%s"));
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.BUILD_SUCCEEDED));
    }

    @Test(testName = "Failed build - check for error. Predicted 0, MSBuild 0")
    public void failedCheckForErrorBuildNoPredictedNoMSBuild(){
        setRegistry("0", RegistryKeys.PREDICTED);
        setRegistry("0", RegistryKeys.MSBUILD);
        ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_FAIL, "%s"));
        Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.ERROR));
    }

    @Test(testName = "Successful build - check for xgtaskid. Predicted 0, MSBuild 0")
    public void failedCheckForXgTaskIdNoPredictedNoMSBuild(){
        setRegistry("0", RegistryKeys.PREDICTED);
        setRegistry("0", RegistryKeys.MSBUILD);
        ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_FAIL, "%s"));
        Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.XDTASKID));
    }

    @Test(testName = "Successful build - check for xgspeculativetaskid. Predicted 0, MSBuild 0")
    public void failedCheckForXgSpeculativeTaskIdNoPredictedNoMSBuild(){
        setRegistry("0", RegistryKeys.PREDICTED);
        setRegistry("0", RegistryKeys.MSBUILD);
        ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_FAIL, "%s"));
        Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.XDSPECULATIVETASKID));
    }

    @Test(testName = "Successful build - check for success. Predicted 0, MSBuild 1")
    public void successCheckForSuccessfulBuildNoPredictedMSBuild(){
        setRegistry("0", RegistryKeys.PREDICTED);
        setRegistry("1", RegistryKeys.MSBUILD);
        ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_SUCCESS, "%s"));
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.BUILD_SUCCEEDED));
    }

    @Test(testName = "Failed build - check for error. Predicted 0, MSBuild 1")
    public void failedCheckForErrorBuildNoPredictedMSBuild(){
        setRegistry("0", RegistryKeys.PREDICTED);
        setRegistry("1", RegistryKeys.MSBUILD);
        ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_FAIL, "%s"));
        Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.ERROR));
    }

    @Test(testName = "Successful build - check for xgtaskid. Predicted 0, MSBuild 1")
    public void failedCheckForXgTaskIdNoPredictedMSBuild(){
        setRegistry("0", RegistryKeys.PREDICTED);
        setRegistry("1", RegistryKeys.MSBUILD);
        ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_FAIL, "%s"));
        Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.XDTASKID));
    }

    @Test(testName = "Successful build - check for xgspeculativetaskid. Predicted 0, MSBuild 1")
    public void failedCheckForXgSpeculativeTaskIdNoPredictedMSBuild(){
        setRegistry("0", RegistryKeys.PREDICTED);
        setRegistry("1", RegistryKeys.MSBUILD);
        ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_FAIL, "%s"));
        Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.XDSPECULATIVETASKID));
    }

    @Test(testName = "Successful build - check for success. Predicted 2, MSBuild 0")
    public void successCheckForSuccessfulBuildPredictedNoMSBuild(){
        setRegistry("2", RegistryKeys.PREDICTED);
        setRegistry("0", RegistryKeys.MSBUILD);
        ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_SUCCESS, "%s"));
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.BUILD_SUCCEEDED));
    }

    @Test(testName = "Failed build - check for error. Predicted 2, MSBuild 0")
    public void failedCheckForErrorBuildPredictedNoMSBuild(){
        setRegistry("2", RegistryKeys.PREDICTED);
        setRegistry("0", RegistryKeys.MSBUILD);
        ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_FAIL, "%s"));
        Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.ERROR));
    }

    @Test(testName = "Successful build - check for xgtaskid. Predicted 2, MSBuild 0")
    public void failedCheckForXgTaskIdPredictedNoMSBuild(){
        setRegistry("2", RegistryKeys.PREDICTED);
        setRegistry("0", RegistryKeys.MSBUILD);
        ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_FAIL, "%s"));
        Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.XDTASKID));
    }

    @Test(testName = "Successful build - check for xgspeculativetaskid. Predicted 2, MSBuild 0")
    public void failedCheckForXgSpeculativeTaskIdPredictedNoMSBuild(){
        setRegistry("2", RegistryKeys.PREDICTED);
        setRegistry("0", RegistryKeys.MSBUILD);
        ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_FAIL, "%s"));
        Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.XDSPECULATIVETASKID));
    }

    @Test(testName = "Successful build - check for success. Predicted 2, MSBuild 1")
    public void successCheckForSuccessfulBuildPredictedMSBuild(){
        setRegistry("2", RegistryKeys.PREDICTED);
        setRegistry("1", RegistryKeys.MSBUILD);
        ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_SUCCESS, "%s"));
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.BUILD_SUCCEEDED));
    }

    @Test(testName = "Failed build - check for error. Predicted 2, MSBuild 1")
    public void failedCheckForErrorBuildPredictedMSBuild(){
        setRegistry("2", RegistryKeys.PREDICTED);
        setRegistry("1", RegistryKeys.MSBUILD);
        ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_FAIL, "%s"));
        Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.ERROR));
    }

    @Test(testName = "Successful build - check for xgtaskid. Predicted 2, MSBuild 1")
    public void failedCheckForXgTaskIdPredictedMSBuild(){
        setRegistry("2", RegistryKeys.PREDICTED);
        setRegistry("1", RegistryKeys.MSBUILD);
        ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_FAIL, "%s"));
        Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.XDTASKID));
    }

    @Test(testName = "Successful build - check for xgspeculativetaskid. Predicted 2, MSBuild 1")
    public void failedCheckForXgSpeculativeTaskIdPredictedMSBuild(){
        setRegistry("2", RegistryKeys.PREDICTED);
        setRegistry("1", RegistryKeys.MSBUILD);
        ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_FAIL, "%s"));
        Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.XDSPECULATIVETASKID));
    }

    
    /*------------------------------METHODS------------------------------*/
    
    private void setRegistry(String required, String keyName){
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\builder", keyName, required);
    }


}
