package Native.windowstests.batman;

import com.aventstack.extentreports.Status;
import frameworkInfra.testbases.BatmanBCTestBase;
import frameworkInfra.utils.*;
import frameworkInfra.utils.parsers.Parser;
import frameworkInfra.utils.RegistryService;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static frameworkInfra.Listeners.SuiteListener.test;
import static frameworkInfra.utils.StaticDataProvider.*;
/**
 * @brief  <b>Coverage Samples of projects</b> tests on VS 2017 Professional</b>
 * @details Run on Batman
 */
public class BatmanVC15Tests extends BatmanBCTestBase {


    @Test(testName = "Audacity 2017 - Debug - build", groups = {"Build"})
    public void audacityDebugBuild() {
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.AUDACITY_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "ACE 2017 - Debug - build", groups = {"Build"})
    public void ace2017DebugBuild() {
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.ACE_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Big Project 2017 - Debug - build", groups = {"Build"})
    public void bigProjectDebugBuild() {
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.BIGPROJECT_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Big Project 2017 - Release - build", groups = {"Build"})
    public void bigProjectReleaseBuild() {
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.BIGPROJECT_X32_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

/*----------------Temporarily commented out Prospero tests----------------------*/
//    @Test(testName = "PS4 - 2017 Sample 1 release|Orbis SDK6 - build", groups = {"Build"})
//    public void ps42017Sample1ReleaseOrbisSDK6Build() {
//        changePSSDKVersionTo(OrbisSDK.PS4_SDK6);
//        int returnCode = ibService.cleanAndBuild(WindowsCommands.REFRESH_ENV_VARS + IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.PS4_SAMPLE1_ORBIS_RELEASE, "%s"));
//        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
//    }
//
//    @Test(testName = "PS4 - 2017 Sample 2 debug|Orbis SDK6 - build", groups = {"Build"})
//    public void ps42017Sample2DebugOrbisSDK6Build() {
//        changePSSDKVersionTo(OrbisSDK.PS4_SDK6);
//        int returnCode = ibService.cleanAndBuild(WindowsCommands.REFRESH_ENV_VARS + IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.PS4_SAMPLE2_ORBIS_DEBUG, "%s"));
//        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
//    }
//
//    @Test(testName = "PS4 - 2017 Sample 3 release|Orbis SDK6 - build", groups = {"Build"})
//    public void ps42017Sample3ReleaseOrbisSDK6Build() {
//        changePSSDKVersionTo(OrbisSDK.PS4_SDK6);
//        int returnCode = ibService.cleanAndBuild(WindowsCommands.REFRESH_ENV_VARS + IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.PS4_SAMPLE3_ORBIS_RELEASE, "%s"));
//        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
//    }
//
//    @Test(testName = "PS4 - 2017 Sample 4 debug|Orbis SDK6 - build", groups = {"Build"})
//    public void ps42017Sample4DebugOrbisSDK6Build() {
//        changePSSDKVersionTo(OrbisSDK.PS4_SDK6);
//        int returnCode = ibService.cleanAndBuild(WindowsCommands.REFRESH_ENV_VARS + IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.PS4_SAMPLE4_ORBIS_DEBUG, "%s"));
//        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
//    }
//
//    @Test(testName = "PS Prospero 0.900 - 2017 Sample 1 release - build", groups = {"Build"})
//    public void psProspero0900_2017Sample1ReleaseBuild() {
//        changePSSDKVersionTo(OrbisSDK.PS_PROSPERO_0900);
//        int returnCode = ibService.cleanAndBuild(WindowsCommands.REFRESH_ENV_VARS + IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.PS_PROSPERO_SAMPLE1_RELEASE, "%s"));
//        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
//    }
//
//    @Test(testName = "PS Prospero 0.900 - 2017 Sample 2 debug - build", groups = {"Build"})
//    public void psProspero0900_2017Sample2DebugBuild() {
//        changePSSDKVersionTo(OrbisSDK.PS_PROSPERO_0900);
//        int returnCode = ibService.cleanAndBuild(WindowsCommands.REFRESH_ENV_VARS + IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.PS_PROSPERO_SAMPLE2_DEBUG, "%s"));
//        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
//    }
//
//    @Test(testName = "PS Prospero 0.900 - 2017 Sample 3 release - build", groups = {"Build"})
//    public void psProspero0900_Sample3ReleaseBuild() {
//        changePSSDKVersionTo(OrbisSDK.PS_PROSPERO_0900);
//        int returnCode = ibService.cleanAndBuild(WindowsCommands.REFRESH_ENV_VARS + IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.PS_PROSPERO_SAMPLE3_RELEASE, "%s"));
//        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
//    }
//
//    @Test(testName = "PS Prospero 0.900 - 2017 Sample 4 debug - build", groups = {"Build"})
//    public void psProspero0900Sample4DebugBuild() {
//        changePSSDKVersionTo(OrbisSDK.PS_PROSPERO_0900);
//        int returnCode = ibService.cleanAndBuild(WindowsCommands.REFRESH_ENV_VARS + IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.PS_PROSPERO_SAMPLE4_DEBUG, "%s"));
//        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
//    }

    @Ignore
    @Test(testName = "Chrome release - build" , groups = { "Build" })
    public void chromeReleaseBuild(){
        if (testName.equals("Minimal")){
            test.log(Status.SKIP, "Skipping Chrome test on Minimal logging");
            throw new SkipException("Skipped test");
        }
        winService.runCommandWaitForFinish(ProjectsCommands.CHROME_BATMAN.CHROME_RELEASE_CLEAN);
        int returnCode = winService.runCommandWaitForFinish(ProjectsCommands.CHROME_BATMAN.CHROME_RELEASE_BUILD);
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    /**
     * @bug #10217 New "Chromium" Visual Studio 2017 solution doesn't compile with IncrediBuild*
     * @TODO Return to simulation after #10217 bug fix "support vs2017 cromuium solution
     */
    @Ignore
    @Test(testName = "Chrome X64 GN - build", groups = {"Build"})
    public void chromeX64GNBuild() {
        if (testName.equals("Minimal")) {
            test.log(Status.SKIP, "Skipping Chrome test on Minimal logging");
            throw new SkipException("Skipped test");
        }
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.CHROME_X64_GN, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Qt release - build", groups = {"Build"})
    public void qtReleaseBuild() {
        if (testName.equals("Minimal")) {
            test.log(Status.SKIP, "Skipping QT test on Minimal logging");
            throw new SkipException("Skipped test");
        }
        winService.runCommandWaitForFinish(ProjectsCommands.QT_BATMAN.QT_CLEAN);
        winService.runCommandWaitForFinish(ProjectsCommands.QT_BATMAN.QT_BUILD);
        String result = "";
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"), "Build failed");
        } catch (IOException e) {
            test.log(Status.WARNING, e.getMessage());
        }
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '"), "No agents were assigned to the build");
    }


    @Test(enabled = false, testName = "Android CPP - Debug - build", groups = {"Build"})
    public void androidCPPDebugBuild() {
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.ANDROIDCPP_ARM64_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }


    @Test(enabled = false, testName = "Android CS - Debug - build", groups = {"Build"})
    public void androidCSDebugBuild() {
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.ANDROIDCS_ANYCPU_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(enabled = false, testName = "Core CLR - Debug - build", groups = {"Build"}) //bug #10018
    public void coreCLRDebugBuild() {
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.CORECLR_X64_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Check that \"Predicted\" execution is enable: VS2017 Release")
    public void checkForPredictedExecutionWithoutMSBuild() {
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.AUDACITY_X32_DEBUG, "%s"));
        Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.PREDICTED_DISABLED));
    }

    @Test(testName = "Check if IBMSBHLP.log created")
    public void checkIBMSBHLPlogCreation() {
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.AUDACITY_X32_DEBUG, "%s"));
        File ibmsbhlpLog = new File(IbLocations.LOGS_ROOT + "\\IBMSBHLP.log");
        Assert.assertFalse(ibmsbhlpLog.exists(), "IBMSBHLP.log file was created during the \"Predicted\" execution");
    }

    @Test(testName = "Check Basic Agent Assignment")
    public void checkBasicAgentAssignment() {
        boolean pass = true;
        winService.runCommandWaitForFinish(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.AUDACITY_X32_DEBUG
                + " /out=" + StaticDataProvider.Locations.OUTPUT_LOG_FILE + " /showagent /showcmd /showtime", ProjectsCommands.REBUILD));
        for (String machine : batmanMachineList) {
            if (!Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '" + machine)) {
                test.log(Status.INFO, machine + " is not assigned to build");
                pass = false;
            }
        }
        Assert.assertTrue(pass, "Some machines from the Grid were not assigned to the build process");
    }

    @Test(testName = "Verify Build Groups")
    public void verifyBuildGroups() {
        boolean pass = true;
        winService.runCommandWaitForFinish(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.AUDACITY_X32_DEBUG
                + " /out=" + StaticDataProvider.Locations.OUTPUT_LOG_FILE + " /showagent /showcmd /showtime", ProjectsCommands.REBUILD));
        for (String machine : vmSimMachineList) {
            if (Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '" + machine)) {
                test.log(Status.INFO, machine + " is assigned to build");
                pass = false;
            }
        }
        Assert.assertTrue(pass, "Some machines from VMSIM Grid were assigned to the Batman build process");
    }

    /**
     * @test Verify Nintendo accountApplication project
     * @pre{ cfg="debug|NX32"}
     * @steps{
     * - Set Registry Key to PredictedExecutionMode 0
     * - Run clean and build accountApplication project on VS 2017
     * - Set Registry Key to PredictedExecutionMode 2
     * }
     * @result{ - Expected result return code 0 or 2 }
     */
    @Test(testName = "AccountApplication - 2017 debug|NX32 - build" , groups = { "Build" })
    public void accountApplication2017DebugNX32Build(){
        setRegistry("0", RegistryKeys.PREDICTED);
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.NINTENDO_AAA_NX32_DEBUG, "%s"));
        setRegistry("2", RegistryKeys.PREDICTED);
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);

    }

    /**
     * @test Verify Nintendo accountApplication project
     * @pre{ cfg="debug|NX64"}
     * @steps{
     * - Set Registry Key to PredictedExecutionMode 0
     * - Run clean and build accountApplication project on VS 2017
     * - Set Registry Key to PredictedExecutionMode 2
     * }
     * @result{ - Expected result return code 0 or 2 }
     */
    @Test(testName = "AccountApplication - 2017 debug|NX64 - build" , groups = { "Build" })
    public void accountApplication2017DebugNX64Build(){
        setRegistry("0", RegistryKeys.PREDICTED);
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.NINTENDO_AAA_NX64_DEBUG, "%s"));
        setRegistry("2", RegistryKeys.PREDICTED);
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    /**
     * @test Verify Nintendo accountApplication project
     * @pre{ cfg="release|NX32"}
     * @steps{
     * - Set Registry Key to PredictedExecutionMode 0
     * - Run clean and build accountApplication project on VS 2017
     * - Set Registry Key to PredictedExecutionMode 2
     * }
     * @result{ - Expected result return code 0 or 2 }
     */
    @Test(testName = "AccountApplication - 2017 release|NX32 - build" , groups = { "Build" })
    public void accountApplication2017ReleaseNX32Build(){
        setRegistry("0", RegistryKeys.PREDICTED);
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.NINTENDO_AAA_NX32_RELEASE, "%s"));
        setRegistry("2", RegistryKeys.PREDICTED);
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    /**
     * @test Verify Nintendo accountApplication project
     * @pre{ cfg="release|NX64"}
     * @steps{
     * - Set Registry Key to PredictedExecutionMode 0
     * - Run clean and build accountApplication project on VS 2017
     * - Set Registry Key to PredictedExecutionMode 2
     * }
     * @result{ - Expected result return code 0 or 2 }
     */
    @Test(testName = "AccountApplication - 2017 release|NX64 - build" , groups = { "Build" })
    public void accountApplication017ReleaseNX64Build(){
        setRegistry("0", RegistryKeys.PREDICTED);
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.NINTENDO_AAA_NX64_RELEASE, "%s"));
        setRegistry("2", RegistryKeys.PREDICTED);
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }
    // Not supported of Nintendo
