package Native.vstests;

import frameworkInfra.Listeners.SuiteListener;
import frameworkInfra.testbases.VSIntegrationTestBase;
import frameworkInfra.utils.SystemActions;
import frameworkInfra.utils.parsers.Parser;
import frameworkInfra.utils.StaticDataProvider.*;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;

@Listeners(SuiteListener.class)
public class VSIntegrationTests extends VSIntegrationTestBase {

    @Test(testName = "Execute Build from Menu")
    public void executeBuildFromMenu() {
        String result;
        vsuiService.performIbActionFromMenu(VsActions.CLEAN_SOLUTION);
        SystemActions.sleep(15);
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"));
        } catch (IOException e) {
            e.getMessage();
        }
    }

    @Test(testName = "Execute ReBuild from Menu")
    public void executeReBuildFromMenu() {
        String result;
        vsuiService.performIbActionFromMenu(VsActions.REBUILD_SOLUTION);
        SystemActions.sleep(15);
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"));
        } catch (IOException e) {
            e.getMessage();
        }
    }

    @Test(testName = "Execute Build from Project Explorer")
    public void executeBuildFromProjectExplorer() throws Exception {
        String result;
        vsuiService.performIbActionFromPrjExplorer(VsActions.CLEAN_SOLUTION, VsTreeType.SOLUTION, projectName);
        SystemActions.sleep(15);
        vsuiService.performIbActionFromPrjExplorer(VsActions.BUILD_SOLUTION, VsTreeType.SOLUTION, projectName);
        SystemActions.sleep(25);
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"));
        } catch (IOException e) {
            e.getMessage();
        }
    }

    @Test(testName = "Execute ReBuild from Project Explorer")
    public void executeReBuildFromProjectExplorer() throws Exception {
        String result;
        vsuiService.performIbActionFromPrjExplorer(VsActions.REBUILD_SOLUTION, VsTreeType.SOLUTION, projectName);
        SystemActions.sleep(15);
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"));
        } catch (IOException e) {
            e.getMessage();
        }
    }

    @Test(testName = "Execute Build project from Menu")
    public void executeBuildProjectFromMenu() {
        String result;
        vsuiService.performIbActionFromMenu(VsActions.CLEAN_PROJECT);
        SystemActions.sleep(15);
        vsuiService.performIbActionFromMenu(VsActions.BUILD_PROJECT);
        SystemActions.sleep(25);
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"));
        } catch (IOException e) {
            e.getMessage();
        }
    }

    @Test(testName = "Execute ReBuild project from Menu")
    public void executeReBuildProjectFromMenu() {
        String result;
        vsuiService.performIbActionFromMenu(VsActions.REBUILD_PROJECT);
        SystemActions.sleep(15);
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"));
        } catch (IOException e) {
            e.getMessage();
        }
    }

    @Test(testName = "Execute Build project from Project Explorer")
    public void executeBuildProjectFromProjectExplorer() throws Exception {
        String result;
        vsuiService.performIbActionFromPrjExplorer(VsActions.CLEAN_PROJECT, VsTreeType.PROJECT, projectName);
        SystemActions.sleep(15);
        vsuiService.performIbActionFromPrjExplorer(VsActions.BUILD_PROJECT, VsTreeType.PROJECT, projectName);
        SystemActions.sleep(15);
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"));
        } catch (IOException e) {
            e.getMessage();
        }
    }

    @Test(testName = "Execute ReBuild project from Project Explorer")
    public void executeReBuildProjectFromProjectExplorer() throws Exception {
        String result;
        vsuiService.performIbActionFromPrjExplorer(VsActions.REBUILD_PROJECT, VsTreeType.PROJECT, projectName);
        SystemActions.sleep(15);
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"));
        } catch (IOException e) {
            e.getMessage();
        }
    }

    @Test(testName = "IncrediBuild Stop Build")
    public void stopIbBuild() {
        vsuiService.performIbActionFromMenuDontWaitForFinish(VsActions.REBUILD_SOLUTION);
        vsuiService.performIbActionFromMenu(VsActions.STOP_BUILD);
        SystemActions.sleep(15);
        Assert.assertTrue(Parser.doesFileContainString(Locations.SYSTEM_APPDATA_TEMP_FOLDER + "IB_BuildOutput.log", LogOutput.TERMINATION_MESSAGE));
    }

}//end of class
