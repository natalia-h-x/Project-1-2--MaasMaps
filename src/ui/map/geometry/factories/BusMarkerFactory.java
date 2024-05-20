package ui.map.geometry.factories;

import java.awt.geom.Point2D;

import ui.map.geometry.ImageMarkerFactory;
import ui.map.geometry.Marker;
import ui.map.geometry.interfaces.AbstractMarkerFactory;

public class BusMarkerFactory implements AbstractMarkerFactory {
    @Override
    public Marker createMarker(Point2D point) {
        return ImageMarkerFactory.createBusImageMarker(point);
    }
}
