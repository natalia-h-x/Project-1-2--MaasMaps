package ui.map.translation;

import java.awt.Point;

public interface Translatable {
    public abstract void setScale(double scale);
    public abstract void setTranslation(Point point);
}