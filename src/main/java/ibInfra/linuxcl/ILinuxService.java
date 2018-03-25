package ibInfra.linuxcl;

import java.util.List;

public interface ILinuxService {

    int linuxRunSSHCommand(String command, String hostIP);

    String linuxRunSSHCommandOutputString(String command, String hostIP);

    public List<String> breakDownIPList(List ipList);

    void deleteLogsFolder(List<String> ipList);

    boolean isIBServiceUp(String service, String IP);

    boolean startIBService(String service, String IP);

    boolean stopIBService(String service, String IP);

    void killib_db_check(String IP);

    String getLinuxOS(String IP);

    String runQueryLastBuild(String fieldName, String sqliteTable, String IP);
}
