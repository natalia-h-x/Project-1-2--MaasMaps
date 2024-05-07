package core.managers;

import static core.Constants.Paths.DATABASE_FILEPATH;
import static core.Constants.Paths.DATABASE_URL;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

    private static String insertString(String tableName,  String[] attributes, String[][] data) {
        String insertSQL = "INSERT INTO `%s` (%s";
    
        StringBuilder bd = new StringBuilder();
        for (int i = 0; i < attributes.length; i++) {
            bd.append("`" + attributes[i] + "`");
            if (i < attributes.length - 1) {
                bd.append(", ");
            }
        }
        bd.append(") VALUES ");
    
        for (int attribute = 0; attribute < data.length; attribute++) {
            bd.append("(");
            for (int i = 0; i < data[0].length; i++) {
                bd.append("?");
                if (i < data[0].length - 1) {
                    bd.append(", ");
                }
            }
            bd.append(")");
            if (attribute < data.length - 1)
                bd.append(", ");
        }
        bd.append(";");
        insertSQL = String.format(insertSQL, tableName, bd.toString());
        return insertSQL;
    }

    public static void insertInTable(String tableName, String[] attributes, String[][] data) throws IllegalArgumentException {
        try {
            Connection conn = connect();
            System.out.println("Inserting data into " + tableName);
            conn.setAutoCommit(false); // start transaction
            try (PreparedStatement pstmt = conn.prepareStatement(insertString(tableName, attributes, data))) {
                int count = 0;
                for (int tupleIndex = 0; tupleIndex < data.length; tupleIndex++) {
                    for (int attIndex = 0; attIndex < data[0].length; attIndex++) {
                        pstmt.setString(tupleIndex * data[0].length + (attIndex + 1), data[tupleIndex][attIndex]);
                    }
                }
                pstmt.addBatch();
                if (++count % 100 == 0) {
                    pstmt.executeBatch();
                    pstmt.clearBatch();
                }
                pstmt.executeBatch(); // final batch
                conn.commit(); // commit transaction
                System.out.println("-- Insertion complete --");
            }
        }
        catch (SQLException e) {
            throw new IllegalArgumentException("Error on inserting in the table \"%s\"".formatted(tableName), e);
        }
    }

    public static void createTable(String tableName, String[] headers, String[] types) throws IllegalArgumentException {
        try (Statement stmt = optimizeDatabaseForBulkInsert(connect()).createStatement()) {
            StringBuilder bld = new StringBuilder();
            String createTableSQL = "CREATE TABLE IF NOT EXISTS " + tableName + "(";
            bld.append(createTableSQL);

            for (int i = 0; i < headers.length; i++) {
                if (!types[i].toUpperCase().contains("DROP") && !headers[i].toUpperCase().contains("DROP")) {
                    bld.append(headers[i] + " " + types[i]);
                    if (i < headers.length-1) {
                        bld.append(", ");
                    }
                }
                else {
                    System.out.println("Why do you want to drop my table? :/");
                }
            }

            bld.append(");");
            System.out.println("Creating table " + tableName);
            stmt.execute(bld.toString());
            System.out.println("-- Table creation successful --");     

        } catch (SQLException e) {
            throw new IllegalArgumentException("Error on creating table \"%s\"".formatted(tableName), e);
        }
    }

    private static Connection optimizeDatabaseForBulkInsert(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("PRAGMA synchronous = OFF");
            stmt.execute("PRAGMA journal_mode = MEMORY");
            return connection;
        }
    }
}
