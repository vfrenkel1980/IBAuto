package Native.uitests;

import com.aventstack.extentreports.Status;
import frameworkInfra.testbases.UIValidationTestBase;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import ibInfra.vs.VS16UIService;
import ibInfra.vs.VSUIService;
import org.testng.SkipException;
import org.testng.annotations.Test;

import static frameworkInfra.Listeners.SuiteListener.test;

/**
 * @brief <b> UI tests</b>
 * @details Run on UI Automation (HOST-4)
 */
public class UIValidationsTests extends UIValidationTestBase {

    /**
     * @tets Verify Visual Studio 2017 Monitor Bar
     * @steps{
     * - Open VS instance version 15
     * - Open project
     * }
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
     * @steps{
     * - Open VS instance version 16
     * - Open project
     * }
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
     * @steps{
     * - Open VS instance version 116
     * - Open project
     * }
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
            client.verifyVSBarPattern(vsBarPattern);
        } catch (RuntimeException e) {
            e.getMessage();
        } finally {
            vsuiService.killDriver();
        }
    }

    /**
     * @test Verify Tray Icon Color
     * @steps{ -  Verify green tray icon}
     */
    @Test(testName = "Verify Tray Icon Color")
    public void verifyTrayIconColor() {
        client.verifyTrayIconPattern(trayIconPattern);
    }

    /**
     * @test Verify IB Monitor Bar
     * @steps{
     * - Open build monitor exe
     * - Verify Monitor Bar Pattern
     * }
     */
    @Test(testName = "Verify IB Monitor Bar")
    public void verifyIBMonitorBar() {
        ibService.openBuildMonitor();
        client.verifyMonitorBarPattern(ibMonBarPattern);
    }

    /**
     * @test Verify History Coloring
     * @steps{
     * - Run BuildHistory.exe file
     * - Verify History Coloring Pattern
     * }
     */
    @Test(testName = "Verify History Coloring")
    public void verifyHistoryColoring() {
        winService.runCommandDontWaitForTermination(IbLocations.BUILDHISTORY);
        client.verifyHistoryColoringPattern(historyPattern);
    }

    /**
     * @test Verify Projects Tab Coloring
     * @steps{
     * - Open Build Monitor
     * - Click Projects Tab
     * - Verify ProjectsTab Coloring
     * }
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
     * @steps{
     * - Open Monitor From Tray
     * - Verify Build Monitor Opened
     * }
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
     * @steps{
     * - Open History From Tray
     * - Verify Build History Opened
     * }
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
     * @steps{
     * - Open Coordinator Monitor From Tray
     * - Verify Coordinator Monitor Opened
     * }
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
     * @test Verify Agent Settings Opened From Tray
     * @steps{
     * - open Agent Settings From Tray
     * - Verify Agent Settings Opened
     * }
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

}
