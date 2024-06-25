package core.algorithms;

import java.awt.geom.Point2D;
import java.util.Comparator;

import core.datastructures.graph.Graph;
import core.models.gtfs.Time;
import core.models.transport.Transport;

public class AStarAlgorithm<T extends Point2D> extends PathStrategy<T> {
    private DijkstraAlgorithm<T> dijkstraAlgorithm;

    @Override
    public Transport[] getShortestPath(Graph<T> graph, T source, T end, Time startTime, Time maxWalkingTime) throws IllegalArgumentException {
        if (dijkstraAlgorithm == null) {
            dijkstraAlgorithm = new DijkstraAlgorithm<>();
        }

        return dijkstraAlgorithm.shortestPath(graph, source, end, startTime, getHeuristicComparator(source, end), maxWalkingTime.toSeconds());
    }

    private Comparator<? super T> getHeuristicComparator(T source, T end) {
        return (a, b) -> Double.compare(getFitness(a, end), getFitness(b, end));
    }

    private double getFitness(T current, T end) {
        return current.distance(end);
    }

    @Override
    public String toString() {
        return "A*";
    }
}
