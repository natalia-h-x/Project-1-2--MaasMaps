package ui;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.Test;

import core.managers.MapManager;
import core.models.Location;

public class MapManagerTest {
    public static void main(String[] args) throws ClassNotFoundException{
        test1();
    }
    @Test
    public static void test1() throws ClassNotFoundException {
        new MaasMapsUI();
        try {
            assert (MapManager.MAP_TOP_LEFT_GLOBAL_XY.equals(MapManager.locationToPoint(MapManager.MAP_TOP_LEFT_LOCATION)));
            assert (MapManager.MAP_BOTTOM_RIGHT_GLOBAL_XY.equals(MapManager.locationToPoint(MapManager.MAP_BOTTOM_RIGHT_LOCATION)));

            assert (MapManager.locationToPoint(new Location(50.853571,5.693554)).equals(MapManager.locationToPoint(new Location(50.853551,5.692933))));
        }
        catch (Exception e) {
            fail(e);
        }
    }
}
