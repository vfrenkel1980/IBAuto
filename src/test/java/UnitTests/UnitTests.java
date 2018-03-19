package UnitTests;

import frameworkInfra.utils.StaticDataProvider;
import ibInfra.vsui.VSUIService;
import org.testng.annotations.Test;

public class UnitTests {

    @Test
    public void test() {

        VSUIService vsService = new VSUIService();
        vsService.openVSInstance("15");
        vsService.openProject(StaticDataProvider.TestProjects.CONSOLE_APPLICATION_01);
        vsService.performIbActionFromPrjExplorer(StaticDataProvider.VsActions.CLEAN_SOLUTION,"project", "ConsoleApplication1");
    }
}
