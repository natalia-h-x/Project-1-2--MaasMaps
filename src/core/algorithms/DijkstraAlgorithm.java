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
import core.models.Time;
import core.models.Trip;
import core.models.transport.Transport;


public class DijkstraAlgorithm extends PathStrategy{

    // Boy do I hope java has objInserting.equals(objInList) instead of the other way around. Why?
    public Transport shortestPath(Graph<Point2D> graph, Point2D source, Point2D end, Time startTime) throws IllegalArgumentException {
        if (source.equals(end))
            throw new IllegalArgumentException("Start is destination");

        Map<Point2D, List<Point2D>> paths = new HashMap<>();
        Map<Point2D, List<Trip>> transfers = new HashMap<>();
        Map<Point2D, List<Time>> times = new HashMap<>();
        Map<Point2D, Integer> weights = new HashMap<>();
        PriorityQueue<Point2D> unsettled = new PriorityQueue<>((a, b) -> weights.get(b).compareTo(weights.get(a)));
        Set<Point2D> settled = new HashSet<>();

        weights.put(source, startTime.toSeconds());

        // Create an edge with departing time and source
        unsettled.add(source);
        transfers.put(source, new LinkedList<>());
        times.computeIfAbsent(source, v -> new LinkedList<>()).add(startTime);
        paths.computeIfAbsent(source, v -> new LinkedList<>()).add(source);

        while (!unsettled.isEmpty()) {
            // Removing the minimum distance node from the priority process
            Point2D vertex = unsettled.poll();
            List<Time> time = times.get(vertex);
            List<Point2D> path = paths.get(vertex);
            List<Trip> trips = transfers.get(vertex);
            Trip trip = trips.isEmpty() ? null : trips.get(trips.size() - 1);
            int currentWeight = weights.get(vertex);

            if (vertex.equals(end))
                return Transport.of(toGeographicLine(path, time), Time.of(currentWeight).minus(startTime), transfers.get(vertex));

            if (!settled.add(vertex))
                continue; // Skip processing if already settled

            // Visit all adjacent vertices of the vertex
            for (EdgeNode<Point2D> edge : graph.neighbors(vertex)) {
                Point2D adjacent = edge.getElement();
                Trip transfer = Trip.empty();
                int weight = edge.getWeight(currentWeight, transfer);

                if (weight == Integer.MAX_VALUE)
                    continue;

                if (!settled.contains(adjacent)) {
                    int newTime = currentWeight + weight;

                    if (newTime < weights.getOrDefault(adjacent, Integer.MAX_VALUE)) {
                        weights.put(adjacent, newTime);
                        unsettled.add(adjacent);

                        // Add vertex to path
                        times.computeIfAbsent(adjacent, v -> new LinkedList<>(time)).add(Time.of(weight));
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


}
