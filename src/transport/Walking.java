package transport;

public class Walking implements TransportMode {
    final double AVERAGE_SPEED = 83.33333; // meters per minute

    public double getAverageSpeed() {
        return AVERAGE_SPEED;
    }

    public String toString() {
        return "walking";
    }
}
