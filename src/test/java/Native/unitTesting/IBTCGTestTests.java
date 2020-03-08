package Native.unitTesting;

import com.aventstack.extentreports.Status;
import frameworkInfra.testbases.UnitTestingTestBase;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.StaticDataProvider.IbLocations;
import frameworkInfra.utils.StaticDataProvider.Locations;
import frameworkInfra.utils.StaticDataProvider.ProjectsCommands;
import frameworkInfra.utils.SystemActions;
import frameworkInfra.utils.parsers.CustomJsonParser;
import frameworkInfra.utils.parsers.Parser;
import frameworkInfra.utils.parsers.XmlParser;
import ibInfra.ibExecs.IIBCoordMonitor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.util.StopWatch;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static frameworkInfra.Listeners.SuiteListener.test;

/**
 * @brief Unit tests execution with IbTestConsole
 * <p>
 * - GTest
 * @details Requires Unit Tests license solution
 */
public class IBTCGTestTests extends UnitTestingTestBase {

    private final String ERROR_MESSAGE_INTERNAL_ERROR = " Gtest flag '%s' is not supported.";
    private final String XML_OUTPUT_TESTSUITES = "testsuites";
    private final String XML_OUTPUT_TESTSUITE = "testsuite";

    private IIBCoordMonitor coordMonitor = new IIBCoordMonitor();

