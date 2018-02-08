package ibInfra.windowscl;

import com.aventstack.extentreports.Status;
import frameworkInfra.testbases.TestBase;
import frameworkInfra.testbases.WindowsTestBase;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider;
import org.jutils.jprocesses.JProcesses;
import org.jutils.jprocesses.model.ProcessInfo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;

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
    public String runCommandGetOutput(String command) {
        String line;
        try {
            Runtime rt = Runtime.getRuntime();
            Process pr = rt.exec(command);

            BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));

            StringBuilder commandOutput = new StringBuilder();
            while ((line = input.readLine()) != null) {
                commandOutput.append(line);
                commandOutput.append('\n');
            }
            pr.waitFor();
            System.out.println("Command " + command + " - Completed Successfully");
            return commandOutput.toString();
        } catch(Exception e) {
            test.log(Status.ERROR, "Failed to run command.\n" +
                    "Command: " + command +"\n"+
                    e.getMessage());
            return "Unable to get result output from command " + command;
        }
    }

    @Override
    public int cleanAndBuild(String command) {
        runCommandWaitForFinish(String.format(command, StaticDataProvider.ProjectsCommands.CLEAN));
        return runCommandWaitForFinish(String.format(command, StaticDataProvider.ProjectsCommands.BUILD));
    }


    @Override
    public void installIB() {
        String installationFile = getIbConsoleInstallation();
        runCommandWaitForFinish(String.format(StaticDataProvider.WindowsCommands.IB_INSTALL_COMMAND, installationFile));
        loadIbLicense();
    }

    @Override
    public String getIbConsoleInstallation() {
        String path = "c:\\bld";
        String postFix = "console.exe";
        String installerName = "";
        try (DirectoryStream<Path> newDirectoryStream = Files.newDirectoryStream(Paths.get(path), "*" + postFix)) {
            for (final Path newDirectoryStreamItem : newDirectoryStream) {
                installerName = String.valueOf(newDirectoryStreamItem);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return installerName;
    }

    @Override
    public void loadIbLicense() {
        runCommandWaitForFinish(StaticDataProvider.WindowsCommands.APPLY_IB_LICENSE);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.getMessage();
        }
        runCommandWaitForFinish(StaticDataProvider.WindowsCommands.KILL_COORDMON);
    }

    @Override
    public void waitForProcessToFinish(String processName) {
        boolean isRunning = true;
        String output;
        while (isRunning){
            output = runCommandGetOutput(String.format(StaticDataProvider.WindowsCommands.GET_RUNNING_TASK, processName));
            System.out.println(output);
            if (output.contains("INFO: No tasks are running")){
                isRunning = false;
            }
        }
    }

    @Override
    public void waitForProcessToStart(String processName) {
        boolean notRunning = true;
        String output;
        while (notRunning){
            output = runCommandGetOutput(String.format(StaticDataProvider.WindowsCommands.GET_RUNNING_TASK, processName));
            System.out.println(output);
            if (!output.contains("INFO: No tasks are running")){
                notRunning = false;
            }
        }
    }

    @Override
    public String getProcessPid(String processName) {
        List<ProcessInfo> processesList = JProcesses.getProcessList();
        String pid = "";
        for (final ProcessInfo processInfo : processesList) {
            if (processInfo.getName().contains(processName)){
                pid = processInfo.getPid();
            }
        }
        return pid;
    }


}
