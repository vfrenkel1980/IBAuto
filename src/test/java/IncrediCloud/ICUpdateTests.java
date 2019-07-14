package IncrediCloud;

import frameworkInfra.testbases.incrediCloud.CloudUpdateTestBase;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import org.testng.Assert;
import org.testng.annotations.Test;

import static frameworkInfra.utils.StaticDataProvider.WindowsMachines.IC_COORDINATOR;

public class ICUpdateTests extends CloudUpdateTestBase {

    /**
     * @test Perform IC onboarding artificially suing redirect link and registry manipulation<br>
     * @pre{ this is the only test in the class that uses chromedriver</a>}
     * @steps{
     * - perform onboarding}
     * @result{
     * - IC is registered on the coordinator and cloud resource is ready}
     *
     * @todo once AWS implementation is ready, modify the test
     */
    @Test(testName = "Perform Onboarding Before Update")
    public void performOnboardingBeforeUpdate(){
        startWebServerThread();
        onboardingPageObject.clickTryIncredicloud();
        cloudRegistrationPageObject.selectUser(PROD_USER);
        onboardingPageObject.performOnboarding(onboardingPage);
        waitForWebServerResponse();
        icService.setSecret(webServer.secret);
        icService.setSecretInRegistry();
        winService.restartService(WindowsServices.COORD_SERVICE);
        icService.loginToCloud();
        Assert.assertTrue(icService.waitForDeliveredMachines(POOL_SIZE), "Number of delivered machines is not equal to " + POOL_SIZE);
    }

    /**
     * @test increase number of machines in pool<br>
     *
     * @steps{
     * - update cloud settings
     * - verify changes committed
     * }
     * @result{
     * - Cloud settings updated}
     *
     */
    @Test(testName = "Update - Add Machines To Pool", dependsOnMethods = {"performOnboardingBeforeUpdate"})
    public void updateAddMachinesToPool(){
        onboardingPageObject.clickTryIncredicloud();
        cloudRegistrationPageObject.selectUser(PROD_USER);
        onboardingPageObject.performUpdate(updateIncreasePoolSize);
        winService.runCommandDontWaitForTermination(String.format(ProjectsCommands.MISC_PROJECTS.TEST_SAMPLE, GRID_CORES_AFTER_INCREASE, "900000"));
        icService.waitForDeliveredMachines(updateIncreasePoolSize.getPoolSize());
        SystemActions.sleep(20);
        int machinesParticipatingInBuild = ibService.getNumberOfMachinesParticipateInBuild(IC_COORDINATOR);
        winService.waitForProcessToFinish(Processes.BUILDSYSTEM);
        Assert.assertEquals(machinesParticipatingInBuild, updateIncreasePoolSize.getPoolSize(), "Number of machines participating in build is different then pool size " + updateIncreasePoolSize.getPoolSize());
    }

    /**
     * @test decrease number of machines in pool<br>
     *
     * @steps{
     * - update cloud settings
     * - verify changes committed
     * }
     * @result{
     * - Cloud settings updated}
     *
     */
    @Test(testName = "Update - Remove Machines From Pool", dependsOnMethods = {"updateAddMachinesToPool"})
    public void updateRemoveMachinesFromPool(){
        onboardingPageObject.clickTryIncredicloud();
        cloudRegistrationPageObject.selectUser(PROD_USER);
        onboardingPageObject.performUpdate(updateDecreasePoolSize);
        winService.runCommandDontWaitForTermination(String.format(ProjectsCommands.MISC_PROJECTS.TEST_SAMPLE, GRID_CORES_AFTER_DECREASE, "900000"));
        SystemActions.sleep(TIMEOUT + 30);
        icService.waitForDeliveredMachines(updateDecreasePoolSize.getPoolSize());
        SystemActions.sleep(20);
        int machinesParticipatingInBuild = ibService.getNumberOfMachinesParticipateInBuild(IC_COORDINATOR);
        winService.waitForProcessToFinish(Processes.BUILDSYSTEM);
        Assert.assertEquals(machinesParticipatingInBuild, updateDecreasePoolSize.getPoolSize(), "Number of machines participating in build is different then pool size " + updateDecreasePoolSize.getPoolSize());
    }

    //TODO: when we have the API to query the resource - verify new security group is created.
    /**
     * @test Change ports numbers<br>
     *
     * @steps{
     * - update cloud settings
     * - verify changes committed
     * }
     * @result{
     * - when machines are deallocated new machines should be created with the new configuration}
     *
     */
    @Test(testName = "Update - Change Ports", dependsOnMethods = {"updateRemoveMachinesFromPool"})
    public void updateChangePorts(){
        onboardingPageObject.clickTryIncredicloud();
        cloudRegistrationPageObject.selectUser(PROD_USER);
        onboardingPageObject.performUpdate(updatePorts);
        SystemActions.sleep(TIMEOUT + 30);
        winService.runCommandDontWaitForTermination(String.format(ProjectsCommands.MISC_PROJECTS.TEST_SAMPLE, GRID_CORES_AFTER_DECREASE, "900000"));
        icService.waitForDeliveredMachines(updateDecreasePoolSize.getPoolSize());
        SystemActions.sleep(20);
        int machinesParticipatingInBuild = ibService.getNumberOfMachinesParticipateInBuild(IC_COORDINATOR);
        winService.waitForProcessToFinish(Processes.BUILDSYSTEM);
        Assert.assertEquals(machinesParticipatingInBuild, updateDecreasePoolSize.getPoolSize(), "Number of machines participating in build is different then pool size " + updateDecreasePoolSize.getPoolSize());
    }
}
