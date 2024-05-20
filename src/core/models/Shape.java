package core.models;

import java.awt.Color;

import core.algorithms.datastructures.EdgeNode;
import lombok.Data;

@Data
public class Shape extends EdgeNode<BusStop> {
    private Color color;
    private Location[] locations;

    public Shape(BusStop y, int weight, Color color, Location... locations) {
        super(y, weight);
        this.color = color;
        this.locations = locations;
    }
}
