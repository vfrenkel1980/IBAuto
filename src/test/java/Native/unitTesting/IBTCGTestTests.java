package Native.unitTesting;

import com.aventstack.extentreports.Status;
import frameworkInfra.testbases.UnitTestingTestBase;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.StaticDataProvider.IbLocations;
import frameworkInfra.utils.StaticDataProvider.Locations;
import frameworkInfra.utils.StaticDataProvider.ProjectsCommands;
import frameworkInfra.utils.SystemActions;
import frameworkInfra.utils.parsers.Parser;
import ibInfra.ibExecs.IIBCoordMonitor;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
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

    private final String ERROR_MESSAGE_INTERNAL_ERROR = "[error]: Gtest flag '%s'";

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
        RegistryService.createRegValue(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\Builder", StaticDataProvider.RegistryKeys.SHOWCORESEMPLOYED, "1");
        final String CORES_IN_USE = "\\d+ cores employed";
        final int expectedCoreInUse = 8;
        final String HOSTNAME = "Robin";

        Thread.sleep(4000);
        String output = winService.runCommandGetOutput(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_CPPSORTER_TEST + " /testlevel=" + expectedCoreInUse);
        int actualNumOfCoresInUse = SystemActions.extractNumberFromStringInText(output, CORES_IN_USE);
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\Builder", StaticDataProvider.RegistryKeys.SHOWCORESEMPLOYED, "0");
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
        RegistryService.createRegValue(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\Builder", StaticDataProvider.RegistryKeys.SHOWCORESEMPLOYED, "1");
        final String CORES_IN_USE = "\\d+ cores employed";
        final int CoreInUse = 10;
        final String HOSTNAME = "Robin";

        Thread.sleep(4000);
        String output = winService.runCommandGetOutput(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_CPPSORTER_TEST + " /testlevel=" + CoreInUse);
        int actualNumOfCoresInUse = SystemActions.extractNumberFromStringInText(output, CORES_IN_USE);
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\Builder", StaticDataProvider.RegistryKeys.SHOWCORESEMPLOYED, "0");
        Assert.assertTrue(actualNumOfCoresInUse >= 8, "The number of cores in use is not as expected!");
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
        Assert.assertEquals(exitCode, 3, "The test was expected to fail with Error code 3");
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
        Assert.assertEquals(exitCode, 1, "The test was expected to fail with Error code 1000001");
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
        Assert.assertEquals(exitCode, 1, "The test was expected to fail with Error code 1");
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
            Assert.assertEquals(result, "1", "verifyOnlyFailLocallyFlagNegativeTest failed with exit code " + result);
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
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_EXECUTABLE_STRESS_TEST);
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
        // cd C:\QA\Simulation\Testing\google-tests\Executables\openCV
        // Locations.GTEST_ROOT_PATH + "\\Executables\\openCV";
        // set OPENCV_TEST_DATA_PATH=C:\QA\Simulation\Testing\google-tests\Executables\openCV\testdata\
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_EXECUTABLE_EXTREMELY_LONG_TEST);
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
        final String TEST_DISCOVERY = "Analyzing files for tests to execute\n" +
                "Analyzing  cpp_sorter_test.exe for tests...\n" +
                "Analyzing  sample6_unittest.exe for tests...\n" +
                "found 0 tests in cpp_sorter_test.exe\n" +
                "found 0 tests in cpp_sorter_test.exe\n" +
                "found 4 tests in sample6_unittest.exe\n" +
                "4 tests discovered from 2 files\n" +
                "\n" +
                "GTest: cpp_sorter_test.exe sample6_unittest.exe";
        Assert.assertTrue(output.startsWith(TEST_DISCOVERY), "The Test discovery details are not as expected!");
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

    //comparing output files: xml and json
//    testsuites tests="12"

    /**
     * @test GTest executable with test filter --gtest_output=xml test.<br>
     * @pre{ }
     * @steps{ - Run the gtest executable with test filter --gtest_output=xml}
     * @result{ - Build is succeeded;
     */
    @Test(testName = "GTest Validate Number Of Tests In Xml Output Test")
    public void gTestValidateNumberOfTestsInXmlOutputTest() throws InterruptedException {
//        String outputFile = "C:\\QA\\Simulation\\GtestResult.xml";
//        String ibOutputFile = "C:\\QA\\Simulation\\ibGtestResult.xml";
        String outputFile = "C:\\QA\\Simulation\\out1.xml";
        String ibOutputFile = "C:\\QA\\Simulation\\out2.xml";
        int exitCode = winService.runCommandWaitForFinish(ProjectsCommands.TESTING_ROBIN.GTEST_CPPSORTER_TEST + String.format(" --gtest_output=xml:%s", outputFile));
        Assert.assertEquals(exitCode, 0, "The test execution(without ibtc) failed with the exitcode " + exitCode);
        int exitCode2 = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_CPPSORTER_TEST + String.format(" --gtest_output=xml:%s", ibOutputFile));
        Assert.assertEquals(exitCode2, 0, "The test execution(with ibtc) failed with the exitcode " + exitCode);
        final String TEXT_TO_FIND = "<testsuites tests=\"\\d+\"";
        int n1 = numberOfTests(outputFile, TEXT_TO_FIND);
        int n2 = numberOfTests(ibOutputFile, TEXT_TO_FIND);
        Assert.assertEquals(n1, n2, "The number of tests running with or without ibtc should be equal!");
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

    private static int numberOfTests(String filePath, String textToFind) throws InterruptedException {
        String fileContent = SystemActions.getFileContent(filePath);
        Matcher matcher = SystemActions.searchPattern(fileContent, textToFind);
        int numberOfTests = 0;
        while (matcher.find()) {
            numberOfTests++;
        }
        return numberOfTests;
    }
}
