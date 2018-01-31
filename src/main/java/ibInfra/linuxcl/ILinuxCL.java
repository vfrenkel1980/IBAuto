package ibInfra.linuxcl;

import java.util.List;

public interface ILinuxCL {

    int linuxRunSSHCommand(String command, String hostIP);

    String linuxRunSSHCommandOutputString(String command, String hostIP) throws InterruptedException;

    void deleteLogsFolder(List<String> ipList);

    List<String> breakDownIPList(List ipList);
    boolean isIBServiceUp(String IP, String service);
    int runQueryLastBuild(String IP, String fieldName, String sqliteTable);
}
