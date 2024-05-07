package ui.map.geometry;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ui.map.geometry.interfaces.MapGraphics;

/**
 * This class represents a line connecting two points in the map.
 * @author Arda Ayyildizbayraktar
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Line extends Component implements MapGraphics {
    private transient List<Point2D> locations = new ArrayList<>();
    private transient Paint paint = new Color(001, 010, 100);
    private transient Stroke stroke = new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10);
    private Point offset = new Point();

    // take the locations as parameter
    public Line(Point2D... points) {
        this.locations.addAll(Arrays.asList(points));
    }

    public Line(Paint paint, Stroke stroke, Point2D... locations) {
        this.locations.addAll(Arrays.asList(locations));
        this.paint = paint;
        this.stroke = stroke;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public Stroke getStroke() {
        return stroke;
    }

    public void setStroke(Stroke stroke) {
        this.stroke = stroke;
    }

    public void addLocation(Point2D point) {
        locations.add(point);
    }

    public void addRelativeLocation(Point2D loc) {
        Point2D p = locations.get(locations.size() - 1);
        loc.setLocation(p.getX() + loc.getX(), p.getY() + loc.getY());
        addLocation(loc);
    }

    public void drawLineSegment(Graphics2D g2, Point2D p1, Point2D p2) {
        g2.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        // Get the each location to draw the lines
        g2.setPaint(paint);
        g2.setStroke(stroke);

        for (int i = 0; i < locations.size() - 1; i++) {
            Point2D p1 = locations.get(i);
            Point2D p2 = locations.get(i + 1);

            if (p1 == null || p2 == null)
                continue;

            // Set paint color to blue for the line
            g2.setPaint(paint);
            drawLineSegment(g2, p1, p2);
        }
    }
}
