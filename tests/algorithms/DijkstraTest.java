package algorithms;

import static org.junit.Assert.assertEquals;

import java.awt.geom.Point2D;

import org.junit.Test;


import core.algorithms.DijkstraAlgorithm;
import core.algorithms.PathStrategy;
import core.managers.MapManager;
import core.models.BusStop;
import core.models.Location;
import core.models.Time;
import core.models.transport.Transport;
import ui.map.geometry.GeographicLine;

public class DijkstraTest {

    @Test
    public void testForRouteWithBusNumber2() {
        Location[] closestStarts = MapManager.getClosestPoint(new Location(50.848101, 5.722739), 1);
        Location[] closestDestinations = MapManager.getClosestPoint(new Location(50.836348, 5.726151), 1);
        PathStrategy strategy = new DijkstraAlgorithm();
       
        Transport transport = strategy.shortestPath(MapManager.getBusGraph(), closestStarts[0], closestDestinations[0], Time.of(25200));

        assertEquals(transport.getTime(), Time.of(360));

        GeographicLine line = transport.getLine();

        assert(((BusStop) line.getLocations().get(0)).getStopName().contains("Maastricht, Scharn Kerk"));
        assert(((BusStop) line.getLocations().get(1)).getStopName().contains("Maastricht, Burgemeester Cortenstraat"));
        assert(((BusStop) line.getLocations().get(2)).getStopName().contains("Maastricht, Dorpstraat/De Leim"));
        assert(((BusStop) line.getLocations().get(3)).getStopName().contains("Maastricht, Dorpstraat/Kerk"));
        assert(((BusStop) line.getLocations().get(4)).getStopName().contains("Maastricht, Rijksweg/Veldstraat"));
        
        assertEquals("Walking", transport.getManualTransportModeA().toString());
        assertEquals("Walking", transport.getManualTransportModeB().toString());
        assertEquals("6 minutes.", transport.getTime().toString());
        transport.toString();
        transport.getTransfers();
    }

    @Test
    public void testForSameStop() {
        try {
            Location[] closestStarts = MapManager.getClosestPoint(new Location(50.848101, 5.722739), 1);
            Location[] closestDestinations = MapManager.getClosestPoint(new Location(50.848101, 5.722739), 1);
            Transport transport = (new DijkstraAlgorithm()).shortestPath(MapManager.getBusGraph(), closestStarts[0], closestDestinations[0], Time.of(25200));
        } catch (IllegalArgumentException e) {
            assert(true);
        }
    }
}
