package core.algorithms;

import java.awt.geom.Point2D;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import core.Context;
import core.algorithms.datastructures.EdgeNode;
import core.algorithms.datastructures.Graph;
import core.models.GTFSTime;
import core.models.Location;
import core.models.transport.Route;
import ui.map.geometry.GeographicLine;
import ui.map.geometry.factories.LineFactory;

public class DijkstraAlgorithm {
    private DijkstraAlgorithm() {}

    public static <T extends Point2D> Route shortestPath(Graph<T> graph, T source, T end, GTFSTime startTime) {
        GTFSTime duration = GTFSTime.of(0);
        return toRoute(shortestPath(graph, source, end, startTime, duration), duration);
    }

    // Boy do I hope java has objInserting.equals(objInList) instead of the other way around. Why?
    public static <T> List<T> shortestPath(Graph<T> graph, T source, T end, GTFSTime startTime, GTFSTime endTime) {
        EdgeNode.pleaseForgiveMe = false;

        if (source.equals(end))
            throw new IllegalArgumentException("Start is destination");

        Map<T, List<T>> paths = new HashMap<>();
        Map<T, GTFSTime> times = new HashMap<>();
        Map<T, Integer> shortestDistances = new HashMap<>();
        PriorityQueue<T> unsettledNodes = new PriorityQueue<>((a, b) -> shortestDistances.get(b).compareTo(shortestDistances.get(a)));
        Set<T> settledNodes = new HashSet<>();

        // Give infinity to flag unreachable
        for (T vertex : graph.getVertecesList()) {
            shortestDistances.put(vertex, Integer.MAX_VALUE);
        }

        shortestDistances.put(source, 0);

        // Create an edge with departing time and source
        unsettledNodes.add(source);
        times.put(source, startTime);
        LinkedList<T> originPath = new LinkedList<>();
        originPath.add(source);
        paths.put(source, originPath);

        while (!unsettledNodes.isEmpty()) {
            // Removing the minimum distance node from the priority process
            T currentVertex = unsettledNodes.poll();
            GTFSTime currentTime = times.get(currentVertex);

            for (T l : unsettledNodes) {
                System.out.println(shortestDistances.get(l));
            }

            if (currentVertex.equals(end)) {
                endTime.update(shortestDistances.get(currentVertex));
                return paths.get(currentVertex);
            }

            if (!settledNodes.add(currentVertex)) {
                continue; // Skip processing if already settled
            }

            // Visit all adjacent vertices of the currentVertex
            List<EdgeNode<T>> adjacentNodes = graph.neighbors(currentVertex);

            for (EdgeNode<T> edge : adjacentNodes) {
                T adjacentVertex = edge.getElement();
                int edgeWeight = edge.getWeight(currentTime);

                if (edgeWeight == Integer.MAX_VALUE)
                    continue;

                if (!settledNodes.contains(adjacentVertex)) {
                    int newDist = shortestDistances.get(currentVertex) + edgeWeight;

                    if (newDist < shortestDistances.get(adjacentVertex)) {
                        shortestDistances.put(adjacentVertex, newDist);
                        times.put(adjacentVertex, currentTime.add(edgeWeight));
                        unsettledNodes.add(adjacentVertex);

                        // Add vertex to path and then to paths
                        LinkedList<T> path = new LinkedList<>(paths.get(currentVertex));
                        path.add(adjacentVertex);
                        paths.put(adjacentVertex, path);

                        // Context.getContext().getMap().addMapGraphics(LineFactory.createGeographicArrowLine((Location) currentVertex, (Location) adjacentVertex));
                        // Context.getContext().getMap().repaint();
                    }
                }
            }

            EdgeNode.pleaseForgiveMe = true;
        }

        return originPath;
    }

    private static <T extends Point2D> Route toRoute(List<T> shortestDistances, GTFSTime duration) {
        return Route.ofWalking(LineFactory.createGeographicArrowLine(shortestDistances.toArray(Location[]::new)), duration);
    }
}
