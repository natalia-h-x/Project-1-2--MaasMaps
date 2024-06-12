package ui.results;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.List;

import javax.swing.JPanel;

import core.models.Location;
import ui.map.geometry.GeographicLine;
import ui.map.geometry.ImageMarker;
import ui.map.geometry.ImageMarkerFactory;
import ui.map.geometry.Marker;

public class ResultsPanel extends JPanel implements ResultDisplay {
    private GeographicLine line;

    public ResultsPanel() {
        setLine(new GeographicLine(new Location(50.854581, 5.690199), new Location(50.85291, 5.692407), new Location(50.853228, 5.690603)));
    }

    public void setLine(GeographicLine line) {
        this.line = new GeographicLine();
        List<Point2D> locations = line.getLocations();
        Location previousLoc = null;
        this.line.addLocation(core.managers.MapManager.MAP_TOP_LEFT_LOCATION);
        for (int i = 0; i < locations.size(); i++) {
            Location location = (Location) locations.get(i);
            if (previousLoc != null) {
                double distance = previousLoc.distance(location);
                double scaledDistance = distance / line.getTotalDistance() / 20;
                Location linePart = new Location(0,scaledDistance);
                this.line.addRelativeLocation(linePart);
            }
            previousLoc = location;
        }
    }

    public ImageMarker resultMarker(Location location) {
        return ImageMarkerFactory.createBusImageMarker(location);
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);

        if (line != null) {
            Point offset = new Point(getWidth()/7, getHeight()/2);
            line.setOffset(offset);
            line.paint((Graphics2D) graphics);

            for (Point2D location : line.getLocations()) {
                Marker resultMarker = resultMarker((Location) location);
                resultMarker.setOffset(offset);
                resultMarker.paint((Graphics2D) graphics);
            }
        }
    }
}
