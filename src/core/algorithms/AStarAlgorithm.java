package core.algorithms;

import java.awt.geom.Point2D;
import java.util.Comparator;

import core.algorithms.datastructures.Graph;
import core.models.Time;
import core.models.transport.Transport;

public class AStarAlgorithm extends PathStrategy {
    @Override
    public Transport shortestPath(Graph<Point2D> graph, Point2D source, Point2D end, Time startTime) throws IllegalArgumentException {
        return new DijkstraAlgorithm().shortestPath(graph, source, end, startTime, getHeuristicComparator(source, end));
    }

    private Comparator<? super Point2D> getHeuristicComparator(Point2D source, Point2D end) {
        return (a, b) -> Double.compare(getFitness(a, source, end), getFitness(b, source, end));
    }

    private double getFitness(Point2D current, Point2D source, Point2D end) {
        return 0;
    }
}
