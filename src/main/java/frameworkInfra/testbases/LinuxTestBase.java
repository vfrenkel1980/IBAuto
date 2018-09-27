package frameworkInfra.testbases;

import com.aventstack.extentreports.Status;
import frameworkInfra.Listeners.SuiteListener;
import frameworkInfra.utils.StaticDataProvider.*;
import ibInfra.linuxcl.LinuxDBService;
import ibInfra.linuxcl.LinuxService;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static frameworkInfra.Listeners.SuiteListener.extent;
import static frameworkInfra.Listeners.SuiteListener.test;

@Listeners(SuiteListener.class)
public class LinuxTestBase extends TestBase{

    public enum TestType{
        MultiBuild, MultiIn, Sim
    }

    protected static List rawIpList;
    protected static List rawIpList2;
    protected static String className;
    protected static String ENV = System.getProperty("env");
    protected static String VERSION = System.getProperty("version");

    protected static LinuxSimTestBase.TestType testType;

    public static List<String> ipList;
    static List<String> connectedMachinesToGrid;
    public static String buildID;
    protected static String ibVersion = "";
    public LinuxService linuxService = new LinuxService();
    LinuxDBService linuxDBService = new LinuxDBService();

    public Calendar calendar = Calendar.getInstance();
    protected SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");

    @BeforeSuite
    public void linuxSetup(ITestContext testContext) {
        OS = "linux";
        className = getClass().getName();
    }

    @AfterMethod
    public void afterMethod(ITestResult result) throws InterruptedException, IOException {

        int i=1;
        if (className.contains("Dock"))
            i=2;

        buildID = linuxService.runQueryLastBuild(LinuxCommands.BUILD_ID, LinuxCommands.BUILD_HISTORY, ipList.get(i));
        test.log(Status.INFO, buildID);
        extent.flush();
    }

/*    protected static String getIBVersion() {
        LinuxService runCommand = new LinuxService();
        ibVersion = runCommand.linuxRunSSHCommandOutputString(LinuxCommands.GET_IB_VERSION, ipList.get(0));
        return ibVersion.substring(ibVersion.indexOf("[") + 1, ibVersion.indexOf("]"));
    }*/

    public String getFirstBuild(String host){
        String lastBuild = linuxService.runQueryLastBuild(LinuxCommands.BUILD_ID, LinuxCommands.BUILD_HISTORY, host);
        lastBuild = lastBuild.replace("\n","");
        if (lastBuild.equals(""))
            lastBuild = "0";
        int firstBuild = Integer.parseInt(lastBuild) + 1;
        return String.valueOf(firstBuild);
    }
}

