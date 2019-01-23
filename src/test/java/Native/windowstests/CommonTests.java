package Native.windowstests;

import com.aventstack.extentreports.Status;
import frameworkInfra.testbases.WindowsSimTestBase;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static frameworkInfra.Listeners.SuiteListener.test;
/**
 * @brief<b> <b> Common tests for all windows build simulations</b>
 * @details
 */
public class CommonTests extends WindowsSimTestBase {

    /**
     * @test Verify errors in all log files of IncrediBuild
     * @pre{ }
     * @steps{ }
     * @result{ }
     */
    @Test(testName = "Verify Errors In Logs")
    public void verifyErrorsInLogs() {
        Set<String> errorList = new HashSet<>();
        ArrayList<String> ignoreList = winService.textFileToList(Locations.IGNORE_ERRORS_LIST);
        boolean isFail = false;
        List<String> files = SystemActions.getAllFilesInDirectory(IbLocations.IB_ROOT + "\\logs");
        for (String file : files) {
            try (Scanner sc = new Scanner(new File(IbLocations.IB_ROOT + "\\logs\\" + file))) {
                while (sc.hasNext(LogOutput.START_LOG_PATTERN)) {
                    String[] header = sc.nextLine().split("--*");
                    String date = header.length > 2 ? header[2] : "";

                    while (sc.hasNextLine() && !sc.hasNext(LogOutput.START_LOG_PATTERN)) {
                        String str = sc.nextLine();
                        int i = str.indexOf(" ");
                        if (!str.isEmpty() && i > 0) {
                            str = str.substring(0, i);
                            if (LogOutput.ERROR_LIST.contains(str)) {
                                String error = str;

                                while (sc.hasNextLine() && !sc.hasNext(LogOutput.START_LOG_PATTERN)) {
                                    str = sc.nextLine();
                                    if (!str.isEmpty() && !ignoreList.contains(str)) {
                                        String errorMessage = error + ": \"" + str + "\" appears in " + file;
                                        if (!errorList.contains(errorMessage)) {
                                            test.log(Status.WARNING, date + ": " + errorMessage);
                                            errorList.add(errorMessage);
                                            isFail = true;
                                        }
                                    }
                                    if (!str.isEmpty()) {
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                test.log(Status.INFO, "Failed with error: " + e.getMessage());
            }
        }
        Assert.assertFalse(isFail);
    }
}

