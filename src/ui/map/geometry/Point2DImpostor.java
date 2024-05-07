package ui.map.geometry;

import java.awt.geom.Point2D;
import java.util.function.DoubleFunction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class Point2DImpostor extends Point2D {
    @NonNull
    private Point2D imposedPoint;
    private DoubleFunction<java.lang.Double> impostorFunction = i -> i;

    @Override
    public double getX() {
        return impostorFunction.apply(imposedPoint.getX());
    }

    @Override
    public double getY() {
        return impostorFunction.apply(imposedPoint.getY());
    }

    @Override
    public void setLocation(double x, double y) {
        imposedPoint.setLocation(x, y);
    }

    @Override
    public void setLocation(Point2D p) {
        imposedPoint.setLocation(p);
    }

    @Override
    public double distanceSq(double px, double py) {
        return imposedPoint.distanceSq(px, py);
    }

    @Override
    public double distanceSq(Point2D pt) {
        return imposedPoint.distanceSq(pt);
    }

    @Override
    public double distance(double px, double py) {
        return imposedPoint.distance(px, py);
    }

    @Override
    public String toString() {
        return imposedPoint.toString();
    }

    @Override
    public double distance(Point2D pt) {
        return imposedPoint.distance(pt);
    }

    @Override
    public int hashCode() {
        return imposedPoint.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return imposedPoint.equals(obj);
    }
}
