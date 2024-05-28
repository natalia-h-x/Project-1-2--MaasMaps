package geometry;

import static org.junit.jupiter.api.Assertions.fail;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import org.junit.Test;
import ui.map.geometry.ImageMarker;

public class ImageMarkerTest {

    public static void main(String[] args) {
        BufferedImage image = new BufferedImage(100, 200, BufferedImage.TYPE_INT_ARGB);
        Point2D location = new Point2D.Double(50.0, 50.0);
        ImageMarker imageMarker = new ImageMarker(location, image);
    }

    @Test
    public void test1() {
        try {
            BufferedImage image = new BufferedImage(100, 200, BufferedImage.TYPE_INT_ARGB);
            Point2D location = new Point2D.Double(50.0, 50.0);
            ImageMarker imageMarker = new ImageMarker(location, image);
        } catch (Exception e) {
            fail(e);
        }
    }
}
