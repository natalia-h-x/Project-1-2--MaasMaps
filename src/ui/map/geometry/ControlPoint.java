package ui.map.geometry;

import java.awt.Point;
import java.awt.geom.Point2D;

public class ControlPoint extends Point {
    public ControlPoint() {}
    public ControlPoint(int x, int y) {
        super(x, y);
    }
    
    public void map(Point2D start, Point2D end) {
        // Override this method in latter classes which generate the control point based neighboring points.
    }

    public static class Arc extends ControlPoint {
        @Override
        public void map(Point2D start, Point2D end) {
            setLocation(Math.min(start.getX(), end.getX()), Math.min(start.getY(), end.getY()));
        }
    }

    public static class InvertedArc extends ControlPoint {
        @Override
        public void map(Point2D start, Point2D end) {
            setLocation(Math.max(start.getX(), end.getX()), Math.max(start.getY(), end.getY()));
        }
    }

    public static class Bloom extends ControlPoint {
        @Override
        public void map(Point2D start, Point2D end) {
            setLocation(1.0 / Math.exp(Math.max(start.getX(), end.getX())), Math.min(start.getY(), end.getY()));
        }
    }
    
    public static class InvertedBloom extends ControlPoint {
        @Override
        public void map(Point2D start, Point2D end) {
            setLocation(1.0 / Math.exp(Math.max(start.getX(), end.getX())), Math.min(start.getY(), end.getY()));
        }
    }

    public static class Withering extends ControlPoint {
        @Override
        public void map(Point2D start, Point2D end) {
            setLocation(1.0 / Math.exp(Math.max(start.getX(), end.getX())), Math.max(start.getY(), end.getY()));
        }
    }
    
    public static class InvertedWithering extends ControlPoint {
        @Override
        public void map(Point2D start, Point2D end) {
            setLocation(Math.exp(Math.max(start.getX(), end.getX())), Math.max(start.getY(), end.getY()));
        }
    }

    public static class Implode extends ControlPoint {
        @Override
        public void map(Point2D start, Point2D end) {
            setLocation(Math.min(start.getX(), end.getX()), 1.0 / Math.exp(Math.max(start.getY(), end.getY())));
        }
    }
    
    public static class InvertedImplode extends ControlPoint {
        @Override
        public void map(Point2D start, Point2D end) {
            setLocation(Math.min(start.getX(), end.getX()), Math.exp(Math.max(start.getY(), end.getY())));
        }
    }

    public static class Explode extends ControlPoint {
        @Override
        public void map(Point2D start, Point2D end) {
            setLocation(Math.max(start.getX(), end.getX()), 1.0 / Math.exp(Math.max(start.getY(), end.getY())));
        }
    }
    
    public static class InvertedExplode extends ControlPoint {
        @Override
        public void map(Point2D start, Point2D end) {
            setLocation(Math.max(start.getX(), end.getX()), Math.exp(Math.max(start.getY(), end.getY())));
        }
    }
}
