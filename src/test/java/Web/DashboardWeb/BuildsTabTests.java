package Web.DashboardWeb;

import frameworkInfra.testbases.web.dashboard.BuildsTabTestBase;
import frameworkInfra.testbases.web.dashboard.DashboardTestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

import static frameworkInfra.utils.databases.PostgresJDBC.*;

public class BuildsTabTests extends BuildsTabTestBase {
    //KPI
    @Test(testName = "Builds KPI All Executed Builds")
    public void buildsAllKPIExecutedBuilds() {
        buildPageObject.goToAllTab();
        String successBuildsUI = buildPageObject.getSuccessfulBuildsUI();
        String failedBuildsUI = buildPageObject.getFailedBuildsUI();
        String totalBuildsUI = buildPageObject.getTotalBuildsUI();
        int successBuilds = getAllBuilds("0");
        int failedBuilds = getAllBuilds("1, 2");
        int totalBuilds = successBuilds + failedBuilds;
        Assert.assertEquals(successBuildsUI, Integer.toString(successBuilds), "Successful builds values are different");
        Assert.assertEquals(failedBuildsUI, Integer.toString(failedBuilds), "Failed builds values are different");
        Assert.assertEquals(totalBuildsUI, Integer.toString(totalBuilds), "Failed builds values are different");
    }

    @Test(testName = "Builds KPI All Avg Build Duration")
    public void buildsAllKPIAvgBuildDuration() {
        buildPageObject.goToBuildsPage();
        buildPageObject.goToAllTab();
        long avgBuildDurUI = buildPageObject.convertStringTimeToEpoch(buildPageObject.getAvgBuildDurationUI()) * 1000;
        long avgBuildDur = getBuildsDuration("0,1,2") / getAllBuilds("0,1,2");
        Assert.assertEquals(avgBuildDurUI, avgBuildDur, "Builds KPI Avg Build Duration values are different");
    }

    @Test(testName = "Builds KPI Distributed Time All")
    public void buildsAllKPIDistributedTime() {
        buildPageObject.goToBuildsPage();
        buildPageObject.goToAllTab();
        String distributedTimeUI = buildPageObject.getDistributedTimeUI();
        int allBuildsDistributedTime = getBuildsDistributedCoreHours("0,1,2") / getBuildsDurationCoreHours("0,1,2") * 100;
        Assert.assertEquals(distributedTimeUI, Integer.toString(allBuildsDistributedTime), "Builds KPI Distributed Time values are different");
    }

    //Top Initiator Agents
    @Test(testName = "Builds KPI All Top 5 Build Time")
    public void BuildsKPIAllTop5BuildTime() {
        buildPageObject.goToBuildsPage();
        buildPageObject.goToAllTab();
        for(int i=0;i<=5;i++) {
            String distributedTimeInitiatorUI = buildPageObject.getTopBuildTimeInitiator(i);
            String distributedTimeDurationUI = buildPageObject.getTopBuildTimeInitiator(i);

        }
    }


    private int getAllBuilds(String status) {
        return postgresJDBC.getIntFromQuery("localhost", "ib", "ib", "coordinatordb", "COUNT(*) ", "coord_build ", "status IN (" + status + ")");
    }

    private long getBuildsDuration(String status) {
        return postgresJDBC.getLongFromQuery("localhost", "ib", "ib", "coordinatordb", "SUM (end_time -start_time) ", "coord_build ", "status IN (" + status + ")");
    }

    private int getBuildsDurationCoreHours(String status) {
        return postgresJDBC.getIntFromQuery("localhost", "ib", "ib", "coordinatordb", "SUM (total_local_time + total_remote_time) ", "coord_build ","status IN (" + status + ")");
    }

    public int getBuildsDistributedCoreHours(String status) {
        return postgresJDBC.getIntFromQuery("localhost", "ib", "ib", "coordinatordb", "total_remote_time ","coord_build ", "status IN (" + status + ")");
    }
}