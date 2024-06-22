package core.managers.amenity;

public class AmenityAccessibilityManager {
    private AmenityAccessibilityManager() {}

    public static double getAccessibilityMetric(String postalCode) {
        double accessibility = 0;
        return accessibility;
    }

    public int getAmenityFrequency(String type, String amenity) {
        return AmenitySerializationManager.getGeoData(type, amenity).size();
    }
}
