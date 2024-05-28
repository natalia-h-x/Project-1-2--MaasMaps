package core.algorithms.datastructures;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import core.models.Time;
import core.models.Trip;

public class AdjacencyListGraph<T> implements Graph<T> {
    private HashMap<T, List<EdgeNode<T>>> vertices;

    public AdjacencyListGraph() {
        vertices = new HashMap<>();
    }

    @Override
    public List<EdgeNode<T>> neighbors(T x) {
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
    public void addEdge(T x, T y, int weight, Trip trip, Time time) {
        if (!containsVertex(x) || !containsVertex(y)) {
            throw new IllegalArgumentException("One or both vertices are not in the Graph.");
        }

        List<EdgeNode<T>> edges = vertices.get(x);

        if (time != null && trip != null)
            for (EdgeNode<T> edge : edges) {
                if (edge.getElement().equals(y)) {
                    edge.addTrip(trip, time);

                    return;
                }
            }

        EdgeNode<T> edge = new EdgeNode<>(y, weight);

        if (time != null && trip != null)
            edge.addTrip(trip, time);

        edges.add(edge);
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
            List<EdgeNode<T>> neighbours = vertices.get(vertex);
            result.append(vertex).append(" neighbors: -> ");
            for (EdgeNode<T> edge : neighbours) {
                result.append(edge.getElement()).append(" (").append(edge.getWeight()).append(") -> ");
            }
            result.append("null\n");
        }
        return result.toString();
    }

    @Override
    public List<T> getVertecesList() {
        return new LinkedList<>(vertices.keySet());
    }

    @Override
    public Graph<T> clone() {
        AdjacencyListGraph<T> copy = new AdjacencyListGraph<>();
        for (T vertex : vertices.keySet()) {
            if (!copy.containsVertex(vertex))
                copy.addVertex(vertex);
            for (EdgeNode<T> edge : vertices.get(vertex)) {
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

