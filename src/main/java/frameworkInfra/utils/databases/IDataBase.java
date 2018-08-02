package frameworkInfra.utils.databases;

import ibInfra.dataObjects.postgres.CoordBuild;

import java.sql.Connection;

public interface IDataBase {

    Connection connectToDb(String ip, String username, String password, String db);

    String getLastValueFromTable(String ip, String username, String password, String db, String select, String table, String column, String orderBy);

    long getLongFromQuery(String ip, String username, String password, String db, String select, String table, String where);

    int getIntFromQuery(String ip, String username, String password, String db, String select, String table, String where);

    int getAllBuildsWhere(String ip, String username, String password, String db, String select, String table, String where);

    void runFunctionOnCoordBuildTable(String ip, String username, String password, String db, String function, CoordBuild cb);

}
