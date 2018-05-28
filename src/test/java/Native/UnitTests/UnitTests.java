package Native.UnitTests;

import com.aventstack.extentreports.Status;
import frameworkInfra.utils.Parser;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.SystemActions;
import ibInfra.ibService.IbService;
import ibInfra.vs.VSUIService;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static frameworkInfra.Listeners.SuiteListener.test;

public class UnitTests {

    @Test
    public void test() {
        VSUIService vsService = new VSUIService();
        IbService ibService = new IbService();
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT +"\\Builder", StaticDataProvider.RegistryKeys.SAVE_BUILD_PACKET, "1");
        vsService.openVSInstance("15", false);
        vsService.createNewProject("custom");
        vsService.performIbActionFromPrjExplorer(StaticDataProvider.VsActions.REBUILD_SOLUTION, "solution", "custom");
        String result;
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"));
        } catch (IOException e) {
            e.getMessage();
        }
    }

    @Test
    public void test2() {
        List<String> news = SystemActions.getAllFilesInDirectory("C:\\Users\\Mark\\Downloads");
        for (String aNew : news) System.out.println(aNew);
    }

    @Test(testName = "Verify Errors In Logs")
    public void verifyErrorsInLogs() {
        int isFailed = 0;
        List<String> files = SystemActions.getAllFilesInDirectory("C:\\Program Files (x86)\\Incredibuild\\Logs");
        for (String file : files) {
            for (String aERROR_LIST : StaticDataProvider.LogOutput.ERROR_LIST) {
                if(Parser.doesFileContainString("C:\\Program Files (x86)\\Incredibuild\\Logs\\" + file, aERROR_LIST))
                    isFailed++;
                //test.log(Status.INFO, aERROR_LIST + " Appears in " + file);
                System.out.println(aERROR_LIST + " Appears in " + file);
            }
        }
        Assert.assertFalse(isFailed > 0);
    }
}
