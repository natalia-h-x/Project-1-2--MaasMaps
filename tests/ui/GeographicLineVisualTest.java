package ui;

import org.junit.jupiter.api.Test;

import core.Context;
import core.models.Location;
import core.models.Time;
import ui.map.geometry.GeographicLine;
import ui.map.geometry.factories.LineFactory;

public class GeographicLineVisualTest {

    @Test
    public void testLineDrawing() {
        new MaasMapsUI();

        Location loc1 = new Location(50.855200, 5.692200);
        Location loc2 = new Location(50.853600, 5.692000);
        Location loc3 = new Location(50.852000, 5.691800);
        Location loc4 = new Location(50.850400, 5.691600);
        Location loc5 = new Location(50.848400, 5.691600);
        Location loc6 = new Location(50.846000, 5.691600);
        GeographicLine line = LineFactory.createGeographicArrowLine(loc1, loc2, loc3, loc4, loc5, loc6);

        Time travelTime1 = Time.of(0, 10, 20); // 10 minutes and 20 seconds
        Time travelTime2 = Time.of(0, 15, 25); // 15 minutes and 25 seconds
        Time travelTime3 = Time.of(0, 20, 30); // 20 minutes and 30 seconds
        Time travelTime4 = Time.of(0, 25, 35); // 25 minutes and 35 seconds
        Time travelTime5 = Time.of(0, 30, 40); // 30 minutes and 40 seconds

        line.addTime(travelTime1);
        line.addTime(travelTime2);
        line.addTime(travelTime3);
        line.addTime(travelTime4);
        line.addTime(travelTime5);

        Context.getContext().getMap().addMapGraphics(line);
        Context.getContext().getMap().repaint();

        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
