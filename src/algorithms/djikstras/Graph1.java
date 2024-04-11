package algorithms.djikstras;
import models.Location;

import java.util.*;

public class Graph {
    private Map<Location, List<Edge>> adjacencyList;

    public Graph() {
        adjacencyList = new HashMap<>();
    }

    public void addEdge(Location source, Location destination) {
        adjacencyList.putIfAbsent(source, new ArrayList<>());
        adjacencyList.putIfAbsent(destination, new ArrayList<>());
        double weight = source.distanceTo(destination);
        adjacencyList.get(source).add(new Edge(destination, weight));
    }


}
