package frameworkInfra.testbases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import frameworkInfra.sikuli.sikulimapping.IBMonitor.Monitor;
import frameworkInfra.sikuli.sikulimapping.IBSettings.IBSettings;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import ibInfra.ibService.IIBService;
import ibInfra.ibService.IbService;
import ibInfra.vsui.VSUIService;
import ibInfra.windowscl.WindowsService;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;

public class UIValidationTestBase extends TestBase {

    private static int ibVersion = 0;
    public WindowsService winService = new WindowsService();
    public IbService ibService = new IbService();
    public Screen screen = new Screen();
    public VSUIService vsService = new VSUIService();
    protected String project = "";
    protected String projectLocation = "";
    protected final List<String> batchProjects = Arrays.asList("green02", "green03", "green04", "green05", "red07", "red08", "red09");
    protected Pattern pattern = new Pattern();


    static {
        ibVersion = IIBService.getIbVersion();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/TestOutput" + formatter.format(calendar.getTime()) + " - " + ibVersion + ".html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @BeforeSuite
    public void beforeSuite(){
        test = extent.createTest("Before Suite");
        test.assignCategory("BEFORE SUITE");
        test.log(Status.INFO, "BEFORE SUITE started");
        log.info("BEFORE SUITE started");
        //stop agent service in order to delete history
        winService.runCommandWaitForFinish("net stop \"IncrediBuild Agent\" ");
        SystemActions.deleteFilesByPrefix(Locations.IB_ROOT + "\\History", "*");
        winService.runCommandWaitForFinish("net start \"IncrediBuild Agent\" ");
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\builder", RegistryKeys.KEEP_BUILD_STATUS_ICON, "1");
    }

    @BeforeClass
    public void beforeClass(ITestContext testContext){
        test = extent.createTest("Before Class - Running project " + testContext.getName());
        test.assignCategory("BEFORE CLASS");
        test.log(Status.INFO, "BEFORE CLASS started");
        log.info("BEFORE CLASS started - Change Logging Level to" + testContext.getName());

        project = testContext.getName();
        String command = "";

        if (project.equals("red05") || project.equals("red06"))
            RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\builder", RegistryKeys.MSBUILD, "1");
        else
            RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\builder", RegistryKeys.MSBUILD, "0");

        if (project.equals("red04"))
            RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\builder", RegistryKeys.CUSTOM_STEP_VS10_SUPPORT, "0");
        else
            RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\builder", RegistryKeys.CUSTOM_STEP_VS10_SUPPORT, "1");

        switch (project){
            case "red01":
                command = Processes.BUILD_CONSOLE + ProjectsCommands.UIVALIDATIONS.RED01;
                projectLocation = UIValidationsProjects.RED01;
                pattern = Monitor.Bars.VSRedBar;
                break;
            case "red02":
                command = Processes.BUILD_CONSOLE + ProjectsCommands.UIVALIDATIONS.RED02;
                projectLocation = UIValidationsProjects.RED02;
                pattern = Monitor.Bars.VSRedBar;
                break;
            case "red03":
                command = Processes.BUILD_CONSOLE + ProjectsCommands.UIVALIDATIONS.RED03;
                projectLocation = UIValidationsProjects.RED03;
                pattern = Monitor.Bars.VSRedBar;
                break;
            case "red04":
                command = Processes.BUILD_CONSOLE + ProjectsCommands.UIVALIDATIONS.RED04;
                projectLocation = UIValidationsProjects.RED04;
                pattern = Monitor.Bars.VSRedBar;
                break;
            case "red05":
                command = Processes.BUILD_CONSOLE + ProjectsCommands.UIVALIDATIONS.RED05;
                projectLocation = UIValidationsProjects.RED05;
                pattern = Monitor.Bars.VSRedBar;
                break;
            case "red06":
                command = Processes.BUILD_CONSOLE + ProjectsCommands.UIVALIDATIONS.RED06;
                projectLocation = UIValidationsProjects.RED06;
                pattern = Monitor.Bars.VSRedBar;
                break;
            case "red07":
                command = ProjectsCommands.UIVALIDATIONS.RED07;
                break;
            case "red08":
                command = ProjectsCommands.UIVALIDATIONS.RED08;
                break;
            case "red09":
                command = ProjectsCommands.UIVALIDATIONS.RED09;
                break;
            case "yellow01":
                command = Processes.BUILD_CONSOLE + ProjectsCommands.UIVALIDATIONS.YELLOW01;
                projectLocation = UIValidationsProjects.YELLOW01;
                pattern = Monitor.Bars.VSYellowBar;
                break;
            case "green01":
                command = Processes.BUILD_CONSOLE + ProjectsCommands.UIVALIDATIONS.GREEN01;
                projectLocation = UIValidationsProjects.GREEN01;
                pattern = Monitor.Bars.VSGreenBar;
                break;
            case "green02":
                command = ProjectsCommands.UIVALIDATIONS.GREEN02;
                break;
            case "green03":
                command = ProjectsCommands.UIVALIDATIONS.GREEN03;
                break;
            case "green04":
                command = ProjectsCommands.UIVALIDATIONS.GREEN04;
                break;
            case "green0":
                command = ProjectsCommands.UIVALIDATIONS.GREEN05;
                break;
        }
        winService.runCommandWaitForFinish(command);
    }

    @BeforeMethod
    public void beforeMethod(Method method, ITestContext context){
        test = extent.createTest(method.getName());
        test.log(Status.INFO, method.getName() + " test started");
        test.assignCategory(context.getName());
        log.info(method.getName() + " test started");
    }

    @AfterMethod
    public void afterMethod(ITestResult result) throws IOException {
        SystemActions.deleteFile(Locations.OUTPUT_LOG_FILE);
        if (driver != null) {
            driver.quit();
        }
        driver = null;
        getResult(result);
    }


}
