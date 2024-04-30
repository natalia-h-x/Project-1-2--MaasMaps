package core.models.transport;

/**
 * This class represents the transport mode biking.
 * 
 * @author Kimon Navridis
 */
public class Biking implements TransportMode {
    private static final double AVERAGE_SPEED = 291.66666;

    public double getAverageSpeed() {
        return AVERAGE_SPEED;
    }

    public String toString() {
        return "biking";
    }
}