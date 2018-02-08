package windowstests;

import frameworkInfra.testbases.VmBCTestBase;
import frameworkInfra.utils.StaticDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

public class VmSimVC6Tests extends VmBCTestBase {

    @Test(testName = "News 1998 - Debug x32 - build" , groups = { "Build" })
    public void news1998DebugBuild(){
        int returnCode = runWin.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC6_VMSIM.NEWS_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Hexedit 1998 - build" , groups = { "Build" })
    public void hexedit1998Build(){
        int returnCode = runWin.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC6_VMSIM.HEXEDIT, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "IFProjects 1998 - build" , groups = { "Build" })
    public void ifprojects1998Build(){
        int returnCode = runWin.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC6_VMSIM.IFPROJECTS, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "NOLF 1998 -build" , groups = { "Build" })
    public void noLF1998Build(){
        int returnCode = runWin.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC6_VMSIM.NOLF, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "SciLexer 1998 - build" , groups = { "Build" })
    public void sciLexer1998Build(){
        int returnCode = runWin.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC6_VMSIM.SCILEXER, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Apache 1998 - Debug x32 - build" , groups = { "Build" })
    public void apache1998DebugBuild(){
        int returnCode = runWin.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC6_VMSIM.APACHE_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }
}
