package Native.Consoles;

import frameworkInfra.testbases.WindowsTestBase;
import frameworkInfra.utils.StaticDataProvider.*;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @brief<b> <b>Nintendo Switch project tests</b>
 * @details vm: on Host-24
 */
public class NintendoSwitchTests extends WindowsTestBase {

    @Test(testName = "Demo1 - Release|NX64 - build" , groups = { "Build" })
    public void blender2015ReleaseBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.CONSOLES.DEMO1_NX64_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }


}
