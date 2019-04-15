package Native.unitTesting;

import frameworkInfra.testbases.RobinTestingTestBase;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.parsers.Parser;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @brief Unit tests execution with IbConsole
 * @details Requires Unit Tests license solution
 */
public class IBCTestingTests extends RobinTestingTestBase {

    /**
     * Framework tests:
     * VSTest
     * XUnit
     * Google Test (Gtest)
     * CppUTest
     * QTtest
     * CTest
     */


    /**
     * @test Cpp utest support test.<br>
     * @pre{ <a href="https://github.com/cpputest/cpputest">CppUTest framework project</a>}
     * @steps{
     * - Run the cpputest-master tests}
     * @result{
     * - Build is succeeded;
     * - Build is distributed.}
     */
    @Test(testName = "Cpp Utest")
    public void cppUtest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBCONSOLE + ProjectsCommands.TESTING_ROBIN.CPP_UTEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '"), "No agents were assigned to the build");
    }

    /**
     * @test Google test support test.<br>
     * @pre{ <a href="https://github.com/google/googletest">Google's C++ test framework project</a>}
     * @steps{
     * - Run the google-test-examples-master tests}
     * @result{
     * - Build is succeeded;
     * - Build is distributed.}
     */
    @Test(testName = "Google Test")
    public void googleTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '"), "No agents were assigned to the build");
    }

    /**
     * @test Qt test support test.<br>
     * @pre{ <a href="https://github.com/vivaladav/BitsOfBytes/tree/master/cpp-unit-testing-with-qt-test-advanced">An example project of Qt Test framework usage</a>}
     * @steps{
     * - Run the qt-test-advanced tests}
     * @result{
     * - Build is succeeded;
     * - Build is distributed.}
     */
    @Test(testName = "QT Test")
    public void qttest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBCONSOLE + ProjectsCommands.TESTING_ROBIN.QT_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '"), "No agents were assigned to the build");
    }

    /**
     * @test VS test support test.<br>
     * @pre{ <a href="https://github.com/Microsoft/vstest">The Visual Studio Test Platform Project</a>}
     * @steps{
     * - Run the vstest-master tests}
     * @result{
     * - Build is succeeded;
     * - Build is distributed.}
     */
    @Test(testName = "VS Test")
    public void vsTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBCONSOLE + ProjectsCommands.TESTING_ROBIN.VS_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '"), "No agents were assigned to the build");
    }

    /**
     * @test XUnit test support test.<br>
     * @pre{ <a href="https://github.com/xunit/xunit">xUnit.net unit testing tool project</a>}
     * @steps{
     * - Run the xunit-master tests}
     * @result{
     * - Build is succeeded;
     * - Build is distributed.}
     */
    @Test(testName = "XUnit Test")
    public void xunitTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBCONSOLE + ProjectsCommands.TESTING_ROBIN.XUNIT_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '"), "No agents were assigned to the build");
    }

    /**
     * @test SameOS flag test.<br>
     * @pre{ }
     * @steps{
     * - Run the vstest-master tests with /sameos flag}
     * @result{
     * - Build is succeeded;
     * - Build is distributed.}
     */
    @Test(testName = "SameOS Flag Test")
    public void sameOSFlagTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBCONSOLE + ProjectsCommands.TESTING_ROBIN.VS_TEST_ANY_OS + " /sameos");
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '"), "No agents were assigned to the build");
    }

    /**
     * @test SameOS error message test.<br>
     * @pre{ }
     * @steps{
     * - Run the vstest-master tests with /minwinver and /sameos flags}
     * @result{
     * - Build is failed;
     * - The error is displayed: "A /SAMEOS cannot be specified with /MINWINVER, /MAXWINVER."}
     */
    @Test(testName = "SameOS Error Test")
    public void sameOSErrorTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBCONSOLE + ProjectsCommands.TESTING_ROBIN.VS_TEST + " /sameos");
        String output = winService.runCommandGetOutput(IbLocations.IBCONSOLE + ProjectsCommands.TESTING_ROBIN.VS_TEST + " /sameos");
        Assert.assertTrue(exitCode != 0, "The test execution isn't failed.");
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "A /SAMEOS cannot be specified with /MINWINVER, /MAXWINVER"), "The error message isn't correct. Error displayed: "+output);
    }
    /**
     * @test Error message for invalid parameter /test=nunit3 test.<br>
     * @pre{ }
     * @steps{
     * - Run the nunit3 tests with invalid /test=nunit3 flag}
     * @result{
     * - Build is failed;
     * - "In order to accelerate NUnit tests, please use IBTestConsole" error message is displayed in the console.}
     */
    @Test(testName = "NUnit3 Error Message Test")
    public void NUnit3ErrorMessageTest() {
        String result = winService.runCommandGetOutput(IbLocations.IBCONSOLE+ " /command=\"" + ProjectsCommands.TESTING_ROBIN.NUNIT3_1DLL_TEST+"\" /test=nunit3" );
        Assert.assertTrue(result.contains("In order to accelerate NUnit tests, please use IBTestConsole"));
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBCONSOLE+ " /command=\"" + ProjectsCommands.TESTING_ROBIN.NUNIT3_1DLL_TEST+"\" /test=nunit3");
        Assert.assertTrue(exitCode == 3, "The test execution errorlevel is not match to 3. Errorlevel = " + exitCode);
    }
}
