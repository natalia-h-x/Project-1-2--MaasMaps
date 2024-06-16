package ui.map.geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.HashMap;
import java.util.List;

import core.Context;
import core.managers.GeoJSONManager;
import core.models.Location;

public class AccessibilityMapBackground extends MapBackground {
    private List<String> postalCodes;
    private HashMap<String, Double> accessibilityMap = new HashMap<>();

    public AccessibilityMapBackground(List<String> postalCodes) {
        this.postalCodes = postalCodes;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        for (String postalCode : postalCodes) {
            accessibilityMap.computeIfAbsent(postalCode, s -> GeoJSONManager.getAccessibilityMetric(postalCode));

            Location location = Context.getContext().getZipCodeDatabase().getLocation(postalCode);
            g2.setPaint(notSiansLinearInterpolation(accessibilityMap.get(postalCode), new Color[] {Color.YELLOW, Color.GREEN}));
            
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
}
