package Web.DashboardWeb;

import frameworkInfra.testbases.web.dashboard.UpgradeEntTestBase;
import frameworkInfra.utils.StaticDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;
/**
 * @brief<b> <b>Upgrade Tests</b>
 * @details vm: h6-w10-dashbord on Srv-10
 */
public class UpgradeTests extends UpgradeEntTestBase {

    @Test(testName = "Perform Upgrade To Ent")
    public void performUpgradeToEnt() {
        int exitCode = ibService.upgradeToEnt();
        Assert.assertEquals(exitCode, 0, "Enterprise Installation finished with Exit Code different than 0");
    }

    @Test(testName = "Verify Ent DB After Building Projects", dependsOnMethods = "performUpgradeToEnt")
    public void verifyEntDBAfterBuildingProjects() {
        ibService.cleanAndBuild(StaticDataProvider.IbLocations.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.ConsoleAppProj.CONSOLE_APP_SUCCESS, "%s"));
        ibService.cleanAndBuild(StaticDataProvider.IbLocations.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.ConsoleAppProj.CONSOLE_APP_FAIL, "%s"));
        int successful = postgresJDBC.getIntFromQuery("localhost", "ib", "ib", "coordinatordb", "COUNT(*) ", "coord_build ", "status IN (0) AND build_type IN (1,3)");
        int failed = postgresJDBC.getIntFromQuery("localhost", "ib", "ib", "coordinatordb", "COUNT(*) ", "coord_build ", "status IN (1) AND build_type IN (1,3)");
        Assert.assertEquals(successful, 2, "Number of successful builds does not match expected");
        Assert.assertEquals(failed, 2, "Number of failed builds does not match expected");
    }

}
