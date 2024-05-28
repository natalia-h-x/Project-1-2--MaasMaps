package geometry;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.Test;

import core.models.Location;
import ui.map.geometry.Line;
import ui.map.geometry.factories.LineFactory;

public class LineFactoryTest {
    @Test
    public void test1() {
        Location loc1 = new Location(50.855233, 5.692237);
        Location loc2 = new Location(50.853608, 5.691958);
        Line line = LineFactory.createResultsLine(loc1, loc2);
    }
}
