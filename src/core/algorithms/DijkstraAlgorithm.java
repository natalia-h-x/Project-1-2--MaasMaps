package core.algorithms;

import core.algorithms.datastructures.AdjacencyListGraph;
import core.algorithms.datastructures.EdgeNode;
import core.models.Location;
import ui.map.geometry.Line;

import java.util.*;

public class DijkstraAlgorithm {
    private DijkstraAlgorithm() {}

    public static <T> Map<T, Integer> shortestPath(AdjacencyListGraph<T> graph, T source) {
        Map<T, Integer> shortestDistances = new HashMap<>();
        Set<T> settledNodes = new HashSet<>();
        PriorityQueue<EdgeNode<T>> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(EdgeNode::getWeight));

        // give infinity to flag unreachable
        for (T vertex : graph.getVertecesList()) {
            shortestDistances.put(vertex, Integer.MAX_VALUE);
        }
        shortestDistances.put(source, 0);
        priorityQueue.add(new EdgeNode<>(source, 0));

        while (!priorityQueue.isEmpty()) {
            // Removing the minimum distance node from the priority process
            T currentVertex = priorityQueue.poll().getElement();

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
                        // update with new chained distance
                        priorityQueue.add(new EdgeNode<>(adjacentVertex, newDist));
                    }
                }
            }
        }

        return shortestDistances;
    }

    public static Line toLine(Map<Location, Integer> shortestDistances) {
        return new Line(shortestDistances.keySet().toArray(Location[]::new));
    }
}

