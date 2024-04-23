package models;

import algorithms.util.DistanceCalculator;
import lombok.Data;

import java.util.HashMap;

/**
 * This class represents a location in the map, based on latitude and longitude.
 *
 * @author 
 */
@Data
public class Location {
    private double latitude;
    private double longitude;
    private HashMap<Location, Double> distanceToLocationMap;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.distanceToLocationMap = new HashMap<>();
    }

    public double distanceTo(Location destination) {

        if (distanceToLocationMap.containsKey(destination)) {
            return distanceToLocationMap.get(destination);
        }

        double distance = DistanceManager.haversine(this, destination);

        distanceToLocationMap.put(destination, distance);

        return distance;
    }

    public String toString() {
        return "[Latitude: " + getLatitude() + ", " + "Longitude: " + getLongitude() + "]";
    }

    public Location translate(Location location) {
        longitude += location.getLongitude();
        latitude += location.getLatitude();
        return this;
    }
}
