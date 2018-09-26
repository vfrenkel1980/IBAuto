package frameworkInfra.utils.databases;

import ibInfra.dataObjects.postgres.CoordBuild;

import java.sql.Connection;
import java.util.LinkedHashMap;

public interface IDataBase {

    Connection connectToDb(String ip, String username, String password, String db);

    /**
     * Used to get the last value from table
     * @param ip db machine ip
     * @param username db username
     * @param password user's password
     * @param db DB
     * @param select what to select from query
     * @param table db table
     * @param column what column to select from
     * @param orderBy orderby selection
     * @return String value.
     */
    String getLastValueFromTable(String ip, String username, String password, String db, String select, String table, String column, String orderBy);

    /**
     * Used to get the long value from query
     * @param ip db machine ip
     * @param username db username
     * @param password user's password
     * @param db DB
     * @param select what to select from query
     * @param table db table
     * @param where condition for selection
     * @return log value
     */
    long getLongFromQuery(String ip, String username, String password, String db, String select, String table, String where);

    /**
     * Used to get the int value from query
     * @param ip db machine ip
     * @param username db username
     * @param password user's password
     * @param db DB
     * @param select what to select from query
     * @param table db table
     * @param where condition for selection
     * @return int value
     */
    int getIntFromQuery(String ip, String username, String password, String db, String select, String table, String where);

    /**
     * Used to get the int value from query
     * @param ip db machine ip
     * @param username db username
     * @param password user's password
     * @param db DB
     * @param select what to select from query
     * @param table db table
     * @param where condition for selection
     * @return String value
     */
    String getStringFromQuery(String ip, String username, String password, String db, String select, String table, String where);

    /**
     * Used to get the long value from query without WHERE statement
     * @param ip db machine ip
     * @param username db username
     * @param password user's password
     * @param db DB
     * @param select what to select from query
     * @param table db table
     * @return long value
     */
    long getLongFromQuery(String ip, String username, String password, String db, String select, String table);

    /**
     * Used to get the int value from query without WHERE statement
     * @param ip db machine ip
     * @param username db username
     * @param password user's password
     * @param db DB
     * @param select what to select from query
     * @param table db table
     * @return int value
     */
    int getIntFromQuery(String ip, String username, String password, String db, String select, String table);

    /**
     * Used in order to run a function from the DB on coord_build table
     * @param ip db machine ip
     * @param username db username
     * @param password user's password
     * @param db DB
     * @param function function to run
     * @param cb coord_build object that contains all columns from the table
     */
    void runFunctionOnCoordBuildTable(String ip, String username, String password, String db, String function, CoordBuild cb);

    LinkedHashMap<String,String> getLinkedHashMapFromQuery(String ip, String username, String password, String db, String select, String table, String join, String groupBy, String orderBy, int limit);

    /**
     * Used in order to insert rows to an existing table
     * @param ip db machine ip
     * @param username db username
     * @param password user's password
     * @param db DB
     * @param columns the columns you would like to enter data into
     * @param values the values you would like to add
     */
    void insertDataToTable(String ip, String username, String password, String db, String table, String columns, String values);

}
