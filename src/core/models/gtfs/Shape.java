package core.models.gtfs;

import java.awt.Color;
import java.util.Arrays;

import core.managers.DistanceManager;
import core.models.BusStop;
import core.models.Location;
import lombok.Data;

@Data
public class Shape {
    private int id;
    private Color color;
    private Location[] locations;

    public Shape(int id, Color color, Location... locations) {
        this.color = color;
        this.locations = locations;
        this.id = id;
    }

    public static Shape empty() {
        return new Shape(0, Color.white);
    }

    public Shape prune(BusStop a, BusStop b) {
        int ca = closestLocationIndex(a);
        int cb = closestLocationIndex(b);

        Location[] prunedLocations = Arrays.copyOfRange(locations, Math.min(ca, cb), Math.max(ca, cb));

        return new Shape(id, color, prunedLocations);
    }

    private int closestLocationIndex(Location loc) {
        double previousDistance = Integer.MAX_VALUE;
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
