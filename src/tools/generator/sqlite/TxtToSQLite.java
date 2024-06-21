package tools.generator.sqlite;

import static core.Constants.ANSI.GREEN;
import static core.Constants.ANSI.RESET;
import static core.Constants.ANSI.YELLOW;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import core.managers.FileManager;
import core.managers.database.DatabaseDefinitionManager;
import core.managers.database.DatabaseModificationManager;
import core.managers.database.QueryManager;

/*
 * To recreate, first unzip the GTFS file in /resources and rename .txt files to .csv (line 14 assumes unzipped location)
 */
public class TxtToSQLite {
    private TxtToSQLite() {}

    private static Map<String, String[]> types;

    public static void txtToSQLite(String folderPath) throws IOException {
        long startTime = System.currentTimeMillis(); // Start timer
        File folder = new File(folderPath);
        File[] listOfFiles = sortFiles(folder.listFiles());

        types = new HashMap<>();
        types.put("agency.txt", "<PK>INT:VARCHAR(64):VARCHAR(256):VARCHAR(64):VARCHAR(32)".split(":"));
        types.put("calendar_dates.txt", "INT:DATE:INT".split(":"));
        types.put("feed_info.txt", "VARCHAR(64):VARCHAR(8):VARCHAR(256):DATE:DATE:VARCHAR(8):INT".split(":"));
        types.put("routes.txt", "<PK>INT:VARCHAR(64):VARCHAR(256):VARCHAR(512):VARCHAR(256):INT:CHAR(6):CHAR(6):VARCHAR(256)".split(":"));
        types.put("shapes.txt", "<PK>INT:<PK>INT:DOUBLE:DOUBLE:INT".split(":"));
        types.put("stop_times.txt", "<PK>INT:<PK>INT:<FK stops.stop_id>INT:VARCHAR(64):TIME:TIME:INT:INT:INT:INT UNSIGNED:INT UNSIGNED".split(":"));
        types.put("stops.txt", "<PK>INT:VARCHAR(16):VARCHAR(64):DOUBLE:DOUBLE:INT:<FK stops.stop_id>INT:VARCHAR(64):INT:VARCHAR(8):VARCHAR(32)".split(":"));
        types.put("transfers.txt", "<PK><FK stops.stop_id>INT:<PK><FK stops.stop_id>INT:<PK><FK routes.route_id>INT:<PK><FK routes.route_id>INT:<PK><FK trips.trip_id>INT:<PK><FK trips.trip_id>INT:INT".split(":"));
        types.put("trips.txt", "<FK routes.route_id>INT:INT:<PK>INT:VARCHAR(64):VARCHAR(256):VARCHAR(256):VARCHAR(512):INT:INT:<FK shapes.shape_id>INT:INT:INT".split(":"));

        System.out.println("\n" + GREEN + "[CONNECTING TO DATABASE]" + RESET);

        for (File file : listOfFiles) {
            String fname = file.getName();

            if (file.isFile() && fname.endsWith(".txt")) { // Only process .txt files formatted as CSV
                System.out.println("\nProcessing file \"" + fname + "\"" );
                createTable(file);
            }
        }

        System.out.println(GREEN + "\n[DATABASE VERIFIED]" + RESET);

        long endTime = System.currentTimeMillis(); // end timer
        long totalTime = endTime - startTime;
        double totalTimeInMins = Math.round(((totalTime) / 60000.0) * 100.0) / 100.0;

        System.out.println(YELLOW + "Elapsed time: " + RESET + totalTimeInMins + " min.");
    }

