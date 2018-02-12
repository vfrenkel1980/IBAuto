package windowstests;

import frameworkInfra.testbases.BatmanBCTestBase;
import frameworkInfra.utils.StaticDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;
import static frameworkInfra.utils.StaticDataProvider.*;

public class BatmanVC11Tests extends BatmanBCTestBase {


    @Test(testName = "ACE 2012 - Release - build" , groups = { "Build" })
    public void ace2012ReleaseBuild(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC11_BATMAN.ACE_X32_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "PS4 EDGE 2012 - debug|Orbis - build" , groups = { "Build" })
    public void ps4Edge2012DebugOrbisBuild(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC11_BATMAN.PS4_EDGE_ORBIS_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "PS4 EDGE 2012 - release|Orbis - build" , groups = { "Build" })
    public void ps4Edge2012ReleaseOrbisBuild(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC11_BATMAN.PS4_EDGE_ORBIS_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "PS4 EDGE 2012 - profile|Orbis - build" , groups = { "Build" })
    public void ps4Edge2012ProfileOrbisBuild(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC11_BATMAN.PS4_EDGE_ORBIS_PROFILE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "PS4 GNM 2012 - debug|Orbis - build" , groups = { "Build" })
    public void ps4GNM2012DebugOrbisBuild(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC11_BATMAN.PS4_GNM_ORBIS_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "PS4 GNM 2012 - release|Orbis - build" , groups = { "Build" })
    public void ps4GNM2012ReleaseOrbisBuild(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC11_BATMAN.PS4_GNM_ORBIS_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Sahdowmap - 2012 debug|Durango - build" , groups = { "Build" })
    public void shadowmap2012DebugDurangoBuild(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC11_BATMAN.SHADOWMAP_DEBUG_DURANGO, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Sahdowmap - 2012 release|Durango - build" , groups = { "Build" })
    public void shadowmap2012ReleaseDurangoBuild(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC11_BATMAN.SHADOWMAP_RELEASE_DURANGO, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

}
