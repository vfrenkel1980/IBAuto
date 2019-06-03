package Native.windowstests.vmsim;

import frameworkInfra.testbases.VmSimTestBase;
import frameworkInfra.utils.StaticDataProvider.IbLocations;
import frameworkInfra.utils.StaticDataProvider.Locations;
import frameworkInfra.utils.StaticDataProvider.ProjectsCommands;
import frameworkInfra.utils.StaticDataProvider.WindowsMachines;
import frameworkInfra.utils.parsers.Parser;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

public class VmSimVC16Tests extends VmSimTestBase {

    @Ignore
    @Test(testName = "Audacity 2019 - Debug - build" , groups = { "Build" })
    public void audacity2019DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC16_VMSIM.AUDACITY_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Big Project 2019 - Debug - build" , groups = { "Build" })
    public void bigProject2019DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC16_VMSIM.BIGPROJECT_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Big Project 2019 - Release - build" , groups = { "Build" })
    public void bigProject2019ReleaseBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC16_VMSIM.BIGPROJECT_X32_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }
    @Ignore
    @Test(testName = "Blender 2019 - Release - build" , groups = { "Build" })
    public void blender2019ReleaseBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC16_VMSIM.BLENDER_X64_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "WxWidgets 2019 - Debug x64- build" , groups = { "Build" })
    public void wxWidgets2019Debugx64build(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC16_VMSIM.WXWIDGETS_X64_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Check Win 10 Fastring Assignment")
    public void checkWin10FastringAssignment(){
        winService.runCommandWaitForFinish(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC16_VMSIM.AUDACITY_X32_DEBUG
                + " /out=" + Locations.OUTPUT_LOG_FILE + " /showagent /showcmd /showtime", ProjectsCommands.REBUILD));
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '" + WindowsMachines.WIN_INSIDER));
    }
}
