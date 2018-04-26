package Native.vstests;

import frameworkInfra.testbases.VSIntegrationTestBase;
import frameworkInfra.utils.StaticDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;


public class VSIntegrationTests extends VSIntegrationTestBase {

    @Test(testName = "Execute Build from Menu")
    public void executeBuildFromMenu(){
        String result;
        vsService.performIbActionFromMenu(StaticDataProvider.VsActions.CLEAN_SOLUTION);
        vsService.performIbActionFromMenu(StaticDataProvider.VsActions.BUILD_SOLUTION);
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
        vsService.performIbActionFromMenu(StaticDataProvider.VsActions.REBUILD_SOLUTION);
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
        vsService.performIbActionFromPrjExplorer(StaticDataProvider.VsActions.CLEAN_SOLUTION,"solution", projectName);
        vsService.performIbActionFromPrjExplorer(StaticDataProvider.VsActions.BUILD_SOLUTION,"solution", projectName);
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
        vsService.performIbActionFromPrjExplorer(StaticDataProvider.VsActions.REBUILD_SOLUTION,"solution", projectName);
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
        vsService.performIbActionFromMenu(StaticDataProvider.VsActions.CLEAN_PROJECT);
        vsService.performIbActionFromMenu(StaticDataProvider.VsActions.BUILD_PROJECT);
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
        vsService.performIbActionFromMenu(StaticDataProvider.VsActions.REBUILD_PROJECT);
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
        vsService.performIbActionFromPrjExplorer(StaticDataProvider.VsActions.CLEAN_PROJECT,"project", projectName);
        vsService.performIbActionFromPrjExplorer(StaticDataProvider.VsActions.BUILD_PROJECT,"project", projectName);
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
        vsService.performIbActionFromPrjExplorer(StaticDataProvider.VsActions.REBUILD_PROJECT,"project", projectName);
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"));
        } catch (IOException e) {
            e.getMessage();
        }
    }

    @Test(testName = "IncrediBuild Stop Build")
    public void stopIbBuild(){
        vsService.performIbActionFromMenuDontWaitForFinish(StaticDataProvider.VsActions.REBUILD_SOLUTION);
        vsService.performIbActionFromMenu(StaticDataProvider.VsActions.STOP_BUILD);
        String result;
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("4"));
        } catch (IOException e) {
            e.getMessage();
        }
    }

}
