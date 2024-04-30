package tools.generator.sqlite;

import static core.Constants.ANSI.*;
import static core.Constants.Paths.DATABASE_FILEPATH;
import static core.Constants.Paths.DATABASE_URL;

import java.io.*;
import java.sql.*;
import java.util.*;

/*
 * To recreate, first unzip the GTFS file in /resources and rename .txt files to .csv (line 14 assumes unzipped location)
 */
public class TxtToSQLite {
    // FIXME: Convert implementation to a manager instead of requiring an object to be created.
    public TxtToSQLite(String folderPath) {
        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();

        long startTime = System.currentTimeMillis(); // Start timer

        System.out.println("\n" + GREEN + "[CONNECTING TO DATABASE]" + RESET);
        
        try (Connection connection = DriverManager.getConnection(DATABASE_URL)) {
            if (connection != null) {
                optimizeDatabaseForBulkInsert(connection);

                DatabaseMetaData meta = connection.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("Database file created at " + DATABASE_FILEPATH);

                for (File file : listOfFiles) {
                    String fname = file.getName();
                    if (file.isFile() && fname.endsWith(".txt")) { // Only process .txt files formatted as CSV
                        System.out.println("\nProcessing file \"" + fname + "\"" );
                        createTable(file, connection);
                    }
                }
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        long endTime = System.currentTimeMillis(); // end timer
        long totalTime = endTime - startTime;
        double totalTimeInMins = Math.round(((totalTime) / 60000.0) * 100.0) / 100.0;

        System.out.println(GREEN + "\n[DATABASE VERIFIED]" + RESET);
        System.out.println(YELLOW + "Elapsed time: " + RESET + totalTimeInMins + " min.");
    }

    private static void optimizeDatabaseForBulkInsert(Connection connection) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("PRAGMA synchronous = OFF");
        stmt.execute("PRAGMA journal_mode = MEMORY");
        stmt.close();
    }

    private static void createTable(File file, Connection connection) throws SQLException {
        String tableName = file.getName().replaceFirst("[.][^.]+$", "");
        String createTableSQL = "CREATE TABLE IF NOT EXISTS " + tableName + "(";
        String insertSQL = "INSERT INTO " + tableName + " (";
        List<String> columns = new ArrayList<>();
        List<String> values = new ArrayList<>();
    
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine(); // read the first line to get headers
            if (line != null) {
                String[] headers = parseCSV(line);
                for (int i = 0; i < headers.length; i++) {
                    String header = headers[i].trim();
                    columns.add(header);
                    createTableSQL += header + " TEXT";
                    insertSQL += header;
                    values.add("?");
                    if (i < headers.length - 1) {
                        createTableSQL += ", ";
                        insertSQL += ", ";
                    }
                }
            }
            createTableSQL += ")";
            insertSQL += ") VALUES (" + String.join(", ", values) + ")";
    
            System.out.println("Creating table " + tableName);
            try (Statement stmt = connection.createStatement()) {
                stmt.execute(createTableSQL);
            }
            System.out.println("-- Table creation successful --");
    
            System.out.println("Inserting data into " + tableName);
            connection.setAutoCommit(false); // start transaction
            try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
                int count = 0;
                while ((line = br.readLine()) != null) {
                    String[] data = parseCSV(line);
                    for (int i = 0; i < data.length; i++) {
                        pstmt.setString(i + 1, data[i]);
                    }
                    pstmt.addBatch();
                    if (++count % 100 == 0) {
                        pstmt.executeBatch();
                        pstmt.clearBatch();
                    }
                }
                pstmt.executeBatch(); // final batch
                connection.commit(); // commit transaction
                System.out.println("-- Insertion complete --");
            }
        }
        catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
    
    private static String[] parseCSV(String csvLine) {
        String regex = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
        return csvLine.split(regex);
    }
}
