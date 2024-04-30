package ui.map;

import ui.map.geometry.MapGraphics;

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

    public void addMapIcon(MapGraphics... mapIcons) {
        for (MapGraphics mapIcon : mapIcons) {
            map.addMapIcon(mapIcon);
        }
    }

    public int getMapHeight(){
        return map.getMapHeight();
    }

    public int getMapWidth(){
        return map.getMapWidth();
    }
    public void clearIcons(){
        map.clearIcons();
    }
}
