package ui;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.TreeMap;

import org.junit.Test;

import algorithms.datastructures.AdjacencyListTest;
import core.Context;
import core.datastructures.graph.AdjacencyListGraph;
import core.datastructures.graph.BusEdge;
import core.datastructures.graph.Edge;
import core.datastructures.graph.Graph;
import core.datastructures.graph.WalkingEdge;
import core.managers.map.MapManager;
import core.models.BusStop;
import ui.map.geometry.AbstractedBusNetwork;
import ui.map.geometry.ArrowStroke;
import ui.map.geometry.BusNetwork;
import ui.map.geometry.Network;
import ui.map.geometry.factories.BusMarkerFactory;
import ui.map.geometry.interfaces.MapGraphics;

public class NetworkTest {
    private Graph<BusStop> adjacencyListGraph;

    public NetworkTest() {
        new MaasMapsUI();
        adjacencyListGraph = AdjacencyListTest.createTestGraph();
    }

    @Test
    public void test1() {
        makeAbstractedBusNetwork(adjacencyListGraph);
        makeNetwork(adjacencyListGraph);
    }

    @Test
    public void test3() {
        makeBusNetwork(MapManager.getBusGraph());
    }

    public static <P extends Point2D> void makeBusNetwork(Graph<P> adjacencyListGraph) {
        BusNetwork busNetwork = new BusNetwork(adjacencyListGraph);
        busNetwork.setPaint(Color.gray);
        Context.getContext().getMap().addMapGraphics(busNetwork);
    }

    public static <P extends Point2D> void makeAbstractedBusNetwork(Graph<P> adjacencyListGraph) {
        BusNetwork busNetwork = new BusNetwork(adjacencyListGraph);
        busNetwork.setPaint(Color.gray);
        Context.getContext().getMap().addMapGraphics(busNetwork);

        AbstractedBusNetwork abstractedBusNetwork = new AbstractedBusNetwork(adjacencyListGraph);
        abstractedBusNetwork.setPaint(Color.green);
        abstractedBusNetwork.createLineSegment();
        abstractedBusNetwork.createLines(adjacencyListGraph);

        for (P p1 : adjacencyListGraph) {
            abstractedBusNetwork.createMarker(p1);
        }

        Network network = new Network(adjacencyListGraph);
        network.createLines(adjacencyListGraph);

        Context.getContext().getMap().addMapGraphics(abstractedBusNetwork);

        network.setFactory(new BusMarkerFactory());
        assertTrue(network.getFactory() instanceof BusMarkerFactory);
        network.setStroke(new ArrowStroke(2, 1, 2));
        List<MapGraphics> list = new ArrayList<>();
        list.add(abstractedBusNetwork);
        network.setMapGraphics(list);
        assertTrue(network.getMapGraphics().contains(abstractedBusNetwork));
        assertFalse(network.getPaint().equals(abstractedBusNetwork.getPaint()));
    }

    public static <P extends Point2D> void makeNetwork(Graph<P> adjacencyListGraph) {
        Network network = new Network(adjacencyListGraph);
        network.createLines(adjacencyListGraph);

        Network network2 = new Network(new AdjacencyListGraph<>());
        network2.createLines(new AdjacencyListGraph<>());

        Context.getContext().getMap().addMapGraphics(network);
    }

    @Test
    public void test2() {
        try {
            adjacencyListGraph.neighbors(new BusStop(0, 0));
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

        adjacencyListGraph.clone();
    }

    @Test
    public void edgeNodeTest() {
        BusEdge<Integer> a = new BusEdge<>(1, 0);
        BusEdge<Integer> b = new BusEdge<>(1, 0);
        assertTrue(a.equals(b));
        assertTrue(a.hashCode() == b.hashCode());
        assertTrue(a.toString().equals(b.toString()));

        a.setElement(2);
        b.setWeight(4);
        b.setDepartureTimes(new TreeMap<>());
        assertFalse(a.equals(b));
    }
}
