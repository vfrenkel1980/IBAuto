package vstests;

import frameworkInfra.testbases.VSIntegrationTestBase;
import frameworkInfra.utils.StaticDataProvider;
import org.testng.annotations.Test;

public class VSIntegrationTests extends VSIntegrationTestBase {

    @Test(testName = "IncrediBuild execution from VS2017 menu bar")
    public void executeVSBuild(){
        vsService.executeBuildFromMenu(StaticDataProvider.VsActions.REBUILD_SOLUTION);
        System.out.println("");
    }


}
