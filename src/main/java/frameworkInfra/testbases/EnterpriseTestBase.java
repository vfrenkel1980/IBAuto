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
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
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
    protected Screen screen = new Screen();
    private IBUIService ibuiService = new IBUIService();
    protected IBUIService.Client client = ibuiService.new Client();

    static {
        ibVersion = IIBService.getIbVersion();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/TestOutput" + formatter.format(calendar.getTime()) + " - " + ibVersion + ".html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }


    @BeforeSuite
    public void beforeSuite() {
        test = extent.createTest("Before Suite");
        test.assignCategory("BEFORE SUITE");
        test.log(Status.INFO, "BEFORE SUITE started");
        log.info("BEFORE SUITE started");

        ibService.installIB("Latest");
        ibService.upgradeToEnt();
        ibService.loadIbLicense(StaticDataProvider.IbLicenses.VALID_LIC);
        SystemActions.sleep(10);
        winService.runCommandWaitForFinish(StaticDataProvider.IbLocations.XGCOORDCONSOLE + "/AllocateAll");
        SystemActions.sleep(10);
        IIBCoordMonitor coordMonitor = new IIBCoordMonitor();
        try {
            coordMonitor.waitForAgentIsUpdated("vm-lictest-hlp");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        test.log(Status.INFO, "BEFORE SUITE finished");
        log.info("BEFORE SUITE finished");
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        testName = getTestName(method);
        test = extent.createTest(testName);
        test.assignCategory("Enterprise");
        test.log(Status.INFO, method.getName() + " test started");
        log.info(method.getName() + " test started");
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



