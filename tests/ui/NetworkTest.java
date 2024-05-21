package ui;
import java.awt.Color;

import org.junit.Test;

import core.Context;
import core.algorithms.datastructures.AdjacencyListGraph;
import core.algorithms.datastructures.Graph;
import core.models.BusStop;
import ui.map.geometry.AbstractedBusNetwork;

public class NetworkTest {
    private Graph<BusStop> adjacencyListGraph;

    public NetworkTest() {
        adjacencyListGraph = new AdjacencyListGraph<>();

        BusStop loc1 = new BusStop(50.855233, 5.692237, "Maastricht Bus Stop nr. 1");
        BusStop loc2 = new BusStop(50.853608, 5.691958, "Maastricht Bus Stop nr. 2");
        BusStop loc3 = new BusStop(50.853617, 5.692009, "Maastricht Bus Stop nr. 3");
        BusStop loc4 = new BusStop(50.853037, 5.691825, "Maastricht Bus Stop nr. 4");
        BusStop loc5 = new BusStop(50.854993, 5.692294, "Maastricht Bus Stop nr. 5");
        BusStop loc6 = new BusStop(50.854581, 5.690199, "Maastricht Bus Stop nr. 6");

        adjacencyListGraph.addVertex(loc1);
        adjacencyListGraph.addVertex(loc2);
        adjacencyListGraph.addVertex(loc3);
        adjacencyListGraph.addVertex(loc4);
        adjacencyListGraph.addVertex(loc5);
        adjacencyListGraph.addVertex(loc6);

        adjacencyListGraph.addEdge(loc1, loc2, 0);
        adjacencyListGraph.addEdge(loc2, loc3, 0);
        adjacencyListGraph.addEdge(loc3, loc4, 0);
        adjacencyListGraph.addEdge(loc4, loc5, 0);
        adjacencyListGraph.addEdge(loc5, loc6, 0);
        adjacencyListGraph.addEdge(loc6, loc1, 0);
    }

    @Test
    public void test1() {
        makeAbstractedBusNetwork(adjacencyListGraph);
    }

    public static void makeAbstractedBusNetwork(Graph<BusStop> adjacencyListGraph) {
        new MaasMapsUI();

        // BusNetwork busNetwork = new BusNetwork(adjacencyListGraph);
        // busNetwork.setPaint(Color.gray);
        // Context.getContext().getMap().addMapGraphics(busNetwork);

        AbstractedBusNetwork abstractedBusNetwork = new AbstractedBusNetwork(adjacencyListGraph);
        abstractedBusNetwork.setPaint(Color.green);

        Context.getContext().getMap().addMapGraphics(abstractedBusNetwork);
    }
}
