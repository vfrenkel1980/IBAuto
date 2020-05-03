package Web.DashboardWeb;

import com.aventstack.extentreports.Status;
import frameworkInfra.testbases.web.dashboard.DBSchemasTestBase;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import org.sikuli.script.FindFailed;
import org.testng.Assert;

import org.testng.annotations.Test;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static frameworkInfra.Listeners.SuiteListener.test;
/**
 * @brief<b> <b>DB Schemas Tests </b>
 * @details vm: h6-w10-dashbord on Srv-10
 */
public class DBSchemasTests extends DBSchemasTestBase {

    /**
     * @test Install Older Schema
     * @pre{ }
     * @steps{
     * - Get The Nth Row From End
     * - Get Single Value With Condition
     * - Install IB with Dashboard License
     * - Clean and Build CONSOLE_APP_SUCCESS Project
     * - Decrypt SQLite DB (old)
     * - Get Successful Int From Query
     * }
     * @result{ - successful expected 1
     * }
     */
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
    /**
     * @test Upgrade IB To Latest Schema
     * @pre{
     * - depends On Methods  "installOlderSchema"
     * }
     * @steps{
     * - update IB version
     * - Decrypt SQLite DB (new)
     * - Get Successful Int From Query
     * }
     * @result{ - successful expected 0
     * }
     */
    @Test(testName = "Upgrade IB To Latest Schema", dependsOnMethods = "installOlderSchema")
    public void upgradeIBToLatestSchema() {
        ibService.updateIB(IB_VERSION);
        ibService.decryptSQLiteDB("new");
        int successful = sqLiteJDBC.getIntFromQuery("", "", "", "", "COUNT(*) ", "coord_build ", "status IN (0) AND build_type IN (1,3)");
        Assert.assertEquals(successful, 0, "Number of successful builds does not match expected");
    }
    /**
     * @test Upgrade To Older Version Of Ent
     * @pre{
     * - Depends On Methods  "upgradeIBToLatestSchema"
     * }
     * @steps{
     * - Get The Nth Row From End
     * - Get Single Value With Condition
     * - Clean and Build CONSOLE_APP_SUCCESS Project
     * - Start Ent Installer
     * - Get Successful Int From Query
     * }
     * @result{ - successful expected 0
     * }
     */
    @Test(testName = "Upgrade To Older Version Of Ent", dependsOnMethods = "upgradeIBToLatestSchema")
    public void upgradeToOlderVersionOfEnt() {
        String previousScheme = postgresJDBC.getTheNthRowFromEnd("192.168.10.73", "postgres", "postgres123", "release_manager", "*", "postgres_schema_version", 2);
        String versionToInstall = postgresJDBC.getSingleValueWithCondition("192.168.10.73", "postgres", "postgres123", "release_manager", "*",
                "windows_builds_ib_info", "postgres_db_version=\'" + previousScheme + "\'");
        ibService.cleanAndBuild(StaticDataProvider.IbLocations.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.ConsoleAppProj.CONSOLE_APP_SUCCESS, "%s"));
        ibuiService.startEntInstaller(versionToInstall);
        SystemActions.sleep(30);
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
    /**
     * @test Upgrade To Latest Version Of Ent
     * @pre{
     * - Depends On Methods  "upgradeToOlderVersionOfEnt"
     * }
     * @steps{
     * - Clean and Build CONSOLE_APP_SUCCESS Project
     * - Upgrade to Ent
     * - Get Successful Int From Query
     * }
     * @result{ - successful expected 1
     * }
     */
    @Test(testName = "Upgrade To Latest Version Of Ent", dependsOnMethods = "upgradeToOlderVersionOfEnt")
    public void upgradeToLatestVersionOfEnt() {
        ibService.cleanAndBuild(StaticDataProvider.IbLocations.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.ConsoleAppProj.CONSOLE_APP_SUCCESS, "%s"));
        ibService.upgradeToEnt();
        int successful = postgresJDBC.getIntFromQuery("localhost", "ib", "ib", "coordinatordb", "COUNT(*) ", "coord_build ", "status IN (0) AND build_type IN (1,3)");
        Assert.assertEquals(successful, 1, "Number of successful builds does not match expected");
    }
    /**
     * @test Downgrade To Latest Pro Schema
     * @pre{
     * - Depends On Methods  "upgradeToLatestVersionOfEnt"
     * }
     * @steps{
     * - Downgrade Ent to Pro
     * - Upgrade to Ent
     * - Decrypt SQLite DB (new)
     * - Get Successful Int From Query
     * }
     * @result{ - successful expected 0
     * }
     */
    @Test(testName = "Downgrade To Latest Pro Schema", dependsOnMethods = "upgradeToLatestVersionOfEnt")
    public void downgradeToLatestProSchema() {
        ibService.downgradeEntToPro(IB_VERSION);
        ibService.decryptSQLiteDB("new");
        int successful = sqLiteJDBC.getIntFromQuery("", "", "", "", "COUNT(*) ", "coord_build ", "status IN (0) AND build_type IN (1,3)");
        Assert.assertEquals(successful, 0, "Number of successful builds does not match expected");
    }
    /**
     * @test Upgrade Pro To Latest Ent
     * @pre{
     * - Depends On Methods  "downgradeToLatestProSchema"
     * }
     * @steps{
     * - Clean and Build CONSOLE_APP_SUCCESS Project
     * - Upgrade to Ent
     * - Get Successful Int From Query
     * }
     * @result{ - successful expected 1
     * }
     */
    @Test(testName = "Upgrade Pro To Latest Ent", dependsOnMethods = "downgradeToLatestProSchema")
    public void upgradeProToLatestEnt() {
        ibService.cleanAndBuild(StaticDataProvider.IbLocations.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.ConsoleAppProj.CONSOLE_APP_SUCCESS, "%s"));
        ibService.upgradeToEnt();
        int successful = postgresJDBC.getIntFromQuery("localhost", "ib", "ib", "coordinatordb", "COUNT(*) ", "coord_build ", "status IN (0) AND build_type IN (1,3)");
        Assert.assertEquals(successful, 1, "Number of successful builds does not match expected");
    }
    /**
     * @test Verify ExitCodeBase in Ent DB
     * @pre{
     * - Depends On Methods  "upgradeProToLatestEnt"
     * }
     * @steps{
     * - Build CONSOLE_APP_SUCCESS_REBUILD Project
     * - kill BUILD_CONSOLE Process
     * - Get Last Value From DB Table
     * }
     * @result{ - latest expected 4
     * }
     */
    @Test(enabled=false,testName= "Verify ExitCodeBase in Ent DB", dependsOnMethods = "upgradeProToLatestEnt")
    public void verifyExitCodeBaseInEntDB(){
        winService.runCommandDontWaitForTermination(StaticDataProvider.IbLocations.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.ConsoleAppProj.CONSOLE_APP_SUCCESS_REBUILD+" /exitcodebase "));
        SystemActions.sleep(6);
        SystemActions.killProcess(StaticDataProvider.Processes.BUILD_CONSOLE);
        SystemActions.sleep(8);
        String latest = postgresJDBC.getLastValueFromTable("localhost", "ib", "ib", "coordinatordb", " status ", "coord_build ", "status","end_time");
        Assert.assertTrue(latest.equals("4"), "Exitcode base errorlevel does not match expected. Found status "+latest);
    }
    /**
     * @test Verify Predicted Off Exit code in Ent DB
     * @pre{
     * - Depends On Methods  "upgradeProToLatestEnt"
     * }
     * @steps{
     * - Build CONSOLE_APP_SUCCESS_REBUILD Project
     * - kill BUILD_CONSOLE Process
     * - Get Last Value From DB Table
     * }
     * @result{ - latest expected 4
     * }
     */
    @Test(testName= "Verify Predicted Off Exitcode in Ent DB", dependsOnMethods = "upgradeProToLatestEnt")
    public void verifyPredictedOffExitCodeInEntDB(){
        setRegistry("0", RegistryKeys.PREDICTED);
        ibService.cleanAndBuild(StaticDataProvider.IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.ConsoleAppProj.CONSOLE_APP_FAIL, "%s"));
        String latest = postgresJDBC.getLastValueFromTable("localhost", "ib", "ib", "coordinatordb", " status ", "coord_build ", "status","end_time");
        setRegistry("2", RegistryKeys.PREDICTED);
        Assert.assertTrue(latest.equals("1"), "Exitcode should be overwritten from -1 to 1. Found status "+latest);
    }

    /*------------------------------METHODS------------------------------*/

    private void setRegistry(String required, String keyName){
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\builder", keyName, required);
    }

}
