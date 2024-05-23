package core.models;

import java.awt.Color;
import java.util.Arrays;

import core.managers.DistanceManager;
import lombok.Data;

@Data
public class Shape {
    private Color color;
    private Location[] locations;

    public Shape(Color color, Location... locations) {
        this.color = color;
        this.locations = locations;
    }

    public Shape prune(BusStop a, BusStop b) {
        int ca = closestLocationIndex(a);
        int cb = closestLocationIndex(b);

        Location[] prunedLocations = Arrays.copyOfRange(locations, Math.min(ca, cb), Math.max(ca, cb));

        return new Shape(color, prunedLocations);
    }

    private int closestLocationIndex(Location loc) {
        double previousDistance = 0;
        int best = Integer.MAX_VALUE;

        for (int i = 0; i < locations.length; i++) {
            Location location = locations[i];
            double distance = DistanceManager.haversine(location, loc);

            if (distance > previousDistance) {
                return best;
            }

            best = i;
            previousDistance = distance;
        }

        return best;
    }
}
