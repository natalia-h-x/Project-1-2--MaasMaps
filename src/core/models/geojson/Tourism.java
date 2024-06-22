package core.models.geojson;

import core.models.Location;

public class Tourism extends GeoData {
    public static final double WEIGHT = 0.7;

    public Tourism(Location location, String id) {
        super(location, id);
    }

    @Override
    public double getWeight() {
        return WEIGHT;
    }
}
