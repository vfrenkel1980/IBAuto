package Native.uitests;

import com.aventstack.extentreports.Status;
import frameworkInfra.sikuli.sikulimapping.CoordMonitor.CoordMonitor;
import frameworkInfra.sikuli.sikulimapping.IBSettings.IBSettings;
import frameworkInfra.testbases.UIValidationTestBase;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import ibInfra.vs.VS16UIService;
import ibInfra.vs.VSUIService;
import org.sikuli.script.FindFailed;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import static frameworkInfra.Listeners.SuiteListener.test;
import static frameworkInfra.utils.StaticDataProvider.ProjectsCommands.COORD_SETTINGS.COORD_SETTINGS_LOCATION;

/**
 * @brief <b> <a href="https://incredibuild.atlassian.net/wiki/spaces/IUM/pages/11272241/Visual+Studio+UI+Add-in"><b>Visual Studio UI Add-in</b></a> UI tests</b>
 * @brief <b> <a href="https://incredibuild.atlassian.net/wiki/spaces/IUM/pages/21758026/The+Build+Monitor"><b>The Build Monitor</b></a> UI tests</b>
 * @details Run on UI Automation (HOST-4)
 */
public class UIValidationsTests extends UIValidationTestBase {

    /**
     * @test Verify Visual Studio 2017 Monitor Bar
     * @pre{ }
     * @steps{
     * - Open VS instance version 15
     * - Open project
     * - Perform Ib action Rebuild Solution
     * - Perform Ib action Stop Build
     * - Verify VS bar pattern
     * - Kill process devenv}
     * @result{ }
     */
    @Test(testName = "Verify VS 2017 Monitor Bar")
    public void verifyVS2017MonitorBar() {
        VSUIService vsuiService = new VSUIService();
        try {
            batchProjects.forEach(item -> {
                if (project.equals(item)) {
                    test.log(Status.SKIP, "Skipping test as this is a Batch project");
                    throw new SkipException("Skipped test");
                }
            });
            vsuiService.openVSInstance("15", false, "");
            vsuiService.openProject(projectLocation);
            if (project.contains("white")) {
                vsuiService.performIbActionFromMenuDontWaitForFinish(VsActions.REBUILD_SOLUTION);
                SystemActions.sleep(12);
                vsuiService.performIbActionFromMenu(VsActions.STOP_BUILD);
            } else {
                vsuiService.performIbActionFromMenu(VsActions.REBUILD_SOLUTION);
            }
            client.verifyVSBarPattern(vsBarPattern);
        } catch (RuntimeException e) {
            e.getMessage();
        } finally {
            vsuiService.killDriver();
        }
    }

    /**
     * @test Verify Visual Studio 2019 Monitor Bar
     * @pre{ }
     * @steps{
     * - Open VS instance version 16
     * - Open project
     * - Perform Ib action Rebuild Solution
     * - Perform Ib action Stop Build
     * - Verify VS bar pattern
     * - Kill process devenv}
     * @result{ }
     */
    @Test(testName = "Verify VS 2019 Monitor Bar")
    public void verifyVS2019MonitorBar() {
        VS16UIService vsuiService = new VS16UIService();
        try {
            batchProjects.forEach(item -> {
                if (project.equals(item)) {
                    test.log(Status.SKIP, "Skipping test as this is a Batch project");
                    throw new SkipException("Skipped test");
                }
            });
            vsuiService.openVSInstance("16", false, "");
            vsuiService.openProject(projectLocation);
            if (project.contains("white")) {
                vsuiService.performIbActionFromMenuDontWaitForFinish(VsActions.REBUILD_SOLUTION);
                SystemActions.sleep(12);
                vsuiService.performIbActionFromMenu(VsActions.STOP_BUILD);
            } else {
                vsuiService.performIbActionFromMenu(VsActions.REBUILD_SOLUTION);
            }
            client.verifyVSBarPattern(vsBarPattern);
        } catch (RuntimeException e) {
            e.getMessage();
        } finally {
            vsuiService.killDriver();
        }
    }

