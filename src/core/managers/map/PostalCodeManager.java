package core.managers.map;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import core.Context;
import core.models.Location;
import core.models.ZipCode;

public class PostalCodeManager {
    private PostalCodeManager() {}

    public static Location getClosestPoint(Location to) {
        try {
            return getClosestPoint(to, 1)[0];
        }
        catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Location %s is not in the graph".formatted(to), e);
        }
    }

    public static Location[] getClosestPoint(Location to, int n) {
        return getClosestPoint(MapManager.getBusGraph().getVertecesList(), to, n);
    }

    public static <P extends Point2D> Location[] getClosestPoint(List<P> list, Location to, int n) {
        Map<Location, Double> distances = new HashMap<>();
        PriorityQueue<Location> closestSet = new PriorityQueue<>((a, b) -> distances.get(b).compareTo(distances.get(a)));
        double worstDist = Double.NEGATIVE_INFINITY;

        for (Point2D point : list) {
            Location location = ((Location) point);
            double dist = location.distance(to);

            distances.put(location, dist);

            if (closestSet.size() < n) {
                closestSet.add(location);
                worstDist = Math.max(dist, worstDist);

                continue;
            }

            if (dist < worstDist) {
                closestSet.poll();
                closestSet.add(location);

                worstDist = dist;

                for (Location l : closestSet) {
                    worstDist = Math.max(distances.get(l), worstDist);
                }
            }
        }

        return closestSet.toArray(Location[]::new);
    }

    public static List<Location> getAllPointsWithin(Location center, Double radius) {
        Map<Location, Double> distances = new HashMap<>();
        PriorityQueue<Location> closestSet = new PriorityQueue<>((a, b) -> distances.get(b).compareTo(distances.get(a)));

        for (Point2D vertex : MapManager.getBusGraph()) {
            Location location = ((Location) vertex);
            double dist = location.distance(center);

            distances.put(location, dist);

            if (dist < radius) {
                closestSet.add(location);
            }
        }

        return Arrays.asList(closestSet.toArray(Location[]::new));
    }

    public static String getRandomPostalCode() {
        List<ZipCode> zipCodes = Context.getContext().getZipCodeDatabase().getZipCodes();
        return zipCodes.get((int) (zipCodes.size() * Context.getContext().getRandom().nextDouble())).getCode();
    }
}
