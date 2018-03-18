package frameworkInfra.testbases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import frameworkInfra.utils.Parser;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider;
import ibInfra.ibService.IIBService;
import ibInfra.ibService.IbService;
import ibInfra.windowscl.WindowsService;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;

public class AgentSettingsTestBase extends TestBase {

    private static int ibVersion = 0;
    public WindowsService winService = new WindowsService();
    public IbService ibService = new IbService();

    static {
        ibVersion = IIBService.getIbVersion();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/TestOutput" + formatter.format(calendar.getTime()) + " - " + ibVersion + ".html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    //build a project and verify that both local and agent appear in the log
    @BeforeSuite
    public void beforeSuite(){
        test = extent.createTest("Before Suite");
        test.assignCategory("BEFORE SUITE");
        test.log(Status.INFO, "BEFORE SUITE started");
        log.info("BEFORE SUITE started");
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\builder", StaticDataProvider.RegistryKeys.STANDALONE_MODE, "0");
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\builder", StaticDataProvider.RegistryKeys.AVOID_LOCAL, "0");
        ibService.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.ConsoleAppProj.CONSOLE_APP_FAIL, "%s"));
        Assert.assertTrue(Parser.doesFileContainString(StaticDataProvider.Locations.OUTPUT_LOG_FILE, StaticDataProvider.LogOutput.LOCAL));
        Assert.assertTrue(Parser.doesFileContainString(StaticDataProvider.Locations.OUTPUT_LOG_FILE, StaticDataProvider.LogOutput.AGENT));
        test.log(Status.INFO, "BEFORE SUITE finished");
        log.info("BEFORE SUITE started");
    }

}
