package transport;

public class Biking implements TransportMode {
    final double AVERAGE_SPEED = 291.66666;

    public double getAverageSpeed() {
        return AVERAGE_SPEED;
    }

    public String toString() {
        return "biking";
    }
}