    private static File[] sortFiles(File[] listOfFiles) {
        List<File> sortedFiles = new ArrayList<>();

        for (File file : listOfFiles) {
            if(file.getName().equals("stops.txt")) {
                sortedFiles.add(file);
            }
        }

        for (File file : listOfFiles) {
            if (file.getName().equals("stop_times.txt"))
                sortedFiles.add(file);
        }

        for (File file : listOfFiles) {
            if (file.getName().equals("trips.txt"))
                sortedFiles.add(file);
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

        if (file.getName().contains("trips")) {
            System.out.println();
        }

        DatabaseDefinitionManager.createTable(tableName, headers, types.get(file.getName()));
        int maxLines = 10000;
        int current = lines.length;
        String[] batch = new String[maxLines];

        for (int i = 0; i < divideData(lines.length, maxLines); i++) {
            System.arraycopy(lines, i*maxLines, batch, 0, maxLines);
            current = current - maxLines;
            String[] pruned = prune(tableName, batch, headers);

            if (pruned.length != 0)
                insertData(tableName, pruned, headers);
        }

        if (current > 0) {
            batch = new String[current];
            System.arraycopy(lines, lines.length - current, batch, 0, current);
            String[] pruned = prune(tableName, batch, headers);

            if (pruned.length != 0)
                insertData(tableName, pruned, headers);
        }
    }

    @SuppressWarnings("unchecked")
    private static String[] prune(String tableName, String[] lines, String[] headers) {
        if (tableName.equals("stops"))
            lines = pruneLines(lines);

        if (tableName.equals("stop_times") || tableName.equals("transfers")) {
            List<Integer> stopIDs = (List<Integer>) QueryManager.executeQuery("SELECT `stop_id` FROM stops", new ArrayList<Integer>())[0];
            List<Integer> indexes = indexOf(headers, "stop_id");

            lines = pruneLines(lines, new HashSet<>(stopIDs), new HashSet<>(indexes));
        }

        if (tableName.equals("trips")) {
            List<Integer> tripIDs = (List<Integer>) QueryManager.executeQuery("SELECT `trip_id` FROM stop_times", new ArrayList<Integer>())[0];
            List<Integer> indexes = indexOf(headers, "trip_id");

            lines = pruneLines(lines, new HashSet<>(tripIDs), new HashSet<>(indexes));
        }

        if (tableName.equals("shapes")) {
            List<Integer> shapeIDs = (List<Integer>) QueryManager.executeQuery("SELECT `shape_id` FROM trips", new ArrayList<Integer>())[0];
            List<Integer> indexes = indexOf(headers, "shape_id");

            lines = pruneLines(lines, new HashSet<>(shapeIDs), new HashSet<>(indexes));
        }

        if (tableName.equals("routes")) {
            List<Integer> shapeIDs = (List<Integer>) QueryManager.executeQuery("SELECT `route_id` FROM trips", new ArrayList<Integer>())[0];
            List<Integer> indexes = indexOf(headers, "route_id");

            lines = pruneLines(lines, new HashSet<>(shapeIDs), new HashSet<>(indexes));
        }

        return lines;
    }

    private static List<Integer> indexOf(String[] headers, String attributeName) {
        List<Integer> indexes = new ArrayList<>();

        for (int i = 0; i < headers.length; i++) {
            if (headers[i].equals(attributeName))
                indexes.add(i);
        }

        return indexes;
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
        String[][] smallerData = new String[maxLines][headers.length];

        for (int i = 0; i < divideData(data.length, maxLines); i++) {
            System.arraycopy(data, i * maxLines, smallerData, 0, maxLines);
            DatabaseModificationManager.insertInTable(tableName, headers, smallerData);
            current = current - maxLines;
        }

        if (current > 0) {
            smallerData = new String[current][headers.length];
            System.arraycopy(data, data.length - current, smallerData, 0, current);
            DatabaseModificationManager.insertInTable(tableName, headers, smallerData);
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

    private static String[] pruneLines(String[] lines, Set<Integer> ids, Set<Integer> indexes) {
        Set<String> prunedLines = new HashSet<>();

        for (int i = 1; i < lines.length; i++) {
            String[] current = parseCSV(lines[i].trim());

            try {
                for (int index : indexes) {
                    if (ids.contains(Integer.parseInt(current[index])) && !prunedLines.contains(lines[i]))
                        prunedLines.add(lines[i]);
                }
            } catch (NumberFormatException e) {}
        }

        return prunedLines.toArray(new String[0]);
    }

    private static int divideData(int n, int m) {
        return n / m;
    }

    private static String[] parseCSV(String csvLine) {
        String regex = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
        return csvLine.split(regex);
    }
}
