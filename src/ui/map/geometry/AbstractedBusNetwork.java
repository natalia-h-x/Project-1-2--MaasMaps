package ui.map.geometry;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.function.UnaryOperator;

import core.algorithms.datastructures.Graph;
import ui.map.geometry.Line.Segment;

public class AbstractedBusNetwork extends BusNetwork {
    private static final AbstractorMap map = new AbstractorMap(
        new Line(Color.GREEN, new Point(745, 942), new Point(1300, 0)),
        new Line(Color.BLUE, new Point(745, 942), new Point (1300, 500)),
        new Line(Color.PINK, new Point(745, 942), new Point(1000, 1500)),
        new Line(Color.RED, new Point(745, 942), new Point(1000, 150)),
        new Line(Color.YELLOW, new Point(745, 942), new Point(100, 500)),
        new Line(Color.PINK, new Point(745, 942), new Point(100, 0)),
        new Line(Color.BLUE, new Point(745, 942), new Point(100,1000)),
        new Line(Color.YELLOW, new Point(745, 942), new Point(1192, 1035)),
        new Line(Color.GREEN, new Point(745, 942), new Point(487, 1300)),
        new Line(Color.RED, new Point(745, 945), new Point(1031, 1215))
    );
    private static final UnaryOperator<Point2D> abstractingFunction = map::map;

    public <P extends Point2D> AbstractedBusNetwork(Graph<P> graph) {
        super(graph);
    }

    @Override
    public <P extends Point2D> void createLines(Graph<P> graph) {
        for (Line line : map.getLines()) {
            addGraphic(line);
        }
    }

    @Override
    public Marker createMarker(Point2D point) {
        return super.createMarker(new Point2DImpostor(point, abstractingFunction));
    }

    private static final class AbstractorMap {
        private Line[] lines;

        private AbstractorMap(Line... lines) {
            this.lines = lines;
        }

        public Line[] getLines() {
            return lines;
        }

        public Point2D map(Point2D point) {
            if (lines.length <= 0)
                return point;

            Point2D closestPoint = null;
            double dist = 0;

            for (Line line : lines) {
                Point2D clampedPoint = nearestPointOnLine(line, point);

                if (clampedPoint == null)
                    continue;

                double distanceSq = clampedPoint.distanceSq(point);

                if (closestPoint == null || distanceSq < dist) {
                    dist = distanceSq;
                    closestPoint = clampedPoint;
                }
            }

            return closestPoint;
        }

        private static Point2D nearestPointOnLine(Line line, Point2D point) {
            Point2D closestPoint = null;
            double dist = 0;

            for (Segment segment : line) {
                Point2D clampedPoint = nearestPointOnLine(segment.getStart().getX(), segment.getStart().getY(), segment.getEnd().getX(), segment.getEnd().getY(), point, true);
                double distanceSq = clampedPoint.distanceSq(point);

                if (closestPoint == null || distanceSq < dist) {
                    dist = distanceSq;
                    closestPoint = clampedPoint;
                }
            }

            return closestPoint;
        }

        private static Point2D nearestPointOnLine(double ax, double ay, double bx, double by, Point2D point,
                boolean clampToSegment) {
            double apx = point.getX() - ax;
            double apy = point.getY() - ay;
            double abx = bx - ax;
            double aby = by - ay;

            double ab2 = abx * abx + aby * aby;
            double apAb = apx * abx + apy * aby;
            double t = apAb / ab2;

            if (clampToSegment) {
                if (t < 0) {
                    t = 0;
                } else if (t > 1) {
                    t = 1;
                }
            }

            return new Point.Double(ax + abx * t, ay + aby * t);
        }
    }
}
