package models;

import org.junit.jupiter.api.Test;

import core.Context;
import core.models.Location;
import core.models.Shape;
import ui.MaasMapsUI;
import ui.map.geometry.factories.LineFactory;

public class ShapeTest {
    @Test
    public void tripTest() {
        Shape shape = Shape.empty();
        shape.setId(1129890);
        shape.setLocations(new Location[] {
            new Location(50.847854, 5.724670), 
            new Location(50.844914, 5.727588), 
            new Location(50.842640, 5.727500)
        });

        new MaasMapsUI();

        Context.getContext().getMap().addMapGraphics(LineFactory.createResultsLine(shape.getLocations()));
        Context.getContext().getMap().repaint();
    }
}
