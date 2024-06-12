package ui.map.geometry;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.function.UnaryOperator;

import core.algorithms.datastructures.Graph;
import ui.map.geometry.Line.Segment;
import ui.map.geometry.factories.LineFactory;

public class AbstractedBusNetwork extends BusNetwork {
    private static final AbstractorMap map = new AbstractorMap(
            LineFactory.createLine(Color.GREEN,
                    new Point(747 , 942 ), new ControlPoint(759 , 535 ), new Point(848 , 470 ), new Point(866 , 240 ),
                    new Point(1045, 232 ), new Point(1108, 140 ), new Point(1157, 138 ), new Point(1221, 39  ),
                    new Point(1301, 33  )),
            LineFactory.createLine(Color.BLUE,
                    new Point(745 , 942 ), new Point(952 , 942 ), new Point(952 , 622 ), new Point(1112, 622 ),
                    new Point(1112, 387 ), new Point(1205, 387 ), new Point(1205, 466 ), new Point(1112, 466 )),
            LineFactory.createLine(Color.MAGENTA,
                    new Point(745 , 942 ), new Point(773 , 947 ), new Point(773 , 1138),
                    new Point(846 , 1138), new Point(846 , 1201), new Point(988 , 1201), new Point(988 , 1309)),
            LineFactory.createLine(Color.RED,
                    new Point(745 , 942 ), new Point(752 , 532 ), new Point(844 , 469 ), new Point(843 , 150 ),
                    new Point(1000, 150 ), new Point(1046, 230 )),
            LineFactory.createLine(Color.YELLOW,
                    new Point(743 , 942 ), new Point(953 , 945 ), new Point(953 , 1149),
                    new Point(846 , 1149)),
            LineFactory.createLine(Color.MAGENTA,
                    new Point(745 , 929 ), new Point(573 , 929 ), new ControlPoint(521 , 929 ), new Point(521 , 971 ), new Point(521 , 1003),
                    new Point(451 , 1003), new Point(451 , 822 ), new Point(147 , 822 ), new Point(147 , 586 ),
                    new Point(63  , 586 )),
            LineFactory.createLine(Color.BLUE,
                    new Point(745 , 934 ), new Point(574 , 934 ), new Point(525 , 974 ), new Point(525 , 1008),
                    new Point(52  , 1008), new Point(52  , 1147), new Point(145 , 1147)),
            LineFactory.createLine(Color.YELLOW,
                    new Point(745 , 939 ), new Point(575 , 939 ), new Point(506 , 757 ), new Point(246 , 757 ),
                    new Point(246 , 827 ), new Point(92  , 827 ), new Point(92  , 950 ), new Point(215 , 950 ),
                    new Point(215 , 1020), new Point(391 , 1020), new Point(391 , 1078), new Point(434 , 1078),
                    new Point(558 , 1029), new Point(558 , 1006), new Point(575 , 997 ), new Point(575 , 940 )),
            LineFactory.createLine(Color.GREEN,
                    new Point(745 , 942 ), new Point(768 , 962 ), new Point(768 , 1088), new Point(601 , 1088),
                    new Point(536 , 1043), new Point(536 , 981 ), new Point(534 , 979 ), new Point(577 , 945 ),
                    new Point(745 , 942 )),
            LineFactory.createLine(Color.RED,
                    new Point(745 , 940 ), new Point(574 , 940 ), new Point(530 , 977 ), new Point(530 , 1043),
                    new Point(603 , 1093), new Point(767 , 1093), new Point(767 , 1144), new Point(947 , 1144),
                    new Point(947 , 1043), new Point(1135, 1043), new Point(1060, 1144), new Point(952 , 1144)));
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
                for (Segment subdivision : segment) {
                    Point2D clampedPoint = nearestPointOnLine(subdivision.getStart().getX(), subdivision.getStart().getY(),
                    subdivision.getEnd().getX(), subdivision.getEnd().getY(), point, true);
                    double distanceSq = clampedPoint.distanceSq(point);

                    if (closestPoint == null || distanceSq < dist) {
                        dist = distanceSq;
                        closestPoint = clampedPoint;
                    }
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
                }
                else if (t > 1) {
                    t = 1;
                }
            }

            return new Point.Double(ax + abx * t, ay + aby * t);
        }
    }
}
