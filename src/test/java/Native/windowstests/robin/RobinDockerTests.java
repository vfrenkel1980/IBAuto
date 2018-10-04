package Native.windowstests.robin;

import frameworkInfra.testbases.RobinDockerTestBase;
import frameworkInfra.utils.StaticDataProvider;
import ibInfra.ibService.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RobinDockerTests extends RobinDockerTestBase {

    @Test(testName = "Verify Docker IB Build Updated")
    public void verifyDockerIBBuildUpdated(){
        if(ibVersion != IIBService.getIbVersion()) {
            winService.waitForProcessToFinish("BuildService.exe");
            winService.waitForProcessToStart("BuildService.exe");
        }
        Assert.assertTrue(ibVersion == IIBService.getIbVersion(),"IB build is not compatible");
    }

    @Test(testName = "Docker Mono - Debug - Build")
    public void dockerMonoDebugBuild() {
        int returnCode = ibService.cleanAndBuild(StaticDataProvider.IbLocations.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.DOCKER_ROBIN.MONO_X64_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Docker Audacity 2017 - Debug - build" , groups = { "Build" })
    public void dockerAudacityDebugBuild(){
        int returnCode = ibService.cleanAndBuild(StaticDataProvider.IbLocations.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.DOCKER_ROBIN.AUDACITY_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }
}
