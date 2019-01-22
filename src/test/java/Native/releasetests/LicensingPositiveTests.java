package Native.releasetests;

import frameworkInfra.testbases.LicensingPositiveTestBase;
import frameworkInfra.utils.SystemActions;
import frameworkInfra.utils.parsers.Parser;
import ibInfra.ibExecs.IIBCoordMonitor;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;
import frameworkInfra.utils.StaticDataProvider.*;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class LicensingPositiveTests extends LicensingPositiveTestBase {

    int exitStatus;

    @Test(testName = "Licence Positive Test: VS2017 C++")
    public void licTestVS2017Cpp() {
        exitStatus = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + Locations.LICENSE_TEST_PROJECTS + LicTestPrjBuildConsoleCommands.VS2017_CPP);
        if (exitStatus == 0) {
            Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "(Agent '"));
        } else {
            Assert.assertTrue(false, "Build wasn't executed correctly");
        }
    }

    @Test(testName = "Licence Positive Test: VS2017 CSC")
    public void licTestVS2017Csc() {
        IIBCoordMonitor coordMonitor = new IIBCoordMonitor();
        int counter = 0;
        winService.runCommandDontWaitForTermination(IbLocations.BUILD_CONSOLE + Locations.LICENSE_TEST_PROJECTS + LicTestPrjBuildConsoleCommands.VS2017_CSC);
        SystemActions.sleep(2);
        while (winService.isProcessRunning(Processes.BUILDSYSTEM)){
            try {
                if (coordMonitor.checkIfAgentIsHelper("vm-lictest", "vm-lictest-hlp")){
                    counter++;
                }
            } catch (IOException | SAXException | ParserConfigurationException e) {
                e.printStackTrace();
            }
        }
        Assert.assertTrue(counter > 0);
    }

    @Test(testName = "Licence Test: VS2017 PS4")
    public void licTestVS2017PS4() {
        IIBCoordMonitor coordMonitor = new IIBCoordMonitor();
        int counter = 0;
        winService.runCommandDontWaitForTermination(IbLocations.BUILD_CONSOLE + LicTestPrjBuildConsoleCommands.VS2017_PS4_ORBIS);
        SystemActions.sleep(2);
        while (winService.isProcessRunning(Processes.BUILDSYSTEM)){
            try {
                if (coordMonitor.checkIfAgentIsHelper("vm-lictest", "vm-lictest-hlp")){
                    counter++;
                }
            } catch (IOException | SAXException | ParserConfigurationException e) {
                e.printStackTrace();
            }
        }
        Assert.assertTrue(counter > 0);
    }

    @Test(testName = "Licence Test: VS2015 XBox One")
    public void licTestVS2015XBoxOne() {
        exitStatus = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + LicTestPrjBuildConsoleCommands.VS2015_XBOX_DURANGO);
        if (exitStatus == 0) {
            Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "(Agent '"));
        } else {
            Assert.assertTrue(false, "Build wasn't executed correctly");
        }
    }

    @Test(testName = "Licence Test: Make & Build Tools - MSbuild")
    public void licTestMakeAndBuild_MSbuild() {
        exitStatus = winService.runCommandWaitForFinish(IbLocations.BUILD_CONSOLE + LicTestPrjBuildConsoleCommands.MSBUILD_CPP);
        if (exitStatus == 0) {
            Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "(Agent '"));
        } else {
            Assert.assertTrue(false, "Build wasn't executed correctly");
        }
    }

    @Test(testName = "Licence Test: Make & Build Tools - Jom")
    public void licTestMakeAndBuild_Jom() {
        exitStatus = winService.runCommandWaitForFinish(IbLocations.BUILD_CONSOLE + LicTestPrjBuildConsoleCommands.JOM);
        if (exitStatus == 0) {
            Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "(Agent '"));
        } else {
            Assert.assertTrue(false, "Build wasn't executed correctly");
        }
    }

    @Test(testName = "Licence Test: Dev Tools - Interception")
    public void licTestDevTools_Interception() {
        String command = ibService.getIBInstallFolder() + LicTestPrjBuildConsoleCommands.INTERCEPTION;
        exitStatus = winService.runCommandWaitForFinish(command);
        if (exitStatus == 0) {
            Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "(Agent '"));
        } else {
            Assert.assertTrue(false, "Build wasn't executed correctly");
        }
    }

    @Test(testName = "Licence Test: Dev Tools - Submition")
    public void licTestDevTools_Submition() {
        exitStatus = winService.runCommandWaitForFinish(ibService.getIBInstallFolder() + LicTestPrjBuildConsoleCommands.SUBMITION);
        if (exitStatus == 0) {
            Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "(Agent '"));
        } else {
            Assert.assertTrue(false, "Build wasn't executed correctly");
        }
    }

    @Test(testName = "Licence Test: Dev Tools - XML")
    public void licTestDevTools_XML() {
        exitStatus = winService.runCommandWaitForFinish(ibService.getIBInstallFolder() + LicTestPrjBuildConsoleCommands.XML);
        if (exitStatus == 0) {
            Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "(Agent '"));
        } else {
            Assert.assertTrue(false, "Build wasn't executed correctly");
        }
    }

    @Test(testName = "Licence Test: Unit Tests")
    public void licTestUnitTests() {
        exitStatus = winService.runCommandWaitForFinish(LicTestPrjBuildConsoleCommands.UNIT_TEST);
        if (exitStatus == 0) {
            Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "(Agent '"));
        } else {
            Assert.assertTrue(false, "Build wasn't executed correctly");
        }
    }
}
