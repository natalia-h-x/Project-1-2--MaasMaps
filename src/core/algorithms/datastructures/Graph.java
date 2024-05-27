package core.algorithms.datastructures;

import java.util.List;

import core.models.Route;
import core.models.Time;

public interface Graph<T> extends Iterable<T> {
    /**
     * Returns the list of all vertices y s.t. there’s an edge from x to y
     *
     * @param x
     * @return
     */
    public List<EdgeNode<T>> neighbors(T x);

    /**
     * Adds the vertex x to the graph
     *
     * @param x
     */
    public void addVertex(T x);

    /**
     * Removes the vertex x from the graph
     *
     * @param x
     */
    public void removeVertex(T x);

    /**
     * Returns if the vertex x is in the graph
     *
     * @param x
     */
    public boolean containsVertex(T x);

    /**
     * Adds an edge from the vertices x to y
     *
     * @param x
     * @param y
     */
    public void addEdge(T x, T y, int weight);

    /**
     * Adds an edge from the vertices x to y
     *
     * @param x
     * @param y
     */
    public void addEdge(T x, T y, int weight, Route route, Time time);

    /**
     * Inserts an edge in the vertex x
     *
     * @param x
     * @param y
     */
    public void addEdge(EdgeNode<T> edge, T x);

    /**
     * Removes the edge from the vertices x to y
     *
     * @param x
     * @param y
     */
    public void removeEdge(T x, T y);

    public Graph<T> clone();

    public List<T> getVertecesList();
}
