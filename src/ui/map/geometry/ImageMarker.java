package ui.map.geometry;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * This class represents an icon in the map.
 * @author Meriç Uruş
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ImageMarker extends Marker {
    private ImageGraphics image;
    private double scaler = 0.01;

    public ImageMarker(Point2D location, BufferedImage image) {
        super(location);

        this.image = new ImageGraphics(location, image);
        this.image.setScale(scaler);
    }

    @Override
    public void paint(Graphics2D g2) {
        super.paint(g2);

        image.setTopLeftPoint(getLocation());
        image.setOffset(new Point(0, -getMarkerOffsetY()));
        image.paint(g2);
    }
}
