package Web.DashboardWeb;

import frameworkInfra.testbases.web.dashboard.BuildsTabTestBase;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.LinkedHashMap;
/**
 * @brief<b> <b>Builds Tab Tests</b>
 * @details vm: h6-w10-dashbord on Srv-10
 */
public class BuildsTabTests extends BuildsTabTestBase {
    private static final String ALL = "All";
    private static final String TODAY = "Today";
    private static final String H24 = "H24";
    private static final String H12 = "H12";

//ALL
    @Test(testName = "Verify Builds All KPI Executed")
    public void verifyBuildsAllKPIExecuted() {
        buildPageObject.goToTab(ALL);
        String successBuildsUI = buildPageObject.getSuccessfulBuildsUI();
        String failedBuildsUI = buildPageObject.getFailedBuildsUI();
        String totalBuildsUI = buildPageObject.getTotalBuildsUI();
        int successBuilds = dashboardHelper.getBuilds("0", ALL);
        int failedBuilds = dashboardHelper.getBuilds("1, 2", ALL);
        int totalBuilds = successBuilds + failedBuilds;
        Assert.assertEquals(successBuildsUI, Integer.toString(successBuilds), "Successful builds values in DB don't match UI");
        Assert.assertEquals(failedBuildsUI, Integer.toString(failedBuilds), "Failed builds values in DB don't match UI");
        Assert.assertEquals(totalBuildsUI, Integer.toString(totalBuilds), "Failed builds values in DB don't match UI");
    }

    @Test(testName = "Verify Builds All KPI Avg Duration")
    public void verifyBuildsAllKPIAvgDuration() {
        buildPageObject.goToTab(ALL);
        long avgBuildDurUI = dashboardHelper.convertStringTimeToEpoch(buildPageObject.getAvgBuildDurationUI());
        long avgBuildDur = dashboardHelper.getAvgBuildDuration("0,1,2", ALL);
        Assert.assertEquals(avgBuildDurUI, avgBuildDur, "Builds KPI Avg Build Duration values in DB don't match UI");
    }

    @Test(testName = "Verify Builds All KPI Distributed Time")
    public void verifyBuildsAllKPIDistributedTime() {
        buildPageObject.goToTab(ALL);
        String distributedTimeUI = buildPageObject.getDistributedTimeUI();
        int distributedTime = dashboardHelper.getBuildsDistributedCoreHours("0,1,2", ALL);
        int buildTime = dashboardHelper.getBuildsDurationCoreHours("0,1,2", ALL) * 100;
        int procentDistributedTime = 0;
        if (buildTime != 0) {
            procentDistributedTime = distributedTime / buildTime;
        }
        Assert.assertEquals(distributedTimeUI, Integer.toString(procentDistributedTime), "Builds KPI Distributed Time values in DB don't match UI");
    }

    @Test(testName = "Verify Builds All KPI Top 5 Build Time", enabled = false)
    public void verifyBuildsAllKPITop5BuildTime() {
        buildPageObject.goToTab(ALL);
        LinkedHashMap<String, String> topInitiators = dashboardHelper.getAllBuildsTopInitiatorAgentsTime(5, ALL);
        for (int i = 0; i <= 5; i++) {
            String topInitiatorDurationUI = buildPageObject.getTopBuildTimeInitiator(i + 1);
            String topInitiatorNameUI = buildPageObject.getTopBuildTimeInitiator(i + 1);
            String topInitiatorDuration = (String) topInitiators.values().toArray()[i];
            String topInitiatorName = (String) topInitiators.keySet().toArray()[i];
            Assert.assertEquals(topInitiatorDurationUI, topInitiatorDuration, " All Builds Top # " + i + " Initiator Duration times in DB don't match UI ");
            Assert.assertEquals(topInitiatorNameUI, topInitiatorName, " All Builds Top # " + i + " Initiator names in DB don't match UI");
        }
    }

//TODAY
    @Test(testName = "Verify Builds Today KPI Executed")
    public void verifyBuildsTodayKPIExecuted() {
        buildPageObject.goToTab(TODAY);
        String successBuildsUI = buildPageObject.getSuccessfulBuildsUI();
        String failedBuildsUI = buildPageObject.getFailedBuildsUI();
        String totalBuildsUI = buildPageObject.getTotalBuildsUI();
        int successBuilds = dashboardHelper.getBuilds("0", TODAY);
        int failedBuilds = dashboardHelper.getBuilds("1, 2", TODAY);
        int totalBuilds = successBuilds + failedBuilds;
        Assert.assertEquals(successBuildsUI, Integer.toString(successBuilds), "Successful builds values in DB don't match UI");
        Assert.assertEquals(failedBuildsUI, Integer.toString(failedBuilds), "Failed builds values in DB don't match UI");
        Assert.assertEquals(totalBuildsUI, Integer.toString(totalBuilds), "Failed builds values in DB don't match UI");
    }

    @Test(testName = "Verify Builds Today KPI Avg Duration")
    public void verifyBuildsTodayKPIAvgDuration() {
        buildPageObject.goToTab(TODAY);
        long avgBuildDurUI = dashboardHelper.convertStringTimeToEpoch(buildPageObject.getAvgBuildDurationUI());
        long avgBuildDur = dashboardHelper.getAvgBuildDuration("0,1,2", TODAY);
        Assert.assertEquals(avgBuildDurUI, avgBuildDur, "Builds KPI Avg Build Duration values in DB don't match UI");
    }

