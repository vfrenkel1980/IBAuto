package Native.windowstests.robin;

import frameworkInfra.testbases.RobinTestBase;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.parsers.Parser;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RobinIBCTestingTests extends RobinTestBase {

    @Test(testName = "Cpp Utest")
    public void cppUtest(){
        int exitCode=winService.runCommandWaitForFinish(IbLocations.IBCONSOLE+ProjectsCommands.TESTING_ROBIN.CPP_UTEST);
        Assert.assertTrue(exitCode==0, "The test execution failed with the exitcode "+exitCode);
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '"), "No agents were assigned to the build");
    }

    @Test(testName = "Google Test")
    public void googleTest(){
        int exitCode=winService.runCommandWaitForFinish(ProjectsCommands.TESTING_ROBIN.GTEST);
        Assert.assertTrue(exitCode==0, "The test execution failed with the exitcode "+exitCode);
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '"), "No agents were assigned to the build");
    }

    @Test(testName = "QT Test")
    public void qttest(){
        int exitCode=winService.runCommandWaitForFinish(IbLocations.IBCONSOLE+ProjectsCommands.TESTING_ROBIN.QT_TEST);
        Assert.assertTrue(exitCode==0, "The test execution failed with the exitcode "+exitCode);
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '"), "No agents were assigned to the build");
    }

    @Test(testName = "VS Test")
    public void vsTest(){
        int exitCode=winService.runCommandWaitForFinish(IbLocations.IBCONSOLE+ProjectsCommands.TESTING_ROBIN.VS_TEST);
        Assert.assertTrue(exitCode==0, "The test execution failed with the exitcode "+exitCode);
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '"), "No agents were assigned to the build");
    }

    @Test(testName = "XUnit Test")
    public void xunitTest(){
        int exitCode=winService.runCommandWaitForFinish(IbLocations.IBCONSOLE+ProjectsCommands.TESTING_ROBIN.XUNIT_TEST);
        Assert.assertTrue(exitCode==0, "The test execution failed with the exitcode "+exitCode);
        Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '"), "No agents were assigned to the build");
    }

}
