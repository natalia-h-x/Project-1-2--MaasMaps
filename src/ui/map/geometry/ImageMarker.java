package ui.map.geometry;

import core.managers.FileManager;
import models.Location;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.w3c.dom.DOMException;

import static constants.Constants.Paths.BUS_STOP_ICON;
import static constants.Constants.Paths.RANDOM_ICON;

public class ImageMarker extends Marker {
    private static BufferedImage busStopImage;
    private static BufferedImage randomImage;

    static {
        try {
            busStopImage = FileManager.getImage(BUS_STOP_ICON);
            randomImage = FileManager.getImage(RANDOM_ICON);
        }
        catch (IOException e) {
            throw new DOMException((short) 0, "Could not find icon.");
        }
    }

    private transient BufferedImage image;
    private int imageWidth;
    private int imageHeight;
    private double scaler = 0.01;

    public ImageMarker(Location location, BufferedImage image) {
        super(location);
        setImage(image);
    }

    public static ImageMarker createBusImageMarker(Location location){
        return new ImageMarker(location, busStopImage);
    }

    public static ImageMarker createRandomImageMarker(Location location){
        return new ImageMarker(location, randomImage);
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
