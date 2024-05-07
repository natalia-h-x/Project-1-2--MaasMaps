package ui;
import org.junit.Test;

import core.Context;
import core.models.Location;
import ui.map.geometry.GeographicLine;

public class ResultsTest {
    @Test
    public void test1() {
        new MaasMapsUI();
        GeographicLine line = new GeographicLine();

        Location loc1 = new Location(50.855233, 5.692237);
        Location loc2 = new Location(50.853608, 5.691958);
        Location loc3 = new Location(50.853617, 5.692009);
        Location loc4 = new Location(50.853037, 5.691825);
        Location loc5 = new Location(50.854993, 5.692294);
        Location loc6 = new Location(50.854581, 5.690199);

        line.addLocation(loc1);
        line.addLocation(loc2);
        line.addLocation(loc3);
        line.addLocation(loc4);
        line.addLocation(loc5);
        line.addLocation(loc6);

        Context.getContext().getResultsPanel().setLine(line);
    }
}