    /**
     * @test Verify Visual Studio 2019 Preview Monitor Bar
     * @prse{ }
     * @steps{
     * - Open VS instance version 116
     * - Open project
     * - Perform Ib action Rebuild Solution
     * - Perform Ib action Stop Build
     * - Verify VS bar pattern
     * - Kill process devenv}
     * @result{ }
     */
    @Test(testName = "Verify VS 2019 Preview Monitor Bar")
    public void verifyVS2019PreviewMonitorBar() {
        VS16UIService vsuiService = new VS16UIService();
        try {
            batchProjects.forEach(item -> {
                if (project.equals(item)) {
                    test.log(Status.SKIP, "Skipping test as this is a Batch project");
                    throw new SkipException("Skipped test");
                }
            });
            vsuiService.openVSInstance("116", false, "");
            vsuiService.openProject(projectLocation);
            if (project.contains("white")) {
                vsuiService.performIbActionFromMenuDontWaitForFinish(VsActions.REBUILD_SOLUTION);
                SystemActions.sleep(12);
                vsuiService.performIbActionFromMenu(VsActions.STOP_BUILD);
            } else {
                vsuiService.performIbActionFromMenu(VsActions.REBUILD_SOLUTION);
            }
            SystemActions.sleep(30);
            client.verifyVSBarPattern(vsBarPattern);
        } catch (RuntimeException e) {
            e.getMessage();
        } finally {
            vsuiService.killDriver();
        }
    }

    /**
     * @test Verify Tray Icon Color
     * @pre{ }
     * @steps{ - Verify green tray icon pattern}
     * @result{ - Try icon is present}
     */
    @Test(testName = "Verify Tray Icon Color")
    public void verifyTrayIconColor() {
        client.verifyTrayIconPattern(trayIconPattern);
    }

    /**
     * @test Verify IB Monitor Bar
     * @pre{ }
     * @steps{
     * - Open build monitor exe
     * - Verify Monitor Bar Pattern}
     * @result{ - Monitor Bar pattern is present}
     */
    @Test(testName = "Verify IB Monitor Bar")
    public void verifyIBMonitorBar() {
        ibService.openBuildMonitor();
        client.verifyMonitorBarPattern(ibMonBarPattern);
    }

    /**
     * @test Verify History Coloring
     * @pre{ }
     * @steps{
     * - Run BuildHistory.exe file
     * - Verify History coloring pattern}
     * @result{ - History coloring pattern is present}
     */
    @Test(testName = "Verify History Coloring")
    public void verifyHistoryColoring() {
        winService.runCommandDontWaitForTermination(IbLocations.BUILDHISTORY);
        client.verifyHistoryColoringPattern(historyPattern);
    }

    /**
     * @test Verify Projects Tab Coloring
     * @pre{ }
     * @steps{
     * - Open Build monitor
     * - Click Projects tab
     * - Verify Projects tab coloring}
     * @result{ - Progress pattern is present}
     */
    @Test(testName = "Verify Projects Tab Coloring")
    public void verifyProjectsTabColoring() {
        batchProjects.forEach(item -> {
            if (project.equals(item)) {
                test.log(Status.SKIP, "Skipping test as this is a Batch project");
                throw new SkipException("Skipped test");
            }
        });

        ibService.openBuildMonitor();
        client.clickProjectsTab();
        client.verifyProjectsTabColoring(progressPattern);
    }

    /**
     * @test Verify Monitor Opened From Tray
     * @pre{ }
     * @steps{
     * - Open Monitor from tray
     * - Verify Build monitor opened}
     * @result{ - Build monitor window is present}
     */
    @Test(testName = "Verify Monitor Opened From Tray")
    public void verifyMonitorOpenedFromTray() {
        if (!project.equals("green01")) {
            test.log(Status.SKIP, "Test should run once on green project");
            throw new SkipException("Skipped test");
        }
        client.openMonitorFromTray();
        client.verifyBuildMonitorOpened();
    }

