package core.models.transport;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import core.Constants;
import core.Context;
import core.algorithms.DijkstraAlgorithm;
import core.managers.MapManager;
import core.models.Location;
import core.models.Time;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ui.map.geometry.ImageMarkerFactory;
import ui.map.geometry.Radius;
import ui.map.geometry.factories.LineFactory;
import ui.map.geometry.interfaces.MapGraphics;

@Data
@EqualsAndHashCode(callSuper = true)
public class Bus extends TransportMode {
    private Time departingTime = Time.of(25200);
    private boolean allowTransfers = false;
    private Optional<Transport> shortestRoute = Optional.empty();
    private Optional<Transport> shortestManualRoute = Optional.empty();
    private Optional<Transport> shortestVehicleRoute = Optional.empty();
    private static final double AVERAGE_SPEED = 333; // meters per minute

    public double getAverageSpeed() {
        return AVERAGE_SPEED;
    }

    public String toString() {
        return "Take Bus";
    }

    @Override
    public void dispose() {
        shortestRoute = Optional.empty();
        shortestManualRoute = Optional.empty();
        shortestVehicleRoute = Optional.empty();
    }

    public Transport getChosenRoute() {
        try {
            return getShortestRoute();
        }
        catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Cannot find a connection between these postal codes.");
        }
    }

    // Je kan die bus pakken en die bus pakken, je kan dan kiezen welke bus je neemt. een aantal opties
    public Transport getShortestRoute() {
        if (!shortestRoute.isPresent())
            calculateShortestPath();

        return shortestRoute.orElseThrow();
    }

    public Transport getShortestManualRoute() {
        if (!shortestManualRoute.isPresent())
            calculateShortestPath();

        return shortestManualRoute.orElseThrow();
    }

    public Transport getShortestVehicleRoute() {
        if (!shortestVehicleRoute.isPresent())
            calculateShortestPath();

        return shortestVehicleRoute.orElseThrow();
    }

    public int getTransfers() {
        return getChosenRoute().getTransfers().size() - 1;
    }

    public void calculateShortestPath() {
        List<Transport> routes = new ArrayList<>();
        List<Location> locationsIntoRadius = MapManager.getAllPointsWithin(getStart(), Context.getContext().getMap().getRadius());
        Location[] closestStarts = MapManager.getClosestPoint(getStart(), Constants.Map.POSTAL_CODE_MAX_BUS_OPTIONS);
        Location[] closestDestinations = MapManager.getClosestPoint(getDestination(), Constants.Map.POSTAL_CODE_MAX_BUS_OPTIONS);

        for (int i = 0; i < closestStarts.length; i++) {
            if (!locationsIntoRadius.contains(closestStarts[i]))
                closestStarts[i] = null;
        }

        for (int i = 0; i < closestStarts.length; i++) {
            for (int j = 0; j < closestDestinations.length; j++) {
                try {
                    if (closestStarts[i] != null) {
                        TransportMode manualSource = new Walking();
                        TransportMode manualDestination = new Walking();
                        manualSource.setStart(getStart());
                        manualSource.setDestination(closestStarts[i]);
                        manualDestination.setStart(closestDestinations[j]);
                        manualDestination.setDestination(getDestination());

                        Transport route = DijkstraAlgorithm.shortestPath(MapManager.getBusGraph(), closestStarts[i], closestDestinations[j], departingTime.add(manualSource.getTravelTime()));
                        route.setManualTransportModeA(manualSource);
                        route.setManualTransportModeB(manualDestination);

                        routes.add(route);
                    }
                }
                catch (IllegalArgumentException e) {
                    // System.out.println("Could not find a route here.");
                }
            }
        }

        shortestRoute = Optional.empty();
        shortestManualRoute = Optional.empty();
        shortestVehicleRoute = Optional.empty();

        double shortestDistance = Double.POSITIVE_INFINITY;
        double shortestManualDistance = Double.POSITIVE_INFINITY;
        double shortestVehicleDistance = Double.POSITIVE_INFINITY;

        for (Transport route : routes) {
            double manualDistanceA = route.getManualTransportModeA().getTravelTime().toSeconds();
            double manualDistanceB = route.getManualTransportModeB().getTravelTime().toSeconds();
            double manualDistance = manualDistanceA + manualDistanceB;
            double vehicleDistance = route.getTime().toSeconds() - manualDistanceA;
            double distance = vehicleDistance + manualDistance;

            if (vehicleDistance == 0)
                continue;

            if (distance < shortestDistance) {
                shortestDistance = distance;
                shortestRoute = Optional.of(route);
            }

            if (manualDistance < shortestManualDistance) {
                shortestManualDistance = manualDistance;
                shortestManualRoute = Optional.of(route);
            }

            if (vehicleDistance < shortestVehicleDistance) {
                shortestVehicleDistance = vehicleDistance;
                shortestVehicleRoute = Optional.of(route);
            }
        }
    }

    @Override
    public Time getTravelTime() {
        Transport chosenRoute = getChosenRoute();
        return chosenRoute.getTime()
                          .add(chosenRoute.getManualTransportModeA().getTravelTime())
                          .add(chosenRoute.getManualTransportModeB().getTravelTime());
    }

    @Override
    public MapGraphics[] getGraphics() {
        return new MapGraphics[] {
            new Radius((int) getStart().getX(), (int) getStart().getY(), (int) Context.getContext().getMap().getRadius()),
            new Radius((int) getDestination().getX(), (int) getDestination().getY(), (int) Context.getContext().getMap().getRadius()),
            getChosenRoute().getLine(),
            LineFactory.createResultsLine(getChosenRoute().getManualTransportModeA().getStart(), getChosenRoute().getManualTransportModeA().getDestination()),
            LineFactory.createResultsLine(getChosenRoute().getManualTransportModeB().getStart(), getChosenRoute().getManualTransportModeB().getDestination()),
            ImageMarkerFactory.createAImageMarker(getStart()),
            ImageMarkerFactory.createBImageMarker(getDestination())
        };
    }
}
