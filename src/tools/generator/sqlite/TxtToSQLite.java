package tools.generator.sqlite;

import static core.Constants.ANSI.GREEN;
import static core.Constants.ANSI.RESET;
import static core.Constants.ANSI.YELLOW;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

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
            types[j] = "TEXT(100000)"; //FIXME change to actual type
        }

        DatabaseManager.createTable(tableName, headers, types);

        String[][] data = new String[lines.length - 1][headers.length];

        for (int i = 1; i < data.length + 1; i++) {
            while ((parseCSV(lines[i].trim())).length < headers.length) {
                lines[i] = lines[i].trim() + "null";
            }
            data[i - 1] = parseCSV(lines[i].trim());
        }
        
        int maxLines = 10000;
        int current = data.length;
        while (current > 10000) {
            String[][] smallerData = new String[maxLines][headers.length];
            for (int i = 0; i < divideData(data.length, maxLines); i++) {
                System.arraycopy(data, i*10000, smallerData, 0, maxLines);
                DatabaseManager.insertInTable(tableName, headers, smallerData);
                current = current - 10000;
            }
        }
        if (current > 0) {
            String[][] smallerData = new String[current][headers.length];
            System.arraycopy(data, data.length - current, smallerData, 0, current);
            DatabaseManager.insertInTable(tableName, headers, smallerData);
        }
    }

    private static int divideData(int n, int m) {
        return (int) n/m;
    }
    
    private static String[] parseCSV(String csvLine) {
        String regex = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
        return csvLine.split(regex);
    }
}
