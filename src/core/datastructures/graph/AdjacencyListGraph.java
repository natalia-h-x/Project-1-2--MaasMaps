package core.datastructures.graph;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import core.models.gtfs.Time;
import core.models.gtfs.Trip;
import lombok.Data;

@Data
public class AdjacencyListGraph<T> implements Graph<T> {
    private HashMap<T, List<Edge<T>>> vertices;

    public AdjacencyListGraph() {
        vertices = new HashMap<>();
    }

    @Override
    public List<Edge<T>> neighbors(T x) {
        if (!containsVertex(x)) {
            throw new IllegalArgumentException("The vertex " + x + " is not in the Graph.");
        }
        // return new linked list to avoid possible conflicts
        return new LinkedList<>(vertices.get(x));
    }

    @Override
    public void addVertex(T x) {
        if (containsVertex(x)) {
            throw new IllegalArgumentException("The vertex " + x + " is already in the Graph.");
        }

        vertices.put(x, new LinkedList<>());
    }

    @Override
    public void removeVertex(T x) {
        // check if the vertex exists in the graph
        if (!containsVertex(x)) {
            throw new IllegalArgumentException("The vertex " + x + " is not in the Graph.");
        }

        // Remove all issuing edges from vertex x
        vertices.remove(x);

        // Iterate all vertices in the graph
        for (T vertex : vertices.keySet()) {
            // remove the edge if it points to x
            vertices.get(vertex).removeIf(edge -> edge.getElement().equals(x));
        }
    }

    public boolean containsVertex(T x) {
        return vertices.containsKey(x);
    }

    @Override
    public void addEdge(T x, T y, int weight) {
        addEdge(x, y, weight, null, null);
    }

    @Override
    public void addEdge(T x, T y, int weight, Trip trip, Time departureTime) {
        if (!containsVertex(x) || !containsVertex(y)) {
            throw new IllegalArgumentException("One or both vertices are not in the Graph.");
        }

        List<Edge<T>> edges = vertices.get(x);

        if (departureTime != null && trip != null) {
            for (Edge<T> edge : edges) {
                if (edge.getElement().equals(y) && edge instanceof BusEdge<T> busEdge) {
                    busEdge.addTrip(trip, departureTime, weight);

                    return;
                }
            }

            BusEdge<T> edge = new BusEdge<>(y, weight);

            edge.addTrip(trip, departureTime, weight);
            edges.add(edge);

            return;
        }

        edges.add(new WalkingEdge<>(y, weight));
    }

    @Override
    public void removeEdge(T x, T y) {
        if (!containsVertex(x)) {
            throw new IllegalArgumentException("The vertex " + x + " is not in the Graph.");
        }
        vertices.get(x).removeIf(edge -> edge.getElement().equals(y));
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (T vertex : vertices.keySet()) {
            List<Edge<T>> neighbours = vertices.get(vertex);
            result.append(vertex).append(" neighbors: -> ");
            for (Edge<T> edge : neighbours) {
                result.append(edge.getElement()).append(" (").append(edge.getWeight()).append(") -> ");
            }
            result.append("null\n");
        }
        return result.toString();
    }

    @Override
    public List<T> getVertices() {
        return new LinkedList<>(vertices.keySet());
    }

    @Override
    public Graph<T> clone() {
        AdjacencyListGraph<T> copy = new AdjacencyListGraph<>();
        for (T vertex : vertices.keySet()) {
            if (!copy.containsVertex(vertex))
                copy.addVertex(vertex);
            for (Edge<T> edge : vertices.get(vertex)) {
                if (!copy.containsVertex(edge.getElement()))
                    copy.addVertex(edge.getElement());
                copy.addEdge(vertex, edge.getElement(), edge.getWeight());
            }
        }
        return copy;
    }

    @Override
    public Iterator<T> iterator() {
        Iterator<T> iterator = new GraphIterator<T>(vertices.keySet());
        return iterator;
    }
}
