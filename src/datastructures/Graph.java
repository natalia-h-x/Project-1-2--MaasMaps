package datastructures;

import java.util.List;

public interface Graph<T> {

    /**
     * Returns the list of all vertices y s.t. there’s an edge from x to y
     *
     * @param x
     * @return
     */
    public List<T> neighbors(T x);

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
     * Adds an edge from the vertices x to y
     *
     * @param x
     * @param y
     */
    public void addEdge(T x, T y);

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
