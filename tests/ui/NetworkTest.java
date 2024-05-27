package ui;
import java.awt.Color;
import java.awt.geom.Point2D;

import org.junit.Test;

import algorithms.datastructures.AdjacencyListTest;
import core.Context;
import core.algorithms.datastructures.Graph;
import core.models.BusStop;
import ui.map.geometry.AbstractedBusNetwork;

public class NetworkTest {
    private Graph<BusStop> adjacencyListGraph;

    public NetworkTest() {
        adjacencyListGraph = AdjacencyListTest.createTestGraph();
    }

    @Test
    public void test1() {
        makeAbstractedBusNetwork(adjacencyListGraph);
    }

    public static <P extends Point2D> void makeAbstractedBusNetwork(Graph<P> adjacencyListGraph) {
        new MaasMapsUI();

        // BusNetwork busNetwork = new BusNetwork(adjacencyListGraph);
        // busNetwork.setPaint(Color.gray);
        // Context.getContext().getMap().addMapGraphics(busNetwork);

        AbstractedBusNetwork abstractedBusNetwork = new AbstractedBusNetwork(adjacencyListGraph);
        abstractedBusNetwork.setPaint(Color.green);

        Context.getContext().getMap().addMapGraphics(abstractedBusNetwork);
    }
}
