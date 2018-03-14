package UnitTests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import frameworkInfra.testbases.UnitTestBase;
import frameworkInfra.testbases.VSTestBase;
import frameworkInfra.utils.Parser;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider;
import ibInfra.vsui.VSUIService;
import ibInfra.windowscl.WindowsService;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static frameworkInfra.testbases.TestBase.extent;

public class UnitTests {

    @Test
    public void test() {

        VSUIService vsService = new VSUIService();
        vsService.openVS2017instance("15");
        vsService.openProject(StaticDataProvider.TestProjects.CONSOLE_APPLICATION_01);
        vsService.performIbActionFromPrjExplorer(StaticDataProvider.VsActions.CLEAN_SOLUTION,"project", "ConsoleApplication1");
    }
}
