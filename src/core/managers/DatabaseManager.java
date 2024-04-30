package core.managers;

import static constants.Constants.Paths.DATABASE_URL;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager {

    public static Connection connect() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(DATABASE_URL);
            System.out.println("Connected.");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return con;
    }

    public static void executeQuery() {
        String sql = "SELECT stop_id, stop_lat FROM stops";

        try (
            Connection conn = connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql)
            ) {
            
            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("stop_id") +  "\t" + 
                                    rs.getDouble("stop_lat"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
