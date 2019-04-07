package frameworkInfra.testbases;

import frameworkInfra.Listeners.SuiteListener;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;

@Listeners(SuiteListener.class)
public class RobinTestingTestBase extends RobinTestBase{

    @BeforeClass
    public void avoidLocal() {
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\Builder", RegistryKeys.AVOID_LOCAL, "1");
    }

    @AfterClass
    public void restartLocal() {
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\Builder", RegistryKeys.AVOID_LOCAL, "0");
    }
}
