package ui.map.translation;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.SwingUtilities;

/**
 * This class translates a translatable component
 *
 * @author Sian Lodde
 */
public class TranslationListener {
    /** For zooming out */
    private static final int ZOOM_LEVEL_BOUND_MIN = -3;
    /** For zooming in */
    private static final int ZOOM_LEVEL_BOUND_MAX = 10;

    private TranslateableComponent translateableComponent;
    private Point translation;
    private int zoomLevel;

    private boolean useMiddle = false;

    public TranslationListener(TranslateableComponent translateableComponent) {
        this.translateableComponent = translateableComponent;
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

                    translateableComponent.repaint();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();

                if (SwingUtilities.isMiddleMouseButton(e) || !useMiddle) {
                    pp.setLocation(point);
                }

                point.translate((int) -translation.getX(), (int) -translation.getY());
                pushClipboardString("new Point(%d, %d)".formatted((int) (point.getX() / getScale()), (int) (point.getY() / getScale())));
            }

            private void pushClipboardString(String formatted) {
                StringSelection selection = new StringSelection(formatted);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(selection, selection);
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

                translateableComponent.repaint();
            }
        };

        translateableComponent.addMouseMotionListener(ma);
        translateableComponent.addMouseListener(ma);
        translateableComponent.addMouseWheelListener(ma);
    }

    public void translate(int x, int y) {
        translation.x -= x;
        translation.y -= y;

        translateableComponent.setTranslation(translation);
    }

    public void translateTo(int x, int y) {
        translation.x = x;
        translation.y = y;

        translateableComponent.setTranslation(translation);
    }

    public void setTranslation(Point point) {
        translation = point;
    }

    public void setScale(int level) {
        zoomLevel = Math.min(Math.max(zoomLevel + level, ZOOM_LEVEL_BOUND_MIN), ZOOM_LEVEL_BOUND_MAX);

        translateableComponent.setScale(getScale());
    }

    public double getScale() {
        return Math.pow(2, zoomLevel);
    }
}
