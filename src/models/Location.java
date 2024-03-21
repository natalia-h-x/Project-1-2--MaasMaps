package models;

import algorithms.utils.DistanceManager;

/**
 * This class represents a location in the map, based on latitude and longitude.
 *
 * @author Kimon Navridis
 */
public class Location {
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

        temp = Double.doubleToLongBits(latitude);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
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

        if (Double.doubleToLongBits(latitude) != Double.doubleToLongBits(other.latitude))
            return false;

        return (Double.doubleToLongBits(longitude) != Double.doubleToLongBits(other.longitude));
    }

    public double distanceTo(Location destination) {
        return DistanceManager.haversine(this, destination);
    }

    public String toString() {
        return "[Latitude: " + getLatitude() + ", " + "Longitude: " + getLongitude() + "]";
    }
}
