package core.models.gtfs;

import java.util.HashMap;
import java.util.Map;

import core.managers.database.GTFSManager;
import core.models.BusStop;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Trip {
    private static Map<Integer, Shape> shapeMap = new HashMap<>();
    private int id;
    private int shapeId;
    private int routeId;
    private String tripHeadsign;

    public static Trip empty() {
        return new Trip(0, 0, 0, "");
    }

    public void copyInto(Trip trip) {
        id = trip.id;
        shapeId = trip.shapeId;
        routeId = trip.routeId;
        tripHeadsign = trip.tripHeadsign;
    }

    public Shape loadShape(BusStop from, BusStop to) {
        return shapeMap.computeIfAbsent(shapeId, GTFSManager::getShape).prune(from, to);
    }
}
