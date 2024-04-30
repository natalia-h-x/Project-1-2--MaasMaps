package core.models.transport;

/**
 * This class represents the transport mode walking.
 * 
 * @author Kimon Navridis
 */
public class Walking implements TransportMode {
    private static final double AVERAGE_SPEED = 83.33333; // meters per minute

    public double getAverageSpeed() {
        return AVERAGE_SPEED;
    }

    public String toString() {
        return "walking";
    }
}
