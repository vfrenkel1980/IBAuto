package ibInfra.linuxcl;

import java.util.List;

public interface ILinuxService {

    int linuxRunSSHCommand(String command, String hostIP);

    String linuxRunSSHCommandOutputString(String command, String hostIP);

    void deleteLogsFolder(List<String> ipList);

    boolean isIBServiceUp( String IP);

    boolean startIBService( String IP);

    boolean stopIBService( String IP);

    void killibDbCheck(String IP);

    boolean isLinuxOSUbuntu(String IP);

    String runQueryLastBuild(String fieldName, String sqliteTable, String IP);
}
