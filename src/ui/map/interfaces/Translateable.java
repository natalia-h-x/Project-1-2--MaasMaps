package ui.map.interfaces;

import java.awt.Point;
import java.awt.event.*;

public interface Translateable {
    public abstract void setScale(double scale);
    public abstract void setTranslation(Point point);
    public abstract void repaint();
    public abstract void addMouseMotionListener(MouseMotionListener listener);
    public abstract void addMouseListener(MouseListener listener);
    public abstract void addMouseWheelListener(MouseWheelListener listener);
}
