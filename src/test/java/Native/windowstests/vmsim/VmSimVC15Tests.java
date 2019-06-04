package Native.windowstests.vmsim;

import frameworkInfra.testbases.VmSimTestBase;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.parsers.Parser;
import org.testng.Assert;
import org.testng.annotations.Test;

public class VmSimVC15Tests extends VmSimTestBase {

    @Test(testName = "Audacity 2017 - Debug - build" , groups = { "Build" })
    public void audacity2017DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_VMSIM.AUDACITY_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Big Project 2017 - Debug - build" , groups = { "Build" })
    public void bigProject2017DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_VMSIM.BIGPROJECT_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Big Project 2017 - Release - build" , groups = { "Build" })
    public void bigProject2017ReleaseBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_VMSIM.BIGPROJECT_X32_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Blender 2017 - Release - build" , groups = { "Build" })
    public void blender2017ReleaseBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_VMSIM.BLENDER_X64_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "WxWidgets 2017 - Debug x64- build" , groups = { "Build" })
    public void wxWidgets2017Debugx64build(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_VMSIM.WXWIDGETS_X64_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Check Win 10 Fastring Assignment")
    public void checkWin10FastringAssignment(){
        winService.runCommandWaitForFinish(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_VMSIM.AUDACITY_X32_DEBUG
                + " /out=" + Locations.OUTPUT_LOG_FILE + " /showagent /showcmd /showtime", ProjectsCommands.REBUILD));
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '" + WindowsMachines.WIN_INSIDER));
    }
}
