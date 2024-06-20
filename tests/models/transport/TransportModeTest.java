package models.transport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import core.Context;
import core.algorithms.DijkstraAlgorithm;
import core.models.Location;
import core.models.gtfs.Time;
import core.models.gtfs.Trip;
import core.models.transport.Biking;
import core.models.transport.Bus;
import core.models.transport.Route;
import core.models.transport.Walking;
import ui.MaasMapsUI;
import ui.map.geometry.GeographicLine;
import ui.map.geometry.interfaces.MapGraphics;

public class TransportModeTest {
    public TransportModeTest() {
        new MaasMapsUI();
    }

    @Test
    public void bikingTest() {
        Biking biking = new Biking(new Location(50.848101, 5.722739), new Location(50.836348, 5.726151));

        assertEquals(291.66666, biking.getAverageSpeed());
        assertEquals("4 minutes and 33 seconds.", biking.getTravelTime().toString());

        MapGraphics[] mapGraphics = biking.getGraphics();

        Context.getContext().getMap().addMapGraphics(mapGraphics);
        Context.getContext().getMap().repaint();
    }

    @Test
    public void walkingTest() {
        Walking walking = new Walking(new Location(50.848101, 5.722739), new Location(50.836348, 5.726151));

        assertEquals(83.33333, walking.getAverageSpeed());
        assertEquals("15 minutes and 56 seconds.", walking.getTravelTime().toString());

        MapGraphics[] mapGraphics = walking.getGraphics();

        Context.getContext().getMap().addMapGraphics(mapGraphics);
        Context.getContext().getMap().repaint();
    }

    @Test
    public void busTest() {
        Bus bus = new Bus(new Location(50.848101, 5.722739), new Location(50.836348, 5.726151));
        assertEquals(333, bus.getAverageSpeed());
        bus.dispose();
        bus.setStart(new Location(50.848131, 5.722733));
        bus.setDestination(new Location(50.836328, 5.726121));

        assertEquals("20 minutes and 59 seconds.", bus.getTravelTime().toString());

        MapGraphics[] mapGraphics = bus.getGraphics();

        Context.getContext().getMap().addMapGraphics(mapGraphics);
        Context.getContext().getMap().repaint();

        Route.of(Time.of(2), new ArrayList<>());

        DijkstraAlgorithm.calculateShortestPath(bus);

        Bus bus2 = bus;
        bus.hashCode();
        assertTrue(bus.equals(bus2));
    }
}
