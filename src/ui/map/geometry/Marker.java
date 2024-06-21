package ui.map.geometry;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import lombok.Data;
import ui.map.geometry.interfaces.MapGraphics;
import ui.map.translation.ProxyTranslatableGraphics2D;

/**
 * This class represents icons that are different types of markers in the map.
 * @author Alexandra Plishkin Islamgulova
 * @author Meriç Uruş
 */
@Data
public class Marker implements MapGraphics {
    private int markerOffsetY = 10;
    private int innerSize = 7;
    private Point2D location;
    private int size = 5;
    private Point offset = new Point(0, 0);

    public Marker(Point2D location) {
        this.location = location;
    }

    public void setText(String text) {
        // TODO Meric
    }

    // TODO Meric: Add getText() method

    public void setOffset(Point offset) {
        this.offset = offset;
    }

    public Point2D getMarkerLocation() {
        return location;
    }

    public Point getIconLocation() {
        double redCenterX = location.getX();
        double redCenterY = location.getY() - markerOffsetY;
        return new Point((int)redCenterX, (int)redCenterY);
    }

    @Override
    public void paint(Graphics2D g2) {
        g2 = new ProxyTranslatableGraphics2D(g2, 1, offset);

        Point iconPoint = getIconLocation();

        double topLeftX = iconPoint.getX() - size / 2.0;
        double topLeftY = iconPoint.getY() - size / 2.0;
        Ellipse2D redEllipse = new Ellipse2D.Double(topLeftX, topLeftY, size, size);

        g2.setColor(Color.RED);
        g2.setStroke(new BasicStroke(size));

        try {
            g2.draw(redEllipse);
        }
        catch (NullPointerException e) {}

        int[] xPoints = {(int) iconPoint.getX(), (int) (iconPoint.getX() - 4), (int) (iconPoint.getX() + 4)};
        int[] yPoints = {(int) location.getY(), (int) iconPoint.getY(), (int) iconPoint.getY()};

        Polygon triangle = new Polygon(xPoints, yPoints, 3);
        g2.setColor(Color.RED);
        g2.fillPolygon(triangle);

        double smallEllipseX = iconPoint.getX() - innerSize / 2.0;
        double smallEllipseY = iconPoint.getY() - innerSize / 2.0;
        Ellipse2D whiteEllipse = new Ellipse2D.Double(smallEllipseX, smallEllipseY, innerSize, innerSize);

        g2.setColor(Color.WHITE);
        g2.fill(whiteEllipse);

        // TODO Meric: Draw text here somewhere
    }
}
