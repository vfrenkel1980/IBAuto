package frameworkInfra.utils;

import frameworkInfra.testbases.TestBase;
import com.aventstack.extentreports.Status;

import java.sql.*;

import static frameworkInfra.Listeners.SuiteListener.test;

public class PostgresJDBC extends TestBase {

    public static int getLastBuildExitCode(){
        Connection c = null;
        Statement stmt = null;
        int status = 0;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/coordinatordb",
                            "ib", "ib");
            c.setAutoCommit(false);
            test.log(Status.INFO,"Connection established to DB");
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM public.coord_build ORDER BY id DESC LIMIT 1" );
            while ( rs.next() ) {
                status = rs.getInt("status");
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            test.log(Status.WARNING, "DB operation failed with error: " + e.getMessage());
            e.getMessage();
        }
        return status;
    }
}
