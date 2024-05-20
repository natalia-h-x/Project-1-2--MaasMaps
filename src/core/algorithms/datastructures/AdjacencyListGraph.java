package core.algorithms.datastructures;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class AdjacencyListGraph<T> implements Graph<T> {
    private HashMap<T, List<EdgeNode<T>>> vertices;

    public AdjacencyListGraph() {
        vertices = new HashMap<>();
    }

    @Override
    public List<EdgeNode<T>> neighbors(T x) {
        if (!vertices.containsKey(x)) {
            throw new IllegalArgumentException("The vertex " + x + " is not in the Graph.");
        }
        // return new linked list to avoid possible conflicts
        return new LinkedList<>(vertices.get(x));
    }

    @Override
    public void addVertex(T x) {
        if (vertices.containsKey(x)) {
            throw new IllegalArgumentException("The vertex " + x + " is already in the Graph.");
        }
        vertices.put(x, new LinkedList<>());
    }

    @Override
    public void removeVertex(T x) {
        // check if the vertex exists in the graph
        if (!vertices.containsKey(x)) {
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
        if (!vertices.containsKey(x) || !vertices.containsKey(y)) {
            throw new IllegalArgumentException("One or both vertices are not in the Graph.");
        }

        List<EdgeNode<T>> adjList = vertices.get(x);
        adjList.add(new EdgeNode<>(y, weight));
    }

    @Override
    public void addEdge(EdgeNode<T> edge, T x) {
        if (!vertices.containsKey(x) || !vertices.containsKey(edge.getElement())) {
            throw new IllegalArgumentException("One or both vertices are not in the Graph.");
        }

        vertices.get(x).add(edge);
    }

    @Override
    public void removeEdge(T x, T y) {
        if (!vertices.containsKey(x)) {
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
            copy.addVertex(vertex);
            for (EdgeNode<T> edge : vertices.get(vertex)) {
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

