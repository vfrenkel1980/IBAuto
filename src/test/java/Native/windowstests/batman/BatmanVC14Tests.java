package Native.windowstests.batman;

import frameworkInfra.testbases.BatmanBCTestBase;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.parsers.Parser;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static frameworkInfra.utils.StaticDataProvider.*;

public class BatmanVC14Tests extends BatmanBCTestBase {



    @Test(testName = "Blender 2015 - Release - build" , groups = { "Build" })
    public void blender2015ReleaseBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC14_BATMAN.BLENDER_X64_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "ACE 2015 - Debug - build" , groups = { "Build" })
    public void ace2015DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC14_BATMAN.ACE_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Mono 2015 - Debug Win32 - build" , groups = { "Build" })
    public void mono2015DebugX32Build(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC14_BATMAN.MONO_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Mono 2015 - Debug x64 - build" , groups = { "Build" })
    public void mono2015DebugX64Build(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC14_BATMAN.MONO_X64_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Mono 2015 - Release Win32 - build" , groups = { "Build" })
    public void mono2015ReleaseX32Build(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC14_BATMAN.MONO_X32_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Mono 2015 - Release x64 - build" , groups = { "Build" })
    public void mono2015ReleaseX64Build(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC14_BATMAN.MONO_X64_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "AccountApplicationAuthorization - 2015 debug|NX32 - build" , groups = { "Build" })
    public void accountApplicationAuthorization2015DebugNX32Build(){
        setRegistry("0", RegistryKeys.PREDICTED);
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC14_BATMAN.NINTENDO_AAA_NX32_DEBUG, "%s"));
        setRegistry("2", RegistryKeys.PREDICTED);
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);

    }

    @Test(testName = "AccountApplicationAuthorization - 2015 debug|NX64 - build" , groups = { "Build" })
    public void accountApplicationAuthorization2015DebugNX64Build(){
        setRegistry("0", RegistryKeys.PREDICTED);
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC14_BATMAN.NINTENDO_AAA_NX64_DEBUG, "%s"));
        setRegistry("2", RegistryKeys.PREDICTED);
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "AccountApplicationAuthorization - 2015 release|NX32 - build" , groups = { "Build" })
    public void accountApplicationAuthorization2015ReleaseNX32Build(){
        setRegistry("0", RegistryKeys.PREDICTED);
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC14_BATMAN.NINTENDO_AAA_NX32_RELEASE, "%s"));
        setRegistry("2", RegistryKeys.PREDICTED);
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "AccountApplicationAuthorization - 2015 release|NX64 - build" , groups = { "Build" })
    public void accountApplicationAuthorization2015ReleaseNX64Build(){
        setRegistry("0", RegistryKeys.PREDICTED);
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC14_BATMAN.NINTENDO_AAA_NX64_RELEASE, "%s"));
        setRegistry("2", RegistryKeys.PREDICTED);
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "AccountApplicationAuthorization - 2015 release|x64 - build" , groups = { "Build" })
    public void accountApplicationAuthorization2015ReleaseX64Build(){
        setRegistry("0", RegistryKeys.PREDICTED);
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC14_BATMAN.NINTENDO_AAA_X64_RELEASE, "%s"));
        setRegistry("2", RegistryKeys.PREDICTED);
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "AccountApplicationAuthorization - 2015 debug|win32 - build" , groups = { "Build" })
    public void accountApplicationAuthorization2015DebugX32Build(){
        setRegistry("0", RegistryKeys.PREDICTED);
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC14_BATMAN.NINTENDO_AAA_X32_DEBUG, "%s"));
        setRegistry("2", RegistryKeys.PREDICTED);
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "NvnTutorial06 - 2015 debug|NX32- build" , groups = { "Build" })
    public void nvnTutorial2015DebugNX32Build(){
        setRegistry("0", RegistryKeys.PREDICTED);
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC14_BATMAN.NVNTUTORIAL_NX32_DEBUG, "%s"));
        setRegistry("2", RegistryKeys.PREDICTED);
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "NvnTutorial06 - 2015 debug|NX64 - build" , groups = { "Build" })
    public void nvnTutorial2015DebugNX64Build(){
        setRegistry("0", RegistryKeys.PREDICTED);
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC14_BATMAN.NVNTUTORIAL_NX64_DEBUG, "%s"));
        setRegistry("2", RegistryKeys.PREDICTED);
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "NvnTutorial06 - 2015 release|NX32 - build" , groups = { "Build" })
    public void nvnTutorial2015ReleaseNX32Build(){
        setRegistry("0", RegistryKeys.PREDICTED);
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC14_BATMAN.NVNTUTORIAL_NX32_RELEASE, "%s"));
        setRegistry("2", RegistryKeys.PREDICTED);
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "NvnTutorial06 - 2015 release|NX64 - build" , groups = { "Build" })
    public void nvnTutorial015ReleaseNX64Build(){
        setRegistry("0", RegistryKeys.PREDICTED);
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC14_BATMAN.NVNTUTORIAL_NX64_RELEASE, "%s"));
        setRegistry("2", RegistryKeys.PREDICTED);
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "NvnTutorial06 - 2015 debug|win32 - build" , groups = { "Build" })
    public void nvnTutorial2015DebugX32Build(){
        setRegistry("0", RegistryKeys.PREDICTED);
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC14_BATMAN.NVNTUTORIAL_X32_DEBUG, "%s"));
        setRegistry("2", RegistryKeys.PREDICTED);
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "NvnTutorial06 - 2015 release|x64 - build" , groups = { "Build" })
    public void nvnTutorial2015ReleaseX32Build(){
        setRegistry("0", RegistryKeys.PREDICTED);
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC14_BATMAN.NVNTUTORIAL_X64_RELEASE, "%s"));
        setRegistry("2", RegistryKeys.PREDICTED);
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Sahdowmap - 2015 debug|Durango - build" , groups = { "Build" })
    public void shadowmap2015DebugDurangoBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC14_BATMAN.SHADOWMAP_DEBUG_DURANGO, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Sahdowmap - 2015 release|Durango - build" , groups = { "Build" })
    public void shadowmap2015ReleaseDurangoBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC14_BATMAN.SHADOWMAP_RELEASE_DURANGO, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Xbox_One_Mix - 2015 debug|Durango - build" , groups = { "Build" })
    @Ignore
    public void xboxOneMix2015DebugDurangoBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC14_BATMAN.XBOX_ONE_MIX_DEBUG_DURANGO, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Xbox_One_Mix - 2015 release|Durango - build" , groups = { "Build" })
    @Ignore
    public void xboxOneMix2015ReleaseDurangoBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC14_BATMAN.XBOX_ONE_MIX_RELEASE_DURANGO, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Big Project 2015 - Debug - build" , groups = { "Build" })
    public void bigProject2015DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC14_BATMAN.BIGPROJECT_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Big Project 2015 - Release - build" , groups = { "Build" })
    public void bigProject2015ReleaseBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC14_BATMAN.BIGPROJECT_X32_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "PS4 - 2015 Sample 1 debug|Orbis SDK5 - build", groups = {"Build"})
    public void ps42015Sample1DebugOrbisSDK4Build() {
        changePSSDKVersionTo(OrbisSDK.PS4_SDK5);
        int returnCode = ibService.cleanAndBuild(WindowsCommands.REFRESH_ENV_VARS + IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC14_BATMAN.PS4_SAMPLE1_ORBIS_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "PS4 - 2015 Sample 2 release|Orbis SDK5 - build", groups = {"Build"})
    public void ps42015Sample2ReleaseOrbisSDK4Build() {
        changePSSDKVersionTo(OrbisSDK.PS4_SDK5);
        int returnCode = ibService.cleanAndBuild(WindowsCommands.REFRESH_ENV_VARS + IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC14_BATMAN.PS4_SAMPLE2_ORBIS_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "PS4 - 2015 Sample 3 debug|Orbis SDK5 - build", groups = {"Build"})
    public void ps42015Sample3DebugOrbisSDK4Build() {
        changePSSDKVersionTo(OrbisSDK.PS4_SDK5);
        int returnCode = ibService.cleanAndBuild(WindowsCommands.REFRESH_ENV_VARS + IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC14_BATMAN.PS4_SAMPLE3_ORBIS_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "PS4 - 2015 Sample 4 release|Orbis SDK5 - build", groups = {"Build"})
    public void ps42015Sample4ReleaseOrbisSDK4Build() {
        changePSSDKVersionTo(OrbisSDK.PS4_SDK5);
        int returnCode = ibService.cleanAndBuild(WindowsCommands.REFRESH_ENV_VARS + IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC14_BATMAN.PS4_SAMPLE4_ORBIS_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Google Stadia - 2015 Sample 1 release|GGP SDK - build", groups = {"Build"})
    public void googleStadia20Sample1releaseGGPSDK129Build() {
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC14_BATMAN.GOOGLE_STADIA_SAMPLE1_GGP_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Google Stadia - 2015 Sample 2 debug|GGP SDK - build", groups = {"Build"})
    public void googleStadia20Sample2DebugGGPSDK129Build() {
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC14_BATMAN.GOOGLE_STADIA_SAMPLE2_GGP_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Google Stadia - 2015 Sample 3 release|GGP SDK - build", groups = {"Build"})
    public void googleStadia20Sample3releaseGGPSDK129Build() {
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC14_BATMAN.GOOGLE_STADIA_SAMPLE3_GGP_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Google Stadia - 2015 Sample 4 debug|GGP SDK - build", groups = {"Build"})
    public void googleStadia20Sample4DebugGGPSDK129Build() {
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC14_BATMAN.GOOGLE_STADIA_SAMPLE4_GGP_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

        /*------------------------------METHODS------------------------------*/

    private void setRegistry(String required, String keyName){
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\builder", keyName, required);
    }

    public void changePSSDKVersionTo(String SDKVersion) {
        winService.runCommandWaitForFinish(winService.changeCurDirTo(OrbisSDK.SDK_INSTALLER_FOLDER) + String.format(OrbisSDK.SWITCH_PS_SDK, SDKVersion));
    }
}
