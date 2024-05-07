package tests;

import java.awt.geom.Point2D;

import org.junit.Test;

import core.Context;
import core.algorithms.datastructures.AdjacencyListGraph;
import core.algorithms.datastructures.Graph;
import core.models.Location;
import ui.MaasMapsUI;
import ui.map.geometry.Network;

public class NetworkTest {
    @Test
    public void test1() {
        new MaasMapsUI();
        Line line = new Line();

        Location loc1 = new Location(50.855233, 5.692237);
        Location loc2 = new Location(50.853608, 5.691958);
        Location loc3 = new Location(50.853617, 5.692009);
        Location loc4 = new Location(50.853037, 5.691825);
        Location loc5 = new Location(50.854993, 5.692294);
        Location loc6 = new Location(50.854581, 5.690199);

        line.addVertex(loc1);
        line.addVertex(loc2);
        line.addVertex(loc3);
        line.addVertex(loc4);
        line.addVertex(loc5);
        line.addVertex(loc6);

        Context.getContext().getResultsPanel().setLine(line);
    }
}
