package geometry;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

import core.Context;
import core.models.Location;
import ui.MaasMapsUI;
import ui.map.geometry.GeographicLine;

public class GeographicLineTest {

    public GeographicLineTest() {
        new MaasMapsUI();
    }

    @Test
    public void testGetTotalDistance() {
        Location loc1 = new Location(50.0, 5.0);
        Location loc2 = new Location(51.0, 6.0);
        GeographicLine geographicLine = new GeographicLine(loc1, loc2);

        double totalDistance = geographicLine.getTotalDistance();
        
        assertTrue(totalDistance > 0);
        assertFalse(totalDistance < 0);
    }

    @Test
    public void testDrawLineSegment() {
        Location loc1 = new Location(50.0, 5.0);
        Location loc2 = new Location(51.0, 6.0);
        GeographicLine geographicLine = new GeographicLine(loc1, loc2);

        Context.getContext().getMap().addMapGraphics(geographicLine);
        Context.getContext().getMap().repaint();
    }

    @Test
    public void testDrawDistance() {
        Location loc1 = new Location(50.0, 5.0);
        Location loc2 = new Location(51.0, 6.0);
        GeographicLine geographicLine = new GeographicLine(loc1, loc2);

        Context.getContext().getMap().addMapGraphics(geographicLine);
        Context.getContext().getMap().repaint();
    }
}
