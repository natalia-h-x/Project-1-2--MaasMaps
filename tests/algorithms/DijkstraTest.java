package algorithms;

import static org.junit.Assert.assertEquals;

import java.awt.geom.Point2D;

import org.junit.Test;

import core.algorithms.DijkstraAlgorithm;
import core.managers.map.MapManager;
import core.managers.map.PostalCodeManager;
import core.models.BusStop;
import core.models.Location;
import core.models.gtfs.Time;
import core.models.transport.Route;
import core.models.transport.TransportFactory;
import ui.map.geometry.GeographicLine;

public class DijkstraTest {

    @Test
    public void testForRouteWithBusNumber2() {
        Location start = new Location(50.848101, 5.722739);
        Location destination = new Location(50.836348, 5.726151);
        Location[] closestStarts = PostalCodeManager.getClosestPoint(start, 1);
        Location[] closestDestinations = PostalCodeManager.getClosestPoint(destination, 1);

        Route route = Route.empty();
        route.addTransport(TransportFactory.createWalking(start, closestStarts[0]));
        route.addTransport(new DijkstraAlgorithm<>().getShortestPath(MapManager.getBusGraph(), closestStarts[0], closestDestinations[0], Time.of(25200), Time.of(0, 30, 0)));
        route.addTransport(TransportFactory.createWalking(closestDestinations[0], destination));

        assertEquals(route.getTime(), Time.of(360));

        GeographicLine line = route.getLine();

        assert(((BusStop) line.getLocations().get(0)).getStopName().contains("Maastricht, Scharn Kerk"));
        assert(((BusStop) line.getLocations().get(1)).getStopName().contains("Maastricht, Burgemeester Cortenstraat"));
        assert(((BusStop) line.getLocations().get(2)).getStopName().contains("Maastricht, Dorpstraat/De Leim"));
        assert(((BusStop) line.getLocations().get(3)).getStopName().contains("Maastricht, Dorpstraat/Kerk"));
        assert(((BusStop) line.getLocations().get(4)).getStopName().contains("Maastricht, Rijksweg/Veldstraat"));

        assertEquals("Walking", route.getTransfers().get(0).toString());
        assertEquals("Walking", route.getTransfers().get(route.getTransfers().size() - 1).toString());
        assertEquals("6 minutes.", route.getTime().toString());
        route.toString();
        route.getTransfers();
    }

    @Test
    public void testForSameStop() {
        try {
            Location[] closestStarts = PostalCodeManager.getClosestPoint(new Location(50.848101, 5.722739), 1);
            Location[] closestDestinations = PostalCodeManager.getClosestPoint(new Location(50.848101, 5.722739), 1);
            new DijkstraAlgorithm<Point2D>().getShortestPath(MapManager.getBusGraph(), closestStarts[0], closestDestinations[0], Time.of(25200), Time.of(0, 30, 0));
        } catch (IllegalArgumentException e) {
            assert(true);
        }
    }

    @Test
    public void testForNoWalkTime() {
        try {
            Location[] closestStarts = PostalCodeManager.getClosestPoint(new Location(50.848101, 5.722739), 1);
            Location[] closestDestinations = PostalCodeManager.getClosestPoint(new Location(50.848101, 5.722739), 1);
            new DijkstraAlgorithm<Point2D>().getShortestPath(MapManager.getBusGraph(), closestStarts[0], closestDestinations[0], Time.of(25200), Time.of(0));
        } catch (IllegalArgumentException e) {
            assert(true);
        }
    }
}
