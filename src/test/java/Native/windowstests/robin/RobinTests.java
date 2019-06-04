package Native.windowstests.robin;

import com.aventstack.extentreports.Status;
import frameworkInfra.testbases.RobinTestBase;
import frameworkInfra.utils.StaticDataProvider;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import static frameworkInfra.Listeners.SuiteListener.test;

public class RobinTests extends RobinTestBase {

    @Test(testName = "Chrome release - build" , groups = { "Build" })
    public void chromeReleaseBuild(){
        if (testName.equals("Minimal")){
            test.log(Status.SKIP, "Skipping Chrome test on Minimal logging");
            throw new SkipException("Skipped test");
        }
        winService.runCommandWaitForFinish(StaticDataProvider.ProjectsCommands.CHROME.CHROME_RELEASE_CLEAN);
        int returnCode = winService.runCommandWaitForFinish(StaticDataProvider.ProjectsCommands.CHROME.CHROME_RELEASE_BUILD);
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Audacity 2017 - Debug - build" , groups = { "Build" })
    public void audacity2017DebugBuild(){
        int returnCode = ibService.cleanAndBuild(StaticDataProvider.IbLocations.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC15_ROBIN.AUDACITY_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Blender 2017 - Release - build" , groups = { "Build" })
    public void blender2017ReleaseBuild(){
        int returnCode = ibService.cleanAndBuild(StaticDataProvider.IbLocations.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC15_ROBIN.BLENDER_X64_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "ACE 2017 - Debug - build" , groups = { "Build" })
    public void ace2017DebugBuild(){
        int returnCode = ibService.cleanAndBuild(StaticDataProvider.IbLocations.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC15_ROBIN.ACE_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Audacity 2019 - Debug - build" , groups = { "Build" })
    public void audacity2019DebugBuild(){
        int returnCode = ibService.cleanAndBuild(StaticDataProvider.IbLocations.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC16_ROBIN.AUDACITY_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Blender 2019 - Release - build" , groups = { "Build" })
    public void blender2019ReleaseBuild(){
        int returnCode = ibService.cleanAndBuild(StaticDataProvider.IbLocations.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC16_ROBIN.BLENDER_X64_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }
}
