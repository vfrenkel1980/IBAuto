package ibInfra.windowscl;


public interface IWindowsCL {

    int runCommandWaitForFinish(String command);

    String runCommandGetOutput(String command);

    int cleanAndBuild(String command);

    void installIB();

    String getIbConsoleInstallation();

    void loadIbLicense();

    void waitForProcessToFinish(String processName);

    String getProcessPid(String processName);

}