    /**
     * @test IBTC /targetdir flag test (the exe is written only with its name, without full path)<br>
     * @pre{ }
     * @steps{ - Run the google test master tests with /targetdir="c:\path\to\dll\dir" flag.}
     * @result{ - Build is succeeded.}
     */
    @Test(testName = "Targetdir Test")
    public void targetdirTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_CPPSORTER_TARGETDIR_TEST);
        Assert.assertEquals(exitCode, 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test IBTC /logfile & /loglevel flag test.<br>
     * @pre{ }
     * @steps{ - Run the google test master tests with /logfile="c:\path\to\log.txt" and /loglevel=info flag.}
     * @result{ - Build is succeeded;
     * - The log file is created.}
     */
    @Test(testName = "LogFile Test")
    public void logFileTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_CPPSORTER_LOGFILE_TEST);
        Assert.assertEquals(exitCode, 0, "The test execution failed with the exitcode " + exitCode);
        Assert.assertTrue(new File(Locations.OUTPUT_LOG_FILE).exists(), "LogFile is not found");
    }


    /**
     * @test GTest CPPSorter Assembly Level support test.<br>
     * @pre{ }
     * @steps{ - Run the google test master tests.}
     * @result{ - Build is succeeded.}
     */
    @Test(testName = "GTEST Assembly Level Test")
    public void gTestCPPSorterAssemblyLevelTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_CPPSORTER_TEST);
        Assert.assertEquals(exitCode, 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test GTest CPPSorter Test Level support test.<br>
     * @pre{ }
     * @steps{ - Run the google test master tests with /testlevel=8 flag.}
     * @result{ - Build is succeeded.}
     */
    @Test(testName = "GTest CPPSorter TestLevel=8 Test")
    public void gTestCPPSorterTestLevelTest8() throws InterruptedException {
        final String CORES_IN_USE = "\\d+ cores employed";
        final int expectedCoreInUse = 8;
        final String HOSTNAME = "windows-qa-1";

        Thread.sleep(4000);
        String output = winService.runCommandGetOutput(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_CPPSORTER_TEST + " /testlevel=" + expectedCoreInUse);
        int actualNumOfCoresInUse = SystemActions.extractNumberFromStringInText(output, CORES_IN_USE);
        Assert.assertEquals(actualNumOfCoresInUse, expectedCoreInUse, "The number of cores in use is not as expected!");
    }

    /**
     * @test GTest CPPSorter Test Level support test.<br>
     * @pre{ }
     * @steps{ - Run the google test master tests with /testlevel=10 flag.}
     * @result{ - Build is succeeded.}
     */
    @Test(testName = "GTest CPPSorter TestLevel=10 Test")
    public void gTestCPPSorterTestLevelTest10() throws InterruptedException {
        final String CORES_IN_USE = "\\d+ cores employed";
        final int expectedCoreInUse = 10;
        final String HOSTNAME = "windows-qa-1";

        Thread.sleep(5000);
        String output = winService.runCommandGetOutput(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_CPPSORTER_TEST + " /testlevel=" + expectedCoreInUse);
        int actualNumOfCoresInUse = SystemActions.extractNumberFromStringInText(output, CORES_IN_USE);
        Assert.assertEquals(actualNumOfCoresInUse, expectedCoreInUse, "The number of cores in use is not as expected!");
    }


    /**
     * @test GTest Master Test Level support test.<br>
     * @pre{ }
     * @steps{ - Run the google test master tests with /testlevel=12 flag.}
     * @result{ - Build is succeeded.}
     */
    @Test(testName = "GTest Master Test Level Test")
    public void gTestMasterTestLevelTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_MASTER_TESTLEVEL_TEST);
        Assert.assertEquals(exitCode, 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test GTest CPPSorter testlevel=deep test. (Every testcase runs in the separate task bar).<br>
     * @pre{ }
     * @steps{ - Run the google test master tests with /testlevel=deep flag.}
     * @result{ - Build is succeeded.}
     */
    @Test(testName = "GTest CPPSorter Testlevel Deep Test")
    public void gTestCPPSorterTestLevelDeepTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_CPPSORTER_TESTLEVEL_DEEP_TEST);
        Assert.assertEquals(exitCode, 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test GTest All Supported Flags test.<br>
     * @pre{ }
     * @steps{ - Run the google test master tests with all supported flags:
     * --gtest_filter=*int*<br>
     * --gtest_also_run_disabled_tests<br>
     * --gtest_repeat=100<br>
     * --gtest_shuffle<br>
     * --gtest_random_seed=1236<br>
     * --gtest_output=xml:C:\QA\Simulation\gtestResult.xml
     * .}
     * @result{ - Build is succeeded.
     * - Test result file is created.}
     */
    @Test(testName = "GTest All Supported Flags Test")
    public void gTestAllSupportedFlagsTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_CPPSORTER_FLAGS);
        Assert.assertEquals(exitCode, 0, "The test execution failed with the exitcode " + exitCode);
        File f = new File("C:\\QA\\Simulation\\gtestResult.xml");
        Assert.assertTrue(f.isFile(), "The test result file is not created");
        f.delete();
    }

    /**
     * @test GTest SameOS flag test.<br>
     * @pre{ }
     * @steps{ - Run the google test master  tests with /sameos flag}
     * @result{ - Build is succeeded;
     * - All assigned to the build agents have the same OS version: windows 10 or windows server 2016.}
     */
    @Test(testName = "GTest SameOS Flag Test")
    public void gTestSameOSFlagTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_MASTER_TESTLEVEL_TEST + " /sameos");
        Assert.assertEquals(exitCode, 0, "The test execution failed with the exitcode " + exitCode);
        Set<String> helpers = Parser.getHelpers(Locations.OUTPUT_LOG_FILE);
        for (String helper : helpers) {
            try {
                Assert.assertTrue(coordMonitor.getAgentOSVersion(helper).equals("10"), helper + " os version isn't equal to 10.");
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * @test GTest executable with failing tests<br>
     * @pre{ }
     * @steps{ - Run gtest executable with failing tests}
     * @result{ - Build is failing;
     */
    @Test(testName = "GTest With Failing Tests Test")
    public void gTestWithFailingTestsTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_EXECUTABLE_WITH_FAILING_TESTS);
        Assert.assertEquals(exitCode, 1000001, "The test was expected to fail with Error code 1000001");
    }

    /**
     * @test GTest executable with test that throws an exception<br>
     * @pre{ }
     * @steps{ - Run gtest executable with test that throws an exception}
     * @result{ - Build is failing;
     */
    @Test(testName = "GTest With Exception Thrown Test")
    public void gTestWithExceptionThrownTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_EXECUTABLE_WITH_TEST_THAT_THROWS_AN_EXCEPTION);
        Assert.assertEquals(exitCode, 1000001, "The test was expected to fail with Error code 1000001");
    }

    /**
     * @test GTest executable with test that crashes<br>
     * @pre{ }
     * @steps{ - Run gtest executable with test that crashes}
     * @result{ - Build is failing;
     */
    @Test(testName = "GTest With Crash Test")
    public void gTestWithCrashTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_EXECUTABLE_THAT_CRASHES);
        Assert.assertEquals(exitCode, 1000001, "The test was expected to fail with Error code 1000001");
    }

    /**
     * @test Verify <a href="https://docs.google.com/document/d/14uCrC8cqjP1o_nBh0gEwAr8SLC4odY2yNrtkWc_jQ6U/edit?usp=sharing">only fail locally feature </a> is ON
     * @pre{ - Set OnlyFailLocally reg key to ON
     * - Set Avoid local reg key to ON
     * }
     * @steps{ - Run the gtest executable that fails on the remote cores, but is succeeded on local cores
     * }
     * @result{ - The build is succeeded;
     * Post:
     * - Set OnlyFailLocally reg key to OFF
     * - Set Avoid local reg key to OFF
     * }
     */
    @Test(testName = "GTest Verify OnlyFailLocally Flag Positive Test")
    public void gTestVerifyOnlyFailLocallyFlagPositiveTest() {
        setRegistry("1", "Builder", StaticDataProvider.RegistryKeys.AVOID_LOCAL);
        setRegistry("1", "Builder", StaticDataProvider.RegistryKeys.ONLY_FAIL_LOCALLY);
        String result = "";
        winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_EXECUTABLE_FAILS_ON_REMOTE_NOT_ON_LOCAL);
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertEquals(result, "0", "verifyOnlyFailLocallyFlag failed with exit code " + result);
        } catch (IOException e) {
            test.log(Status.ERROR, "Test failed with the following error: " + e.getMessage());
        } finally {
            setRegistry("0", "Builder", StaticDataProvider.RegistryKeys.AVOID_LOCAL);
            setRegistry("0", "Builder", StaticDataProvider.RegistryKeys.ONLY_FAIL_LOCALLY);
        }
    }

    /**
     * @test Verify <a href="https://docs.google.com/document/d/14uCrC8cqjP1o_nBh0gEwAr8SLC4odY2yNrtkWc_jQ6U/edit?usp=sharing">only fail locally feature</a> is OFF
     * @pre{ - Set Avoid local reg key to ON
     * }
     * @steps{ - Run gtest executable that fails on the remote cores, but is succeeded on local cores
     * }
     * @result{ - The build is failed;
     * Post:
     * - Set Avoid local reg key to OFF
     * }
     */
    @Test(testName = "GTest Verify OnlyFailLocally Flag Negative Test")
    public void gTestVerifyOnlyFailLocallyFlagNegativeTest() {
        setRegistry("1", "Builder", StaticDataProvider.RegistryKeys.AVOID_LOCAL);
        String result = "";
        winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_EXECUTABLE_FAILS_ON_REMOTE_NOT_ON_LOCAL);
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertEquals(result, "1000001", "verifyOnlyFailLocallyFlagNegativeTest failed with exit code " + result);
        } catch (IOException e) {
            test.log(Status.ERROR, "Test failed with the following error: " + e.getMessage());
        } finally {
            setRegistry("0", "Builder", StaticDataProvider.RegistryKeys.AVOID_LOCAL);
        }
    }

    /**
     * @test GTest executable for Stress test<br>
     * @pre{ }
     * @steps{ - Run gtest executable for stress}
     * @result{ - Build is failing;
     */
    @Test(testName = "GTest Stress Test")
    public void gTestStressTest() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_EXECUTABLE_STRESS_TEST);
        stopWatch.stop();
        Assert.assertEquals(exitCode, 0, "The test execution failed with the exitcode " + exitCode);
        // Calculate test duration
        long durationMillis = stopWatch.getTotalTimeMillis();
        int numOfTests = 100;
        long testDuration = durationMillis / numOfTests;
        Assert.assertTrue(testDuration <= 1020, "The test execution took longer than expected! Expected time per test is: less than 1020ms, Actual was: " + testDuration);

    }

    @Ignore
    /**
     * @test GTest executable for Stress per 10 test<br>
     * @pre{ }
     * @steps{ - Run gtest executable for stress}
     * @result{ - Build is failing;
     */
    @Test(testName = "GTest Stress per 10 Test")
    public void gTestStressPer10Test() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_EXECUTABLE_STRESS_BY_10_INPUTFILE_TEST);
        Assert.assertEquals(exitCode, 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test GTest executable for Extremely Long test(10 hours)<br>
     * @pre{ }
     * @steps{ - Run gtest executablefor stress}
     * @result{ - Build is failing;
     */
    @Ignore
    @Test(testName = "GTest Extremely Long Test")
    public void gTestExtremelyLongTest() {
        int exitCode = winService.runCommandWaitForFinish(ProjectsCommands.TESTING_ROBIN.GTEST_OPENCV_ALL_EXECUTABLES_TEST);
        Assert.assertEquals(exitCode, 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test GTest running multiple google executables with google flags<br>
     * @pre{ }
     * @steps{ - Run multiple google executables with google flags}
     * @result{ - Builds are succeeded;
     */
    @Test(testName = "GTest CLI Multiple Executables Test")
    public void gTestCLIMultipleExecutableTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_CPPSORTER_TEST + " " + ProjectsCommands.TESTING_ROBIN.GTEST_MASTER_FLAGS);
        Assert.assertEquals(exitCode, 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test GTest running multiple google executables and check test discovery<br>
     * @pre{ }
     * @steps{ - Run multiple google executables and check Test discovery}
     * @result{ - Builds are succeeded;
     */
    @Test(testName = "GTest CLI Multiple Executables Check Test Discovery Test")
    public void gTestCLIMultipleExecutablesCheckTestDiscoveryTest() {
        String output = winService.runCommandGetOutput(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_CPPSORTER_TEST + " " + ProjectsCommands.TESTING_ROBIN.GTEST_MASTER_FLAGS);
        Assert.assertTrue(output.contains("Analyzing files for tests to execute"), "The Test discovery details are not as expected!");
        Assert.assertTrue(output.contains("Analyzing  cpp_sorter_test.exe for tests..."), "The Test discovery details are not as expected!");
        Assert.assertTrue(output.contains("Analyzing  sample6_unittest.exe for tests..."), "The Test discovery details are not as expected!");
        Assert.assertTrue(output.contains("found 0 tests in cpp_sorter_test.exe"), "The Test discovery details are not as expected!");
        Assert.assertTrue(output.contains("found 4 tests in sample6_unittest.exe"), "The Test discovery details are not as expected!");
        Assert.assertTrue(output.contains("4 tests discovered from 2 files"), "The Test discovery details are not as expected!");
        Assert.assertTrue(output.contains("GTest: cpp_sorter_test.exe sample6_unittest.exe"), "The Test discovery details are not as expected!");
    }

    /**
     * @test GTest running multiple google executables with google flags with txt Input file<br>
     * @pre{ }
     * @steps{ - Run multiple google executables with google flags with txt Input file}
     * @result{ - Builds are succeeded;
     */
    @Test(testName = "GTest Txt InputFile Multiple Executables Test")
    public void gTestTxtInputFileMultipleExecutablesTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_MUTIPLE_EXECUTABLES_TXT_INPUT_FILE);
        Assert.assertEquals(exitCode, 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test GTest running multiple google executables with google flags with Doc Input file<br>
     * @pre{ }
     * @steps{ - Run multiple google executables with google flags with txt Input file}
     * @result{ - Builds are succeeded;
     */
    @Test(testName = "GTest Doc InputFile Multiple Executables Test")
    public void gTestDocInputFileMultipleExecutablesTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_MUTIPLE_EXECUTABLES_DOC_INPUT_FILE);
        Assert.assertEquals(exitCode, 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test GTest running multiple google executables with google flags with Html Input file<br>
     * @pre{ }
     * @steps{ - Run multiple google executables with google flags with txt Input file}
     * @result{ - Builds are succeeded;
     */
    @Test(testName = "GTest Html InputFile Multiple Executables Test")
    public void gTestHtmlInputFileMultipleExecutablesTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_MUTIPLE_EXECUTABLES_HTML_INPUT_FILE);
        Assert.assertEquals(exitCode, 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test GTest running multiple google executables with Input file and with /test=gtest.  <a href="http://redmine.incredibuild.local/issues/13111">Bug #13111</a>.<br><br>
     * @pre{ }
     * @steps{ - Run multiple google executables with Input file and with /test=gtest}
     * @result{ - Builds are succeeded;
     */
    @Test(testName = "GTest With InputFile and with /test=gtest")
    public void gTestInputFileAndTestEqualGTestTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_OPENCV_INPUT_FILE_ + " /test=gtest");
        Assert.assertEquals(exitCode, 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test GTest running multiple google executables with google flags with an invalid Input file<br>
     * @pre{ }
     * @steps{ - Run multiple google executables with google flags with an invalid Input file}
     * @result{ - Builds fail;
     */
    @Test(testName = "GTest Invalid InputFile Multiple Executables Test")
    public void gTestInvalidInputFileMultipleExecutablesTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_MUTIPLE_EXECUTABLES_INVALID_INPUT_FILE);
        Assert.assertEquals(exitCode, -4, "The test execution was expected to fail");
    }

    /**
     * @test GTest executable with /test=gtest flag test.<br>
     * @pre{ }
     * @steps{ - Run gtest executable with /test=gtest flag}
     * @result{ - Build is succeeded;
     */
    @Test(testName = "GTest Run GTest Executable With /test=gtest Test")
    public void gTestGTestExecutableWithGtestFlagWithTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + " /test=gtest " + ProjectsCommands.TESTING_ROBIN.GTEST_CPPSORTER_TEST);
        Assert.assertEquals(exitCode, 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test Nunit2 with /test=gtest flag test.<br>
     * @pre{ }
     * @steps{ - Run nunit2 dll with /test=gtest flag}
     * @result{ - Build is failing;
     */
    @Test(testName = "Run NUnit2 Dll With /test=gtest Test")
    public void nunit2WithTestFlagEqualToGtestTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + " /test=gtest " + ProjectsCommands.TESTING_ROBIN.NUNIT2_FRAMEWORK_1DLL_TEST);
        Assert.assertEquals(exitCode, -4, "The test was expected to fail");
    }

    /**
     * @test Nunit3 with /test=gtest flag test.<br>
     * @pre{ }
     * @steps{ - Run nunit2 dll with /test=gtest flag}
     * @result{ - Build is failing;
     */
    @Test(testName = "Run Non GTest Executable With /test=gtest Test")
    public void gTestNonGTestExecutableWithTestFlagEqualToGtestTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + " /test=gtest " + ProjectsCommands.TESTING_ROBIN.GTEST_NON_GTEST_EXECUTABLE);
        Assert.assertEquals(exitCode, -4, "The test was expected to fail");
    }

    /**
     * @test GTest executable /test=gtest flag test.<br>
     * @pre{ }
     * @steps{ - Run the google test master  tests with /test=gtest flag}
     * @result{ - Build is succeeded;
     */
    @Test(testName = "GTest run GTest executable with Test Filter Test")
    public void gTestRunGTestExecutableWithNUnitTestFilterTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_CPPSORTER_TEST + " /test=nunit2");
        Assert.assertEquals(exitCode, -4, "The test execution failed with the exitcode " + exitCode);
    }


    /**
     * @test GTest executable with test filter --gtest_output=xml test.<br>
     * @pre{ }
     * @steps{ - Run the gtest executable with test filter --gtest_output=xml}
     * @result{ - Build is succeeded;
     */
    @Test(testName = "GTest Validate Number Of Tests In Xml Output Test")
    public void gTestValidateNumberOfTestsInXmlOutputTest() throws InterruptedException, ParserConfigurationException, SAXException, IOException {
        String outputFile = "C:\\QA\\Simulation\\GtestResult.xml";
        String ibOutputFile = "C:\\QA\\Simulation\\ibGtestResult.xml";
//        String outputFile = "C:\\QA\\Simulation\\out1.json";
//        String ibOutputFile = "C:\\QA\\Simulation\\out2.json";
        int exitCode = winService.runCommandWaitForFinish(ProjectsCommands.TESTING_ROBIN.GTEST_MASTER_TESTLEVEL_TEST + String.format(" --gtest_output=xml:%s", outputFile));
        Assert.assertEquals(exitCode, 0, "The test execution(without ibtc) failed with the exitcode " + exitCode);
        int exitCode2 = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_MASTER_TESTLEVEL_TEST + String.format(" --gtest_output=xml:%s", ibOutputFile));
        Assert.assertEquals(exitCode2, 0, "The test execution(with ibtc) failed with the exitcode " + exitCode2);

        String s = compareXmlOutputFiles(ibOutputFile, outputFile);

        final String TEXT_TO_FIND = "<testsuites tests=\"\\d+\"";
        int n1 = numberOfTests(outputFile, TEXT_TO_FIND);
        int n2 = numberOfTests(ibOutputFile, TEXT_TO_FIND);
        Assert.assertEquals(n1, n2, "The number of tests running with or without ibtc should be equal!");
    }

    /**
     * @test GTest executable compare JSON output IBTC vs. no IBTC test.<br>
     * @pre{ }
     * @steps{ - Run the gtest executable with IBTC and without IBTC with test filter --gtest_output=json }
     * @result{ - Builds are successful. Beside time Output are identical;
     */
    @Test(testName = "GTest Compare Json Output Files IB vs. No IB Test")
    public void gTestCompareJsonOutputFilesIBvsNoIBTest() throws ParserConfigurationException, SAXException, IOException, ParseException {
        String outputFile = "C:\\QA\\Simulation\\GtestResult.json";
        String ibOutputFile = "C:\\QA\\Simulation\\ibGtestResult.json";
        int exitCode = winService.runCommandWaitForFinish(ProjectsCommands.TESTING_ROBIN.GTEST_CPPSORTER_TEST + String.format(" --gtest_output=json:%s", outputFile));
        Assert.assertEquals(exitCode, 0, "The test execution(without ibtc) failed with the exitcode " + exitCode);
        int exitCode2 = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_CPPSORTER_TEST + String.format(" --gtest_output=json:%s", ibOutputFile));
        Assert.assertEquals(exitCode2, 0, "The test execution(with ibtc) failed with the exitcode " + exitCode2);

//        String diff = compareJsonOutputFiles(ibOutputFile, outputFile);
//        Assert.assertTrue(diff.isEmpty(), "Json Output files without IBTC and with IBTC are not identical! \n" + diff);
        Boolean areEquals = compareJsonOutputFiles(outputFile, ibOutputFile);
        Assert.assertTrue(areEquals, "Json Output files without IBTC and with IBTC are not identical! \n");

    }

    /**
     * @test GTest executable compare JSON output /testlevel=auto vs. testlevel=deep test.<br>
     * @pre{ }
     * @steps{ - Run the gtest executable with /testlevel=auto vs. /testlevel=deep with test filter --gtest_output=json }
     * @result{ - Builds are successful. Beside time Output are identical;
     */
    @Test(testName = "GTest Compare Json Output Files Auto vs. Deep Test")
    public void gTestCompareJsonOutputTestLevelAutoVsDeepTest() throws ParserConfigurationException, SAXException, IOException, ParseException, InterruptedException {
        String outputFile = "C:\\QA\\Simulation\\GtestResult.json";
        String ibOutputFile = "C:\\QA\\Simulation\\ibGtestResult.json";
        //SystemActions.sleep(3);
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_MASTER_TEST + " /testlevel=auto " + String.format(" --gtest_output=json:%s", outputFile));
        Assert.assertEquals(exitCode, 0, "The test execution(/testlevel=auto) failed with the exitcode " + exitCode);
        int exitCode2 = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_MASTER_TEST + " /testlevel=deep " + String.format(" --gtest_output=json:%s", ibOutputFile));
        Assert.assertEquals(exitCode2, 0, "The test execution(/testlevel=deep) failed with the exitcode " + exitCode2);

