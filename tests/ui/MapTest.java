package ui;

import java.awt.Point;
import java.awt.geom.Point2D;

import org.junit.jupiter.api.Test;

import core.Context;
import core.algorithms.datastructures.Graph;
import core.managers.MapManager;
import ui.map.Map;
import ui.map.geometry.AbstractedBusNetwork;
import ui.map.geometry.MapBackground;
import ui.map.geometry.Network;

public class MapTest {
    private Map map;

    // This main method is to test more in depth if the GUI actually looks good.
    public static void main(String[] args) {
        new MaasMapsUI();

        Graph<Point2D> graph = MapManager.getBusGraph();
        Network abstractedBusNetwork = new AbstractedBusNetwork(graph);

        Context.getContext().getMap().addMapGraphics(abstractedBusNetwork);
        Context.getContext().getMap().repaint();
    }

    public MapTest() {
        map = new Map(new MapBackground());
    }

    @Test
    public void test1() {
        Point point = map.getTranslation();
        Point newPoint = new Point();
        newPoint.setLocation(point.getX(), 2.2);
        map.setTranslation(newPoint);
        map.setScale(map.getScale() + 0.1);

        Point point1 = map.getTranslation();
        Point newPoint1 = new Point();
        newPoint1.setLocation(point1.getX(), 9);
        map.setTranslation(newPoint1);
        map.setScale(map.getScale() + 0.5);

        Point point2 = map.getTranslation();
        Point newPoint2 = new Point();
        newPoint2.setLocation(point2.getX(), 5.2);
        map.setTranslation(newPoint2);
        map.setScale(map.getScale() + 5);

        Point point3 = map.getTranslation();
        Point newPoint3 = new Point();
        newPoint3.setLocation(point3.getX(), 1);
        map.setTranslation(newPoint3);
        map.setScale(map.getScale() + 100);

        map.clear();
        Context.getContext().getMap().clearIcons();
    }
}
