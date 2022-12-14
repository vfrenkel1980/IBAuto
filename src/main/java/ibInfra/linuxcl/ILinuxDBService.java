package ibInfra.linuxcl;

import java.util.List;

public interface ILinuxDBService {

    List<String> selectAll(String db, String select, String table, String host);

    List<String> selectAll(String db, String select, String table, String host, String keyFilePath);

    List<String> selectAllWhere(String db, String select, String table, String where, String host);

    List<String> selectAllWhere(String db, String select, String table, String where, String host, String keyFilePath);
}
