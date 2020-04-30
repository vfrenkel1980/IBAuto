package Native.windowstests.batman;

import frameworkInfra.testbases.BatmanBCTestBase;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.parsers.Parser;
import frameworkInfra.utils.StaticDataProvider.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;

public class BatmanVC16PreviewTests extends BatmanBCTestBase {


    @Test(testName = "Audacity 2019 Preview - Debug - build" , groups = { "Build" })
    public void audacity2019DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC16Preview_BATMAN.AUDACITY_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "ACE 2019 Preview - Debug - build" , groups = { "Build" })
    public void ace2019DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC16Preview_BATMAN.ACE_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Big Project 2019 Preview - Debug - build" , groups = { "Build" })
    public void bigProject2019DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC16Preview_BATMAN.BIGPROJECT_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Big Project 2019 Preview - Release - build" , groups = { "Build" })
    public void bigProject2019ReleaseBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC16Preview_BATMAN.BIGPROJECT_X32_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Check if IBMSBHLP.log created")
    public void checkIBMSBHLPlogCreation(){
        ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC16Preview_BATMAN.AUDACITY_X32_DEBUG, "%s"));
        File ibmsbhlpLog = new File(IbLocations.LOGS_ROOT + "\\IBMSBHLP.log");
        Assert.assertFalse(ibmsbhlpLog.exists(), "IBMSBHLP.log file was created during the \"Predicted\" execution");
    }
    @Test(testName = "AccountApplication - 2019 Preview debug|NX32 - build" , groups = { "Build" })
    public void accountApplication2019DebugNX32Build(){
        setRegistry("0", RegistryKeys.PREDICTED);
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC16Preview_BATMAN.NINTENDO_AAA_NX32_DEBUG, "%s"));
        setRegistry("2", RegistryKeys.PREDICTED);
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);

    }

    @Test(testName = "AccountApplication - 2019 Preview debug|NX64 - build" , groups = { "Build" })
    public void accountApplication2019DebugNX64Build(){
        setRegistry("0", RegistryKeys.PREDICTED);
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC16Preview_BATMAN.NINTENDO_AAA_NX64_DEBUG, "%s"));
        setRegistry("2", RegistryKeys.PREDICTED);
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "AccountApplication - 2019 Preview release|NX32 - build" , groups = { "Build" })
    public void accountApplication2019ReleaseNX32Build(){
        setRegistry("0", RegistryKeys.PREDICTED);
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC16Preview_BATMAN.NINTENDO_AAA_NX32_RELEASE, "%s"));
        setRegistry("2", RegistryKeys.PREDICTED);
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }


    @Test(testName = "AccountApplication - 2019 Preview release|NX64 - build" , groups = { "Build" })
    public void accountApplication2019ReleaseNX64Build(){
        setRegistry("0", RegistryKeys.PREDICTED);
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC16Preview_BATMAN.NINTENDO_AAA_NX64_RELEASE, "%s"));
        setRegistry("2", RegistryKeys.PREDICTED);
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "NvnTutorial06 - 2019 Preview debug|NX32- build" , groups = { "Build" })
    public void nvnTutorial2019DebugNX32Build(){
        setRegistry("0", RegistryKeys.PREDICTED);
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC16Preview_BATMAN.NVNTUTORIAL_NX32_DEBUG, "%s"));
        setRegistry("2", RegistryKeys.PREDICTED);
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    /*-------------------------------------METHODS---------------------------------------------------------------*/
    private void setRegistry(String required, String keyName){
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\builder", keyName, required);
    }
}
