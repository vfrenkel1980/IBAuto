package ibInfra.linuxcl;

import java.util.List;

public interface ILinuxService {

    int linuxRunSSHCommand(String command, String hostIP);

    String linuxRunSSHCommandOutputString(String command, String hostIP) throws InterruptedException;

    void deleteLogsFolder(List<String> ipList);

    List<String> breakDownIPList(List ipList);

    boolean isIBServiceUp(String service, String IP);

    boolean StartIBService(String service, String IP);

    boolean StopIBService(String service, String IP);

    String runQueryLastBuild(String fieldName, String sqliteTable, String IP) throws InterruptedException;
}
