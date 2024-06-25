package core.algorithms;
import java.awt.geom.Point2D;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import core.datastructures.graph.Edge;
import core.datastructures.graph.Graph;
import core.models.gtfs.Time;
import core.models.transport.Transport;

public class AStarAlgorithm<T extends Point2D> extends PathStrategy<T> {
    private DijkstraAlgorithm<T> dijkstraAlgorithm;
    private static final double SCALING_FACTOR = 0.0001;

    @Override
    public Transport[] getShortestPath(Graph<T> graph, T source, T end, Time startTime, Time maxWalkingTime) throws IllegalArgumentException {
        if (dijkstraAlgorithm == null) {
            dijkstraAlgorithm = new DijkstraAlgorithm<>();
        }

        return dijkstraAlgorithm.shortestPath(graph, source, end, startTime, getHeuristicComparator(source, end), maxWalkingTime.toSeconds());
    }

    private Comparator<? super T> getHeuristicComparator(T source, T end) {
        return (a, b) -> Double.compare(getFitness(a, source, end), getFitness(b, source, end));
    }

    private double getFitness(T current, T source, T end) {
        double gCost = current.distance(source);
        double hCost = current.distance(end) * SCALING_FACTOR;
        return gCost + hCost;
    }

    @Override
    public String toString() {
        return "A";
    }
}