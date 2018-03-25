package ibInfra.windowscl;


import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider;

import java.io.IOException;
import java.net.MalformedURLException;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;

public interface IWindowsService {

    int runCommandWaitForFinish(String command);

    void runCommandDontWaitForTermination(String command);

    String runCommandGetOutput(String command);

    void waitForProcessToFinish(String processName);

    int getNumberOfProcessInstances(String processName);

    void waitForProcessToStart(String processName);

    String getProcessPid(String processName);

    boolean isServiceRunning(String serviceName);

    void downloadFile(String url, String fileName) throws IOException;

}
