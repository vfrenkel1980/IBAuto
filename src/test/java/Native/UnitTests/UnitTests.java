package Native.UnitTests;

import frameworkInfra.testbases.BatmanBCTestBase;
import frameworkInfra.testbases.TestBase;
import frameworkInfra.utils.*;

import ibInfra.ibService.IbService;

import ibInfra.windowscl.WindowsService;

import org.testng.annotations.Test;

import static com.sun.jna.platform.win32.WinReg.*;


public class UnitTests extends TestBase {
    WindowsService winService = new WindowsService();
    IbService ibService = new IbService();

    @Test(testName = "test1")
    public void testAttachment() {
        RegistryService.deleteRegKey(HKEY_CLASSES_ROOT,"WOW6432Node\\Interface","{8CA4C95D-CBE4-474A-AB9E-3F8C9313D740}");
    }
}


