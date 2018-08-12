package Web.DashboardWeb;

import com.aventstack.extentreports.Status;
import frameworkInfra.testbases.web.dashboard.DBSchemasTestBase;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.StaticDataProvider.*;
import org.sikuli.script.FindFailed;
import org.testng.Assert;
import org.testng.annotations.Test;

import static frameworkInfra.Listeners.SuiteListener.test;

public class DBSchemasTests extends DBSchemasTestBase {

    @Test(testName = "Install Older Schema")
    public void installOlderSchema() {
        ibService.installIB("2026", IbLicenses.DASHBOARD_LIC);
        ibService.cleanAndBuild(StaticDataProvider.IbLocations.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.ConsoleAppProj.CONSOLE_APP_SUCCESS, "%s"));
        int successful = sqLiteJDBC.getIntFromQuery("", "", "", "", "COUNT(*) ", "coord_build ", "status IN (0) AND build_type IN (1,3)");
        Assert.assertEquals(successful, 1, "Number of successful builds does not match expected");
    }

    @Test(testName = "Upgrade IB To Latest Schema", dependsOnMethods = "installOlderSchema")
    public void upgradeIBToLatestSchema() {
        ibService.updateIB("Latest");
        int successful = sqLiteJDBC.getIntFromQuery("", "", "", "", "COUNT(*) ", "coord_build ", "status IN (0) AND build_type IN (1,3)");
        Assert.assertEquals(successful, 0, "Number of successful builds does not match expected");
    }

    @Test(testName = "Upgrade To Older Version Of Ent", dependsOnMethods = "upgradeIBToLatestSchema")
    public void upgradeToOlderVersionOfEnt() {
        ibService.cleanAndBuild(StaticDataProvider.IbLocations.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.ConsoleAppProj.CONSOLE_APP_SUCCESS, "%s"));
        ibuiService.startEntInstaller("2173");
        try {
            installer.clickNext();
            installer.clickNext();
            installer.clickNext();
            installer.uncheckLaunchDashboard();
            installer.uncheckCreateEntShortcut();
            installer.uncheckReleaseNotes();
            installer.uncheckRemoteUpdate();
            installer.clickFinish();
        } catch (FindFailed e) {
            test.log(Status.ERROR, "Test failed with the following error: " + e.getMessage());
            Assert.fail();
        }
        int successful = postgresJDBC.getIntFromQuery("localhost", "ib", "ib", "coordinatordb", "COUNT(*) ", "coord_utilization_hour ", "status IN (0) AND build_type IN (1,3)");
        Assert.assertEquals(successful, 0, "Number of successful builds does not match expected");
    }

    @Test(testName = "Upgrade To Latest Version Of Ent", dependsOnMethods = "upgradeToOlderVersionOfEnt")
    public void upgradeToLatestVersionOfEnt() {
        ibService.cleanAndBuild(StaticDataProvider.IbLocations.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.ConsoleAppProj.CONSOLE_APP_SUCCESS, "%s"));
        int utilBeforeUpgrade = postgresJDBC.getIntFromQuery("localhost", "ib", "ib", "coordinatordb", "COUNT(*) ", "coord_build ", "agent_id NOT IN (0)");
        ibService.upgradeToEnt();
        int utilAfterUpgrade = postgresJDBC.getIntFromQuery("localhost", "ib", "ib", "coordinatordb", "COUNT(*) ", "coord_build ", "agent_id NOT IN (0)");
        int successful = postgresJDBC.getIntFromQuery("localhost", "ib", "ib", "coordinatordb", "COUNT(*) ", "coord_build ", "status IN (0) AND build_type IN (1,3)");
        Assert.assertEquals(successful, 1, "Number of successful builds does not match expected");
        Assert.assertEquals(utilBeforeUpgrade, utilAfterUpgrade, "Coord utilization hour table values do not match between upgrade!");
    }

    @Test(testName = "Downgrade To Latest Pro Schema", dependsOnMethods = "upgradeToLatestVersionOfEnt")
    public void downgradeToLatestProSchema() {
        ibService.downgradeEntToPro("Latest");
        int successful = sqLiteJDBC.getIntFromQuery("", "", "", "", "COUNT(*) ", "coord_build ", "status IN (0) AND build_type IN (1,3)");
        Assert.assertEquals(successful, 0, "Number of successful builds does not match expected");
    }


}
