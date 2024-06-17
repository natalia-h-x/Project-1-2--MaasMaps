package geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import org.junit.Test;

import core.Context;
import ui.MaasMapsUI;
import ui.map.geometry.AccessibilityMapBackground;
import ui.map.geometry.Marker;

public class AccessibilityMapBackgroundTest {
    public AccessibilityMapBackgroundTest() {
        new MaasMapsUI();
    }

    public static void main(String[] args) {
        new AccessibilityMapBackgroundTest().test();
    }

    @Test
    public void test() {
        for (double i = 0; i < 1; i += 0.01) {
            final double j = i;
            Context.getContext().getMap().addMapGraphics(new Marker(new Point2D.Double(i*100, -30.0)) {
                @Override
                public void paint(Graphics2D g) {
                    g.setPaint(AccessibilityMapBackground.notSiansLinearInterpolation(j, new Color[] {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN}));
                    g.fill(new Ellipse2D.Double(getLocation().getX(), getLocation().getY(), 10, 10));
                }
            });
        }
    }
}
