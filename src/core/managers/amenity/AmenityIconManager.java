package core.managers.amenity;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import core.managers.FileManager;
import core.models.geojson.GeoData;

public class AmenityIconManager {
    private AmenityIconManager() {}

    private static Map<String, BufferedImage> icons;

    public static void loadIcon() {
        Map<String, List<GeoData>> geoData = AmenitySerializationManager.getGeoData();

        for (Map.Entry<String, List<GeoData>> data: geoData.entrySet()) {
            for (GeoData amenity : data.getValue()) {
                try {
                    icons.computeIfAbsent(data.getKey(), k -> {
                        try {
                            return FileManager.getImage(amenity.getIconPath());
                        } catch (IOException e) {
                            throw new IllegalArgumentException("Could not load image.");
                        }
                    });
                } catch (Exception e) {}
            }
        }
    }

    public static Map<String, BufferedImage> getAmenityIcons() {
        if (icons == null)
            loadIcon();

        return icons;
    }

    public static BufferedImage getIcon(String type) {
        if (icons == null)
            loadIcon();

        return icons.get(type);
    }
}
