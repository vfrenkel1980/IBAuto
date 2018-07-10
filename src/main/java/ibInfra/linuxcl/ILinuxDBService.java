package ibInfra.linuxcl;

import java.util.List;

public interface ILinuxDBService {

    List<String> selectAll(String db, String select, String table, String host);
}
