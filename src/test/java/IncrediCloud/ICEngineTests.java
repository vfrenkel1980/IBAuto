package IncrediCloud;

import cloudInfra.IncrediCloud.Pages.OnboardingPage;
import frameworkInfra.testbases.incrediCloud.ICEngineTestBase;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import org.testng.Assert;
import org.testng.annotations.Test;

import static frameworkInfra.utils.StaticDataProvider.WindowsMachines.IC_COORDINATOR;
import static frameworkInfra.utils.StaticDataProvider.WindowsMachines.IC_INITIATOR;

/**
 * @brief Tests for IncrediCloud engine functionality
 * @details Requires an enterprise license
 *
 */


public class ICEngineTests extends ICEngineTestBase {

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
    @Test(testName = "Perform Onboarding")
    public void performOnboarding(){
        startWebServerThread();
        OnboardingPage onboardingPage = new OnboardingPage("North Europe", "Test", "User", "Test@user.com", "Com", TIMEOUT, CORES_LIMIT, POOL_SIZE,
                COORD_PORT, VM_PORT);
        onboardingPageObject.clickTryIncredicloud();
        azurePageObject.selectAzureUser();
        onboardingPageObject.performOnboarding(onboardingPage);
        waitForWebServerResponse();
        icService.setSecret(webServer.secret);
        icService.setSecretInRegistry();
        winService.restartService(WindowsServices.COORD_SERVICE);
        icService.loginToCloud();
        Assert.assertTrue(icService.waitForDeliveredMachines(POOL_SIZE), "Number of delivered machines is not equal to " + POOL_SIZE);
    }

    /**
     * @test Start a build and verify no unneeded cloud machines are created<br>
     *
     * @steps{
     * - Build Testsample
     * - Query statusExport}
     * @result{
     * - No unneeded machines are created}
     */
    @Test(testName = "Verify No Unneeded Machines Are Created", dependsOnMethods = { "performOnboarding"})
    public void verifyNoUnneededMachinesAreCreated(){
        winService.runCommandDontWaitForTermination(String.format(ProjectsCommands.MISC_PROJECTS.TEST_SAMPLE, GRID_CORES, "90000"));
        SystemActions.sleep(40);
        int machinesParticipatingInBuild = ibService.getNumberOfMachinesParticipateInBuild(IC_COORDINATOR);
        int machinesInPool = icService.getStatusQueue(false);
        winService.waitForProcessToFinish(Processes.BUILDSYSTEM);
        Assert.assertEquals(machinesParticipatingInBuild, POOL_SIZE + 1, "Number of machines participating in build is different then pool size " + POOL_SIZE);
        Assert.assertEquals(machinesInPool, POOL_SIZE, "Number of machines in pool is different then original pool size");
    }

    /**
     * @test Start a build and verify 2 machines participate in build<br>
     *
     * @steps{
     * - Build Testsample
     * - Query statusExport}
     * @result{
     * - Only 2 machines participate in build}
     */
    @Test(testName = "Verify Not All Machines Participate In Build", dependsOnMethods = { "verifyNoUnneededMachinesAreCreated"})
    public void verifyNotAllMachinesParticipateInBuild(){
        winService.runCommandDontWaitForTermination(String.format(ProjectsCommands.MISC_PROJECTS.TEST_SAMPLE, GRID_CORES - 4, "40000"));
        SystemActions.sleep(20);
        int machinesParticipatingInBuild = ibService.getNumberOfMachinesParticipateInBuild(IC_COORDINATOR);
        winService.waitForProcessToFinish(Processes.BUILDSYSTEM);
        Assert.assertEquals(machinesParticipatingInBuild, POOL_SIZE - 2 + 1, "Number of machines participating in build is different then expected " + (POOL_SIZE - 2 +1));
    }

