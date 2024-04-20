package ui.map.geometry;

import models.Location;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * This class represents an icon in the map.
 * @author Meriç Uruş
 */
public class ImageMarker extends Marker {
    private transient BufferedImage image;
    private int imageWidth;
    private int imageHeight;
    private double scaler = 0.01;

    public ImageMarker(Location location, BufferedImage image) {
        super(location);
        setImage(image);
    }

    public void setImage(BufferedImage image){
        this.image = image;
        imageWidth = image.getWidth();
        imageHeight = image.getHeight();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        Point point = getIconLocation();

        double imageX = (point.x - (imageWidth * scaler) / 2.0);
        double imageY = (point.y - (imageHeight * scaler) / 2.0);

        TexturePaint paint = new TexturePaint(image, new Rectangle2D.Double(imageX, imageY, imageWidth * scaler, imageHeight * scaler));
        g2.setPaint(paint);
        g2.fill(new Rectangle2D.Double(imageX, imageY, imageWidth * scaler, imageHeight * scaler));
    }
}
