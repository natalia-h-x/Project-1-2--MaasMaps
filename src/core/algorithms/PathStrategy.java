package core.algorithms;

import core.algorithms.datastructures.Graph;
import core.models.Location;
import core.models.Time;
import core.models.transport.Transport;
import ui.map.geometry.GeographicLine;
import ui.map.geometry.factories.LineFactory;

import java.awt.geom.Point2D;
import java.util.List;

public abstract interface PathStrategy {
    public static Transport shortestPath(Graph<Point2D> graph, Point2D source, Point2D end, Time startTime) throws IllegalArgumentException {
        throw new UnsupportedOperationException("cannot instanciate this in PathStrategy");
    }

    public static GeographicLine toGeographicLine(List<Point2D> shortestDistances, List<Time> timesTaken) {
        return LineFactory.createResultsLine(timesTaken.toArray(Time[]::new), shortestDistances.toArray(Location[]::new));
    }

    public static Time toTime(List<Time> timesTaken, Time startTime) {
        Time total = Time.of(0);

        for (Time time : timesTaken) {
            total = total.add(time);
        }

        return total.minus(startTime);
    }
}
