package Native.UnitTests;

import com.jcraft.jsch.JSchException;
import frameworkInfra.utils.*;
import frameworkInfra.utils.databases.PostgresJDBC;
import frameworkInfra.utils.databases.SQLiteJDBC;
import frameworkInfra.utils.parsers.HtmlParser;
import frameworkInfra.utils.parsers.Parser;
import ibInfra.dataObjects.postgres.CoordBuild;
import ibInfra.ibService.IbService;
import ibInfra.windowscl.WindowsService;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.FileFileFilter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileFilter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class UnitTests {

    @Test
    public void test() {
        WindowsService winService = new WindowsService();
        winService.runCommandDontWaitForTermination(StaticDataProvider.IbLocations.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC15_BATMAN.AUDACITY_X32_DEBUG, StaticDataProvider.ProjectsCommands.REBUILD));
        winService.runCommandDontWaitForTermination(StaticDataProvider.Processes.PSEXEC + " \\\\" + StaticDataProvider.WindowsMachines.SECOND_INITIATOR + " -u Administrator -p 4illumination -i 1 " +
                "\"C:\\Program Files (x86)\\IncrediBuild\\buildconsole\" C:\\QA\\Simulation\\VC11\\ACE_VC11\\ACE_vc2012.sln /rebuild /cfg=\"debug|win32\" /title=\"ACE 2012 - Debug\" " +
                "/out=\"C:\\QA\\simulation\\buildlog.txt\" /showagent /showcmd /showtime");

        winService.waitForProcessToFinishOnRemoteMachine(StaticDataProvider.WindowsMachines.SECOND_INITIATOR, "Administrator" , "4illumination", "buildsystem");
        winService.runCommandWaitForFinish("xcopy \"r:\\QA\\Simulation\\buildLog.txt\" " + StaticDataProvider.Locations.SECOND_INITIATOR_LOG_PATH );
        Assert.assertTrue(SystemActions.doesFileExist(StaticDataProvider.Locations.SECOND_INITIATOR_LOG_PATH + "buildLog.txt"));

        boolean isPresent = Parser.doesFileContainString(StaticDataProvider.Locations.SECOND_INITIATOR_LOG_PATH + "buildlog.txt", StaticDataProvider.LogOutput.AGENT);
        if (isPresent){
            SystemActions.copyFile(StaticDataProvider.Locations.SECOND_INITIATOR_LOG_PATH + "buildlog.txt", StaticDataProvider.Locations.QA_ROOT + "\\logs\\for_investigation");
        }
        SystemActions.deleteFile(StaticDataProvider.Locations.SECOND_INITIATOR_LOG_PATH + "buildlog.txt");
        winService.waitForProcessToFinish(StaticDataProvider.Processes.BUILDSYSTEM);
        Assert.assertTrue(isPresent, "No agent assigned to build");
    }

    @Test(testName = "test2")
    public void test2() throws JSchException {
        List<CoordBuild> coords= new ArrayList<CoordBuild>();
        PostgresJDBC postgresJDBC = new PostgresJDBC();
        for (int i= 0; i < 1; i++)
            coords.add(new CoordBuild());
        for (CoordBuild coord:coords) {
            postgresJDBC.runFunctionOnCoordBuildTable("localhost", "ib", "ib", "coordinatordb", "sp_insert_coord_build", coord);
        }
        System.out.println("");

    }


    @Test(testName = "test3")
    public void test3 () {
/*        String file = "C:\\Users\\Mark\\Desktop\\TestOutput20_08_2018_07_38_56 - 2433.html";
        String destFile = "\\\\192.168.10.15\\share\\Automation\\Reports\\" + "TestResultReport.html";
        SystemActions.deleteFile(destFile);
        String addVersionNumber = "exceptionsGrandChild: 0,\n" +
                "\t\t\t\tversionNumber: TEST";
        String orgScript = "<script src='https://cdn.rawgit.com/anshooarora/extentreports-java/fca20fb7653aade98810546ab96a2a4360e3e712/dist/js/extent.js' type='text/javascript'></script>";
        String desiredScript= "<script src='../static/js/jquery_bundle.js' type='text/javascript'></script>\n" +
                "\t\t\t<script src='../static/js/extent.js' type='text/javascript'></script>";
        int numberOfLines = HtmlParser.countLinesInFile(file);
        int desiredLine = Parser.getFirstLineForString(file, "<div id='test-view-charts' class='subview-full'>");
        HtmlParser.copyLinesToNewFile(file,destFile,0,23);
        HtmlParser.copyLinesToNewFile(file,destFile,desiredLine - 1,desiredLine + 33);
        HtmlParser.copyLinesToNewFile(file,destFile,numberOfLines - 37,numberOfLines);
        HtmlParser.replaceStringInFile(destFile, "parent-analysis", "parent-analysis-TEST");
        HtmlParser.replaceStringInFile(destFile, "child-analysis", "child-analysis-TEST");
        HtmlParser.replaceStringInFile(destFile, "exceptionsGrandChild: 0,", addVersionNumber);
        HtmlParser.replaceStringInFile(destFile, orgScript, desiredScript);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm");
        SystemActions.copyFile(file, "\\\\192.168.10.15\\share\\Automation\\Reports\\" + "test" + "\\" + "test" + "_" + formatter.format(calendar.getTime()) + ".html");*/
        File directory = new File(StaticDataProvider.Locations.NETWORK_REPORTS_FOLDER + "test");
        File[] files = directory.listFiles((FileFilter) FileFileFilter.FILE);
        if (files.length > 3) {
            Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_COMPARATOR);
            SystemActions.deleteFile(files[files.length - 1].getPath());
        }
    }

}