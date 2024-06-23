package core.managers.amenity;

import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import core.models.geojson.GeoData;

public class AmenityIconManager {
    private AmenityIconManager() {}

    private static Map<String, ImageIcon> icons;

    public static void loadIcon() {
        Map<String, List<GeoData>> geoData = AmenitySerializationManager.getGeoData();

        for (Map.Entry<String, List<GeoData>> data: geoData.entrySet()) {
            for (GeoData amenity : data.getValue()) {
                icons.computeIfAbsent(data.getKey(), k -> new ImageIcon(amenity.getIconPath()));
            }
        }
    }


    public static Map<String, ImageIcon> getAmenityIcons() {
        if (icons == null)
            loadIcon();

        return icons;
    }

    public static ImageIcon getIcon(String type) {
        if (icons == null)
            loadIcon();

        return icons.get(type);
    }
}
