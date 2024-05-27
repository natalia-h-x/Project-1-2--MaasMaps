package algorithms;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.Test;

import core.algorithms.DijkstraAlgorithm;
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

        Transport transport = DijkstraAlgorithm.shortestPath(MapManager.getBusGraph(), closestStarts[0], closestDestinations[0], Time.of(25200));
        
        assertEquals(transport.getTime(), Time.of(360));
        
        GeographicLine line = transport.getLine();
 
        assert(((BusStop) line.getLocations().get(0)).getStopName().contains("Maastricht, Scharn Kerk"));
        assert(((BusStop) line.getLocations().get(1)).getStopName().contains("Maastricht, Burgemeester Cortenstraat"));
        assert(((BusStop) line.getLocations().get(2)).getStopName().contains("Maastricht, Dorpstraat/De Leim"));
        assert(((BusStop) line.getLocations().get(3)).getStopName().contains("Maastricht, Dorpstraat/Kerk"));
        assert(((BusStop) line.getLocations().get(4)).getStopName().contains("Maastricht, Rijksweg/Veldstraat"));
    }

    @Test
    public void testForSameStop() {
        try {
            Location[] closestStarts = MapManager.getClosestPoint(new Location(50.848101, 5.722739), 1);
            Location[] closestDestinations = MapManager.getClosestPoint(new Location(50.848101, 5.722739), 1);
            Transport transport = DijkstraAlgorithm.shortestPath(MapManager.getBusGraph(), closestStarts[0], closestDestinations[0], Time.of(25200));
        } catch (IllegalArgumentException e) {
        }
    }
}
