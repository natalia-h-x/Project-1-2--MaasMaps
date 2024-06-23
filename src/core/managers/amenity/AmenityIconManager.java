package core.managers.amenity;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.managers.FileManager;
import core.models.Location;
import core.models.geojson.GeoData;

public class AmenityIconManager {
    private AmenityIconManager() {}

    private static Map<String, BufferedImage> icons;
    private static Map<BufferedImage, List<Location>> locationOfIcon;

    public static void loadIcon() {
        Map<String, List<GeoData>> geoData = AmenitySerializationManager.getGeoData();

        icons = new HashMap<>();
        locationOfIcon = new HashMap<>();

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
                    locationOfIcon.computeIfAbsent(icons.get(data.getKey()), s -> new ArrayList<>());
                    locationOfIcon.get(icons.get(data.getKey())).add(amenity.getLocation());
                } catch (Exception e) {}
            }
        }
    }

    public static Map<String, BufferedImage> getAmenityIcons() {
        if (icons == null)
            loadIcon();

        return icons;
    }

    public static Map<BufferedImage, List<Location>> getLocationsOfIcons() {
        if (locationOfIcon == null) {
            loadIcon();
        }

        return locationOfIcon;
    }

    public static BufferedImage getIcon(String type) {
        if (icons == null)
            loadIcon();

        return icons.get(type);
    }
}
