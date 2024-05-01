package tests.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import core.managers.DatabaseManager;
import core.managers.ExceptionManager;

public class SQLConnectionTest {
    @Test
    public void test1() {
        ResultSet rs = DatabaseManager.executeQuery("SELECT stop_id, stop_lat FROM stops");
        
        // loop through the result set
        try {
            while (rs.next()) {
                System.out.println(rs.getString("stop_id") +  "\t" + 
                                   rs.getDouble("stop_lat"));
            }
        }
        catch (SQLException e) {
            ExceptionManager.handle(e);
        }
    }
}