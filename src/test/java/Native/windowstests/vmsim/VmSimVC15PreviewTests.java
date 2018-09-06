package Native.windowstests.vmsim;

import frameworkInfra.testbases.VmSimTestBase;
import frameworkInfra.utils.StaticDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

public class VmSimVC15PreviewTests extends VmSimTestBase {

    @Test(testName = "Audacity 2017 Preview - Debug - build" , groups = { "Build" })
    public void audacityDebugBuild(){
        int returnCode = ibService.cleanAndBuild(StaticDataProvider.IbLocations.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC15Preview_VMSIM.AUDACITY_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Big Project 2017 Preview - Debug - build" , groups = { "Build" })
    public void bigProjectDebugBuild(){
        int returnCode = ibService.cleanAndBuild(StaticDataProvider.IbLocations.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC15Preview_VMSIM.BIGPROJECT_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Big Project 2017 Preview - Release - build" , groups = { "Build" })
    public void bigProjectReleaseBuild(){
        int returnCode = ibService.cleanAndBuild(StaticDataProvider.IbLocations.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC15Preview_VMSIM.BIGPROJECT_X32_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Blender 2017 Preview - Release - build" , groups = { "Build" })
    public void blender2015ReleaseBuild(){
        int returnCode = ibService.cleanAndBuild(StaticDataProvider.IbLocations.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC15Preview_VMSIM.BLENDER_X64_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }
}
