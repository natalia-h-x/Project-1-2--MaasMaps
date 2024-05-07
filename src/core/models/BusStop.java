package core.models;

import java.awt.Color;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class BusStop extends Location {
    private Color routeColor;

    public BusStop(double latitude, double longitude) {
        super(latitude, longitude);
    }
}
