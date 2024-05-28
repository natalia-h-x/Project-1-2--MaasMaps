package core.models.transport;

import core.models.Location;
import ui.map.geometry.ImageMarkerFactory;
import ui.map.geometry.factories.LineFactory;
import ui.map.geometry.interfaces.MapGraphics;

/**
 * This class represents the transport mode biking.
 *
 * @author Kimon Navridis
 */
public class Biking extends TransportMode {
    private static final double AVERAGE_SPEED = 291.66666;

    public Biking() {}
    public Biking(Location start, Location destination) {
        super(start, destination);
    }


    public double getAverageSpeed() {
        return AVERAGE_SPEED;
    }

    public String toString() {
        return "Biking";
    }

    @Override
    public MapGraphics[] getGraphics() {
        return new MapGraphics[] {
            LineFactory.createResultsLine(getStart(), getDestination()),
            ImageMarkerFactory.createAImageMarker(getStart()),
            ImageMarkerFactory.createBImageMarker(getDestination())
        };
    }
}   