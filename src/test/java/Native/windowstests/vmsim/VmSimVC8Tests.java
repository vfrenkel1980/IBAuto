package Native.windowstests.vmsim;

import frameworkInfra.testbases.VmSimTestBase;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import static frameworkInfra.utils.StaticDataProvider.*;

public class VmSimVC8Tests extends VmSimTestBase {

    @Test(testName = "Notepad Plus 2005 - Debug x32 - build" , groups = { "Build" })
    public void notepadPlus2005DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC8_VMSIN.NONTEPAD_PLUS_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "VC8 Test project 2005 - Debug Mixed - build" , groups = { "Build" })
    public void vc8Test2005DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC8_VMSIN.VC8_TEST_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Ignore
    @Test(testName = "MIDL Interfaces 2005 - Debug x32 - build" , groups = { "Build" })
    public void midlInterfaces2005DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC8_VMSIN.MIDL_INTERFACES_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "VC8 multiconfig 2005 - Debug x32 - build" , groups = { "Build" })
    public void vc8Multiconfig2005DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC8_VMSIN.VC8_MULTUCONFIG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "VC8 Ogre 2005 - Debug x32 - build" , groups = { "Build" }, enabled = false)
    public void vc8Ogre2005DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC8_VMSIN.VC8_OGRE_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Loader Test 1 2005 - Debug x32 - build" , groups = { "Build" })
    public void loaderTest12005DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC8_VMSIN.LOADER_TEST_1_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Loader Test 2 2005 - Debug x32 - build" , groups = { "Build" })
    public void loaderTest22005DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC8_VMSIN.LOADER_TEST_2_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Loader Test 3 2005 - Debug x32 - build" , groups = { "Build" })
    public void loaderTest32005DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC8_VMSIN.LOADER_TEST_3_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Loader Test 4 2005 - Debug x32 - build" , groups = { "Build" })
    public void loaderTest42005DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC8_VMSIN.LOADER_TEST_4_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Loader Test 5 2005 - Debug x32 - build" , groups = { "Build" })
    public void loaderTest52005DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC8_VMSIN.LOADER_TEST_5_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Loader Test 6 2005 - Debug x32 - build" , groups = { "Build" })
    public void loaderTest62005DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC8_VMSIN.LOADER_TEST_6_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Loader Test 8 2005 - Debug x32 - build" , groups = { "Build" })
    public void loaderTest82005DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC8_VMSIN.LOADER_TEST_8_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Loader Test 9 2005 - Debug x32 - build" , groups = { "Build" })
    public void loaderTest92005DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC8_VMSIN.LOADER_TEST_9_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Loader Test 10 2005 - Debug x32 - build" , groups = { "Build" })
    public void loaderTest102005DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC8_VMSIN.LOADER_TEST_10_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Loader Test 11 2005 - Debug x32 - build" , groups = { "Build" })
    public void loaderTest112005DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC8_VMSIN.LOADER_TEST_11_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Loader Test 12 2005 - Debug x32 - build" , groups = { "Build" })
    public void loaderTest122005DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC8_VMSIN.LOADER_TEST_12_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Loader Test 13 2005 - Debug x32 - build" , groups = { "Build" })
    public void loaderTest132005DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC8_VMSIN.LOADER_TEST_13_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Loader Test 14 2005 - Debug x32 - build" , groups = { "Build" })
    public void loaderTest142005DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC8_VMSIN.LOADER_TEST_14_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Loader Test 15 2005 - Debug x32 - build" , groups = { "Build" })
    public void loaderTest152005DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC8_VMSIN.LOADER_TEST_15_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Loader Test 16 2005 - Debug x32 - build" , groups = { "Build" })
    public void loaderTest162005DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC8_VMSIN.LOADER_TEST_16_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Loader Test 18 2005 - Debug x32 - build" , groups = { "Build" })
    public void loaderTest182005DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC8_VMSIN.LOADER_TEST_18_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Prop Test 2005 - Debug x32 - build" , groups = { "Build" })
    public void propTest2005DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC8_VMSIN.PROP_TEST_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Env Vars 2005 - Debug x32 - build" , groups = { "Build" })
    public void envVars2005DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC8_VMSIN.ENV_VARS_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "PropInheritance2 2005 - Debug x32 - build" , groups = { "Build" })
    public void propInheritance22005DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC8_VMSIN.PROP_INHERITANCE_2_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "PropInheritance4 2005 - Debug x32 - build" , groups = { "Build" })
    public void propInheritance42005DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC8_VMSIN.PROP_INHERITANCE_4_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "PropInheritance5 2005 - Debug x32 - build" , groups = { "Build" })
    public void propInheritance52005DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC8_VMSIN.PROP_INHERITANCE_5_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "ReferencesMacro 2005 - Debug x32 - build" , groups = { "Build" })
    public void referencesMacro2005DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC8_VMSIN.REFERENCES_MACRO_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Info Path Bug 2005 - Debug x32 - build" , groups = { "Build" })
    public void infoPathBug2005DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC8_VMSIN.INPUT_PATH_BUG_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Invalid #define directive syntax - Debug x32 - build" , groups = { "Build" })
    public void invalidDefineDirectiveSyntax2005DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC8_VMSIN.INVALID_DEFINE_SYNTAX_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Loader Test 1 2005 - Release x32 - build" , groups = { "Build" })
    public void loaderTest12005ReleaseBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC8_VMSIN.LOADER_TEST_1_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Loader Test 2 2005 - Release x32 - build" , groups = { "Build" })
    public void loaderTest22005ReleaseBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC8_VMSIN.LOADER_TEST_2_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Loader Test 3 2005 - Release x32 - build" , groups = { "Build" })
    public void loaderTest32005ReleaseBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC8_VMSIN.LOADER_TEST_3_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Prop Test 2005 - Release x32 - build" , groups = { "Build" })
    public void propTest2005ReleaseBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC8_VMSIN.PROP_TEST_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Env Vars 2005 - Release x32 - build" , groups = { "Build" })
    public void envVars2005ReleaseBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC8_VMSIN.ENV_VARS_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "PropInheritance1 2005 - Batch" , groups = { "Build" })
    public void propInheritance12005Batch(){
        winService.runCommandWaitForFinish(ProjectsCommands.VC8_VMSIN.PROP_INHERITANCE_1_BATCH + Locations.QA_ROOT + " " + ProjectsCommands.CLEAN);
        int returnCode = winService.runCommandWaitForFinish(ProjectsCommands.VC8_VMSIN.PROP_INHERITANCE_1_BATCH + Locations.QA_ROOT + " " + ProjectsCommands.BUILD);
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "PropInheritance2 2005 - Release x32 - build" , groups = { "Build" })
    public void propInheritance22005ReleaseBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC8_VMSIN.PROP_INHERITANCE_2_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "PropInheritance3 2005 - Batch" , groups = { "Build" })
    public void propInheritance32005Batch(){
        winService.runCommandWaitForFinish(ProjectsCommands.VC8_VMSIN.PROP_INHERITANCE_3_BATCH + Locations.QA_ROOT + " " + ProjectsCommands.CLEAN);
        int returnCode = winService.runCommandWaitForFinish(ProjectsCommands.VC8_VMSIN.PROP_INHERITANCE_3_BATCH + Locations.QA_ROOT + " " + ProjectsCommands.BUILD);
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "PropInheritance4 2005 - Batch" , groups = { "Build" })
    public void propInheritance42005Batch(){
        winService.runCommandWaitForFinish(ProjectsCommands.VC8_VMSIN.PROP_INHERITANCE_4_BATCH + Locations.QA_ROOT + " " + ProjectsCommands.CLEAN);
        int returnCode = winService.runCommandWaitForFinish(ProjectsCommands.VC8_VMSIN.PROP_INHERITANCE_4_BATCH + Locations.QA_ROOT + " " + ProjectsCommands.BUILD);
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "ReferencesMacro 2005 - Release x32 - build" , groups = { "Build" })
    public void referencesMacro2005ReleaseBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC8_VMSIN.REFERENCES_MACRO_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Ace 2005 Batch" , groups = { "Build" })
    public void ace2005Batch(){
        winService.runCommandWaitForFinish(ProjectsCommands.VC8_VMSIN.ACE_BATCH + Locations.QA_ROOT + " " + ProjectsCommands.CLEAN);
        int returnCode = winService.runCommandWaitForFinish(ProjectsCommands.VC8_VMSIN.ACE_BATCH + Locations.QA_ROOT + " " + ProjectsCommands.BUILD);
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }
}
