package Native.Consoles;

import frameworkInfra.testbases.WindowsTestBase;
import frameworkInfra.utils.StaticDataProvider.*;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @brief<b> <b>Nintendo Switch project tests</b>
 * @details VM Gameboy10: on Host-24
 */
public class NintendoSwitchTests extends WindowsTestBase {
    /**
     * @test Demo1 - Release|NX64 - build
     * @pre { }
     * @steps {- Build from console Demo1-spec.NX.autogen.vs2017.sln project}
     * @result {- Return number of code}
     */
    @Test(testName = "Demo1 - Release|NX64 - build" , groups = { "Build" })
    public void blender2015ReleaseBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.CONSOLES.DEMO1_NX64_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    /**
     * @test Application Account - Debug|NX64 - build
     * @pre { Disable Predictive execution option fom VS Builds -> Advanced
     *  - Set value X64 to ForcePredictedExecutionPlatforms parameter in the IB profile xml file }
     * @steps {- Build from console AccountApplication-spec.NX.autogen.vs2017.sln project}
     * @result {- Return number of code}
     */
    @Test(testName = "Application Account - Debug|NX64 - build", groups = {"Build"})
    public void applicationAccountDebugBuild() {
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.CONSOLES.ACCOUNT_APPLICATION_NX64_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }
}
