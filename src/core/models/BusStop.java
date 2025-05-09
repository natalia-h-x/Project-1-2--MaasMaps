package core.models;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import core.models.gtfs.Route;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Exclude;

@Data
@EqualsAndHashCode(callSuper=true)
public class BusStop extends Location {
    private int stopId;
    private String stopName;
    @Exclude
    private Set<Route> routes;
    @Exclude
    private Color color;

    public BusStop(double latitude, double longitude) {
        super(latitude, longitude);
        routes = new HashSet<>();
    }

    public BusStop(int stopId, double latitude, double longitude, String stopName) {
        this(latitude, longitude);
        this.stopName = stopName;
        this.stopId = stopId;
    }

    public void addRoute(Route route) {
        routes.add(route);
    }
}
