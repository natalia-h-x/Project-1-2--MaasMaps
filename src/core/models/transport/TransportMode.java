package core.models.transport;

import core.models.Time;
import core.models.Location;
import lombok.Data;
import ui.map.geometry.interfaces.MapGraphics;

/**
 * Interface that gets implemented but all different means of transport.
 *
 * @author Kimon Navridis
 */
@Data
public abstract class TransportMode {
    private Location start;
    private Location destination;

    protected TransportMode() {}
    protected TransportMode(Location start, Location destination) {
        this.start = start;
        this.destination = destination;
    }

    public Time getTravelTime() {
        return Time.of((int) ((start.distanceTo(destination) / getAverageSpeed()) * 60.0));
    }

    public abstract String toString();
    public abstract double getAverageSpeed();
    public void dispose() {}
    public abstract MapGraphics[] getGraphics();
}
