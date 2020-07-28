package frameworkInfra.testbases.rest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import frameworkInfra.testbases.TestBase;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.SystemActions;
import io.restassured.builder.RequestSpecBuilder;
import webInfra.rest.utils.RestConstants;
import webInfra.rest.utils.RestUtils;
import ibInfra.windowscl.WindowsService;
import io.restassured.RestAssured;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static frameworkInfra.Listeners.SuiteListener.*;
import static frameworkInfra.Listeners.SuiteListener.htmlReporter;
import static frameworkInfra.Listeners.SuiteListener.test;

public class BuildGroupRestTestsBase extends TestBase {

    public WindowsService winService = new WindowsService();

    static {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/TestOutput" + formatter.format(calendar.getTime()) + ".html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @BeforeClass
    public void beforeClass(){
        RestAssured.baseURI =  String.format(RestConstants.Paths.Buildgroup.ROOT_PATH,"192.168.8.71");
      //  coordinatorApiAccess();
        RestUtils.initCertificateForBuildService();
//        Map<String, String> headers = new HashMap<>();
//        headers.put("Content-Type", "application/json");
//        headers.put("api-key", "AE2BAC97974A444993D57A");
//        headers.put("Content", "");
//        RestAssured.requestSpecification = new RequestSpecBuilder().addHeaders(headers).build();

    }


    @BeforeMethod
    public void beforeMethod(Method method){
        testName = getTestName(method);
        test = extent.createTest(testName);
        test.assignCategory("Rest Calls");
        test.log(Status.INFO, method.getName() + " test started");
        log.info(method.getName() + " test started");
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        extent.flush();
    }


    @AfterClass
    public void afterClass(){
        extent.flush();
    }

    private  void coordinatorApiAccess()
    {
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\Coordinator", StaticDataProvider.RegistryKeys.COORDINATOR_API_ACESSCoordinator, "1");
        winService.runCommandWaitForFinish(StaticDataProvider.Processes.PSEXEC + " \\\\" + StaticDataProvider.WindowsMachines.VM_AGENT_SET_Vlad + " -u vladfrenkel -p Genris1980 -i 0 " + "net stop \"" + StaticDataProvider.WindowsServices.COORD_SERVICE + "\"");
        winService.runCommandWaitForFinish(StaticDataProvider.Processes.PSEXEC + " \\\\" + StaticDataProvider.WindowsMachines.VM_AGENT_SET_Vlad + " -u vladfrenkel -p Genris1980 -i 0 " + "net start \"" + StaticDataProvider.WindowsServices.COORD_SERVICE + "\"");
        SystemActions.sleep(15);
    }



}
