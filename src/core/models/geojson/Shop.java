package core.models.geojson;

import core.models.Location;

public class Shop extends GeoData {
    public static final double WEIGHT = 1;

    public Shop(Location location, String id) {
        super(location, id);
    }

    @Override
    public double getWeight() {
        return WEIGHT;
    }
}
