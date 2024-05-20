package core.managers;

import static core.Constants.Paths.DATABASE_URL;

import java.awt.Color;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.algorithms.datastructures.AdjacencyListGraph;
import core.algorithms.datastructures.EdgeNode;
import core.algorithms.datastructures.Graph;
import core.models.BusStop;
import core.models.Location;
import core.models.Shape;
import core.models.Trip;
import tools.generator.sqlite.TxtToSQLite;

public class DatabaseManager {
    private DatabaseManager() {}

    private static Connection connection = connect();

    private static Connection connect() {
        try {
            return DriverManager.getConnection(DATABASE_URL);
        }
        catch (SQLException e) {
            throw new IllegalAccessError("Could not access database.");
        }
    }

    private static Connection getConnection() {
        return connection;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static List<?>[] executeQuery(String query, List<?>... list) throws IllegalArgumentException {
        try (
            Statement stmt  = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);
        ) {
            while (rs.next()) {
                for (int i = 1; i < list.length + 1; i++) {
                    ((List) list[i - 1]).add(rs.getObject(i));
                }
            }

            return list;
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
            Connection conn = getConnection();
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
                System.out.println("-- Insertion complete --");
                pstmt.executeBatch(); // final batch
                conn.commit(); // commit transaction
            }
        }
        catch (SQLException e) {
            throw new IllegalArgumentException("Error on inserting in the table \"%s\"".formatted(tableName), e);
        }
    }

    public static void createTable(String tableName, String[] headers, String[] types) throws IllegalArgumentException {
        try (Statement stmt = optimizeDatabaseForBulkInsert(getConnection()).createStatement()) {
            StringBuilder bld = new StringBuilder();
            String createTableSQL = "CREATE TABLE IF NOT EXISTS " + tableName + "(";
            bld.append(createTableSQL);

            for (int i = 0; i < headers.length; i++) {
                bld.append(headers[i] + " " + types[i]);
                if (i < headers.length-1) {
                    bld.append(", ");
                }
            }

            bld.append(");");
            System.out.println("Creating table " + tableName);
            stmt.execute(bld.toString());
            System.out.println("-- Table creation successful --");
        }
        catch (SQLException e) {
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

    public static void createDatabase() throws IOException {
        TxtToSQLite.txtToSQLite("resources/gtfs");
    }

    public static Map<Integer, BusStop> getBusStops() {
        Map<Integer, BusStop> busStopMap = new HashMap<>();
        List<?>[] attributes = executeQuery("select stop_id, stop_lat, stop_lon, stop_name\r\n" + //
            "from stops;\r\n", new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>());

        for (int i = 0; i < attributes[0].size(); i++) {
            try {
                Integer stopId = Integer.parseInt((String) attributes[0].get(i));
                BusStop busStop = new BusStop(Double.parseDouble((String) attributes[1].get(i)), Double.parseDouble((String) attributes[2].get(i)), (String) attributes[3].get(i));

                busStopMap.put(stopId, busStop);
            }
            catch (NumberFormatException e) {
                ExceptionManager.warn(e);
            }
        }

        return busStopMap;
    }

    public static Map<Integer, Trip> getTrips() {
        Map<Integer, Trip> busStopMap = new HashMap<>();
        List<?>[] attributes = executeQuery("select trip_id, shape_id, route_id, trip_headsign\r\n" + //
            "from trips;\r\n", new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>());

        for (int i = 0; i < attributes[0].size(); i++) {
            try {
                int tripId = Integer.parseInt((String) attributes[0].get(i));
                Trip trip = new Trip(tripId, Integer.parseInt((String) attributes[1].get(i)), Integer.parseInt((String) attributes[2].get(i)), (String) attributes[3].get(i));

                busStopMap.put(tripId, trip);
            }
            catch (NumberFormatException e) {
                ExceptionManager.warn(e);
            }
        }

        return busStopMap;
    }

    public static Map<Integer, Shape> getShapes() {
        Map<Integer, Shape> shapeMap = new HashMap<>();
        List<?>[] attributes = executeQuery("select shape_pt_sequence, shape_pt_lat, shape_pt_lon\r\n" + //
            "from shapes;\r\n", new ArrayList<Double>(), new ArrayList<Double>(), new ArrayList<Double>());

        int previousShapeId;

        for (int i = 0; i < attributes[0].size();) {
            try {
                int shapeId = Integer.parseInt((String) attributes[0].get(i));
                previousShapeId = shapeId;
                List<Location> locations = new ArrayList<>();

                while (previousShapeId == shapeId && i < attributes[0].size()) {
                    locations.add(new Location(Double.parseDouble((String) attributes[1].get(i)),
                                               Double.parseDouble((String) attributes[2].get(i))));
                    i += 1;
                    shapeId = Integer.parseInt((String) attributes[0].get(i));
                }

                Shape shape = new Shape(null, 0, Color.gray, locations.toArray(Location[]::new));

                shapeMap.put(shapeId, shape);
            }
            catch (NumberFormatException e) {
                ExceptionManager.warn(e);
            }
        }

        return shapeMap;
    }

    public static Graph<BusStop> loadGraph() {
        Graph<BusStop> graph = new AdjacencyListGraph<>();
        Map<Integer, BusStop> busStopMap = getBusStops();
        Map<Integer, Trip> tripMap = getTrips();
        //Map<Integer, Shape> shapeMap = getShapes();

        List<?>[] attributes = executeQuery("select trip_id, stop_id, arrival_time\r\n" + //
            "from stop_times ORDER BY trip_id;\r\n", new ArrayList<Double>(), new ArrayList<Double>(), new ArrayList<Double>());

        int previousTripId = -1;
        int previousTime = 0;
        BusStop previousBusStop = null;

        for (int i = 0; i < attributes[0].size(); i++) {
            int tripId = Integer.parseInt((String) attributes[0].get(i));
            BusStop busStop = busStopMap.get(Integer.parseInt((String) attributes[1].get(i)));
            String[] parts = ((String) attributes[2].get(i)).split(":");
            int time = 3600 * Integer.parseInt(parts[0]) + 60 * Integer.parseInt(parts[1]) + Integer.parseInt(parts[2]);

            if (!graph.containsVertex(busStop))
                graph.addVertex(busStop);

            if (tripId == previousTripId) {
                EdgeNode<BusStop> shape = new EdgeNode<>(busStop, time - previousTime);

                // if (tripMap.containsKey(tripId)) {
                //     shape = shapeMap.get(tripMap.get(tripId).getShapeId());
                //     shape.setElement(busStop);
                //     shape.setWeight(time - previousTime);
                // }

                graph.addEdge(shape, previousBusStop);
            }

            previousTripId = tripId;
            previousBusStop = busStop;
            previousTime = time;
        }

        System.out.println("Generated graph.");

        return graph;
    }
}
