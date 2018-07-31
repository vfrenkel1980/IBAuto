package Web.DashboardWeb;

import frameworkInfra.testbases.web.dashboard.DashboardTestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

import static frameworkInfra.utils.PostgresJDBC.*;

public class BuildsTests extends DashboardTestBase {
    //KPI
    @Test(testName = "Builds KPI All Executed Builds")
    public void buildsAllKPIExecutedBuilds() {
        buildPageObject.goToBuildsPage();
        buildPageObject.goToAllTab();
        String succeesBuildsUI = buildPageObject.getSuccesfulBuildsUI();
        String failedBuildsUI = buildPageObject.getFailedBuildsUI();
        String totalBuildsUI = buildPageObject.getTotalBuildsUI();
        int succeesBuilds = getAllBuilds("0");
        int failedBuilds = getAllBuilds("1, 2");
        int totalBuilds = succeesBuilds + failedBuilds;
        Assert.assertTrue(succeesBuildsUI.equals(Integer.toString(succeesBuilds)), "Successful builds values are different");
        Assert.assertTrue(failedBuildsUI.equals(Integer.toString(failedBuilds)), "Failed builds values are different");
        Assert.assertTrue(totalBuildsUI.equals(Integer.toString(totalBuilds)), "Failed builds values are different");
    }

    @Test(testName = "Builds KPI All Avg Build Duration")
    public void BuildsKPIAllAvgBuildDuration() {
        buildPageObject.goToBuildsPage();
        buildPageObject.goToAllTab();
        long avgBuildDurUI = buildPageObject.convertStringTimeToEpoch(buildPageObject.getAvgBuildDurationUI()) * 1000;
        long avgBuildDur = getBuildsDuration("0,1,2") / getAllBuilds("0,1,2");
        Assert.assertTrue(avgBuildDurUI == avgBuildDur, "Builds KPI Avg Build Duration values are different");
    }

    @Test(testName = "Builds KPI Distributed Time All")
    public void BuildsAllKPIDistributedTime() {
        buildPageObject.goToBuildsPage();
        buildPageObject.goToAllTab();
        String distributedTimeUI = buildPageObject.getDistributedTimeUI();
        int allBuildsdistributedTime = getBuildsDistributedCoreHours("0,1,2") / getBuildsDurationCoreHours("0,1,2") * 100;
        Assert.assertTrue(distributedTimeUI.equals(Integer.toString(allBuildsdistributedTime)), "Builds KPI Distributed Time values are different");
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


    public static int getAllBuilds(String status) {
        return getIntFromQuery("192.168.10.16", "ib", "ib", "coordinatordb", "SELECT COUNT(*) FROM coord_build WHERE status IN (" + status + ");");
    }

    public long getBuildsDuration(String status) {
        return getLongFromQuery("192.168.10.16", "ib", "ib", "coordinatordb", "SELECT SUM (end_time -start_time) FROM coord_build WHERE status IN (" + status + ");");
    }

    public int getBuildsDurationCoreHours(String status) {
        return getIntFromQuery("192.168.10.16", "ib", "ib", "coordinatordb", "SELECT SUM (total_local_time + total_remote_time) FROM coord_build WHERE status IN (" + status + ");");
    }

    public int getBuildsDistributedCoreHours(String status) {
        return getIntFromQuery("192.168.10.16", "ib", "ib", "coordinatordb", "SELECT total_remote_time FROM coord_build WHERE status IN (" + status + ");");
    }
}