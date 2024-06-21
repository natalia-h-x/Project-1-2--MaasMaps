package core.managers.database;

import static core.Constants.Paths.DATABASE_PATH;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private ConnectionManager() {}

    private static final Connection connection = connect();

    private static Connection connect() {
        try {
            return StatementManager.optimizeDatabaseForBulkInsert(DriverManager.getConnection(DATABASE_PATH));
        }
        catch (SQLException e) {
            throw new IllegalArgumentException("Could not access database.", e);
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
