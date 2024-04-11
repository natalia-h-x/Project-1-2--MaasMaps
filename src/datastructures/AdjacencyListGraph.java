package datastructures;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class AdjacencyListGraph<T> implements Graph<T> {

    private HashMap<T, List<EdgeNode<T>>> vertices;

    public AdjacencyListGraph() {
        vertices = new HashMap<>();
    }

    @Override
    public List<T> neighbors(T x) {
        List<T> neighboursElements = new LinkedList<>();
        if (!vertices.containsKey(x)) {
            throw new IllegalArgumentException("The vertex " + x + " is not in the Graph.");
        } else {
            List<EdgeNode<T>> adjList = vertices.get(x);
            for (EdgeNode<T> edge : adjList) {
                neighboursElements.add(edge.getElement());
            }
        }
        return neighboursElements;
    }

    @Override
    public void addVertex(T x) {
        if (vertices.containsKey(x)) {
            throw new IllegalArgumentException("The vertex " + x + " is already in the Graph.");
        } else {
            List<EdgeNode<T>> newList = new LinkedList<>();
            vertices.put(x, newList);
        }
    }

    @Override
    public void removeVertex(T x) {
        if (!vertices.containsKey(x)) {
            throw new IllegalArgumentException("The vertex " + x + " is not in the Graph.");
        } else {
            vertices.remove(x);
        }
    }

    @Override
    public void addEdge(T x, T y) {
        if (!vertices.containsKey(x)) {
            throw new IllegalArgumentException("The vertex " + x + " is not in the Graph.");
        } else if (!vertices.containsKey(y)) {
            throw new IllegalArgumentException("The vertex " + y + " is not in the Graph.");
        } else {
            List<EdgeNode<T>> adjList = vertices.get(x);
            if (adjList.contains(new EdgeNode<T>(y, 0))) {
                throw new IllegalArgumentException("The edge from " + x + " to " + y + " is already in the graph");
            } else {
                adjList.add(new EdgeNode<T>(y, 0));
            }
        }
    }

    @Override
    public void removeEdge(T x, T y) {
        if (!vertices.containsKey(x)) {
            throw new IllegalArgumentException("The vertex " + x + " is not in the Graph.");
        } else if (!vertices.containsKey(y)) {
            throw new IllegalArgumentException("The vertex " + y + " is not in the Graph.");
        } else {
            List <EdgeNode<T>> adjList = vertices.get(x);
            if (!adjList.contains(new EdgeNode<T>(y, 0))) {
                throw new IllegalArgumentException("The edge from " + x + " to " + y + " is not in the graph and cannot be removed.");
            } else {
                adjList.remove(new EdgeNode<T>(y, 0));
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (T vertex : vertices.keySet()) {
            result.append(vertex + " neighbors: ->");
            List<EdgeNode<T>> neighbours = vertices.get(vertex);
            for (EdgeNode<T> edge : neighbours) {
                result += edge.getElement() + "->";
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
        }

        for (T vertex : vertices.keySet()) {
            for (EdgeNode<T> edge : vertices.get(vertex)) {
                copy.addEdge(vertex, edge.getElement());
            }
        }

        return copy;
    }

}
