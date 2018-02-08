package ibInfra.windowscl;


import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;

public interface IWindowsService {

    int runCommandWaitForFinish(String command);

    String runCommandGetOutput(String command);

    void waitForProcessToFinish(String processName);

    void waitForProcessToStart(String processName);

    String getProcessPid(String processName);

}
