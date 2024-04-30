package ui.map.geometry;

import java.awt.*;

import algorithms.util.DistanceCalculator;

public class CarthesianLine extends Line<Point> {
    @Override
    public void drawLineSegment(Graphics2D g2, Point p1, Point p2) {
        int offset = 1;

        g2.drawLine(p1.x, p1.y, p2.x, p2.y);
        // Calculate distance and midpoint
        String distance = String.valueOf((double) Math.round(DistanceCalculator.haversine(p1, p2) * 100.0) / 100.0) + " km";
        Point center = getCenter(p1, p2);

        // Create a 'border' effect for the text by drawing it in black first with
        // slight offsets
        g2.setPaint(Color.BLACK);
        g2.drawString(distance, center.x - offset, center.y - offset);
        g2.drawString(distance, center.x - offset, center.y         );
        g2.drawString(distance, center.x - offset, center.y + offset);
        g2.drawString(distance, center.x + offset, center.y         );
        g2.drawString(distance, center.x + offset, center.y - offset);
        g2.drawString(distance, center.x         , center.y - offset);
        g2.drawString(distance, center.x + offset, center.y + offset);
        g2.drawString(distance, center.x         , center.y + offset);

        // Then draw the text in white at the original position
        g2.setPaint(Color.WHITE);
        g2.drawString(distance, center.x, center.y);
    }

    private Point getCenter(Point p1, Point p2) {
        int x = (p1.x + p2.x) / 2;
        int y = (p1.y + p2.y) / 2;

        return new Point(x, y);
    }
}
