package frameworkInfra.testbases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.Status;
import frameworkInfra.Listeners.SuiteListener;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import frameworkInfra.utils.databases.PostgresJDBC;
import frameworkInfra.utils.parsers.CustomJsonParser;
import frameworkInfra.utils.parsers.HtmlParser;
import frameworkInfra.utils.parsers.Parser;
import ibInfra.ibService.IbService;
import ibInfra.vs.VSCommands;
import ibInfra.vs.VSUIService;
import ibInfra.windowscl.WindowsService;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.FileFileFilter;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.*;
import org.testng.internal.Systematiser;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import static frameworkInfra.Listeners.SuiteListener.*;

@Listeners(SuiteListener.class)
public class VSTestBase extends TestBase {

    protected static int ibVersion = 0;
    protected static String SCENARIO = System.getProperty("scenario");
    public String VSINSTALLATION = System.getProperty("vsinstallation");
    public String devenvPath = "";
    public IbService ibService = new IbService();
    public VSUIService vsuiService = new VSUIService();
    public VSCommands vsCommands = new VSCommands();
    public WindowsService winService = new WindowsService();
    public PostgresJDBC postgresJDBC = new PostgresJDBC();

    static {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/TestOutput" + formatter.format(calendar.getTime()) + "_SCENARIO_" + SCENARIO + ".html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    /**
     * This class is used in order to test different scenarios for IB and VS installations.
     * We use the parameter SCENARIO in order to distinguish between installation scenarios:
     * 1 - vs installed, install IB from installer
     * 2 - upgrade vs and install IB from vs installer
     * 3 - install old IB, install vs and upgrade IB from VS installer
     * 4 - install vs without IB
     *
     * @exception SkipException is thrown in scenario 4 in order to avoid performing
     * test class as IB is not installed in this scenario.
     *
     */

    @BeforeClass
    public void setUpEnv() {
        test = extent.createTest("Before Class");

        if (VSINSTALLATION.toLowerCase().equals("preview")){
            devenvPath = VsDevenvInstallPath.VS2017_PREVIEW;
        } else {
            devenvPath = VsDevenvInstallPath.VS2017_RELEASE;
            try {
                winService.downloadFile(URL.VS_RELEASE_URL, Locations.VS_INSTALL_DIR + "\\vs_professional.exe");
            } catch (IOException e) {
                e.getMessage();
            }
        }

        switch (SCENARIO) {

            case "1":
                test.log(Status.INFO, "Before class started\n SCENARIO 1: vs installed, install IB from installer");
                if (VSINSTALLATION.equals("15"))
                    vsCommands.upgradeVS();
                else
                    vsCommands.upgradeVSPreview();
                ibService.installIB("Latest", IbLicenses.VSTESTS_LIC);
                ibService.verifyIbServicesRunning(true, true);
                break;

            case "2":
                test.log(Status.INFO, "Before class started\n SCENARIO 2: upgrade vs and install IB from vs installer");
                if (VSINSTALLATION.equals("15"))
                    vsCommands.upgradeVSWithIB();
                else
                    vsCommands.upgradeVSPreviewWithIB();
                ibService.verifyIbServicesRunning(true, true);
                break;

            case "3":
                test.log(Status.INFO, "Before class started\n SCENARIO 3: install old IB, install vs and upgrade IB from VS installer");
                ibService.installIB("2147", IbLicenses.VSTESTS_LIC);
                if (VSINSTALLATION.equals("15"))
                    vsCommands.installVSWithIB();
                else
                    vsCommands.installVSPreviewWithIB();
                ibService.verifyIbServicesRunning(true, true);
                vsuiService.openVSInstance(VSINSTALLATION, true, SCENARIO);
                SystemActions.killProcess("devenv.exe");
                break;

            case "4":
                test.log(Status.INFO, "Before class started\n SCENARIO 4: install vs without IB");
                if (VSINSTALLATION.equals("15"))
                    vsCommands.installVSWithoutIB();
                else
                    vsCommands.installVSPreviewWithoutIB();
                test.log(Status.PASS, "");
                extent.flush();
                throw new SkipException("EXITING");

            default:
                break;
        }
        extent.flush();
    }

    @BeforeMethod
    public void beforeMethod(Method method, ITestContext context){
        test = extent.createTest(method.getName());
        test.log(Status.INFO, method.getName() + " test started");
        test.assignCategory("VC: " + VSINSTALLATION + " Scenario: " + SCENARIO);
        log.info(method.getName() + " test started");
    }

    @AfterMethod
    public void afterMethod(Method method,ITestResult result) throws IOException {
        if (result.getStatus() == 2)
            SystemActions.copyFile(Locations.OUTPUT_LOG_FILE, "\\\\ryzen\\simulation_logs\\buildLog_" + method.getName() + "_" + "SCENARIO_" + SCENARIO + ".txt");
        SystemActions.deleteFile(Locations.OUTPUT_LOG_FILE);
        vsuiService.killDriver();
        extent.flush();
    }

    @AfterClass
    public void afterClass(ITestContext context){
        test = extent.createTest("AFTER CLASS");
        test.log(Status.INFO, "AFTER CLASS started");
        test.assignCategory("VC: " + VSINSTALLATION + " Scenario: " + SCENARIO);
        log.info("AFTER CLASS started");
        ibService.unloadIbLicense();
        SystemActions.sleep(5);
        ibService.isLicenseLoaded();
        extent.flush();

        generateCustomReport();
    }




    /**
     * Generates a custom HTML report derived from the full extent report
     * and saves the result in network automation\reports folder
     */
    private void generateCustomReport(){
        String version = getVersionFromInstaller("Latest");
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm");
        String file = winService.getLatestFileFromDir(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/" , "TestOutput").getAbsolutePath();
        String suite = VSINSTALLATION + "_" + SCENARIO;
        String suiteId = CustomJsonParser.getValueFromKey(System.getProperty("user.dir") + "/src/main/resources/Configuration/VSScenariosClassId.json", suite);
        String destFile = Locations.NETWORK_REPORTS_FOLDER + "TestResultReport" + suite + ".html";
        SystemActions.copyFile(file, Locations.NETWORK_REPORTS_FOLDER + suite + "\\" + suite + "_" + formatter.format(calendar.getTime()) + "VSVERSION_" + VSINSTALLATION + "_SCENARIO" + SCENARIO + "_" + version + ".html");
        SystemActions.deleteFile(destFile);
        filterOlderReports(suite);
        String addVersionNumber = "exceptionsGrandChild: 0,\n" +
                "\t\t\t\tversionNumber: " + suiteId;
        String orgScript = "<script src='https://cdn.rawgit.com/anshooarora/extentreports-java/fca20fb7653aade98810546ab96a2a4360e3e712/dist/js/extent.js' type='text/javascript'></script>";
        String desiredScript= "<script src='../static/js/jquery_bundle.js' type='text/javascript'></script>\n" +
                "\t\t\t<script src='../static/js/extent.js' type='text/javascript'></script>";
        int numberOfLines = HtmlParser.countLinesInFile(file);
        int desiredLine = Parser.getFirstLineForString(file, "<div id='test-view-charts' class='subview-full'>");
        HtmlParser.copyLinesToNewFile(file,destFile,0,23);
        HtmlParser.copyLinesToNewFile(file,destFile,desiredLine - 1,desiredLine + 33);
        HtmlParser.copyLinesToNewFile(file,destFile,numberOfLines - 37,numberOfLines);
        HtmlParser.replaceStringInFile(destFile, "<body class='extent standard default hide-overflow '>", "<body>");
        HtmlParser.replaceStringInFile(destFile, "parent-analysis", "parent-analysis" + suiteId);
        HtmlParser.replaceStringInFile(destFile, "child-analysis", "child-analysis" + suiteId);
        HtmlParser.replaceStringInFile(destFile, "exceptionsGrandChild: 0,", addVersionNumber);
        HtmlParser.replaceStringInFile(destFile, orgScript, desiredScript);
    }

    private void filterOlderReports(String suite){
        File directory = new File(StaticDataProvider.Locations.NETWORK_REPORTS_FOLDER + suite);
        File[] files = directory.listFiles((FileFilter) FileFileFilter.FILE);
        int numberOfFiles = 0;
        if (files != null) {
            numberOfFiles = files.length;
        }
        if (numberOfFiles > 10) {
            Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_COMPARATOR);
            SystemActions.deleteFile(files[0].getPath());
        }

    }

    private String getVersionFromInstaller(String version){
        String installer = ibService.getIbConsoleInstallation(version);
        return installer.replaceAll("\\D+","");
    }

}
