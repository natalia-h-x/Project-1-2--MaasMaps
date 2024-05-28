package geometry;
import static org.junit.jupiter.api.Assertions.fail;
import java.awt.Graphics2D;
import org.junit.Test;
import core.models.Location;
import ui.map.geometry.GeographicLine;

public class GeographicLineTest {

    public static void main(String[] args) {
        Location loc1 = new Location(50.0, 5.0);
        Location loc2 = new Location(51.0, 6.0);
        GeographicLine geographicLine = new GeographicLine(loc1, loc2);
    }

    @Test
    public void testGetTotalDistance() {
        try {
            Location loc1 = new Location(50.0, 5.0);
            Location loc2 = new Location(51.0, 6.0);
            GeographicLine geographicLine = new GeographicLine(loc1, loc2);

            double totalDistance = geographicLine.getTotalDistance();

            assert totalDistance > 0 : "Total distance should be greater than zero.";
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void testDrawLineSegment() {
        try {
            Location loc1 = new Location(50.0, 5.0);
            Location loc2 = new Location(51.0, 6.0);
            GeographicLine geographicLine = new GeographicLine(loc1, loc2);

            Graphics2D g2 = null;
            geographicLine.drawLineSegment(g2, loc1, loc2);
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void testDrawDistance() {
        try {
            Location loc1 = new Location(50.0, 5.0);
            Location loc2 = new Location(51.0, 6.0);
            GeographicLine geographicLine = new GeographicLine(loc1, loc2);

            Graphics2D g2 = null;
            geographicLine.drawDistance(g2, loc1, loc2);
        } catch (Exception e) {
            fail(e);
        }
    }
}
