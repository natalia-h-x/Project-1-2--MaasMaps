package core.algorithms;

import core.algorithms.datastructures.AdjacencyListGraph;
import core.algorithms.datastructures.EdgeNode;
import core.models.GTFSTime;
import core.models.Location;
import ui.map.geometry.Line;

import java.util.*;

public class DijkstraAlgorithm {
    private DijkstraAlgorithm() {}

    public static <T> List<T> shortestPath(AdjacencyListGraph<T> graph, T source, T end, GTFSTime startTime) {
        Map<T, List<T>> paths = new HashMap<>();
        Map<T, Integer> shortestDistances = new HashMap<>();
        PriorityQueue<T> unsettledNodes = new PriorityQueue<>(Comparator.comparingInt(shortestDistances::get));
        Set<T> settledNodes = new HashSet<>();

        // Give infinity to flag unreachable
        for (T vertex : graph.getVertecesList()) {
            shortestDistances.put(vertex, Integer.MAX_VALUE);
        }

        shortestDistances.put(source, 0);

        // Create an edge with departing time and source
        LinkedList<GTFSTime> departureTimes = new LinkedList<>();
        departureTimes.add(startTime);
        unsettledNodes.add(source);

        while (!unsettledNodes.isEmpty()) {
            // Removing the minimum distance node from the priority process
            T currentVertex = unsettledNodes.poll();

            if (currentVertex.equals(end)) {
                return paths.get(currentVertex);
            }

            if (!settledNodes.add(currentVertex)) {
                continue; // Skip processing if already settled
            }

            // Visit all adjacent vertices of the currentVertex
            List<EdgeNode<T>> adjacentNodes = graph.neighbors(currentVertex);
            for (EdgeNode<T> edge : adjacentNodes) {
                T adjacentVertex = edge.getElement();
                int edgeWeight = edge.getWeight();

                if (!settledNodes.contains(adjacentVertex)) {
                    int newDist = shortestDistances.get(currentVertex) + edgeWeight;

                    if (newDist < shortestDistances.get(adjacentVertex)) {
                        shortestDistances.put(adjacentVertex, newDist);
                        unsettledNodes.add(adjacentVertex);

                        // Add vertex to path and then to paths
                        LinkedList<T> path = new LinkedList<>(paths.getOrDefault(paths, new LinkedList<>()));
                        path.add(adjacentVertex);
                        paths.put(adjacentVertex, path);
                    }
                }
            }
        }

        return new LinkedList<>();
    }

    public static Line toLine(List<Location> shortestDistances) {
        return new Line(shortestDistances.toArray(Location[]::new));
    }
}
