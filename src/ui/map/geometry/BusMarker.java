package ui.map.geometry;


import core.managers.MapManager;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

@Getter
@Setter
// add or change the class when result section will be implemented
public class BusMarker extends ImageMarker {
    private int busId;
    private String locationName;
    public BusMarker(Point2D location, int bus_ID, String locationName, BufferedImage image){
        super(location, image);
        this.busId = bus_ID;
        this.locationName = locationName;

    }

    @Override
    public void paint(Graphics2D g2) {
        super.paint(g2);

        Point point = getIconLocation();
        MapManager.drawString(g2, locationName, point);




    }

}
