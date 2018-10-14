package ibInfra.windowscl;

import com.aventstack.extentreports.Status;
import frameworkInfra.testbases.TestBase;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import org.jutils.jprocesses.JProcesses;
import org.jutils.jprocesses.model.ProcessInfo;

import java.io.*;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import static frameworkInfra.Listeners.SuiteListener.test;

public class WindowsService implements IWindowsService {

    @Override
    public int runCommandWaitForFinish(String command) {
        String line;
        int exitStatus = 0;
        try {
            Runtime rt = Runtime.getRuntime();
            if (test != null) {
                test.log(Status.INFO, "Running command " + command + " - Waiting for result");
            }
            Process pr = rt.exec(command);
            BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));

            while ((line = input.readLine()) != null) {
                System.out.println(line);
            }

            exitStatus = pr.waitFor();
            System.out.println("Command " + command + " - Completed Successfully");
            if (test != null) {
                test.log(Status.INFO, "Command " + command + " - Completed Successfully");
            }
        } catch (Exception e) {
            test.log(Status.ERROR, "Failed to run command.\n" +
                    "Command: " + command + "\n" +
                    e.getMessage());
            exitStatus = 1;
        }
        return exitStatus;
    }

    @Override
    public void runCommandDontWaitForTermination(String command) {
        try {
            Runtime rt = Runtime.getRuntime();
            if (test != null) {
                test.log(Status.INFO, "Running command " + command + " - Waiting for result");
            }
            rt.exec(command);
            System.out.println("Command " + command + " - Completed Successfully");
            if (test != null) {
                test.log(Status.INFO, "Command " + command + " - Completed Successfully");
            }
        } catch (Exception e) {
            test.log(Status.WARNING, "Failed to run command.\n" +
                    "Command: " + command + "\n" +
                    e.getMessage());
        }
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
        } catch (Exception e) {
            test.log(Status.ERROR, "Failed to run command.\n" +
                    "Command: " + command + "\n" +
                    e.getMessage());
            return "Unable to get result output from command " + command;
        }
    }

    @Override
    public void waitForProcessToFinish(String processName) {
        boolean isRunning = true;
        String output;
        while (isRunning) {
            output = runCommandGetOutput(String.format(WindowsCommands.GET_RUNNING_TASK, processName));
            System.out.println(output);
            if (output.contains("INFO: No tasks are running")) {
                isRunning = false;
            }
        }
        test.log(Status.INFO, processName + " Finished running");
    }

    @Override
    public void waitForProcessToFinishOnRemoteMachine(String host, String user, String pass, String processName) {
        boolean isRunning = true;
        String output;
        while (isRunning) {
            output = runCommandGetOutput(Processes.PSLIST + " \\\\" + host + " -u " + user + " -p " + pass + " -e " + processName);
            System.out.println(output);
            if (output.contains("process " + processName + " was not found on " + host)) {
                isRunning = false;
            }
            if (output.contains("Access is denied.") || output.contains("Failed to take process snapshot on")) {
                isRunning = false;
            }
        }
        test.log(Status.INFO, processName + " Finished running");
    }

    @Override
    public int getNumberOfProcessInstances(String processName) {
        String output;
        int instanceCount = 0;
        int lastIndex = 0;
        output = runCommandGetOutput(String.format(WindowsCommands.GET_RUNNING_TASK, processName));
        System.out.println(output);
        while (lastIndex != -1) {
            lastIndex = output.indexOf(processName, lastIndex);
            if (lastIndex != -1) {
                instanceCount++;
                lastIndex += processName.length();
            }
        }
        return instanceCount;
    }

    @Override
    public void waitForProcessToStart(String processName) {
        boolean notRunning = true;
        String output;
        int seconds = 0;
        while (notRunning && seconds < 50) {
            output = runCommandGetOutput(String.format(WindowsCommands.GET_RUNNING_TASK, processName));
            System.out.println(output);
            if (!output.contains("INFO: No tasks are running")) {
                notRunning = false;
            }
            SystemActions.sleep(1);
            seconds++;
        }

    }

    @Override
    public String getProcessPid(String processName) {
        List<ProcessInfo> processesList = JProcesses.getProcessList();
        String pid = "";
        for (final ProcessInfo processInfo : processesList) {
            if (processInfo.getName().contains(processName)) {
                pid = processInfo.getPid();
            }
        }
        return pid;
    }

    @Override
    public boolean isServiceRunning(String serviceName) {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("sc query " + serviceName);
        } catch (IOException e) {
            e.getMessage();
        }
        Scanner reader = new Scanner(process.getInputStream(), "UTF-8");
        while (reader.hasNextLine())
            if (reader.nextLine().contains(serviceName))
                return true;
        return false;
    }

    @Override
    public String getHostName() {
        String hostname = "Unknown";
        InetAddress addr;
        try {
            addr = InetAddress.getLocalHost();
            hostname = addr.getHostName();
        } catch (UnknownHostException e) {
            e.getMessage();
        }
        return hostname;
    }

    @Override
    public void downloadFile(String url, String fileName) throws IOException {
        URL website = new URL(url);
        System.setProperty("http.agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36");
        ReadableByteChannel rbc = Channels.newChannel(website.openStream());
        FileOutputStream fos = new FileOutputStream(fileName);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
    }

    @Override
    public File getLatestFileFromDir(String dirPath, String substring) {
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            return null;
        }

        File lastModifiedFile = files[0];
        for (int i = 1; i < files.length; i++) {
            if (files[i].getName().length() > 5) {
                if (lastModifiedFile.lastModified() < files[i].lastModified() && files[i].getName().contains(substring)) {
                    lastModifiedFile = files[i];
                }
            }
        }
        return lastModifiedFile;
    }

    @Override
    public void restartService(String serviceName) {
        runCommandWaitForFinish("net stop " + serviceName);
        runCommandWaitForFinish("net start " + serviceName);
    }
    @Override
    public long getTodayMidnight() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime midnight = now.toLocalDate().atStartOfDay();
        return Timestamp.valueOf(midnight).getTime();
    }

    @Override
    public long getNowWOSeconds() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nowWOSeconds = now.withSecond(0).withNano(0);
        return Timestamp.valueOf(nowWOSeconds).getTime();
    }

    @Override
    public String changeCurDirTo(String path) {
        return "cmd /c c: && cd " + path + " && ";
    }
}
