package core.managers;

import java.sql.Statement;

import static core.Constants.Paths.DATABASE_URL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager {
    private DatabaseManager() {}

    private static Connection connect() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL);
    }

    public static ResultSet executeQuery(String query) throws IllegalArgumentException {
        try (
            Connection conn = connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(query)
            ) {
            return rs;
        }
        catch (SQLException e) {
            throw new IllegalArgumentException("Error on executing query \"%s\"".formatted(query), e);
        }
    }
}
