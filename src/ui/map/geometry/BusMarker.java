package ui.map.geometry;

import core.managers.FileManager;
import core.managers.MapManager;
import models.Location;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static constants.Constants.Paths.BUS_STOP_ICON;

public class BusMarker extends Marker {
    private static BufferedImage BusMarkerImage;
    private static int imageWidth, imageHeight;
    private static double scaler = 0.01;

    static {
        try {
            BusMarkerImage = FileManager.getImage(BUS_STOP_ICON);
            imageWidth = BusMarkerImage.getWidth();
            imageHeight = BusMarkerImage.getHeight();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public BusMarker(Location location) {
        super(location);
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        Point point = MapManager.locationToPoint(getMarkerLocation());

        double whiteCenterX = point.getX() - 2 + 2; // Center X coordinate of the white ellipse
        double whiteCenterY = point.getY() - 2 + 2; // Center Y coordinate of the white ellipse

        int imageX = (int) (whiteCenterX - (imageWidth * scaler) / 2);
        int imageY = (int) (whiteCenterY - (imageHeight * scaler) / 3);


        TexturePaint paint = new TexturePaint(BusMarkerImage, new Rectangle2D.Double(imageX, imageY, imageWidth * scaler, imageHeight * scaler));
        g2.setPaint(paint);
        g2.fill(new Rectangle2D.Double(imageX, imageY, imageWidth * scaler, imageHeight * scaler));
    }
    }

