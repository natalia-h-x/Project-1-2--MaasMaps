package geometry;

import static org.junit.jupiter.api.Assertions.fail;

import java.awt.geom.Point2D;

import org.junit.Test;

import ui.map.geometry.Marker;
import ui.map.geometry.factories.MarkerFactory;

public class MarkerFactoryTest {

    @Test
    public void test1() {
        Point2D point = new Point2D.Double(50.0, 50.0);
        MarkerFactory factory = new MarkerFactory();
        Marker marker = factory.createMarker(point);
    }
}
