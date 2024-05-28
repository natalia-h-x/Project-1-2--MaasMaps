package geometry;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import org.junit.Test;

import ui.map.geometry.ImageMarker;

public class ImageMarkerTest {
    @Test
    public void test1() {
        BufferedImage image = new BufferedImage(100, 200, BufferedImage.TYPE_INT_ARGB);
        Point2D location = new Point2D.Double(50.0, 50.0);
        ImageMarker imageMarker = new ImageMarker(location, image);
    }
}
