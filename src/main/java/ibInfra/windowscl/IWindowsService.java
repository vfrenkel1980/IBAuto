package ibInfra.windowscl;


import java.io.File;
import java.io.IOException;

public interface IWindowsService {

    int runCommandWaitForFinish(String command);

    void runCommandDontWaitForTermination(String command);

    String runCommandGetOutput(String command);

    void waitForProcessToFinish(String processName);

    void waitForProcessToFinishOnRemoteMachine(String host, String user, String pass, String processName);

    int getNumberOfProcessInstances(String processName);

    void waitForProcessToStart(String processName);

    String getProcessPid(String processName);

    boolean isServiceRunning(String serviceName);

    void downloadFile(String url, String fileName) throws IOException;

    File getLatestFileFromDir(String dirPath, String substring);

    String getHostName();

    void restartService(String serviceName);

    long getTodayMidnight();

    long getNowWOSeconds();
}
