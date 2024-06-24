package core.algorithms;

import java.awt.geom.Point2D;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import core.datastructures.TriMonoid;
import core.datastructures.graph.Edge;
import core.datastructures.graph.Graph;
import core.datastructures.graph.Weight;
import core.datastructures.graph.BusWeight;
import core.models.BusStop;
import core.models.gtfs.Time;
import core.models.gtfs.Trip;
import core.models.transport.Bus;
import core.models.transport.Transport;

public class DijkstraAlgorithm<T extends Point2D> extends PathStrategy<T> {
    // Boy do I hope java has objInserting.equals(objInList) instead of the other way around. Why?
    public Transport[] shortestPath(Graph<T> graph, T source, T end, Time startTime) throws IllegalArgumentException {
        return shortestPath(graph, source, end, startTime, (a, b) -> 0);
    }

    public Transport[] shortestPath(Graph<T> graph, T source, T end, Time startTime, Comparator<? super T> heuristic) throws IllegalArgumentException {
        if (source.equals(end))
            throw new IllegalArgumentException("start is destination");

        Map<T, TriMonoid<Transport, T, Trip, Edge<T>>> pathMonoids = new HashMap<>();
        Map<T, Integer> weights = new HashMap<>();
        PriorityQueue<T> unsettled = new PriorityQueue<>((a, b) -> weights.get(b).compareTo(weights.get(a)) + heuristic.compare(a, b));
        Set<T> settled = new HashSet<>();

        weights.put(source, startTime.toSeconds());

        // Create an edge with departing time and source
        unsettled.add(source);
        pathMonoids.computeIfAbsent(source, v -> new TriMonoid<>(Edge::asTransport));

        while (!unsettled.isEmpty()) {
            // Removing the minimum distance node from the priority process
            T vertex = unsettled.poll();
            TriMonoid<Transport, T, Trip, Edge<T>> pathMonoid = pathMonoids.get(vertex);
            int currentWeight = weights.get(vertex);

            if (vertex.equals(end))
                return pathMonoid.toArray(Transport[]::new);

            if (!settled.add(vertex))
                continue; // Skip processing if already settled

            // Visit all adjacent vertices of the vertex
            for (Edge<T> edge : graph.neighbors(vertex)) {
                T adjacent = edge.getElement();
                Trip transfer = Trip.empty();
                Weight weight = edge.getWeight(currentWeight, transfer);

                if (weight.waitTime().toHours() > 2)
                    System.out.println();

                if (!weight.isReachable())
                    continue;

                if (!settled.contains(adjacent)) {
                    int newTime = currentWeight + weight.getWeight();

                    if (newTime < weights.getOrDefault(adjacent, Integer.MAX_VALUE)) {
                        weights.put(adjacent, newTime);
                        unsettled.add(adjacent);

                        // Add vertex to path
                        Transport element = pathMonoids.computeIfAbsent(adjacent, v -> new TriMonoid<>(pathMonoid)).add(edge, vertex, transfer).getElement();
                        element.setTime(weight.weightTime());
                        element.setWaitTime(weight.waitTime());

                        if (adjacent instanceof BusStop busStop && element instanceof Bus bus)
                            bus.setBusStop(busStop);
                    }
                }
            }
        }

        throw new IllegalArgumentException("Could not find a route between these two bus stops.");
    }

    @Override
    public String toString() {
        return "Dijkstra's";
    }
}
