package windowstests;

import frameworkInfra.testbases.BatmanBCTestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

import static frameworkInfra.utils.StaticDataProvider.Processes;
import static frameworkInfra.utils.StaticDataProvider.ProjectsCommands;

public class BatmanVC15PreviewTests extends BatmanBCTestBase {

    @Test(testName = "Blender 2017 Preview - Release - build" , groups = { "Build" })
    public void blender2017ReleaseBuild(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC15Preview_BATMAN.BLENDER_X64_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Audacity 2017 Preview - Debug - build" , groups = { "Build" })
    public void audacityDebugBuild(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC15Preview_BATMAN.AUDACITY_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "ACE 2017 Preview - Debug - build" , groups = { "Build" })
    public void ace2017DebugBuild(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC15Preview_BATMAN.ACE_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Big Project 2017 Preview - Debug - build" , groups = { "Build" })
    public void bigProjectDebugBuild(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC15Preview_BATMAN.BIGPROJECT_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Big Project 2017 Preview - Release - build" , groups = { "Build" })
    public void bigProjectReleaseBuild(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC15Preview_BATMAN.BIGPROJECT_X32_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

}
