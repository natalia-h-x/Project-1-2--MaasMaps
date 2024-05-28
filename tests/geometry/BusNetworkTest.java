package geometry;

import static algorithms.datastructures.AdjacencyListTest.createTestGraph;
import static org.junit.jupiter.api.Assertions.fail;
import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.List;

import core.algorithms.datastructures.AdjacencyListGraph;
import core.algorithms.datastructures.EdgeNode;
import org.junit.Test;
import core.algorithms.datastructures.Graph;
import core.models.BusStop;
import ui.map.geometry.BusNetwork;
import ui.map.geometry.Line;
import ui.map.geometry.Marker;

public class BusNetworkTest {

    public static void main(String[] args) {
        Graph<BusStop> graph = createGraph();
        BusNetwork busNetwork = new BusNetwork(graph);
    }

    @Test
    public void testCreateLineSegment() {
        try {
            Point2D point1 = new Point2D.Double(50.0, 50.0);
            Point2D point2 = new Point2D.Double(100.0, 100.0);
            BusNetwork busNetwork = new BusNetwork(createGraph());
            Line line = busNetwork.createLineSegment(point1, point2);
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void testCreateMarker() {
        try {
            Point2D point = new Point2D.Double(50.0, 50.0);
            BusNetwork busNetwork = new BusNetwork(createGraph());
            Marker marker = busNetwork.createMarker(point);
        } catch (Exception e) {
            fail(e);
        }
    }

    private static Graph<BusStop> createGraph() {
        return new AdjacencyListGraph<>();
    }
}
