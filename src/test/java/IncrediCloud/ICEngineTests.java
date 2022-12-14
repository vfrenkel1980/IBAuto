package IncrediCloud;

import frameworkInfra.testbases.incrediCloud.ICEngineTestBase;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import org.testng.Assert;
import org.testng.annotations.Test;

import static frameworkInfra.utils.StaticDataProvider.WindowsMachines.IC_COORDINATOR;

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
        onboardingPageObject.clickTryIncredicloud();
        cloudRegistrationPageObject.selectUser(PROD_USER, ONBOARDING_TYPE);
        onboardingPageObject.performOnboarding(onboardingPage);
        waitForWebServerResponse();
        icService.setSecret(webServer.secret);
        icService.setSecretInRegistry();
        winService.restartService(WindowsServices.COORD_SERVICE);
        icService.loginToCloud();
        Assert.assertTrue(icService.waitForDeliveredMachines(POOL_SIZE), "Number of delivered machines is not equal to " + POOL_SIZE);
        isOnBoarding = true;
        verifyVirtualMachinesInfo();
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
        Assert.assertEquals(machinesParticipatingInBuild, POOL_SIZE + 1, "Number of machines participating in build is different then pool size " + (POOL_SIZE + 1));
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
        //SystemActions.sleep(120);
        winService.runCommandDontWaitForTermination(String.format(ProjectsCommands.MISC_PROJECTS.TEST_SAMPLE, GRID_CORES - (MACHINE_CORES * 2), "40000"));
        SystemActions.sleep(20);
        int machinesParticipatingInBuild = ibService.getNumberOfMachinesParticipateInBuild(IC_COORDINATOR);
        winService.waitForProcessToFinish(Processes.BUILDSYSTEM);
        Assert.assertEquals(machinesParticipatingInBuild, POOL_SIZE - 2 + 1, "Number of machines participating in build is different then expected " + (POOL_SIZE - 2 +1));
    }

/*    @Test(testName = "Verify Undelivered Machine Is Deleted And New One Created", dependsOnMethods = { "verifyNoUnneededMachinesAreCreated"})
    public void verifyUndeliveredMachineIsDeletedAndNewOneCreated(){
        List ipList = icService.getCouldIps();
        winService.runCommandWaitForFinish(Processes.PSEXEC + " \\\\" + ipList.get(0) + " -u helperAdmin -p OrangeJuice1! -i 0 " + "net stop \"" + WindowsServices.AGENT_SERVICE + "\"");
        icService.waitForDeliveredMachines(POOL_SIZE - 1);
        SystemActions.sleep(900);
        Assert.assertTrue(icService.waitForDeliveredMachines(POOL_SIZE), "New machine wasn't created to replace the Undelivered one.");
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
        winService.runCommandDontWaitForTermination(String.format(ProjectsCommands.MISC_PROJECTS.TEST_SAMPLE, GRID_CORES + (MACHINE_CORES * 3), "660000"));
        icService.waitForDeliveredMachines(POOL_SIZE + 3);
        int machinesParticipatingInBuild = ibService.getNumberOfMachinesParticipateInBuild(IC_COORDINATOR);
        int machinesInPool = icService.getStatusQueue(true);
        winService.waitForProcessToFinish(Processes.BUILDSYSTEM);
        Assert.assertEquals(machinesParticipatingInBuild, POOL_SIZE + 4, "Number of machines participating in build is different then expected " + (POOL_SIZE - 2 +1));
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
        SystemActions.sleep(TIMEOUT*2 + 30);
        int machinesInPool = icService.getStatusQueue(false);
        Assert.assertEquals(machinesInPool, 0, "Number of machines in pool is different then expected");
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
        winService.runCommandWaitForFinish(String.format(ProjectsCommands.MISC_PROJECTS.TEST_SAMPLE, ON_PREM_CORES, "240000"));
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
//        winService.runCommandWaitForFinish(Processes.PSEXEC + " -d -i 0 -u admin -p 4illumination \\\\"
//                + WindowsMachines.IC_INITIATOR + " cmd.exe /c \"buildconsole /disable\"");
        winService.runCommandDontWaitForTermination(String.format(ProjectsCommands.MISC_PROJECTS.TEST_SAMPLE, ON_PREM_CORES, "240000"));
        boolean cloudMachinesRunning = icService.waitForDeliveredMachines(POOL_SIZE);
        winService.waitForProcessToFinish(Processes.BUILDSYSTEM);
        Assert.assertTrue(cloudMachinesRunning, "Cloud Machines should be started");
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
//        winService.runCommandWaitForFinish(Processes.PSEXEC + " -d -i 0 -u admin -p 4illumination \\\\"
//                + WindowsMachines.IC_INITIATOR + " cmd.exe /c \"buildconsole /enable\"");
//        winService.runCommandDontWaitForTermination(String.format(ProjectsCommands.MISC_PROJECTS.TEST_SAMPLE, ON_PREM_CORES, "240000"));
        SystemActions.sleep(180);
        int machinesParticipatingInBuild = ibService.getNumberOfMachinesParticipateInBuild(IC_COORDINATOR);
        winService.waitForProcessToFinish(Processes.BUILDSYSTEM);
        Assert.assertEquals(machinesParticipatingInBuild, 1, "Number of machines participating in build is different then expected");
    }
/*

    */
