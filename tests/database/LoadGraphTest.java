package database;

import static org.junit.jupiter.api.Assertions.fail;

import java.awt.geom.Point2D;
import java.util.EmptyStackException;
import java.util.Map;

import org.junit.Test;

import core.algorithms.datastructures.BusEdge;
import core.algorithms.datastructures.Edge;
import core.algorithms.datastructures.Graph;
import core.managers.MapManager;
import core.models.gtfs.Time;
import core.models.gtfs.Trip;
import ui.NetworkTest;

public class LoadGraphTest {

    public static void main(String[] args){
        test1();
    }

    @Test
    public static void test1() {
        try {
            Graph<Point2D> graph = MapManager.getBusGraph();

            for (Point2D p : graph) {
                for (Edge<Point2D> edge : graph.neighbors(p)) {
                    // if (edge.getWeight() == 0)
                    //     throw new NullPointerException("Weight is 0!");

                    if (edge instanceof BusEdge<Point2D> busEdge) {
                        if (busEdge.getDepartureTimes().isEmpty())
                            throw new EmptyStackException();

                        for (Map.Entry<Time, Trip> entry : busEdge.getDepartureTimes().entrySet()) {
                            if (entry.getKey() == null)
                                throw new NullPointerException("Time is null!");

                            if (entry.getValue() == null)
                                throw new NullPointerException("Trip is null!");
                        }
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
