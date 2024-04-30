package ourGraphAPI;

public class AdjacencyMatrixGraph{
    private int[][] adjMatrix;
    private int numberOfVertices;

    public AdjacencyMatrixGraph(int numberOfVertices) {
        this.numberOfVertices = numberOfVertices;
        adjMatrix = new int[numberOfVertices][numberOfVertices];
    }

    public void addEdge(int i, int j, int weight) {
        if (i > adjMatrix.length || j > adjMatrix[0].length)
            throw new IllegalArgumentException("Adjancency matrix does not contain this vertex.");
        else if (weight < 0)
            throw new IllegalArgumentException("Weight: " + weight + "has to be positive.");

        adjMatrix[i][j] = weight;
        adjMatrix[i][j] = weight;
    }

    public void removeEdge(int i, int j) {
        if (i > adjMatrix.length || j > adjMatrix[0].length)
            throw new IllegalArgumentException("Adjancency matrix does not contain this vertex.");
        
        adjMatrix[i][j] = -1;
        adjMatrix[i][j] = -1;
    }

    public int[][] getAdjMatrix() {
        return adjMatrix;
    }
    
    public void setAdjMatrix(int[][] adjMatrix) {
        this.adjMatrix = adjMatrix;
    }

    public int getNumberOfVertices() {
        return numberOfVertices;
    }

    public void setNumberOfVertices(int numberOfVertices) {
        this.numberOfVertices = numberOfVertices;
    }
}

