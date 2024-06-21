package core.models.geojson;

import core.models.Location;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class GeoData {
    private Location location;
    private String id;

    public abstract double getWeight();
}
