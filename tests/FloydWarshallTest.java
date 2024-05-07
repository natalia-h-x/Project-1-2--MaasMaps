package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

import core.algorithms.FloydWarshall;
import core.algorithms.datastructures.AdjacencyMatrixGraph;

public class FloydWarshallTest {
    private FloydWarshall floydWarshall;
    private AdjacencyMatrixGraph adjacencyMatrixGraph;

    public FloydWarshallTest() {
        floydWarshall = new FloydWarshall();
        adjacencyMatrixGraph = new AdjacencyMatrixGraph(7);
    }
    
    @Test
    public void test1() {
        adjacencyMatrixGraph.addEdge(0, 1, 2);
        adjacencyMatrixGraph.addEdge(1, 2, 1);
        adjacencyMatrixGraph.addEdge(0, 2, 5);
        adjacencyMatrixGraph = floydWarshall.floydWarshall(adjacencyMatrixGraph);
        assertAll("Shortest Distance", 
            () -> assertEquals(3, adjacencyMatrixGraph.getAdjMatrix()[0][2])
        );
    }

    @Test
    public void test2() {
        adjacencyMatrixGraph.addEdge(0, 1, 7);
        adjacencyMatrixGraph.addEdge(1, 2, 1);
        adjacencyMatrixGraph.addEdge(0, 2, 5);
        adjacencyMatrixGraph.addEdge(0, 6, 100);
        adjacencyMatrixGraph.addEdge(2, 4, 10);
        adjacencyMatrixGraph.addEdge(1, 3, 2);
        adjacencyMatrixGraph.addEdge(4, 6, 5);
        adjacencyMatrixGraph = floydWarshall.floydWarshall(adjacencyMatrixGraph);
        assertAll("Shortest Distance", 
            () -> assertEquals(5, adjacencyMatrixGraph.getAdjMatrix()[0][2]),
            () -> assertEquals(1, adjacencyMatrixGraph.getAdjMatrix()[1][2]),
            () -> assertEquals(20, adjacencyMatrixGraph.getAdjMatrix()[0][6]),
            () -> assertEquals(11, adjacencyMatrixGraph.getAdjMatrix()[1][4]),
            () -> assertEquals(9999999, adjacencyMatrixGraph.getAdjMatrix()[3][4])
        );
    }

    @Test
    public void negativeTest() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
        adjacencyMatrixGraph.addEdge(0, 2, -100)
        );
        assertEquals("Weight: " + -100 + "has to be positive.", exception.getMessage());
    }

    @Test
    public void removeTest() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
        adjacencyMatrixGraph.removeEdge(2, 10)
        );
        assertEquals("Adjancency matrix does not contain this vertex.", exception.getMessage());

        adjacencyMatrixGraph.addEdge(2, 5, 2);
        adjacencyMatrixGraph.removeEdge(2, 5);
        assertEquals(9999999, adjacencyMatrixGraph.getAdjMatrix()[2][5]);

    }
}
