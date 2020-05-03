package Web.DashboardWeb;

import frameworkInfra.testbases.web.dashboard.OverviewTabTestBase;
import org.testng.Assert;
import org.testng.annotations.Test;
/**
 * @brief<b> <b>Overview Tab Tests </b>
 * @details vm: h6-w10-dashbord on Srv-10
 */
public class OverviewTabTests extends OverviewTabTestBase {
    /**
     * @test Verify Saved Time
     * @pre{ }
     * @steps{
     * - Convert String Time To Epoch
     * - Get Total Saved Time
     * }
     * @result{ - saved TIme, saved Time UI
     * }
     */
    @Test(testName = "Verify Saved Time")
    public void verifySavedTime() {
        long savedTimeUI = dashboardHelper.convertStringTimeToEpoch(overviewPageObject.getTimeSaved());
        long savedTIme = dashboardHelper.getTotalSavedTime() / 1000;
        Assert.assertEquals(savedTIme, savedTimeUI, "Saved time in DB does not match UI");
    }
    /**
     * @test Verify Saved Cost
     * @pre{ }
     * @steps{
     * - Get Cost Saved
     * - Calculate Cost Saved
     * }
     * @result{ - saved Cost, saved Cost UI
     * }
     */
    @Test(testName = "Verify Saved Cost")
    public void verifySavedCost() {
        String savedCostUI = overviewPageObject.getCostSaved();
        long savedCost = dashboardHelper.calculateCostSaved();
        Assert.assertEquals(savedCost, Long.parseLong(savedCostUI), "Saved cost in DB does not match UI");
    }
    /**
     * @test Verify Local Processing Time
     * @pre{ }
     * @steps{
     * - GetLocal Processing Time
     * - Get Long From Query
     * }
     * @result{ - local PT, local PT UI
     * }
     */
    @Test(testName = "Verify Local Processing Time")
    public void verifyLocalProcessingTime() {
        long localPTUI = dashboardHelper.convertStringTimeToEpoch(overviewPageObject.getLocalProcessingTime());
        long localPT = postgresJDBC.getLongFromQuery("localhost", "ib", "ib", "coordinatordb", "SUM (total_local_time) ", "coord_build ");
        localPT = localPT/1000;
        Assert.assertEquals(localPT, localPTUI, "Local processing time in DB does not match UI");
    }
    /**
     * @test Verify Remote Processing Time
     * @pre{ }
     * @steps{
     * - Get Remote Processing Time
     * - Get Long From Query
     * }
     * @result{ - local PT, local PT UI
     * }
     */
    @Test(testName = "Verify Remote Processing Time")
    public void verifyRemoteProcessingTime() {
        long localPTUI = dashboardHelper.convertStringTimeToEpoch(overviewPageObject.getRemoteProcessingTime());
        long localPT = postgresJDBC.getLongFromQuery("localhost", "ib", "ib", "coordinatordb", "SUM (total_remote_time) ", "coord_build ");
        localPT = localPT/1000;
        Assert.assertEquals(localPT, localPTUI, "Local processing time in DB does not match UI");
    }
}
