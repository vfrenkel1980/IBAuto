package windowstests;

import frameworkInfra.testbases.VmSimTestBase;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import static frameworkInfra.utils.StaticDataProvider.*;

public class VmSimVC9Tests extends VmSimTestBase {

    @Test(testName = "ACE 2008 - Debug x32 - build" , groups = { "Build" })
    public void ace2008DebugBuild(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC9_VMSIM.ACE_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Scintilla 2008 - Debug x32 - build" , groups = { "Build" })
    public void scintilla2008DebugBuild(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC9_VMSIM.SCINTILLA, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "VC9 All 2008 - Debug x32 - build" , groups = { "Build" })
    public void vc9All2008DebugBuild(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC9_VMSIM.VC9_ALL, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "CL No Properties 2008 - Debug x32- build" , groups = { "Build" })
    public void clNoProperties2008x32Debug(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC9_VMSIM.CL_NO_PROPERTIES, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Quotes In Intermediate Dir Bug 2008 - Debug x32- build" , groups = { "Build" })
    public void quotes2008x32Debug(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC9_VMSIM.QUOTES, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Assembly Output Path 2008 - Debug - build" , groups = { "Build" })
    public void assemblyOutputPath2008x32Debug(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC9_VMSIM.ASSEMBLY, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Copy Local False 2008 - Debug - build" , groups = { "Build" })
    public void copyLocalFalse2008x32Debug(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC9_VMSIM.COPY_LOCAL_FALSE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Ref Output Csharp Changed 2008 - Debug - build" , groups = { "Build" })
    public void refOutputCsharpChanged2008x32Debug(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC9_VMSIM.REF_OUTPUT_CHANGED_CSHARP, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Ref Output Dir Changed 2008 - Debug - build" , groups = { "Build" })
    public void refOutputDirChanged2008x32Debug(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC9_VMSIM.REF_OUTPUT_DIR_CHANGED, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Utility 2008 - Debug - build" , groups = { "Build" })
    public void utility2008x32Debug(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC9_VMSIM.UTILITY, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }
    @Test(testName = "Prop Sheet Out Dir 2008 - Debug - build" , groups = { "Build" })
    public void propSheetOutDir2008x32Debug(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC9_VMSIM.PROP_SHEET_OUT_DIR, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "PCH 2008 - Debug - build" , groups = { "Build" })
    public void pch2008x32Debug(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC9_VMSIM.PCH, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Using Bug 2008 - Debug - build" , groups = { "Build" })
    public void usingBug2008x32Debug(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC9_VMSIM.USING_BUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "/NoLink Makes Post Build Step 2008 - Debug - build" , groups = { "Build" })
    public void noLink2008x32Debug(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC9_VMSIM.NOLINK, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Loader Test 1 2008 - Debug - build" , groups = { "Build" })
    public void loaderTest12008x32Debug(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC9_VMSIM.LOADER_1, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Loader Test 2 2008 - Debug - build" , groups = { "Build" })
    public void loaderTest22008x32Debug(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC9_VMSIM.LOADER_2, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Loader Test 3 2008 - Debug - build" , groups = { "Build" })
    public void loaderTest32008x32Debug(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC9_VMSIM.LOADER_3, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Loader Test 4 2008 - Debug - build" , groups = { "Build" })
    public void loaderTest42008x32Debug(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC9_VMSIM.LOADER_4, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Loader Test 5 2008 - Debug - build" , groups = { "Build" })
    public void loaderTest52008x32Debug(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC9_VMSIM.LOADER_5, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Loader Test 6 2008 - Debug - build" , groups = { "Build" })
    public void loaderTest62008x32Debug(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC9_VMSIM.LOADER_6, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Loader Test 7 2008 - Debug - build" , groups = { "Build" })
    public void loaderTest72008x32Debug(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC9_VMSIM.LOADER_7, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Loader Test 8 2008 - Debug - build" , groups = { "Build" })
    public void loaderTest82008x32Debug(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC9_VMSIM.LOADER_8, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Loader Test 9 2008 - Debug - build" , groups = { "Build" })
    public void loaderTest92008x32Debug(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC9_VMSIM.LOADER_9, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Loader Test 10 2008 - Debug - build" , groups = { "Build" })
    public void loaderTest102008x32Debug(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC9_VMSIM.LOADER_10, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Loader Test 11 2008 - Debug - build" , groups = { "Build" })
    public void loaderTest112008x32Debug(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC9_VMSIM.LOADER_11, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Loader Test 12 2008 - Debug - build" , groups = { "Build" })
    public void loaderTest122008x32Debug(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC9_VMSIM.LOADER_12, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Loader Test 13 2008 - Debug - build" , groups = { "Build" })
    public void loaderTest132008x32Debug(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC9_VMSIM.LOADER_13, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Loader Test 14 2008 - Debug - build" , groups = { "Build" })
    public void loaderTest142008x32Debug(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC9_VMSIM.LOADER_14, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Loader Test 15 2008 - Debug - build" , groups = { "Build" })
    public void loaderTest152008x32Debug(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC9_VMSIM.LOADER_15, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Loader Test 16 2008 - Debug - build" , groups = { "Build" })
    public void loaderTest162008x32Debug(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC9_VMSIM.LOADER_16, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Loader Test 18 2008 - Debug - build" , groups = { "Build" })
    public void loaderTest182008x32Debug(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC9_VMSIM.LOADER_18, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Prop Test 2008 - Debug - build" , groups = { "Build" })
    public void propTest2008x32Debug(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC9_VMSIM.PROP, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Property Sheet 2 2008 - Debug - build" , groups = { "Build" })
    public void propertySheet22008x32Debug(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC9_VMSIM.PROPERTY_SHEET_2, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Property Sheet 3 2008 - Debug - build" , groups = { "Build" })
    public void propertySheet32008x32Debug(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC9_VMSIM.PROPERTY_SHEET_3, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Property Sheet 4 2008 - Debug - build" , groups = { "Build" })
    public void propertySheet42008x32Debug(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC9_VMSIM.PROPERTY_SHEET_4, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "ParentName Macro Bug 2008 - Debug - build" , groups = { "Build" })
    public void parentNameMacroBug42008x32Debug(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC9_VMSIM.PARENT_NAME_MACRO, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Dep Eval 13 2008 - Debug - build" , groups = { "Build" })
    public void depEval12008x32Debug(){
        int returnCode = runWin.runCommandWaitForFinish(ProjectsCommands.VC9_VMSIM.DEP_EVAL_1);
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Dep Eval 3 2008 - Debug - build" , groups = { "Build" })
    public void depEval32008x32Debug(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC9_VMSIM.DEP_EVAL_3, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Dep Eval 4 2008 - Debug - build" , groups = { "Build" })
    public void depEval42008x32Debug(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC9_VMSIM.DEP_EVAL_4, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Dep Eval 5 2008 - Debug - build" , groups = { "Build" })
    public void depEval52008x32Debug(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC9_VMSIM.DEP_EVAL_5, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }
    @Ignore
    @Test(testName = "Dep Eval 6 2008 - Debug - build" , groups = { "Build" })
    public void depEval62008x32Debug(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC9_VMSIM.DEP_EVAL_6, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Dep Eval 7 2008 - Debug - build" , groups = { "Build" })
    public void depEval72008x32Debug(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC9_VMSIM.DEP_EVAL_7, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Dep Eval 9 2008 - Debug - build" , groups = { "Build" })
    public void depEval92008x32Debug(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC9_VMSIM.DEP_EVAL_9, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Dep Eval 10 2008 - Debug - build" , groups = { "Build" })
    public void depEval102008x32Debug(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC9_VMSIM.DEP_EVAL_10, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Dep Eval 11 2008 - Debug - build" , groups = { "Build" })
    public void depEval112008x32Debug(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC9_VMSIM.DEP_EVAL_11, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Dep Eval 12 2008 (division by zero 1)- Debug - build" , groups = { "Build" })
    public void depEval122008x32Debug(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC9_VMSIM.DEP_EVAL_12, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Root Include Bug 2008- Debug - build" , groups = { "Build" })
    public void rootInclude22008x32Debug(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC9_VMSIM.ROOT_INCLUDE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Space In Compile Switch Bug 2008- Debug - build" , groups = { "Build" })
    public void spaceInCompileSwitchBug2008x32Debug(){
        int returnCode = runWin.runCommandWaitForFinish(ProjectsCommands.VC9_VMSIM.SPACE_IN_COMPILE_SWITCH_BUG);
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Dep Eval Self Test 2008- Debug - build" , groups = { "Build" })
    public void depEvalSelfTest2008x32Debug(){
        int returnCode = ibService.cleanAndBuild(Processes.BUILD_CONSOLE + String.format(ProjectsCommands.VC9_VMSIM.DEP_EVAL_SELF_TEST, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);

    }

    @Test(testName = "Build System Self Test 1" , groups = { "Build" })
    public void buildSystemSelfTest1(){
        int returnCode = runWin.runCommandWaitForFinish(Processes.BUILDSYSTEM + ProjectsCommands.VC9_VMSIM.BUILD_SYSTEM_SELF_1_TEST);
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Build System Self Test 2" , groups = { "Build" })
    public void buildSystemSelfTest2(){
        int returnCode = runWin.runCommandWaitForFinish(ProjectsCommands.VC9_VMSIM.BUILD_SYSTEM_SELF_2_TEST);
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }
}
