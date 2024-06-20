package core.algorithms.datastructures;

import java.util.List;

import core.models.gtfs.Time;
import core.models.gtfs.Trip;

public interface Graph<T> extends Iterable<T> {
    /**
     * Returns the list of all vertices y s.t. there’s an edge from x to y
     *
     * @param x
     * @return
     */
    public List<Edge<T>> neighbors(T x);

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
    public void addEdge(T x, T y, int weight, Trip trip, Time time);


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
