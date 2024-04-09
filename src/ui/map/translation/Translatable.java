package ui.map.translation;

import java.awt.Point;

/**
 * Interface that components can implement to be able to be translated by the translation listener.
 * 
 * @author Sian Lodde
 */
public interface Translatable {
    public abstract void setScale(double scale);
    public abstract void setTranslation(Point point);
}