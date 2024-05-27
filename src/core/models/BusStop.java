package core.models;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Exclude;

@Data
@EqualsAndHashCode(callSuper=true)
public class BusStop extends Location {
    private String stopName;
    @Exclude
    private Set<Route> routes;

    public BusStop(double latitude, double longitude) {
        super(latitude, longitude);
        routes = new HashSet<>();
    }

    public BusStop(double latitude, double longitude, String stopName) {
        this(latitude, longitude);
        this.stopName = stopName;
    }

    public void addRoute(Route route) {
        routes.add(route);
    }
}
