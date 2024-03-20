package ui.map;

import ui.map.geometry.MapIcon;

public class ProxyMap {
    private Map map;

    public ProxyMap(Map map) {
        this.map = map;
    }

    public void addMapIcon(MapIcon... mapIcons) {
        for (MapIcon mapIcon : mapIcons) {
            map.addMapIcon(mapIcon);
        }
    }

    public int getMapHeight(){
        return map.getMapHeight();
    }

    public int getMapWidth(){
        return map.getMapWidth();
    }
}
