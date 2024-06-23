package core.managers.amenity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import core.Context;
import core.models.AccessibilityMeasure;
import core.models.ZipCode;

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

    public static double getAccessibilityMetric(double position) {
        List<Double> sortedAccessibilityList = getSortedAccessibilityList();

        return sortedAccessibilityList.get((int) (position * sortedAccessibilityList.size()));
    }

    public static List<AccessibilityMeasure> getSortedAccessibilityList() {
        List<AccessibilityMeasure> sortedList = new ArrayList<>();

        for (ZipCode zipCode : Context.getContext().getZipCodeDatabase().getZipCodes()) {
            String postalCode = zipCode.getCode();
            double accessibility = PostalCodeAccessibilityManager.getAccessibilityMetric(postalCode);
            sortedList.add(new AccessibilityMeasure(postalCode, accessibility));
        }

        Collections.sort(sortedList, (a, a2) -> Double.compare(a.getAccessibility(), a2.getAccessibility()));

        return sortedList;
    }
}
