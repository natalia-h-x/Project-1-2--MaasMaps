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

    public static List<AccessibilityMeasure> getTop5() {
        List<AccessibilityMeasure> accessibilityMeasure = new ArrayList<>();
        List<AccessibilityMeasure> sortedAccessibilityList = getSortedAccessibilityList();

        for (int i = 0; i < 5; i++) {
            accessibilityMeasure.add(sortedAccessibilityList.get(i));
        }

        return accessibilityMeasure;
    }

    public static List<AccessibilityMeasure> getBottom5() {
        List<AccessibilityMeasure> accessibilityMeasure = new ArrayList<>();
        List<AccessibilityMeasure> sortedAccessibilityList = getSortedAccessibilityList();

        for (int i = sortedAccessibilityList.size() - 1; i >= sortedAccessibilityList.size() - 5; i--) {
            accessibilityMeasure.add(sortedAccessibilityList.get(i));
        }

        return accessibilityMeasure;
    }

    public static double getMeanAccessibility() {
        double mean = 0;
        List<AccessibilityMeasure> sortedAccessibilityList = getSortedAccessibilityList();
        
        for (AccessibilityMeasure accessibilityMeasure : sortedAccessibilityList) {
            mean += accessibilityMeasure.getAccessibility();
        }

        return mean/sortedAccessibilityList.size();
    }

    public static double getAccessibilityMetric(double position) {
        List<AccessibilityMeasure> sortedAccessibilityList = getSortedAccessibilityList();

        return sortedAccessibilityList.get((int) (position * sortedAccessibilityList.size())).getAccessibility();
    }

    public static List<AccessibilityMeasure> getSortedAccessibilityList() {
        List<AccessibilityMeasure> sortedList = new ArrayList<>();

        for (ZipCode zipCode : Context.getContext().getZipCodeDatabase().getZipCodes()) {
            String postalCode = zipCode.getCode();
            double accessibility = PostalCodeAccessibilityManager.getAccessibilityMetric(postalCode);
            sortedList.add(new AccessibilityMeasure(postalCode, accessibility));
        }

        Collections.sort(sortedList, (a, a2) -> Double.compare(a2.getAccessibility(), a.getAccessibility()));

        return sortedList;
    }
}
