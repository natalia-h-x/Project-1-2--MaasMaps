package ui.map;

import java.awt.Point;
import java.util.HashMap;

import ui.map.geometry.interfaces.MapGraphics;
import ui.map.translation.TranslationListener;

/**
 * This class represents a proxy to interact with the map
 *
 * @author Alexandra Plishkin Islamgulova
 * @author Sheena Gallagher
 */
public class ProxyMap {
    private java.util.Map<String, MapGraphics> hiddenTopGraphics = new HashMap<>();
    private Map map;

    public ProxyMap(Map map) {
        this.map = map;
    }

    public void addMapGraphics(MapGraphics... mapIcons) {
        for (MapGraphics mapIcon : mapIcons) {
            map.addMapGraphics(mapIcon);
        }
    }

    public int getMapHeight() {
        return map.getMapHeight();
    }

    public int getMapWidth() {
        return map.getMapWidth();
    }

    public void clearIcons() {
        map.clear();
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

    public void linkMapGraphics(String option, MapGraphics top) {
        map.getTopGraphics().put(option, top);
        map.repaint();
    }

    public void toggleMapGraphics(String option) {
        if (hiddenTopGraphics.containsKey(option))
            showMapGraphics(option);
        else
            hideMapGraphics(option);
    }

    public void hideMapGraphics(String option) {
        if (!map.getTopGraphics().containsKey(option))
            throw new IllegalArgumentException("this option %s has not been linked.".formatted(option));

        if (hiddenTopGraphics.containsKey(option))
            return;

        hiddenTopGraphics.put(option, map.getTopGraphics().get(option));
        unlinkMapGraphics(option);
    }

    public void showMapGraphics(String option) {
        if (!hiddenTopGraphics.containsKey(option))
            return;

        linkMapGraphics(option, hiddenTopGraphics.get(option));
        hiddenTopGraphics.remove(option);
    }

    public void unlinkMapGraphics(String option) {
        if (map.getTopGraphics().containsKey(option))
            map.getTopGraphics().remove(option);

        map.repaint();
    }
}
