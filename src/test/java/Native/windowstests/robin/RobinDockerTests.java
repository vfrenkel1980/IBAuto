package Native.windowstests.robin;

import frameworkInfra.testbases.RobinDockerTestBase;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.parsers.Parser;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RobinDockerTests extends RobinDockerTestBase {

    @Test(testName = "Docker Mono - Debug - Build" , groups = { "Build" })
    public void dockerMonoDebugBuild() {
        int returnCode = winService.runCommandWaitForFinish(DockerCommands.DOCKER_EXEC + DockerCommands.WIN10_DOC_CONTAINER + " " +
                IbLocations.BUILD_CONSOLE + ProjectsCommands.DOCKER_ROBIN.MONO_X64_DEBUG);
        winService.runCommandWaitForFinish("docker cp  " + DockerCommands.WIN10_DOC_CONTAINER + ":c:\\QA\\Simulation\\buildLog.txt  C:\\QA\\Simulation\\buildLog.txt\"");
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.AGENT), "No agents were assigned to the build");
    }

    @Test(testName = "Docker Audacity 2017 - Debug - build", groups = {"Build"})
    public void dockerAudacityDebugBuild() {
        int returnCode = winService.runCommandWaitForFinish(DockerCommands.DOCKER_EXEC + DockerCommands.WIN10_DOC_CONTAINER + " " +
                IbLocations.BUILD_CONSOLE + ProjectsCommands.DOCKER_ROBIN.AUDACITY_X32_DEBUG);
        winService.runCommandWaitForFinish("docker cp  " + DockerCommands.WIN10_DOC_CONTAINER + ":c:\\QA\\Simulation\\buildLog.txt  C:\\QA\\Simulation\\buildLog.txt\"");
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.AGENT), "No agents were assigned to the build");
    }
}