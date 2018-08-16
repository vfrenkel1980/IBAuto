package frameworkInfra.utils.databases;

import ibInfra.dataObjects.postgres.CoordBuild;

import java.sql.Connection;
import java.util.LinkedHashMap;

public interface IDataBase {

    Connection connectToDb(String ip, String username, String password, String db);

    /**
     * Used to get the last value from table
     * @return String value.
     */
    String getLastValueFromTable(String ip, String username, String password, String db, String select, String table, String column, String orderBy);

    /**
     * Used to get the long value from query
     * @return log value
     */
    long getLongFromQuery(String ip, String username, String password, String db, String select, String table, String where);

    /**
     * Used to get the int value from query
     * @return int value
     */
    int getIntFromQuery(String ip, String username, String password, String db, String select, String table, String where);

    /**
     * Used to get the long value from query without WHERE statement
     * @return long value
     */
    long getLongFromQuery(String ip, String username, String password, String db, String select, String table);

    /**
     * Used to get the int value from query without WHERE statement
     * @return int value
     */
    int getIntFromQuery(String ip, String username, String password, String db, String select, String table);

    /**
     * Used in order to run a function from the DB on coord_build table
     * @param cb coord_build object that contains all columns from the table
     * @return long value
     */
    void runFunctionOnCoordBuildTable(String ip, String username, String password, String db, String function, CoordBuild cb);

    LinkedHashMap<String,String> getLinkedHashMapFromQuery(String ip, String username, String password, String db, String select, String table, String join, String groupBy, String orderBy, int limit);

}
