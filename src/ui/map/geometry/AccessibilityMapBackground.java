package ui.map.geometry;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import core.Context;
import core.managers.MapManager;
import core.managers.amenity.AmenityAccessibilityManager;
import core.models.Location;

public class AccessibilityMapBackground extends MapBackground {
    private List<String> postalCodes;
    private Map<String, Double> accessibilityMap = new HashMap<>();
    private Map<Point2D, Double> accesibilityPoints = new HashMap<>();

    public AccessibilityMapBackground(List<String> postalCodes) {
        this.postalCodes = postalCodes;
    }

    @Override
    public void paint(Graphics2D g) {
        for (String postalCode : postalCodes) {
            accessibilityMap.computeIfAbsent(postalCode, s -> AmenityAccessibilityManager.getAccessibilityMetric(postalCode));

            Location location = Context.getContext().getZipCodeDatabase().getLocation(postalCode);
            accesibilityPoints.computeIfAbsent(new Point2D.Double(location.getX(), location.getY()), s -> accessibilityMap.get(postalCode));
        }

        fill(g);
    }

    public static Color notSiansLinearInterpolation(double accessibility, Color[] colors) {
        double number = accessibility * (colors.length - 1);
        
        double newNumber = number - Math.floor(number);
        double inv = 1 - newNumber;

        double r = colors[(int) Math.max(0, Math.ceil(number - 1))].getRed()*inv + colors[(int) Math.max(0, Math.ceil(number - 1)) + 1].getRed()*newNumber;
        double g = colors[(int) Math.max(0, Math.ceil(number - 1))].getGreen()*inv + colors[(int) Math.max(0, Math.ceil(number - 1)) + 1].getGreen()*newNumber;
        double b = colors[(int) Math.max(0, Math.ceil(number - 1))].getBlue()*inv + colors[(int) Math.max(0, Math.ceil(number - 1)) + 1].getBlue()*newNumber;

        return new Color((float) r / 255, (float) g / 255, (float) b / 255, 0.4f);
    }

    public void fill(Graphics2D g2) {
        Point2D start = MapManager.MAP_TOP_LEFT_XY;
        Point2D end = MapManager.MAP_BOTTOM_RIGHT_XY;
        int scale = 10;

        for (double y = start.getY(); y <= end.getY(); y+=1) {
            for (double x = start.getX(); x <= end.getX(); x+=1) {
                Point2D point = new Point2D.Double(x, y);

                if (!accesibilityPoints.containsKey(point)) {
                    Point2D[] closestPoints = getClosestPoints(point);
                    double accessibility = 0;

                    for (Point2D p : closestPoints) {
                        double distance = distanceBetweenTwoPoints(point, p);
                        accessibility += accesibilityPoints.get(p);
                        for (double i = distance; i >= 0; i -= 5) {
                            if (accessibility >= 0.01)
                                accessibility -= 0.01;
                        }
                    }

                    accessibility = accessibility / 4;
                    g2.setPaint(notSiansLinearInterpolation(accessibility, new Color[] {Color.GREEN, Color.YELLOW, Color.RED}));

                    g2.fill(new Ellipse2D.Double(x, y, scale, scale));
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
