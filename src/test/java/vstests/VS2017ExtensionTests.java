package vstests;

import frameworkInfra.testbases.VSTestBase;
import frameworkInfra.utils.Parser;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.io.IOException;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;

public class VS2017ExtensionTests extends VSTestBase {

    @Test(testName = "Check version of installed IB extension")
    public void getVsVersion(){
        ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_SUCCESS, "%s"));
        ibService.getVSVersionFromOutputLog(Locations.OUTPUT_LOG_FILE);
    }

    @Test(testName = "Check version of installed IB extension")
    public void checkInstalledExtension(){
        String extensionVersion = ibService.getIbVsExtensionVersion(devenvPath);
        String expectedExtensionVersion = ibService.getExpectedIbVsExtensionVersion();
        Assert.assertTrue(extensionVersion.equals(expectedExtensionVersion), "IncrediBuild Extension Version: " + expectedExtensionVersion + "\n" + "Installed Extension Version: " + extensionVersion);
    }

    @Test(testName = "IncrediBuild execution from VS2017 menu bar")
    public void executeVSBuild(){
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT +"\\Builder", RegistryKeys.SAVE_BUILD_PACKET, "1");
        vsService.openVSInstance(VSINSTALLATION);
        SystemActions.sleep(30);
        vsService.openProject(TestProjects.CONSOLE_APPLICATION_01);
        vsService.performIbActionFromMenu(VsActions.REBUILD_SOLUTION);
        String result;
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"));
        } catch (IOException e) {
            e.getMessage();
        }
    }

    @Test(testName = "IncrediBuild execution from VS2017 project explorer")
    public void executeVSBuildExplorer(){
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT +"\\Builder", RegistryKeys.SAVE_BUILD_PACKET, "1");
        vsService.openVSInstance(VSINSTALLATION);
        vsService.openProject(TestProjects.CONSOLE_APPLICATION_01);
        vsService.performIbActionFromPrjExplorer(VsActions.REBUILD_SOLUTION,"solution", "ConsoleApplication1");
        String result;
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"));
        } catch (IOException e) {
            e.getMessage();
        }
    }

    @Test(testName = "Check that \"Predicted\" execution is enable: With MSBuild")
    public void checkForPredictedExecutionWithMSBuild() {
        setRegistry("2", RegistryKeys.PREDICTED);
        setRegistry("1", RegistryKeys.MSBUILD);
        ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_SUCCESS, "%s"));
        Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.PREDICTED_DISABLED));
    }

    @Test(testName = "Check that \"Predicted\" execution is enable: Without MSBuild")
    public void checkForPredictedExecutionWithoutMSBuild() {
        setRegistry("2", RegistryKeys.PREDICTED);
        setRegistry("0", RegistryKeys.MSBUILD);
        ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_SUCCESS, "%s"));
        Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.PREDICTED_DISABLED));
    }

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
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.ERROR));
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
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.ERROR));
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
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.ERROR));
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
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.ERROR));
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
