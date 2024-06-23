package core.models.transport;

import javax.swing.ImageIcon;

import core.models.Location;
import core.models.gtfs.Time;
import lombok.Data;
import ui.map.geometry.interfaces.MapGraphics;

/**
 * Interface that gets implemented but all different means of transport.
 *
 * @author Kimon Navridis
 */
@Data
public abstract class Transport {
    private Location start;
    private Location destination;
    private Time departingTime = Time.of(7, 0, 0);
    private Time time = Time.empty();
    private Time waitTime = Time.empty();

    protected Transport() {}
    protected Transport(Location start, Location destination) {
        this.start = start;
        this.destination = destination;
    }

    public Time totalTime() {
        return time.add(waitTime);
    }

    public Time getTravelTime() {
        return Time.of((int) ((start.distance(destination) / getAverageSpeed()) * 60.0));
    }

    public boolean canMerge(Transport before) {
        if (before == null)
            return false;

        if ((canEqual(before) && (this instanceof Bus bus && bus.getTrip().equals(((Bus) before).getTrip()))) || !(this instanceof Bus))
            return before.destination.equals(start);

        return false;
    }

    public Route getRoute() {
        return Route.of(departingTime, this);
    }

    public void dispose() {}

    public abstract ImageIcon getIcon();
    public abstract String toString();
    public abstract String takeTransport();
    public abstract double getAverageSpeed();
    public abstract MapGraphics[] getGraphics();
    public abstract Transport copy();
}
