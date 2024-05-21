package manager;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import core.managers.DistanceManager;
import core.models.Location;

public class DistanceManagerTest {
    @Test
    public void test1() {
        double dist = DistanceManager.haversine(new Location(50.855233, 5.692237), new Location(50.852666, 5.692532));
        assertEquals(288.58, dist*1000, 2.5);
    }
}
