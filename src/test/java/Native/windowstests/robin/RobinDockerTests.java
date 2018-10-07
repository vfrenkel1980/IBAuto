package Native.windowstests.robin;

import frameworkInfra.testbases.RobinDockerTestBase;
import frameworkInfra.utils.StaticDataProvider.*;
import ibInfra.ibService.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RobinDockerTests extends RobinDockerTestBase {

    @Test(testName = "Docker Mono - Debug - Build")
    public void dockerMonoDebugBuild() {
        int returnCode = winService.runCommandWaitForFinish(DockerCommands.WIN10_DOC_CONTAINER_EXE + IbLocations.BUILD_CONSOLE + ProjectsCommands.DOCKER_ROBIN.MONO_X64_DEBUG);
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Docker Audacity 2017 - Debug - build", groups = {"Build"})
    public void dockerAudacityDebugBuild() {
        int returnCode = winService.runCommandWaitForFinish(DockerCommands.WIN10_DOC_CONTAINER_EXE + IbLocations.BUILD_CONSOLE + ProjectsCommands.DOCKER_ROBIN.AUDACITY_X32_DEBUG);
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }
}
