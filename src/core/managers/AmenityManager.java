package core.managers;

/**
 * Class for interacting with the data from Geo JSON files.
 *
 * @author Kimon
 */
public class AmenityManager {
    /**
     * gets the Accessibility Metric.
     *
     * @param postalCode
     * @return a accessibility metric ranging from 0 - 1.
     */
    public static double getAccessibilityMetric(String postalCode) {
        return Math.random();
    }
}
