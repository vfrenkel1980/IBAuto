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
        try {
            screen.wait(vsBarPattern.similar((float) 0.9),2);
        } catch (FindFailed findFailed) {
            findFailed.printStackTrace();
            Assert.fail("Failed to find VS Bar on screen");
        }
    }

    @Test(testName = "Verify Tray Icon Color")
    public void verifyTrayIconColor(){
        try {
            screen.wait(trayIconPattern.similar((float) 0.8),2);
        } catch (FindFailed findFailed) {
            findFailed.printStackTrace();
            Assert.fail("Failed to find Matching tray icon color on screen");
        }
    }

    @Test(testName = "Verify IB Monitor Bar")
    public void verifyIBMonitorBar(){
        winService.runCommandDontWaitForTermination(IbLocations.BUILDMONITOR);
        try {
            screen.wait(ibMonBarPattern.similar((float) 0.9),2);
        } catch (FindFailed findFailed) {
            findFailed.printStackTrace();
            Assert.fail("Failed to find IB Bar on screen");
        }
        finally {
            SystemActions.killProcess(Processes.BUILDMONITOR);
        }
    }

    @Test(testName = "Verify History Coloring")
    public void verifyHistoryColoring(){
        winService.runCommandDontWaitForTermination(IbLocations.BUILDHISTORY);
        try {
            screen.wait(historyPattern.similar((float) 0.8),2);
        } catch (FindFailed findFailed) {
            findFailed.printStackTrace();
            Assert.fail("Failed to find matching history/project color on screen");
        }
        finally {
            SystemActions.killProcess(Processes.BUILDHISTORY);
        }
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
        try {
            screen.wait(Monitor.Tabs.Projects.similar((float) 0.8),2).click();
            screen.wait(progressPattern.similar((float) 0.9),2);
        } catch (FindFailed findFailed) {
            Assert.fail(findFailed.getMessage());
        }
        finally {
            SystemActions.killProcess(Processes.BUILDMONITOR);
        }
    }



}
