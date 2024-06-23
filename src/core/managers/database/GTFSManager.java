package core.managers.database;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import core.Constants;
import core.datastructures.graph.AdjacencyListGraph;
import core.datastructures.graph.Graph;
import core.managers.ExceptionManager;
import core.models.BusStop;
import core.models.Location;
import core.models.gtfs.Route;
import core.models.gtfs.Shape;
import core.models.gtfs.Time;
import core.models.gtfs.Trip;
import core.models.transport.Walking;

public class GTFSManager {
    private GTFSManager() {}

    private static Graph<Point2D> busGraph;
    private static Map<Integer, BusStop> busStopMap;
    private static Map<Integer, Trip> tripMap;
    private static Map<Integer, Route> routeMap;

    /**
     * This function generates a graph from the GTFS Dataset. It calculates the weight of a connection
     * from the arrivalTime of the destination stop minus the departing time of the departing stop.
     * As such, all departing times have to be stored in the edge. The weight will then work as a
     * duration to travel the trip from this bus stop to the next bus stop.
     *
     * @return a graph of the GTFS Dataset.
     */
    private static void loadBusGraph() {
        busGraph = new AdjacencyListGraph<>();

        // Order by tripId AND stop_sequence to insert the correct connections in the graph.
        // Take for example A -> B -> C, that cannot be A -> C -> B. This will not have correct weights because
        // the times are also not chronological.
        List<?>[] attributes = QueryManager.executeQuery("select trip_id, stop_id, arrival_time, departure_time\r\n" + //
            "from stop_times ORDER BY trip_id, stop_sequence LIMIT -1;\r\n", new ArrayList<Integer>(), new ArrayList<Integer>(), new ArrayList<Double>(), new ArrayList<Double>());

        int previousTripId = -1;
        Time departureTime = null;
        BusStop previousBusStop = null;

        for (int i = 0; i < attributes[0].size(); i++) {
            int tripId = (int) attributes[0].get(i);
            int stopId = (int) attributes[1].get(i);
            BusStop busStop = GTFSManager.getBusStop(stopId);
            Trip trip = Optional.ofNullable(GTFSManager.getTrip(tripId)).orElse(Trip.empty());
            Route route = Optional.ofNullable(GTFSManager.getRoute(trip.getRouteId())).orElse(Route.empty());
            Time arrivalTime = Time.of((String) attributes[2].get(i));

            busStop.addRoute(route);

            if (stopId == 2578363)
                System.out.println();

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

        //addWalkEdges(busGraph);

        System.out.println("Generated graph.");
    }

    public static void addWalkEdges(Graph<Point2D> graph) {
        for (Point2D vertex : graph) {
            for (Point2D unconnectedVertex : graph) {
                Time travelTime = new Walking((Location) vertex, (Location) unconnectedVertex).getTravelTime();
                if (travelTime.toMinutes() < Constants.Map.WALKING_MAX_TIME) {
                    graph.addEdge(vertex, unconnectedVertex, travelTime.toSeconds());
                }
            }
        }
    }

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

    public static Trip getTrip(int id) {
        return getTripMap().get(id);
    }

    public static Route getRoute(int id) {
        return getRouteMap().get(id);
    }

    public static BusStop getBusStop(int stopId) {
        return getBusStopMap().get(stopId);
    }

    public static Graph<Point2D> getBusGraph() {
        if (busGraph == null)
            loadBusGraph();

        return busGraph;
    }

    private static Map<Integer, Trip> getTrips() {
        Map<Integer, Trip> map = new HashMap<>();
        List<?>[] attributes = QueryManager.executeQuery("select trip_id, shape_id, route_id, trip_headsign\r\n" + //
            "from trips;\r\n", new ArrayList<Integer>(), new ArrayList<Integer>(), new ArrayList<Integer>(), new ArrayList<String>());

        for (int i = 0; i < attributes[0].size(); i++) {
            try {
                int id = (int) attributes[0].get(i);
                Trip trip = new Trip(id, (int) attributes[1].get(i), (int) attributes[2].get(i), (String) attributes[3].get(i));

                map.put(id, trip);
            }
            catch (ClassCastException e) {
                ExceptionManager.warn(new IllegalStateException("Could not parse row from trips table", e));
            }
        }

        return map;
    }

    private static Map<Integer, Route> getRoutes() {
        Map<Integer, Route> map = new HashMap<>();
        List<?>[] attributes = QueryManager.executeQuery("select route_id, route_long_name, route_color\r\n" + //
            "from routes;\r\n", new ArrayList<Integer>(), new ArrayList<String>(), new ArrayList<String>());

        for (int i = 0; i < attributes[0].size(); i++) {
            try {
                int id = (int) attributes[0].get(i);
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
        List<?>[] attributes = QueryManager.executeQuery("select shape_pt_lat, shape_pt_lon\r\n" + //
            "from shapes\r\n" + //
            "where shape_id = '%s' ORDER BY shape_pt_sequence".formatted(shapeId), new ArrayList<Double>(), new ArrayList<Double>());

        try {
            List<Location> locations = new ArrayList<>();

            for (int i = 0; i < attributes[0].size(); i++) {
                locations.add(new Location((double) attributes[0].get(i),
                                           (double) attributes[1].get(i)));
            }

            return new Shape(shapeId, Color.gray, locations.toArray(Location[]::new));
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException("Could not make a shape from shape_id '%s'".formatted(shapeId), e);
        }
        catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Could find a shape with shape_id '%s'".formatted(shapeId));
        }
    }

    private static Map<Integer, BusStop> getBusStops() {
        Map<Integer, BusStop> map = new HashMap<>();
        List<?>[] attributes = QueryManager.executeQuery("select stop_id, stop_lat, stop_lon, stop_name\r\n" + //
            "from stops;\r\n", new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>());

        for (int i = 0; i < attributes[0].size(); i++) {
            try {
                Integer id = (int) attributes[0].get(i);
                BusStop busStop = new BusStop(id, (double) attributes[1].get(i), (double) attributes[2].get(i), (String) attributes[3].get(i));

                map.put(id, busStop);
            }
            catch (ClassCastException e) {
                ExceptionManager.warn(new IllegalStateException("Could not parse row from stops table", e));
            }
        }

        return map;
    }
}
