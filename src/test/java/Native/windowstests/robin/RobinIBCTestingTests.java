package Native.windowstests.robin;

import frameworkInfra.testbases.RobinTestingTestBase;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.parsers.Parser;
import org.testng.Assert;
import org.testng.annotations.Test;
/**
 * @brief Unit tests execution with IbConsole
 * @details Requires Unit Tests license solution
 */
public class RobinIBCTestingTests extends RobinTestingTestBase {

    /**
     * @test Cpp utest support test.<br>
     * @pre{ }
     * @steps{
     * Run the cpputest-master tests}
     * @result{
     * - Build is succeeded;
     * - Build is distributed.}
     */
    @Test(testName = "Cpp Utest")
    public void cppUtest(){
        int exitCode=winService.runCommandWaitForFinish(IbLocations.IBCONSOLE+ProjectsCommands.TESTING_ROBIN.CPP_UTEST);
        Assert.assertTrue(exitCode==0, "The test execution failed with the exitcode "+exitCode);
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '"), "No agents were assigned to the build");
    }
    /**
     * @test Google test support test.<br>
     * @pre{ }
     * @steps{
     * Run the google-test-examples-master tests}
     * @result{
     * - Build is succeeded;
     * - Build is distributed.}
     */
    @Test(testName = "Google Test")
    public void googleTest(){
        int exitCode=winService.runCommandWaitForFinish(ProjectsCommands.TESTING_ROBIN.GTEST);
        Assert.assertTrue(exitCode==0, "The test execution failed with the exitcode "+exitCode);
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '"), "No agents were assigned to the build");
    }
    /**
     * @test Qt test support test.<br>
     * @pre{ }
     * @steps{
     * Run the qt-test-advanced tests}
     * @result{
     * - Build is succeeded;
     * - Build is distributed.}
     */
    @Test(testName = "QT Test")
    public void qttest(){
        int exitCode=winService.runCommandWaitForFinish(IbLocations.IBCONSOLE+ProjectsCommands.TESTING_ROBIN.QT_TEST);
        Assert.assertTrue(exitCode==0, "The test execution failed with the exitcode "+exitCode);
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '"), "No agents were assigned to the build");
    }
    /**
     * @test VS test support test.<br>
     * @pre{ }
     * @steps{
     * Run the vstest-master tests}
     * @result{
     * - Build is succeeded;
     * - Build is distributed.}
     */
    @Test(testName = "VS Test")
    public void vsTest(){
        int exitCode=winService.runCommandWaitForFinish(IbLocations.IBCONSOLE+ProjectsCommands.TESTING_ROBIN.VS_TEST);
        Assert.assertTrue(exitCode==0, "The test execution failed with the exitcode "+exitCode);
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '"), "No agents were assigned to the build");
    }
    /**
     * @test XUnit test support test.<br>
     * @pre{ }
     * @steps{
     * Run the xunit-master tests}
     * @result{
     * - Build is succeeded;
     * - Build is distributed.}
     */
    @Test(testName = "XUnit Test")
    public void xunitTest(){
        int exitCode=winService.runCommandWaitForFinish(IbLocations.IBCONSOLE+ProjectsCommands.TESTING_ROBIN.XUNIT_TEST);
        Assert.assertTrue(exitCode==0, "The test execution failed with the exitcode "+exitCode);
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '"), "No agents were assigned to the build");
    }

}
