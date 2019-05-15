package ibInfra.linuxcl;

import java.util.List;

public interface ILinuxService {

    int linuxRunSSHCommand(String command, String hostIP);

    int linuxRunSSHCommand(String command, String hostI, String keyFilePath);

    String linuxRunSSHCommandOutputString(String command, String hostIP);

    String linuxRunSSHCommandOutputString(String command, String hostIP, String keyFilePath);

    List linuxRunSSHCommandAssignToList(String command, String hostIP);

    List linuxRunSSHCommandAssignToList(String command, String hostIP, String keyFilePath);

    void deleteLogsFolder(List<String> ipList);

    boolean isIBServiceUp( String IP);

    boolean isIBCoordinatorServiceUp( String IP);

    boolean startIBService( String IP);

    boolean startIBService( String IP, String keyFilePath);

    boolean stopIBService( String IP);

    void killibDbCheck(String IP);

    boolean isLinuxOSUbuntu(String IP);

    String runQueryLastBuild(String fieldName, String sqliteTable, String IP);

    void updateIB(String destMachine, String fileName, List<String> grid);

    void updateIB(String destMachine, String fileName, List<String> grid, String keyFilePath);

    void copyFileFromLinuxToLinux(String srcMachine, String destMachine, String fileName);

    void copyFileFromLinuxToLinuxWithKey(String srcMachine, String destMachine, String fileName, String keyFilePath);

    String getInstallerName(String machineName, String version);

    String getInstallerFolder(String machineName, String version);

    String getInstallationFileerName (String machineName, String version);

    void extractUpgradeFile(String machineName, String file);

    void extractFile(String machineName, String filePath);

    String getIBVersion(String machine);

    String getIBVersion(String machine , String keyFilePath);

    void verifyAgentsUpdated(String hostName, String version);

    void verifyAgentsUpdated(String hostName, String version, String keyFilePath);

    int installIB(String machineName, String version, String flags, String coord, String binSource,String instFolder, boolean isCoord);
}
