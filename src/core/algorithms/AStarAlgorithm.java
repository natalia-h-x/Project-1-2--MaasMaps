package core.algorithms;

import java.awt.geom.Point2D;
import java.util.Comparator;

import core.algorithms.datastructures.Graph;
import core.models.gtfs.Time;
import core.models.transport.Transport;

public class AStarAlgorithm<T extends Point2D> extends PathStrategy<T> {
    private DijkstraAlgorithm<T> dijkstraAlgorithm;

    @Override
    public Transport[] shortestPath(Graph<T> graph, T source, T end, Time startTime) throws IllegalArgumentException {
        return dijkstraAlgorithm.shortestPath(graph, source, end, startTime, getHeuristicComparator(source, end));
    }

    private Comparator<? super T> getHeuristicComparator(T source, T end) {
        return (a, b) -> Double.compare(getFitness(a, end), getFitness(b, end));
    }

    private double getFitness(T current, T end) {
        return current.distance(end);
    }
}
