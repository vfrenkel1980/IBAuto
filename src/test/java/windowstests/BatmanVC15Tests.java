package windowstests;

import frameworkInfra.testbases.BatmanBCTestBase;
import frameworkInfra.testbases.UnitTestBase;
import frameworkInfra.utils.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BatmanVC15Tests extends BatmanBCTestBase {

    @Test(testName = "Blender 2017 - Release - build" , groups = { "Build" })
    public void blender2017ReleaseBuild(){
        int returnCode = runCommand.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC15_BATMAN.BLENDER_X64_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Audacity 2017 - Debug - build" , groups = { "Build" })
    public void audacityDebugBuild(){
        int returnCode = runCommand.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC15_BATMAN.AUDACITY_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "ACE 2017 - Debug - build" , groups = { "Build" })
    public void ace2017DebugBuild(){
        int returnCode = runCommand.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC15_BATMAN.ACE_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Big Project 2017 - Debug - build" , groups = { "Build" })
    public void bigProjectDebugBuild(){
        int returnCode = runCommand.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC15_BATMAN.BIGPROJECT_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Big Project 2017 - Release - build" , groups = { "Build" })
    public void bigProjectReleaseBuild(){
        int returnCode = runCommand.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC15_BATMAN.BIGPROJECT_X32_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Chrome release - build" , groups = { "Build" })
    public void chromeReleaseBuild(){
        runCommand.runCommandWaitForFinish(StaticDataProvider.ProjectsCommands.CHROME_RELEASE_CLEAN);
        int returnCode = runCommand.cleanAndBuild(StaticDataProvider.ProjectsCommands.CHROME_RELEASE_BUILD);
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }


}
