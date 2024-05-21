package ui;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.awt.Point;

import org.junit.Test;

import core.managers.MapManager;

public class MapManagerTest {
    @Test
    public void test1()  {
        try {
            new MaasMapsUI();

            assertTrue(new Point(0, 0).equals(MapManager.locationToPoint(MapManager.MAP_TOP_LEFT_LOCATION)));
            assertTrue(new Point(1318, 1583).equals(MapManager.locationToPoint(MapManager.MAP_BOTTOM_RIGHT_LOCATION)));
        }
        catch (Exception e) {
            fail(e);
        }
    }
}
