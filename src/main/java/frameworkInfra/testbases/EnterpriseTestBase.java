package frameworkInfra.testbases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.SystemActions;
import frameworkInfra.utils.parsers.Parser;
import ibInfra.ibExecs.IIBCoordMonitor;
import ibInfra.ibService.IIBService;
import ibInfra.ibService.IbService;
import ibInfra.ibUIService.IBUIService;
import ibInfra.windowscl.WindowsService;
import org.sikuli.script.Screen;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static frameworkInfra.Listeners.SuiteListener.extent;
import static frameworkInfra.Listeners.SuiteListener.htmlReporter;
import static frameworkInfra.Listeners.SuiteListener.test;

public class EnterpriseTestBase extends TestBase {

    private static int ibVersion = 0;
    public WindowsService winService = new WindowsService();
    public IbService ibService = new IbService();
    public IIBCoordMonitor coordMonitor = new IIBCoordMonitor();

    static {
        ibVersion = IIBService.getIbVersion();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/TestOutput" + formatter.format(calendar.getTime()) + " - " + ibVersion + "- ENTERPRISE.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @AfterMethod
    public void afterMethod(ITestResult result) throws IOException {
        SystemActions.deleteFile(StaticDataProvider.Locations.OUTPUT_LOG_FILE);
        extent.flush();
    }

    @AfterClass
    public void afterClass() {
        ibService.uninstallIB("Latest");
        extent.flush();
    }

}



