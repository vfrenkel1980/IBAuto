package releasetests;

import frameworkInfra.testbases.LicensingTestBase;
import frameworkInfra.utils.Parser;
import frameworkInfra.utils.StaticDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LicensingTests extends LicensingTestBase {

    int exitStatus;

    @Test(testName = "Licence Test: VS2017 C++")
    public void licTestVS2017Cpp() {
        exitStatus = ibService.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + StaticDataProvider.Locations.LICENSE_TEST_PROJECTS + StaticDataProvider.LicTestPrjBuildConsoleCommands.VS2017_CPP);
        if (exitStatus == 0) {
            Assert.assertFalse(Parser.doesFileContainString(StaticDataProvider.Locations.OUTPUT_LOG_FILE, "(Agent '"));
        } else {
            Assert.assertTrue(false, "Build wasn't executed correctly");
        }
    }

    @Test(testName = "Licence Test: VS2017 CSC")
    public void licTestVS2017Csc() {
        exitStatus = ibService.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + StaticDataProvider.Locations.LICENSE_TEST_PROJECTS + StaticDataProvider.LicTestPrjBuildConsoleCommands.VS2017_CSC);
        if (exitStatus == 0) {
            Assert.assertFalse(Parser.doesFileContainString(StaticDataProvider.Locations.OUTPUT_LOG_FILE, "(Agent '"));
        } else {
            Assert.assertTrue(false, "Build wasn't executed correctly");
        }
    }

    @Test(testName = "Licence Test: VS2017 PS4")
    public void licTestVS2017PS4() {
        exitStatus = ibService.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + StaticDataProvider.LicTestPrjBuildConsoleCommands.VS2017_PS4_ORBIS);
        if (exitStatus == 0) {
            Assert.assertFalse(Parser.doesFileContainString(StaticDataProvider.Locations.OUTPUT_LOG_FILE, "(Agent '"));
        } else {
            Assert.assertTrue(false, "Build wasn't executed correctly");
        }
    }

    @Test(testName = "Licence Test: VS2015 XBox One")
    public void licTestVS2015XBoxOne() {
        exitStatus = ibService.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + StaticDataProvider.LicTestPrjBuildConsoleCommands.VS2015_XBOX_DURANGO);
        if (exitStatus == 0) {
            Assert.assertFalse(Parser.doesFileContainString(StaticDataProvider.Locations.OUTPUT_LOG_FILE, "(Agent '"));
        } else {
            Assert.assertTrue(false, "Build wasn't executed correctly");
        }
    }

    @Test(testName = "Licence Test: Make & Build Tools - MSbuild")
    public void licTestMakeAndBuild_MSbuild() {
        exitStatus = winService.runCommandWaitForFinish(StaticDataProvider.Processes.BUILD_CONSOLE + StaticDataProvider.LicTestPrjBuildConsoleCommands.MSBUILD_CPP);
        if (exitStatus == 0) {
            Assert.assertFalse(Parser.doesFileContainString(StaticDataProvider.Locations.OUTPUT_LOG_FILE, "(Agent '"));
        } else {
            Assert.assertTrue(false, "Build wasn't executed correctly");
        }
    }

    @Test(testName = "Licence Test: Make & Build Tools - Jom")
    public void licTestMakeAndBuild_Jom() {
        exitStatus = winService.runCommandWaitForFinish(StaticDataProvider.Processes.BUILD_CONSOLE + StaticDataProvider.LicTestPrjBuildConsoleCommands.JOM);
        if (exitStatus == 0) {
            Assert.assertFalse(Parser.doesFileContainString(StaticDataProvider.Locations.OUTPUT_LOG_FILE, "(Agent '"));
        } else {
            Assert.assertTrue(false, "Build wasn't executed correctly");
        }
    }

    @Test(testName = "Licence Test: Dev Tools - Interception")
    public void licTestDevTools_Interception() {
        exitStatus = winService.runCommandWaitForFinish(StaticDataProvider.LicTestPrjBuildConsoleCommands.INTERCEPTION);
        if (exitStatus == 0) {
            Assert.assertFalse(Parser.doesFileContainString(StaticDataProvider.Locations.OUTPUT_LOG_FILE, "(Agent '"));
        } else {
            Assert.assertTrue(false, "Build wasn't executed correctly");
        }
    }

    @Test(testName = "Licence Test: Dev Tools - Submition")
    public void licTestDevTools_Submition() {
        exitStatus = winService.runCommandWaitForFinish(StaticDataProvider.LicTestPrjBuildConsoleCommands.SUBMITION);
        if (exitStatus == 0) {
            Assert.assertFalse(Parser.doesFileContainString(StaticDataProvider.Locations.OUTPUT_LOG_FILE, "(Agent '"));
        } else {
            Assert.assertTrue(false, "Build wasn't executed correctly");
        }
    }

    @Test(testName = "Licence Test: Dev Tools - XML")
    public void licTestDevTools_XML() {
        exitStatus = winService.runCommandWaitForFinish(StaticDataProvider.LicTestPrjBuildConsoleCommands.XML);
        if (exitStatus == 0) {
            Assert.assertFalse(Parser.doesFileContainString(StaticDataProvider.Locations.OUTPUT_LOG_FILE, "(Agent '"));
        } else {
            Assert.assertTrue(false, "Build wasn't executed correctly");
        }
    }
}
