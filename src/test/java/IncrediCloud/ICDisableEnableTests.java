package IncrediCloud;

import frameworkInfra.testbases.incrediCloud.ICEngineTestBase;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import org.testng.Assert;
import org.testng.annotations.Test;

import static frameworkInfra.utils.StaticDataProvider.WindowsMachines.IC_COORDINATOR;

/**
 * @brief Tests for IncrediCloud engine functionality
 * @details Requires an enterprise license
 */


public class ICDisableEnableTests extends ICEngineTestBase {

    @Test(testName = "performOnboarding")
    protected void performOnboarding() {
        startWebServerThread();
        onboardingPageObject.clickTryIncredicloud();
        cloudRegistrationPageObject.selectUser(PROD_USER, ONBOARDING_TYPE);
        onboardingPageObject.performOnboarding(onboardingPage);
        waitForWebServerResponse();
        icService.setSecret(webServer.secret);
        icService.setSecretInRegistry();
        winService.restartService(StaticDataProvider.WindowsServices.COORD_SERVICE);
        icService.loginToCloud();
        Assert.assertTrue(icService.waitForDeliveredMachines(POOL_SIZE), "Number of delivered machines is not equal to " + POOL_SIZE);
        isOnBoarding = true;
        verifyVirtualMachinesInfo();
    }

    /**
     * @test pause cloud and perform a build<br>
     * @steps{ - pause cloud using sikuli
     * - start a build
     * }
     * @result{ - no cloud machines should participate in build}
     */
    @Test(testName = "Pause Cloud", dependsOnMethods = {"performOnboarding"})
    public void pauseCloud() {
        winService.runCommandDontWaitForTermination(IbLocations.COORDMONITOR);
        coordinator.pauseCloud();
        icService.waitForDeliveredMachines(0);
        winService.runCommandDontWaitForTermination(String.format(ProjectsCommands.MISC_PROJECTS.TEST_SAMPLE, GRID_CORES, "180000"));
        SystemActions.sleep(60);
        int machinesParticipatingInBuild = ibService.getNumberOfMachinesParticipateInBuild(IC_COORDINATOR);
        winService.waitForProcessToFinish(Processes.BUILDSYSTEM);
        Assert.assertEquals(machinesParticipatingInBuild, 1, "More than one machine are participating in build.");
    }

    /**
     * @test enable cloud and perform a build<br>
     * @steps{ - enable cloud using sikuli
     * - start a build
     * }
     * @result{ - cloud machines should participate in build}
     */
    @Test(testName = "Enable Cloud", dependsOnMethods = {"pauseCloud"})
    public void enableCloud() {
        coordinator.enableCloud(false);
        winService.runCommandDontWaitForTermination(String.format(ProjectsCommands.MISC_PROJECTS.TEST_SAMPLE, ON_PREM_CORES + POOL_CORES, "240000"));
        icService.waitForDeliveredMachines(POOL_SIZE);
        int machinesParticipatingInBuild = ibService.getNumberOfMachinesParticipateInBuild(IC_COORDINATOR);
        winService.waitForProcessToFinish(Processes.BUILDSYSTEM);
        Assert.assertEquals(machinesParticipatingInBuild, POOL_SIZE + 1);
    }

    /**
     * @test pause cloud and delete pool and perform a build<br>
     * @steps{ - pause cloud and delete pool using sikuli
     * - start a build
     * }
     * @result{ - no cloud machines should participate in build}
     */
    @Test(testName = "Pause Cloud And Delete Pool", dependsOnMethods = {"enableCloud"})
    public void pauseCloudAndDeletePool() {
        coordinator.pauseCloudAndDeletePool();
        icService.waitForCloudStatus("Pool does not exist (Paused)");
        winService.runCommandDontWaitForTermination(String.format(ProjectsCommands.MISC_PROJECTS.TEST_SAMPLE, ON_PREM_CORES, "180000"));
        SystemActions.sleep(20);
        int machinesParticipatingInBuild = ibService.getNumberOfMachinesParticipateInBuild(IC_COORDINATOR);
        winService.waitForProcessToFinish(Processes.BUILDSYSTEM);
        Assert.assertEquals(machinesParticipatingInBuild, 1);
    }

    /**
     * @test enable cloud and perform a build<br>
     * @steps{ - enable cloud using sikuli
     * - start a build
     * }
     * @result{ - cloud machines should be created and participate in build}
     */
    @Test(testName = "Enable Cloud And Create New Pool", dependsOnMethods = {"pauseCloudAndDeletePool"})
    public void enableCloudAndCreateNewPool() {
        coordinator.enableCloud(true);
        winService.runCommandDontWaitForTermination(String.format(ProjectsCommands.MISC_PROJECTS.TEST_SAMPLE, GRID_CORES, "900000"));
        SystemActions.sleep(180);
        boolean cloudMachinesRunning = icService.waitForDeliveredMachines(POOL_SIZE);
        winService.waitForProcessToFinish(Processes.BUILDSYSTEM);
        Assert.assertTrue(cloudMachinesRunning, "Cloud Machines should be started");
    }

}
