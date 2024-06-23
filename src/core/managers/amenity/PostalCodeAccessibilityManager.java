package core.managers.amenity;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import core.Constants;
import core.managers.FileManager;

public class PostalCodeAccessibilityManager {
    private PostalCodeAccessibilityManager() {}

    private static Map<String, Double> accessibilityMap;

    private static double loadAccessibilityMetric() {
        double accessibility = 0;
        accessibilityMap = new HashMap<>();

        try {
            String[] lines = FileManager.readLines(new File(Constants.Paths.ACCESSIBILITY_FILE));

            for (int i = 1; i < lines.length; i++) {
                String line = lines[i];
                String[] parts = line.split(",");

                if (parts.length == 4)
                    accessibilityMap.computeIfAbsent(parts[0], s -> Double.parseDouble(parts[3]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return accessibility;
    }

    public static double getAccessibilityMetric(String postalCode) {
        if (accessibilityMap == null) {
            loadAccessibilityMetric();
        }

        return accessibilityMap.get(postalCode);
    }

    public static int getAmenityFrequency(String type) {
        return AmenitySerializationManager.getGeoData(type.equals("shop") || type.equals("tourism")? type : "amenity", type).size();
    }
}
