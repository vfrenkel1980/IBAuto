package ibInfra.windowscl;


import java.io.IOException;

public interface IWindowsService {

    int runCommandWaitForFinish(String command);

    void runCommandDontWaitForTermination(String command);

    String runCommandGetOutput(String command);

    void waitForProcessToFinish(String processName);

    void waitForProcessToFinishOnRemoteMachine(String host,String user, String pass, String processName);

    int getNumberOfProcessInstances(String processName);

    void waitForProcessToStart(String processName);

    String getProcessPid(String processName);

    boolean isServiceRunning(String serviceName);

    void downloadFile(String url, String fileName) throws IOException;

    String getHostName();

}
