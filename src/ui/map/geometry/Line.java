package ui.map.geometry;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import javax.swing.Timer;

import core.Context;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Exclude;
import ui.map.geometry.interfaces.MapGraphics;

/**
 * This class represents a line connecting two points in the map.
 * @author Arda Ayyildizbayraktar
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Line implements MapGraphics, Iterable<Line.Segment> {
    private static final int ANIMATION_SPEED = 100;
    private List<Point2D> locations = new ArrayList<>();
    private Paint paint = new Color(001, 010, 100);
    private Stroke stroke = new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10);
    private Point offset = new Point();
    @Exclude private Timer animatorTimer = new Timer(ANIMATION_SPEED, e -> advanceLineDrawIterator(e));
    @Exclude private Segment[] lineIterator;
    @Exclude private int animatedSegments = 0;

    // take the locations as parameter
    public Line(Point2D... points) {
        this.locations.addAll(Arrays.asList(points));
    }

    public void addLocation(Point2D point) {
        locations.add(point);
    }

    public void addRelativeLocation(Point2D loc) {
        Point2D p = locations.get(locations.size() - 1);
        loc.setLocation(p.getX() + loc.getX(), p.getY() + loc.getY());

        addLocation(loc);
    }

    public Segment[] getLineIterator() {
        ArrayList<Segment> segments = new ArrayList<>();

        try {
            for (int i = 0; i < locations.size() - 1; i++) {
                Point2D start = locations.get(i);
                Point2D end = locations.get(i + 1);

                if (start == null || end == null)
                    continue;

                // Filter out control points here into the segment, to make the creation of bezier or cubic curves a lot easier (nicely automatic)
                List<Point2D> controlPoints = new ArrayList<>();
                Point2D previousPoint = start;

                while (end instanceof ControlPoint controlPoint) {
                    controlPoints.add(controlPoint);
                    end = locations.get(++i + 1);
                    controlPoint.map(previousPoint, end);
                    previousPoint = controlPoint;
                }

                segments.add(createSegment(i, start, end, controlPoints));
            }
        }
        catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("A line cannot end with a control point.");
        }

        return segments.toArray(Segment[]::new);
    }

    public Segment createSegment(int i, Point2D start, Point2D end, List<Point2D> controlPoints) {
        return new Segment(start, end, controlPoints.toArray(Point2D[]::new));
    }

    @Override
    public void paint(Graphics2D g2) {
        g2.setStroke(stroke);
        g2.setPaint(paint);

        if (animatedSegments == 0)
            animatorTimer.start();

        lineIterator = getLineIterator();

        for (int i = 0; i < Math.min(animatedSegments, lineIterator.length); i++) {
            // Set paint color for the line
            g2.setPaint(paint);
            lineIterator[i].paint(g2);
        }

        g2.setPaint(new Color(0, 0, 0));
    }

    public void advanceLineDrawIterator(ActionEvent e) {
        if (animatedSegments >= lineIterator.length)
            animatorTimer.stop();

        animatedSegments++;

        Context.getContext().getMap().repaint();
    }

    @Data
    public static class Segment implements MapGraphics, Iterable<Segment> {
        private static final int QUAD_SUBDIVISIONS = 4;
        private static final int BEZIER_SUBDIVISIONS = 6;
        private Point2D start;
        private Point2D end;
        private List<Point2D> controlPoints = new ArrayList<>();
        private Interpolation interpolation = Interpolation.FLAT;

        public Segment(Point2D start, Point2D end, Interpolation interpolation, Point2D... controlPoints) {
            this.start = start;
            this.end = end;
            this.controlPoints = Arrays.asList(controlPoints);
            this.interpolation = interpolation;
        }

        public Segment(Point2D start, Point2D end, Point2D... controlPoints) {
            this(start, end, controlPoints.length <= 0? Interpolation.FLAT :
                             controlPoints.length <= 1? Interpolation.QUADRATIC : Interpolation.BEZIER, controlPoints);

            if (controlPoints.length > 2) {
                throw new IllegalArgumentException("More than 2 control points are not supported");
            }
        }

        enum Interpolation {
            FLAT {
                @Override
                public void paint(Graphics2D g2, Point2D start, Point2D end, Point2D... controlPoints) {
                    g2.drawLine((int) start.getX(), (int) start.getY(), (int) end.getX(), (int) end.getY());
                }

                @Override
                public Segment[] subdivide(Point2D start, Point2D end, Point2D... controlPoints) {
                    return new Segment[] {new Segment(start, end)};
                }
            },
            QUADRATIC {
                @Override
                public void paint(Graphics2D g2, Point2D start, Point2D end, Point2D... controlPoints) {
                    g2.draw(new QuadCurve2D.Double((int) start.getX(), (int) start.getY(),
                                                   (int) controlPoints[0].getX(), (int) controlPoints[0].getY(),
                                                   (int) end.getX(), (int) end.getY()));
                }

                @Override
                public Segment[] subdivide(Point2D start, Point2D end, Point2D... controlPoints) {
                    var curve = new QuadCurve2D.Double((int) start.getX(), (int) start.getY(),
                                            (int) controlPoints[0].getX(), (int) controlPoints[0].getY(),
                                            (int) end.getX(), (int) end.getY());

                    return subdivide(curve, new ArrayList<>(QUAD_SUBDIVISIONS), 0).toArray(Segment[]::new);
                }

                private List<Segment> subdivide(QuadCurve2D curve, List<Segment> segments, int level) {
                    if (level >= QUAD_SUBDIVISIONS) {
                        segments.add(new Segment(curve.getP1(), curve.getP2()));

                        return segments;
                    }

                    var curve1 = new QuadCurve2D.Double();
                    var curve2 = new QuadCurve2D.Double();
                    curve.subdivide(curve1, curve2);

                    subdivide(curve1, segments, level + 1);
                    subdivide(curve2, segments, level + 1);

                    return segments;
                }
            },
            BEZIER {
                @Override
                public void paint(Graphics2D g2, Point2D start, Point2D end, Point2D... controlPoints) {
                    g2.draw(new CubicCurve2D.Double((int) start.getX(), (int) start.getY(),
                                                   (int) controlPoints[0].getX(), (int) controlPoints[0].getY(),
                                                   (int) controlPoints[1].getX(), (int) controlPoints[1].getY(),
                                                   (int) end.getX(), (int) end.getY()));
                }

                @Override
                public Segment[] subdivide(Point2D start, Point2D end, Point2D... controlPoints) {
                    var curve = new CubicCurve2D.Double((int) start.getX(), (int) start.getY(),
                                            (int) controlPoints[0].getX(), (int) controlPoints[0].getY(),
                                            (int) controlPoints[1].getX(), (int) controlPoints[1].getY(),
                                            (int) end.getX(), (int) end.getY());

                    return subdivide(curve, new ArrayList<>(BEZIER_SUBDIVISIONS), 0).toArray(Segment[]::new);
                }

                private List<Segment> subdivide(CubicCurve2D curve, List<Segment> segments, int level) {
                    if (level >= BEZIER_SUBDIVISIONS) {
                        segments.add(new Segment(curve.getP1(), curve.getP2()));

                        return segments;
                    }

                    var curve1 = new CubicCurve2D.Double();
                    var curve2 = new CubicCurve2D.Double();
                    curve.subdivide(curve1, curve2);

                    subdivide(curve1, segments, level + 1);
                    subdivide(curve2, segments, level + 1);

                    return segments;
                }
            };

            public abstract void paint(Graphics2D g2, Point2D start, Point2D end, Point2D... controlPoints);
            public abstract Segment[] subdivide(Point2D start, Point2D end, Point2D... controlPoints);
        }

        @Override
        public void paint(Graphics2D g2) {
            interpolation.paint(g2, start, end, controlPoints.toArray(Point2D[]::new));
        }

        public Segment[] getSubdivisions() {
            return interpolation.subdivide(start, end, controlPoints.toArray(Point2D[]::new));
        }

        @Override
        public Iterator<Segment> iterator() {
            return Arrays.stream(getSubdivisions()).iterator();
        }
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
}
