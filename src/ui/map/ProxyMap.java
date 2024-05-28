package ui.map;

import java.awt.Point;

import ui.map.geometry.interfaces.MapGraphics;
import ui.map.translation.TranslationListener;

/**
 * This class represents a proxy to interact with the map
 *
 * @author Alexandra Plishkin Islamgulova
 * @author Sheena Gallagher
 */
public class ProxyMap {
    private Map map;

    public ProxyMap(Map map) {
        this.map = map;
    }

    public void addMapGraphics(MapGraphics... mapIcons) {
        for (MapGraphics mapIcon : mapIcons) {
            map.addMapIcon(mapIcon);
        }
    }

    public int getMapHeight() {
        return map.getMapHeight();
    }

    public int getMapWidth() {
        return map.getMapWidth();
    }

    public void clearIcons() {
        map.clearIcons();
    }

    public void repaint() {
        map.repaint();
    }

    public TranslationListener getTranslationListener() {
        return map.getTranslationListener();
    }

    public Point getLocation() {
        return map.getLocation();
    }
}
