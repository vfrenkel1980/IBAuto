package frameworkInfra.utils;

import frameworkInfra.testbases.TestBase;
import com.aventstack.extentreports.Status;

import java.sql.*;

public class SQLite extends TestBase {

    public static void selectQuery(String ip, String dbName, String query) {
        Connection conn = null;
        String url = "jdbc:sqlite:\\\\" + ip +"\\etc\\incredibuild\\db\\" + dbName;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
            test.log(Status.INFO, "Connection to SQLite DB has been established.");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                //HANDLE DATE HERE
            }
            conn.close();
            test.log(Status.INFO, "Disconnected from DB.");
        } catch (Exception e) {
          test.log(Status.ERROR, e.getMessage());
        }
    }

}
