package ui.map.translation;

import java.awt.event.*;

/**
 * Interface the component that gets translated implements
 * 
 * @author Sian Lodde
 */
public interface TranslateableComponent extends Translatable {
    public abstract void repaint();
    public abstract void addMouseMotionListener(MouseMotionListener listener);
    public abstract void addMouseListener(MouseListener listener);
    public abstract void addMouseWheelListener(MouseWheelListener listener);
}
