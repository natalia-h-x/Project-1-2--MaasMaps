package transport;
import models.Location;

public interface TransportMode {

    default double calculateTravelTime(Location loc1, Location loc2) {
        double distance = loc1.distanceTo(loc2);
        return distance / getAverageSpeed();
    }

    abstract String toString();
    abstract double getAverageSpeed();
}
