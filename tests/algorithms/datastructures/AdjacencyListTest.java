package algorithms.datastructures;

import core.algorithms.datastructures.AdjacencyListGraph;
import core.algorithms.datastructures.Graph;
import core.models.BusStop;

public class AdjacencyListTest {
    public static Graph<BusStop> createTestGraph() {
        Graph<BusStop> graph = new AdjacencyListGraph<>();

        BusStop loc1 = new BusStop(0, 50.839644, 5.684095, "Maastricht Bus Stop nr. 1");
        BusStop loc2 = new BusStop(0, 50.850692, 5.659871, "Maastricht Bus Stop nr. 2");
        BusStop loc3 = new BusStop(0, 50.862971, 5.655573, "Maastricht Bus Stop nr. 3");
        BusStop loc4 = new BusStop(0, 50.85297, 5.706588, "Maastricht Bus Stop nr. 4");
        BusStop loc5 = new BusStop(0, 50.836008, 5.731903, "Maastricht Bus Stop nr. 5");
        BusStop loc6 = new BusStop(0, 50.833482, 5.703838, "Maastricht Bus Stop nr. 6");

        graph.addVertex(loc1);
        graph.addVertex(loc2);
        graph.addVertex(loc3);
        graph.addVertex(loc4);
        graph.addVertex(loc5);
        graph.addVertex(loc6);

        graph.addEdge(loc1, loc2, 3);
        graph.addEdge(loc2, loc3, 3);
        graph.addEdge(loc3, loc4, 4);
        graph.addEdge(loc4, loc5, 4);
        graph.addEdge(loc5, loc6, 5);
        graph.addEdge(loc6, loc1, 5);

        return graph;
    }
}
