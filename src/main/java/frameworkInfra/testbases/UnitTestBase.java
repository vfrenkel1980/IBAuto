package frameworkInfra.testbases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import frameworkInfra.utils.StaticDataProvider;
import ibInfra.windowscl.WindowsCLService;
import org.testng.annotations.BeforeMethod;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class UnitTestBase {

    public static int runCommandWaitForFinish(String command) {
        String line;
        int exitStatus = 0;
        try {
            Runtime rt = Runtime.getRuntime();
            Process pr = rt.exec(command);
            System.out.println(command);
            BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));

            while((line=input.readLine()) != null) {
                System.out.println(line);
            }

            exitStatus = pr.waitFor();
        } catch(Exception e) {
            exitStatus = 1;
        }
        return exitStatus;
    }

    public static int cleanAndBuild(String command) {
        runCommandWaitForFinish(String.format(command,StaticDataProvider.ProjectsCommands.CLEAN));
        return runCommandWaitForFinish(String.format(command,StaticDataProvider.ProjectsCommands.BUILD));
    }
}
