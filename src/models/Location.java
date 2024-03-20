package models;

import algorithms.utils.DistanceManager;

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

    public double distanceTo(Location destination) {
        return DistanceManager.haversine(this, destination);
    }

    public String toString() {
        return "[Latitude: " + getLatitude() + ", " + "Longitude: " + getLongitude() + "]";
    }
}
