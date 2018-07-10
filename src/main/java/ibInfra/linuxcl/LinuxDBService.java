package ibInfra.linuxcl;

import java.util.List;

public class LinuxDBService implements ILinuxDBService {

    private LinuxService linuxService = new LinuxService();

    private static final String SELECT_ALL = "/opt/incredibuild/bin/sqlite3 /etc/incredibuild/db/%s \"SELECT %s FROM %s\"";

    @Override
    public List<String> selectAll(String db, String select, String table, String host) {
        return linuxService.linuxRunSSHCommandAssignToList(String.format(SELECT_ALL, db, select, table), host);
    }
}
