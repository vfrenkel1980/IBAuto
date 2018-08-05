package Web.DashboardWeb;

import frameworkInfra.testbases.web.dashboard.upgradeEntTestBase;
import frameworkInfra.utils.StaticDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

public class upgradeTests extends upgradeEntTestBase {

    @Test(testName = "Perform Upgrade To Ent")
    public void performUpgradeToEnt() {
        int exitCode = ibService.installEnt();
        Assert.assertEquals(exitCode, 0, "Enterprise Installation finished with Exit Code different than 0");
    }

    @Test(testName = "Verify Ent DB After Building Projects", dependsOnMethods = "performUpgradeToEnt")
    public void verifyEntDBAfterBuildingProjects() {
        ibService.cleanAndBuild(StaticDataProvider.IbLocations.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.ConsoleAppProj.CONSOLE_APP_SUCCESS, "%s"));
        ibService.cleanAndBuild(StaticDataProvider.IbLocations.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.ConsoleAppProj.CONSOLE_APP_FAIL, "%s"));
        int successful = postgresJDBC.getIntFromQuery("localhost", "ib", "ib", "coordinatordb", "COUNT(*) ", "coord_build ", "status IN (0)");
        int failed = postgresJDBC.getIntFromQuery("localhost", "ib", "ib", "coordinatordb", "COUNT(*) ", "coord_build ", "status IN (1)");
        Assert.assertEquals(successful, 2, "Number of successful builds does not match expected");
        Assert.assertEquals(failed, 2, "Number of failed builds does not match expected");
    }

}
