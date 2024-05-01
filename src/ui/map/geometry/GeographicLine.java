package ui.map.geometry;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import algorithms.util.DistanceManager;
import core.managers.MapManager;
import models.Location;
import ui.map.geometry.interfaces.GeographicMapGraphics;

public class GeographicLine extends Line implements GeographicMapGraphics {
    public GeographicLine(Location... locations) {
        super(locations);
    }

    @Override
    public double getTotalDistance() {
        double totalDistance = 0;

        try {
            for (int i = 0; i < getLocations().size() - 1; i++) {
                Location loc1 = (Location) getLocations().get(i);
                Location loc2 = (Location) getLocations().get(i + 1);
                totalDistance += DistanceManager.haversine(loc1, loc2);
            }
        }
        catch (ClassCastException e) {
            throw new IllegalArgumentException("A Geographic Line only supports Locations. Please do not use other Point2D's!", e);
        }

        return totalDistance;
    }

    @Override
    public void drawLineSegment(Graphics2D g2, Point2D p1, Point2D p2) {
        super.drawLineSegment(g2, p1, p2);

        try {
            Location loc1 = (Location) p1;
            Location loc2 = (Location) p2;

            // Calculate distance and midpoint
            drawDistance(g2, loc1, loc2);
        }
        catch (ClassCastException e) {
            throw new IllegalArgumentException("A Geographic Line only supports Locations. Please do not use other Point2D's!", e);
        }
    }

    @Override
    public void drawDistance(Graphics2D g2, Location loc1, Location loc2) {
        String distance = String.valueOf(Math.round(DistanceManager.haversine(loc1, loc2) * 100.0) / 100.0) + " km";
        Point2D center = getCenter(loc1, loc2);

        // Create a 'border' effect for the text by drawing it in black first with
        // slight offsets
        MapManager.drawString(g2, distance, center);
    }

    private Point2D getCenter(Point2D p1, Point2D p2) {
        int x = ((int) p1.getX() + (int) p2.getX()) / 2;
        int y = ((int) p1.getY() + (int) p2.getY()) / 2;

        return new Point2D.Double(x, y);
    }
}
