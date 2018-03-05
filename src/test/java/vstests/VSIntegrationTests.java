package vstests;

import frameworkInfra.testbases.VSIntegrationTestBase;
import frameworkInfra.utils.StaticDataProvider;
import org.testng.annotations.Test;

public class VSIntegrationTests extends VSIntegrationTestBase {

    @Test(testName = "IncrediBuild execution from VS2017 menu bar")
    public void executeVSBuild(){
        vsService.executeBuildFromPrjExplorer(StaticDataProvider.VsActions.REBUILD_SOLUTION, projectName);
        System.out.println("");
    }


}
