package ui.map;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import ui.map.interfaces.Translateable;

public class TranslationListener {
    private static final int ZOOM_LEVEL_BOUND_MIN = -3;
    private static final int ZOOM_LEVEL_BOUND_MAX = 10;

    private Translateable moveable;
    private Point translation;
    private int zoomLevel;

    private boolean useMiddle = false;

    public TranslationListener(Translateable moveable) {
        this.moveable = moveable;
        zoomLevel = 0;
        translation = new Point(0, 0);

        initMouseListeners();
    }

    public void initMouseListeners() {
        MouseAdapter ma = new MouseAdapter() {
            private final Point pp = new Point();

            @Override
            public void mouseDragged(MouseEvent e) {
                if (SwingUtilities.isMiddleMouseButton(e) || !useMiddle) {
                    Point cp = e.getPoint();
                    translate(pp.x - cp.x, pp.y - cp.y);
                    pp.setLocation(cp);

                    moveable.repaint();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isMiddleMouseButton(e) || !useMiddle) {
                    pp.setLocation(e.getPoint());
                }
            }

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                Point cp = e.getPoint();

                double prevScale = getScale();

                // Scale the image
                setScale(-e.getWheelRotation());

                // Delta of scale
                double d = getScale() / prevScale;

                // Compensate for the scaling offsetting pixels
                double dx = (cp.x - translation.x) * (d - 1.0);
                double dy = (cp.y - translation.y) * (d - 1.0);

                // Translate the image along the scaling offset
                translate((int) dx, (int) dy);

                moveable.repaint();
            }
        };

        moveable.addMouseMotionListener(ma);
        moveable.addMouseListener(ma);
        moveable.addMouseWheelListener(ma);
    }

    public void translate(int x, int y) {
        translation.x -= x;
        translation.y -= y;

        moveable.setTranslation(translation);
    }

    public void translateTo(int x, int y) {
        translation.x = x;
        translation.y = y;

        moveable.setTranslation(translation);
    }

    public void setTranslation(Point point) {
        translation = point;
    }

    public void setScale(int level) {
        zoomLevel = Math.min(Math.max(zoomLevel + level, ZOOM_LEVEL_BOUND_MIN), ZOOM_LEVEL_BOUND_MAX);

        moveable.setScale(getScale());
    }

    public double getScale() {
        return Math.pow(2, zoomLevel);
    }
}
