package frameworkInfra.utils;

import frameworkInfra.testbases.TestBase;
import com.aventstack.extentreports.Status;

import java.sql.*;

import static frameworkInfra.Listeners.SuiteListener.test;

public class PostgresJDBC extends TestBase {

    public static String getLastValueFromTable(String ip, String username, String password, String db, String select, String table, String coloumn){
        Connection c = null;
        Statement stmt = null;
        String value = "";
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://" + ip + ":5432/" + db,
                            username, password);
            c.setAutoCommit(false);
            //test.log(Status.INFO,"Connection established to DB");
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT " + select + " FROM " + table + " ORDER BY id DESC LIMIT 1" );
            while ( rs.next() ) {
                value = rs.getString(coloumn);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            test.log(Status.WARNING, "DB operation failed with error: " + e.getMessage());
        }
        return value;
    }
}
