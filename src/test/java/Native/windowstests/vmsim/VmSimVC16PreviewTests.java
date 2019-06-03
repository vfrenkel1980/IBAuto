package Native.windowstests.vmsim;

import frameworkInfra.testbases.VmSimTestBase;
import frameworkInfra.utils.StaticDataProvider.*;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

public class VmSimVC16PreviewTests extends VmSimTestBase {
    @Ignore
    @Test(testName = "Audacity 2019 Preview - Debug - build" , groups = { "Build" })
    public void audacity2019PreviewDebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC16Preview_VMSIM.AUDACITY_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Big Project 2019 Preview - Debug - build" , groups = { "Build" })
    public void bigProject2019PreviewDebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC16Preview_VMSIM.BIGPROJECT_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Big Project 2019 Preview - Release - build" , groups = { "Build" })
    public void bigProject2019PreviewReleaseBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC16Preview_VMSIM.BIGPROJECT_X32_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }
    @Ignore
    @Test(testName = "Blender 2019 Preview - Release - build" , groups = { "Build" })
    public void blender2019PreviewReleaseBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC16Preview_VMSIM.BLENDER_X64_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }
}
