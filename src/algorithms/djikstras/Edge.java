package algorithms.djikstras;
import models.Location;


class Edge {
    Location destination;
    double weight;

    public Edge(Location destination, double weight) {
        this.destination = destination;
        this.weight = weight;
    }
}