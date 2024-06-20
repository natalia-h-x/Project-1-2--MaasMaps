package core.managers.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class StatementManager {
    private StatementManager() {}

    public static Connection optimizeDatabaseForBulkInsert(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("PRAGMA synchronous = OFF");
            stmt.execute("PRAGMA journal_mode = MEMORY");
            return connection;
        }
    }
}
