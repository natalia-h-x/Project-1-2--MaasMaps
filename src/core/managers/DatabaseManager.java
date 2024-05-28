package core.managers;

import static core.Constants.Paths.DATABASE_PATH;

import java.awt.Color;
import java.awt.geom.Point2D;
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
import java.util.Optional;

import core.algorithms.datastructures.AdjacencyListGraph;
import core.algorithms.datastructures.Graph;
import core.models.BusStop;
import core.models.Location;
import core.models.Route;
import core.models.Shape;
import core.models.Time;
import core.models.Trip;
import tools.generator.sqlite.TxtToSQLite;

public class DatabaseManager {
    private DatabaseManager() {}

    private static final Connection connection = connect();

    private static Connection connect() {
        try {
            return optimizeDatabaseForBulkInsert(DriverManager.getConnection(DATABASE_PATH));
        }
        catch (SQLException e) {
            throw new IllegalArgumentException("Could not access database.", e);
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
            }
        }
        catch (SQLException e) {
            throw new IllegalArgumentException("Error on inserting in the table \"%s\"".formatted(tableName), e);
        }
    }

    public static void createTable(String tableName, String[] headers, String[] types) throws IllegalArgumentException {
        try (Statement stmt = getConnection().createStatement()) {
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
            stmt.execute(bld.toString());
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
        Map<Integer, BusStop> map = new HashMap<>();
        List<?>[] attributes = executeQuery("select stop_id, stop_lat, stop_lon, stop_name\r\n" + //
            "from stops;\r\n", new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>());

        for (int i = 0; i < attributes[0].size(); i++) {
            try {
                Integer id = Integer.parseInt((String) attributes[0].get(i));
                BusStop busStop = new BusStop(id, Double.parseDouble((String) attributes[1].get(i)), Double.parseDouble((String) attributes[2].get(i)), (String) attributes[3].get(i));

                map.put(id, busStop);
            }
            catch (NumberFormatException e) {
                ExceptionManager.warn(new IllegalStateException("Could not parse row from stops table", e));
            }
        }

        return map;
    }

    public static Map<Integer, Trip> getTrips() {
        Map<Integer, Trip> map = new HashMap<>();
        List<?>[] attributes = executeQuery("select trip_id, shape_id, route_id, trip_headsign\r\n" + //
            "from trips;\r\n", new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>());

        for (int i = 0; i < attributes[0].size(); i++) {
            try {
                int id = Integer.parseInt((String) attributes[0].get(i));
                Trip trip = new Trip(id, Integer.parseInt((String) attributes[1].get(i)), Integer.parseInt((String) attributes[2].get(i)), (String) attributes[3].get(i));

                map.put(id, trip);
            }
            catch (NumberFormatException e) {
                ExceptionManager.warn(new IllegalStateException("Could not parse row from trips table", e));
            }
        }

        return map;
    }

    public static Map<Integer, Route> getRoutes() {
        Map<Integer, Route> map = new HashMap<>();
        List<?>[] attributes = executeQuery("select route_id, route_long_name, route_color\r\n" + //
            "from routes;\r\n", new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>());

        for (int i = 0; i < attributes[0].size(); i++) {
            try {
                int id = Integer.parseInt((String) attributes[0].get(i));
                Route trip = new Route(id, (String) attributes[1].get(i), Color.decode((String) attributes[2].get(i)));

                map.put(id, trip);
            }
            catch (NumberFormatException e) {
                ExceptionManager.warn(new IllegalStateException("Could not parse row from trips table", e));
            }
        }

        return map;
    }

    public static Shape getShape(int shapeId) {
        List<?>[] attributes = executeQuery("select shape_pt_sequence, shape_pt_lat, shape_pt_lon\r\n" + //
            "from shapes\r\n" + //
            "where shape_id = '%s'".formatted(shapeId), new ArrayList<Double>(), new ArrayList<Double>(), new ArrayList<Double>());

        try {
            List<Location> locations = new ArrayList<>();

            for (int i = 0; i < attributes[0].size(); i++) {
                locations.add(new Location(Double.parseDouble((String) attributes[1].get(i)),
                                           Double.parseDouble((String) attributes[2].get(i))));
            }

            return new Shape(Color.gray, locations.toArray(Location[]::new));
        }
        catch (NumberFormatException e) {
            throw new IllegalAccessError("Could not make a shape from shape_id '%s'".formatted(shapeId));
        }
        catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Could find a shape with shape_id '%s'".formatted(shapeId));
        }
    }

    private static Graph<Point2D> busGraph;
    private static Map<Integer, BusStop> busStopMap;
    private static Map<Integer, Trip> tripMap;
    private static Map<Integer, Route> routeMap;

    public static Map<Integer, BusStop> getBusStopMap() {
        if (busStopMap == null)
            busStopMap = getBusStops();

        return busStopMap;
    }

    public static Map<Integer, Trip> getTripMap() {
        if (tripMap == null)
            tripMap = getTrips();

        return tripMap;
    }

    public static Map<Integer, Route> getRouteMap() {
        if (routeMap == null)
            routeMap = getRoutes();

        return routeMap;
    }

    private static Trip getTrip(int id) {
        return getTripMap().get(id);
    }

    private static Route getRoute(int id) {
        return getRouteMap().get(id);
    }

    private static BusStop getBusStop(int stopId) {
        return getBusStopMap().get(stopId);
    }

    protected static Graph<Point2D> getBusGraph() {
        if (busGraph == null)
            loadBusGraph();

        return busGraph;
    }

    /**
     * This function generates a graph from the GTFS Dataset. It calculates the weight of a connection
     * from the arrivalTime of the destination stop minus the departing time of the departing stop.
     * As such, all departing times have to be stored in the edge. The weight will then work as a
     * duration to travel the trip from this bus stop to the next bus stop.
     *
     * @return a graph of the GTFS Dataset.
     */
    public static void loadBusGraph() {
        busGraph = new AdjacencyListGraph<>();

        // Order by tripId AND stop_sequence to insert the correct connections in the graph.
        // Take for example A -> B -> C, that cannot be A -> C -> B. This will not have correct weights because
        // the times are also not chonological.
        List<?>[] attributes = executeQuery("select trip_id, stop_id, arrival_time, departure_time\r\n" + //
            "from stop_times ORDER BY CAST(trip_id AS INT), CAST(stop_sequence AS INT);\r\n", new ArrayList<Double>(), new ArrayList<Double>(), new ArrayList<Double>(), new ArrayList<Double>());

        int previousTripId = -1;
        Time departureTime = null;
        BusStop previousBusStop = null;

        for (int i = 0; i < attributes[0].size(); i++) {
            int tripId = Integer.parseInt((String) attributes[0].get(i));
            int stopId = Integer.parseInt((String) attributes[1].get(i));
            BusStop busStop = getBusStop(stopId);
            Trip trip = Optional.ofNullable(getTrip(tripId)).orElse(Trip.empty());
            Route route = Optional.ofNullable(getRoute(trip.getRouteId())).orElse(Route.empty());
            Time arrivalTime = Time.of((String) attributes[2].get(i));

            busStop.addRoute(route);

            // We have this following if statement to check if there are at least two stops in a trip.
            // If there is only one, we do not need to connect / add vertices.
            if (tripId == previousTripId) {
                if (!busGraph.containsVertex(busStop))
                    busGraph.addVertex(busStop);

                if (!busGraph.containsVertex(Optional.ofNullable(previousBusStop).orElseThrow()))
                    busGraph.addVertex(previousBusStop);

                busGraph.addEdge(previousBusStop, busStop, arrivalTime.minus(Optional.ofNullable(departureTime).orElseThrow()).toSeconds(), trip, departureTime);
            }

            previousTripId = tripId;
            previousBusStop = busStop;
            departureTime = Time.of((String) attributes[3].get(i));
        }

        System.out.println("Generated graph.");
    }
}
