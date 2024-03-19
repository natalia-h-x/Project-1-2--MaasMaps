package ui.map.interfaces;

import java.awt.event.*;

public interface TranslateableComponent extends Translateable {
    public abstract void repaint();
    public abstract void addMouseMotionListener(MouseMotionListener listener);
    public abstract void addMouseListener(MouseListener listener);
    public abstract void addMouseWheelListener(MouseWheelListener listener);
}
