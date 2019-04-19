package Native.unitTesting;

import frameworkInfra.testbases.RobinTestingTestBase;
import frameworkInfra.utils.StaticDataProvider.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

/**
 * @brief Unit tests execution with IbTestConsole
 * - NUnit2
 * - Nunit3
 * - GTest
 * @details Requires Unit Tests license solution
 */
public class IbTestConsoleTests extends RobinTestingTestBase {

    /**
     * @test IBTC /targetdir flag test (the dll is written only with its name, without full path)<br>
     * @pre{ }
     * @steps{
     * - Run the nunit-console-master tests with /targetdir="c:\path\to\dll\dir" flag.}
     * @result{
     * - Build is succeeded.}
     */
    @Test(testName = "Targetdir Test")
    public void targetdirTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_CONSOLE_TARGETDIR_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test IBTC /logfile & /loglevel flag test.<br>
     * @pre{ }
     * @steps{
     * - Run the nunit-console-master tests with /logfile="c:\path\to\log.txt" and /loglevel=info flag.}
     * @result{
     * - Build is succeeded;
     * - The log file is created.}
     */
    @Test(testName = "LogFile Test")
    public void logFileTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_CONSOLE_LOGFILE_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
        Assert.assertTrue(new File(Locations.OUTPUT_LOG_FILE).exists(), "LogFile is not found");
    }

    /**
     * @test IbTestConsole "HELP" with /help flag test<br>
     * @pre{ }
     * @steps{
     * - Run the IbTestConsole.exe with /help flag}
     * @result{
     * - The "HELP" ("Usage:\n  IbTestConsole.exe [options] <command>") is displayed in the console.}
     */
    @Test(testName = "IBTC Help Test")
    public void iBTCHelpTest() {
        String out = winService.runCommandGetOutput(IbLocations.IBTESTCONSOLE + " /help");
        Assert.assertTrue(out.contains("Usage:\n  IbTestConsole.exe [options] <command>"));
    }

    /**
     * @test IbTestConsole "HELP" without parameter test<br>
     * @pre{ }
     * @steps{
     * - Run the IbTestConsole.exe without parametres}
     * @result{
     * - The "HELP" ("Usage:\n  IbTestConsole.exe [options] <command>") is displayed in the console.}
     */
    @Test(testName = "IBTC Without Help Test")
    public void iBTCWithoutHelpTest() {
        String out = winService.runCommandGetOutput(IbLocations.IBTESTCONSOLE);
        Assert.assertTrue(out.contains("Usage:\n  IbTestConsole.exe [options] <command>"));
    }
//NUNIT2
    /**
     * @test NUnit2 Assembly Level support test.<br>
     * @pre{ }
     * @steps{
     * - Run the nunit framework tests.}
     * @result{
     * - Build is succeeded.}
     */
    @Test(testName = "NUnit2 Assembly Level Test")
    public void nunit2AssemblyLevelTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT2_FRAMEWORK_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test NUnit2 Test Level support test.<br>
     * @pre{ }
     * @steps{
     * - Run the nunit framework tests with /testlevel=10 flag.}
     * @result{
     * - Build is succeeded.}
     */
    @Test(testName = "NUnit2 Test Level Test")
    public void nunit2TestLevelTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT2_FRAMEWORK_TESTLEVEL_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test NUnit2 testlevel=deep test. (Every testcase runs in the separate task bar).<br>
     * @pre{ }
     * @steps{
     * - Run the nunit framework tests with /testlevel=deep flag.}
     * @result{
     * - Build is succeeded.}
     */
    @Test(testName = "NUnit2 Testlevel Deep Test")
    public void nunit2TestLevelDeepTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT2_FRAMEWORK_TESTLEVEL_DEEP_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }
//NUNIT3
    /**
     * @test NUnit3 Assembly Level support test.<br>
     * @pre{ }
     * @steps{
     * - Run the nunit-console-master tests.}
     * @result{
     * - Build is succeeded.}
     */
    @Test(testName = "NUnit3 Assembly Level Test")
    public void nunit3AssemblyLevelTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_CONSOLE_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test NUnit3 Test Level support test.<br>
     * @pre{ }
     * @steps{
     * - Run the nunit-console-master tests with /testlevel=10 flag.}
     * @result{
     * - Build is succeeded.}
     */
    @Test(testName = "NUnit3 Test Level Test")
    public void nunit3TestLevelTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_TESTLEVEL_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test NUnit3 testlevel=deep test. (Every testcase runs in the separate task bar).<br>
     * @pre{ }
     * @steps{
     * - Run the nunit-console-master tests with testlevel=deep flag.}
     * @result{
     * - Build is succeeded.}
     */
    @Test(testName = "NUnit3 Testlevel Deep Test")
    public void nunit3TestLevelDeepTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_CONSOLE_TESTLEVEL_DEEP_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test NUnit3 testresult is created for failed tests.<br>
     * @pre{ }
     * @steps{
     * - Run the nunit-console-master failed tests with "--result=c:\path\to\res.xml" flag.}
     * @result{
     * - The result.xml file is created.}
     */
    @Test(testName = "NUnit3 Test Result Test")
    public void nunit3TestResultTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_CONSOLE_WHERE_FILTER_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test NUnit3 --where class filter test.<br>
     * @pre{ }
     * @steps{
     * - Run the nunit-console-master tests with --where "class == 'TestClassName'" filter.}
     * @result{
     * - Build is succeeded.}
     */
    @Test(testName = "NUnit3 Class Filter Test")
    public void nunit3ClassFilterTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_CONSOLE_WHERE_FILTER_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test NUnit3 SEED flag test.<br>
     * @pre{ }
     * @steps{
     * - Run the nunit-console-master tests with --where "class == 'TestClassName'" filter.}
     * @result{
     * - Build is succeeded.}
     */
    @Test(testName = "NUnit3 Seed Flag Test")
    public void nunit3SeedFlagTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_CONSOLE_SEED_FLAG_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }
    /**
     * @test NUnit3 Timeout flag test.<br>
     * @pre{ }
     * @steps{
     * - Run the nunit-console-master tests with --where "class == 'TestClassName'" filter.}
     * @result{
     * - Build is succeeded.}
     */
    @Test(testName = "NUnit3 Timeout Flag Test")
    public void nunit3TimeoutFlagTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_CONSOLE_TIMEOUT_FLAG_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }



//GTEST
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
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_CPPSORTER_TESTLEVEL_TEST);
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
