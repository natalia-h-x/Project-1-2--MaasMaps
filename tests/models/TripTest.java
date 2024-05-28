package models;

import java.awt.Color;

import org.junit.jupiter.api.Test;

import core.models.BusStop;
import core.models.Location;
import core.models.Shape;
import core.models.Trip;

public class TripTest {
   /*  @Test
    public void tripTest() {
        Trip trip = Trip.empty();
        trip.setId(178502020);
        trip.setShapeId(1129890);
        trip.setRouteId(90786);
        trip.setTripHeadsign("De Heeg");

        Shape shape = trip.loadShape(new BusStop(50.847853, 5.724670), new BusStop(50.844914, 5.727588));
        Shape shape2 = new Shape(Color.MAGENTA, new Location[]{new Location(50.847853, 5.724670), 
                                                                new Location(50.844914, 5.727588), 
                                                                new Location(50.842640, 5.727500)});
    
        Shape pruned = shape2.prune(new BusStop(50.847853, 5.724670), new BusStop(50.844914, 5.727588));
        
        System.out.println();
    }

    @Test
    public void tripTest1() {
       
        Shape shape2 = new Shape(Color.MAGENTA, new Location[]{new Location(50.847853, 5.724670), 
                                                                new Location(50.844914, 5.727588), 
                                                                new Location(50.842640, 5.727500)});
    
        Shape pruned = shape2.prune(new BusStop(50.847853, 5.724670), new BusStop(50.844914, 5.727588));
        
        System.out.println(pruned);
    } */
}
