package Native.windowstests.alfred;

import frameworkInfra.testbases.InterfacesTestBase;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import frameworkInfra.utils.parsers.Parser;
import org.testng.Assert;
import org.testng.annotations.Test;

public class InterfaceTests extends InterfacesTestBase{

    @Test(testName = "BuildConsole Multiple Parameters Success")
    public void buildConsoleMultipleParametersSuccess(){
        int exitCode = winService.runCommandWaitForFinish(Processes.BUILD_CONSOLE + ProjectsCommands.INTERFACES.BUILDCONSOLE_MULTIPLE_PARAMS);
        Assert.assertEquals(exitCode, 0, "Build exit code is different then 0");
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '"), "No agents shown in the output file");
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "buildconsoletest"), "Title not shown in the output file");
    }

    @Test(testName = "BuildConsole No Parameters Fail")
    public void buildConsoleNoParametersFail(){
        int exitCode = winService.runCommandWaitForFinish(Processes.BUILD_CONSOLE + ProjectsCommands.INTERFACES.BUILDCONSOLE_NO_PARAMS);
        Assert.assertEquals(exitCode, 3, "Build exit code is different then 3");
    }

    @Test(testName = "BuildConsole Invalid Param Fail")
    public void buildConsoleInvalidParamFail(){
        winService.runCommandWaitForFinish(Processes.BUILD_CONSOLE + ProjectsCommands.INTERFACES.BUILDCONSOLE_INVALID_PARAM);
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.INVALID_PARAM_ERROR), "Invalid param error does not appear in log");
    }

    @Test(testName = "BuildConsole No Params")
    public void buildConsoleNoParams(){
        String output = winService.runCommandGetOutput(Processes.BUILD_CONSOLE);
        Assert.assertTrue(output.contains("General Options:"), "No param error does not appear in log");
    }

    @Test(testName = "IBConsole Multiple Parameters Success")
    public void ibConsoleMultipleParametersSuccess(){
        int exitCode = winService.runCommandWaitForFinish(ProjectsCommands.INTERFACES.IBCONSOLE_MULTIPLE_PARAMS);
        Assert.assertEquals(exitCode, 0, "Build exit code is different then 0");
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "ibconsoletest"), "Title not shown in the output file");
    }

    @Test(testName = "IBConsole One Param")
    public void ibConsoleOneParameter(){
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBCONSOLE + ProjectsCommands.INTERFACES.OPEN_MONITOR_ONLY);
        Assert.assertEquals(exitCode, 3, "Build exit code is different then 3");
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.MISSING_PARAM_ERROR), "Missing parameter error does not appear in log");
    }

    @Test(testName = "IBConsole Invalid Parameters")
    public void ibConsoleInvalidParameters(){
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBCONSOLE + ProjectsCommands.INTERFACES.IBCONSOLE_INVALID_PARAM);
        Assert.assertEquals(exitCode, 3, "Build exit code is different then 3");
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.INVALID_PARAM_ERROR), "Invalid parameter error does not appear in log");
    }

    @Test(testName = "IbConsole No Params")
    public void ibConsoleNoParams(){
        String output = winService.runCommandGetOutput(IbLocations.IBCONSOLE);
        Assert.assertTrue(output.contains("General Options:"), "No param error does not appear in log");
    }

    @Test(testName = "XGConsole Success")
    public void xgConsoleSuccess(){
        int exitCode = winService.runCommandWaitForFinish(ProjectsCommands.INTERFACES.XGCONSOLE_SUCCESS);
        Assert.assertEquals(exitCode, 0, "Build exit code is different then 0");
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Automatic Interception Sample"), "Title not shown in the output file");
    }

    @Test(testName = "XGConsole One Parameter")
    public void xgConsoleOneParameter(){
        int exitCode = winService.runCommandWaitForFinish(Processes.XGCONSOLE + ProjectsCommands.INTERFACES.OPEN_MONITOR_ONLY);
        Assert.assertEquals(exitCode, 3, "Build exit code is different then 3");
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.MISSING_PARAM_ERROR), "Missing parameter error does not appear in log");
    }

    @Test(testName = "XGConsole Single Parameter")
    public void xgConsoleSingleParameter(){
        int exitCode = winService.runCommandWaitForFinish(ProjectsCommands.INTERFACES.XGCONSOLE_SINGLE_PARAM);
        Assert.assertEquals(exitCode, 0, "Build exit code is different then 0");
    }

    @Test(testName = "XGConsole No Params")
    public void xgConsoleNoParams(){
        String output = winService.runCommandGetOutput(Processes.XGCONSOLE);
        Assert.assertTrue(output.contains("General Options:"), "No param error does not appear in log");
    }

    @Test(testName = "XGCoordConsole Reset Caches")
    public void xgCoordConsoleResetCaches(){
        String output = winService.runCommandGetOutput(Processes.XGCOORDCONSOLE + ProjectsCommands.INTERFACES.RESET_CACHES);
        Assert.assertFalse(output.contains("failed to complete request"), "Error was found during process.");
    }

    @Test(testName = "XGCoordConsole Export Status")
    public void xgCoordConsoleExportStatus(){
        winService.runCommandWaitForFinish(Processes.XGCOORDCONSOLE + ProjectsCommands.INTERFACES.EXPORT_STATUS);
        Assert.assertTrue(SystemActions.doesFileExist("c:\\qa\\simulation\\coordexport.xml"));
    }

    @Test(testName = "XGCoordConsole Export Status And Reset Caches")
    public void xgCoordConsoleExportStatusAndResetCaches(){
        String output = winService.runCommandGetOutput(Processes.XGCOORDCONSOLE + ProjectsCommands.INTERFACES.EXPORT_STATUS + " " + ProjectsCommands.INTERFACES.RESET_CACHES);
        Assert.assertTrue(SystemActions.doesFileExist("c:\\qa\\simulation\\coordexport.xml"));
        Assert.assertFalse(output.contains("Resetting file cache"), "Reset caches should not run in the same command as Export status.");
    }

    @Test(testName = "XGCoordConsole No Params")
    public void xgCoordConsoleNoParams(){
        String output = winService.runCommandGetOutput(IbLocations.XGCOORDCONSOLE);
        Assert.assertTrue(output.contains("Options:"), "No param error does not appear in log");
    }

/*    //TODO: ask Vlad how to verify this
    @Test(testName = "XGSubmit XGWait Integration")
    public void xgSubmitXGWaitIntegration(){
        winService.runCommandWaitForFinish(ProjectsCommands.INTERFACES.XGSUBMIT_BATCH);

    }*/


}
