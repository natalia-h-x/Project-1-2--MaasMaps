package core.models;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.HashMap;

import core.managers.DistanceManager;
import core.managers.MapManager;

/**
 * This class represents a location in the map, based on latitude and longitude.
 *
 * @author
 */
public class Location extends Point2D {
    private static HashMap<Location, Point> convertedPoints = new HashMap<>();

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

        return java.lang.Double.doubleToLongBits(longitude) == java.lang.Double.doubleToLongBits(other.longitude);
    }

    @Override
    public double distance(Point2D destination) {
        if (destination instanceof Location l)
            return DistanceManager.haversine(this, l) * 1000;

        return super.distance(destination);
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
