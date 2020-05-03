package Web.DashboardWeb;

import frameworkInfra.testbases.web.dashboard.DashboardTestBase;
import frameworkInfra.utils.StaticDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;
/**
 * @brief<b> <b>Downgrade Tests </b>
 * @details vm: h6-w10-dashbord on Srv-10
 */
public class DowngradeTests extends DashboardTestBase {

    /**
     * @test Perform Downgrade To Pro
     * @pre{ }
     * @steps{
     * - Downgrade Enterprise To Pro
     * }
     * @result{ - exitCode expected 0
     * }
     */
    @Test(testName = "Perform Downgrade To Pro")
    public void performDowngradeToPro() {
        int exitCode = ibService.downgradeEntToPro(IB_VERSION);
        Assert.assertEquals(exitCode, 0, "Enterprise Downgrade finished with Exit Code different than 0");
    }
    /**
     * @test Verify Pro DB After Building Projects
     * @pre{
     *   - depends on methods  "performDowngradeToPro"
     * }
     * @steps{
     * - Clean And Build CONSOLE_APP_SUCCESS project
     * - Clean And Build CONSOLE_APP_FAIL project
     * - decrypt SQLite DB
     * - get Successful Int From Query
     * - get Failed Int From Query
     * }
     * @result{ -  expected 2
     * }
     */
    @Test(testName = "Verify Pro DB After Building Projects", dependsOnMethods = "performDowngradeToPro")
    public void verifyProDBAfterBuildingProjects() {
        ibService.cleanAndBuild(StaticDataProvider.IbLocations.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.ConsoleAppProj.CONSOLE_APP_SUCCESS, "%s"));
        ibService.cleanAndBuild(StaticDataProvider.IbLocations.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.ConsoleAppProj.CONSOLE_APP_FAIL, "%s"));
        ibService.decryptSQLiteDB("new");
        int successful = sqLiteJDBC.getIntFromQuery("", "", "", "", "COUNT(*) ", "coord_build ", "status IN (0) AND build_type IN (1,3)");
        int failed = sqLiteJDBC.getIntFromQuery("", "", "", "", "COUNT(*) ", "coord_build ", "status IN (1) AND build_type IN (1,3)");
        Assert.assertEquals(successful, 2, "Number of successful builds does not match expected");
        Assert.assertEquals(failed, 2, "Number of failed builds does not match expected");
    }

}
