package core.models.transport;

public class Bus implements TransportMode {
    
    final double AVERAGE_SPEED = 1200; // meters per minute

    public double getAverageSpeed() {
        return AVERAGE_SPEED;
    }

    public String toString() {
        return "Take Bus";
    }
}
