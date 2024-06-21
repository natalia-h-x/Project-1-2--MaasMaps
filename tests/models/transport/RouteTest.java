package models.transport;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import core.models.Location;
import core.models.gtfs.Time;
import core.models.gtfs.Trip;
import core.models.transport.Route;
import core.models.transport.TransportFactory;

public class RouteTest {
    @Test
    public void pruneTest1() {
        Route route = Route.empty();
        route.addTransport(
            TransportFactory.createWalking(new Location(0, 0), new Location(0, 1)),
            TransportFactory.createWalking(new Location(0, 1), new Location(1, 1)),
            TransportFactory.createWalking(new Location(1, 1), new Location(1, 2)),
            TransportFactory.createWalking(new Location(1, 2), new Location(3, 2))
        );

        assertEquals(1, route.getTransfers().size());
    }

    @Test
    public void pruneTest2() {
        Route route = Route.empty();
        route.addTransport(
            TransportFactory.createWalking(new Location(0, 0), new Location(0, 1)),
            TransportFactory.createWalking(new Location(0, 1), new Location(1, 1)),
            TransportFactory.createWalking(new Location(1, 1), new Location(1, 2)),
            TransportFactory.createWalking(new Location(1, 2), new Location(3, 2)),
            TransportFactory.createBus(new Location(3, 2), new Location(1, 2)),
            TransportFactory.createBus(new Location(1, 2), new Location(2, 2)),
            TransportFactory.createBus(new Location(2, 2), new Location(3, 2)),
            TransportFactory.createBus(new Location(3, 2), new Location(4, 2)),
            TransportFactory.createBus(new Location(4, 2), new Location(5, 2)),
            TransportFactory.createBus(new Location(5, 2), new Location(6, 2)),
            TransportFactory.createBus(new Location(6, 2), new Location(7, 2)),
            TransportFactory.createBus(new Location(7, 2), new Location(8, 2)),
            TransportFactory.createBus(new Location(8, 2), new Location(9, 2))
        );

        assertEquals(2, route.getTransfers().size());
    }

    @Test
    public void pruneTestTime() {
        Route route = Route.empty();
        route.addTransport(
            TransportFactory.createBus(new Location(3, 2), new Location(1, 2), Time.of(1, 0, 1)),
            TransportFactory.createBus(new Location(1, 2), new Location(2, 2), Time.of(1, 0, 1)),
            TransportFactory.createBus(new Location(2, 2), new Location(3, 2), Time.of(1, 0, 1)),
            TransportFactory.createBus(new Location(3, 2), new Location(4, 2), Time.of(1, 0, 1)),
            TransportFactory.createBus(new Location(4, 2), new Location(5, 2), Time.of(1, 0, 1)),
            TransportFactory.createBus(new Location(5, 2), new Location(6, 2), Time.of(1, 0, 1)),
            TransportFactory.createBus(new Location(6, 2), new Location(7, 2), Time.of(1, 0, 1)),
            TransportFactory.createBus(new Location(7, 2), new Location(8, 2), Time.of(1, 0, 1)),
            TransportFactory.createBus(new Location(8, 2), new Location(9, 2), Time.of(1, 0, 1))
        );

        assertEquals(1, route.getTransfers().size());
        assertEquals(Time.of(9, 0, 9), route.getTime());
    }

    @Test
    public void pruneTestTrip() {
        Route route = Route.empty();
        route.addTransport(
            TransportFactory.createBus(new Location(3, 2), new Location(1, 2), new Trip(1, 1, 1, "trip 1")),
            TransportFactory.createBus(new Location(1, 2), new Location(2, 2), new Trip(2, 2, 2, "trip 2")),
            TransportFactory.createBus(new Location(2, 2), new Location(3, 2), new Trip(3, 3, 3, "trip 3")),
            TransportFactory.createBus(new Location(3, 2), new Location(4, 2), new Trip(4, 4, 4, "trip 4")),
            TransportFactory.createBus(new Location(4, 2), new Location(5, 2), new Trip(5, 5, 5, "trip 5")),
            TransportFactory.createBus(new Location(5, 2), new Location(6, 2), new Trip(6, 6, 6, "trip 6")),
            TransportFactory.createBus(new Location(6, 2), new Location(7, 2), new Trip(7, 7, 7, "trip 7")),
            TransportFactory.createBus(new Location(7, 2), new Location(8, 2), new Trip(8, 8, 8, "trip 8")),
            TransportFactory.createBus(new Location(8, 2), new Location(9, 2), new Trip(9, 9, 9, "trip 9"))
        );

        assertEquals(9, route.getTransfers().size());

        route = Route.empty();
        route.addTransport(
            TransportFactory.createBus(new Location(3, 2), new Location(1, 2), new Trip(1, 1, 1, "trip 1")),
            TransportFactory.createBus(new Location(1, 2), new Location(2, 2), new Trip(1, 1, 1, "trip 1")),
            TransportFactory.createBus(new Location(2, 2), new Location(3, 2), new Trip(3, 3, 3, "trip 3")),
            TransportFactory.createBus(new Location(3, 2), new Location(4, 2), new Trip(2, 2, 2, "trip 2")),
            TransportFactory.createBus(new Location(4, 2), new Location(5, 2), new Trip(2, 2, 2, "trip 2")),
            TransportFactory.createBus(new Location(5, 2), new Location(6, 2), new Trip(2, 2, 2, "trip 2")),
            TransportFactory.createBus(new Location(6, 2), new Location(7, 2), new Trip(7, 7, 7, "trip 7")),
            TransportFactory.createBus(new Location(7, 2), new Location(8, 2), new Trip(8, 8, 8, "trip 8")),
            TransportFactory.createBus(new Location(8, 2), new Location(9, 2), new Trip(9, 9, 9, "trip 9"))
        );

        assertEquals(6, route.getTransfers().size());
    }
}
