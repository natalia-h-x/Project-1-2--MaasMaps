package database;

import static org.junit.jupiter.api.Assertions.fail;

import java.awt.geom.Point2D;
import java.util.EmptyStackException;
import java.util.Map;

import org.junit.Test;

import core.algorithms.datastructures.EdgeNode;
import core.algorithms.datastructures.Graph;
import core.managers.MapManager;
import core.models.Time;
import core.models.Trip;
import ui.NetworkTest;

public class LoadGraphTest {
    public static void main(String[] args) {
        Graph<Point2D> graph = MapManager.getBusGraph();
        NetworkTest.makeAbstractedBusNetwork(graph);
    }

    @Test
    public void test1() {
        try {
            Graph<Point2D> graph = MapManager.getBusGraph();

            for (Point2D p : graph) {
                for (EdgeNode<Point2D> edge : graph.neighbors(p)) {
                    if (edge.getDepartureTimes().isEmpty())
                        throw new EmptyStackException();

                    // if (edge.getWeight() == 0)
                    //     throw new NullPointerException("Weight is 0!");

                    for (Map.Entry<Time, Trip> entry : edge.getDepartureTimes().entrySet()) {
                        if (entry.getKey() == null)
                            throw new NullPointerException("Time is null!");

                        if (entry.getValue() == null)
                            throw new NullPointerException("Trip is null!");
                    }
                }
            }

            NetworkTest.makeAbstractedBusNetwork(graph);
        }
        catch (Exception e) {
            fail(e);
        }
    }
}
