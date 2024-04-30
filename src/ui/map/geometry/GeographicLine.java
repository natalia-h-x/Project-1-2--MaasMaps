package ui.map.geometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import algorithms.util.DistanceCalculator;
import models.Location;

public class GeographicLine {
    private Line line = new Line();
    private List<Location> locations = new ArrayList<>();

    // take the locations as parameter
    public GeographicLine(Location... locations) {
        this.locations.addAll(Arrays.asList(locations));
    }

    public void addLocation(Location loc) {
        locations.add(loc);
    }

    public double getTotalDistance() {
        double totalDistance = 0;

        for (int i = 0; i < locations.size() - 1; i++) {
            Location loc1 = locations.get(i);
            Location loc2 = locations.get(i + 1);
            totalDistance += DistanceCalculator.haversine(loc1, loc2);
        }

        return totalDistance;
    }
}
