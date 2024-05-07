package ui.map.geometry;

import java.awt.geom.Point2D;

import ui.map.geometry.interfaces.AbstractMarkerFactory;

public class MarkerFactory implements AbstractMarkerFactory {
    @Override
    public Marker createMarker(Point2D point) {
        return new Marker(point);
    }
}
