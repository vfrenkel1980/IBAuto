package IncrediCloud;

import frameworkInfra.testbases.incrediCloud.ICEngineTestBase;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.SystemActions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static frameworkInfra.utils.StaticDataProvider.WindowsMachines.IC_COORDINATOR;

/**
 * @brief Tests for IncrediCloud engine functionality
 * @details Requires an enterprise license
 */

public class ICSanityTests extends ICEngineTestBase {
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
        //verifyVirtualMachinesInfo();
    }


    /**
     * @test Start a build and verify no unneeded cloud machines are created<br>
     * @steps{ - Build Testsample
     * - Query statusExport}
     * @result{ - No unneeded machines are created}
     */
    @Test(testName = "runBuildAndVerifyNumberOfParticipatingMachinesIsEqualToPoolSize", dependsOnMethods = {"performOnboarding"})
    public void runBuildAndVerifyNumberOfParticipatingMachinesIsEqualToPoolSize() {
        winService.runCommandDontWaitForTermination(String.format(StaticDataProvider.ProjectsCommands.MISC_PROJECTS.TEST_SAMPLE, GRID_CORES, "90000"));
        SystemActions.sleep(40);
        int machinesParticipatingInBuild = ibService.getNumberOfMachinesParticipateInBuild(IC_COORDINATOR);
        int machinesInPool = icService.getStatusQueue(false);
        winService.waitForProcessToFinish(StaticDataProvider.Processes.BUILDSYSTEM);
        Assert.assertEquals(machinesParticipatingInBuild, POOL_SIZE + 1, "Number of machines participating in build is different then pool size " + (POOL_SIZE + 1));
        Assert.assertEquals(machinesInPool, POOL_SIZE, "Number of machines in pool is different then original pool size");
    }

//    /**
//     * @test deactivate cloud and perform a build<br>
//     * @steps{ - deactivate cloud using sikuli
//     * - start a build
//     * }
//     * @result{ - no cloud machines should participate in build}
//     */
//    @Test(testName = "Deactivate Cloud", dependsOnMethods = {"runBuildAndVerifyNumberOfParticipatingMachinesIsEqualToPoolSize"})
//    public void deactivateCloud() {
//        coordinator.deactivateCloud();
//        coordinator.verifyCloudDeactivated();
//        icService.getCloudStatus();
//        isOnBoarding = false;
//        winService.runCommandDontWaitForTermination(String.format(StaticDataProvider.ProjectsCommands.MISC_PROJECTS.TEST_SAMPLE, GRID_CORES, "180000"));
//        SystemActions.sleep(120);
//        int machinesParticipatingInBuild = ibService.getNumberOfMachinesParticipateInBuild(IC_COORDINATOR);
//        winService.waitForProcessToFinish(StaticDataProvider.Processes.BUILDSYSTEM);
//        Assert.assertEquals(machinesParticipatingInBuild, 1, "More than one machine are participating in build.");
//    }
}
