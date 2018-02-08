package ibInfra.windowscl;


import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;

public interface IWindowsCL {

    int runCommandWaitForFinish(String command);

    String runCommandGetOutput(String command);

    int cleanAndBuild(String command);

    void installIB();

    static int getIbVersion(){
        String regVersion = RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\builder", StaticDataProvider.RegistryKeys.VERSION);
        int version = Integer.parseInt(regVersion);
        version -= 1001000;
        return version;
    }

    String getIbConsoleInstallation();

    void loadIbLicense();

    void waitForProcessToFinish(String processName);

    void waitForProcessToStart(String processName);

    String getProcessPid(String processName);

}
