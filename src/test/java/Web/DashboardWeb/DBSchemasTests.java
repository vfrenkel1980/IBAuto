package Web.DashboardWeb;

import com.aventstack.extentreports.Status;
import frameworkInfra.testbases.web.dashboard.DBSchemasTestBase;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.StaticDataProvider.*;
import org.sikuli.script.FindFailed;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static frameworkInfra.Listeners.SuiteListener.test;

public class DBSchemasTests extends DBSchemasTestBase {

    @Test(testName = "Install Older Schema")
    public void installOlderSchema() {
        String previousScheme = postgresJDBC.getTheNthRowFromEnd("192.168.10.73", "postgres", "postgres123", "release_manager", "*", "sqlite_schema_version", 2);
        String versionToInstall = postgresJDBC.getSingleValueWithCondition("192.168.10.73", "postgres", "postgres123", "release_manager", "*",
                "windows_builds_ib_info", "sqlite_db_version=\'" + previousScheme + "\'");
        ibService.installIB(versionToInstall, IbLicenses.DASHBOARD_LIC);
        ibService.cleanAndBuild(StaticDataProvider.IbLocations.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.ConsoleAppProj.CONSOLE_APP_SUCCESS, "%s"));
        ibService.decryptSQLiteDB("old");
        int successful = sqLiteJDBC.getIntFromQuery("", "", "", "", "COUNT(*) ", "coord_build ", "status IN (0) AND build_type IN (1,3)");
        Assert.assertEquals(successful, 1, "Number of successful builds does not match expected");
    }

    @Test(testName = "Upgrade IB To Latest Schema", dependsOnMethods = "installOlderSchema")
    public void upgradeIBToLatestSchema() {
        ibService.updateIB("Latest");
        ibService.decryptSQLiteDB("new");
        int successful = sqLiteJDBC.getIntFromQuery("", "", "", "", "COUNT(*) ", "coord_build ", "status IN (0) AND build_type IN (1,3)");
        Assert.assertEquals(successful, 0, "Number of successful builds does not match expected");
    }

    @Test(testName = "Upgrade To Older Version Of Ent", dependsOnMethods = "upgradeIBToLatestSchema")
    public void upgradeToOlderVersionOfEnt() {
        String previousScheme = postgresJDBC.getTheNthRowFromEnd("192.168.10.73", "postgres", "postgres123", "release_manager", "*", "postgres_schema_version", 2);
        String versionToInstall = postgresJDBC.getSingleValueWithCondition("192.168.10.73", "postgres", "postgres123", "release_manager", "*",
                "windows_builds_ib_info", "postgres_db_version=\'" + previousScheme + "\'");
        ibService.cleanAndBuild(StaticDataProvider.IbLocations.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.ConsoleAppProj.CONSOLE_APP_SUCCESS, "%s"));
        ibuiService.startEntInstaller(versionToInstall);
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
        int successful = postgresJDBC.getIntFromQuery("localhost", "ib", "ib", "coordinatordb", "COUNT(*) ", "coord_build ", "status IN (0) AND build_type IN (1,3)");
        Assert.assertEquals(successful, 0, "Number of successful builds does not match expected");
    }

    @Test(testName = "Upgrade To Latest Version Of Ent", dependsOnMethods = "upgradeToOlderVersionOfEnt")
    public void upgradeToLatestVersionOfEnt() {
        ibService.cleanAndBuild(StaticDataProvider.IbLocations.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.ConsoleAppProj.CONSOLE_APP_SUCCESS, "%s"));
        ibService.upgradeToEnt();
        int successful = postgresJDBC.getIntFromQuery("localhost", "ib", "ib", "coordinatordb", "COUNT(*) ", "coord_build ", "status IN (0) AND build_type IN (1,3)");
        Assert.assertEquals(successful, 1, "Number of successful builds does not match expected");
    }

    @Test(testName = "Downgrade To Latest Pro Schema", dependsOnMethods = "upgradeToLatestVersionOfEnt")
    public void downgradeToLatestProSchema() {
        ibService.downgradeEntToPro("Latest");
        ibService.decryptSQLiteDB("new");
        int successful = sqLiteJDBC.getIntFromQuery("", "", "", "", "COUNT(*) ", "coord_build ", "status IN (0) AND build_type IN (1,3)");
        Assert.assertEquals(successful, 0, "Number of successful builds does not match expected");
    }

    @Test(testName = "Upgrade Pro To Latest Ent", dependsOnMethods = "downgradeToLatestProSchema")
    public void upgradeProToLatestEnt() {
        ibService.cleanAndBuild(StaticDataProvider.IbLocations.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.ConsoleAppProj.CONSOLE_APP_SUCCESS, "%s"));
        ibService.upgradeToEnt();
        int successful = postgresJDBC.getIntFromQuery("localhost", "ib", "ib", "coordinatordb", "COUNT(*) ", "coord_build ", "status IN (0) AND build_type IN (1,3)");
        Assert.assertEquals(successful, 1, "Number of successful builds does not match expected");
    }

    @Test(testName= "Verify ExitCodeBase in Ent DB", dependsOnMethods = "upgradeProToLatestEnt")
    public void verifyExitCodeBaseInEntDB(){
        ibService.cleanAndBuild(StaticDataProvider.IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_FAIL_EXIT3 + " /exitcodebase " , "%s"));
        String latest = postgresJDBC.getLastValueFromTable("localhost", "ib", "ib", "coordinatordb", "* ", "coord_build ", "status","end_time");
        Assert.assertTrue(latest.equals("3"), "Exitcode base errorlevel does not match expected");
    }

    @Test(testName= "Verify Predicted Off Exitcode in Ent DB", dependsOnMethods = "upgradeProToLatestEnt")
    public void verifyPredictedOffExitCodeInEntDB(){
        setRegistry("0", RegistryKeys.PREDICTED);
        ibService.cleanAndBuild(StaticDataProvider.IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_FAIL, "%s"));
        int latest = postgresJDBC.getIntFromQuery("localhost", "ib", "ib", "coordinatordb", "COUNT(*) ", "coord_build ", "status IN (0) AND build_type IN (1,3)");
        setRegistry("2", RegistryKeys.PREDICTED);
        Assert.assertEquals(latest, 1, "Exitcode should be overwritten from -1 to 1");
    }

    /*------------------------------METHODS------------------------------*/

    private void setRegistry(String required, String keyName){
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\builder", keyName, required);
    }

}
