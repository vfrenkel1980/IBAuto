package ibInfra.linuxcl;

import java.util.List;

public class LinuxDBService implements ILinuxDBService {

    private LinuxService linuxService = new LinuxService();

    private static final String SELECT_ALL = "/opt/incredibuild/bin/sqlite3 /etc/incredibuild/db/%s \"SELECT %s FROM %s\"";
    private static final String SELECT_ALL_WHERE = "/opt/incredibuild/bin/sqlite3 /etc/incredibuild/db/%s \"SELECT %s FROM %s WHERE %s\"";

    @Override
    public List<String> selectAll(String db, String select, String table, String host) {
        return linuxService.linuxRunSSHCommandAssignToList(String.format(SELECT_ALL, db, select, table), host);
    }

    @Override
    public List<String> selectAllWhere(String db, String select, String table, String where, String host) {
        return linuxService.linuxRunSSHCommandAssignToList(String.format(SELECT_ALL_WHERE, db, select, table, where), host);
    }
}
