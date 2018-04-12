package Native.uitests;

import frameworkInfra.sikuli.sikulimapping.IBSettings.IBSettings;
import frameworkInfra.testbases.IBSettingsTestBase;
import com.aventstack.extentreports.Status;
import com.sun.jna.platform.win32.Advapi32Util;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;
import org.testng.Assert;
import org.testng.annotations.Test;
import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static frameworkInfra.Listeners.SuiteListener.test;


public class IbSettingsUITests extends IBSettingsTestBase {


    @Test
    public void changeCoordinatorInUIAndVerifyRegistry() throws FindFailed {
        Screen screen = new Screen();
        screen.wait(IBSettings.agent.similar((float) 0.5),2).click(IBSettings.agent);
        screen.wait(IBSettings.network.similar((float) 0.8),2).click(IBSettings.network);
        test.log(Status.INFO, "Clicked Network");
        screen.wait(IBSettings.networkcoordinator.similar((float) 0.8),2).click(IBSettings.networkcoordinator);
        test.log(Status.INFO, "Clicked Coordinator");
        screen.wait(IBSettings.coordinatorNameTB.similar((float) 0.8),2).click(IBSettings.coordinatorNameTB);
        screen.type("Test");
        test.log(Status.INFO, "Entered \"Test\" as coordinator");
        screen.wait(IBSettings.OKButton.similar((float) 0.8),2).click(IBSettings.OKButton);
        test.log(Status.INFO, "Saved configuration");
        String value = Advapi32Util.registryGetStringValue
                (HKEY_LOCAL_MACHINE,
                        "SOFTWARE\\WOW6432Node\\Xoreax\\IncrediBuild\\BuildService", "CoordHost");
        Assert.assertEquals(value, "Test");
        Advapi32Util.registrySetStringValue(HKEY_LOCAL_MACHINE,
                "SOFTWARE\\WOW6432Node\\Xoreax\\IncrediBuild\\BuildService", "CoordHost","");
    }



}
