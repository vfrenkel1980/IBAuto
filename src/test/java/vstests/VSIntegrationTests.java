package vstests;

import frameworkInfra.testbases.VSIntegrationTestBase;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;

public class VSIntegrationTests extends VSIntegrationTestBase {

    @Test(testName = "IncrediBuild execution from VS2017 menu bar")
    public void executeVSBuild(){
        vsService.openVS2017instance(devenvPath);
        vsService.openProject(StaticDataProvider.TestProjects.CONSOLE_APPLICATION_01);
        vsService.executeBuildFromMenu(StaticDataProvider.VsActions.REBUILD_SOLUTION);
        System.out.println("");
    }


}
