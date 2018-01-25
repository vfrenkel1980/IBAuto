package windowstests;

import frameworkInfra.testbases.VmBCTestBase;
import frameworkInfra.utils.StaticDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

public class VmSimVC7Tests extends VmBCTestBase {

    @Test(testName = "Big Lib 2002 - Debug Release x32 - build" , groups = { "Build" })
    public void bigLib2002DebugReleaseBuild(){
        int returnCode = runCommand.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC7_VMSIM.BIG_LIB_DEBUG_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Quake 2 2002 - build" , groups = { "Build" })
    public void quake22002Build(){
        int returnCode = runCommand.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC7_VMSIM.QUAKE2, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "AbiWord 2002 - build" , groups = { "Build" })
    public void abiWord2002Build(){
        int returnCode = runCommand.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC7_VMSIM.ABIWORD, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Shareaza 2002 - Debug x32 - build" , groups = { "Build" })
    public void shareaza2002DebugBuild(){
        int returnCode = runCommand.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC7_VMSIM.SHAREAZA_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "VScap 2002 - Release x32 - build" , groups = { "Build" })
    public void vscap2002ReleaseBuild(){
        int returnCode = runCommand.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC7_VMSIM.VSCAP_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }
}
