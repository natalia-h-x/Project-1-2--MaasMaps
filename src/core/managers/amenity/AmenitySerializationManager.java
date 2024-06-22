package core.managers.amenity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.managers.serialization.JSONSerializationManager;
import core.models.Location;
import core.models.geojson.GeoData;
import core.models.serialization.Serializable;

public class AmenitySerializationManager {
    private static Map<String, List<GeoData>> geoData = new HashMap<>();

    private AmenitySerializationManager() {}

    private static List<GeoData> amenities(String type) {
        List<GeoData> data = new ArrayList<>();

        try {
            Serializable serializable = JSONSerializationManager.deSterializeJSON(new String(Files.readAllBytes(Paths.get(String.format(core.Constants.Paths.GEOJSON, type.toLowerCase())))));

            List<Object> features = serializable.getArrays().get("features");
            for (Object obj : features) {
                String geoDataType = type;
                Serializable feature = (Serializable) obj;

                Serializable properties = feature.getObjects().get("properties");
                Serializable geometry = feature.getObjects().get("geometry");

                if (geoDataType.equals("amenity")) {
                    geoDataType = (String) properties.get("amenity");
                }

                String[] coordinates = geometry.getArray("coordinates").toArray(String[]::new);
                String id = (String) feature.get("id");

                data.add(GeoData.of(new Location(Double.parseDouble(coordinates[1]), Double.parseDouble(coordinates[0])), id, geoDataType));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static List<GeoData> getGeoData(String type) {
        geoData.computeIfAbsent(type, s -> amenities(type));
        return geoData.get(type);
    }
}