    /**
     * @test Verify History Opened From Tray
     * @pre{ }
     * @steps{
     * - Open History from tray
     * - Verify Build history opened}
     * @result{ - Build history window is present}
     */
    @Test(testName = "Verify History Opened From Tray")
    public void verifyHistoryFromTray() {
        if (!project.equals("green01")) {
            test.log(Status.SKIP, "Test should run once on green project");
            throw new SkipException("Skipped test");
        }
        client.openHistoryFromTray();
        client.verifyBuildHistoryOpened();
    }

    /**
     * @test Verify Coordinator Monitor Opened From Tray
     * @pre{ }
     * @steps{
     * - Open Coordinator monitor from tray
     * - Verify Coordinator Monitor Opened}
     * @result{ - Coordinator monitor window is opened}
     */
    @Test(testName = "Verify Coord Monitor Opened From Tray")
    public void verifyCoordMonitorOpenedFromTray() {
        if (!project.equals("green01")) {
            test.log(Status.SKIP, "Test should run once on green project");
            throw new SkipException("Skipped test");
        }
        client.openCoordMonitorFromTray();
        SystemActions.sleep(30);
        client.verifyCoordinatorMonitorOpened();
    }

    /**
     * @test Verify Coordinator Settings Opened as Administrator
     * @pre{ }
     * @steps{
     * - Open Coordinator Setting as Administrator
     * - Verify Coordinator Monitor Opened}
     * @result{ - Coordinator Settings option is enabled}
     */
    @Test(testName="Run Coordinator settings as Administrator")
    public void RunCoordSetingsAsAdministrator(){
       SystemActions.doesFileExist(COORD_SETTINGS_LOCATION);
        winService.runCommandDontWaitForTermination(Processes.COORDMONITOR);
        if (!project.equals("green01")) {
            test.log(Status.SKIP, "Test should run once on green project");
            throw new SkipException("Skipped test");
        }
        SystemActions.sleep(30);
        client.verifyCoordinatorMonitorOpened();
        try {
            screen.wait(CoordMonitor.ToolsMenu.similar((float)0.9), 15).click();
            screen.wait(CoordMonitor.StopServiceMenu.similar((float) 0.95), 15);
            boolean objectExists = false;
            if (screen.exists(CoordMonitor.StopServiceMenu.similar((float) 0.95), 15) != null)
                objectExists = true;
            Assert.assertTrue(objectExists, "Could not find Stop Service");
        }catch(FindFailed findFailed) {
            test.log(Status.WARNING, "Stop coordinator service is not enabled, failed with error: " + findFailed.getMessage());
            Assert.fail();
        }
    }

    /**
     * @test Verify Agent Settings Opened From Try
     * @pre{ }
     * @steps{
     * - Open Agent Settings from tray
     * - Verify Agent Settings opened}
     * @result{ - Agent Settings window is opened}
     */
    @Test(testName = "Verify Agent Settings Opened From Tray")
    public void verifyAgentSettingsOpenedFromTray() {
        if (!project.equals("green01")) {
            test.log(Status.SKIP, "Test should run once on green project");
            throw new SkipException("Skipped test");
        }
        client.openAgentSettingsFromTray();
        client.verifyAgentSettingsOpened();
    }

    //TODO
    /**
     * @test Verify Build Groups default state is OFF
     * @pre{ }
     * @steps{
     * - Open Coordinator Setting from Tray
     * - Click to Advanced option of CoordSettings
     * @result{ - Allow Agents to register to Build Group is disabled}
     */
     @Test(testName="Verify Build Groups default state is OFF")
      public void verifyBuildGroupsIsOff() {
      client.clickAdvancedOptionOfCoordSettings();
      if (!project.equals("green01")) {
        test.log(Status.SKIP, "Test should run once on green project");
        throw new SkipException("Skipped test");
      }
      try {
        screen.wait(IBSettings.AllowAgentsToBuildGroups.similar((float) 0.9), 10);
      }catch(FindFailed findFailed) {
        test.log(Status.WARNING, "Build Groups is not disabled, failed with error: " + findFailed.getMessage());
        Assert.fail();
      }
     }

}// end of UIValidationsTests class
