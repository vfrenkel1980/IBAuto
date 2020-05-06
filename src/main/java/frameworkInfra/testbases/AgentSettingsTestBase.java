package frameworkInfra.testbases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import frameworkInfra.Listeners.SuiteListener;
import frameworkInfra.utils.parsers.Parser;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
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
import static frameworkInfra.Listeners.SuiteListener.*;

@Listeners(SuiteListener.class)
public class AgentSettingsTestBase extends TestBase {

    private static int ibVersion = 0;
    public WindowsService winService = new WindowsService();
    public IbService ibService = new IbService();
    protected Screen screen = new Screen();
    private IBUIService ibuiService = new IBUIService();
    protected IBUIService.Client client = ibuiService.new Client();
    protected IBUIService.Coordinator coordinator=ibuiService.new Coordinator();
    IIBCoordMonitor coordMonitor = new IIBCoordMonitor();


    static {
        ibVersion = IIBService.getIbVersion();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/TestOutput" + formatter.format(calendar.getTime()) + " - " + ibVersion + ".html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    /**
     * In the before suite we build a project and verify that
     * both "agent" and "local" appear in the log.
     */

    @BeforeSuite
    public void beforeSuite(){
        test = extent.createTest("Before Suite");
        test.assignCategory("BEFORE SUITE");
        test.log(Status.INFO, "BEFORE SUITE started");
        log.info("BEFORE SUITE started");

        ibService.updateIB(IB_VERSION);
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\builder", RegistryKeys.STANDALONE_MODE, "0");
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\builder", RegistryKeys.AVOID_LOCAL, "0");
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\builder", RegistryKeys.RESTART_ON_LOCAL, "0");
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT +"\\Builder", RegistryKeys.SAVE_BUILD_PACKET, "1");
        try {

            coordMonitor.waitForAgentIsUpdated(WindowsMachines.AGENT_SETTINGS_HLPR_NAME);
            SystemActions.sleep(30);
        } catch (RuntimeException | SAXException |IOException | ParserConfigurationException e) {
            test.log(Status.ERROR, "Helper is not updated. Error: "+e);
        }
        test.log(Status.INFO, "BEFORE SUITE finished");
        log.info("BEFORE SUITE finished");

    }

    @BeforeMethod
    public void beforeMethod(Method method){
        testName = getTestName(method);
        test = extent.createTest(testName);
        test.assignCategory("Agent Settings");
        test.log(Status.INFO, method.getName() + " test started");
        log.info(method.getName() + " test started");
    }

    @AfterMethod
    public void afterMethod(ITestResult result) throws IOException {
        SystemActions.deleteFile(Locations.OUTPUT_LOG_FILE);
        extent.flush();
    }

    @AfterClass
    public void afterClass(){
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\Builder", RegistryKeys.FLAGS, "");
        SystemActions.killProcess(Processes.BUILDSETTINGS);
        winService.restartService(WindowsServices.AGENT_SERVICE);

        extent.flush();
    }

}
