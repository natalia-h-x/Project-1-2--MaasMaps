package ui.map.geometry;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import core.managers.map.DrawManager;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
// add or change the class when result section will be implemented
public class BusMarker extends ImageMarker {
    private int busId;
    private String locationName;

    public BusMarker(Point2D location, int busId, String locationName, BufferedImage image){
        super(location, image);
        this.busId = busId;
        this.locationName = locationName;
    }

    @Override
    public void paint(Graphics2D g2) {
        super.paint(g2);

        Point point = getIconLocation();
        DrawManager.drawString(g2, "Bus ID " + busId + ": " + locationName, point);
    }
}
