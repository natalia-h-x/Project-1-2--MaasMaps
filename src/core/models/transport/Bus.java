package core.models.transport;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import core.Constants;
import core.algorithms.DijkstraAlgorithm;
import core.managers.MapManager;
import core.models.GTFSTime;
import core.models.Location;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ui.map.geometry.ImageMarkerFactory;
import ui.map.geometry.factories.LineFactory;
import ui.map.geometry.interfaces.MapGraphics;

@Data
@EqualsAndHashCode(callSuper = true)
public class Bus extends TransportMode {
    private GTFSTime departingTime = GTFSTime.of(0);
    private Optional<Route> shortestRoute = Optional.empty();
    private Optional<Route> shortestManualRoute = Optional.empty();
    private Optional<Route> shortestVehicleRoute = Optional.empty();
    private static final double AVERAGE_SPEED = 333; // meters per minute

    public double getAverageSpeed() {
        return AVERAGE_SPEED;
    }

    public String toString() {
        return "Take Bus";
    }

    public void dispose() {
        shortestRoute = Optional.empty();
        shortestManualRoute = Optional.empty();
        shortestVehicleRoute = Optional.empty();
    }

    public Route getChosenRoute() {
        return getShortestRoute();
    }

    // Je kan die bus pakken en die bus pakken, je kan dan kiezen welke bus je neemt. een aantal opties
    public Route getShortestRoute() {
        if (!shortestRoute.isPresent())
            calculateShortestPath();

        return shortestRoute.orElseThrow();
    }

    public Route getShortestManualRoute() {
        if (!shortestManualRoute.isPresent())
            calculateShortestPath();

        return shortestManualRoute.orElseThrow();
    }

    public Route getShortestVehicleRoute() {
        if (!shortestVehicleRoute.isPresent())
            calculateShortestPath();

        return shortestVehicleRoute.orElseThrow();
    }

    public void calculateShortestPath() {
        List<Route> routes = new ArrayList<>();
        Location[] closestStarts = MapManager.getClosestPoint(getStart(), Constants.Map.POSTAL_CODE_MAX_BUS_OPTIONS);
        Location[] closestDestinations = MapManager.getClosestPoint(getDestination(), Constants.Map.POSTAL_CODE_MAX_BUS_OPTIONS);

        for (int i = 0; i < Constants.Map.POSTAL_CODE_MAX_BUS_OPTIONS; i++) {
            for (int j = 0; j < Constants.Map.POSTAL_CODE_MAX_BUS_OPTIONS; j++) {
                Route route = DijkstraAlgorithm.shortestPath(MapManager.getBusGraph(), closestStarts[i], closestDestinations[j], getDepartingTime().clone().add((int) (60 * getStart().distanceTo(closestStarts[i]))));
                route.getManualTransportModeA().setStart(getStart());
                route.getManualTransportModeA().setDestination(closestStarts[i]);
                route.getManualTransportModeB().setStart(closestDestinations[j]);
                route.getManualTransportModeB().setDestination(getDestination());
                routes.add(route);
            }
        }

        shortestRoute = Optional.empty();
        shortestManualRoute = Optional.empty();
        shortestVehicleRoute = Optional.empty();

        double shortestDistance = Double.POSITIVE_INFINITY;
        double shortestManualDistance = Double.POSITIVE_INFINITY;
        double shortestVehicleDistance = Double.POSITIVE_INFINITY;

        for (Route route : routes) {
            double distance = shortestVehicleRoute.orElse(route).getTime().toSeconds();
            double manualDistance  = shortestManualRoute.orElse(route).getManualTransportModeA().getTravelTime().toSeconds();
                   manualDistance += shortestManualRoute.orElse(route).getManualTransportModeB().getTravelTime().toSeconds();
            double vehicleDistance = shortestVehicleRoute.orElse(route).getTime().toSeconds();

            if (distance == 0)
                continue;

            if (distance < shortestDistance) {
                shortestDistance = manualDistance;
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
    public GTFSTime getTravelTime() {
        return getShortestRoute().getTime().add(getShortestRoute().getManualTransportModeA().getTravelTime()).add(getShortestRoute().getManualTransportModeB().getTravelTime());
    }

    @Override
    public MapGraphics[] getGraphics() {
        return new MapGraphics[] {
            getChosenRoute().getLine(),
            LineFactory.createResultsLine(getChosenRoute().getManualTransportModeA().getStart(), getChosenRoute().getManualTransportModeA().getDestination()),
            LineFactory.createResultsLine(getChosenRoute().getManualTransportModeB().getStart(), getChosenRoute().getManualTransportModeB().getDestination()),
            ImageMarkerFactory.createAImageMarker(getStart()),
            ImageMarkerFactory.createBImageMarker(getDestination())
        };
    }
}
