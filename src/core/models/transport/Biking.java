package core.models.transport;

import javax.swing.ImageIcon;

import core.models.Location;
import core.models.gtfs.Time;
import ui.map.geometry.ImageMarkerFactory;
import ui.map.geometry.factories.LineFactory;
import ui.map.geometry.interfaces.MapGraphics;

/**
 * This class represents the transport mode biking.
 *
 * @author Kimon Navridis
 */
public class Biking extends Transport {
    private static final double AVERAGE_SPEED = 291.66666;

    public Biking() {}
    public Biking(Location start, Location destination) {
        super(start, destination);
    }

    @Override
    public Time getTime() {
        return getTravelTime();
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
    @Override
    public ImageIcon getIcon() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getIcon'");
    }
    @Override
    public String takeTransport() {
        String location = String.format("Start: %s Destination: %s", getStart(), getDestination());
        
        return location;
    }
}