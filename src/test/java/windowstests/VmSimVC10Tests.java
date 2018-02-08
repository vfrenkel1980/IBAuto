package windowstests;

import frameworkInfra.testbases.VmBCTestBase;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.SystemActions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

public class VmSimVC10Tests extends VmBCTestBase {

    @Test(testName = "XBMC 2010 - Debug x32- build" , groups = { "Build" })
    public void xbmc2010x32Debug(){
        int returnCode = runWin.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC10_VMSIM.XMBC_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "XBMC 2010 - Release x32- build" , groups = { "Build" })
    public void xbmc2010x32Release(){
        int returnCode = runWin.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC10_VMSIM.XMBC_X32_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Blender 2010 - Release x32- build" , groups = { "Build" })
    public void blender2010x32Release(){
        int returnCode = runWin.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC10_VMSIM.BLENDER_X32_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "0.A.D 2010 - Debug x32- build" , groups = { "Build" })
    public void OAD2010x32Debug(){
        int returnCode = runWin.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC10_VMSIM.OAD_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "0.A.D 2010 - Release x32- build" , groups = { "Build" })
    public void OAD2010x32Release(){
        int returnCode = runWin.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC10_VMSIM.OAD_X32_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "CL No Properties 2010 - Debug x32- build" , groups = { "Build" })
    public void clNoProperties2010x32Debug(){
        int returnCode = runWin.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC10_VMSIM.CL_NO_PROPERTIES, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "DLL Data 2010 - Debug x32- build" , groups = { "Build" })
    public void dllData2010x32Debug(){
        int returnCode = runWin.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC10_VMSIM.DLL_DATA, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "FU CL 2010 - Debug x32- build" , groups = { "Build" })
    public void fucl2010x32Debug(){
        int returnCode = runWin.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC10_VMSIM.FUCL, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Batch Build Bug 2010 - build" , groups = { "Build" })
    public void batchBuildBug2010x32Debug(){
        int returnCode = runWin.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC10_VMSIM.BATCH_BUILD_BUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "No Debug Configuration 2010 - build" , groups = { "Build" })
    public void noDebugConfiguration2010x32Debug(){
        int returnCode = runWin.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC10_VMSIM.NO_DEBUG_CONFIGURATION, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Active Doc 2010 - build" , groups = { "Build" })
    public void activeDoc2010x32Debug(){
        int returnCode = runWin.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC10_VMSIM.ACTIVE_DOC, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "All In One Diagnostics Samples 2010 - build" , groups = { "Build" })
    public void allInOne2010x32Debug(){
        int returnCode = runWin.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC10_VMSIM.ALL_IN_ONE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Interfaces 2010 - build" , groups = { "Build" })
    public void interfaces2010x32Debug(){
        int returnCode = runWin.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC10_VMSIM.INTERFACES, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "VC 2010 Test - build" , groups = { "Build" })
    public void vc2010Testx32Debug(){
        int returnCode = runWin.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC10_VMSIM.VC_2010_TEST, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Dep Eval 4 2010- build" , groups = { "Build" })
    public void depEval4x32Debug(){
        int returnCode = runWin.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC10_VMSIM.DEP_EVAL_4, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Dep Eval 5 2010- build" , groups = { "Build" })
    public void depEval5x32Debug(){
        int returnCode = runWin.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC10_VMSIM.DEP_EVAL_5, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Dep Eval 6 2010- build" , groups = { "Build" })
    public void depEval6x32Debug(){
        int returnCode = runWin.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC10_VMSIM.DEP_EVAL_6, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Dep Eval 7 2010- build" , groups = { "Build" })
    public void depEval7x32Debug(){
        int returnCode = runWin.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC10_VMSIM.DEP_EVAL_7, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Configuration test 1 2010- build" , groups = { "Build" })
    public void configurationTest1x32Debug(){
        int returnCode = runWin.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC10_VMSIM.CONFIGURATION_TEST_1, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Configuration test 1 2010 No Unneeded- build" , groups = { "Build" })
    public void configurationTest1NoUnneededx32Debug(){
        int returnCode = runWin.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC10_VMSIM.CONFIGURATION_TEST_1_NO_UNNEEDED, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Configuration test 1 2010 Unneeded- build" , groups = { "Build" })
    public void configurationTest1Unneededx32Debug(){
        int returnCode = runWin.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC10_VMSIM.CONFIGURATION_TEST_1_UNNEEDED, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Configuration test 2 2010 - build" , groups = { "Build" })
    public void configurationTest2x32Debug(){
        int returnCode = runWin.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC10_VMSIM.CONFIGURATION_TEST_2, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Configuration test 2 2010 Custom Configuration - build" , groups = { "Build" })
    public void configurationTest2CustomConfigurationx32Debug(){
        int returnCode = runWin.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC10_VMSIM.CONFIGURATION_TEST_2_CUSTOM_CONF, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Configuration test 2 2010 Several Configuration - build" , groups = { "Build" })
    public void configurationTest2SeveralConfigurationx32Debug(){
        int returnCode = runWin.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC10_VMSIM.CONFIGURATION_TEST_2_SEVERAL_CONF, "%s"));
        Assert.assertTrue(returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Configuration test 3 2010 - build" , groups = { "Build" })
    public void configurationTest3x32Debug(){
        int returnCode = runWin.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC10_VMSIM.CONFIGURATION_TEST_3, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Configuration test 3 2010 Batch Build - build" , groups = { "Build" })
    public void configurationTest3BatchBuildx32Debug(){
        int returnCode = runWin.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC10_VMSIM.CONFIGURATION_TEST_3_BATCH_BUILD, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Configuration test 4 2010 - build" , groups = { "Build" })
    public void configurationTest4x32Debug(){
        int returnCode = runWin.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC10_VMSIM.CONFIGURATION_TEST_4, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Configuration test 4 2010 Batch Build - build" , groups = { "Build" })
    public void configurationTest4BatchBuildx32Debug(){
        int returnCode = runWin.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC10_VMSIM.CONFIGURATION_TEST_4_BATCH_BUILD, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Configuration test 5 2010 Batch Build - build" , groups = { "Build" })
    public void configurationTest5BatchBuildx32Debug(){
        int returnCode = runWin.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC10_VMSIM.CONFIGURATION_TEST_5_BATCH_BUILD, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Configuration test 6 2010 - build" , groups = { "Build" })
    public void configurationTest6x32Debug(){
        int returnCode = runWin.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC10_VMSIM.CONFIGURATION_TEST_6, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Configuration test 7 2010 (using .sln and no /prj) - build" , groups = { "Build" })
    public void configurationTest7x32Debug(){
        int returnCode = runWin.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC10_VMSIM.CONFIGURATION_TEST_7, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Configuration test 8 2010 - build" , groups = { "Build" })
    public void configurationTest8x32Debug(){
        int returnCode = runWin.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC10_VMSIM.CONFIGURATION_TEST_8, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Configuration test 9 2010 - build" , groups = { "Build" })
    public void configurationTest9x32Debug(){
        int returnCode = runWin.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC10_VMSIM.CONFIGURATION_TEST_9, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Configuration test 10 2010 - build" , groups = { "Build" })
    public void configurationTest10x32Debug(){
        int returnCode = runWin.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC10_VMSIM.CONFIGURATION_TEST_10, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Configuration test 8 Check 2010 - build" , groups = { "Build" }, dependsOnMethods = { "configurationTest8x32Debug" })
    public void configurationTest8CheckBatch(){
        int returnCode = runWin.runCommandWaitForFinish(StaticDataProvider.Processes.XGCONSOLE + StaticDataProvider.ProjectsCommands.VC10_VMSIM.CONFIGURATION_TEST_8_CHECK);
        Assert.assertTrue(returnCode == 0 , "Build failed with return code " + returnCode);
        SystemActions.deleteFilesByPrefix(StaticDataProvider.Locations.QA_ROOT + "VC10\\Configuration_Tests\\ConfigurationTest8\\Proj1", "i_was_ran");
    }

    @Test(testName = "Single Inverted Comma Batch 2010 - build" , groups = { "Build" })
    public void singleInvertedCommaBatch(){
        runWin.runCommandWaitForFinish((String.format(StaticDataProvider.ProjectsCommands.VC10_VMSIM.SINGLE_INVERTED_COMMA,StaticDataProvider.ProjectsCommands.CLEAN)));
        int returnCode = runWin.runCommandWaitForFinish(String.format(StaticDataProvider.ProjectsCommands.VC10_VMSIM.SINGLE_INVERTED_COMMA,StaticDataProvider.ProjectsCommands.BUILD));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Ignore
    @Test(testName = "Dependency Reference Test Batch 2010 - build" , groups = { "Build" })
    public void dependencyReferenceTestBatch(){
        int returnCode = runWin.runCommandWaitForFinish(StaticDataProvider.ProjectsCommands.VC10_VMSIM.DEPENDENCY_REFERENCE_TEST);
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

}
