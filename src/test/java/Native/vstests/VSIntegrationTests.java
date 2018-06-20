package Native.vstests;

import frameworkInfra.Listeners.SuiteListener;
import frameworkInfra.testbases.VSIntegrationTestBase;
import frameworkInfra.utils.Parser;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import org.apache.velocity.runtime.directive.Parse;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;

@Listeners(SuiteListener.class)
public class VSIntegrationTests extends VSIntegrationTestBase {

    @Test(testName = "Execute Build from Menu")
    public void executeBuildFromMenu(){
        String result;
        vsuiService.performIbActionFromMenu(VsActions.CLEAN_SOLUTION);
        vsuiService.performIbActionFromMenu(VsActions.BUILD_SOLUTION);
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"));
        } catch (IOException e) {
            e.getMessage();
        }
    }

    @Test(testName = "Execute ReBuild from Menu")
    public void executeReBuildFromMenu(){
        String result;
        vsuiService.performIbActionFromMenu(VsActions.REBUILD_SOLUTION);
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"));
        } catch (IOException e) {
            e.getMessage();
        }
    }

    @Test(testName = "Execute Build from Project Explorer")
    public void executeBuildFromProjectExplorer(){
        String result;
        vsuiService.performIbActionFromPrjExplorer(VsActions.CLEAN_SOLUTION,"solution", projectName);
        vsuiService.performIbActionFromPrjExplorer(VsActions.BUILD_SOLUTION,"solution", projectName);
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"));
        } catch (IOException e) {
            e.getMessage();
        }
    }

    @Test(testName = "Execute ReBuild from Project Explorer")
    public void executeReBuildFromProjectExplorer(){
        String result;
        vsuiService.performIbActionFromPrjExplorer(VsActions.REBUILD_SOLUTION,"solution", projectName);
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"));
        } catch (IOException e) {
            e.getMessage();
        }
    }

    @Test(testName = "Execute Build project from Menu")
    public void executeBuildProjectFromMenu(){
        String result;
        vsuiService.performIbActionFromMenu(VsActions.CLEAN_PROJECT);
        vsuiService.performIbActionFromMenu(VsActions.BUILD_PROJECT);
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"));
        } catch (IOException e) {
            e.getMessage();
        }
    }

    @Test(testName = "Execute ReBuild project from Menu")
    public void executeReBuildProjectFromMenu(){
        String result;
        vsuiService.performIbActionFromMenu(VsActions.REBUILD_PROJECT);
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"));
        } catch (IOException e) {
            e.getMessage();
        }
    }

    @Test(testName = "Execute Build project from Project Explorer")
    public void executeBuildProjectFromProjectExplorer(){
        String result;
        vsuiService.performIbActionFromPrjExplorer(VsActions.CLEAN_PROJECT,"project", projectName);
        vsuiService.performIbActionFromPrjExplorer(VsActions.BUILD_PROJECT,"project", projectName);
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"));
        } catch (IOException e) {
            e.getMessage();
        }
    }

    @Test(testName = "Execute ReBuild project from Project Explorer")
    public void executeReBuildProjectFromProjectExplorer(){
        String result;
        vsuiService.performIbActionFromPrjExplorer(VsActions.REBUILD_PROJECT,"project", projectName);
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"));
        } catch (IOException e) {
            e.getMessage();
        }
    }

    @Test(testName = "IncrediBuild Stop Build")
    public void stopIbBuild(){
        vsuiService.performIbActionFromMenuDontWaitForFinish(VsActions.REBUILD_SOLUTION);
        vsuiService.performIbActionFromMenu(VsActions.STOP_BUILD);
        Assert.assertTrue(Parser.doesFileContainString(Locations.SYSTEM_APPDATA_TEMP_FOLDER + "IB_BuildOutput.log", LogOutput.TERMINATION_MESSAGE));
    }

}
