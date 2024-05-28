package ui;
import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;

import algorithms.datastructures.AdjacencyListTest;
import core.Context;
import core.algorithms.datastructures.EdgeNode;
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

    @Test
    public void test2() {
        try {
            List<EdgeNode<BusStop>> a = adjacencyListGraph.neighbors(new BusStop(0, 0));
        } catch (IllegalArgumentException e) {
            assert(true);
        }

        BusStop stop = new BusStop(0, 0);
        try {
            adjacencyListGraph.addVertex(stop);
            adjacencyListGraph.addVertex(stop);
        } catch (IllegalArgumentException e) {
            assert(true);
        }

        try {
            adjacencyListGraph.removeVertex(stop);
            adjacencyListGraph.removeVertex(stop);
        } catch (IllegalArgumentException e) {
            assert(true);
        }
        BusStop stop2 = new BusStop(1, 1);
        try {
            adjacencyListGraph.addEdge(stop, stop2, 0);
        } catch (IllegalArgumentException e) {
            assert(true);
        }

        adjacencyListGraph.addVertex(stop);
        try {
            adjacencyListGraph.addEdge(stop, stop2, 0);
        } catch (IllegalArgumentException e) {
            assert(true);
        }

        try {
            adjacencyListGraph.removeEdge(stop, stop2);
        } catch (IllegalArgumentException e) {
            assert(true);
        }

        adjacencyListGraph.addVertex(stop2);
        adjacencyListGraph.addEdge(stop, stop2, 0);
        adjacencyListGraph.removeEdge(stop, stop2);

        assertEquals(8, adjacencyListGraph.getVertecesList().size());
        adjacencyListGraph.toString();

        Graph<BusStop> copy = adjacencyListGraph.clone();
    }
}
