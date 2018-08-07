package ibInfra.linuxcl;

import java.util.List;

public interface ILinuxService {

    int linuxRunSSHCommand(String command, String hostIP);

    String linuxRunSSHCommandOutputString(String command, String hostIP);

    List linuxRunSSHCommandAssignToList(String command, String hostIP);

    void deleteLogsFolder(List<String> ipList);

    boolean isIBServiceUp( String IP);

    boolean startIBService( String IP);

    boolean stopIBService( String IP);

    void killibDbCheck(String IP);

    boolean isLinuxOSUbuntu(String IP);

    String runQueryLastBuild(String fieldName, String sqliteTable, String IP);

    void updateIB(String destMachine, String fileName, List<String> grid);

    void copyFileFromLinuxToLinux(String srcMachine, String destMachine, String fileName);

    String getInstallerName(String machineName, String version);

    String getInstallerFolder(String machineName, String version);

    void extractUpgradeFile(String machineName, String file);

    void extractFile(String machineName, String filePath);

    String getIBVersion(String machine);

    void verifyAgentsUpdated(String hostName, String version);
}
