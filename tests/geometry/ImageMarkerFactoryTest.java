package geometry;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.awt.geom.Point2D;

import org.junit.Test;

import ui.map.geometry.ImageMarker;
import ui.map.geometry.ImageMarkerFactory;

public class ImageMarkerFactoryTest {
    @Test
    public void testCreateBusImageMarker() {
        Point2D point = new Point2D.Double(50.0, 50.0);
        ImageMarker busMarker = ImageMarkerFactory.createBusImageMarker(point);
        assertNotNull(busMarker);
    }

    @Test
    public void testCreateAImageMarker() {
        Point2D point = new Point2D.Double(50.0, 50.0);
        ImageMarker aMarker = ImageMarkerFactory.createAImageMarker(point);
        assertNotNull(aMarker);
    }

    @Test
    public void testCreateBImageMarker() {
        Point2D point = new Point2D.Double(50.0, 50.0);
        ImageMarker bMarker = ImageMarkerFactory.createBImageMarker(point);
        assertNotNull(bMarker);
    }
}
