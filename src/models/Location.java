package models;

import algorithms.util.DistanceCalculator;
import core.managers.MapManager;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.HashMap;

/**
 * This class represents a location in the map, based on latitude and longitude.
 *
 * @author
 */
public class Location extends Point2D {
    private static HashMap<Location, Point> convertedPoints = new HashMap<>();
    private static HashMap<Location, java.lang.Double> distanceToLocationMap = new HashMap<>();

    private double latitude;
    private double longitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;

        temp = java.lang.Double.doubleToLongBits(latitude);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = java.lang.Double.doubleToLongBits(longitude);
        result = prime * result + (int) (temp ^ (temp >>> 32));

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (getClass() != obj.getClass())
            return false;

        Location other = (Location) obj;

        if (java.lang.Double.doubleToLongBits(latitude) != java.lang.Double.doubleToLongBits(other.latitude))
            return false;

        return (java.lang.Double.doubleToLongBits(longitude) != java.lang.Double.doubleToLongBits(other.longitude));
    }

    public double distanceTo(Location destination) {

        if (distanceToLocationMap.containsKey(destination)) {
            return distanceToLocationMap.get(destination);
        }

        double distance = DistanceCalculator.haversine(this, destination);

        distanceToLocationMap.put(destination, distance);

        return distance;
    }

    public String toString() {
        return "[Latitude: " + getLatitude() + ", " + "Longitude: " + getLongitude() + "]";
    }

    @Override
    public double getX() {
        if (!convertedPoints.containsKey(this)) {
            convertedPoints.put(this, MapManager.locationToPoint(this));
        }

        return convertedPoints.get(this).getX();
    }

    @Override
    public double getY() {
        if (!convertedPoints.containsKey(this)) {
            convertedPoints.put(this, MapManager.locationToPoint(this));
        }

        return convertedPoints.get(this).getY();
    }

    @Override
    public void setLocation(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
