package ui.map.geometry;

import java.awt.geom.Point2D;

import core.algorithms.datastructures.Graph;
import core.models.BusStop;

public class BusNetwork extends Network {
    public BusNetwork(Graph<Point2D> graph) {
        super(graph, new BusMarkerFactory());
    }

    @Override
    public void addLineSegment(Point2D from, Point2D to) {
        BusStop bFrom = (BusStop) from;
        BusStop bTo = (BusStop) to;

        Line segment = new Line(bFrom, bTo);
        segment.setPaint(bTo.getRouteColor());

        getMapGraphics().add(segment);
    }
}
