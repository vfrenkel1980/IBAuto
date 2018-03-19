package vstests;

import frameworkInfra.testbases.VSIntegrationTestBase;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;

public class VSIntegrationTests extends VSIntegrationTestBase {

/*    @Test(testName = "IncrediBuild execution from VS2017 menu bar")
    public void executeVSBuild(){
        vsService.performIbActionFromPrjExplorer(StaticDataProvider.VsActions.REBUILD_SOLUTION, projectName);
        System.out.println("");
    }*/

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
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT +"\\Builder", StaticDataProvider.RegistryKeys.SAVE_BUILD_PACKET, "1");
        vsService.openVSInstance("15");
        vsService.openProject(StaticDataProvider.TestProjects.CONSOLE_APPLICATION_01);
        vsService.performIbActionFromMenu(StaticDataProvider.VsActions.REBUILD_SOLUTION);
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
