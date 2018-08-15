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
        Assert.assertEquals(savedCost, Long.parseLong(savedCostUI), "Saved cost in DB does not match UI");
    }

    @Test(testName = "Verify Local Processing Time")
    public void verifyLocalProcessingTime() {
        long localPTUI = dashboardHelper.convertStringTimeToEpoch(overviewPageObject.getLocalProcessingTime());
        long localPT = postgresJDBC.getLongFromQuery("localhost", "ib", "ib", "coordinatordb", "SUM (total_local_time) ", "coord_build ");
        localPT = localPT/1000;
        Assert.assertEquals(localPT, localPTUI, "Local processing time in DB does not match UI");
    }

    @Test(testName = "Verify Remote Processing Time")
    public void verifyRemoteProcessingTime() {
        long localPTUI = dashboardHelper.convertStringTimeToEpoch(overviewPageObject.getRemoteProcessingTime());
        long localPT = postgresJDBC.getLongFromQuery("localhost", "ib", "ib", "coordinatordb", "SUM (total_remote_time) ", "coord_build ");
        localPT = localPT/1000;
        Assert.assertEquals(localPT, localPTUI, "Local processing time in DB does not match UI");
    }
}
