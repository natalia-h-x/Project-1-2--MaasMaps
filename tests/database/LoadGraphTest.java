package tests.database;

import static org.junit.Assert.assertTrue;

import java.awt.geom.Point2D;
import java.sql.SQLException;

import org.junit.Test;

import core.algorithms.datastructures.Graph;
import core.managers.DatabaseManager;
import tests.NetworkTest;

public class LoadGraphTest {
    @Test
    public void test1() {
        try {
            Graph<Point2D> graph = DatabaseManager.loadGraph();
            new NetworkTest(graph).test1();
        }
        catch (SQLException e) {
            assertTrue(false);
        }
    }
}
