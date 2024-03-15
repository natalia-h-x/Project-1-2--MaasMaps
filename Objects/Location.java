package Objects;

import CalculatorFunctions.DistanceCalculator;

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
        return DistanceCalculator.haversine(this, destination);
    }

    public String toString() {
        return "[Lantitude: " + getLatitude() + ", " + "Longitude: " + getLongitude() + "]";
    }

}
