package core.managers.amenity;

import java.util.ArrayList;
import java.util.List;

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

    public static List<Double> getSortedAccessibilityList() {
        List<Double> sortedList = new ArrayList<>();

        return sortedList;
    }
}
