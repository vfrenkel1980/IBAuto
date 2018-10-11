package frameworkInfra.testbases;

import com.aventstack.extentreports.Status;
import frameworkInfra.Listeners.SuiteListener;
import frameworkInfra.utils.parsers.Parser;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import static frameworkInfra.Listeners.SuiteListener.extent;
import static frameworkInfra.Listeners.SuiteListener.test;

@Listeners(SuiteListener.class)
public class WindowsSimTestBase extends WindowsTestBase {

    @BeforeMethod
    @Parameters ({ "logLevel"})
    public void beforeMethod(Method method, String logLevel, ITestContext testContext){
        testName = getTestName(method);
        test = extent.createTest(testName);
        if (logLevel.equals("4"))
            test.assignCategory("Detailed logging");
        if (logLevel.equals("0"))
            test.assignCategory("Minimal logging");
        test.log(Status.INFO, method.getName() + " test started");
        log.info(method.getName() + " test started");
        testName = testContext.getName();
    }

    /**
     * After each test we validate that an message stating corrupt pdb's is not found in the log.
     *
     * @param result testng  test result
     * @throws IOException exceptions thrown for not being able to read log
     */

    @AfterMethod
    public void afterMethod(ITestResult result) throws IOException {
        if (new File(Locations.QA_ROOT, "buildlog.txt").exists()) {
            if (Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, LogOutput.PDB_ERROR))
                LogOutput.PDB_ERROR_TESTS = LogOutput.PDB_ERROR_TESTS + testName + "\n";
            SystemActions.deleteFile(Locations.OUTPUT_LOG_FILE);
        }
        extent.flush();
    }

}
