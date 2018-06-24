package Native.uitests;

import com.aventstack.extentreports.Status;
import frameworkInfra.sikuli.sikulimapping.IBMonitor.Monitor;
import frameworkInfra.testbases.UIValidationTestBase;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import org.sikuli.script.FindFailed;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import static frameworkInfra.Listeners.SuiteListener.test;

public class UIValidationsTests extends UIValidationTestBase {


    @Test(testName = "Verify VS Monitor Bar")
    public void verifyVSMonitorBar(){
        batchProjects.forEach(item->{
            if(project.equals(item)){
                test.log(Status.SKIP, "Skipping test as this is a Batch project");
                throw new SkipException("Skipped test");
            }
        });

        vsuiService.openVSInstance("15", false);
        vsuiService.openProject(projectLocation);
        vsuiService.performIbActionFromMenu(VsActions.REBUILD_SOLUTION);
        client.verifyVSBarPattern(vsBarPattern);
    }

    @Test(testName = "Verify Tray Icon Color")
    public void verifyTrayIconColor(){
        client.verifyTrayIconPattern(trayIconPattern);
    }

    @Test(testName = "Verify IB Monitor Bar")
    public void verifyIBMonitorBar(){
        winService.runCommandDontWaitForTermination(IbLocations.BUILDMONITOR);
        client.verifyMonitorBarPattern(ibMonBarPattern);
        SystemActions.killProcess(Processes.BUILDMONITOR);
    }

    @Test(testName = "Verify History Coloring")
    public void verifyHistoryColoring(){
        winService.runCommandDontWaitForTermination(IbLocations.BUILDHISTORY);
        client.verifyHistoryColoringPattern(historyPattern);
        SystemActions.killProcess(Processes.BUILDHISTORY);
    }

    @Test(testName = "Verify Projects Tab Coloring")
    public void verifyProjectsTabColoring(){
        batchProjects.forEach(item->{
            if(project.equals(item)){
                test.log(Status.SKIP, "Skipping test as this is a Batch project");
                throw new SkipException("Skipped test");
            }
        });

        winService.runCommandDontWaitForTermination(IbLocations.BUILDMONITOR);
        client.clickProjectsTab();
        client.verifyProjectsTabColoring(progressPattern);
        SystemActions.killProcess(Processes.BUILDMONITOR);
    }



}
