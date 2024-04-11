package database.util.sqlite;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class RoutesTxtToSQLite {

    public static void main(String[] args) {
        String txtFilePath = "resources/routes.txt";
        String jdbcUrl = "jdbc:sqlite:transport.db";
        String line;
        String delimiter = ","; 

        try (Connection conn = DriverManager.getConnection(jdbcUrl)) {

            String createTableSQL = "CREATE TABLE IF NOT EXISTS routes (" +
                                    "route_id TEXT, " +
                                    "agency_id TEXT, " +
                                    "route_short_name TEXT, " +
                                    "route_long_name TEXT, " +
                                    "route_desc TEXT, " +
                                    "route_type INTEGER, " +
                                    "route_color TEXT, " +
                                    "route_text_color TEXT, " +
                                    "route_url TEXT" +
                                    ");";
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(createTableSQL);
                System.out.println("Table created.");
            }

            String insertSQL = "INSERT INTO routes (route_id, agency_id, route_short_name, route_long_name, route_desc, route_type, route_color, route_text_color, route_url) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement pstmt = conn.prepareStatement(insertSQL);

            try (BufferedReader br = new BufferedReader(new FileReader(txtFilePath))) {
                
                br.readLine();
                
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(delimiter, -1); // -1 to include trailing empty strings

                    for (int i = 0; i < data.length; i++) {
                        pstmt.setString(i + 1, data[i].trim()); // Set string values, trimming whitespace
                    }
                    pstmt.executeUpdate();
                }
                System.out.println("Data inserted.");
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

