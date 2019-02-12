package frameworkInfra.testbases;

import com.aventstack.extentreports.Status;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import ibInfra.ibExecs.IIBCoordMonitor;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.lang.reflect.Method;

import static frameworkInfra.Listeners.SuiteListener.extent;
import static frameworkInfra.Listeners.SuiteListener.test;

public class EnterpriseNegativeTestBase extends  EnterpriseTestBase{

    @BeforeClass
    public void beforeClass(){
        test = extent.createTest("Before Class");
        test.log(Status.INFO, "BEFORE CLASS atarted");
        log.info("BEFORE CLASS staerted");
        ibService.installIB("Latest", IbLicenses.PRO_LIC);
        SystemActions.sleep(10);
        winService.runCommandWaitForFinish(IbLocations.XGCOORDCONSOLE + "/AllocateAll");
        SystemActions.sleep(10);
        IIBCoordMonitor coordMonitor = new IIBCoordMonitor();
        try {
            coordMonitor.waitForAgentIsUpdated(WindowsMachines.DASHBORD_HELPER);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ibService.customPackAllocationOn();
        test.log(Status.INFO, "BEFORE CLASS finished");
        log.info("BEFORE CLASS finished");
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        testName = getTestName(method);
        test = extent.createTest(testName);
        test.assignCategory("Enterprise Negative");
        test.log(Status.INFO, method.getName() + " test started");
        log.info(method.getName() + " test started");
    }
}
