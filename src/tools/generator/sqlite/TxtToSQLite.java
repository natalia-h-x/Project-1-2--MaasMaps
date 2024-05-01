package tools.generator.sqlite;

import static core.Constants.ANSI.GREEN;
import static core.Constants.ANSI.RESET;
import static core.Constants.ANSI.YELLOW;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import core.managers.DatabaseManager;
import core.managers.FileManager;

/*
 * To recreate, first unzip the GTFS file in /resources and rename .txt files to .csv (line 14 assumes unzipped location)
 */
public class TxtToSQLite {
    private TxtToSQLite() {}

    public static void txtToSQLite(String folderPath) throws IOException {
        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();

        long startTime = System.currentTimeMillis(); // Start timer

        System.out.println("\n" + GREEN + "[CONNECTING TO DATABASE]" + RESET);
        
        for (File file : listOfFiles) {
            String fname = file.getName();
            if (file.isFile() && fname.endsWith(".txt")) { // Only process .txt files formatted as CSV
                System.out.println("\nProcessing file \"" + fname + "\"" );
                createTable(file);
            }
        }

        long endTime = System.currentTimeMillis(); // end timer
        long totalTime = endTime - startTime;
        double totalTimeInMins = Math.round(((totalTime) / 60000.0) * 100.0) / 100.0;

        System.out.println(GREEN + "\n[DATABASE VERIFIED]" + RESET);
        System.out.println(YELLOW + "Elapsed time: " + RESET + totalTimeInMins + " min.");
    }

    private static void createTable(File file) throws IOException {
        String tableName = file.getName().replaceFirst("[.][^.]+$", "");
        String[] lines = FileManager.readLines(file);
        String[] headers = parseCSV(lines[0].trim());
        String[] types = new String[headers.length];
        
        for (int j = 0; j < types.length; j++) {
            types[j] = "TEXT"; //FIXME change to actual type
        }

        DatabaseManager.createTable(tableName, headers, types);

        String[][] data = new String[lines.length - 1][headers.length];
        
        for (int i = 1; i < data.length; i++) {
            data[i] = parseCSV(lines[i].trim());
        }

        DatabaseManager.insertInTable(tableName, data);
    }
    
    private static String[] parseCSV(String csvLine) {
        String regex = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
        return csvLine.split(regex);
    }
}
