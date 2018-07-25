package frameworkInfra.utils;

import frameworkInfra.testbases.TestBase;
import com.aventstack.extentreports.Status;
import ibInfra.dataObjects.postgres.CoordBuild;

import java.sql.*;

import static frameworkInfra.Listeners.SuiteListener.test;

public class PostgresJDBC extends TestBase {

    public static String getLastValueFromTable(String ip, String username, String password, String db, String select, String table, String column, String orderBy){
        Connection c = null;
        Statement stmt = null;
        String value = "";
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://" + ip + ":5432/" + db,
                            username, password);
            c.setAutoCommit(false);
            if (test !=null)
                test.log(Status.INFO,"Connection established to DB");
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT " + select + " FROM " + table + " ORDER BY " + orderBy +" DESC LIMIT 1" );
            while ( rs.next() ) {
                value = rs.getString(column);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            if (test !=null)
                test.log(Status.WARNING, "DB operation failed with error: " + e.getMessage());
        }
        return value;
    }

    public static int getAllBuildsWhere(String ip, String username, String password, String db, String select, String table, String where){
        Connection c = null;
        Statement stmt = null;
        int count = 0;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://" + ip + ":5432/" + db,
                            username, password);
            c.setAutoCommit(false);
            if (test !=null)
                test.log(Status.INFO,"Connection established to DB");
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT " + select + " FROM " + table + " WHERE " + where);
            while ( rs.next() ) {
                count++;
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            if (test !=null)
                test.log(Status.WARNING, "DB operation failed with error: " + e.getMessage());
        }
        return count;
    }

    public static void runFunctionOnCoordBuildTable(String ip, String username, String password, String db, String function, CoordBuild cb){
        Connection c = null;
        Statement stmt = null;
        String value = "";
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://" + ip + ":5432/" + db,
                            username, password);
            c.setAutoCommit(true);
            if (test !=null)
                test.log(Status.INFO,"Connection established to DB");
            stmt = c.createStatement();
            stmt.executeQuery( "SELECT " + function + "(" + "'" + cb.getBuildID()+ "'" + "," + cb.getAgentID() + "," +
                    cb.getStartTime() + "," + cb.getEndTime() + "," +"'" + cb.getCaption() +"'" + "," + cb.getTotalLocalTime() + "," + cb.getTotalRemoteTime() + "," +
                    cb.getStatus() + "," + cb.getMaxCoresUsed() + "," + cb.getNumberOfTasks() + "," + cb.getAvgReadyTasks() + "," + cb.getAvgUsedCores() + "," +
                    cb.getMaxReadyTasks() + "," + cb.getBiIssuer() + "," + cb.getCiIssuer() + "," + cb.getBuildType() + "," + cb.getProductName() + "," +
                    cb.getUserName() + "," + cb.getGroupName() + "," + cb.getSavedTime() + "," + cb.isAvoidLocal() + "," +"'" + cb.getCommandText() +"'" + "," +
                    cb.isPredicted() + ")");

            stmt.close();
            c.close();
        } catch ( Exception e ) {
            if (test !=null)
                test.log(Status.WARNING, "DB operation failed with error: " + e.getMessage());
        }
    }
}