//    @Test(testName = "AccountApplication - 2017 release|x64 - build" , groups = { "Build" })
//    public void accountApplication2017ReleaseX64Build(){
//        setRegistry("0", RegistryKeys.PREDICTED);
//        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.NINTENDO_AAA_X64_RELEASE, "%s"));
//        setRegistry("2", RegistryKeys.PREDICTED);
//        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
//    }
// Not supported of Nintendo
//    @Test(testName = "AccountApplication - 2017 debug|win32 - build" , groups = { "Build" })
//    public void accountApplication2017DebugX32Build(){
//        setRegistry("0", RegistryKeys.PREDICTED);
//        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.NINTENDO_AAA_X32_DEBUG, "%s"));
//        setRegistry("2", RegistryKeys.PREDICTED);
//        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
//    }
    /**
     * @test Verify Nintendo nvnTutorial project
     * @pre{ cfg="debug|NX32"}
     * @steps{
     * - Set Registry Key to PredictedExecutionMode 0
     * - Run clean and build nvnTutorial project on VS 2017
     * - Set Registry Key to PredictedExecutionMode 2
     * }
     * @result{ - Expected result return code 0 or 2 }
     */
    @Test(testName = "NvnTutorial06 - 2017 debug|NX32- build" , groups = { "Build" })
    public void nvnTutorial2017DebugNX32Build(){
        setRegistry("0", RegistryKeys.PREDICTED);
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.NVNTUTORIAL_NX32_DEBUG, "%s"));
        setRegistry("2", RegistryKeys.PREDICTED);
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    /**
     * @test Verify Xbox Simple Bezier
     * @pre{ cfg="Debug|Gaming.Xbox.Scarlett.x64"}
     * @steps{
     * - Run clean and build Xbox Simple Bezier on VS 2017
     * }
     * @result{ - Expected result return code 0 or 2 }
     */
    @Test(testName = "Xbox_IntroGraphics_SimpleBezier - 2017 debug - build" , groups = { "Build" })
    public void xboxSimpleBezier2017DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.XBOX_SIMPLEBEZIER, "%s"));
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
    @Test(testName = "Xbox_Graphics_SimpleESRAMR - 2017 debug - build" , groups = { "Build" })
    public void xboxSimpleESRAM2017DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.XBOX_SIMPLEESRAM, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    /**
     * @test Verify Xbox InGameChat2
     * @pre{ cfg="Debug|Gaming.Xbox.Scarlett.x64"}
     * @steps{
     * - Run clean and build Xbox Simple InGameChat2 on VS 2017
     * }
     * @result{ - Expected result return code 0 or 2 }
     */
    @Test(testName = "Xbox_Audio_InGameChat - 2017 debug - build" , groups = { "Build" })
    public void xboxInGameChat2017DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.XBOX_INGAMECHAT, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    /**
     * @test Verify Xbox Simple WinHttp
     * @pre{ cfg="Debug|Gaming.Xbox.Scarlett.x64"}
     * @steps{
     * - Run clean and build Xbox Simple WinHttp on VS 2017
     * }
     * @result{ - Expected result return code 0 or 2 }
     */
    @Test(testName = "Xbox_Live_SimpleWinHttp - 2017 debug - build" , groups = { "Build" })
    public void xboxSimpleWinHttp2017DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.XBOX_SIMPLEWINHTTP, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    /**
     * @test Verify Xbox Simple DirectStorageWin32
     * @pre{ cfg="Debug|Gaming.Desktop.x64"}
     * @steps{
     * - Run clean and build Xbox Simple DirectStorageWin32 on VS 2017
     * }
     * @result{ - Expected result return code 0 or 2 }
     */
    @Test(testName = "Xbox_System_SimpleDirectStorageWin32 - 2017 debug - build" , groups = { "Build" })
    public void xboxSimpleDirectStorageWin32_2017DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.XBOX_SIMPLEDIRECTSTORAGEWIN32, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    /**
     * @test Verify Xbox BWOIExample
     * @pre{ cfg="Debug|Gaming.Desktop.x64"}
     * @steps{
     * - Run clean and build Xbox BWOIExample on VS 2017
     * }
     * @result{ - Expected result return code 0 or 2 }
     */
    @Test(testName = "Xbox_Tools_BWOIExample - 2017 debug - build" , groups = { "Build" })
    public void xboxBWOIExample2017DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.XBOX_BWOIEXAMPLE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    /**
     * @test Verify Google Stadia Achievements C
     * @pre{ cfg="debug|GGP"}
     * @steps{
     * - Run clean and build Google Stadia Achievements C on VS 2017
     * }
     * @result{ - Expected result return code 0 or 2 }
     */
    @Test(testName = "Google Stadia - 2017 Achievements C debug|GGP SDK 1.45- build", groups = {"Build"})
    public void googleStadia2017AchievementsCBuild() {
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.GOOGLE_STADIA_ACHIEVEMENTS_C, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    /**
     * @test Verify Google Stadia Achievements CPP
     * @pre{ cfg="debug|GGP"}
     * @steps{
     * - Run clean and build Google Stadia Achievements CPP on VS 2017
     * }
     * @result{ - Expected result return code 0 or 2 }
     */
    @Test(testName = "Google Stadia - 2017 Achievements CPP debug|GGP SDK 1.45- build", groups = {"Build"})
    public void googleStadia2017AchievementsCPPBuild() {
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.GOOGLE_STADIA_ACHIEVEMENTS_CPP, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    /**
     * @test Verify Google Stadia Authentication C
     * @pre{ cfg="debug|GGP"}
     * @steps{
     * - Run clean and build Google Stadia Authentication C on VS 2017
     * }
     * @result{ - Expected result return code 0 or 2 }
     */
    @Test(testName = "Google Stadia - 2017 Authentication C debug|GGP SDK 1.45- build", groups = {"Build"})
    public void googleStadia2017AuthenticationCBuild() {
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.GOOGLE_STADIA_AUTHENTICATION_C, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    /**
     * @test Verify Google Stadia Authentication CPP
     * @pre{ cfg="debug|GGP"}
     * @steps{
     * - Run clean and build Google Stadia Authentication CPP on VS 2017
     * }
     * @result{ - Expected result return code 0 or 2 }
     */
    @Test(testName = "Google Stadia - 2017 Authentication CPP debug|GGP SDK 1.45- build", groups = {"Build"})
    public void googleStadia2017AuthenticationCPPBuild() {
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.GOOGLE_STADIA_AUTHENTICATION_CPP, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    /**
     * @test Verify Google Stadia Commerce C
     * @pre{ cfg="debug|GGP"}
     * @steps{
     * - Run clean and build Google Stadia Commerce C on VS 2017
     * }
     * @result{ - Expected result return code 0 or 2 }
     */
    @Test(testName = "Google Stadia - 2017 Commerce C debug|GGP SDK 1.45- build", groups = {"Build"})
    public void googleStadia2017CommerceCBuild() {
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.GOOGLE_STADIA_COMMERCE_C, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    /**
     * @test Verify Google Stadia Commerce CPP
     * @pre{ cfg="debug|GGP"}
     * @steps{
     * - Run clean and build Google Stadia Commerce CPP on VS 2017
     * }
     * @result{ - Expected result return code 0 or 2 }
     */
    @Test(testName = "Google Stadia - 2017 Commerce CPP debug|GGP SDK 1.45- build", groups = {"Build"})
    public void googleStadia2017CommerceCPPBuild() {
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.GOOGLE_STADIA_COMMERCE_CPP, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }
    /*-------------------------------------METHODS---------------------------------------------------------------*/
    private void changePSSDKVersionTo(String SDKVersion) {
        winService.runCommandWaitForFinish(winService.changeCurDirTo(OrbisSDK.SDK_INSTALLER_FOLDER) + String.format(OrbisSDK.SWITCH_PS_SDK, SDKVersion));
    }
    private void setRegistry(String required, String keyName){
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\builder", keyName, required);
    }

}//end of class
