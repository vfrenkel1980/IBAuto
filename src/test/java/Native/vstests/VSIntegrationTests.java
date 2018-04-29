package Native.vstests;

import frameworkInfra.Listeners.SuiteListener;
import frameworkInfra.testbases.VSIntegrationTestBase;
import frameworkInfra.utils.StaticDataProvider;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;

@Listeners(SuiteListener.class)
public class VSIntegrationTests extends VSIntegrationTestBase {

    @Test(testName = "Execute Build from Menu")
    public void executeBuildFromMenu(){
        String result;
        vsuiService.performIbActionFromMenu(StaticDataProvider.VsActions.CLEAN_SOLUTION);
        vsuiService.performIbActionFromMenu(StaticDataProvider.VsActions.BUILD_SOLUTION);
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
        vsuiService.performIbActionFromMenu(StaticDataProvider.VsActions.REBUILD_SOLUTION);
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
        vsuiService.performIbActionFromPrjExplorer(StaticDataProvider.VsActions.CLEAN_SOLUTION,"solution", projectName);
        vsuiService.performIbActionFromPrjExplorer(StaticDataProvider.VsActions.BUILD_SOLUTION,"solution", projectName);
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
        vsuiService.performIbActionFromPrjExplorer(StaticDataProvider.VsActions.REBUILD_SOLUTION,"solution", projectName);
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
        vsuiService.performIbActionFromMenu(StaticDataProvider.VsActions.CLEAN_PROJECT);
        vsuiService.performIbActionFromMenu(StaticDataProvider.VsActions.BUILD_PROJECT);
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
        vsuiService.performIbActionFromMenu(StaticDataProvider.VsActions.REBUILD_PROJECT);
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
        vsuiService.performIbActionFromPrjExplorer(StaticDataProvider.VsActions.CLEAN_PROJECT,"project", projectName);
        vsuiService.performIbActionFromPrjExplorer(StaticDataProvider.VsActions.BUILD_PROJECT,"project", projectName);
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
        vsuiService.performIbActionFromPrjExplorer(StaticDataProvider.VsActions.REBUILD_PROJECT,"project", projectName);
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"));
        } catch (IOException e) {
            e.getMessage();
        }
    }

    @Test(testName = "IncrediBuild Stop Build")
    public void stopIbBuild(){
        vsuiService.performIbActionFromMenuDontWaitForFinish(StaticDataProvider.VsActions.REBUILD_SOLUTION);
        vsuiService.performIbActionFromMenu(StaticDataProvider.VsActions.STOP_BUILD);
        String result;
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("4"));
        } catch (IOException e) {
            e.getMessage();
        }
    }

}
