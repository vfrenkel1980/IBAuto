package frameworkInfra.testbases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import frameworkInfra.Listeners.SuiteListener;
import frameworkInfra.sikuli.sikulimapping.IBHistory.History;
import frameworkInfra.sikuli.sikulimapping.IBMonitor.Monitor;
import frameworkInfra.sikuli.sikulimapping.IBSettings.IBSettings;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import ibInfra.ibService.IIBService;
import ibInfra.ibService.IbService;
import ibInfra.vs.VSUIService;
import ibInfra.windowscl.WindowsService;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static frameworkInfra.Listeners.SuiteListener.*;

@Listeners(SuiteListener.class)
public class UIValidationTestBase extends TestBase {

    private static int ibVersion = 0;
    public WindowsService winService = new WindowsService();
    public IbService ibService = new IbService();
    public Screen screen = new Screen();
    public VSUIService vsuiService = new VSUIService();
    protected String project = "";
    protected String projectLocation = "";
    protected final List<String> batchProjects = Arrays.asList("green02", "green03", "green04", "green05", "red07", "red08", "red09");
    protected Pattern vsBarPattern = new Pattern();
    protected Pattern trayIconPattern = new Pattern();
    protected Pattern ibMonBarPattern = new Pattern();
    protected Pattern historyPattern = new Pattern();
    protected Pattern progressPattern = new Pattern();


    static {
        ibVersion = IIBService.getIbVersion();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/TestOutput" + formatter.format(calendar.getTime()) + " - " + ibVersion + " - UIValidation.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @BeforeSuite
    public void beforeSuite(){
        //copy latest extent report to backup folder
        SystemActions.copyFilesByExtension(Locations.WORKSPACE_REPORTS, Locations.QA_ROOT + "\\Logs\\Automation HTML Reports", ".html", false);
        //delete HTML report from workspace folder
        SystemActions.deleteFilesByPrefix(Locations.WORKSPACE_REPORTS, "Test");
        test = extent.createTest("Before Suite");
        test.assignCategory("BEFORE SUITE");
        test.log(Status.INFO, "BEFORE SUITE started");
        log.info("BEFORE SUITE started");
        //stop agent service in order to delete history
        winService.runCommandWaitForFinish("net stop \"IncrediBuild Agent\" ");
        SystemActions.deleteFolder(new File(InitIBRoot.IB_ROOT + "\\History"));
        winService.runCommandWaitForFinish("net start \"IncrediBuild Agent\" ");
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\builder", RegistryKeys.KEEP_BUILD_STATUS_ICON, "1");
    }

    @BeforeClass
    public void beforeClass(ITestContext testContext){
        test = extent.createTest("Before Class - Running project " + testContext.getName());
        test.assignCategory("BEFORE CLASS");
        test.log(Status.INFO, "BEFORE CLASS started");
        log.info("BEFORE CLASS started - " + testContext.getName());

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
                vsBarPattern = Monitor.Bars.VSRedBar;
                historyPattern = History.Projects.Red01;
                break;
            case "red02":
                command = Processes.BUILD_CONSOLE + ProjectsCommands.UIVALIDATIONS.RED02;
                projectLocation = UIValidationsProjects.RED02;
                vsBarPattern = Monitor.Bars.VSRedBar;
                historyPattern = History.Projects.Red02;
                break;
            case "red03":
                command = Processes.BUILD_CONSOLE + ProjectsCommands.UIVALIDATIONS.RED03;
                projectLocation = UIValidationsProjects.RED03;
                vsBarPattern = Monitor.Bars.VSRedBar;
                historyPattern = History.Projects.Red03;
                break;
            case "red04":
                command = Processes.BUILD_CONSOLE + ProjectsCommands.UIVALIDATIONS.RED04;
                projectLocation = UIValidationsProjects.RED04;
                vsBarPattern = Monitor.Bars.VSRedBar;
                historyPattern = History.Projects.Red04;
                break;
            case "red05":
                command = Processes.BUILD_CONSOLE + ProjectsCommands.UIVALIDATIONS.RED05;
                projectLocation = UIValidationsProjects.RED05;
                vsBarPattern = Monitor.Bars.VSRedBar;
                historyPattern = History.Projects.Red05;
                break;
            case "red06":
                command = Processes.BUILD_CONSOLE + ProjectsCommands.UIVALIDATIONS.RED06;
                projectLocation = UIValidationsProjects.RED06;
                vsBarPattern = Monitor.Bars.VSRedBar;
                historyPattern = History.Projects.Red06;
                break;
            case "red07":
                command = ProjectsCommands.UIVALIDATIONS.RED07;
                historyPattern = History.Projects.Red07;
                break;
            case "red08":
                command = ProjectsCommands.UIVALIDATIONS.RED08;
                historyPattern = History.Projects.Red08;
                break;
            case "red09":
                command = ProjectsCommands.UIVALIDATIONS.RED09;
                historyPattern = History.Projects.Red09;
                break;
            case "yellow01":
                command = Processes.BUILD_CONSOLE + ProjectsCommands.UIVALIDATIONS.YELLOW01;
                projectLocation = UIValidationsProjects.YELLOW01;
                vsBarPattern = Monitor.Bars.VSYellowBar;
                historyPattern = History.Projects.Yellow01;
                break;
            case "green01":
                command = Processes.BUILD_CONSOLE + ProjectsCommands.UIVALIDATIONS.GREEN01;
                projectLocation = UIValidationsProjects.GREEN01;
                vsBarPattern = Monitor.Bars.VSGreenBar;
                historyPattern = History.Projects.Green01;
                break;
            case "green02":
                command = ProjectsCommands.UIVALIDATIONS.GREEN02;
                historyPattern = History.Projects.Green02;
                break;
            case "green03":
                command = ProjectsCommands.UIVALIDATIONS.GREEN03;
                historyPattern = History.Projects.Green03;
                break;
            case "green04":
                command = ProjectsCommands.UIVALIDATIONS.GREEN04;
                historyPattern = History.Projects.Green04;
                break;
            case "green05":
                command = ProjectsCommands.UIVALIDATIONS.GREEN05;
                historyPattern = History.Projects.Green05;
                break;
        }
        if (project.contains("red")){
            trayIconPattern = IBSettings.TrayIcon.Red;
            ibMonBarPattern = Monitor.Bars.IBRedBar;
            progressPattern = Monitor.Progress.Red;
        } else if (project.contains("yellow")){
            trayIconPattern = IBSettings.TrayIcon.Yellow;
            ibMonBarPattern = Monitor.Bars.IBYellowBar;
            progressPattern = Monitor.Progress.Yellow;
        } else if (project.contains("green")){
            trayIconPattern = IBSettings.TrayIcon.Green;
            ibMonBarPattern = Monitor.Bars.IBGreenBar;
            progressPattern = Monitor.Progress.Green;
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
        extent.flush();
    }


}
