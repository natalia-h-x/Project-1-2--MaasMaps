package algorithms.djikstras;
import models.Location;

// You can rename this to Route.java and move it to /models
// can also rename <double weight> to <double distance/distanceInMeters> for intuition

class Edge {
    Location destination;
    double weight;

    public Edge(Location destination, double weight) {
        this.destination = destination;
        this.weight = weight;
    }
}