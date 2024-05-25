package core.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Trip {
    private int id;
    private int shapeId;
    private int routeId;
    private String tripHeadsign;

    public static Trip empty() {
        return new Trip(0, 0, 0, null);
    }

    public void copyInto(Trip trip) {
        id = trip.id;
        shapeId = trip.shapeId;
        routeId = trip.routeId;
        tripHeadsign = trip.tripHeadsign;
    }
}
