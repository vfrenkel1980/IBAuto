package Native.windowstests.robin;

import frameworkInfra.testbases.RobinTestBase;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.parsers.Parser;
import org.testng.Assert;
import org.testng.annotations.Test;
/**
 * @brief Unit tests execution with IbTestConsole
 * @details Requires Unit Tests license solution
 */
public class RobinIBTCTestingTests extends RobinTestBase {
    /**
     * @test NUnit3 Assembly Level support test.<br>
     * @pre{ }
     * @steps{ Run the nunit-console-master tests}
     * @result{
     * - Build is succeeded;}
     */
    @Test(testName = "NUnit3-console Assembly Level Test")
    public void nunit3ConsoleAssemblyLevelTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_CONSOLE_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test NUnit3 Test Level support test.<br>
     * @pre{ }
     * @steps{ Run the nunit-console-master tests with /testlevel=10 flag}
     * @result{
     * - Build is succeeded;}
     */
    @Test(testName = "NUnit3-console Test Level Test")
    public void nunit3ConsoleAssemblyTestLevelTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_CONSOLE_TESTLEVEL_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }

}
