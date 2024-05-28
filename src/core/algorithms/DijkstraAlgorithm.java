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
import core.models.Time;
import core.models.Trip;
import core.models.transport.Transport;
import ui.map.geometry.GeographicLine;
import ui.map.geometry.factories.LineFactory;

public class DijkstraAlgorithm {
    private DijkstraAlgorithm() {}

    // Boy do I hope java has objInserting.equals(objInList) instead of the other way around. Why?
    public static <T extends Point2D> Transport shortestPath(Graph<T> graph, T source, T end, Time startTime) throws IllegalArgumentException {
        if (source.equals(end))
            throw new IllegalArgumentException("Start is destination");

        Map<T, List<T>> paths = new HashMap<>();
        Map<T, List<Trip>> transfers = new HashMap<>();
        Map<T, Time> times = new HashMap<>();
        Map<T, Integer> weights = new HashMap<>();
        PriorityQueue<T> unsettled = new PriorityQueue<>((a, b) -> weights.get(b).compareTo(weights.get(a)));
        Set<T> settled = new HashSet<>();

        weights.put(source, 0);

        // Create an edge with departing time and source
        unsettled.add(source);
        times.put(source, startTime);
        transfers.put(source, new LinkedList<>());
        paths.computeIfAbsent(source, v -> new LinkedList<>()).add(source);

        while (!unsettled.isEmpty()) {
            // Removing the minimum distance node from the priority process
            T vertex = unsettled.poll();
            Time time = times.get(vertex);
            List<T> path = paths.get(vertex);
            List<Trip> trips = transfers.get(vertex);
            Trip trip = trips.isEmpty() ? null : trips.get(trips.size() - 1);

            if (vertex.equals(end))
                return Transport.of(toGeographicLine(paths.get(vertex)), times.get(vertex).minus(startTime), transfers.get(vertex));

            if (!settled.add(vertex))
                continue; // Skip processing if already settled

            // Visit all adjacent vertices of the vertex
            for (EdgeNode<T> edge : graph.neighbors(vertex)) {
                T adjacent = edge.getElement();
                Trip transfer = Trip.empty();
                int weight = edge.getWeight(time, transfer);

                if (weight == Integer.MAX_VALUE)
                    continue;

                if (!settled.contains(adjacent)) {
                    int newTime = weights.get(vertex) + weight;

                    if (newTime < weights.getOrDefault(adjacent, Integer.MAX_VALUE)) {
                        times.put(adjacent, time.add(weight));
                        weights.put(adjacent, newTime);
                        unsettled.add(adjacent);

                        // Add vertex to path
                        paths.computeIfAbsent(adjacent, v -> new LinkedList<>(path)).add(adjacent);

                        // Conditionally add trip to transfers
                        List<Trip> adjacentTransfers = transfers.computeIfAbsent(adjacent, v -> new LinkedList<>(trips));

                        if (!transfer.equals(trip)) {
                            adjacentTransfers.add(transfer);
                        }
                    }
                }
            }
        }

        throw new IllegalArgumentException("Could not find a route between these two bus stops.");
    }

    private static <T extends Point2D> GeographicLine toGeographicLine(List<T> shortestDistances) {
        return LineFactory.createGeographicArrowLine(shortestDistances.toArray(Location[]::new));
    }
}
