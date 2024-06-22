package ui;

import org.junit.jupiter.api.Test;

import algorithms.datastructures.AdjacencyListTest;
import core.Context;
import core.datastructures.graph.Graph;
import core.models.BusStop;
import ui.map.geometry.AbstractedBusNetwork;
import ui.map.geometry.Network;
import ui.map.geometry.Point2DImpostor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.geom.Point2D;
import java.util.function.UnaryOperator;

public class Point2DImpostorTest {
    private Graph<BusStop> adjacencyListGraph;

    public Point2DImpostorTest() {
        adjacencyListGraph = AdjacencyListTest.createTestGraph();
    }

    @Test
    public void creatingAnImpostor() {
        makeAbstractedBusNetwork(adjacencyListGraph);
    }

    public static <P extends Point2D> void makeAbstractedBusNetwork(Graph<P> adjacencyListGraph) {
        new MaasMapsUI();

        Point2D point2d = null;
        for (P p : adjacencyListGraph) {
            point2d = p;
        }

        Point2DImpostor point2dImpostor = new Point2DImpostor(point2d);
        point2dImpostor.setLocation(2, 3);
        point2dImpostor.setLocation(point2d);
        assertNotEquals(point2dImpostor.distanceSq(2, 2), point2dImpostor.distanceSq(point2d));
        assertNotEquals(point2dImpostor.distanceSq(2, 2), point2dImpostor.distance(2, 2));
        assertEquals(point2dImpostor.distanceSq(point2d), point2dImpostor.distance(point2d));

        assertNotEquals(point2d, point2dImpostor);

        Point2DImpostor point2dImpostor2 = new Point2DImpostor(point2d);
        assertEquals(point2dImpostor2.toString(), point2d.toString());
        assertEquals(point2dImpostor2.hashCode(), point2d.hashCode());
        assertTrue(point2dImpostor2.equals(point2d));

        Point2DImpostor point2dImpostor3 = new Point2DImpostor(point2d, p -> p);
        assertNotEquals(point2dImpostor2, point2dImpostor3);

        point2dImpostor2.setImpostorFunction(point2dImpostor3.getImpostorFunction());
        assertEquals(point2dImpostor2.getImpostorFunction(), point2dImpostor3.getImpostorFunction());

        point2dImpostor.setImposedPoint(point2dImpostor2.getImposedPoint());
        assertEquals(point2dImpostor.getImposedPoint(), point2dImpostor2.getImposedPoint());

    }
}
