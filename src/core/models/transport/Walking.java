package core.models.transport;

import javax.swing.ImageIcon;

import core.models.Location;
import core.models.gtfs.Time;
import ui.map.geometry.ImageMarkerFactory;
import ui.map.geometry.factories.LineFactory;
import ui.map.geometry.interfaces.MapGraphics;

/**
 * This class represents the transport mode walking.
 *
 * @author Kimon Navridis
 */
public class Walking extends Transport {
    private static final double AVERAGE_SPEED = 83.33333; // meters per minute

    public Walking() {}
    public Walking(Location start, Location destination) {
        super(start, destination);
    }

    @Override
    public Time getTime() {
        return getTravelTime();
    }

    @Override
    public double getAverageSpeed() {
        return AVERAGE_SPEED;
    }

    public String toString() {
        return "Walking";
    }

    @Override
    public MapGraphics[] getGraphics() {
        return new MapGraphics[] {
            LineFactory.createResultsLine(getStart(), getDestination()),
            ImageMarkerFactory.createAImageMarker(getStart()),
            ImageMarkerFactory.createBImageMarker(getDestination())
        };
    }
    @Override
    public ImageIcon getIcon() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getIcon'");
    }
    @Override
    public String takeTransport() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'takeTransport'");
    }
}
