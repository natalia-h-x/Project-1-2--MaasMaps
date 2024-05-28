package geometry;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import java.awt.geom.Point2D;
import org.junit.Test;
import ui.map.geometry.ImageMarker;
import ui.map.geometry.ImageMarkerFactory;

public class ImageMarkerFactoryTest {

    public static void main(String[] args) {
        Point2D point = new Point2D.Double(50.0, 50.0);
        ImageMarker busMarker = ImageMarkerFactory.createBusImageMarker(point);
        ImageMarker aMarker = ImageMarkerFactory.createAImageMarker(point);
        ImageMarker bMarker = ImageMarkerFactory.createBImageMarker(point);
    }

    @Test
    public void testCreateBusImageMarker() {
        try {
            Point2D point = new Point2D.Double(50.0, 50.0);
            ImageMarker busMarker = ImageMarkerFactory.createBusImageMarker(point);
            assertNotNull(busMarker);
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void testCreateAImageMarker() {
        try {
            Point2D point = new Point2D.Double(50.0, 50.0);
            ImageMarker aMarker = ImageMarkerFactory.createAImageMarker(point);
            assertNotNull(aMarker);
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void testCreateBImageMarker() {
        try {
            Point2D point = new Point2D.Double(50.0, 50.0);
            ImageMarker bMarker = ImageMarkerFactory.createBImageMarker(point);
            assertNotNull(bMarker);
        } catch (Exception e) {
            fail(e);
        }
    }
}
