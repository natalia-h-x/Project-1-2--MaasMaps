package core.algorithms;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import core.algorithms.datastructures.EdgeNode;
import core.algorithms.datastructures.Graph;
import core.models.Location;
import core.models.Route;
import core.models.Time;
import ui.map.geometry.GeographicLine;
import ui.map.geometry.factories.LineFactory;

public class DijkstraAlgorithm {
    private DijkstraAlgorithm() {}

    // Boy do I hope java has objInserting.equals(objInList) instead of the other way around. Why?
    public static <T extends Point2D> GeographicLine shortestPath(Graph<T> graph, T source, T end, Time startTime, Time duration) throws IllegalArgumentException {
        if (source.equals(end))
            throw new IllegalArgumentException("Start is destination");

        EdgeNode.pleaseForgiveMe = false;

        Map<T, List<T>> paths = new HashMap<>();
        Map<T, Time> times = new HashMap<>();
        Map<T, Route> trips = new HashMap<>();
        Map<T, Double> shortestDistances = new HashMap<>();
        PriorityQueue<T> unsettledNodes = new PriorityQueue<>((a, b) -> shortestDistances.get(b).compareTo(shortestDistances.get(a)));
        Set<T> settledNodes = new HashSet<>();

        // Give infinity to flag unreachable
        for (T vertex : graph.getVertecesList()) {
            shortestDistances.put(vertex, Double.POSITIVE_INFINITY);
        }

        shortestDistances.put(source, 0.0);

        // Create an edge with departing time and source
        unsettledNodes.add(source);
        times.put(source, startTime);
        LinkedList<T> originPath = new LinkedList<>();
        originPath.add(source);
        paths.put(source, originPath);

        while (!unsettledNodes.isEmpty()) {
            // Removing the minimum distance node from the priority process
            T currentVertex = unsettledNodes.poll();
            Time currentTime = times.get(currentVertex);
            Route currentRoute = trips.get(currentVertex);

            if (currentVertex.equals(end)) {
                duration.update(times.get(currentVertex).minus(startTime).toSeconds());
                return toGeographicLine(paths.get(currentVertex));
            }

            if (!settledNodes.add(currentVertex))
                continue; // Skip processing if already settled

            // Visit all adjacent vertices of the currentVertex
            List<EdgeNode<T>> adjacentNodes = graph.neighbors(currentVertex);

            for (EdgeNode<T> edge : adjacentNodes) {
                T adjacentVertex = edge.getElement();
                Route edgeTrip = Route.empty();
                int edgeWeight = edge.getWeight(currentTime, currentRoute, edgeTrip);

                if (edgeWeight == Integer.MAX_VALUE)
                    continue;

                if (!settledNodes.contains(adjacentVertex)) {
                    double newDist = shortestDistances.get(currentVertex) + edgeWeight;

                    if (newDist < shortestDistances.get(adjacentVertex)) {
                        shortestDistances.put(adjacentVertex, newDist);
                        times.put(adjacentVertex, currentTime.add(edgeWeight));
                        trips.put(adjacentVertex, edgeTrip);
                        unsettledNodes.add(adjacentVertex);

                        // Add vertex to path and then to paths
                        LinkedList<T> path = new LinkedList<>(paths.get(currentVertex));
                        path.add(adjacentVertex);
                        paths.put(adjacentVertex, path);
                    }
                }
            }

            EdgeNode.pleaseForgiveMe = true;
        }

        duration.update(Integer.MAX_VALUE);
        return toGeographicLine(originPath);
        //throw new IllegalArgumentException("Could not find a route between these two bus stops.");
    }

    private static <T extends Point2D> GeographicLine toGeographicLine(List<T> shortestDistances) {
        return LineFactory.createGeographicArrowLine(shortestDistances.toArray(Location[]::new));
    }
}
