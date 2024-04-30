package core.managers;

import core.models.Location;

/**
 * This class represents the distance calculator to calculate the distance in kilometers between two locations using the haversine function.
 * 
 * @author Kimon Navridis
 */
public class DistanceManager {
    private DistanceManager() {}

    public static double haversine(Location loc1, Location loc2) {
        return haversine(loc1.getLatitude(), loc1.getLongitude(), loc2.getLatitude(), loc2.getLongitude());
    }

	/**
     * @see https://en.wikipedia.org/wiki/Haversine_formula/
     */
    public static double haversine(double lat1, double lon1, double lat2, double lon2) {
        final int EARTH_RADIUS = 6371000; // radius of the earth in meters (assumes non ellipsoidal sphere-- reduced complexity)

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (EARTH_RADIUS * c)/1000; // distance in km
	}
}

