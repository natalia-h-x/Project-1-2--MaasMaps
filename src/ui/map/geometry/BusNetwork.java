package ui.map.geometry;

import java.awt.geom.Point2D;

import core.datastructures.graph.Graph;
import core.models.BusStop;
import ui.map.geometry.factories.BusMarkerFactory;

public class BusNetwork extends Network {
    public <P extends Point2D> BusNetwork(Graph<P> graph) {
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

        BusMarker marker = (BusMarker) super.createMarker(point);
        marker.setLocationName(bTo.getStopName());
        return marker;
    }
}
