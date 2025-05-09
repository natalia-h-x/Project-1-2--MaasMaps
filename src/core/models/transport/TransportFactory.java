package core.models.transport;

import core.algorithms.PathStrategy;
import core.models.BusStop;
import core.models.Location;
import core.models.gtfs.Time;
import core.models.gtfs.Trip;
import core.models.transport.Bus.RouteType;

public class TransportFactory {
    private TransportFactory() {}

    public static Walking emptyWalking() {
        return new Walking();
    }

    public static Biking emptyBiking() {
        return new Biking();
    }

    public static Bus emptyBus() {
        return new Bus();
    }

    public static Walking copyWalking(Walking walking) {
        return createWalking(walking.getStart(), walking.getDestination(), walking.getTime());
    }

    public static Biking copyBiking(Biking biking) {
        return createBiking(biking.getStart(), biking.getDestination(), biking.getTime());
    }

    public static Bus copyBus(Bus bus) {
        return createBus(bus.getStart(), bus.getDestination(), bus.getTime(), bus.getTrip(), bus.getBusStop(), bus.getRouteType(), bus.getPathStrategy());
    }

    public static Walking createWalking(Location start, Location destination) {
        return new Walking(start, destination);
    }

    public static Biking createBiking(Location start, Location destination) {
        return new Biking(start, destination);
    }

    public static Bus createBus(Location start, Location destination) {
        return new Bus(start, destination);
    }

    public static Walking createWalking(Location start, Location destination, Time time) {
        Walking walking = new Walking(start, destination);
        walking.setTime(time);

        return walking;
    }

    public static Biking createBiking(Location start, Location destination, Time time) {
        Biking biking = new Biking(start, destination);
        biking.setTime(time);

        return biking;
    }

    public static Bus createBus(Location start, Location destination, Time time) {
        Bus bus = new Bus(start, destination);
        bus.setTime(time);

        return bus;
    }

    public static Bus createBus(Location start, Location destination, Trip trip) {
        Bus bus = new Bus(start, destination);
        bus.setTrip(trip);

        return bus;
    }

    public static Bus createBus(Location start, Location destination, Time time, Trip trip) {
        Bus bus = new Bus(start, destination);
        bus.setTime(time);
        bus.setTrip(trip);

        return bus;
    }

    public static Bus createBus(Location start, Location destination, Time time, Trip trip, BusStop busStop, RouteType routeType, PathStrategy<Location> pathStrategy) {
        Bus bus = new Bus(start, destination);
        bus.setTime(time);
        bus.setTrip(trip);
        bus.setBusStop(busStop);
        bus.setRouteType(routeType);
        bus.setPathStrategy(pathStrategy);

        return bus;
    }
}
