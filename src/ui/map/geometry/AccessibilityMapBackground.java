package ui.map.geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import core.Context;
import core.managers.GeoJSONManager;
import core.managers.MapManager;
import core.models.Location;

public class AccessibilityMapBackground extends MapBackground {
    private List<String> postalCodes;
    private Map<String, Double> accessibilityMap = new HashMap<>();
    private Map<Point2D, Double> accesibilityPoints = new HashMap<>(); 

    public AccessibilityMapBackground(List<String> postalCodes) {
        this.postalCodes = postalCodes;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        for (String postalCode : postalCodes) {
            accessibilityMap.computeIfAbsent(postalCode, s -> GeoJSONManager.getAccessibilityMetric(postalCode));

            Location location = Context.getContext().getZipCodeDatabase().getLocation(postalCode);
            accesibilityPoints.computeIfAbsent(new Point2D.Double(location.getX(), location.getY()), s -> accessibilityMap.get(postalCode));
            g2.setPaint(notSiansLinearInterpolation(accessibilityMap.get(postalCode), new Color[] {Color.RED, Color.YELLOW, Color.GREEN}));
            
            g2.fill(new Ellipse2D.Double(location.getX(), location.getY(), 10, 10));
        }
    }

    public static Color notSiansLinearInterpolation(double accessibility, Color[] colors) {
        double number = accessibility * (colors.length - 1);
        double newNumber = number - Math.floor(number);
        double inv = 1 - newNumber;

        double r = colors[(int) Math.floor(number)].getRed()*inv + colors[(int) Math.floor(number) + 1].getRed()*newNumber;
        double g = colors[(int) Math.floor(number)].getGreen()*inv + colors[(int) Math.floor(number) + 1].getGreen()*newNumber;
        double b = colors[(int) Math.floor(number)].getBlue()*inv + colors[(int) Math.floor(number) + 1].getBlue()*newNumber;
            
        return new Color((float) r / 255, (float) g / 255, (float) b / 255);
    }

    public void fill() {
        Point2D start = MapManager.MAP_TOP_LEFT_GLOBAL_XY;
        Point2D end = MapManager.MAP_BOTTOM_RIGHT_GLOBAL_XY;
        
        for (double y = start.getY(); y <= end.getY(); y++) {
            for (double x = start.getX(); x <= end.getX(); x++) {
                Point2D point = new Point2D.Double(x, y);

                if (!accesibilityPoints.containsKey(point)) {
                    Point2D[] closestPoints = getClosestPoints(point);
                    double accessibility = 0;

                    for (Point2D p : closestPoints) {
                        accessibility += accesibilityPoints.get(p);
                    }
                    accessibility = accessibility/4;
                }
            }
        }
    }

    public Point2D[] getClosestPoints(Point2D point) {
        Map<Point2D, Double> distances = new HashMap<>();
        PriorityQueue<Point2D> closestSet = new PriorityQueue<>((a, b) -> distances.get(b).compareTo(distances.get(a)));
        double worstDist = Double.NEGATIVE_INFINITY;

        for (Point2D loc : accesibilityPoints.keySet()) {
            double dist = distanceBetweenTwoPoints(loc, point);

            distances.put(loc, dist);

            if (closestSet.size() < 4) {
                closestSet.add(loc);
                worstDist = Math.max(dist, worstDist);

                continue;
            }

            if (dist < worstDist) {
                closestSet.poll();
                closestSet.add(loc);

                worstDist = dist;

                for (Point2D l : closestSet) {
                    worstDist = Math.max(distances.get(l), worstDist);
                }
            }
        }

        return closestSet.toArray(Point2D[]::new);
    }

    public double distanceBetweenTwoPoints(Point2D a, Point2D b) {
        return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
    }
}
