package ibInfra.linuxcl;

import java.util.List;

public interface ILinuxCL {

    int linuxRunSSHCommand(String command, String hostIP);
    void deleteLogsFolder(List<String> ipList);
    List<String> breakDownIPList(List ipList);
}
