package IncrediCloud;

import frameworkInfra.testbases.incrediCloud.ICAzureAccountsTestBase;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;

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

    /**
     * @test install an older version of ib in order to test ib update<br>
     * @steps{
     * - install older IB version
     * - perform onboarding}
     * @result{
     * - update should complete successfully}
     */
    @Test(testName = "Perform Onboarding", dependsOnMethods = { "createPoolOnALimitedRegion"})
    public void performOnboarding(){
        ibService.updateIB("2851");
        SystemActions.sleep(30);
        startWebServerThread();
        onboardingPageObject.clickTryIncredicloud();
        azurePageObject.selectAzureUser(PROD_USER);
        onboardingPageObject.performOnboarding(preUpdateOnboardingPage);
        waitForWebServerResponse();
        icService.setSecret(webServer.secret);
        icService.setSecretInRegistry();
        winService.restartService(WindowsServices.COORD_SERVICE);
        icService.loginToCloud();
        Assert.assertTrue(icService.waitForDeliveredMachines(preUpdateOnboardingPage.getPoolSize()), "Number of delivered machines is not equal to " + preUpdateOnboardingPage.getPoolSize());
    }

    /**
     * @test verify incredicloud after IB update<br>
     * @pre{ previous test (Perform Onboarding) should have older IB version installed </a>}
     * @steps{
     * - update IB version to latest}
     * @result{
     * - update should complete successfully}
     */
    @Test(testName = "Verify Cloud After IB Update", dependsOnMethods = { "performOnboarding"})
    public void verifyCloudAfterIBUpdate(){
        ibService.updateIB(IB_VERSION);
        Assert.assertEquals(RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\Coordinator", RegistryKeys.INCREDICLOUDSECRET), webServer.secret,
                "Secret does not match after updating IB");
        SystemActions.sleep(180);
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
/*    @Test(testName = "Update Policy With Sum Of Machines Is Over The Limit", dependsOnMethods = { "verifyCloudAfterIBUpdate"})
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
    @Test(testName = "Try To Update With A Different User", dependsOnMethods = { "verifyCloudAfterIBUpdate"})
    public void tryToUpdateWithADifferentUser(){
        onboardingPageObject.clickTryIncredicloud();
        azurePageObject.selectAzureUser(LIMITED_USER);
        Assert.assertTrue(onboardingPageObject.verifyDifferentUserUpdateMessage(), "Different user update message did not appear");
    }

}
