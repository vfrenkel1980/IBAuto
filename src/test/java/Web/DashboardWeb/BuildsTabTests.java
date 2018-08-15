package Web.DashboardWeb;

import frameworkInfra.testbases.web.dashboard.BuildsTabTestBase;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.LinkedHashMap;

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
        buildPageObject.goToAllTab();
        long avgBuildDurUI = dashboardHelper.convertStringTimeToEpoch(buildPageObject.getAvgBuildDurationUI()) * 1000;
        long avgBuildDur = getBuildsDuration("0,1,2") / getAllBuilds("0,1,2");
        Assert.assertEquals(avgBuildDurUI, avgBuildDur, "Builds KPI Avg Build Duration values are different");
    }

    @Test(testName = "Builds KPI Distributed Time All")
    public void buildsAllKPIDistributedTime() {
        buildPageObject.goToAllTab();
        String distributedTimeUI = buildPageObject.getDistributedTimeUI();
        int allBuildsDistributedTime = getBuildsDistributedCoreHours("0,1,2") / getBuildsDurationCoreHours("0,1,2") * 100;
        Assert.assertEquals(distributedTimeUI, Integer.toString(allBuildsDistributedTime), "Builds KPI Distributed Time values are different");
    }

    @Test(testName = "Builds KPI All Top 5 Build Time")
    public void BuildsKPIAllTop5BuildTime() {
        buildPageObject.goToAllTab();
        LinkedHashMap<String, String> topIniators = getAllBuildsTopInitiatorAgentsTime(5);
        for (int i = 0; i <= 5; i++) {
            String topIniatorDurationUI = buildPageObject.getTopBuildTimeInitiator(i+1);
            String topIniatorNameUI = buildPageObject.getTopBuildTimeInitiator(i+1);
            String topIniatorDuration = (String) topIniators.values().toArray()[i];
            String topIniatorName  = (String) topIniators.keySet().toArray()[i];
            Assert.assertEquals(topIniatorDurationUI, topIniatorDuration, " All Builds Top # "+i+" Initiator Duration time is dismatch ");
            Assert.assertEquals(topIniatorNameUI, topIniatorName, " All Builds Top # "+i+" Initiator name is dismatch ");
        }
    }

    private int getAllBuilds(String status) {
        return postgresJDBC.getIntFromQuery("localhost", "ib", "ib", "coordinatordb", "COUNT(*) ", "coord_build ", "status IN (" + status + ")");
    }

    private long getBuildsDuration(String status) {
        return postgresJDBC.getLongFromQuery("localhost", "ib", "ib", "coordinatordb", "SUM (end_time -start_time) ", "coord_build ", "status IN (" + status + ")");
    }

    private int getBuildsDurationCoreHours(String status) {
        return postgresJDBC.getIntFromQuery("localhost", "ib", "ib", "coordinatordb", "SUM (total_local_time + total_remote_time) ", "coord_build ", "status IN (" + status + ")");
    }

    public int getBuildsDistributedCoreHours(String status) {
        return postgresJDBC.getIntFromQuery("localhost", "ib", "ib", "coordinatordb", "total_remote_time ", "coord_build ", "status IN (" + status + ")");
    }

    public LinkedHashMap<String, String> getAllBuildsTopInitiatorAgentsTime(int limit) {
        return postgresJDBC.getLinkedHashMapFromQuery("localhost", "ib", "ib", "coordinatordb", "SUM(c.end_time - c.start_time)AS t , a.name", "coord_build as c", "agent as a ON c.agent_id = a.id", "name", "t  DESC", limit);
    }
}