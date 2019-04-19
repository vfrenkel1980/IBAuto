package Native.unitTesting;

import frameworkInfra.testbases.RobinTestingTestBase;
import frameworkInfra.utils.StaticDataProvider.IbLocations;
import frameworkInfra.utils.StaticDataProvider.Locations;
import frameworkInfra.utils.StaticDataProvider.ProjectsCommands;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

/**
 * @brief Unit tests execution with IbTestConsole

 * - GTest
 * @details Requires Unit Tests license solution
 */
public class IBTCGTestTests extends RobinTestingTestBase {

    /**
     * @test IBTC /targetdir flag test (the exe is written only with its name, without full path)<br>
     * @pre{ }
     * @steps{
     * - Run the google test master tests with /targetdir="c:\path\to\dll\dir" flag.}
     * @result{
     * - Build is succeeded.}
     */
    @Test(testName = "Targetdir Test")
    public void targetdirTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_CPPSORTER_TARGETDIR_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test IBTC /logfile & /loglevel flag test.<br>
     * @pre{ }
     * @steps{
     * - Run the google test master tests with /logfile="c:\path\to\log.txt" and /loglevel=info flag.}
     * @result{
     * - Build is succeeded;
     * - The log file is created.}
     */
    @Test(testName = "LogFile Test")
    public void logFileTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_CPPSORTER_LOGFILE_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
        Assert.assertTrue(new File(Locations.OUTPUT_LOG_FILE).exists(), "LogFile is not found");
    }

    /**
     * @test GTest CPPSorter Assembly Level support test.<br>
     * @pre{ }
     * @steps{
     * - Run the google test master tests.}
     * @result{
     * - Build is succeeded.}
     */
    @Test(testName = "GTEST Assembly Level Test")
    public void gTestCPPSorterAssemblyLevelTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_CPPSORTER_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test GTest CPPSorter Test Level support test.<br>
     * @pre{ }
     * @steps{
     * - Run the google test master tests with /testlevel=10 flag.}
     * @result{
     * - Build is succeeded.}
     */
    @Test(testName = "GTest CPPSorter Test Level Test")
    public void gTestCPPSorterTestLevelTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_CPPSORTER_TESTLEVEL_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }


    /**
     * @test GTest Master Test Level support test.<br>
     * @pre{ }
     * @steps{
     * - Run the google test master tests with /testlevel=12 flag.}
     * @result{
     * - Build is succeeded.}
     */
    @Test(testName = "GTest Master Test Level Test")
    public void gTestMasterTestLevelTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_MASTER_TESTLEVEL_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test GTest CPPSorter testlevel=deep test. (Every testcase runs in the separate task bar).<br>
     * @pre{ }
     * @steps{
     * - Run the google test master tests with /testlevel=deep flag.}
     * @result{
     * - Build is succeeded.}
     */
    @Test(testName = "GTest CPPSorter Testlevel Deep Test")
    public void gTestCPPSorterTestLevelDeepTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_CPPSORTER_TESTLEVEL_DEEP_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }
    /**
     * @test GTest Flags test.<br>
     * @pre{ }
     * @steps{
     * - Run the google test master tests with all supported flags:
     * --gtest_filter=*int*
     * --gtest_also_run_disabled_tests
     * --gtest_repeat=100
     * --gtest_shuffle
     * --gtest_random_seed=1236
     * --gtest_output=xml:C:\QA\Simulation\gtestResult.xml
     * .}
     * @result{
     * - Build is succeeded.}
     */
    @Test(testName = "GTest Flags Test")
    public void gTestFlagsTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_CPPSORTER_FLAGS);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
        File f = new File("C:\\QA\\Simulation\\gtestResult.xml");
        Assert.assertTrue(f.isFile(), "The test result file is not created");
        f.delete();
    }
}
