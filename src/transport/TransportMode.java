package transport;
import models.Location;

/**
 * Interface that gets implemented but all different means of transport.
 * 
 * @author Kimon Navridis
 */
public interface TransportMode {

    default double calculateTravelTime(Location loc1, Location loc2) {
        double distance = loc1.distanceTo(loc2);
        return (distance*1000) / getAverageSpeed();
    }

    abstract String toString();
    abstract double getAverageSpeed();
}
