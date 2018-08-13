package Web.DashboardWeb;

import frameworkInfra.testbases.web.dashboard.OverviewTabTestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OverviewTabTests extends OverviewTabTestBase {

    @Test(testName = "Verify Saved Time")
    public void verifySavedTime() {
        long savedTimeUI = dashboardHelper.convertStringTimeToEpoch(overviewPageObject.getTimeSaved());
        long savedTIme = dashboardHelper.getTotalSavedTime() / 1000;
        Assert.assertEquals(savedTIme, savedTimeUI, "Saved time in DB does not match UI");
    }

    @Test(testName = "Verify Saved Cost")
    public void verifySavedCost() {
        String savedCostUI = overviewPageObject.getCostSaved();
        long savedCost = dashboardHelper.calculateCostSaved();
        Assert.assertEquals(savedCost, Long.parseLong(savedCostUI), "Saved time in DB does not match UI");
    }
}
