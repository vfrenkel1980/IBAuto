package ibInfra.linuxcl;

import java.util.List;

public interface ILinuxCL {

    int linuxRunSSHCommand(String command, String hostIP);
    void linuxRunSSHCommandDontWaitForExitCode(String command, String hostIP) throws InterruptedException;
    void deleteLogsFolder(List<String> ipList);
    List<String> breakDownIPList(List ipList);
}
