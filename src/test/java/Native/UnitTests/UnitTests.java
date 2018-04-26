package Native.UnitTests;

import frameworkInfra.Listeners.SuiteListener;
import frameworkInfra.utils.StaticDataProvider;
import ibInfra.ibService.IbService;
import ibInfra.vsui.VSUIService;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import webInfra.RestCalls.Get.GetIsMailRegistered;

import java.io.IOException;

public class UnitTests {

    @Test
    public void test(){
        String projectPath = StaticDataProvider.TestProjects.VC15PROJECT;
        VSUIService vsService = new VSUIService();
        IbService ibService = new IbService();
        vsService.openVSInstance("15", false);
        vsService.openProject("\"C:\\QA\\Simulation\\Projects\\ConsoleApplication1\\ConsoleApplication1.sln\"");
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
