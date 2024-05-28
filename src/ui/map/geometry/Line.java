package ui.map.geometry;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import javax.swing.Timer;

import core.Context;
import core.managers.MapManager;
import core.models.Time;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ui.map.geometry.interfaces.MapGraphics;

/**
 * This class represents a line connecting two points in the map.
 * @author Arda Ayyildizbayraktar
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Line implements MapGraphics, Iterable<ui.map.geometry.Line.Segment> {
    private static final int ANIMATION_SPEED = 100;
    private List<Point2D> locations = new ArrayList<>();
    private List<Time> times = new ArrayList<>();
    private Paint paint = new Color(001, 010, 100);
    private Stroke stroke = new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10);
    private Point offset = new Point();
    private Timer animatorTimer = new Timer(ANIMATION_SPEED, e -> advanceLineDrawIterator(e));
    private Segment[] lineIterator;
    private int animatedSegments = 0;
    private Time time;  

    // take the locations as parameter
    public Line(Point2D... points) {
        this.locations.addAll(Arrays.asList(points));
    }

    public Line(Paint paint, Point2D... points) {
        this(points);
        this.paint = paint;
    }

    public Line(Stroke stroke, Point2D... points) {
        this(points);
        this.stroke = stroke;
    }

    public Line(Paint paint, Stroke stroke, Point2D... points) {
        this(points);
        this.paint = paint;
        this.stroke = stroke;
    }
    public Line(Point2D[] points, Time[] times) {
        this(points);
        this.times.addAll(Arrays.asList(times));
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

    public Segment[] getLineIterator() {
        ArrayList<Segment> segments = new ArrayList<>();

        for (int i = 0; i < locations.size() - 1; i++) {
            Point2D p1 = locations.get(i);
            Point2D p2 = locations.get(i + 1);

            if (p1 == null || p2 == null)
                continue;

            segments.add(new Segment(p1, p2));
        }

        return segments.toArray(Segment[]::new);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setStroke(stroke);

        if (animatedSegments == 0)
            animatorTimer.start();

        lineIterator = getLineIterator();

        for (int i = 0; i < Math.min(animatedSegments, lineIterator.length); i++) {
            // Set paint color for the line
            g2.setPaint(paint);
            drawLineSegment(g2, lineIterator[i].getStart(), lineIterator[i].getEnd());
            
            Point2D centerPoint = new Point2D.Double(
                (lineIterator[i].getStart().getX() + lineIterator[i].getEnd().getX()) / 2,
                (lineIterator[i].getStart().getY() + lineIterator[i].getEnd().getY()) / 2
            );
            if (i < times.size() && times.get(i) != null) {
                MapManager.drawString(g2, times.get(i).toISOString(), centerPoint);
            }
        }
    }

    public void advanceLineDrawIterator(ActionEvent e) {
        if (animatedSegments >= lineIterator.length)
            animatorTimer.stop();
        
        animatedSegments++;
        
        Context.getContext().getMap().repaint();
    }

    @Data
    @AllArgsConstructor
    public static class Segment {
        private Point2D start;
        private Point2D end;
    }

    @Override
    public Iterator<Segment> iterator() {
        return new Iterator<Segment>() {
            private Segment[] segments = getLineIterator();
            private int position = -1;

            @Override
            public boolean hasNext() {
                return position < segments.length - 1;
            }

            @Override
            public Segment next() {
                if (!hasNext())
                    throw new NoSuchElementException("Iterator has no next elements.");

                return segments[++position];
            }
        };
    }
    public void setTime(Time time) {
        this.time = time;
    }
    
    public Time getTime() {
        return this.time;
    }

    public void addTime(Time time) {
        times.add(time);
    }
    public void setTimes(Time... times) {
        this.times.clear();
        this.times.addAll(Arrays.asList(times));
    }
}
