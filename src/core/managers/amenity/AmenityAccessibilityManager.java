package core.managers.amenity;

import java.io.File;
import java.io.IOException;

import core.Constants;
import core.managers.FileManager;

public class AmenityAccessibilityManager {
    private AmenityAccessibilityManager() {}

    private static String[] lines;

    public static double getAccessibilityMetric(String postalCode) {
        double accessibility = 0;

        try {
            if (lines == null)
                lines = FileManager.readLines(new File(Constants.Paths.ACCESSIBILITY_FILE));

            for (String line : lines) {
                String[] parts = line.split(",");

                if (parts.length == 4 && parts[0].equals(postalCode))
                    accessibility = Double.parseDouble(parts[3]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }   

        return accessibility;
    }

    public int getAmenityFrequency(String type, String amenity) {
        return AmenitySerializationManager.getGeoData(type, amenity).size();
    }
}
