package windowstests;

import frameworkInfra.testbases.VmBCTestBase;
import frameworkInfra.utils.StaticDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

public class VmSimVC11Tests extends VmBCTestBase {


    @Test(testName = "ACE 2012 - Debug x32 - build" , groups = { "Build" })
    public void ace2013DebugBuild(){
        int returnCode = runWin.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC11_VMSIM.ACE_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "ACE 2012 - Release x32 - build" , groups = { "Build" })
    public void ace2013ReleaseBuild(){
        int returnCode = runWin.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC11_VMSIM.ACE_X32_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "XBMC 2012 - Debug x32- build" , groups = { "Build" })
    public void xbmc2012x32Debug(){
        int returnCode = runWin.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC11_VMSIM.XMBC_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "XBMC 2012 - Release x32- build" , groups = { "Build" })
    public void xbmc2012x32Release(){
        int returnCode = runWin.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC11_VMSIM.XMBC_X32_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }


}
