package tools.generator.sqlite;

import static core.Constants.ANSI.GREEN;
import static core.Constants.ANSI.RESET;
import static core.Constants.ANSI.YELLOW;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import core.managers.DatabaseManager;
import core.managers.FileManager;

/*
 * To recreate, first unzip the GTFS file in /resources and rename .txt files to .csv (line 14 assumes unzipped location)
 */
public class TxtToSQLite {
    private TxtToSQLite() {}

    public static void txtToSQLite(String folderPath) throws IOException {
        File folder = new File(folderPath);
        File[] listOfFiles = sortFiles(folder.listFiles());
        
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

    private static File[] sortFiles(File[] listOfFiles) {
        List<File> sortedFiles = new ArrayList<>();
        for (File file : listOfFiles) {
            if(file.getName().equals("stops.txt") || file.getName().equals("routes.txt")) {
                sortedFiles.add(file);
            }
        }
        for (File file : listOfFiles) {
            if (!sortedFiles.contains(file))
                sortedFiles.add(file);
        }

        return sortedFiles.toArray(new File[0]);
    }

    private static void createTable(File file) throws IOException {
        String tableName = file.getName().replaceFirst("[.][^.]+$", "");
        String[] lines = FileManager.readLines(file);
        String[] headers = parseCSV(lines[0].trim());
        String[] types = new String[headers.length]; //FIXME
        for (int j = 0; j < types.length; j++) {
            types[j] = "TEXT(100000)"; //FIXME change to actual type
        }

        DatabaseManager.createTable(tableName, headers, types);
        int maxLines = 10000;
        int current = lines.length;
        while (current > 10000) {
            String[] smallerLines = new String[maxLines];
            for (int i = 0; i < divideData(lines.length, maxLines); i++) {
                System.arraycopy(lines, i*10000, smallerLines, 0, maxLines);
                current = current - 10000;
                renameMePlease(tableName, smallerLines, headers);   
            }
        }
        if (current > 0) {
            String[] smallerLines = new String[current];
            System.arraycopy(lines, lines.length - current, smallerLines, 0, current);
            renameMePlease(tableName, smallerLines, headers);
        }
    }
    
    @SuppressWarnings("unchecked")
    private static void renameMePlease(String tableName, String[] lines, String[] headers) { //FIXME
        if (tableName.equals("stops") || tableName.equals("routes") || tableName.equals("trips"))
            lines = pruneLines(lines);
        
        if (tableName.equals("stop_times") || tableName.equals("transfers")) { //TODO add pruning to shapes too and decide if it's better to prune trips or do it based on route_ids
            List<Integer> indexes = new ArrayList<>();
            List<String> IDs = new ArrayList<>();

            List<String> stopIDs = (List<String>) DatabaseManager.executeQuery("SELECT `stop_id` FROM stops", new ArrayList<String>())[0];
            List<String> routeIDs = (List<String>) DatabaseManager.executeQuery("SELECT `route_id` FROM routes", new ArrayList<String>())[0];
            
            IDs.addAll(stopIDs);
            IDs.addAll(routeIDs);
            for (int i = 0; i < headers.length; i++) {  
                if (headers[i].contains("stop_id") || headers[i].contains("route_id"))
                    indexes.add(i);
            }
            lines = pruneLines(lines, IDs, indexes);
        }
        if (lines.length != 0)
            insertData(tableName, lines, headers);
    }

    private static void insertData(String tableName, String[] lines, String[] headers) {
        String[][] data = new String[lines.length - 1][headers.length];
   
        for (int i = 1; i < data.length + 1; i++) {
            while ((parseCSV(lines[i].trim())).length < headers.length) {
                lines[i] = lines[i] + "null";
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

    private static String[] pruneLines(String[] lines) {
        List<String> prunedLines = new ArrayList<>();
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].contains("Maastricht")) {
                prunedLines.add(lines[i]);
            }
        }
        return prunedLines.toArray(new String[0]);
    }
    //FIXME i don't have thhe energy to think a better solution, sorry
    private static String[] pruneLines(String[] lines, List<String> IDs, List<Integer> indexes) {
        List<String> prunedLines = new ArrayList<>();
        for (int i = 0; i < lines.length; i++) {
            for (int index : indexes) { //added arraylist to account for cases where there is no return (i don't think it will happen tho)
                String[][] current = new String[1][index + 1];
                current[0] = parseCSV(lines[i].trim());
                for (String stopID : IDs) {
                    if (current[0][index].equalsIgnoreCase(stopID) && !prunedLines.contains(lines[i]))
                            prunedLines.add(lines[i]);
                }
            }
        }
        return prunedLines.toArray(new String[0]);
    }

    private static int divideData(int n, int m) {
        return n/m;
    }
    
    private static String[] parseCSV(String csvLine) {
        String regex = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
        return csvLine.split(regex);
    }
}