    /**
     * @test Start a build and verify 3 new machines created<br>
     *
     * @steps{
     * - Build Testsample
     * - GetStatusQueue
     * }
     * @result{
     * - 7 machines Are in the pool}
     */
    @Test(testName = "Verify New Machines Are Created", dependsOnMethods = { "verifyNotAllMachinesParticipateInBuild"})
    public void verifyNewMachinesAreCreated(){
        winService.runCommandDontWaitForTermination(String.format(ProjectsCommands.MISC_PROJECTS.TEST_SAMPLE, GRID_CORES + 6, "900000"));
        SystemActions.sleep(780);
        int machinesParticipatingInBuild = ibService.getNumberOfMachinesParticipateInBuild(IC_COORDINATOR);
        int machinesInPool = icService.getStatusQueue(true);
        winService.waitForProcessToFinish(Processes.BUILDSYSTEM);
        Assert.assertEquals(machinesParticipatingInBuild, POOL_SIZE - 2 + 1, "Number of machines participating in build is different then expected " + (POOL_SIZE - 2 +1));
        Assert.assertEquals(machinesInPool, POOL_SIZE + 3, "Number of machines in pool is different then expected");
    }

    /**
     * @test verify machines deleted after timeout<br>
     *
     * @steps{
     * - wait for timeout
     * }
     * @result{
     * - 4 machines Are in the pool}
     */
    @Test(testName = "Verify Machines Deallocated After Reaching Timeout", dependsOnMethods = { "verifyNewMachinesAreCreated"})
    public void verifyMachinesDeallocatedAfterReachingTimeout(){
        SystemActions.sleep(TIMEOUT + 30);
        int machinesInPool = icService.getStatusQueue(false);
        Assert.assertEquals(machinesInPool, POOL_SIZE, "Number of machines in pool is different then expected");
    }

    /**
     * @test verify no cloud machines participate when using onprem machine<br>
     *
     * @steps{
     * - start a build that requires no cloud machines
     * }
     * @result{
     * - no cloud machines are created}
     */
    @Test(testName = "Verify No Cloud Machines Are Created When Using On Prem Machines", dependsOnMethods = { "verifyMachinesDeallocatedAfterReachingTimeout"})
    public void verifyNoCloudMachinesAreCreatedWhenUsingOnPremMachines(){
        winService.runCommandDontWaitForTermination(String.format(ProjectsCommands.MISC_PROJECTS.TEST_SAMPLE, GRID_CORES_WO_CLOUD, "360000"));
        SystemActions.sleep(180);
        int runningMachines = icService.getStatusQueue(true);
        Assert.assertEquals(runningMachines, 0, "NO machines should be running");
    }

    /**
     * @test verify no cloud machines participate when using onprem machine<br>
     *
     * @steps{
     * - disable as helper the second initiator
     * - get number of machines participating in build
     * }
     * @result{
     * - 4 cloud machines are started and participating}
     * @todo{ on new initiator:
     * - run reg add HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\system /v LocalAccountTokenFilterPolicy /t REG_DWORD /d 1 /f
     * - restart
     * - on coordinator: right click initiator and enable remote admin, enable "enable/disable"
     * }
     */
    @Test(testName = "Verify Cloud Machines Are Started When Disabling On Prem", dependsOnMethods = { "verifyNoCloudMachinesAreCreatedWhenUsingOnPremMachines"})
    public void verifyCloudMachinesAreStartedWhenDisablingOnPrem(){
        winService.runCommandWaitForFinish(Processes.PSEXEC + " -d -i 0 -u admin -p 4illumination \\\\"
                + WindowsMachines.IC_INITIATOR + " cmd.exe /c \"buildconsole /disable\"");
        boolean cloudMachinesRunning = icService.waitForDeliveredMachines(GRID_CORES_WO_CLOUD);
        int machinesParticipatingInBuild = ibService.getNumberOfMachinesParticipateInBuild(IC_COORDINATOR);
        Assert.assertTrue(cloudMachinesRunning, "Cloud Machines should be started and participating in build");
        Assert.assertEquals(machinesParticipatingInBuild, POOL_SIZE, "Number of machines participating in build is different then expected " + POOL_SIZE);
    }

