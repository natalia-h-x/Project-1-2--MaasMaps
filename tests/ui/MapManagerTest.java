package ui;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.Test;

import core.managers.MapManager;
import core.models.Location;

public class MapManagerTest {

    @Test
    public void test1() {
        try {
            assertTrue(MapManager.MAP_TOP_LEFT_GLOBAL_XY == MapManager.locationToPoint(MapManager.MAP_TOP_LEFT_LOCATION));
            assertTrue(MapManager.MAP_BOTTOM_RIGHT_GLOBAL_XY == MapManager.locationToPoint(MapManager.MAP_BOTTOM_RIGHT_LOCATION));
            assertTrue(MapManager.MAP_BOTTOM_RIGHT_GLOBAL_XY == MapManager.locationToPoint(MapManager.MAP_BOTTOM_RIGHT_LOCATION));

            System.out.println(MapManager.locationToPoint(new Location(50.853551,5.692933)));
        }
        catch (Exception e) {
            fail(e);
        }
    }
}
