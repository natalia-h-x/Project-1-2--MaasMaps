package core.managers.amenity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.managers.serialization.JSONSerializationManager;
import core.models.Location;
import core.models.geojson.GeoData;
import core.models.serialization.Serializable;

public class AmenitySerializationManager {
    private static Map<String, Map<String, List<GeoData>>> geoData = new HashMap<>();

    private AmenitySerializationManager() {}

    private static Map<String, List<GeoData>> amenities(String type) {
        Map<String, List<GeoData>> data = new HashMap<>();

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

                data.computeIfAbsent(geoDataType, i -> new ArrayList<>()).add(GeoData.of(new Location(Double.parseDouble(coordinates[1]), Double.parseDouble(coordinates[0])), id, geoDataType));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    public static Map<String, List<GeoData>> getGeoData() {
        getGeoDataList();

        Map<String, List<GeoData>> flatGeoData = new HashMap<>();
        geoData.values().stream().forEach(m -> m.keySet().forEach(k -> flatGeoData.put(k, m.get(k))));
        return flatGeoData;
    }

    public static List<GeoData> getGeoData(String type, String amenity) {
        return geoData.computeIfAbsent(type, s -> amenities(type)).get(amenity);
    }

    public static List<GeoData> getGeoDataList(String type) {
        return geoData.computeIfAbsent(type, s -> amenities(type)).values().stream().flatMap(Collection::stream).toList();
    }

    public static List<GeoData> getGeoDataList() {
        List<GeoData> amenities = new ArrayList<>();

        amenities.addAll(getGeoDataList("amenity"));
        amenities.addAll(getGeoDataList("shop"));
        amenities.addAll(getGeoDataList("tourism"));

        return amenities;
    }
}
