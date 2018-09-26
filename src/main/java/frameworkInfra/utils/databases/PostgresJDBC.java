package frameworkInfra.utils.databases;

import com.aventstack.extentreports.Status;
import ibInfra.dataObjects.postgres.CoordBuild;

import java.sql.*;
import java.util.LinkedHashMap;

import static frameworkInfra.Listeners.SuiteListener.test;

public class PostgresJDBC implements IDataBase {

    public Connection connectToDb(String ip, String username, String password, String db) {
        Connection c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://" + ip + ":5432/" + db,
                            username, password);
            c.setAutoCommit(true);
            if (test != null)
                test.log(Status.INFO, "Connection established to DB");
        } catch (Exception e) {
            if (test != null)
                test.log(Status.WARNING, "DB operation failed with error: " + e.getMessage());

        }
        return c;
    }

    public String getLastValueFromTable(String ip, String username, String password, String db, String select, String table, String column, String orderBy) {
        Statement stmt = null;
        String value = "";
        try {
            Connection c = connectToDb(ip, username, password, db);
            stmt = c.createStatement();
            if (test != null)
                test.log(Status.INFO, "Running query: SELECT " + select + " FROM " + table + " ORDER BY " + orderBy + " DESC LIMIT 1");
            ResultSet rs = stmt.executeQuery("SELECT " + select + " FROM " + table + " ORDER BY " + orderBy + " DESC LIMIT 1");
            while (rs.next()) {
                value = rs.getString(column);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            if (test != null)
                test.log(Status.WARNING, "DB operation failed with error: " + e.getMessage());
        }
        return value;
    }

    public long getLongFromQuery(String ip, String username, String password, String db, String select, String table, String where){
        long res = 0;

        try {
            Connection c = connectToDb(ip, username, password, db);
            Statement stmt = c.createStatement();
            if (test != null)
                test.log(Status.INFO, "Running query: SELECT " + select + " FROM " + table + " WHERE " + where);
            ResultSet rs = stmt.executeQuery("SELECT " + select + " FROM " + table + " WHERE " + where);
            rs.next();
            res = rs.getLong(1);
        } catch (Exception e) {
            if (test != null)
                test.log(Status.WARNING, "DB operation failed with error: " + e.getMessage());
        }
        return res;
    }

    public int getIntFromQuery(String ip, String username, String password, String db, String select, String table, String where){
        int res = 0;
        try {
            Connection c = connectToDb(ip, username, password, db);
            Statement stmt = c.createStatement();
            if (test != null)
                test.log(Status.INFO, "Running query: SELECT " + select +  " FROM " + table + " WHERE " + where);
            ResultSet rs = stmt.executeQuery(" SELECT " + select + " FROM " + table + " WHERE " + where);
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
        String res = "";
        try {
            Connection c = connectToDb(ip, username, password, db);
            Statement stmt = c.createStatement();
            if (test != null)
                test.log(Status.INFO, "Running query: SELECT " + select +  " FROM " + table + " WHERE " + where);
            ResultSet rs = stmt.executeQuery(" SELECT " + select + " FROM " + table + " WHERE " + where);
            rs.next();
            res = rs.getString(1);
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            if (test != null)
                test.log(Status.WARNING, "DB operation failed with error: " + e.getMessage());
        }
        return res;
    }

    public long getLongFromQuery(String ip, String username, String password, String db, String select, String table){
        long res = 0;

        try {
            Connection c = connectToDb(ip, username, password, db);
            Statement stmt = c.createStatement();
            if (test != null)
                test.log(Status.INFO, "Running query: SELECT " + select + "FROM " + table);
            ResultSet rs = stmt.executeQuery("SELECT " + select + "FROM " + table);
            rs.next();
            res = rs.getLong(1);
        } catch (Exception e) {
            if (test != null)
                test.log(Status.WARNING, "DB operation failed with error: " + e.getMessage());
        }
        return res;
    }

    public int getIntFromQuery(String ip, String username, String password, String db, String select, String table){
        int res = 0;
        try {
            Connection c = connectToDb(ip, username, password, db);
            Statement stmt = c.createStatement();
            if (test != null)
                test.log(Status.INFO, "Running query: SELECT " + select + "FROM " + table);
            ResultSet rs = stmt.executeQuery("SELECT " + select + "FROM " + table);
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

    public void runFunctionOnCoordBuildTable(String ip, String username, String password, String db, String function, CoordBuild cb) {
        Statement stmt = null;
        try {
            Connection c = connectToDb(ip, username, password, db);
            stmt = c.createStatement();
            if (test != null)
                test.log(Status.INFO, "Running function: " + function);
            stmt.executeQuery("SELECT " + function + "(" + "'" + cb.getBuildID() + "'" + "," + cb.getAgentID() + "," +
                    cb.getStartTime() + "," + cb.getEndTime() + "," + "'" + cb.getCaption() + "'" + "," + cb.getTotalLocalTime() + "," + cb.getTotalRemoteTime() + "," +
                    cb.getStatus() + "," + cb.getMaxCoresUsed() + "," + cb.getNumberOfTasks() + "," + cb.getAvgReadyTasks() + "," + cb.getAvgUsedCores() + "," +
                    cb.getMaxReadyTasks() + "," + cb.getBiIssuer() + "," + cb.getCiIssuer() + "," + cb.getBuildType() + "," + cb.getProductName() + "," +
                    cb.getUserName() + "," + cb.getGroupName() + "," + cb.getSavedTime() + "," + cb.isAvoidLocal() + "," + "'" + cb.getCommandText() + "'" + "," +
                    cb.isPredicted() + ")");

            stmt.close();
            c.close();
        } catch (Exception e) {
            if (test != null)
                test.log(Status.WARNING, "DB operation failed with error: " + e.getMessage());
        }
    }

    public LinkedHashMap<String,String> getLinkedHashMapFromQuery(String ip, String username, String password, String db, String select, String table, String join, String groupBy, String orderBy, int limit) {
        LinkedHashMap<String, String> res = new LinkedHashMap<String, String>();
        try {
            Connection c = connectToDb(ip, username, password, db);
            Statement stmt = c.createStatement();
            if (test != null)
                test.log(Status.INFO, "Running query: SELECT " + select + " FROM " + table + " JOIN " + join + " GROUP BY " + groupBy + "ORDER BY " + orderBy + " LIMIT " + limit);
            ResultSet rs = stmt.executeQuery(" SELECT " + select + " FROM " + table + " JOIN " + join + " GROUP BY " + groupBy + " ORDER BY " + orderBy + " LIMIT " + limit);
            while (rs.next()) {
                res.put(rs.getString(1), rs.getString(2));
            }
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
    public void insertDataToTable(String ip, String username, String password, String db, String table, String columns, String values) {
        try {
            Connection c = connectToDb(ip, username, password, db);
            if (test != null)
                test.log(Status.INFO, "Running query: INSERT into " + table + " (" + columns + ") " + "VALUES " + " (" + values + ");");
            PreparedStatement stmt = c.prepareStatement("INSERT into " + table + " (" + columns + ") " + "VALUES " + "(" + values + ")");
            stmt.executeUpdate();
            stmt.close();
        } catch (Exception e) {
            if (test != null)
                test.log(Status.WARNING, "DB operation failed with error: " + e.getMessage());
        }
    }

}
