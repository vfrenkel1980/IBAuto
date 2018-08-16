package Web.DashboardWeb;

import frameworkInfra.testbases.web.dashboard.BuildsTabTestBase;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.LinkedHashMap;

public class BuildsTabTests extends BuildsTabTestBase {

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

    @Test(testName = "Builds KPI All Avg Build Duration")
    public void buildsAllKPIAvgBuildDuration() {
        buildPageObject.goToAllTab();
        long avgBuildDurUI = dashboardHelper.convertStringTimeToEpoch(buildPageObject.getAvgBuildDurationUI()) * 1000;
        long avgBuildDur = dashboardHelper.getBuildsDuration("0,1,2") / dashboardHelper.getAllBuilds("0,1,2");
        Assert.assertEquals(avgBuildDurUI, avgBuildDur, "Builds KPI Avg Build Duration values are different");
    }

    @Test(testName = "Builds KPI Distributed Time All")
    public void buildsAllKPIDistributedTime() {
        buildPageObject.goToAllTab();
        String distributedTimeUI = buildPageObject.getDistributedTimeUI();
        int allBuildsDistributedTime = dashboardHelper.getBuildsDistributedCoreHours("0,1,2") / dashboardHelper.getBuildsDurationCoreHours("0,1,2") * 100;
        Assert.assertEquals(distributedTimeUI, Integer.toString(allBuildsDistributedTime), "Builds KPI Distributed Time values are different");
    }

    @Test(testName = "Builds KPI All Top 5 Build Time")
    public void BuildsKPIAllTop5BuildTime() {
        buildPageObject.goToAllTab();
        LinkedHashMap<String, String> topInitiators = dashboardHelper.getAllBuildsTopInitiatorAgentsTime(5);
        for (int i = 0; i <= 5; i++) {
            String topInitiatorDurationUI = buildPageObject.getTopBuildTimeInitiator(i+1);
            String topInitiatorNameUI = buildPageObject.getTopBuildTimeInitiator(i+1);
            String topInitiatorDuration = (String) topInitiators.values().toArray()[i];
            String topInitiatorName  = (String) topInitiators.keySet().toArray()[i];
            Assert.assertEquals(topInitiatorDurationUI, topInitiatorDuration, " All Builds Top # "+i+" Initiator Duration time is dismatch ");
            Assert.assertEquals(topInitiatorNameUI, topInitiatorName, " All Builds Top # "+i+" Initiator name is dismatch ");
        }
    }

}