package frameworkInfra.testbases;

import com.aventstack.extentreports.Status;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.XmlParser;
import ibInfra.linuxcl.LinuxCL;
import org.testng.annotations.AfterMethod;
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

   @AfterMethod
    public void afterMethod(Method method){

       runCommand.runQueryLastBuild(StaticDataProvider.LinuxMachines.VM_SIM_1A, "BuildId", "build_history");
    }
}
