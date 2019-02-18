package Native.UnitTests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import frameworkInfra.testbases.BatmanBCTestBase;
import frameworkInfra.testbases.TestBase;
import frameworkInfra.utils.*;

import ibInfra.ibService.IbService;

import ibInfra.windowscl.WindowsService;

import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.sun.jna.platform.win32.WinReg.*;
import static frameworkInfra.Listeners.SuiteListener.extent;
import static frameworkInfra.Listeners.SuiteListener.htmlReporter;
import static frameworkInfra.Listeners.SuiteListener.test;


public class UnitTests {
    WindowsService winService = new WindowsService();
    IbService ibService = new IbService();

    @Test(testName = "test1")
    public void testAttachment() {
       //RegistryService.deleteRegKey(HKEY_CLASSES_ROOT,"WOW6432Node\\Interface\\{8CA4C95D-CBE4-474A-AB9E-3F8C9313D740}","NumMethods");
     //   RegistryService.deleteRegKey(HKEY_CLASSES_ROOT,"WOW6432Node\\Interface\\{8CA4C95D-CBE4-474A-AB9E-3F8C9313D740}","ProxyStubClsid32");
        RegistryService.deleteRegKey(HKEY_CLASSES_ROOT,"WOW6432Node\\Interface","{8CA4C95D-CBE4-474A-AB9E-3F8C9313D740}");
        //winService.runCommandWaitForFinish("reg delete HKEY_CLASSES_ROOT\\WOW6432Node\\Interface\\{8CA4C95D-CBE4-474A-AB9E-3F8C9313D740} /f");
    }
}


