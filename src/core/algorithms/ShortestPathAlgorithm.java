package core.algorithms;

import core.algorithms.datastructures.Graph;
import ui.map.geometry.Line;

public interface ShortestPathAlgorithm<E> {
    public abstract Line getShortestPath(Graph<E> graph);
}