//        String diff = compareJsonOutputFiles(ibOutputFile, outputFile);
//        Assert.assertTrue(diff.isEmpty(), "Json Output files with /testlevel=auto and with /testlevel=deep are not identical!\n" + diff);
        Boolean areEquals = compareJsonOutputFiles(outputFile, ibOutputFile);
        Assert.assertTrue(areEquals, "Json Output files without IBTC and with IBTC are not identical! \n");
    }

    /**
     * @test GTest OpenCV executable compare JSON output IBTC vs. no IBTC test.<br>
     * @pre{ }
     * @steps{ - Run the gtest executable with IBTC and without IBTC with test filter --gtest_output=json }
     * @result{ - Builds are successful. Beside time Output are identical;
     */
    @Test(testName = "GTest OpenCV Compare Json Output Files IB vs. No IB Test")
    public void gTestOpenCVCompareJsonOutputFilesIBvsNoIBTest() throws ParserConfigurationException, SAXException, IOException, ParseException {
        String noIbtcOutputFile = "C:\\QA\\Simulation\\opencv_flannd_noibtc.json";
        String ibtcOutputFile = "C:\\QA\\Simulation\\opencv_flannd_auto.json";
        int exitCode2 = winService.runCommandWaitForFinish(ProjectsCommands.TESTING_ROBIN.GTEST_OPENCV_NO_IBTC_TEST);
        Assert.assertEquals(exitCode2, 0, "The test execution(with ibtc) failed with the exitcode " + exitCode2);
        int exitCode = winService.runCommandWaitForFinish(ProjectsCommands.TESTING_ROBIN.GTEST_OPENCV_TESTLEVEL_AUTO_TEST);
        Assert.assertEquals(exitCode, 0, "The test execution(without ibtc) failed with the exitcode " + exitCode);

        Boolean areEquals = compareJsonOutputFiles(noIbtcOutputFile, ibtcOutputFile);
        Assert.assertTrue(areEquals, "Json Output files without IBTC and with IBTC are not identical! \n");
    }

    /**
     * @test GTest OpenCV executable compare JSON output /testlevel=auto vs. testlevel=deep test.<br>
     * @pre{ }
     * @steps{ - Run the gtest executable with /testlevel=auto vs. /testlevel=deep with test filter --gtest_output=json }
     * @result{ - Builds are successful. Beside time Output are identical;
     */
    @Test(testName = "GTest OpenCV Compare Json Output Files Auto vs. Deep Test")
    public void gTestOpenCVCompareJsonOutputTestLevelAutoVsDeepTest() throws ParserConfigurationException, SAXException, IOException, ParseException, InterruptedException {
        String autoOutputFile = "C:\\QA\\Simulation\\opencv_flannd_auto.json";
        String deepOutputFile = "C:\\QA\\Simulation\\opencv_flannd_deep.json";
        //Thread.sleep(4000);

        int exitCode = winService.runCommandWaitForFinish(ProjectsCommands.TESTING_ROBIN.GTEST_OPENCV_TESTLEVEL_AUTO_TEST);
        Assert.assertEquals(exitCode, 0, "The test execution(without ibtc) failed with the exitcode " + exitCode);
        int exitCode2 = winService.runCommandWaitForFinish(ProjectsCommands.TESTING_ROBIN.GTEST_OPENCV_TESTLEVEL_DEEP_TEST);
        Assert.assertEquals(exitCode2, 0, "The test execution(with ibtc) failed with the exitcode " + exitCode2);

//        String diff = compareJsonOutputFiles(autoOutputFile, deepOutputFile);
//        Assert.assertTrue(diff.isEmpty(), "Json Output files with /testlevel=auto and with /testlevel=deep are not identical!\n" + diff);
        Boolean areEquals = compareJsonOutputFiles(autoOutputFile, deepOutputFile);
        Assert.assertTrue(areEquals, "Json Output files without IBTC and with IBTC are not identical! \n");
    }

    /**
     * @test GTest unsupported flag --gtest_list_tests test.<br>
     * @pre{ }
     * @steps{ - - Run the google test master tests with unsupported flag: --gtest_list_tests}
     * @result{ - Build fails with Internal error: Internal error: Unsupported gtest option '--gtest_list_tests'
     */
    @Test(testName = "Not supported Flag --gtest_list_tests Test")
    public void notSupportedFlagGTestListTestsTest() {
        String gtestFlag = ProjectsCommands.TESTING_ROBIN.UNSUPPORTED_FLAG_GTEST_LIST_TESTS;
        String expectedErrorMessage = String.format(ERROR_MESSAGE_INTERNAL_ERROR, gtestFlag);
        String output = winService.runCommandGetOutput(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_CPPSORTER_TESTLEVEL_TEST + " " + gtestFlag);
        Assert.assertTrue(output.toLowerCase().contains(expectedErrorMessage.toLowerCase()), "This filter should not be supported!");
    }

    /**
     * @test GTest unsupported flag --gtest_color test.<br>
     * @pre{ }
     * @steps{ - - Run the google test master tests with unsupported flag: --gtest_color}
     * @result{ - Build fails with Internal error: Internal error: Unsupported gtest option '--gtest_color'
     */
    @Test(testName = "Not supported Flag --gtest_color Test")
    public void notSupportedFlagGTestColorTest() {
        String gtestFlag = ProjectsCommands.TESTING_ROBIN.UNSUPPORTED_FLAG_GTEST_COLOR;
        String expectedErrorMessage = String.format(ERROR_MESSAGE_INTERNAL_ERROR, gtestFlag);
        String output = winService.runCommandGetOutput(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_CPPSORTER_TESTLEVEL_TEST + " " + gtestFlag);
        Assert.assertTrue(output.toLowerCase().contains(expectedErrorMessage.toLowerCase()), "This filter should not be supported!");
    }

    /**
     * @test GTest unsupported flag --gtest_print_time test.<br>
     * @pre{ }
     * @steps{ - - Run the google test master tests with unsupported flag: --gtest_print_time}
     * @result{ - Build fails with Internal error: Internal error: Unsupported gtest option '--gtest_print_time'
     */
    @Test(testName = "Not supported Flag --gtest_print_time Test")
    public void notSupportedFlagGTestPrintTimeTest() {
        String gtestFlag = ProjectsCommands.TESTING_ROBIN.UNSUPPORTED_FLAG_GTEST_PRINT_TIME;
        String expectedErrorMessage = String.format(ERROR_MESSAGE_INTERNAL_ERROR, gtestFlag);
        String output = winService.runCommandGetOutput(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_CPPSORTER_TESTLEVEL_TEST + " " + gtestFlag + "=0");
        Assert.assertTrue(output.toLowerCase().contains(expectedErrorMessage.toLowerCase()), "This filter should not be supported!");
    }

    /**
     * @test GTest unsupported flag --gtest_break_on_failure test.<br>
     * @pre{ }
     * @steps{ - - Run the google test master tests with unsupported flag: --gtest_break_on_failure}
     * @result{ - Build fails with Internal error: Internal error: Unsupported gtest option '--gtest_break_on_failure'
     */
    @Test(testName = "Not supported Flag --gtest_break_on_failure Test")
    public void notSupportedFlagGTestBreakOnFailureTest() {
        String gtestFlag = ProjectsCommands.TESTING_ROBIN.UNSUPPORTED_FLAG_GTEST_BREAK_ON_FAILURE;
        String expectedErrorMessage = String.format(ERROR_MESSAGE_INTERNAL_ERROR, gtestFlag);
        String output = winService.runCommandGetOutput(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_CPPSORTER_TESTLEVEL_TEST + " " + gtestFlag);
        Assert.assertTrue(output.toLowerCase().contains(expectedErrorMessage.toLowerCase()), "This filter should not be supported!");
    }

    /**
     * @test GTest unsupported flag --gtest_throw_on_failure test.<br>
     * @pre{ }
     * @steps{ - - Run the google test master tests with unsupported flag: --gtest_throw_on_failure}
     * @result{ - Build fails with Internal error: Internal error: Unsupported gtest option '--gtest_throw_on_failure'
     */
    @Test(testName = "Not supported Flag --gtest_throw_on_failure Test")
    public void notSupportedFlagGTestThrowOnFailureTest() {
        String gtestFlag = ProjectsCommands.TESTING_ROBIN.UNSUPPORTED_FLAG_GTEST_THROW_ON_FAILURE;
        String expectedErrorMessage = String.format(ERROR_MESSAGE_INTERNAL_ERROR, gtestFlag);
        String output = winService.runCommandGetOutput(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_CPPSORTER_TESTLEVEL_TEST + " " + gtestFlag);
        Assert.assertTrue(output.toLowerCase().contains(expectedErrorMessage.toLowerCase()), "This filter should not be supported!");
    }

    /**
     * @test GTest unsupported flag --gtest_catch_exceptions test.<br>
     * @pre{ }
     * @steps{ - - Run the google test master tests with unsupported flag: --gtest_catch_exceptions}
     * @result{ - Build fails with Internal error: Internal error: Unsupported gtest option '--gtest_catch_exceptions'
     */
    @Test(testName = "Not supported Flag --gtest_catch_exceptions Test")
    public void notSupportedFlagGTestCatchExceptionsTest() {
        String gtestFlag = ProjectsCommands.TESTING_ROBIN.UNSUPPORTED_FLAG_GTEST_CATCH_EXCEPTIONS;
        String expectedErrorMessage = String.format(ERROR_MESSAGE_INTERNAL_ERROR, gtestFlag);
        String output = winService.runCommandGetOutput(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_CPPSORTER_TESTLEVEL_TEST + " " + gtestFlag);
        Assert.assertTrue(output.toLowerCase().contains(expectedErrorMessage.toLowerCase()), "This filter should not be supported!");
    }

    /*------------------------------METHODS------------------------------*/

    private void setRegistry(String required, String folder, String keyName) {
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\" + folder, keyName, required);
    }

    private String getRegistry(String folder, String keyName) {
        return RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\" + folder, keyName);
    }

    private int numberOfTests(String filePath, String textToFind) throws InterruptedException {
        String fileContent = SystemActions.getFileContent(filePath);
        Matcher matcher = SystemActions.searchPattern(fileContent, textToFind);
        int numberOfTests = 0;
        while (matcher.find()) {
            numberOfTests++;
        }
        return numberOfTests;
    }

    private String compareXmlOutputFiles(String actualXml, String expectedXml) throws IOException, SAXException, ParserConfigurationException {
        NodeList testSuites = XmlParser.retrieveNodeListPerElementName(actualXml, XML_OUTPUT_TESTSUITES);

        return "";

    }


