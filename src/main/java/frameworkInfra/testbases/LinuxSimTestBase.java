package frameworkInfra.testbases;

import com.aventstack.extentreports.Status;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.XmlParser;
import ibInfra.linuxcl.LinuxCL;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

public class LinuxSimTestBase extends TestBase {

    public LinuxCL runCommand = new LinuxCL();
    private List rawIpList;
    public List<String> ipList;
    String buildID;

    @BeforeSuite
    public void envSetUp(){
        test = extent.createTest("Before Suite");
        test.assignCategory("BEFORE SUITE");
        test.log(Status.INFO, "BEFORE SUITE started");

        rawIpList = XmlParser.getIpList();
        ipList = runCommand.breakDownIPList(rawIpList);
        //runCommand.deleteLogsFolder(ipList);

        if(!runCommand.isIBServiceUp(StaticDataProvider.LinuxMachines.VM_SIM_1A, "ib_server")) {
           test.log(Status.ERROR, "IB service is down... Exiting!");
            System.exit(1);
        }
    }

    @BeforeMethod
    public void beforeMethod(Method method){
        testName = getTestName(method);
        test = extent.createTest(testName);
        test.assignCategory("Linux Simulation");
        test.log(Status.INFO, method.getName() + " test started");
    }

    public void getResult(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, result.getName() + " test passed");
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.log(Status.SKIP, result.getName() + " test skipped");
        } else if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.ERROR, result.getName() + " test has failed - Build ID = " + buildID + result.getThrowable());
            String path = captureScreenshot(result.getName());
            test.fail("Screenshot " + test.addScreenCaptureFromPath(path, "Screenshot"));
        }
    }

    @AfterMethod
    public void afterMethod(ITestResult result) throws IOException {
        //buildID = runCommand.linuxRunSSHCommandOutputString(StaticDataProvider.LinuxMachines.VM_SIM_1A, "BuildId", "build_history");
        getResult(result);

    }
}
