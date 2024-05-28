package geometry;

import org.junit.jupiter.api.Test;
import ui.map.geometry.Marker;

import java.awt.*;
import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MarkerTest {

    @Test
    public void testMarkerConstructorAndGetters() {
        Point2D location = new Point.Double(100, 200);
        Marker marker = new Marker(location);

        assertEquals(location, marker.getMarkerLocation());
    }

    @Test
    public void testGetIconLocation() {
        Point2D location = new Point.Double(150, 250);
        Marker marker = new Marker(location);

        Point expectedIconLocation = new Point(150, 240); // Y - 10 due to markerOffsetY
        assertEquals(expectedIconLocation, marker.getIconLocation());
    }
}