package UnitTests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import frameworkInfra.testbases.UnitTestBase;
import frameworkInfra.utils.Parser;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider;
import ibInfra.windowscl.WindowsService;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static frameworkInfra.testbases.TestBase.extent;

public class UnitTests {

    @Test
    public void test(){

        WindowsService windowsService = new WindowsService();
        try {
            windowsService.downloadFile("https://download.visualstudio.microsoft.com/download/pr/11796490/da370bf146a3a1e91ec0ace29e623fdb/vs_Professional.exe", "C:\\Users\\Mark\\Downloads\\vs_professional.exe");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
