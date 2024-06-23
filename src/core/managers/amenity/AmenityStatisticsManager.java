package core.managers.amenity;

public class AmenityStatisticsManager {
    private AmenityStatisticsManager() {}

    public static double getMaxAccessibilityMetric() {
        return getAccessibilityMetric(1);
    }

    public static double getMinAccessibilityMetric() {
        return getAccessibilityMetric(0);
    }

    public static double getMedianAccessibilityMetric() {
        return getAccessibilityMetric(0.5);
    }

    public static double getAccessibilityMetric(double percentage) {
        return Math.random(); // Alexandra
    }
}
