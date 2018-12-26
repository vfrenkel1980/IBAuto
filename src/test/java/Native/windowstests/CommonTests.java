package Native.windowstests;
import com.aventstack.extentreports.Status;
import frameworkInfra.testbases.WindowsSimTestBase;
import frameworkInfra.utils.parsers.Parser;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;

import static frameworkInfra.Listeners.SuiteListener.test;

public class CommonTests extends WindowsSimTestBase {

    @Test(testName = "Verify Errors In Logs")
    public void verifyErrorsInLogs() {
        int errorCount = 0;
        List<String> files = SystemActions.getAllFilesInDirectory(IbLocations.IB_ROOT + "\\logs");
        for (String file : files) {
            for (String aERROR_LIST : LogOutput.ERROR_LIST) {
                if(Parser.doesFileContainString(IbLocations.IB_ROOT + "\\logs\\" + file, aERROR_LIST)) {
                    for(String aIGNORE_ERROR_LIST : winService.textFileToList(Locations.IGNORE_ERRORS_LIST)) {
                        if (!Parser.doesFileContainString(IbLocations.IB_ROOT + "\\logs\\" + file, aIGNORE_ERROR_LIST)) {
                            errorCount++;
                            test.log(Status.WARNING, aERROR_LIST + " Appears in " + file);
                        }
                    }
                }
            }
        }
        Assert.assertFalse(errorCount > 0);
    }
}
