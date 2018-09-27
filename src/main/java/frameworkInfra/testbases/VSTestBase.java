package frameworkInfra.testbases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.Status;
import frameworkInfra.Listeners.SuiteListener;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import frameworkInfra.utils.databases.PostgresJDBC;
import frameworkInfra.utils.parsers.CustomJsonParser;
import frameworkInfra.utils.parsers.HtmlParser;
import frameworkInfra.utils.parsers.Parser;
import ibInfra.ibService.IIBService;
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

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static frameworkInfra.Listeners.SuiteListener.*;

@Listeners(SuiteListener.class)
public class VSTestBase extends TestBase {

    protected static int ibVersion = 0;
    protected static String installedMsBuildVersion = "";
    protected static String vsVersion = "";
    protected static String ibVsInstallationName = "";
    protected static String latestIBVersion = "";
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
     * 4 - upgrade VS and install a custom IB installer from VS(changed in the registry)
     * 5 - install vs without IB
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
                ibVersion = IIBService.getIbVersion();
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
                setMsBuildRegValue();
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
                setMsBuildRegValue();
                break;

            case "4":
                test.log(Status.INFO, "Before class started\n SCENARIO 4: upgrade vs and install a custom IB installer from VS");
                if (SCENARIO.equals("preview")) {
                    latestIBVersion = getLatestInstallerVersion("preview");
                } else
                    latestIBVersion = getLatestInstallerVersion("release");
                createCustomKeyAndChangeVSIntegratedIbInstaller(latestIBVersion);

                if (VSINSTALLATION.equals("15")) {
                    vsCommands.upgradeVSWithIB();
                }
                else {
                    vsCommands.upgradeVSPreviewWithIB();
                }
                ibService.verifyIbServicesRunning(true, true);
                setMsBuildRegValue();
                break;

            case "5":
                test.log(Status.INFO, "Before class started\n SCENARIO 5: install vs without IB");
                if (VSINSTALLATION.equals("15"))
                    vsCommands.installVSWithoutIB();
                else
                    vsCommands.installVSPreviewWithoutIB();
                test.log(Status.PASS, "");
                extent.flush();
                generateCustomReport();
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

    private void setMsBuildRegValue(){
        String msBuildVer = postgresJDBC.getLastValueFromTable("192.168.10.73", "postgres", "postgres123", "release_manager", "*", "Windows_builds_ib_info",
                "ms_build_support_version", "build_number");
        RegistryService.createRegValue(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\builder", "MSBuildMaxSupportedVersion15.0", msBuildVer);
    }

    /**
     * set the IB version that is integrated in the latest VS installation and set the name of the installer into ibVsInstallationName.
     *
     * @param distribution choose between release/preview
     * @return return the latest ib build number
     */
    private String getLatestInstallerVersion(String distribution){
        ibVsInstallationName = postgresJDBC.getLastValueFromTable("192.168.10.73", "postgres", "postgres123", "release_manager", "*",
                "vs_" + distribution + "_versioning", "ib_installer_name", "vs_version");
        return postgresJDBC.getLastValueFromTable("192.168.10.73", "postgres", "postgres123", "release_manager", "*", "windows_builds_ib_info",
                "build_number", "build_number");
    }

    /**
     * Creates a new Folder key with the name of the required IB installation.
     * Changes the integrated VS version of IB to the required one
     * @param version the IB version that will be installed with VS
     */
    private void createCustomKeyAndChangeVSIntegratedIbInstaller(String version){
        String keyPath = Locations.VS_CUSTOM_IB_INSTALLER + ibVsInstallationName;
        RegistryService.createRootRegistryFolder(HKEY_LOCAL_MACHINE, keyPath);
        RegistryService.createRegValue(HKEY_LOCAL_MACHINE, keyPath, "debugger",
                Locations.NETWORK_IB_INSTALLATIONS + version + "\\" + "ibsetup" + latestIBVersion + "_console.exe %1 %2");
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
        SystemActions.copyFile(file, Locations.NETWORK_REPORTS_FOLDER + suite + "\\" + suite + "_" + formatter.format(calendar.getTime()) + "_" + version + ".html");
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
        installer = installer.substring(installer.lastIndexOf("\\"));
        return installer.replaceAll("\\D+","");
    }

}