//    private String compareJsonOutputFiles(String actualJson, String expectedJson) throws IOException, ParseException {
//        JSONParser parser = new JSONParser();
//        Object obj = parser.parse(new FileReader(actualJson));
//        JSONObject expectedJo = (JSONObject) obj;
//
//        parser = new JSONParser();
//        obj = parser.parse(new FileReader(expectedJson));
//        JSONObject actualJo = (JSONObject) obj;
//
//        Iterator<String> keys = expectedJo.keySet().iterator();
//        while(keys.hasNext()) {
//            //if ( json.getJSONObject(key) instanceof JSONObject )
//
//        }
//
//        return "";
//
//    }

    private boolean compareJsonOutputFiles(String actualJson, String expectedJson) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        FileReader actualFr = new FileReader(actualJson);
        Object actual = parser.parse(actualFr);

        parser = new JSONParser();
        FileReader expectedFr = new FileReader(expectedJson);
        Object expected = parser.parse(expectedFr);

        boolean b = CustomJsonParser.areEqual(actual, expected);

        actualFr.close();
        actualFr = null;
        expectedFr.close();
        expectedFr = null;
        return b;
    }

    private String depcompareJsonOutputFiles(String actualJson, String expectedJson) throws IOException, SAXException, ParserConfigurationException, ParseException {
        //timestamp
        JSONParser parser = new JSONParser();
        FileReader actualFr = new FileReader(actualJson);
        Object obj = parser.parse(actualFr);
        JSONObject jsonObject = (JSONObject) obj;
        JSONArray actualTestsuites = (JSONArray) jsonObject.get(XML_OUTPUT_TESTSUITES);

        parser = new JSONParser();
        FileReader expectedFr = new FileReader(actualJson);
        obj = parser.parse(expectedFr);
        jsonObject = (JSONObject) obj;
        JSONArray expectedTestsuites = (JSONArray) jsonObject.get(XML_OUTPUT_TESTSUITES);

        Iterator it = expectedTestsuites.iterator();
        while (it.hasNext()) {
            JSONObject expectedTestsuite = (JSONObject) it.next();
            String testsuiteName = expectedTestsuite.get("name").toString();
            boolean actualElementFound = false;
            for (Object o : actualTestsuites) {
                JSONObject actualTestsuite = (JSONObject) o;
                String actualSuiteName = actualTestsuite.get("name").toString();
                if (!actualSuiteName.equals(testsuiteName)) {
                    continue;
                } else {
                    actualElementFound = true;
                    String diff = compareTestSuites(actualSuiteName, expectedTestsuite, actualTestsuite);
                    if (diff.isEmpty()) {
                        break;
                    } else return diff;
                }
            } // finding the actual element
            if (!actualElementFound) {
                return String.format("Testsuite '%s' was not found in file '%s'!", testsuiteName, actualJson);
            }
        }  // running through the expected elements

        obj = null;
        jsonObject = null;
        actualTestsuites = null;
        expectedTestsuites = null;
        actualFr.close();
        actualFr = null;
        expectedFr.close();
        expectedFr = null;
        return "";

    }

    private String compareTestSuites(String testsuiteName, JSONObject expectedTestsuites, JSONObject actualTestsuites) {
        Iterator<String> keys = expectedTestsuites.keySet().iterator();
        while (keys.hasNext()) {
            String currentKey = keys.next();
            if (currentKey.contains("time")) {
                continue;
            }
            if (currentKey.equals("testsuite")) {
                //continue;
                JSONArray expectedTestsuite = (JSONArray) expectedTestsuites.get(currentKey);
                JSONArray actualTestsuite = (JSONArray) actualTestsuites.get(currentKey);
                String diff = compareTestsuite(expectedTestsuite, actualTestsuite);
                if (!diff.isEmpty()) {
                    return diff;
                }
                continue;
            }
            String expectedValue = expectedTestsuites.get(currentKey).toString();
            String actualValue = actualTestsuites.get(currentKey).toString();
            if (!actualValue.equals(expectedValue)) {
                return String.format("A different value was found for Testsuite '%s' for parameter '%s'. Expected: %s, Actual: %s", testsuiteName, currentKey, expectedValue, actualValue);
            }
        }
        return "";
    }

    private String compareTestsuite(JSONArray expectedTestSuiteArr, JSONArray actualTestSuiteArr) {
        Iterator it = expectedTestSuiteArr.iterator();
        while (it.hasNext()) {
            JSONObject expectedTestsuite = (JSONObject) it.next();
            String testsuiteName = expectedTestsuite.get("name").toString();
            boolean actualElementFound = false;
            for (Object o : actualTestSuiteArr) {
                JSONObject actualTestsuite = (JSONObject) o;
                String actualSuiteName = actualTestsuite.get("name").toString();
                if (!actualSuiteName.equals(testsuiteName)) {
                    continue;
                } else {
                    actualElementFound = true;
                    String diff = compareTestSuites(actualSuiteName, expectedTestsuite, actualTestsuite);
                    if (diff.isEmpty()) {
                        break;
                    } else return diff;
                }
            } // finding the actual element
            if (!actualElementFound) {
                return String.format("Testsuite '%s' was not found!", testsuiteName);
            }

        } // for

        return "";
    }
}
