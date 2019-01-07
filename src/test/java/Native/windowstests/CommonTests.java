package Native.windowstests;

import com.aventstack.extentreports.Status;
import frameworkInfra.testbases.WindowsSimTestBase;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import static frameworkInfra.Listeners.SuiteListener.test;

public class CommonTests extends WindowsSimTestBase {

    @Test(testName = "Verify Errors In Logs")
    public void verifyErrorsInLogs() {
        Set<String> errorList = new HashSet<>();
        boolean isFail = false;
        List<String> files = SystemActions.getAllFilesInDirectory(IbLocations.IB_ROOT + "\\logs");
        for (String file : files) {
            errorList.clear();
            try (Scanner sc = new Scanner(new File(IbLocations.IB_ROOT + "\\logs\\" + file))) {
                while (sc.hasNext(LogOutput.START_LOG_PATTERN)) {
                    String[] header = sc.nextLine().split("--*");
                    String error = header.length > 2 ? header[2] : "";

                    while (sc.hasNextLine() && !sc.hasNext(LogOutput.START_LOG_PATTERN)) {
                        String str = sc.nextLine();
                        int i = str.indexOf(" ");
                        if (!str.isEmpty() && i > 0) {
                            str = str.substring(0, i);
                            if (LogOutput.ERROR_LIST.contains(str)) {
                                error += ("\n" + str);

                                while (sc.hasNextLine() && !sc.hasNext(LogOutput.START_LOG_PATTERN)) {
                                    str = sc.nextLine();
                                    if (!str.isEmpty() && !Locations.IGNORE_ERRORS_LIST.contains(str)) {
                                        String errorMessage = error + "\n" + str + " Appears in " + file;
                                        if (!errorList.contains(errorMessage)) {
                                            test.log(Status.WARNING, errorMessage);
                                            errorList.add(errorMessage);
                                            isFail = true;
                                        }
                                    }
                                    if (!str.isEmpty()) {
                                        break;
                                    }
                                }
                            } else {
                                break;
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

