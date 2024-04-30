package ui.map.geometry;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import algorithms.util.DistanceCalculator;
import core.managers.MapManager;
import models.Location;
import ui.map.geometry.interfaces.Locateable;
import ui.map.geometry.interfaces.MapGraphics;

/**
 * This class represents a line connecting two points in the map.
 * @author Arda Ayyildizbayraktar
 */
public class Line extends Component implements MapGraphics {
    private transient List<Locateable> points = new ArrayList<>();

    // take the locations as parameter
    public Line(Locateable... points) {
        this.points.addAll(Arrays.asList(points));
    }

    public void addLocation(Locateable point) {
        points.add(point);
    }

    public void drawLineSegment(Graphics2D g2, Locateable p1, Locateable p2) {
        Point location1 = p1.getLocation();
        Point location2 = p2.getLocation();
        g2.drawLine(location1.x, location1.y, location2.x, location2.y);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        // Get the each location to draw the lines
        g2.setPaint(new Color(001, 010, 100));
        BasicStroke bs = new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10);
        g2.setStroke(bs);

        for (int i = 0; i < points.size() - 1; i++) {
            T p1 = points.get(i);
            T p2 = points.get(i + 1);

            if (p1 == null || p2 == null)
                continue;

            // Set paint color to blue for the line
            g2.setPaint(new Color(1, 10, 100));
            drawLineSegment(g2, p1, p2);
        }
    }
}
