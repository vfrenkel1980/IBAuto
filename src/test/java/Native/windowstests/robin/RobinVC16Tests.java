package Native.windowstests.robin;

import com.aventstack.extentreports.Status;
import frameworkInfra.testbases.RobinTestBase;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.SystemActions;
import ibInfra.vs.VSUIService;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import static frameworkInfra.Listeners.SuiteListener.test;

public class RobinVC16Tests extends RobinTestBase {


    @Test(testName = "ACE 2019 - Debug - build" , groups = { "Build" })
    public void ace2019DebugBuild(){
        int returnCode = ibService.cleanAndBuild(StaticDataProvider.IbLocations.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC16_ROBIN.ACE_X32_DEBUG, "%s"));
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

    /**
     * @test Verify build in VS if specified PREfast plugin is Enable
     * @steps
     *  1. Use a machine with VS-2019 installed on.
     *  2. Create/Open a c++ project.
     *  3. Define in project properties Code Analysis:
     *  - General -> Enable Code Analysis on Build -> Yes.
     * - General -> Enable Microsoft Code Analysis -> Yes.
     * - Microsoft -> Active rules -> Microsoft All Rules.
     * @result Code
     */
    @Test(testName= "Enabling PREfast")
    public void enablingPREfast(){
        int returnCode = ibService.cleanAndBuild(StaticDataProvider.IbLocations.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC16_ROBIN.VC16PROJPREfast, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }
}