    /**
     * @test verify cloud machines started when disabling on prem machine<br>
     *
     * @steps{
     * - enable as helper the second initiator
     * - get number of machines participating in build
     * }
     * @result{
     * - cloud machines should be deallocated}
     * @todo{ on new initiator:
     * - run reg add HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\system /v LocalAccountTokenFilterPolicy /t REG_DWORD /d 1 /f
     * - restart
     * - on coordinator: right click initiator and enable remote admin, enable "enable/disable"
     * }
     */
    @Test(testName = "Verify Cloud Machines Are Deallocated When Enabling On Prem", dependsOnMethods = { "verifyCloudMachinesAreStartedWhenDisablingOnPrem"})
    public void verifyCloudMachinesAreDeallocatedWhenEnablingOnPrem(){
        winService.runCommandWaitForFinish(Processes.PSEXEC + " -d -i 0 -u admin -p 4illumination \\\\"
                + WindowsMachines.IC_INITIATOR + " cmd.exe /c \"buildconsole /enable\"");
        SystemActions.sleep(10);
        int machinesParticipatingInBuild = ibService.getNumberOfMachinesParticipateInBuild(IC_COORDINATOR);
        winService.waitForProcessToFinish(Processes.BUILDSYSTEM);
        Assert.assertEquals(machinesParticipatingInBuild, 1, "Number of machines participating in build is different then expected");
    }

    /**
     * @test verify multi initiator works as expected<br>
     *
     * @steps{
     * -run 2 build on 2 different initiators
     * }
     * @result{
     * - cloud machines build both builds}
     *
     */
    @Test(testName = "Test MultiInitiator Support", dependsOnMethods = { "verifyCloudMachinesAreDeallocatedWhenEnablingOnPrem"})
    public void testMultiInitiatorSupport(){
        winService.runCommandDontWaitForTermination(Processes.PSEXEC + " \\\\" + IC_INITIATOR + " -u admin -p 4illumination -i 0 " +
                String.format("\"C:\\Program Files (x86)\\IncrediBuild\\buildconsole.exe\" " + ProjectsCommands.MISC_PROJECTS.TEST_SAMPLE, GRID_CORES/2, "180000"));
        winService.runCommandDontWaitForTermination(String.format(ProjectsCommands.MISC_PROJECTS.TEST_SAMPLE, GRID_CORES/2, "180000"));
        icService.waitForDeliveredMachines(4);
        int initMachines = ibService.getNumberOfMachinesParticipateInBuild(IC_INITIATOR);
        int coordMachines = ibService.getNumberOfMachinesParticipateInBuild(IC_COORDINATOR);
        winService.waitForProcessToFinish(Processes.BUILDSYSTEM);
        Assert.assertTrue(initMachines > 1 && coordMachines > 1, "coord machine helpers: " + coordMachines + " init machine helpers: " + initMachines);
    }

    /**
     * @test pause cloud and perform a build<br>
     *
     * @steps{
     * - pause cloud using sikuli
     * - start a build
     * }
     * @result{
     * - no cloud machines should participate in build}
     *
     */
    @Test(testName = "Pause Cloud", dependsOnMethods = { "testMultiInitiatorSupport"})
    public void pauseCloud(){
        coordinator.pauseCloud();
        icService.waitForDeliveredMachines(0);
        winService.runCommandDontWaitForTermination(String.format(ProjectsCommands.MISC_PROJECTS.TEST_SAMPLE, GRID_CORES, "180000"));
        SystemActions.sleep(60);
        int machinesParticipatingInBuild = ibService.getNumberOfMachinesParticipateInBuild(IC_COORDINATOR);
        winService.waitForProcessToFinish(Processes.BUILDSYSTEM);
        Assert.assertEquals(machinesParticipatingInBuild, 1 , "More than one machine are participating in build.");
    }

