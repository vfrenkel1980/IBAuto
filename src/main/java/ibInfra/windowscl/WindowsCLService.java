package ibInfra.windowscl;

import com.aventstack.extentreports.Status;
import frameworkInfra.testbases.TestBase;
import frameworkInfra.utils.StaticDataProvider;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class WindowsCLService extends TestBase implements IWindowsCL{

    @Override
    public int runCommandWaitForFinish(String command) {
        String line;
        int exitStatus = 0;
        try {
            Runtime rt = Runtime.getRuntime();
            test.log(Status.INFO, "Running command " + command + " - Waiting for result");
            Process pr = rt.exec(command);

            BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));

            while((line=input.readLine()) != null) {
                System.out.println(line);
            }

            exitStatus = pr.waitFor();
            System.out.println("Command " + command + " - Completed Successfully");
            test.log(Status.INFO, "Command " + command + " - Completed Successfully");
        } catch(Exception e) {
            test.log(Status.ERROR, "Failed to run command.\n" +
                    "Command: " + command +"\n"+
                    e.getMessage());
            exitStatus = 1;
        }
        return exitStatus;
    }

    @Override
    public int cleanAndBuild(String command) {
        runCommandWaitForFinish(String.format(command, StaticDataProvider.ProjectsCommands.CLEAN));
        return runCommandWaitForFinish(String.format(command, StaticDataProvider.ProjectsCommands.BUILD));
    }


}
