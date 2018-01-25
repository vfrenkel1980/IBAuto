package frameworkInfra.testbases;

import com.aventstack.extentreports.Status;
import frameworkInfra.utils.XmlParser;
import ibInfra.linuxcl.LinuxCL;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.lang.reflect.Method;
import java.util.List;

public class LinuxSimTestBase extends TestBase {

    public LinuxCL runCommand = new LinuxCL();
    private List rawIpList;
    public List<String> ipList;

    @BeforeSuite
    public void envSetUp(){
        test = extent.createTest("Before Suite");
        test.assignCategory("BEFORE SUITE");
        test.log(Status.INFO, "BEFORE SUITE started");

        rawIpList = XmlParser.getIpList();
        ipList = runCommand.breakDownIPList(rawIpList);
        runCommand.deleteLogsFolder(ipList);
    }

    @BeforeMethod
    public void beforeMethod(Method method){
        testName = getTestName(method);
        test = extent.createTest(testName);
        test.assignCategory("Linux Simulation");
        test.log(Status.INFO, method.getName() + " test started");
    }
}