    /**
     * @test enable cloud and perform a build<br>
     *
     * @steps{
     * - enable cloud using sikuli
     * - start a build
     * }
     * @result{
     * - cloud machines should participate in build}
     *
     */
    @Test(testName = "Enable Cloud", dependsOnMethods = { "pauseCloud"})
    public void enableCloud(){
        coordinator.enableCloud();
        winService.runCommandDontWaitForTermination(String.format(ProjectsCommands.MISC_PROJECTS.TEST_SAMPLE, GRID_CORES, "180000"));
        icService.waitForDeliveredMachines(4);
        int machinesParticipatingInBuild = ibService.getNumberOfMachinesParticipateInBuild(IC_COORDINATOR);
        winService.waitForProcessToFinish(Processes.BUILDSYSTEM);
        Assert.assertEquals(machinesParticipatingInBuild, POOL_SIZE + 1);
    }

    /**
     * @test pause cloud and delete pool and perform a build<br>
     *
     * @steps{
     * - pause cloud and delete pool using sikuli
     * - start a build
     * }
     * @result{
     * - no cloud machines should participate in build}
     *
     */
    @Test(testName = "Pause Cloud And Delete Pool", dependsOnMethods = { "enableCloud"})
    public void pauseCloudAndDeletePool(){
        coordinator.pauseCloudAndDeletePool();
        icService.waitForDeliveredMachines(0);
        icService.getStatusQueue(false);
        winService.runCommandDontWaitForTermination(String.format(ProjectsCommands.MISC_PROJECTS.TEST_SAMPLE, GRID_CORES, "960000"));
        boolean cloudMachinesRunning = icService.waitForDeliveredMachines(4);
        winService.waitForProcessToFinish(Processes.BUILDSYSTEM);
        Assert.assertFalse(cloudMachinesRunning, "Looks like there are running cloud machines");
    }

    /**
     * @test enable cloud and perform a build<br>
     *
     * @steps{
     * - enable cloud using sikuli
     * - start a build
     * }
     * @result{
     * - cloud machines should be created and participate in build}
     *
     */
    @Test(testName = "Enable Cloud And Create New Pool", dependsOnMethods = { "pauseCloudAndDeletePool"})
    public void enableCloudAndCreateNewPool(){
        coordinator.enableCloud();
        winService.runCommandDontWaitForTermination(String.format(ProjectsCommands.MISC_PROJECTS.TEST_SAMPLE, GRID_CORES, "960000"));
        icService.waitForDeliveredMachines(4);
        int machinesParticipatingInBuild = ibService.getNumberOfMachinesParticipateInBuild(IC_COORDINATOR);
        winService.waitForProcessToFinish(Processes.BUILDSYSTEM);
        Assert.assertEquals(machinesParticipatingInBuild, POOL_SIZE + 1);
    }

    /**
     * @test deactivate cloud and perform a build<br>
     *
     * @steps{
     * - deactivate cloud using sikuli
     * - start a build
     * }
     * @result{
     * - no cloud machines should participate in build}
     *
     */
    @Test(testName = "Deactivate Cloud", dependsOnMethods = { "enableCloudAndCreateNewPool"})
    public void deactivateCloud(){
        coordinator.deactivateCloud();
        coordinator.verifyCloudDeactivated();
        icService.waitForDeliveredMachines(0);
        winService.runCommandDontWaitForTermination(String.format(ProjectsCommands.MISC_PROJECTS.TEST_SAMPLE, GRID_CORES, "180000"));
        SystemActions.sleep(60);
        int machinesParticipatingInBuild = ibService.getNumberOfMachinesParticipateInBuild(IC_COORDINATOR);
        winService.waitForProcessToFinish(Processes.BUILDSYSTEM);
        Assert.assertEquals(machinesParticipatingInBuild, 1 , "More than one machine are participating in build.");
    }




}