package models;


import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;

import org.junit.jupiter.api.Test;

import core.models.BusStop;
import core.models.Location;
import core.models.Shape;
import core.models.Trip;

public class TripTest {
    @Test
    public void tripTest() {
        Trip trip = Trip.empty();
        trip.setId(178502020);
        trip.setShapeId(1129890);
        trip.setRouteId(90786);
        trip.setTripHeadsign("De Heeg");


        //Shape shape = trip.loadShape(new BusStop(50.847853, 5.724670), new BusStop(50.844914, 5.727588));
        //Shape pruned = shape2.prune(new BusStop(50.847853, 5.724670), new BusStop(50.844914, 5.727588));
        
    }

    @Test
    public void shapeTest() {
        Shape shape = new Shape(Color.MAGENTA, new Location(50.847853, 5.724670), 
            new Location(50.844914, 5.727588), 
            new Location(50.842640, 5.727500));

        assertEquals(Color.MAGENTA, shape.getColor());
        assertEquals(50.847853, shape.getLocations()[0].getLatitude());
        shape.setColor(Color.GREEN);
        shape.setLocations(new Location[]{new Location(50.847854, 5.724670), 
            new Location(50.844914, 5.727588), 
            new Location(50.842640, 5.727500)});
        Shape sameShape = shape;
        Shape notSameShape = new Shape(Color.MAGENTA, new Location(50.847853, 5.724670), 
        new Location(50.844914, 5.727588), 
        new Location(50.842640, 5.727500));
        assertTrue(sameShape.equals(shape));
        assertFalse(notSameShape.equals(shape));
        assertEquals(sameShape.hashCode(), shape.hashCode());
        assertNotEquals(notSameShape.hashCode(), shape.hashCode());
    }
}
