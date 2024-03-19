package ui.map;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import algorithms.utils.DistanceCalculator;
import models.Location;
import fileaccess.Converter;

public class Line extends Component {
    private transient List<Location> locations = new ArrayList<>();

    // take the locations as parameter
    public Line(Location... locations) {
        this.locations.addAll(Arrays.asList(locations));
    }

    public void addLocation(Location loc) {
        locations.add(loc);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        int offset = 1;
        // Get the each location to draw the lines
        g2.setPaint(new Color(001, 010, 100));
        BasicStroke bs = new BasicStroke(5, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 10);
        g2.setStroke(bs);
        for (int i = 0; i < locations.size() - 1; i++) {
            Location loc1 = locations.get(i);
            Point p1 = Converter.convertedLocation(loc1);
            Location loc2 = locations.get(i + 1);
            Point p2 = Converter.convertedLocation(loc2);

            // Set paint color to blue for the line
            g2.setPaint(new Color(1, 10, 100));
            g2.drawLine(p1.x, p1.y, p2.x, p2.y);

            // Calculate distance and midpoint
            String distance = String.valueOf(DistanceCalculator.haversine(loc1, loc2));
            Point center = getCenter(p1, p2);

            // Create a 'border' effect for the text by drawing it in black first with
            // slight offsets
            g2.setPaint(Color.BLACK);
            g2.drawString(distance, center.x - offset, center.y - offset);
            g2.drawString(distance, center.x - offset, center.y + offset);
            g2.drawString(distance, center.x + offset, center.y - offset);
            g2.drawString(distance, center.x + offset, center.y + offset);

            // Then draw the text in white at the original position
            g2.setPaint(Color.WHITE);
            g2.drawString(distance, center.x, center.y);
            g2.setPaint(new Color(1, 10, 100));
        }
        

    }

    private Point getCenter(Point p1, Point p2) {
        int x = (p1.x + p2.x) / 2;
        int y = (p1.y + p2.y) / 2;

        return new Point(x, y);
    }
}
