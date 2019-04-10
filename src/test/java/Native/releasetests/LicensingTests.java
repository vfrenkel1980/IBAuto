package Native.releasetests;

import com.aventstack.extentreports.Status;
import frameworkInfra.testbases.LicensingTestBase;
import frameworkInfra.utils.parsers.Parser;
import frameworkInfra.utils.StaticDataProvider.*;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import static frameworkInfra.Listeners.SuiteListener.test;

public class LicensingTests extends LicensingTestBase {

    int exitStatus;

    @Test(testName = "Licence Test: VS2017 C++")
    public void licTestVS2017Cpp() {
        exitStatus = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + Locations.LICENSE_TEST_PROJECTS + LicTestPrjBuildConsoleCommands.VS2017_CPP);
        if (exitStatus == 0) {
            Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "(Agent '"));
        } else {
            Assert.assertTrue(false, "Build wasn't executed correctly");
        }
    }

    @Test(testName = "Licence Test: VS2017 CSC")
    public void licTestVS2017Csc() {
        exitStatus = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + Locations.LICENSE_TEST_PROJECTS + LicTestPrjBuildConsoleCommands.VS2017_CSC);
        if (exitStatus == 0) {
            Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "(Agent '"));
        } else {
            Assert.assertTrue(false, "Build wasn't executed correctly");
        }
    }

    @Test(testName = "Licence Test: VS2017 PS4")
    public void licTestVS2017PS4() {
        exitStatus = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + LicTestPrjBuildConsoleCommands.VS2017_PS4_ORBIS);
        if (exitStatus == 0) {
            Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "(Agent '"));
        } else {
            Assert.assertTrue(false, "Build wasn't executed correctly");
        }
    }

    @Test(testName = "Licence Test: VS2015 XBox One")
    public void licTestVS2015XBoxOne() {
        exitStatus = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + LicTestPrjBuildConsoleCommands.VS2015_XBOX_DURANGO);
        if (exitStatus == 0) {
            Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "(Agent '"));
        } else {
            Assert.assertTrue(false, "Build wasn't executed correctly");
        }
    }

    @Test(testName = "Licence Test: Make & Build Tools - MSbuild")
    public void licTestMakeAndBuild_MSbuild() {
        exitStatus = winService.runCommandWaitForFinish(IbLocations.BUILD_CONSOLE + LicTestPrjBuildConsoleCommands.MSBUILD_CPP);
        if (exitStatus == 0) {
            Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "(Agent '"));
        } else {
            Assert.assertTrue(false, "Build wasn't executed correctly");
        }
    }

    @Test(testName = "Licence Test: Make & Build Tools - Jom")
    public void licTestMakeAndBuild_Jom() {
        exitStatus = winService.runCommandWaitForFinish(IbLocations.BUILD_CONSOLE + LicTestPrjBuildConsoleCommands.JOM);
        if (exitStatus == 0) {
            Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "(Agent '"));
        } else {
            Assert.assertTrue(false, "Build wasn't executed correctly");
        }
    }

    @Test(testName = "Licence Test: Dev Tools - Interception")
    public void licTestDevTools_Interception() {
        String command = ibService.getIBInstallFolder() + LicTestPrjBuildConsoleCommands.INTERCEPTION;
        exitStatus = winService.runCommandWaitForFinish(command);
        if (exitStatus == 0) {
            Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "(Agent '"));
        } else {
            Assert.assertTrue(false, "Build wasn't executed correctly");
        }
    }

    /**
     * @bug 10384 in scenario 3: No packages allocated (valid license).  Workaround is added: the test checks that only 1 remote core was allocated for build
     */
    @Test(testName = "Licence Test: Dev Tools - Submition")
    public void licTestDevTools_Submition() {
        exitStatus = winService.runCommandWaitForFinish(ibService.getIBInstallFolder() + LicTestPrjBuildConsoleCommands.SUBMITION);
        if (exitStatus == 0 && scenario.equals("3")) {
            int cores = Parser.getHelperCores(Locations.OUTPUT_LOG_FILE).size();
            Assert.assertTrue(cores == 1, "The number of remote cores is not 1(known issue). Found " + cores);
        } else if (exitStatus == 0) {
            Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "(Agent '"));
        } else {
            Assert.assertTrue(false, "Build wasn't executed correctly");
        }
    }


    /**
     * @bug 10384 in scenario 3: No packages allocated (valid license).Workaround is added: the test checks that only 1 remote core was allocated for build
     */
    @Test(testName = "Licence Test: Dev Tools - XML")
    public void licTestDevTools_XML() {
        exitStatus = winService.runCommandWaitForFinish(ibService.getIBInstallFolder() + LicTestPrjBuildConsoleCommands.XML);
        if (exitStatus == 0 && scenario.equals("3")) {
            int cores = Parser.getHelperCores(Locations.OUTPUT_LOG_FILE).size();
            Assert.assertTrue(cores == 1, "The number of remote cores is not 1(known issue). Found " + cores);
        } else if (exitStatus == 0) {
            Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "(Agent '"));
        } else {
            Assert.assertTrue(false, "Build wasn't executed correctly");
        }
    }

    @Test(testName = "Licence Test: Unit Tests")
    public void licTestUnitTests() {
        if (scenario.equals("8")) {
            test.log(Status.SKIP, "Skipping Unit tests  with the Trial license");
            throw new SkipException("Skipped test");
        }
        exitStatus = winService.runCommandWaitForFinish(IbLocations.IBCONSOLE+ LicTestPrjBuildConsoleCommands.UNIT_TEST);
        if (exitStatus == 0 && scenario.equals("3")) {
            int cores = Parser.getHelperCores(Locations.OUTPUT_LOG_FILE).size();
            Assert.assertTrue(cores == 1, "The number of remote cores is not 1(known issue). Found " + cores);
        } else if (exitStatus == 0) {
            Assert.assertFalse(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "(Agent '"));
        } else {
            Assert.assertTrue(false, "Build wasn't executed correctly");
        }
    }
}
