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
import org.json.JSONArray;
import org.json.JSONObject;

public class AmenitySerializationManager {
    private static Map<String, List<GeoData>> geoData = new HashMap<>();

    private AmenitySerializationManager() {}

    private static List<GeoData> amenities(String type) {
        List<GeoData> data = new ArrayList<>();

        try {
            String jsonString = new String(Files.readAllBytes(Paths.get(String.format(core.Constants.Paths.GEOJSON, type.toLowerCase()))));
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray features = jsonObject.getJSONArray("features");

            for (int i = 0; i < features.length(); i++) {
                JSONObject feature = features.getJSONObject(i);
                JSONObject properties = feature.getJSONObject("properties");
                JSONObject geometry = feature.getJSONObject("geometry");

                if (type.equals("amenity")) {
                    type = properties.getString("amenity");
                }

                JSONArray coordinates = geometry.getJSONArray("coordinates");
                String id = feature.getString("id");

                double longitude = coordinates.getDouble(0);
                double latitude = coordinates.getDouble(1);

                data.add(GeoData.of(new Location(latitude, longitude), id, type));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static List<GeoData> getGeoData(String type) {
        geoData.computeIfAbsent(type, s -> amenities(type));
        return geoData.get(type);
    }
}
