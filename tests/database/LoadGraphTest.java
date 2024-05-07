package database;

import static org.junit.Assert.assertTrue;

import java.awt.geom.Point2D;
import java.sql.SQLException;

import org.junit.Test;

import core.algorithms.datastructures.Graph;
import core.managers.DatabaseManager;
import core.models.BusStop;
import ui.NetworkTest;

public class LoadGraphTest {
    @Test
    public void test1() {
        try {
            Graph<BusStop> graph = DatabaseManager.loadGraph();
            //new NetworkTest(graph).test1();
        }
        catch (SQLException e) {
            assertTrue(false);
        }
    }
}
