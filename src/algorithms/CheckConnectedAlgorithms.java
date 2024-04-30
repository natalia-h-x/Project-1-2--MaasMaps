package algorithms;

import datastructures.EdgeNode;
import datastructures.Graph;
import ui.map.geometry.Line;

public interface CheckConnectedAlgorithms<E> {
    public abstract Line makeConnected(Graph<E> graph);
    public abstract boolean isConnected(EdgeNode<E> A, EdgeNode<E> B);
}
