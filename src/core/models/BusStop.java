package core.models;

import java.util.HashMap;
import java.util.Map;

import core.managers.DatabaseManager;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class BusStop extends Location {
    private static Map<Integer, Shape> shapeMap = new HashMap<>();
    private String stopName;
    private Trip trip;

    public BusStop(double latitude, double longitude) {
        super(latitude, longitude);
    }

    public BusStop(double latitude, double longitude, String stopName) {
        this(latitude, longitude);
        this.stopName = stopName;
    }

    public Shape loadShape(BusStop to) {
        return shapeMap.computeIfAbsent(trip.getShapeId(), DatabaseManager::getShape).prune(this, to);
    }
}
