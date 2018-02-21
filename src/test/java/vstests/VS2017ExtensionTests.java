package vstests;

import frameworkInfra.testbases.VSTestBase;
import frameworkInfra.utils.StaticDataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.io.IOException;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;

public class VS2017PreviewExtensionTests extends VSTestBase {

    @Test(testName = "Check version of installed IB extension")
    public void checkInstalledExtension(){
        String extensionVersion = runIb.getIbVsExtensionVersion();
        String expectedExtensionVersion = runIb.getExpectedIbVsExtensionVersion();
        Assert.assertTrue(extensionVersion.equals(expectedExtensionVersion), "Incredibuild Extension Version: " + expectedExtensionVersion + "\n" + "Installed Extension Version: " + extensionVersion);
    }

    @Test(testName = "IncrediBuild exectution from VS2017 menu bar")
    public void executeVSBuild(){
        vsService.openVS2017instance();
        vsService.openProject(StaticDataProvider.TestProjects.CONSOLE_APPLICATION_01);
        vsService.executeBuildFromMenu(StaticDataProvider.VsActions.REBUILD_SOLUTION);
        String result;
        try {
            result = runIb.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
