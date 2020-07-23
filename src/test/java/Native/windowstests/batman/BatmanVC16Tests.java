package Native.windowstests.batman;

import frameworkInfra.testbases.BatmanBCTestBase;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.parsers.Parser;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static frameworkInfra.utils.StaticDataProvider.*;
/**
 * @brief  <b>Coverage Samples of projects</b> tests on VS 2019 Professional</b>
 * @details Run on Batman
 */
public class BatmanVC16Tests extends BatmanBCTestBase {


    @Test(testName = "Audacity 2019 - Debug - build", groups = {"Build"})
    public void audacity2019DebugBuild() {
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC16_BATMAN.AUDACITY_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "ACE 2019 - Debug - build", groups = {"Build"})
    public void ace2019DebugBuild() {
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC16_BATMAN.ACE_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Big Project 2019 - Debug - build", groups = {"Build"})
    public void bigProject2019DebugBuild() {
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC16_BATMAN.BIGPROJECT_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Big Project 2019 - Release - build", groups = {"Build"})
    public void bigProject2019ReleaseBuild() {
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC16_BATMAN.BIGPROJECT_X32_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Check that \"Predicted\" execution is enable: VS2019 Release")
    public void checkForPredictedExecutionWithoutMSBuild() {
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC16_BATMAN.AUDACITY_X32_DEBUG, "%s"));
        Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.PREDICTED_DISABLED));
    }
    @Test(testName = "AccountApplication - 2019  debug|NX32 - build" , groups = { "Build" })
    public void accountApplication2019DebugNX32Build(){
        setRegistry("0", RegistryKeys.PREDICTED);
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC16Preview_BATMAN.NINTENDO_AAA_NX32_DEBUG, "%s"));
        setRegistry("2", RegistryKeys.PREDICTED);
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);

    }

    @Test(testName = "AccountApplication - 2019  debug|NX64 - build" , groups = { "Build" })
    public void accountApplication2019DebugNX64Build(){
        setRegistry("0", RegistryKeys.PREDICTED);
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC16Preview_BATMAN.NINTENDO_AAA_NX64_DEBUG, "%s"));
        setRegistry("2", RegistryKeys.PREDICTED);
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "AccountApplication - 2019  release|NX32 - build" , groups = { "Build" })
    public void accountApplication2019ReleaseNX32Build(){
        setRegistry("0", RegistryKeys.PREDICTED);
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC16Preview_BATMAN.NINTENDO_AAA_NX32_RELEASE, "%s"));
        setRegistry("2", RegistryKeys.PREDICTED);
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }


    @Test(testName = "AccountApplication - 2019  release|NX64 - build" , groups = { "Build" })
    public void accountApplication2019ReleaseNX64Build(){
        setRegistry("0", RegistryKeys.PREDICTED);
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC16Preview_BATMAN.NINTENDO_AAA_NX64_RELEASE, "%s"));
        setRegistry("2", RegistryKeys.PREDICTED);
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    /**
     * @test Verify Nintendo nvnTutorial project
     * @pre{ cfg="debug|NX32"}
     * @steps{
     * - Set Registry Key to PredictedExecutionMode 0
     * - Run clean and build nvnTutorial project on VS 2019
     * - Set Registry Key to PredictedExecutionMode 2
     * }
     * @result{ - Expected result return code 0 or 2 }
     */
    @Test(testName = "NvnTutorial06 - 2019  debug|NX32- build" , groups = { "Build" })
    public void nvnTutorial2019DebugNX32Build(){
        setRegistry("0", RegistryKeys.PREDICTED);
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC16Preview_BATMAN.NVNTUTORIAL_NX32_DEBUG, "%s"));
        setRegistry("2", RegistryKeys.PREDICTED);
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    /**
     * @test Verify Xbox Simple Bezier
     * @pre{ cfg="Debug|Gaming.Xbox.Scarlett.x64 }
     * @steps{
     * - Run clean and build Simple Bezier project on VS 2019
     * }
     * @result{ - Return code 0 or 2 }
     */
    @Test(testName = "Xbox_IntroGraphics_SimpleBezier - 2019 debug - build" , groups = { "Build" })
    public void xboxSimpleBezier2019DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC16_BATMAN.XBOX_SIMPLEBEZIER, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    /**
     * @test Verify Xbox Simple ESRAM
     * @pre{ cfg="Debug|Gaming.Xbox.XboxOne.x64"}
     * @steps{
     * - Run clean and build Xbox Simple ESRAM on VS 2019
     * }
     * @result{ - Expected result return code 0 or 2 }
     */
    @Test(testName = "Xbox_Graphics_SimpleESRAMR - 2019 debug - build" , groups = { "Build" })
    public void xboxSimpleESRAM2019DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC16_BATMAN.XBOX_SIMPLEESRAM, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    /**
     * @test Verify Xbox In Game Chat2
     * @pre{ cfg="Debug|Gaming.Xbox.Scarlett.x64"}
     * @steps{
     * - Run clean and build Xbox Simple InGameChat2 on VS 2019
     * }
     * @result{ - Expected result return code 0 or 2 }
     */
    @Test(testName = "Xbox_Audio_InGameChat - 2019 debug - build" , groups = { "Build" })
    public void xboxInGameChat2019DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC16_BATMAN.XBOX_INGAMECHAT, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    /**
     * @test Verify Xbox Simple WinHttp
     * @pre{ cfg="Debug|Gaming.Xbox.Scarlett.x64"}
     * @steps{
     * - Run clean and build Xbox Simple WinHttp on VS 2019
     * }
     * @result{ - Expected result return code 0 or 2 }
     */
    @Test(testName = "Xbox_Live_SimpleWinHttp - 2019 debug - build" , groups = { "Build" })
    public void xboxSimpleWinHttp2019DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC16_BATMAN.XBOX_SIMPLEWINHTTP, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    /**
     * @test Verify Xbox Simple DirectStorageWin32
     * @pre{ cfg="Debug|Gaming.Desktop.x64"}
     * @steps{
     * - Run clean and build Xbox Simple DirectStorageWin32 on VS 2019
     * }
     * @result{ - Expected result return code 0 or 2 }
     */
    @Test(testName = "Xbox_System_SimpleDirectStorageWin32 - 2019 debug - build" , groups = { "Build" })
    public void xboxSimpleDirectStorageWin32_2019DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC16_BATMAN.XBOX_SIMPLEDIRECTSTORAGEWIN32, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    /**
     * @test Verify Xbox BWOIExample
     * @pre{ cfg="Debug|Gaming.Desktop.x64"}
     * @steps{
     * - Run clean and build Xbox BWOIExample on VS 2019
     * }
     * @result{ - Expected result return code 0 or 2 }
     */
    @Test(testName = "Xbox_Tools_BWOIExample - 2019 debug - build" , groups = { "Build" })
    public void xboxBWOIExample2019DebugBuild(){
        setRegistry("0", RegistryKeys.PREDICTED);
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC16_BATMAN.XBOX_BWOIEXAMPLE, "%s"));
        setRegistry("2", RegistryKeys.PREDICTED);
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    /*-------------------------------------METHODS---------------------------------------------------------------*/
    private void setRegistry(String required, String keyName){
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\builder", keyName, required);
    }
}//end of class
