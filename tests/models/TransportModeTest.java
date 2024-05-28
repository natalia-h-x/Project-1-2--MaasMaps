package models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import core.Context;
import core.models.Location;
import core.models.Time;
import core.models.Trip;
import core.models.transport.Biking;
import core.models.transport.Bus;
import core.models.transport.Transport;
import core.models.transport.Walking;
import ui.MaasMapsUI;
import ui.map.geometry.GeographicLine;
import ui.map.geometry.interfaces.MapGraphics;

public class TransportModeTest {
    @Test
    public void bikingTest() {
        Biking biking = new Biking(new Location(50.848101, 5.722739), new Location(50.836348, 5.726151));

        assertEquals(291.66666, biking.getAverageSpeed());
        assertEquals("4 minutes and 33 seconds.", biking.getTravelTime().toString());

        MapGraphics[] mapGraphics = biking.getGraphics();
        
        new MaasMapsUI();
        Context.getContext().getMap().addMapGraphics(mapGraphics);
        Context.getContext().getMap().repaint();
    }

    @Test
    public void walkingTest() {
        Walking walking = new Walking(new Location(50.848101, 5.722739), new Location(50.836348, 5.726151));

        assertEquals(83.33333, walking.getAverageSpeed());
        assertEquals("15 minutes and 56 seconds.", walking.getTravelTime().toString());

        MapGraphics[] mapGraphics = walking.getGraphics();
        
        new MaasMapsUI();
        Context.getContext().getMap().addMapGraphics(mapGraphics);
        Context.getContext().getMap().repaint();
    }

    @Test
    public void busTest() {
        Bus bus = new Bus();
        assertEquals(333, bus.getAverageSpeed());
        bus.dispose();
        bus.setStart(new Location(50.848101, 5.722739));
        bus.setDestination(new Location(50.836348, 5.726151));

        assertEquals("20 minutes and 59 seconds.", bus.getTravelTime().toString());
        assertEquals("13 minutes and 58 seconds.", bus.getShortestRoute().getTime().toString());
        assertEquals("33 minutes and 21 seconds.", bus.getShortestManualRoute().getTime().toString());
        assertEquals("11 minutes and 58 seconds.", bus.getShortestVehicleRoute().getTime().toString());
        
        MapGraphics[] mapGraphics = bus.getGraphics();
        
        new MaasMapsUI();
        Context.getContext().getMap().addMapGraphics(mapGraphics);
        Context.getContext().getMap().repaint();

        Transport.ofBiking(new GeographicLine(), Time.of(2), new ArrayList<Trip>());
    }
}
