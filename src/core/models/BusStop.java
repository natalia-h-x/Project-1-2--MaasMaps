package core.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class BusStop extends Location {
    private Route route;
    private String stopName;

    public BusStop(double latitude, double longitude) {
        super(latitude, longitude);
    }

    public BusStop(double latitude, double longitude, String stopName, Route route) {
        this(latitude, longitude);
        this.stopName = stopName;
        this.route = route;
    }
}
