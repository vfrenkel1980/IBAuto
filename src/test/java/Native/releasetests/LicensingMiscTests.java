package Native.releasetests;

import frameworkInfra.testbases.ReleaseTestBase;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.SystemActions;
import frameworkInfra.utils.parsers.Parser;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LicensingMiscTests extends ReleaseTestBase {

    @Test(testName = "Verify Expired Package Output")
    public void verifyExpiredPackageOutput(){
        ibService.loadIbLicense("IncrediBuild - Vlad - License Testing Environment April 2018 - Expired Solutions.IB_lic");
        winService.runCommandWaitForFinish(StaticDataProvider.IbLocations.XGCOORDCONSOLE + "/AllocateAll");
        SystemActions.sleep(5);
        int exitStatus = ibService.cleanAndBuild(StaticDataProvider.IbLocations.BUILD_CONSOLE + StaticDataProvider.Locations.LICENSE_TEST_PROJECTS + StaticDataProvider.LicTestPrjBuildConsoleCommands.VS2017_CPP);
        if (exitStatus == 0) {
            Assert.assertTrue(Parser.doesFileContainString(StaticDataProvider.Locations.OUTPUT_LOG_FILE, "One of the following licenses is required in order to run this build in distributed mode"));
            Assert.assertTrue(Parser.doesFileContainString(StaticDataProvider.Locations.OUTPUT_LOG_FILE, "The \"IncrediBuild for Microsoft Visual Studio C/C++\" license is required"));

        } else {
            Assert.assertTrue(false, "verify Expired Package Output test wasn't executed correctly");
        }
    }

    @Test(testName = "Verify Alocated Packages Saved")
    public void verifyAlocatedPackagesSaved(){
        int returncode = winService.runCommandWaitForFinish(StaticDataProvider.IbLocations.XGCOORDCONSOLE + "/deallocatePackage=\"IncrediBuild for Microsoft Visual Studio C/C++\"");
        winService.restartService(StaticDataProvider.WindowsServices.COORD_SERVICE);
        int exitStatus = ibService.cleanAndBuild(StaticDataProvider.IbLocations.BUILD_CONSOLE + StaticDataProvider.Locations.LICENSE_TEST_PROJECTS + StaticDataProvider.LicTestPrjBuildConsoleCommands.VS2017_CPP);
        if (returncode == 0 & exitStatus == 0) {
            Assert.assertTrue(Parser.doesFileContainString(StaticDataProvider.Locations.OUTPUT_LOG_FILE, "One of the following licenses is required in order to run this build in distributed mode"));
            Assert.assertTrue(Parser.doesFileContainString(StaticDataProvider.Locations.OUTPUT_LOG_FILE, "The \"IncrediBuild for Microsoft Visual Studio C/C++\" license is required"));

        } else {
            Assert.assertTrue(false, "verify Alocated Packages Saved wasn't executed correctly");
        }
    }

}