/**
     * @test verify multi initiator works as expected<br>
     *
     * @steps{
     * -run 2 build on 2 different initiators
     * }
     * @result{
     * - cloud machines build both builds}
     *
     *//*

    @Test(testName = "Test MultiInitiator Support", dependsOnMethods = { "verifyCloudMachinesAreDeallocatedWhenEnablingOnPrem"})
    public void testMultiInitiatorSupport(){
        winService.runCommandDontWaitForTermination(Processes.PSEXEC + " \\\\" + IC_INITIATOR + " -u admin -p 4illumination -i 0 " +
                String.format(ProjectsCommands.MISC_PROJECTS.TEST_SAMPLE, (GRID_CORES/2) + (POOL_CORES/2), "240000"));
        winService.runCommandDontWaitForTermination(String.format(ProjectsCommands.MISC_PROJECTS.TEST_SAMPLE, POOL_CORES/2, "240000"));
        SystemActions.sleep(120);
        int initMachines = ibService.getNumberOfMachinesParticipateInBuild(IC_INITIATOR);
        int coordMachines = ibService.getNumberOfMachinesParticipateInBuild(IC_COORDINATOR);
        winService.waitForProcessToFinish(Processes.BUILDSYSTEM);
        Assert.assertTrue(initMachines >= 1 && coordMachines >= 1, "coord machine helpers: " + coordMachines + " init machine helpers: " + initMachines);
    }
*/

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
    @Test(testName = "Pause Cloud", dependsOnMethods = { "verifyCloudMachinesAreStartedWhenDisablingOnPrem"})
    public void pauseCloud(){
        winService.runCommandDontWaitForTermination(IbLocations.COORDMONITOR);
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
        coordinator.enableCloud(false);
        winService.runCommandDontWaitForTermination(String.format(ProjectsCommands.MISC_PROJECTS.TEST_SAMPLE, ON_PREM_CORES + POOL_CORES, "240000"));
        icService.waitForDeliveredMachines(POOL_SIZE);
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
        icService.waitForCloudStatus("Pool does not exist (Paused)");
        winService.runCommandDontWaitForTermination(String.format(ProjectsCommands.MISC_PROJECTS.TEST_SAMPLE, ON_PREM_CORES, "180000"));
        SystemActions.sleep(20);
        int machinesParticipatingInBuild = ibService.getNumberOfMachinesParticipateInBuild(IC_COORDINATOR);
        winService.waitForProcessToFinish(Processes.BUILDSYSTEM);
        Assert.assertEquals(machinesParticipatingInBuild, 1);
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
        coordinator.enableCloud(true);
        winService.runCommandDontWaitForTermination(String.format(ProjectsCommands.MISC_PROJECTS.TEST_SAMPLE, GRID_CORES, "900000"));
        SystemActions.sleep(180);
        boolean cloudMachinesRunning = icService.waitForDeliveredMachines(POOL_SIZE);
        winService.waitForProcessToFinish(Processes.BUILDSYSTEM);
        Assert.assertTrue(cloudMachinesRunning, "Cloud Machines should be started");
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
        coordinator.verifyCloudIsDeactivating();
        icService.getCloudStatus();
        winService.runCommandDontWaitForTermination(String.format(ProjectsCommands.MISC_PROJECTS.TEST_SAMPLE, GRID_CORES, "180000"));
        SystemActions.sleep(120);
        int machinesParticipatingInBuild = ibService.getNumberOfMachinesParticipateInBuild(IC_COORDINATOR);
        winService.waitForProcessToFinish(Processes.BUILDSYSTEM);
        Assert.assertEquals(machinesParticipatingInBuild, 1 , "More than one machine are participating in build.");
    }

}
