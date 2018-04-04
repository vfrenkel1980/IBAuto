package releasetests;

import frameworkInfra.testbases.LicensingTestBase;
import frameworkInfra.utils.Parser;
import frameworkInfra.utils.StaticDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LicensingTests extends LicensingTestBase {

    @Test(testName = "Licence Test: VS2017 C++")
    public void licTestVS2017Cpp() {
        ibService.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + StaticDataProvider.Locations.LICENSE_TEST_PROJECTS + StaticDataProvider.LicTestPrjBuildConsoleCommands.VS2017_CPP);
        Assert.assertFalse(Parser.doesFileContainString(StaticDataProvider.Locations.OUTPUT_LOG_FILE, "(Agent '"));
    }

    @Test(testName = "Licence Test: VS2017 CSC")
    public void licTestVS2017Csc() {
        ibService.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + StaticDataProvider.Locations.LICENSE_TEST_PROJECTS + StaticDataProvider.LicTestPrjBuildConsoleCommands.VS2017_CSC);
        Assert.assertFalse(Parser.doesFileContainString(StaticDataProvider.Locations.OUTPUT_LOG_FILE, "(Agent '"));
    }

    @Test(testName = "Licence Test: VS2017 PS4")
    public void licTestVS2017PS4() {
        ibService.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + StaticDataProvider.LicTestPrjBuildConsoleCommands.VS2017_PS4_ORBIS);
        Assert.assertFalse(Parser.doesFileContainString(StaticDataProvider.Locations.OUTPUT_LOG_FILE, "(Agent '"));
    }

    @Test(testName = "Licence Test: VS2015 XBox One")
    public void licTestVS2015XBoxOne() {
        ibService.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + StaticDataProvider.LicTestPrjBuildConsoleCommands.VS2015_XBOX_DURANGO);
        Assert.assertFalse(Parser.doesFileContainString(StaticDataProvider.Locations.OUTPUT_LOG_FILE, "(Agent '"));
    }

    @Test(testName = "Licence Test: Make & Build Tools - MSbuild")
    public void licTestMakeAndBuild_MSbuild() {
        winService.runCommandWaitForFinish(StaticDataProvider.Processes.BUILD_CONSOLE + StaticDataProvider.LicTestPrjBuildConsoleCommands.MSBUILD_CPP);
        Assert.assertFalse(Parser.doesFileContainString(StaticDataProvider.Locations.OUTPUT_LOG_FILE, "(Agent '"));
    }

    @Test(testName = "Licence Test: Make & Build Tools - Jom")
    public void licTestMakeAndBuild_Jom() {
        winService.runCommandWaitForFinish(StaticDataProvider.Processes.BUILD_CONSOLE + StaticDataProvider.LicTestPrjBuildConsoleCommands.JOM);
        Assert.assertFalse(Parser.doesFileContainString(StaticDataProvider.Locations.OUTPUT_LOG_FILE, "(Agent '"));
    }

    @Test(testName = "Licence Test: Dev Tools - Interception")
    public void licTestDevTools_Interception() {
        winService.runCommandWaitForFinish(StaticDataProvider.LicTestPrjBuildConsoleCommands.INTERCEPTION);
        Assert.assertFalse(Parser.doesFileContainString(StaticDataProvider.Locations.OUTPUT_LOG_FILE, "(Agent '"));
    }

    @Test(testName = "Licence Test: Dev Tools - Submition")
    public void licTestDevTools_Submition() {
        winService.runCommandWaitForFinish(StaticDataProvider.LicTestPrjBuildConsoleCommands.SUBMITION);
        Assert.assertFalse(Parser.doesFileContainString(StaticDataProvider.Locations.OUTPUT_LOG_FILE, "(Agent '"));
    }

    @Test(testName = "Licence Test: Dev Tools - XML")
    public void licTestDevTools_XML() {
        winService.runCommandWaitForFinish(StaticDataProvider.LicTestPrjBuildConsoleCommands.XML);
        Assert.assertFalse(Parser.doesFileContainString(StaticDataProvider.Locations.OUTPUT_LOG_FILE, "(Agent '"));
    }
}
