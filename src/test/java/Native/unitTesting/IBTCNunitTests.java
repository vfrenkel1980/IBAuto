package Native.unitTesting;

import frameworkInfra.testbases.UnitTestingTestBase;
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
public class IBTCNunitTests extends UnitTestingTestBase {

    /**
     * @test IBTC /targetdir flag test (the dll is written with relative (not absolute) path)<br>
     * @pre{ }
     * @steps{
     * - Run the nunit-console-master tests with the /targetdir="c:\path\to\dll\dir" flag.}
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
     * - Run the nunit-console-master tests with the /logfile="c:\path\to\log.txt" and /loglevel=info flag.}
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
     * - Run the IbTestConsole.exe with the /help flag}
     * @result{
     * - The "HELP" ("Usage:\n  IbTestConsole.exe [options] <command>") is displayed in the console.}
     */
    @Test(testName = "IBTC Help Test")
    public void iBTCHelpTest() {
        String out = winService.runCommandGetOutput(IbLocations.IBTESTCONSOLE + " /help");
        Assert.assertTrue(out.contains("Usage:\n  IbTestConsole.exe [options] <command>"));
    }

    /**
     * @test IbTestConsole "HELP" without parameter test. <a href="http://redmine.incredibuild.local/issues/10200">Bug #10200</a><br>
     * @pre{ }
     * @steps{
     * - Run the IbTestConsole.exe without parameters}
     * @result{
     * - The "HELP" ("Usage:\n  IbTestConsole.exe [options] <command>") is displayed in the console.}
     */
    @Test(testName = "IBTC No Help Test")
    public void iBTCNoHelpTest() {
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
     * - Run the nunit framework tests with the /testlevel=deep flag.}
     * @result{
     * - Build is succeeded.}
     */
    @Test(testName = "NUnit2 Testlevel Deep Test")
    public void nunit2TestLevelDeepTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT2_FRAMEWORK_TESTLEVEL_DEEP_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test NUnit2 /result=testresult.xml is created in the deep testlevel mode test. <a href="http://redmine.incredibuild.local/issues/9943">Bug #9943</a>.<br>
     * @pre{ }
     * @steps{
     * - Run the nunit framework tests with the /result=C:\path\to\res.xml flag.}
     * @result{
     * - Build is succeeded.}
     */
    @Test(testName = "NUnit2 Result Testlevel Deep Test")
    public void nunit2ResultTestLevelDeepTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT2_FRAMEWORK_TESTLEVEL_DEEP_TEST + " /result=\'"+Locations.QA_ROOT+"\\nunitres.xml\"");
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
        File f = new File(Locations.QA_ROOT+"\\nunitres.xml");
        Assert.assertTrue(f.isFile(), "The test result file is not created for " +testName);
        f.delete();
    }

    /**
     * @test NUnit2 /xml=testresult.xml is created in the deep testlevel mode test. <a href="http://redmine.incredibuild.local/issues/9943">Bug #9943</a>.<br>
     * @pre{ }
     * @steps{
     * - Run the nunit framework tests with /xml=C:\path\to\res.xml flag.}
     * @result{
     * - Build is succeeded;
     * - The result.xml file is created}
     */
    @Test(testName = "NUnit2 Xml Testlevel Deep Test")
    public void nunit2XmlTestLevelDeepTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT2_FRAMEWORK_TESTLEVEL_DEEP_TEST+ " /xml=\'"+Locations.QA_ROOT+"\\nunitres.xml\"");
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
        File f = new File(Locations.QA_ROOT+"\\nunitres.xml");
        Assert.assertTrue(f.isFile(), "The test result file is not created for " +testName);
        f.delete();
    }

    /**
     * @test NUnit2 testresult.xml isn't created in the deep testlevel mode with /noresult flag test.<br>
     * @pre{ }
     * @steps{
     * - Run the nunit framework tests with the /noresult flag.}
     * @result{
     * - Build is succeeded;
     * - The result.xml file is created}
     */
    @Test(testName = "NUnit2 NoResult Testlevel Deep Test")
    public void nunit2NoResultTestLevelDeepTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT2_FRAMEWORK_TESTLEVEL_DEEP_TEST + " /noresult");
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
        Assert.assertFalse(new File(System.getProperty("user.dir")+"\\TestResult.xml").isFile(), "The test result file is created for " +testName);
    }

    /**
     * @test NUnit2 testresult.xml isn't created in the deep testlevel mode with the /noxml flag test.<br>
     * @pre{ }
     * @steps{
     * - Run the nunit framework tests with the /noxml flag.}
     * @result{
     * - Build is succeeded.
     * - The result.xml file isn't created}
     */
    @Test(testName = "NUnit2 No Xml Testlevel Deep Test")
    public void nunit2NoXmlTestLevelDeepTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT2_FRAMEWORK_TESTLEVEL_DEEP_TEST+ " /noxml");
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
        Assert.assertFalse(new File(System.getProperty("user.dir")+"\\TestResult.xml").isFile(), "The test result file is created for " +testName);
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
     * - Run the nunit-console-master tests with the /testlevel=10 flag.}
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
     * - Run the nunit-console-master tests with the testlevel=deep flag.}
     * @result{
     * - Build is succeeded.}
     */
    @Test(testName = "NUnit3 Testlevel Deep Test")
    public void nunit3TestLevelDeepTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_CONSOLE_TESTLEVEL_DEEP_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test NUnit3 /silent test.<br>
     * @pre{ }
     * @steps{
     * - Run the nunit-console-master tests with the /silent flag.}
     * @result{
     * - Build is succeeded.
     * - There is no output in the command line.}
     */
    @Test(testName = "NUnit3 Silent Test")
    public void nunit3SilentTest() {
        String output = winService.runCommandGetOutput(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_CONSOLE_TESTLEVEL_TEST +  " /silent");
        Assert.assertTrue(output.equals(""), "The test output is not suppressed.  Output: " + output);
    }

    /**
     * @test NUnit3 Path with spaces support test. <a href="http://redmine.incredibuild.local/issues/9856">Bug #9856</a>.<br>
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
     * @test NUnit3 testresult file is created with relative path.<br>
     * @pre{ }
     * @steps{
     * - Run the nunit-console-master failed tests with the "--result=result.xml" flag.}
     * @result{
     * - Build is succeeded.
     * - The result.xml file is created.}
     */
    @Test(testName = "NUnit3 Test Result Test")
    public void nunit3TestResultTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_CONSOLE_TEST +" --result=result.xml");
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
        File f = new File(System.getProperty("user.dir")+"\\result.xml");
        Assert.assertTrue(f.isFile(), "The test result file is not created");
        f.delete();
    }

    /**
     * @test NUnit3 testresult file is created with the absolute path.<br>
     * @pre{ }
     * @steps{
     * - Run the nunit-console-master failed tests with the "--result=c:\path\to\result.xml" flag.}
     * @result{
     * - Build is succeeded.
     * - The result.xml file is created.}
     */
    @Test(testName = "NUnit3 Absolute Path Test Result Test")
    public void nunit3AbsolutePathTestResultTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_CONSOLE_RESULT_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
        File f = new File(Locations.QA_ROOT+"\\nunitres.xml");
        Assert.assertTrue(f.isFile(), "The test result file is not created");
        f.delete();
    }

    /**
     * @test NUnit3 testresult is created when the tests fail. <a href="http://redmine.incredibuild.local/issues/9857">Bug #9857</a>.<br>
     * @pre{ }
     * @steps{
     * - Run the nunit framework with failed tests and "--result=c:\path\to\res.xml" flag.}
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
     * @test NUnit3 testresult path with spaces test. <a href="http://redmine.incredibuild.local/issues/9855">Bug #9855</a>.<br>
     * @pre{ }
     * @steps{
     * - Run the nunit framework tests where the path to the resultfile contains spaces.}
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
     * - Run the nunit-console-master tests with the --where "class == 'TestClassName'" filter.}
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
     * - Run the nunit-console-master tests with the --seed=12354 flag.}
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
     * - Run the nunit-console-master tests with the --timeout=100000 flag.}
     * @result{
     * - Build is succeeded.}
     */
    @Test(testName = "NUnit3 Timeout Flag Test")
    public void nunit3TimeoutFlagTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_CONSOLE_TIMEOUT_FLAG_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test NUnit3 Testlist flag with absolute path test.<br>
     * @pre{ }
     * @steps{
     * - Run the nunit slow sample tests with the --testlist=c:\path\to\testlist.txt flag.}
     * @result{
     * - Build is succeeded.}
     */
    @Test(testName = "NUnit3 Absolute PathTestlist Flag Test")
    public void nunit3AbsolutePathTestlistFlagTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_SLOW_TESTLIST_FLAG_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test NUnit3 Testlist flag with relative path test. <a href="http://redmine.incredibuild.local/issues/9846">Bug #9846</a>.<br>
     * @pre{ }
     * @steps{
     * - Run the nunit slow sample tests with the --testlist=testlist.txt flag.}
     * @result{
     * - Build is succeeded.}
     */
    @Test(testName = "NUnit3 Relative Path Testlist Flag Test")
    public void nunit3RelativePathTestlistFlagTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_SLOW_TESTLIST_FLAG_TARGETDIR_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }
    /**
     * @test NUnit3 @FILE flag test.<br>
     * @pre{ }
     * @steps{
     * - Run the nunit slow sample tests with the @fileWithArguments.txt flag.}
     * @result{
     * - Build is succeeded.}
     */
    @Test(testName = "NUnit3 File Flag Test")
    public void nunit3FileFlagTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_SLOW_FILE_FLAG_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }
}
