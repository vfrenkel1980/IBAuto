package frameworkInfra.testbases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import frameworkInfra.Listeners.SuiteListener;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import ibInfra.ibService.IIBService;
import ibInfra.ibService.IbService;
import ibInfra.windowscl.WindowsService;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.aventstack.extentreports.Status.INFO;
import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static frameworkInfra.Listeners.SuiteListener.extent;
import static frameworkInfra.Listeners.SuiteListener.htmlReporter;
import static frameworkInfra.Listeners.SuiteListener.test;

@Listeners(SuiteListener.class)
public class SingleUseVMTestBase extends TestBase {

    public IbService ibService = new IbService();
    public WindowsService winService = new WindowsService();
    public String testName = "";

    static {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/TestOutput" + formatter.format(calendar.getTime()) + " - Setup.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @BeforeSuite
    public void beforeSuite() {
        test = extent.createTest("Before Suite");
        test.assignCategory("BEFORE SUITE");
        test.log(Status.INFO, "BEFORE SUITE started");
        log.info("BEFORE SUITE started");
        int version = IIBService.getIbVersion();
        if (version != 0)
            ibService.uninstallIB(String.valueOf(version));
        customPackAllocationOn();
    }

    @BeforeMethod
    public void beforeMethod(Method method, ITestContext context) {
        test = extent.createTest(method.getName());
        test.assignCategory("Phoenix");
        test.log(INFO, method.getName() + " test started");
        test.assignCategory(context.getName());
        log.info("Starting test " + method.getName());
        ibService.installSingleUseIB("Latest");
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\builder", RegistryKeys.STANDALONE_MODE, "0");
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\builder", RegistryKeys.AVOID_LOCAL, "1");
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        ibService.uninstallIB("Latest");
    }

    public void customPackAllocationOn() {
        winService.runCommandWaitForFinish("REG ADD \\\\BABYLON\\HKEY_LOCAL_MACHINE\\SOFTWARE\\Wow6432Node\\Xoreax\\IncrediBuild\\Coordinator /v LicenseAllocationOption /t REG_SZ /d \"IbQaMode\" /f");
        winService.runCommandWaitForFinish("copy \\BABYLON\\c$\\CustomAllocation\\agentsList.dat \\babylon\\c$\\Program Files (x86)\\IncrediBuild");
    }
}
