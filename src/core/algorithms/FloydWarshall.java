package core.algorithms;

import core.algorithms.datastructures.AdjacencyMatrixGraph;

public class FloydWarshall {
    public AdjacencyMatrixGraph floydWarshall(AdjacencyMatrixGraph graph) {
        int numberOfVertices = graph.getNumberOfVertices();
        AdjacencyMatrixGraph dist = new AdjacencyMatrixGraph(numberOfVertices);

        for (int i = 0; i < numberOfVertices; i++) {
            for (int j = 0; j < numberOfVertices; j++) {
                dist.addEdge(i, j, graph.getAdjMatrix()[i][j]);
            }
        }

        for (int i = 0; i < numberOfVertices; i++) {
            for (int j = 0; j < numberOfVertices; j++) {
                for (int k = 0; k < numberOfVertices; k++) {
                    if (dist.getAdjMatrix()[i][k] + dist.getAdjMatrix()[k][j] < dist.getAdjMatrix()[i][j])
                        dist.getAdjMatrix()[i][j] = dist.getAdjMatrix()[i][k] + dist.getAdjMatrix()[k][j];
                }
            }
        }

        return dist;
    }
}
