package ui.map.geometry;

import java.awt.geom.Point2D;

import core.algorithms.datastructures.Graph;
import core.models.BusStop;
import ui.map.geometry.factories.BusMarkerFactory;

public class BusNetwork extends Network {
    public BusNetwork(Graph<BusStop> graph) {
        super(graph, new BusMarkerFactory());
    }

    @Override
    public Line createLineSegment(Point2D... points) {
        Line segment = new Line(points);
        segment.setStroke(getStroke());

        return segment;
    }

    @Override
    public Marker createMarker(Point2D point) {
        BusStop bTo;

        if (point instanceof Point2DImpostor impostor) {
            bTo = (BusStop) impostor.getImposedPoint();
        }
        else {
            bTo = (BusStop) point;
        }

        Marker marker = super.createMarker(point);
        marker.setText(bTo.getStopName());
        return marker;
    }
}
