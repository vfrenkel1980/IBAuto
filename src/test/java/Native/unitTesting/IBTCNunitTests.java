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
 * @details Requires Unit Tests license solution
 */
public class IBTCNunitTests extends RobinTestingTestBase {

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
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_CONSOLE_TESTLEVEL_TEST);
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
     * @test NUnit3 Path with spaces support test.<br>
     * @pre{ }
     * @steps{
     * - Run the nunit slow sample tests where the path to dll contains spaces.}
     * @result{
     * - Build is succeeded.}
     */
    @Test(testName = "NUnit3 Path With Spaces Test")
    public void nunit3PathWithSpacesTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_SLOW_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test NUnit3 testresult file is created.<br>
     * @pre{ }
     * @steps{
     * - Run the nunit-console-master failed tests with "--result=c:\path\to\res.xml" flag.}
     * @result{
     * - Build is succeeded.
     * - The result.xml file is created.}
     */
    @Test(testName = "NUnit3 Test Result Test")
    public void nunit3TestResultTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_CONSOLE_RESULT_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
        File f = new File(Locations.QA_ROOT+"\\nunitres.xml");
        Assert.assertTrue(f.isFile(), "The test result file is not created");
        f.delete();
    }

    /**
     * @test NUnit3 testresult is created for failed tests.<br>
     * @pre{ }
     * @steps{
     * - Run the nunit framework failed tests with "--result=c:\path\to\res.xml" flag.}
     * @result{
     * - Build failed.
     * - The result.xml file is created.}
     */
    @Test(testName = "NUnit3 Failed Test Result Test")
    public void nunit3FailedTestResultTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_FAILED_RESULT_TEST);
        Assert.assertFalse(exitCode == 0, "The test execution not failed");
        File f = new File(Locations.QA_ROOT+"\\nunitres.xml");
        Assert.assertTrue(f.isFile(), "The test result file is not created");
        f.delete();
    }

    /**
     * @test NUnit3 testresult path with spaces test.<br>
     * @pre{ }
     * @steps{
     * - Run the nunit framework tests where the path to resultfile contains spaces.}
     * @result{
     * - Build succeeded.
     * - The result.xml file is created.}
     */
    @Test(testName = "NUnit3 Path With Spaces Test Result Test")
    public void nunit3PathWithSpacesTestResultTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_SLOW_TEST + " --result=\""+Locations.QA_ROOT+"\\Nunit3 TestExample\\nunitres.xml\"");
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
        File f = new File(Locations.QA_ROOT+"\\Nunit3 TestExample\\nunitres.xml");
        Assert.assertTrue(f.isFile(), "The test result file is not created");
        f.delete();
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

    /**
     * @test NUnit3 Testlist flag test.<br>
     * @pre{ }
     * @steps{
     * - Run the nunit slow sample tests with --testlist=testlist.txt flag.}
     * @result{
     * - Build is succeeded.}
     */
    @Test(testName = "NUnit3 Testlist Flag Test")
    public void nunit3TestlistFlagTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_SLOW_TESTLIST_FLAG_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test NUnit3 @FILE flag test.<br>
     * @pre{ }
     * @steps{
     * - Run the nunit slow sample tests with @fileWithArguments.txt flag.}
     * @result{
     * - Build is succeeded.}
     */
    @Test(testName = "NUnit3 File Flag Test")
    public void nunit3FileFlagTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_SLOW_FILE_FLAG_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }
}
