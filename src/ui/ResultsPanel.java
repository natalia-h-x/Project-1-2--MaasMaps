package ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import constants.Constants.UIConstants;
import models.Location;
import ui.map.Map;
import ui.map.geometry.ImageMarker;
import ui.map.geometry.Line;
import ui.map.geometry.Marker;
import ui.map.geometry.MarkerFactory;

public class ResultsPanel extends JPanel {
    private Line line;
    public ResultsPanel(){
        line = new Line();
        setLine(new Line(new Location(50.854581,5.690199), new Location(50.85291,5.692407), new Location(50.853228,5.690603)));
    }
    

    public void setLine(Line line){
        this.line = new Line();
        List<Location> locations = line.getLocations();
        Location previousLoc = null;
        this.line.addLocation(core.managers.MapManager.MAP_TOP_LEFT_LOCATION);
        for (int i = 0; i < locations.size(); i++){
            Location location = locations.get(i);
            if (previousLoc != null) {
                double distance = previousLoc.distanceTo(location);
                double scaledDistance = distance / line.getTotalDistance() / 20;
                Location linePart = new Location(0,scaledDistance);
                this.line.addRelativeLocation(linePart);
            }
            previousLoc = location;
        }
    }

    public ImageMarker resultMarker(Location location){
        return MarkerFactory.createBusImageMarker(location);
    }
    
    @Override
    public void paint(Graphics graphics){
        super.paint(graphics);

        if (line != null){
            Point offset = new Point(getWidth()/7, getHeight()/2);
            line.setOffset(offset);
            line.paint(graphics);
            
            for (Location location : line.getLocations()){
                Marker resultMarker = resultMarker(location);
                resultMarker.setOffset(offset);
                resultMarker.paint(graphics);
            }
        }
    }
}
