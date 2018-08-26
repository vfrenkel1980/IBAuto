package frameworkInfra.testbases;

import com.aventstack.extentreports.Status;
import frameworkInfra.Listeners.EventHandler;
import frameworkInfra.Listeners.SuiteListener;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import frameworkInfra.utils.parsers.CustomJsonParser;
import frameworkInfra.utils.parsers.HtmlParser;
import frameworkInfra.utils.parsers.Parser;
import ibInfra.ibService.IbService;
import ibInfra.windowscl.WindowsService;
import io.appium.java_client.windows.WindowsDriver;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.FileFileFilter;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static frameworkInfra.Listeners.SuiteListener.extent;
import static frameworkInfra.Listeners.SuiteListener.htmlReporter;
import static frameworkInfra.Listeners.SuiteListener.test;

/**
 * This is the generic TestBase that all other testBases extend from.
 */

@Listeners(SuiteListener.class)
public class TestBase {

    public static final Logger log = Logger.getLogger(TestBase.class.getName());
    public WindowsDriver driver = null;
    public static WebDriver webDriver = null;
    public EventFiringWebDriver eventWebDriver;
    protected EventHandler handler = new EventHandler();
    public String testName = "";
    public static String OS = System.getProperty("os.name").toLowerCase();
    private WindowsService windowsService = new WindowsService();
    private IbService ibService = new IbService();


    @BeforeSuite
    public static void cleanup(){
        SystemActions.deleteFilesByPrefix(System.getProperty("user.dir") + "", "*log.out");
        String log4jConfPath = "log4j.properties";
        PropertyConfigurator.configure(log4jConfPath);
        SystemActions.deleteFilesOlderThanX("\\\\192.168.10.15\\share\\Automation\\Screenshots\\", 30);

        //copy latest extent report to backup folder
        SystemActions.copyFilesByExtension(Locations.WORKSPACE_REPORTS, Locations.QA_ROOT + "\\Logs\\Automation HTML Reports", ".html", false);
        //delete HTML report from workspace folder
        SystemActions.deleteFilesByPrefix(Locations.WORKSPACE_REPORTS, "Test");
    }

    public void log(String data){
        log.info(data);
        Reporter.log(data);
        test.log(Status.INFO, data);
    }

    @AfterClass
    public void afterClass(){
        extent.flush();
    }

    @AfterSuite
    public void afterSuiteRun(ITestContext context){
        generateCustomReport(context);
    }

    public String getTestName(Method method){
        Test testAnnotation = (Test) method.getAnnotation(Test.class);
        return testAnnotation.testName();
    }

    /**
     * Generates a custom HTML report derived from the full extent report
     * and saves the result in network automation\reports folder
     * @param context used to get the suite name
     */
    private void generateCustomReport(ITestContext context){
        String version = getVersionFromInstaller("Latest");
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm");
        String file = windowsService.getLatestFileFromDir(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/" , "TestOutput").getAbsolutePath();
        String suite = context.getCurrentXmlTest().getSuite().getName();
        String suiteId = CustomJsonParser.getValueFromKey(System.getProperty("user.dir") + "/src/main/resources/Configuration/SuiteId.json", suite);
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
        return installer.replaceAll("\\D+","");
    }
}
