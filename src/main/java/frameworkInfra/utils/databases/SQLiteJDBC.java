package frameworkInfra.utils.databases;

import com.aventstack.extentreports.Status;
import ibInfra.dataObjects.postgres.CoordBuild;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedHashMap;

import static frameworkInfra.Listeners.SuiteListener.test;

public class SQLiteJDBC implements IDataBase {

    @Override
    public Connection connectToDb(String ip, String username, String password, String db) {
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:C:/Program Files (x86)/IncrediBuild/decrypted_db.db");
            c.setAutoCommit(false);
            if (test != null)
                test.log(Status.INFO, "Connection established to DB");
        } catch (Exception e) {
            if (test != null)
                test.log(Status.WARNING, "DB operation failed with error: " + e.getMessage());

        }
        return c;
    }

    @Override
    public String getLastValueFromTable(String ip, String username, String password, String db, String select, String table, String column, String orderBy) {
        return null;
    }

    @Override
    public long getLongFromQuery(String ip, String username, String password, String db, String select, String table, String where) {
        return 0;
    }

    @Override
    public int getIntFromQuery(String ip, String username, String password, String db, String select, String table, String where) {
        int res = 0;
        try {
            Connection c = connectToDb(ip, username, password, db);
            Statement stmt = c.createStatement();
            if (test != null)
                test.log(Status.INFO, "Running query: SELECT " + select + "FROM " + table + "WHERE " + where);
            ResultSet rs = stmt.executeQuery("SELECT " + select + "FROM " + table + "WHERE " + where);
            rs.next();
            res = rs.getInt(1);
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            if (test != null)
                test.log(Status.WARNING, "DB operation failed with error: " + e.getMessage());
        }
        return res;
    }

    @Override
    public String getStringFromQuery(String ip, String username, String password, String db, String select, String table, String where) {
        return "";
    }

    @Override
    public long getLongFromQuery(String ip, String username, String password, String db, String select, String table) {
        return 0;
    }

    @Override
    public int getIntFromQuery(String ip, String username, String password, String db, String select, String table) {
        return 0;
    }

    @Override
    public void runFunctionOnCoordBuildTable(String ip, String username, String password, String db, String function, CoordBuild cb) {

    }

    @Override
    public LinkedHashMap<String, String> getLinkedHashMapFromQuery(String ip, String username, String password, String db, String select, String table, String join, String groupBy, String orderBy, int limit) {
        return null;
    }

    @Override
    public void insertDataToTable(String ip, String username, String password, String db, String table, String columns, String values) {

    }

    @Override
    public String getSingleValueWithCondition(String ip, String username, String password, String db, String select, String table, String where) {
        return null;
    }

    @Override
    public String getTheNthRowFromEnd(String ip, String username, String password, String db, String select, String table, int row) {
        return null;
    }
}
