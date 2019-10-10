package Native.uitests;

import com.aventstack.extentreports.Status;
import frameworkInfra.testbases.UIValidationTestBase;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
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
        vsuiService.openVSInstance("15", false, "");
        vsuiService.openProject(projectLocation);
        if (project.contains("white")){
            vsuiService.performIbActionFromMenuDontWaitForFinish(VsActions.REBUILD_SOLUTION);
            SystemActions.sleep(10);
            vsuiService.performIbActionFromMenu(VsActions.STOP_BUILD);
        }else {
            vsuiService.performIbActionFromMenu(VsActions.REBUILD_SOLUTION);
        }
        client.verifyVSBarPattern(vsBarPattern);
    }

    @Test(testName = "Verify Tray Icon Color")
    public void verifyTrayIconColor(){
        client.verifyTrayIconPattern(trayIconPattern);
    }

    @Test(testName = "Verify IB Monitor Bar")
    public void verifyIBMonitorBar(){
        ibService.openBuildMonitor();
        client.verifyMonitorBarPattern(ibMonBarPattern);
    }

    @Test(testName = "Verify History Coloring")
    public void verifyHistoryColoring(){
        winService.runCommandDontWaitForTermination(IbLocations.BUILDHISTORY);
        client.verifyHistoryColoringPattern(historyPattern);
    }

    @Test(testName = "Verify Projects Tab Coloring")
    public void verifyProjectsTabColoring(){
        batchProjects.forEach(item->{
            if(project.equals(item)){
                test.log(Status.SKIP, "Skipping test as this is a Batch project");
                throw new SkipException("Skipped test");
            }
        });

        ibService.openBuildMonitor();
        client.clickProjectsTab();
        client.verifyProjectsTabColoring(progressPattern);
    }

    @Test(testName = "Verify Monitor Opened From Tray")
    public void verifyMonitorOpenedFromTray(){
        if (!project.equals("green01")){
            test.log(Status.SKIP, "Test should run once on green project");
            throw new SkipException("Skipped test");
        }
        client.openMonitorFromTray();
        client.verifyBuildMonitorOpened();
    }

    @Test(testName = "Verify History Opened From Tray")
    public void verifyHistoryFromTray(){
        if (!project.equals("green01")){
            test.log(Status.SKIP, "Test should run once on green project");
            throw new SkipException("Skipped test");
        }
        client.openHistoryFromTray();
        client.verifyBuildHistoryOpened();
    }

    @Test(testName = "Verify Coord Monitor Opened From Tray")
    public void verifyCoordMonitorOpenedFromTray(){
        if (!project.equals("green01")){
            test.log(Status.SKIP, "Test should run once on green project");
            throw new SkipException("Skipped test");
        }
        client.openCoordMonitorFromTray();
        client.verifyCoordinatorMonitorOpened();
    }

    @Test(testName = "Verify Agent Settings Opened From Tray")
    public void verifyAgentSettingsOpenedFromTray(){
        if (!project.equals("green01")){
            test.log(Status.SKIP, "Test should run once on green project");
            throw new SkipException("Skipped test");
        }
        client.openAgentSettingsFromTray();
        client.verifyAgentSettingsOpened();
    }

}
