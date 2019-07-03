package Native.windowstests.batman;

import frameworkInfra.testbases.BatmanBCTestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

import static frameworkInfra.utils.StaticDataProvider.*;

public class BatmanVC11Tests extends BatmanBCTestBase {


    @Test(testName = "ACE 2012 - Release - build", groups = {"Build"})
    public void ace2012ReleaseBuild() {
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC11_BATMAN.ACE_X32_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Sahdowmap - 2012 debug|Durango - build", groups = {"Build"})
    public void shadowmap2012DebugDurangoBuild() {
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC11_BATMAN.SHADOWMAP_DEBUG_DURANGO, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Sahdowmap - 2012 release|Durango - build", groups = {"Build"})
    public void shadowmap2012ReleaseDurangoBuild() {
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC11_BATMAN.SHADOWMAP_RELEASE_DURANGO, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    /*-----------------------------METHODS---------------------------------------*/

    public void changePSSDKVersionTo(String SDKVersion) {
        winService.runCommandWaitForFinish(winService.changeCurDirTo(OrbisSDK.SDK_INSTALLER_FOLDER) + String.format(OrbisSDK.SWITCH_PS_SDK, SDKVersion));
    }

}
