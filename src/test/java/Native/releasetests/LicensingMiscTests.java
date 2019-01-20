package Native.releasetests;

import frameworkInfra.testbases.LicensingTestBase;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.SystemActions;
import frameworkInfra.utils.parsers.Parser;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LicensingMiscTests extends LicensingTestBase {

    @Test(testName = "Verify Expired Package Output")
    public void verifyExpiredPackageOutput(){
        ibService.loadIbLicense("IncrediBuild - Vlad - License Testing Environment December 2018 - expired solutions.IB_lic");
        int returncode = winService.runCommandWaitForFinish(StaticDataProvider.IbLocations.XGCOORDCONSOLE + "/AllocateAll");
        SystemActions.sleep(5);
        int exitStatus = ibService.cleanAndBuild(StaticDataProvider.IbLocations.BUILD_CONSOLE + StaticDataProvider.Locations.LICENSE_TEST_PROJECTS + StaticDataProvider.LicTestPrjBuildConsoleCommands.VS2017_CPP);
        if (returncode + exitStatus == 0) {
            Assert.assertTrue(Parser.doesFileContainString(StaticDataProvider.Locations.OUTPUT_LOG_FILE, "One of the following licenses is required in order to run this build in distributed mode"));
            //   Assert.assertTrue(Parser.doesFileContainString(StaticDataProvider.Locations.OUTPUT_LOG_FILE, "IncrediBuild for Microsoft Visual Studio C/C++"));
            Assert.assertTrue(Parser.doesFileContainString(StaticDataProvider.Locations.OUTPUT_LOG_FILE, "IncrediBuild for C#"));
            Assert.assertTrue(Parser.doesFileContainString(StaticDataProvider.Locations.OUTPUT_LOG_FILE, "The \"IncrediBuild for Microsoft Visual Studio C/C++\" license is required"));
        } else {
            Assert.assertTrue(false, "Verify Expired Package Output Test failed");
        }
    }

    @Test(testName = "Verify Allocated Packages Saved CoordService Restart")
    public void verifyAlocatedPackagesSavedCoordServiceRestart(){
        ibService.loadIbLicense("IncrediBuild - Vlad - License Testing Environment April 2018.IB_lic");
        winService.runCommandWaitForFinish(StaticDataProvider.IbLocations.XGCOORDCONSOLE + "/AllocateAll");
        int returncode = winService.runCommandWaitForFinish(StaticDataProvider.IbLocations.XGCOORDCONSOLE + "/deallocatePackages=\"VC678 Yearly\"");
        returncode += winService.runCommandWaitForFinish(StaticDataProvider.IbLocations.XGCOORDCONSOLE + "/deallocatePackages=\"C# Yearly\"");
        winService.restartService(StaticDataProvider.WindowsServices.COORD_SERVICE);
        SystemActions.sleep(5);
        int exitStatus = ibService.cleanAndBuild(StaticDataProvider.IbLocations.BUILD_CONSOLE + StaticDataProvider.Locations.LICENSE_TEST_PROJECTS + StaticDataProvider.LicTestPrjBuildConsoleCommands.VS2017_CPP);
        if (returncode + exitStatus == 0) {
            Assert.assertTrue(Parser.doesFileContainString(StaticDataProvider.Locations.OUTPUT_LOG_FILE, "One of the following licenses is required in order to run this build in distributed mode"));
            //   Assert.assertTrue(Parser.doesFileContainString(StaticDataProvider.Locations.OUTPUT_LOG_FILE, "IncrediBuild for Microsoft Visual Studio C/C++"));
            Assert.assertTrue(Parser.doesFileContainString(StaticDataProvider.Locations.OUTPUT_LOG_FILE, "IncrediBuild for C#"));
        } else {
            Assert.assertTrue(false, "Verify Alocated Packages Saved CoordService Restart Test failed");
        }
    }

    @Test(testName = "Verify Allocated Packages Saved Reload License")
    public void verifyAlocatedPackagesSavedReloadLicense(){
        ibService.loadIbLicense("IncrediBuild - Vlad - License Testing Environment April 2018.IB_lic");
        winService.runCommandWaitForFinish(StaticDataProvider.IbLocations.XGCOORDCONSOLE + "/AllocateAll");
        int returncode = winService.runCommandWaitForFinish(StaticDataProvider.IbLocations.XGCOORDCONSOLE + "/deallocateAll");
        ibService.unloadIbLicense();
        ibService.loadIbLicense("IncrediBuild - Vlad - License Testing Environment April 2018.IB_lic");
        SystemActions.sleep(5);
        int exitStatus = ibService.cleanAndBuild(StaticDataProvider.IbLocations.BUILD_CONSOLE + StaticDataProvider.Locations.LICENSE_TEST_PROJECTS + StaticDataProvider.LicTestPrjBuildConsoleCommands.VS2017_CPP);
        if (returncode + exitStatus == 0) {
            Assert.assertTrue(Parser.doesFileContainString(StaticDataProvider.Locations.OUTPUT_LOG_FILE, "One of the following licenses is required in order to run this build in distributed mode"));
            //   Assert.assertTrue(Parser.doesFileContainString(StaticDataProvider.Locations.OUTPUT_LOG_FILE, "IncrediBuild for Microsoft Visual Studio C/C++"));
            Assert.assertTrue(Parser.doesFileContainString(StaticDataProvider.Locations.OUTPUT_LOG_FILE, "IncrediBuild for C#"));
        } else {
            Assert.assertTrue(false, "Verify Alocated Packages Saved Reload License Test failed");
        }
    }

}
