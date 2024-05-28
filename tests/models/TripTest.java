package models;


import org.junit.jupiter.api.Test;

import core.Context;
import core.models.BusStop;
import core.models.Shape;
import core.models.Trip;
import ui.MaasMapsUI;
import ui.map.geometry.Line;
import ui.map.geometry.factories.LineFactory;

public class TripTest {
    @Test
    public void tripTest() {
        Trip trip = Trip.empty();
        trip.setId(178502020);
        trip.setShapeId(1129890);
        trip.setRouteId(90786);
        trip.setTripHeadsign("De Heeg");

        Shape shape = trip.loadShape(new BusStop(50.847853, 5.724670), new BusStop(50.844914, 5.727588));
        Line line = LineFactory.createResultsLine(shape.getLocations());
        
        new MaasMapsUI();

        Context.getContext().getMap().addMapGraphics(line);
        Context.getContext().getMap().repaint();
    }
}
