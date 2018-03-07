package vstests;

import frameworkInfra.testbases.VSIntegrationTestBase;
import frameworkInfra.utils.StaticDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class VSIntegrationTests extends VSIntegrationTestBase {

/*    @Test(testName = "IncrediBuild execution from VS2017 menu bar")
    public void executeVSBuild(){
        vsService.executeBuildFromPrjExplorer(StaticDataProvider.VsActions.REBUILD_SOLUTION, projectName);
        System.out.println("");
    }*/

    @Test(testName = "Execute Build from Menu")
    public void executeBuildFromMenu(){
        String result;
        vsService.executeBuildFromMenu(StaticDataProvider.VsActions.BUILD_SOLUTION);
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
        vsService.executeBuildFromMenu(StaticDataProvider.VsActions.REBUILD_SOLUTION);
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
        vsService.executeBuildFromPrjExplorer(StaticDataProvider.VsActions.BUILD_SOLUTION, projectName);
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
        vsService.executeBuildFromPrjExplorer(StaticDataProvider.VsActions.REBUILD_SOLUTION, projectName);
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"));
        } catch (IOException e) {
            e.getMessage();
        }
    }


}
