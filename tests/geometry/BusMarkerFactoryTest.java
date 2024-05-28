package geometry;

import static org.junit.jupiter.api.Assertions.fail;

import java.awt.geom.Point2D;

import org.junit.Test;

import ui.map.geometry.Marker;
import ui.map.geometry.factories.BusMarkerFactory;

public class BusMarkerFactoryTest {

    @Test
    public void test1() {
        Point2D point = new Point2D.Double(50.0, 50.0);
        BusMarkerFactory factory = new BusMarkerFactory();
        Marker marker = factory.createMarker(point);

    }
}
