package database;

import static org.junit.jupiter.api.Assertions.fail;

import java.awt.geom.Point2D;

import org.junit.Test;

import core.algorithms.datastructures.Graph;
import core.managers.MapManager;
import ui.NetworkTest;

public class LoadGraphTest {
    public static void main(String[] args) {
        Graph<Point2D> graph = MapManager.getBusGraph();
        NetworkTest.makeAbstractedBusNetwork(graph);
    }

    @Test
    public void test1() {
        try {
            Graph<Point2D> graph = MapManager.getBusGraph();
            NetworkTest.makeAbstractedBusNetwork(graph);
        }
        catch (Exception e) {
            fail(e);
        }
    }
}
