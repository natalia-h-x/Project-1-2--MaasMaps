package ui.map.geometry;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.TexturePaint;
import java.awt.geom.Point2D;
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

    public ImageMarker(Point2D location, BufferedImage image) {
        super(location);
        setImage(image);
    }

    public void setImage(BufferedImage image) {
        this.image = image;
        imageWidth = image.getWidth();
        imageHeight = image.getHeight();
    }

    @Override
    public void paint(Graphics2D g2) {
        super.paint(g2);
        
        Point point = getIconLocation();

        double imageX = (point.x - (imageWidth * scaler) / 2.0);
        double imageY = (point.y - (imageHeight * scaler) / 2.0);

        TexturePaint paint = new TexturePaint(image, new Rectangle2D.Double(imageX, imageY, imageWidth * scaler, imageHeight * scaler));
        g2.setPaint(paint);
        g2.fill(new Rectangle2D.Double(imageX, imageY, imageWidth * scaler, imageHeight * scaler));
    }
}
