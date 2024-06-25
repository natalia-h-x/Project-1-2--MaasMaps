package core.managers.database.gtfs;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import core.Constants;
import core.datastructures.graph.AdjacencyListGraph;
import core.datastructures.graph.Graph;
import core.managers.database.QueryManager;
import core.models.BusStop;
import core.models.Location;
import core.models.gtfs.Route;
import core.models.gtfs.Time;
import core.models.gtfs.Trip;
import core.models.transport.Walking;

public class GTFSManager {
    private GTFSManager() {}

    private static Graph<Point2D> busGraph;

    public static Graph<Point2D> getBusGraph() {
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
    private static void loadBusGraph() {
        busGraph = new AdjacencyListGraph<>();

        // Order by tripId AND stop_sequence to insert the correct connections in the graph.
        // Take for example A -> B -> C, that cannot be A -> C -> B. This will not have correct weights because
        // the times are also not chronological.
        List<?>[] attributes = QueryManager.executeQuery(
            "select trip_id, stop_id, arrival_time, departure_time\r\n" + //
            "from stop_times ORDER BY trip_id, stop_sequence LIMIT -1;\r\n", new ArrayList<Integer>(), new ArrayList<Integer>(), new ArrayList<Double>(), new ArrayList<Double>());

        int previousTripId = -1;
        Time departureTime = null;
        BusStop previousBusStop = null;

        for (int i = 0; i < attributes[0].size(); i++) {
            int tripId = (int) attributes[0].get(i);
            int stopId = (int) attributes[1].get(i);
            BusStop busStop = BusStopManager.getBusStop(stopId);
            Trip trip = Optional.ofNullable(TripManager.getTrip(tripId)).orElse(Trip.empty());
            Route route = Optional.ofNullable(RouteManager.getRoute(trip.getRouteId())).orElse(Route.empty());
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

        addWalkEdges(busGraph);

        System.out.println("Generated graph.");
    }

    public static void addWalkEdges(Graph<Point2D> graph) {
        for (Point2D vertex : graph) {
            for (Point2D unconnectedVertex : graph) {
                if (vertex == unconnectedVertex)
                    continue;

                Time travelTime = new Walking((Location) vertex, (Location) unconnectedVertex).getTravelTime();

                if (travelTime.toMinutes() < Constants.Map.WALKING_MAX_TIME) {
                    graph.addEdge(vertex, unconnectedVertex, travelTime.toSeconds());
                }
            }
        }
    }
}
