package geometry;

import org.junit.Test;

import core.datastructures.graph.AdjacencyListGraph;
import core.datastructures.graph.Graph;
import core.models.BusStop;
import ui.MaasMapsUI;
import ui.map.geometry.AbstractedBusNetwork;

public class AbstractedBusNetworkTest {
    public AbstractedBusNetworkTest() {
        new MaasMapsUI();
    }

    @Test
    public void testCreateLines() {
        Graph<BusStop> graph = createGraph();
        AbstractedBusNetwork abstractedBusNetwork = new AbstractedBusNetwork(graph);
        abstractedBusNetwork.createLines(graph);
    }

    @Test
    public void testCreateMarker() {
        Graph<BusStop> graph = createGraph();
        AbstractedBusNetwork abstractedBusNetwork = new AbstractedBusNetwork(graph);

        BusStop point = new BusStop(10, 10);
        abstractedBusNetwork.createMarker(point);
    }

    private static Graph<BusStop> createGraph() {
        return new AdjacencyListGraph<>();
    }
}
