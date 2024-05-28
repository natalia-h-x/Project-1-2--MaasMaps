package geometry;


import static org.junit.jupiter.api.Assertions.fail;
import java.awt.geom.Point2D;
import core.algorithms.datastructures.AdjacencyListGraph;
import org.junit.Test;
import core.algorithms.datastructures.Graph;
import core.models.BusStop;
import ui.map.geometry.AbstractedBusNetwork;

public class AbstractedBusNetworkTest {

    public static void main(String[] args) {
        Graph<BusStop> graph = createGraph();
        AbstractedBusNetwork abstractedBusNetwork = new AbstractedBusNetwork(graph);
    }

    @Test
    public void testCreateLines() {
        try {
            Graph<BusStop> graph = createGraph();
            AbstractedBusNetwork abstractedBusNetwork = new AbstractedBusNetwork(graph);
            abstractedBusNetwork.createLines(graph);
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void testCreateMarker() {
        try {
            Graph<BusStop> graph = createGraph();
            AbstractedBusNetwork abstractedBusNetwork = new AbstractedBusNetwork(graph);

            Point2D point = new Point2D.Double(50.0, 50.0);
            abstractedBusNetwork.createMarker(point);
        } catch (Exception e) {
            fail(e);
        }
    }

    private static Graph<BusStop> createGraph() {
        return new AdjacencyListGraph<>();
    }
}
