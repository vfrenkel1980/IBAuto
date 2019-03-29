package Native.releasetests;

import frameworkInfra.testbases.LicensingTestBase;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import frameworkInfra.utils.parsers.Parser;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import ibInfra.ibExecs.IIBCoordMonitor;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class LicensingMiscTests extends LicensingTestBase {
    IIBCoordMonitor coordMonitor = new IIBCoordMonitor();

    @Test(testName = "Verify Expired Package Output")
    public void verifyExpiredPackageOutput(){
        ibService.loadIbLicense(IbLicenses.EXPIRED_SOLUTIONS_LIC);
        try {
            coordMonitor.waitForAgentIsUpdated("vm-lictest-hlp");
        } catch (Exception e) {
            e.printStackTrace();
        }
        int returncode = winService.runCommandWaitForFinish(IbLocations.XGCOORDCONSOLE + "/AllocateAll");
        SystemActions.sleep(5);
        int exitStatus = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + Locations.LICENSE_TEST_PROJECTS + LicTestPrjBuildConsoleCommands.VS2017_CPP);
        if (returncode + exitStatus == 0) {
            Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "One of the following licenses is required in order to run this build in distributed mode"));
            //   Assert.assertTrue(Parser.doesFileContainString(StaticDataProvider.Locations.OUTPUT_LOG_FILE, "IncrediBuild for Microsoft Visual Studio C/C++"));
            Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "IncrediBuild for C#"));
            Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "The \"IncrediBuild for Microsoft Visual Studio C/C++\" license is required"));
        } else {
            Assert.assertTrue(false, "Verify Expired Package Output Test failed");
        }
    }

    @Test(testName = "Verify Allocated Packages Saved CoordService Restart")
    public void verifyAlocatedPackagesSavedCoordServiceRestart(){
        ibService.loadIbLicense(IbLicenses.VALID_LIC);
        try {
            coordMonitor.waitForAgentIsUpdated("vm-lictest-hlp");
        } catch (Exception e) {
            e.printStackTrace();
        }
        winService.runCommandWaitForFinish(IbLocations.XGCOORDCONSOLE + "/AllocateAll");
        int returncode = winService.runCommandWaitForFinish(IbLocations.XGCOORDCONSOLE + "/deallocatePackages=\"VC678 Yearly\"");
        returncode += winService.runCommandWaitForFinish(IbLocations.XGCOORDCONSOLE + "/deallocatePackages=\"C# Yearly\"");
        winService.restartService(WindowsServices.COORD_SERVICE);
        SystemActions.sleep(5);
        int exitStatus = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + Locations.LICENSE_TEST_PROJECTS + LicTestPrjBuildConsoleCommands.VS2017_CPP);
        if (returncode + exitStatus == 0) {
            Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "One of the following licenses is required in order to run this build in distributed mode"));
            //   Assert.assertTrue(Parser.doesFileContainString(StaticDataProvider.Locations.OUTPUT_LOG_FILE, "IncrediBuild for Microsoft Visual Studio C/C++"));
            Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "IncrediBuild for C#"));
        } else {
            Assert.assertTrue(false, "Verify Alocated Packages Saved CoordService Restart Test failed");
        }
    }

    @Test(testName = "Verify Allocated Packages Saved Reload License")
    public void verifyAlocatedPackagesSavedReloadLicense(){
        ibService.loadIbLicense(IbLicenses.VALID_LIC);
        try {
            coordMonitor.waitForAgentIsUpdated("vm-lictest-hlp");
        } catch (Exception e) {
            e.printStackTrace();
        }
        winService.runCommandWaitForFinish(IbLocations.XGCOORDCONSOLE + "/AllocateAll");
        int returncode = winService.runCommandWaitForFinish(IbLocations.XGCOORDCONSOLE + "/deallocatePackages=\"VC678 Yearly\"");
        returncode += winService.runCommandWaitForFinish(IbLocations.XGCOORDCONSOLE + "/deallocatePackages=\"C# Yearly\"");
        ibService.unloadIbLicense();
        SystemActions.sleep(5);
        ibService.loadIbLicense(IbLicenses.VALID_LIC);
        SystemActions.sleep(5);
        int exitStatus = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + Locations.LICENSE_TEST_PROJECTS + LicTestPrjBuildConsoleCommands.VS2017_CPP);
        if (returncode + exitStatus == 0) {
            Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "One of the following licenses is required in order to run this build in distributed mode"));
            //   Assert.assertTrue(Parser.doesFileContainString(StaticDataProvider.Locations.OUTPUT_LOG_FILE, "IncrediBuild for Microsoft Visual Studio C/C++"));
            Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "IncrediBuild for C#"));
        } else {
            Assert.assertTrue(false, "Verify Alocated Packages Saved Reload License Test failed");
        }
    }

    @Test(testName = "Verify Allocated Packages Saved Upgrade License")
    public void verifyAlocatedPackagesSavedUpgradeLicense(){
        ibService.loadIbLicense(IbLicenses.VALID_NO_UTESTS_LIC);
        try {
            coordMonitor.waitForAgentIsUpdated("vm-lictest-hlp");
        } catch (Exception e) {
            e.printStackTrace();
        }
        int returncode = winService.runCommandWaitForFinish(IbLocations.XGCOORDCONSOLE + "/AllocateAll");
        SystemActions.sleep(5);
        ibService.loadIbLicense(IbLicenses.VALID_LIC);
        SystemActions.sleep(5);
        returncode += winService.runCommandWaitForFinish(LicTestPrjBuildConsoleCommands.UNIT_TEST);
        try {
            coordMonitor.exportCoordMonitorDataToXML(Locations.QA_ROOT, "\\coordExport.xml");
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        int cores = Parser.getHelperCores(Locations.OUTPUT_LOG_FILE).size();
        if (returncode == 0) {
            Assert.assertFalse(Parser.doesFileContainString(Locations.QA_ROOT+ "\\coordExport.xml", "IncrediBuild for NUnit"));
            Assert.assertTrue(cores == 1, "The number of remote cores is not 1(known issue). Found " + cores);
            Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Test Tools Acceleration license is missing"));
            Assert.assertTrue(Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "IncrediBuild for Unit Tests (Yearly)"));
        } else {
            Assert.assertTrue(false, "Build wasn't executed correctly");
        }
    }
}
