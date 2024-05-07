package ui.map.geometry.interfaces;

import java.awt.geom.Point2D;

import ui.map.geometry.Marker;

public interface AbstractMarkerFactory {
    public abstract Marker createMarker(Point2D point);
}
