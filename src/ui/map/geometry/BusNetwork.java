package ui.map.geometry;

import java.awt.geom.Point2D;

import core.algorithms.datastructures.Graph;
import core.models.BusStop;

public class BusNetwork extends Network {
    public BusNetwork(Graph<BusStop> graph) {
        super(graph, new BusMarkerFactory());
    }

    @Override
    public Line createLineSegment(Point2D from, Point2D to) {
        BusStop bTo;

        if (to instanceof Point2DImpostor impostor) {
            bTo = (BusStop) impostor.getImposedPoint();
        }
        else {
            bTo = (BusStop) to;
        }

        Line segment = new Line(from, to);
        segment.setPaint(bTo.getRoute().getColor());

        return segment;
    }
}