    @Test(testName = "Verify Builds Today KPI Distributed Time")
    public void verifyBuildsTodayKPIDistributedTime() {
        buildPageObject.goToTab(TODAY);
        String distributedTimeUI = buildPageObject.getDistributedTimeUI();
        int distributedTime = dashboardHelper.getBuildsDistributedCoreHours("0,1,2", TODAY);
        int buildTime = dashboardHelper.getBuildsDurationCoreHours("0,1,2", TODAY) * 100;
        int procentDistributedTime = 0;
        if (buildTime != 0) {
            procentDistributedTime = distributedTime / buildTime;
        }
        Assert.assertEquals(distributedTimeUI, Integer.toString(procentDistributedTime), "Builds KPI Distributed Time values in DB don't match UI");
    }

//12H
    @Test(testName = "Verify Builds 12H KPI Executed")
    public void verifyBuilds12HKPIExecuted() {
        buildPageObject.goToTab(H12);
        String successBuildsUI = buildPageObject.getSuccessfulBuildsUI();
        String failedBuildsUI = buildPageObject.getFailedBuildsUI();
        String totalBuildsUI = buildPageObject.getTotalBuildsUI();
        int successBuilds = dashboardHelper.getBuilds("0", H12);
        int failedBuilds = dashboardHelper.getBuilds("1, 2", H12);
        int totalBuilds = successBuilds + failedBuilds;
        Assert.assertEquals(successBuildsUI, Integer.toString(successBuilds), "Successful builds values in DB don't match UI");
        Assert.assertEquals(failedBuildsUI, Integer.toString(failedBuilds), "Failed builds values in DB don't match UI");
        Assert.assertEquals(totalBuildsUI, Integer.toString(totalBuilds), "Failed builds values in DB don't match UI");
    }

    @Test(testName = "Verify Builds 12H KPI Avg Duration")
    public void verifyBuilds12HKPIAvgDuration() {
        buildPageObject.goToTab(H12);
        long avgBuildDurUI = dashboardHelper.convertStringTimeToEpoch(buildPageObject.getAvgBuildDurationUI());
        long avgBuildDur = dashboardHelper.getAvgBuildDuration("0,1,2", H12);
        Assert.assertEquals(avgBuildDurUI, avgBuildDur, "Builds KPI Avg Build Duration values in DB don't match UI");
    }

    @Test(testName = "Verify Builds 12H KPI Distributed Time")
    public void verifyBuilds12HKPIDistributedTime() {
        buildPageObject.goToTab(H12);
        String distributedTimeUI = buildPageObject.getDistributedTimeUI();
        int distributedTime = dashboardHelper.getBuildsDistributedCoreHours("0,1,2", H12);
        int buildTime = dashboardHelper.getBuildsDurationCoreHours("0,1,2", H12) * 100;
        int procentDistributedTime = 0;
        if (buildTime != 0) {
            procentDistributedTime = distributedTime / buildTime;
        }
        Assert.assertEquals(distributedTimeUI, Integer.toString(procentDistributedTime), "Builds KPI Distributed Time values in DB don't match UI");
    }

//24H
    @Test(testName = "Verify Builds 24H KPI Executed")
    public void verifyBuilds24HKPIExecuted() {
        buildPageObject.goToTab(H24);
        String successBuildsUI = buildPageObject.getSuccessfulBuildsUI();
        String failedBuildsUI = buildPageObject.getFailedBuildsUI();
        String totalBuildsUI = buildPageObject.getTotalBuildsUI();
        int successBuilds = dashboardHelper.getBuilds("0", H24);
        int failedBuilds = dashboardHelper.getBuilds("1, 2", H24);
        int totalBuilds = successBuilds + failedBuilds;
        Assert.assertEquals(successBuildsUI, Integer.toString(successBuilds), "Successful builds values in DB don't match UI");
        Assert.assertEquals(failedBuildsUI, Integer.toString(failedBuilds), "Failed builds values in DB don't match UI");
        Assert.assertEquals(totalBuildsUI, Integer.toString(totalBuilds), "Failed builds values in DB don't match UI");
    }

    @Test(testName = "Verify Builds 24H KPI Avg Duration")
    public void verifyBuilds24HKPIAvgDuration() {
        buildPageObject.goToTab(H24);
        long avgBuildDurUI = dashboardHelper.convertStringTimeToEpoch(buildPageObject.getAvgBuildDurationUI());
        long avgBuildDur = dashboardHelper.getAvgBuildDuration("0,1,2", H24);
        Assert.assertEquals(avgBuildDurUI, avgBuildDur, "Builds KPI Avg Build Duration values in DB don't match UI");
    }

    @Test(testName = "Builds 24H KPI Distributed Time")
    public void verifyBuilds24HKPIDistributedTime() {
        buildPageObject.goToTab(H24);
        String distributedTimeUI = buildPageObject.getDistributedTimeUI();
        int distributedTime = dashboardHelper.getBuildsDistributedCoreHours("0,1,2", H24);
        int buildTime = dashboardHelper.getBuildsDurationCoreHours("0,1,2", H24) * 100;
        int procentDistributedTime = 0;
        if (buildTime != 0) {
            procentDistributedTime = distributedTime / buildTime;
        }
        Assert.assertEquals(distributedTimeUI, Integer.toString(procentDistributedTime), "Builds KPI Distributed Time values in DB don't match UI");
    }
}