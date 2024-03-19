package ui.map;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import ui.map.interfaces.Moveable;

public class TranslationListener {
    private Moveable moveable;
    private Point translation;
    private int zoomLevel;

    public TranslationListener(Moveable moveable) {
        this.moveable = moveable;
        zoomLevel = 0;

        initMouseListeners();
    }

    public void initMouseListeners() {
        MouseAdapter ma = new MouseAdapter() {
            private final Point pp = new Point();

            @Override
            public void mouseDragged(MouseEvent e) {
                if (SwingUtilities.isMiddleMouseButton(e)) {
                    Point cp = e.getPoint();
                    translate(pp.x - cp.x, pp.y - cp.y);
                    pp.setLocation(cp);

                    moveable.repaint();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isMiddleMouseButton(e)) {
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
    }

    public void translateTo(int x, int y) {
        translation.x = x;
        translation.y = y;
    }

    public void setScale(int level) {
        zoomLevel = Math.min(Math.max(zoomLevel + level, zoomLevelBoundMin), zoomLevelBoundMax);
    }

    public double getScale() {
        return Math.pow(2, zoomLevel);
    }
}
