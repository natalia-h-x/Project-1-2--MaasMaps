package core.algorithms;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import core.Constants;
import core.Context;
import core.algorithms.datastructures.Graph;
import core.managers.MapManager;
import core.models.Location;
import core.models.gtfs.Time;
import core.models.transport.Bus;
import core.models.transport.Route;
import core.models.transport.Transport;
import core.models.transport.Walking;

public abstract class PathStrategy<T extends Point2D> {
    private static Map<Bus, Optional<Route>> shortestPaths = new HashMap<>();

    public abstract Transport[] shortestPath(Graph<T> graph, T source, T end, Time startTime) throws IllegalArgumentException;

    public Optional<Route> calculateShortestPath(Bus bus) {
        if (shortestPaths.containsKey(bus))
            return shortestPaths.get(bus);

        List<Route> routes = new ArrayList<>();
        List<Location> locationsIntoRadius1 = MapManager.getAllPointsWithin(bus.getStart(), Context.getContext().getMap().getRadius());
        List<Location> locationsIntoRadius2 = MapManager.getAllPointsWithin(bus.getDestination(), Context.getContext().getMap().getRadius());
        Location[] closestStarts = MapManager.getClosestPoint(locationsIntoRadius1, bus.getStart(), Constants.Map.POSTAL_CODE_MAX_BUS_OPTIONS);
        Location[] closestDestinations = MapManager.getClosestPoint(locationsIntoRadius2, bus.getDestination(), Constants.Map.POSTAL_CODE_MAX_BUS_OPTIONS);

        if (closestStarts.length <= 0 || closestDestinations.length <= 0)
            throw new IllegalArgumentException("could not find any bus stops to depart from");

        for (int i = 0; i < closestStarts.length; i++) {
            for (int j = 0; j < closestDestinations.length; j++) {
                try {
                    Route route = Route.empty();
                    Transport manualSource = new Walking();
                    Transport manualDestination = new Walking();
                    manualSource.setStart(bus.getStart());
                    manualSource.setDestination(closestStarts[i]);
                    manualDestination.setStart(closestDestinations[j]);
                    manualDestination.setDestination(bus.getDestination());

                    route.addTransport(manualSource);
                    route.addTransport(shortestPath(
                        (Graph<T>) MapManager.getBusGraph(),
                        (T) closestStarts[i], (T) closestDestinations[j],
                        bus.getDepartingTime().add(manualSource.getTravelTime())
                    ));
                    route.addTransport(manualDestination);

                    routes.add(route);
                }
                catch (IllegalArgumentException e) {
                    // System.out.println("Could not find a route here.");
                }
            }
        }

        Optional<Route> shortestRoute = Optional.empty();
        double shortestTime = Double.POSITIVE_INFINITY;

        for (Route route : routes) {
            Time manual = route.getManualTravelTime();
            Time vehicle = route.getVehicleTravelTime();

            if (vehicle.isEmpty())
                continue;

            Time time = bus.getRouteType().apply(manual, vehicle);

            if (time.getSeconds() < shortestTime) {
                shortestTime = time.getSeconds();
                shortestRoute = Optional.of(route);
            }
        }

        shortestPaths.put(bus, shortestRoute);

        return shortestRoute;
    }
}
