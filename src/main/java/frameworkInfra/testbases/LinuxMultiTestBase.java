package frameworkInfra.testbases;

import com.aventstack.extentreports.Status;
import frameworkInfra.utils.StaticDataProvider;
import org.testng.annotations.BeforeSuite;

public class LinuxMultiTestBase extends LinuxSimTestBase{

    @Override
    @BeforeSuite
    public void envSetUp() {
        test = extent.createTest("Before Suite");
        test.assignCategory("BEFORE SUITE");
        test.log(Status.INFO, "BEFORE SUITE started");

        runLinux.deleteLogsFolder(ipList);

        if(!runLinux.isIBServiceUp("ib_server", StaticDataProvider.LinuxMachines.SIM_INITIATOR)) {
            test.log(Status.ERROR, "IB service is down... FAILING ALL TESTS!");
        }

    }
}
