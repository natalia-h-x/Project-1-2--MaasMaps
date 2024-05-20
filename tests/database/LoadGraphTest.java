package database;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.Test;

import core.algorithms.datastructures.Graph;
import core.managers.DatabaseManager;
import core.models.BusStop;
import ui.NetworkTest;

public class LoadGraphTest {
    public static void main(String[] args) {
        Graph<BusStop> graph = DatabaseManager.loadGraph();
        NetworkTest.makeAbstractedBusNetwork(graph);
    }

    @Test
    public void test1() {
        try {
            Graph<BusStop> graph = DatabaseManager.loadGraph();
            NetworkTest.makeAbstractedBusNetwork(graph);
        }
        catch (Exception e) {
            fail(e);
        }
    }
}
