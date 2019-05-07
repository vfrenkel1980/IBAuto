package IncrediCloud;

import frameworkInfra.testbases.incrediCloud.ICAzureAccountsTestBase;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.SystemActions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ICAzureAccountsTests extends ICAzureAccountsTestBase {

    /**
     * @test try to create policy while creating more machines than region allows<br>
     * @pre{ this is the only test in the class that uses chromedriver</a>}
     * @steps{
     * - perform onboarding}
     * @result{
     * - error message should appear}
     */
    @Test(testName = "Create Pool On A Limited Region")
    public void createPoolOnALimitedRegion(){
        onboardingPageObject.clickTryIncredicloud();
        azurePageObject.selectAzureUser(PROD_USER);
        onboardingPageObject.performOnboarding(failedOnboardingPage);
        Assert.assertTrue(onboardingPageObject.verifyQuotaLimitMessage(), "Quota limit message did not appear");
    }

    @Test(testName = "Perform Onboarding", dependsOnMethods = { "createPoolOnALimitedRegion"})
    public void performOnboarding(){
        SystemActions.sleep(30);
        startWebServerThread();
        onboardingPageObject.clickTryIncredicloud();
        azurePageObject.selectAzureUser(PROD_USER);
        onboardingPageObject.performOnboarding(preUpdateOnboardingPage);
        waitForWebServerResponse();
        icService.setSecret(webServer.secret);
        icService.setSecretInRegistry();
        winService.restartService(StaticDataProvider.WindowsServices.COORD_SERVICE);
        icService.loginToCloud();
        Assert.assertTrue(icService.waitForDeliveredMachines(preUpdateOnboardingPage.getPoolSize()), "Number of delivered machines is not equal to " + preUpdateOnboardingPage.getPoolSize());
    }
    /**
     * @test this test is for testing a bug which wont let us update because the sum of machines before and after the change are over the account limit<br>
     * @pre{ perform onboarding - previous test</a>}
     * @steps{
     * - perform update}
     * @result{
     * - update should complete successfully}
     */
/*    @Test(testName = "Update Policy With Sum Of Machines Is Over The Limit", dependsOnMethods = { "performOnboarding"})
    public void updatePolicyWithSumOfMachinesIsOverTheLimit(){
        onboardingPageObject.clickTryIncredicloud();
        azurePageObject.selectAzureUser(PROD_USER);
        onboardingPageObject.performUpdate(postUpdateOnboardingPage);
        boolean doesMessageAppear = onboardingPageObject.verifyQuotaLimitMessage();
        if (!doesMessageAppear)
            icService.waitForDeliveredMachines(postUpdateOnboardingPage.getPoolSize());
        Assert.assertFalse(doesMessageAppear, "Quota limit message did not appear");
    }*/

    /**
     * @test try to create policy while creating more machines than region allows<br>
     * @pre{ this is the only test in the class that uses chromedriver</a>}
     * @steps{
     * - perform onboarding}
     * @result{
     * - error message should appear}
     */
    @Test(testName = "Try To Update With A Different User", dependsOnMethods = { "performOnboarding"})
    public void tryToUpdateWithADifferentUser(){
        onboardingPageObject.clickTryIncredicloud();
        azurePageObject.selectAzureUser(LIMITED_USER);
        Assert.assertTrue(onboardingPageObject.verifyDifferentUserUpdateMessage(), "Different user update message did not appear");
    }

}
