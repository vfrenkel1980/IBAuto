package Native.vstests;

import com.aventstack.extentreports.Status;
import frameworkInfra.testbases.VSTestBase;
import frameworkInfra.utils.*;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.parsers.Parser;
import ibInfra.ibService.IIBService;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static frameworkInfra.Listeners.SuiteListener.test;

public class VS2017ExtensionTests extends VSTestBase {

    @Test(testName = "Check version of VS")
    public void getVsVersion(){
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_SUCCESS, "%s"));
        vsVersion = ibService.getVSVersionFromOutputLog(Locations.OUTPUT_LOG_FILE);
    }

    @Test(testName = "Check version of installed IB extension")
    public void checkInstalledExtension(){
        String extensionVersion = ibService.getIbVsExtensionVersion(devenvPath);
        String expectedExtensionVersion = ibService.getExpectedIbVsExtensionVersion();
        Assert.assertTrue(extensionVersion.equals(expectedExtensionVersion), "Expected Extension Version: " + expectedExtensionVersion + "-------" + "Installed Extension Version: " + extensionVersion);
    }

    @Test(testName = "Get Installed IB Version")
    public void getInstalledIBVersion(){
        ibVersion = IIBService.getIbVersion();
    }

    //TODO add the special reg to create buildlog, build in test and verify to avoid dependency
    @Test(testName = "Compare MSBuild Version", dependsOnMethods = {"executeVSBuild"})
    public void compareMSBuildVersion(){
        installedMsBuildVersion = vsuiService.getInstalledMSBuildVersion();
        int expected = postgresJDBC.getIntFromQuery("192.168.10.73", "postgres", "postgres123", "release_manager", "ms_build_support_version", "Windows_builds_ib_info",
                "build_number=" + ibVersion);
        test.log(Status.INFO, "Expected: " + expected + " <-------> Actual: " + installedMsBuildVersion);
        Assert.assertEquals(installedMsBuildVersion, Integer.toString(expected), "Installed MSBuild version does not match expected");
    }

    @Test(testName = "IncrediBuild execution from VS2017 menu bar")
    public void executeVSBuild(){
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT +"\\Builder", RegistryKeys.SAVE_BUILD_PACKET, "1");
        vsuiService.openVSInstance(VSINSTALLATION, false, SCENARIO);
        SystemActions.sleep(20);
        vsuiService.createNewProject("custom");
        vsuiService.performIbActionFromMenu(VsActions.REBUILD_SOLUTION);
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
        vsuiService.openVSInstance(VSINSTALLATION, false, SCENARIO);
        vsuiService.openProject(TestProjects.CUSTOM_PROJECT);
        vsuiService.performIbActionFromPrjExplorer(VsActions.REBUILD_SOLUTION,"solution", "custom");
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
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_SUCCESS, "%s"));
        Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.PREDICTED_DISABLED));
    }

    @Test(testName = "Check that \"Predicted\" execution is enable: Without MSBuild")
    public void checkForPredictedExecutionWithoutMSBuild() {
        setRegistry("2", RegistryKeys.PREDICTED);
        setRegistry("0", RegistryKeys.MSBUILD);
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_SUCCESS, "%s"));
        Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.PREDICTED_DISABLED));
    }

    @Test(testName = "Successful build - check for success. Predicted 0, MSBuild 0")
    public void successCheckForSuccessfulBuildNoPredictedNoMSBuild(){
        setRegistry("0", RegistryKeys.PREDICTED);
        setRegistry("0", RegistryKeys.MSBUILD);
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_SUCCESS, "%s"));
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.BUILD_SUCCEEDED));
    }

    @Test(testName = "Failed build - check for error. Predicted 0, MSBuild 0")
    public void failedCheckForErrorBuildNoPredictedNoMSBuild(){
        setRegistry("0", RegistryKeys.PREDICTED);
        setRegistry("0", RegistryKeys.MSBUILD);
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_FAIL, "%s"));
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.ERROR));
    }

    @Test(testName = "Successful build - check for xgtaskid. Predicted 0, MSBuild 0")
    public void failedCheckForXgTaskIdNoPredictedNoMSBuild(){
        setRegistry("0", RegistryKeys.PREDICTED);
        setRegistry("0", RegistryKeys.MSBUILD);
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_FAIL, "%s"));
        Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.XDTASKID));
    }

    @Test(testName = "Successful build - check for xgspeculativetaskid. Predicted 0, MSBuild 0")
    public void failedCheckForXgSpeculativeTaskIdNoPredictedNoMSBuild(){
        setRegistry("0", RegistryKeys.PREDICTED);
        setRegistry("0", RegistryKeys.MSBUILD);
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_FAIL, "%s"));
        Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.XDSPECULATIVETASKID));
    }

    @Test(testName = "Successful build - check for success. Predicted 0, MSBuild 1")
    public void successCheckForSuccessfulBuildNoPredictedMSBuild(){
        setRegistry("0", RegistryKeys.PREDICTED);
        setRegistry("1", RegistryKeys.MSBUILD);
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_SUCCESS, "%s"));
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.BUILD_SUCCEEDED));
    }

    @Test(testName = "Failed build - check for error. Predicted 0, MSBuild 1")
    public void failedCheckForErrorBuildNoPredictedMSBuild(){
        setRegistry("0", RegistryKeys.PREDICTED);
        setRegistry("1", RegistryKeys.MSBUILD);
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_FAIL, "%s"));
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.ERROR));
    }

    @Test(testName = "Successful build - check for xgtaskid. Predicted 0, MSBuild 1")
    public void failedCheckForXgTaskIdNoPredictedMSBuild(){
        setRegistry("0", RegistryKeys.PREDICTED);
        setRegistry("1", RegistryKeys.MSBUILD);
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_FAIL, "%s"));
        Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.XDTASKID));
    }

    @Test(testName = "Successful build - check for xgspeculativetaskid. Predicted 0, MSBuild 1")
    public void failedCheckForXgSpeculativeTaskIdNoPredictedMSBuild(){
        setRegistry("0", RegistryKeys.PREDICTED);
        setRegistry("1", RegistryKeys.MSBUILD);
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_FAIL, "%s"));
        Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.XDSPECULATIVETASKID));
    }

    @Test(testName = "Successful build - check for success. Predicted 2, MSBuild 0")
    public void successCheckForSuccessfulBuildPredictedNoMSBuild(){
        setRegistry("2", RegistryKeys.PREDICTED);
        setRegistry("0", RegistryKeys.MSBUILD);
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_SUCCESS, "%s"));
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.BUILD_SUCCEEDED));
    }

    @Test(testName = "Failed build - check for error. Predicted 2, MSBuild 0")
    public void failedCheckForErrorBuildPredictedNoMSBuild(){
        setRegistry("2", RegistryKeys.PREDICTED);
        setRegistry("0", RegistryKeys.MSBUILD);
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_FAIL, "%s"));
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.ERROR));
    }

    @Test(testName = "Successful build - check for xgtaskid. Predicted 2, MSBuild 0")
    public void failedCheckForXgTaskIdPredictedNoMSBuild(){
        setRegistry("2", RegistryKeys.PREDICTED);
        setRegistry("0", RegistryKeys.MSBUILD);
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_FAIL, "%s"));
        Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.XDTASKID));
    }

    @Test(testName = "Successful build - check for xgspeculativetaskid. Predicted 2, MSBuild 0")
    public void failedCheckForXgSpeculativeTaskIdPredictedNoMSBuild(){
        setRegistry("2", RegistryKeys.PREDICTED);
        setRegistry("0", RegistryKeys.MSBUILD);
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_FAIL, "%s"));
        Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.XDSPECULATIVETASKID));
    }

    @Test(testName = "Successful build - check for success. Predicted 2, MSBuild 1")
    public void successCheckForSuccessfulBuildPredictedMSBuild(){
        setRegistry("2", RegistryKeys.PREDICTED);
        setRegistry("1", RegistryKeys.MSBUILD);
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_SUCCESS, "%s"));
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.BUILD_SUCCEEDED));
    }

    @Test(testName = "Failed build - check for error. Predicted 2, MSBuild 1")
    public void failedCheckForErrorBuildPredictedMSBuild(){
        setRegistry("2", RegistryKeys.PREDICTED);
        setRegistry("1", RegistryKeys.MSBUILD);
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_FAIL, "%s"));
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.ERROR));
    }

    @Test(testName = "Successful build - check for xgtaskid. Predicted 2, MSBuild 1")
    public void failedCheckForXgTaskIdPredictedMSBuild(){
        setRegistry("2", RegistryKeys.PREDICTED);
        setRegistry("1", RegistryKeys.MSBUILD);
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_FAIL, "%s"));
        Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.XDTASKID));
    }

    @Test(testName = "Successful build - check for xgspeculativetaskid. Predicted 2, MSBuild 1")
    public void failedCheckForXgSpeculativeTaskIdPredictedMSBuild(){
        setRegistry("2", RegistryKeys.PREDICTED);
        setRegistry("1", RegistryKeys.MSBUILD);
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_FAIL, "%s"));
        Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.XDSPECULATIVETASKID));
    }

    @Test(testName = "Predicted With Project Dependency. Predicted 2, MSBuild 1")
    public void predictedWithProjectDependency(){
        setRegistry("2", RegistryKeys.PREDICTED);
        setRegistry("1", RegistryKeys.MSBUILD);
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.DEPENDENCY_PROJECT, "%s"));
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.BUILD_SUCCEEDED));
        String task1 = Parser.getValueAccordingToString(Locations.OUTPUT_LOG_FILE,"DependencyProject2.cpp", "Local CPU");
        String task2 = Parser.getValueAccordingToString(Locations.OUTPUT_LOG_FILE,"DependencyProject.cpp)", "Local CPU");
        Assert.assertNotEquals(task1, "", "Could not find project in log");
        Assert.assertNotEquals(task1, task2, "Both tasks run on the same core");

        File ibmsbhlpLog = new File(IbLocations.LOGS_ROOT + "\\IBMSBHLP.log");
        Assert.assertFalse(ibmsbhlpLog.exists(), "IBMSBHLP.log file was created during the \"Predicted\" execution");
    }

    @Test(testName = "Write Data To DB")
    public void writeDataToDB() {
        if (SCENARIO.equals("2")) {
            ibVsInstallationName = SystemActions.findFileInDirectoryRecursively("C:\\ProgramData\\Microsoft\\VisualStudio\\Packages", "incredibuild_vs2017*.exe");
            postgresJDBC.insertDataToTable("192.168.10.73", "postgres", "postgres123", "release_manager", "vs_release_versioning",
                    "vs_version, ib_version, msbuild_version, ib_installer_name",
                    "\'" + vsVersion + "\', \'" + ibVersion + "\', \'" + installedMsBuildVersion + "\', \'" + ibVsInstallationName + "\'");
        }
    }


    /*------------------------------METHODS------------------------------*/

    private void setRegistry(String required, String keyName){
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\builder", keyName, required);
    }

}
