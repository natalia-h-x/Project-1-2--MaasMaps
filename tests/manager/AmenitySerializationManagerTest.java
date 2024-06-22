package manager;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import core.managers.amenity.AmenitySerializationManager;
import core.models.geojson.GeoData;

public class AmenitySerializationManagerTest {
    
    @Test
    public void test() {
        List<GeoData> amenities = new ArrayList<>();

        amenities.addAll(AmenitySerializationManager.getGeoData("amenity"));
        amenities.addAll(AmenitySerializationManager.getGeoData("shop"));
        amenities.addAll(AmenitySerializationManager.getGeoData("tourism"));
    }
}
