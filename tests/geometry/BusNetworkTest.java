package geometry;

import static org.junit.jupiter.api.Assertions.fail;

import java.awt.geom.Point2D;

import org.junit.Test;

import core.algorithms.datastructures.AdjacencyListGraph;
import core.algorithms.datastructures.Graph;
import core.models.BusStop;
import ui.MaasMapsUI;
import ui.map.geometry.BusNetwork;
import ui.map.geometry.Line;
import ui.map.geometry.Marker;

public class BusNetworkTest {
    public BusNetworkTest() {
        new MaasMapsUI();
    }

    @Test
    public void testCreateLineSegment() {
        Point2D point1 = new Point2D.Double(50.0, 50.0);
        Point2D point2 = new Point2D.Double(100.0, 100.0);
        BusNetwork busNetwork = new BusNetwork(createGraph());
        Line line = busNetwork.createLineSegment(point1, point2);
    }

    @Test
    public void testCreateMarker() {
        BusStop point = new BusStop(50.0, 50.0);
        BusNetwork busNetwork = new BusNetwork(createGraph());
        Marker marker = busNetwork.createMarker(point);
    }

    private static Graph<BusStop> createGraph() {
        return new AdjacencyListGraph<>();
    }
}
