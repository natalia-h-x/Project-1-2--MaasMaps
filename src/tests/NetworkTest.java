package tests;

import org.junit.Test;

import core.Context;
import core.algorithms.datastructures.AdjacencyListGraph;
import core.models.Location;
import ui.MaasMapsUI;
import ui.map.geometry.Network;

public class NetworkTest {
    private AdjacencyListGraph<Location> adjacencyListGraph;

    public NetworkTest() {
        adjacencyListGraph = new AdjacencyListGraph<>();
    }

    public static void main(String[] args) {
        new NetworkTest().test1();
    }

    @Test
    public void test1() {
        Location loc1 = new Location(50.855233,5.692237);
        Location loc2 = new Location(50.853608,5.691958);
        Location loc3 = new Location(50.853617,5.692009);
        Location loc4 = new Location(50.853037,5.691825);
        Location loc5 = new Location(50.854993,5.692294);
        Location loc6 = new Location(50.854581,5.690199);
        adjacencyListGraph.addVertex(loc1);
        adjacencyListGraph.addVertex(loc2);
        adjacencyListGraph.addVertex(loc3);
        adjacencyListGraph.addVertex(loc4);
        adjacencyListGraph.addVertex(loc5);
        adjacencyListGraph.addVertex(loc6);

        adjacencyListGraph.addEdge(loc1, loc2);
        adjacencyListGraph.addEdge(loc2, loc3);
        adjacencyListGraph.addEdge(loc3, loc4);
        adjacencyListGraph.addEdge(loc4, loc5);
        adjacencyListGraph.addEdge(loc5, loc6);
        adjacencyListGraph.addEdge(loc6, loc1);

        new MaasMapsUI();

        Context.getContext().getMap().addMapIcon(new Network(adjacencyListGraph));
    }
}