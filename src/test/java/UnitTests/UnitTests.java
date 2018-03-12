package UnitTests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import frameworkInfra.testbases.UnitTestBase;
import frameworkInfra.utils.Parser;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider;
import ibInfra.windowscl.WindowsService;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static frameworkInfra.testbases.TestBase.extent;

public class UnitTests {

    @Test
    public void test() {

        Map<String, String> lookFor = new HashMap<String, String>();
        lookFor.put("Command Prompt", "Command Prompt");
        String result = "";

        try {
            result = Parser.retrieveDataFromFile("C:\\QA\\Simulation\\buildLog.txt", lookFor);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(result);

    }
}
