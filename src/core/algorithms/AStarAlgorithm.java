package core.algorithms;

import java.awt.geom.Point2D;
import java.util.Comparator;

import core.algorithms.datastructures.Graph;
import core.models.Time;
import core.models.transport.Transport;

public class AStarAlgorithm extends PathStrategy {

    @Override
    public Transport shortestPath(Graph<Point2D> graph, Point2D source, Point2D end, Time startTime) throws IllegalArgumentException {
        return new DijkstraAlgorithm().shortestPath(graph, source, end, startTime, makeHeuristic());
    }

    private Comparator<? super Point2D> makeHeuristic() {
        return (a, b) -> {
            return 0;
        };
    }



}
