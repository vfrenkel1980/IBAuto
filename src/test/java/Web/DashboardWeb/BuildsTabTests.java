package Web.DashboardWeb;

import frameworkInfra.testbases.web.dashboard.BuildsTabTestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BuildsTabTests extends BuildsTabTestBase {
    //KPI
    @Test(testName = "Builds KPI All Executed Builds")
    public void buildsAllKPIExecutedBuilds() {
        buildPageObject.goToAllTab();
        String successBuildsUI = buildPageObject.getSuccessfulBuildsUI();
        String failedBuildsUI = buildPageObject.getFailedBuildsUI();
        String totalBuildsUI = buildPageObject.getTotalBuildsUI();
        int successBuilds = dashboardHelper.getAllBuilds("0");
        int failedBuilds = dashboardHelper.getAllBuilds("1, 2");
        int totalBuilds = successBuilds + failedBuilds;
        Assert.assertEquals(successBuildsUI, Integer.toString(successBuilds), "Successful builds values are different");
        Assert.assertEquals(failedBuildsUI, Integer.toString(failedBuilds), "Failed builds values are different");
        Assert.assertEquals(totalBuildsUI, Integer.toString(totalBuilds), "Failed builds values are different");
    }

    @Test(testName = "Builds KPI Distributed Time All")
    public void buildsAllKPIDistributedTime() {
        buildPageObject.goToAllTab();
        String distributedTimeUI = buildPageObject.getDistributedTimeUI();
        int allBuildsDistributedTime = dashboardHelper.getBuildsDistributedCoreHours("0,1,2") / dashboardHelper.getBuildsDurationCoreHours("0,1,2") * 100;
        Assert.assertEquals(distributedTimeUI, Integer.toString(allBuildsDistributedTime), "Builds KPI Distributed Time values are different");
    }

    @Test(testName = "Builds KPI All Avg Build Duration")
    public void buildsAllKPIAvgBuildDuration() {
        buildPageObject.goToAllTab();
        long avgBuildDurUI = dashboardHelper.convertStringTimeToEpoch(buildPageObject.getAvgBuildDurationUI());
        long avgBuildDur = dashboardHelper.getAvgBuildDuration();
        Assert.assertEquals(avgBuildDurUI, avgBuildDur, "Builds KPI Avg Build Duration values are different");
    }

/*    //Top Initiator Agents
    @Test(testName = "Builds KPI All Top 5 Build Time")
    public void BuildsKPIAllTop5BuildTime() {
        buildPageObject.goToBuildsPage();
        buildPageObject.goToAllTab();
        for(int i=0;i<=5;i++) {
            String distributedTimeInitiatorUI = buildPageObject.getTopBuildTimeInitiator(i);
            String distributedTimeDurationUI = buildPageObject.getTopBuildTimeInitiator(i);

        }
    }*/



}